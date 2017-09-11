package objectVerifier.applicationRules;

import objectVerifier.utilities.ObjectHelper;
import java.util.List;

public class CustomDomainObjectApplicationRule implements IVerificationRuleApplicationRule {
	private List<String> customDomainPackages;

	public CustomDomainObjectApplicationRule(List<String> packageNames) {
		customDomainPackages = packageNames;
	}

	public boolean dataIsApplicable(Object actualObject, Object expectedObject) {
		if (actualObject != null) {
			return isCustomType(actualObject.getClass());
		}
		if (expectedObject != null) {
			return isCustomType(expectedObject.getClass());
		}
		return false;
	}

	private boolean isCustomType(Class<?> cls) {
		return cls.getPackage() != null &&
				packageMatchesCustomDomainPackage(cls)
				&& !ObjectHelper.implementsEquals(cls);
	}

	private boolean packageMatchesCustomDomainPackage(Class<?> cls) {
		for (String classPackage : customDomainPackages) {
			if (("package " + cls.getPackage()).toString().startsWith(classPackage)) {
				return true;
			}
		}
		return false;
	}

}
