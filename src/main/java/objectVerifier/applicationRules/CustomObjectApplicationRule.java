package objectVerifier.applicationRules;

import objectVerifier.utilities.ObjectHelper;

public class CustomObjectApplicationRule implements IVerificationRuleApplicationRule {

	public boolean dataIsApplicable(Object actualObject, Object expectedObject) {
		if (actualObject != null) {
			return !ObjectHelper.implementsEquals(actualObject.getClass());
		}
		if (expectedObject != null) {
			return !ObjectHelper.implementsEquals(expectedObject.getClass());
		}
		return false;
	}

}
