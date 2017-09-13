package objectVerifier;

import objectVerifier.utilities.RulesHelper;
import objectVerifier.utilities.IntrospectionHelper;
import objectVerifier.verificationRules.*;
import org.testng.Assert;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObjectVerifier {

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

		verificationRules = RulesHelper.setRulesToDefaultValuesIfNotSet(verificationRules);
		Set<String> fieldsRemainingToCheck = getRestrictedFieldList(domainObjectClass, fieldsToCheck);
		PropertyDescriptor[] dataItemDescriptors = IntrospectionHelper.getPropertyDescriptors(domainObjectClass);

		for (PropertyDescriptor dataItemDescriptor : dataItemDescriptors) {
			String currentDataItemName = dataItemDescriptor.getName();
			fieldsRemainingToCheck.remove(currentDataItemName);
			String standardErrorMessage = getStandardErrorMessage(domainObjectClass, currentDataItemName, contextMessage);

			if (thisFieldNeedsToBeChecked(fieldsToCheck, domainObjectClass, currentDataItemName, dataItemDescriptor)) {
				verifyEquality(domainObjectClass, dataItemDescriptor, actualObject, expectedObject, fieldsToCheck,
						verificationRules, standardErrorMessage);
			}
		}
		if (fieldsRemainingToCheck.size() > 0) {
			Assert.fail(String.format("Some fields were not validated as they were not found: %s.", fieldsRemainingToCheck));
		}
	}

	private static String getStandardErrorMessage(Class<?> domainObjectClass, String currentDateMemberName, String contextMessage) {
		String standardErrorMessage = String.format("%sUnexpected results for %s.%s.", System.lineSeparator(), domainObjectClass.getSimpleName(), currentDateMemberName);
		if (contextMessage != null && !contextMessage.equals("")) {
			standardErrorMessage = String.format("%s%s%s%s", System.lineSeparator(), contextMessage, System.lineSeparator(), standardErrorMessage);
		}
		return standardErrorMessage;
	}

	private static Set<String> getRestrictedFieldList(Class<?> cls, FieldsToCheck fieldsToCheck) {
		Class classToCheck = getClassToCheckForFields(fieldsToCheck, cls);

		if (classToCheck != null && fieldsToCheck != null && fieldsToCheck.fieldListForClassIsRestricted(classToCheck))  {
			return new HashSet<>(fieldsToCheck.getFieldListForClass(classToCheck));
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

			Class classToCheck = getClassToCheckForFields(fieldsToCheck, cls);
			return fieldsToCheck == null ||
					classToCheck == null ||
					fieldsToCheck.getFieldListForClass(classToCheck) == null ||
					(fieldsToCheck.getFieldListForClass(classToCheck).contains(currentField) && !fieldsToCheck.fieldIsExcluded(classToCheck, currentField)) ||
					(!fieldsToCheck.getFieldListForClass(classToCheck).contains(currentField) && !fieldsToCheck.fieldListForClassIsRestricted(classToCheck));
		}
	}

	private static Class getClassToCheckForFields(FieldsToCheck fieldsToCheck, Class currentClass) {
		if (fieldsToCheck == null) {
			return null;
		}
		Class classToCheck = null;
		if (fieldsToCheck.getFieldListForClass(currentClass) != null) {
			classToCheck = currentClass;
		} else if (fieldsToCheck.getFieldListForClass(Object.class) != null) {
			classToCheck = Object.class;
		}
		return classToCheck;
	}

	private static void verifyEquality(Class<?> domainObjectClass,
									   PropertyDescriptor dataItemDescriptor,
									   Object objectUnderTest,
									   Object comparisonObject,
									   FieldsToCheck fieldsToCheck,
									   List<VerificationRule> verificationRules,
									   String errorMessage) {

		Object actualObject = IntrospectionHelper.getGetterResult(dataItemDescriptor, objectUnderTest);
		Object expectedObject = IntrospectionHelper.getGetterResult(dataItemDescriptor, comparisonObject);

		if (actualObject == null && expectedObject == null) return;
		if (actualObject == null || expectedObject == null) {
			String nullItem = actualObject == null ? "Actual item" : "Expected item";
			String nonnullItem = actualObject == null ? "Expected item" : "Actual item";
			Object nonnullObject = actualObject == null ? expectedObject : actualObject;
			Assert.fail(String.format("%s%sItems do not match.  %s is null.  %s is not null [%s].",
					errorMessage, System.lineSeparator(), nullItem, nonnullItem, nonnullObject.toString()));
		}

		if (fieldsToCheck != null &&
				fieldsToCheck.fieldHasVerificationRules(domainObjectClass, dataItemDescriptor.getDisplayName())) {

			verificationRules = RulesHelper.getMergedRules(
					fieldsToCheck.getFieldVerificationRules(domainObjectClass, dataItemDescriptor.getDisplayName()),
					verificationRules
			);
		}

		verifyObject(actualObject, expectedObject, fieldsToCheck, verificationRules, errorMessage);
	}

	public static void verifyObject(Object actualObject,
										Object expectedObject,
										FieldsToCheck fieldsToCheck,
										List<VerificationRule> verificationRules,
										String errorMessage) {

		for (VerificationRule verificationRule : verificationRules) {
			verificationRule.verify(actualObject, expectedObject, fieldsToCheck, verificationRules, errorMessage);
		}
	}
}
