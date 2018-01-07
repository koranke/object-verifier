package objectVerifier.applicationRules;

import objectVerifier.utilities.ObjectHelper;

public class MapApplicationRule implements IApplicationRule {

	public boolean dataIsApplicable(Object actualObject, Object expectedObject) {
		if (actualObject != null) {
			return ObjectHelper.isMap(actualObject.getClass());
		}
		if (expectedObject != null) {
			return ObjectHelper.isMap(expectedObject.getClass());
		}
		return false;
	}

}
