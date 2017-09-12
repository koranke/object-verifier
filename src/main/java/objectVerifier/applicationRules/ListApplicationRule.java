package objectVerifier.applicationRules;

import objectVerifier.utilities.ObjectHelper;

public class ListApplicationRule implements IVerificationRuleApplicationRule {

	public boolean dataIsApplicable(Object actualObject, Object expectedObject) {
		if (actualObject != null) {
			return ObjectHelper.isList(actualObject.getClass());
		}
		if (expectedObject != null) {
			return ObjectHelper.isList(expectedObject.getClass());
		}
		return false;
	}

}
