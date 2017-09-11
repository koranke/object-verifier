package objectVerifier;

import objectVerifier.verificationRules.VerificationRule;

import java.util.List;

public class Assert extends org.testng.Assert {

	public static void objectsAreEqual(
			Object actualObject,
			Object expectedObject) {

		objectsAreEqual(actualObject, expectedObject, null, null, null);
	}

	public static void objectsAreEqual(
			Object actualObject,
			Object expectedObject,
			FieldsToCheck fieldsToCheck) {

		objectsAreEqual(actualObject, expectedObject, fieldsToCheck, null, null);
	}

	public static void objectsAreEqual(
			Object actualObject,
			Object expectedObject,
			List<VerificationRule> verificationRules) {

		objectsAreEqual(actualObject, expectedObject, null, verificationRules, null);
	}

	public static void objectsAreEqual(
			Object actualObject,
			Object expectedObject,
			FieldsToCheck fieldsToCheck,
			List<VerificationRule> verificationRules) {

		objectsAreEqual(actualObject, expectedObject, fieldsToCheck, verificationRules, null);
	}

	public static void objectsAreEqual(
			Object actualObject,
			Object expectedObject,
			FieldsToCheck fieldsToCheck,
			List<VerificationRule> verificationRules,
			String errorMessage) {

		ObjectVerifier.verifyDomainObject(
				actualObject.getClass(),
				actualObject,
				expectedObject,
				fieldsToCheck,
				verificationRules,
				errorMessage);
	}
}
