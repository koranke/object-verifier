package objectVerifier;

import com.google.common.collect.Lists;
import objectVerifier.utilities.ObjectHelper;
import objectVerifier.verificationRules.VerificationRule;
import java.util.List;

public class Verify {
	private Object actualObject;
	private Object expectedObject;
	private FieldsToCheck fieldsToCheck;
	private List<VerificationRule> verificationRules;

	public Object getActualObject() {
		return actualObject;
	}

	public Verify setActualObject(Object actualObject) {
		this.actualObject = actualObject;
		return this;
	}

	public Object getExpectedObject() {
		return expectedObject;
	}

	public Verify setExpectedObject(Object expectedObject) {
		this.expectedObject = expectedObject;
		return this;
	}

	public FieldsToCheck getFieldsToCheck() {
		return fieldsToCheck;
	}

	public Verify setFieldsToCheck(FieldsToCheck fieldsToCheck) {
		this.fieldsToCheck = fieldsToCheck;
		return this;
	}

	public List<VerificationRule> getVerificationRules() {
		return verificationRules;
	}

	public Verify setVerificationRules(List<VerificationRule> verificationRules) {
		this.verificationRules = verificationRules;
		return this;
	}

	public static Verify that(Object actualObject) {
		Verify verify = new Verify();
		verify.actualObject = actualObject;
		return verify;
	}

	public Verify usingFields(FieldsToCheck fieldsToCheck) {
		this.fieldsToCheck = fieldsToCheck;
		return this;
	}

	public Verify usingFields(String ... fieldsToCheck) {
		this.fieldsToCheck = new FieldsToCheck();
		this.fieldsToCheck.withKey(Object.class);
		for (String fieldToCheck : fieldsToCheck) {
			this.fieldsToCheck.includeField(fieldToCheck);
		}
		return this;
	}

	public Verify usingRules(List<VerificationRule> rules) {
		this.verificationRules = rules;
		return this;
	}

	public Verify usingRule(VerificationRule rule) {
		this.verificationRules = Lists.newArrayList(rule);
		return this;
	}

	public void isEqualTo(Object expectedObject) {
		this.expectedObject = expectedObject;

		boolean isListComparison = (actualObject != null && ObjectHelper.isList(actualObject.getClass()))
				|| (expectedObject != null && ObjectHelper.isList(expectedObject.getClass()));

		if (isListComparison) {
			ObjectVerifier.verifyObject(
					actualObject,
					expectedObject,
					fieldsToCheck,
					verificationRules,
					null);
		} else {
			ObjectVerifier.verifyDomainObject(
					actualObject.getClass(),
					actualObject,
					expectedObject,
					fieldsToCheck,
					verificationRules,
					null);
		}
	}


}
