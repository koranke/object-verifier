package objectVerifier;

import com.google.common.collect.Lists;
import objectVerifier.applicationRules.DateApplicationRule;
import objectVerifier.applicationRules.IVerificationRuleApplicationRule;
import objectVerifier.utilities.IntrospectionHelper;
import objectVerifier.utilities.ObjectHelper;
import objectVerifier.verificationRules.*;
import org.testng.Assert;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObjectVerifier {
	public static List<String> customDomainPackages = Lists.newArrayList("package rhapsody.qa", "package com.real", "package qa.rhapsody");

	public static void verifyDomainObject(Class<?> domainObjectClass,
									Object actualObject,
									Object expectedObject,
									FieldsToCheck fieldsToCheck,
									List<VerificationRule> verificationRules,
									String contextMessage) {

		if (actualObject == null && expectedObject == null) return;

		if (actualObject == null || expectedObject == null) {
			String msg = String.format("Verification failed for object %s.", domainObjectClass.getSimpleName());
			msg += actualObject == null ? " Actual object is null." : "Actual object is not null.";
			Assert.fail(msg);
		}

		verificationRules = setGlobalRulesToDefaultValuesIfNotSet(verificationRules);
		Set<String> fieldsRemainingToCheck = getRestrictedFieldList(domainObjectClass, fieldsToCheck);
		PropertyDescriptor[] dataItemDescriptors = IntrospectionHelper.getPropertyDescriptors(domainObjectClass);

		for (PropertyDescriptor dataItemDescriptor : dataItemDescriptors) {
			String currentDataItemName = dataItemDescriptor.getName();
			fieldsRemainingToCheck.remove(currentDataItemName);
			String standardErrorMessage = getStandardErrorMessage(domainObjectClass, currentDataItemName, contextMessage);

			if (thisFieldNeedsToBeChecked(fieldsToCheck, domainObjectClass, currentDataItemName, dataItemDescriptor)) {
				verifyField(dataItemDescriptor, actualObject, expectedObject, fieldsToCheck, verificationRules, standardErrorMessage);
			}
		}
		if (fieldsRemainingToCheck.size() > 0) {
			Assert.fail(String.format("Some fields were not validated as they were not found: %s.", fieldsRemainingToCheck));
		}
	}

	private static List<VerificationRule> setGlobalRulesToDefaultValuesIfNotSet(List<VerificationRule> existingRules) {
		List<VerificationRule> defaultRules = getDefaultRules();
		defaultRules.add(new ListExactMatchRule().setChildVerificationRules(getDefaultRules()));

		if (existingRules == null || existingRules.size() == 0) {
			return Lists.newArrayList(defaultRules);
		} else {
			for (VerificationRule defaultRule : defaultRules) {
				boolean matchingRuleFound = false;
				for (VerificationRule existingRule : existingRules) {
					if (matchingApplicationRuleFound(defaultRule.getApplicationRules(), existingRule.getApplicationRules())) {
						matchingRuleFound = true;
					}
				}
				if (!matchingRuleFound) {
					existingRules.add(defaultRule);
				}
			}
			return existingRules;
		}
	}

	private static List<VerificationRule> getDefaultRules() {
		List<VerificationRule> defaultRules = new ArrayList<>();
		defaultRules.add(new StringExactMatchRule());
		defaultRules.add(new NumberExactMatchRule());
		defaultRules.add(new DateTimeInRangeRule(5, ChronoUnit.MINUTES ));
		return defaultRules;
	}

	private static boolean matchingApplicationRuleFound(
			List<IVerificationRuleApplicationRule> ruleList1,
			List<IVerificationRuleApplicationRule> ruleList2) {

		for (IVerificationRuleApplicationRule ruleFromList1 : ruleList1) {
			for (IVerificationRuleApplicationRule ruleFromList2 : ruleList2) {
				if (ruleFromList1.getClass() == ruleFromList2.getClass()) {
					return true;
				}
			}
		}
		return false;
	}

	private static String getStandardErrorMessage(Class<?> domainObjectClass, String currentDateMemberName, String contextMessage) {
		String standardErrorMessage = String.format("%sUnexpected results for %s.%s.", System.lineSeparator(), domainObjectClass.getSimpleName(), currentDateMemberName);
		if (contextMessage != null && !contextMessage.equals("")) {
			standardErrorMessage += String.format("%sContext Message: %s%s", System.lineSeparator(), contextMessage, System.lineSeparator());
		}
		return standardErrorMessage;
	}

	private static Set<String> getRestrictedFieldList(Class<?> cls, FieldsToCheck fieldsToCheck) {
		if (fieldsToCheck != null &&
				fieldsToCheck.getFieldListForClass(cls) != null &&
				fieldsToCheck.fieldListForClassIsRestricted(cls))  {

			return new HashSet<>(fieldsToCheck.getFieldListForClass(cls));
		}
		return new HashSet<>();
	}

	private static <T> boolean thisFieldNeedsToBeChecked(FieldsToCheck fieldsToCheck, Class<T> cls, String currentField, PropertyDescriptor dataItemDescriptor) {
		if (cls == Class.class || currentField.equals("class")) {
			return false;
		} else {
			Annotation[] annotations = dataItemDescriptor.getReadMethod().getDeclaredAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().equals(java.beans.Transient.class)) {
					return false;
				}
			}

			return fieldsToCheck == null || fieldsToCheck.getFieldListForClass(cls) == null ||
					(fieldsToCheck.getFieldListForClass(cls).contains(currentField) && !fieldsToCheck.fieldIsExcluded(cls, currentField)) ||
					(!fieldsToCheck.getFieldListForClass(cls).contains(currentField) && !fieldsToCheck.fieldListForClassIsRestricted(cls));
		}
	}

	private static void verifyField(PropertyDescriptor dataItemDescriptor,
									Object objectUnderTest,
									Object comparisonObject,
									FieldsToCheck fieldsToCheck,
									List<VerificationRule> verificationRules,
									String standardErrorMessage) {

		if (isCustomType(dataItemDescriptor)) {
			verifyDomainObject(dataItemDescriptor.getPropertyType(),
					IntrospectionHelper.getGetterResult(dataItemDescriptor, objectUnderTest),
					IntrospectionHelper.getGetterResult(dataItemDescriptor, comparisonObject),
					fieldsToCheck,
					verificationRules,
					standardErrorMessage);
		} else {
			verifyEquality(dataItemDescriptor, objectUnderTest, comparisonObject, verificationRules, standardErrorMessage);
		}
	}


	private static void verifyEquality(PropertyDescriptor dataItemDescriptor,
									   Object objectUnderTest,
									   Object comparisonObject,
									   List<VerificationRule> verificationRules,
									   String errorMessage) {

		Object actualObject = IntrospectionHelper.getGetterResult(dataItemDescriptor, objectUnderTest);
		Object expectedObject = IntrospectionHelper.getGetterResult(dataItemDescriptor, comparisonObject);

		verifyEquality(actualObject, expectedObject, verificationRules, errorMessage);
	}

	private static void verifyEquality(Object objectUnderTest,
									   Object comparisonObject,
									   List<VerificationRule> verificationRules,
									   String errorMessage) {

		if (objectUnderTest == null && comparisonObject == null) return;
		if (objectUnderTest == null || comparisonObject == null) {
			Assert.fail(String.format("Data does not match.  Actual: %s.  Expected: %s. %s", errorMessage));
		}

		for (VerificationRule verificationRule : verificationRules) {
			verificationRule.verify(objectUnderTest, comparisonObject, errorMessage);
		}
	}


	public boolean isCustomType(Object actualObject, Object expectedObject) {
		if (actualObject != null) {
			return isCustomType(actualObject.getClass());
		}
		if (expectedObject != null) {
			return isCustomType(expectedObject.getClass());
		}
		return false;
	}

	private static boolean isCustomType(PropertyDescriptor propertyDescriptor) {
		return isCustomType(propertyDescriptor.getPropertyType());
	}

	private static boolean isCustomType(Class<?> cls) {
		return cls.getPackage() != null &&
				packageMatchesCustomDomainPackage(cls)
				&& !ObjectHelper.implementsEquals(cls);
	}

	private static boolean packageMatchesCustomDomainPackage(Class<?> cls) {
		for (String classPackage : customDomainPackages) {
			if (cls.getPackage().toString().startsWith(classPackage)) {
				return true;
			}
		}
		return false;
	}

}
