package objectVerifier.applicationRules;

public class NumberApplicationRule implements IApplicationRule {

	public boolean dataIsApplicable(Object actualObject, Object expectedObject) {
		if (actualObject != null) {
			return isNumber(actualObject);
		}
		if (expectedObject != null) {
			return isNumber(expectedObject);
		}
		return false;
	}

	private boolean isNumber(Object object) {
		Class cls = object.getClass();
		return cls == byte.class || cls == int.class
				|| cls == long.class || cls == short.class
				|| cls == Byte.class || cls == Integer.class
				|| cls == Long.class || cls == Short.class;
	}
}
