package objectVerifier.applicationRules;

import objectVerifier.utilities.ObjectHelper;

public class DateApplicationRule implements IVerificationRuleApplicationRule {

	public boolean dataIsApplicable(Object actualObject, Object expectedObject) {
		if (actualObject != null) {
			return ObjectHelper.isDateType(actualObject.getClass());
		}
		if (expectedObject != null) {
			return ObjectHelper.isDateType(expectedObject.getClass());
		}
		return false;
	}
}
