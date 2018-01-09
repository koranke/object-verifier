package objectVerifier.applicationRules;

import objectVerifier.utilities.ObjectHelper;

public class ListApplicationRule implements IApplicationRule {

	public boolean dataIsApplicable(Object actualObject, Object expectedObject) {
		if (actualObject != null) {
			return ObjectHelper.isCollection(actualObject.getClass());
		}
		if (expectedObject != null) {
			return ObjectHelper.isCollection(expectedObject.getClass());
		}
		return false;
	}

}
