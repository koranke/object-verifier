package objectVerifier.applicationRules;

import objectVerifier.utilities.ObjectHelper;

public class ListApplicationRule implements IVerificationRuleApplicationRule {

	public boolean dataIsApplicable(Object actualObject, Object expectedObject) {
		if (actualObject != null) {
			return isList(actualObject);
		}
		if (expectedObject != null) {
			return isList(expectedObject);
		}
		return false;
	}

	private boolean isList(Object object) {
		return ObjectHelper.isList(object.getClass());
	}
}
