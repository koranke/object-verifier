package objectVerifier.applicationRules;

public class StringApplicationRule implements IApplicationRule {

	public boolean dataIsApplicable(Object actualObject, Object expectedObject) {
		if (actualObject != null) {
			return actualObject.getClass() == String.class;
		}
		if (expectedObject != null) {
			return expectedObject.getClass() == String.class;
		}
		return false;
	}
}
