package objectVerifier.applicationRules;

import objectVerifier.utilities.ObjectHelper;

public class ListApplicationRule implements IApplicationRule {

	public boolean dataIsApplicable(Object actualObject, Object expectedObject) {
		if (actualObject != null) {
			return ObjectHelper.isList(actualObject.getClass()) ||
					ObjectHelper.isArray(actualObject.getClass()) ||
					ObjectHelper.isSet(actualObject.getClass());
		}
		if (expectedObject != null) {
			return ObjectHelper.isList(expectedObject.getClass()) ||
					ObjectHelper.isArray(expectedObject.getClass()) ||
					ObjectHelper.isSet(expectedObject.getClass());
		}
		return false;
	}

}
