package objectVerifier.verificationRules;

import objectVerifier.applicationRules.IVerificationRuleApplicationRule;
import java.util.ArrayList;
import java.util.List;

public abstract class VerificationRule {
	protected List<IVerificationRuleApplicationRule> applicationRules;
	protected List<VerificationRule> childVerificationRules = new ArrayList<>();

	public VerificationRule(List<IVerificationRuleApplicationRule> applicationRules) {
		this.applicationRules = applicationRules;
	}

	public List<IVerificationRuleApplicationRule> getApplicationRules() {
		return applicationRules;
	}

	public VerificationRule setChildVerificationRules(List<VerificationRule> childVerificationRules) {
		this.childVerificationRules = childVerificationRules;
		return this;
	}

	public VerificationRule addChildVerificationRule(VerificationRule childVerificationRule) {
		childVerificationRules.add(childVerificationRule);
		return this;
	}

	public boolean verify(Object actualObject, Object expectedObject, String errorMessage) {
		boolean isOkToVerify = true;
		for (IVerificationRuleApplicationRule applicationRule : applicationRules) {
			if (!applicationRule.dataIsApplicable(actualObject, expectedObject)) {
				isOkToVerify = false;
				break;
			}
		}
		if (isOkToVerify) {
			verifyObject(actualObject, expectedObject, errorMessage);
			return true;
		}
		return false;
	}

	protected abstract void verifyObject(Object actualObject, Object expectedObject, String errorMessage);
}
