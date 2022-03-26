package objectVerifier;

import objectVerifier.verificationRules.VerificationRule;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Verify {
	private Object actualObject;
	private Object expectedObject;
	private FieldsToCheck fieldsToCheck;
	private List<VerificationRule> verificationRules;
	private String contextMessage;

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

	public static Verify that(Object actualObject) {
		Verify verify = new Verify();
		verify.actualObject = actualObject;
		return verify;
	}

	public Verify usingFields(FieldsToCheck fieldsToCheck) {
		this.fieldsToCheck = fieldsToCheck;
		return this;
	}

	public Verify usingFields(List<String> fieldsToCheck) {
		this.fieldsToCheck = new FieldsToCheck();
		this.fieldsToCheck.withKey(Object.class);
		for (String fieldToCheck : fieldsToCheck) {
			this.fieldsToCheck.includeField(fieldToCheck);
		}
		return this;
	}

	public Verify usingFields(String ... fieldsToCheck) {
		return usingFields(Arrays.asList(fieldsToCheck));
	}

	public Verify usingRules(List<VerificationRule> rules) {
		if (verificationRules == null) {
			verificationRules = new ArrayList<>();
		}
		this.verificationRules.addAll(rules);
		return this;
	}

	public Verify usingRule(VerificationRule rule) {
		if (verificationRules == null) {
			verificationRules = new ArrayList<>();
		}
		this.verificationRules.add(rule);
		return this;
	}

	public void isEqualTo(Object expectedObject) {
		this.expectedObject = expectedObject;

		ObjectVerifier.verifyObject(
				actualObject,
				expectedObject,
				fieldsToCheck,
				verificationRules,
				contextMessage);
	}

	public Verify withContextMessage(String contextMessage) {
		this.contextMessage = contextMessage;
		return this;
	}
}
