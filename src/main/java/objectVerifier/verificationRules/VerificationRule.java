package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.applicationRules.IVerificationRuleApplicationRule;
import java.util.List;

public abstract class VerificationRule {
	protected List<IVerificationRuleApplicationRule> applicationRules;

	public VerificationRule(List<IVerificationRuleApplicationRule> applicationRules) {
		this.applicationRules = applicationRules;
	}

	public VerificationRule(IVerificationRuleApplicationRule applicationRule) {
		this.applicationRules = Lists.newArrayList(applicationRule);
	}

	public List<IVerificationRuleApplicationRule> getApplicationRules() {
		return applicationRules;
	}

	public boolean verify(Object actualObject, Object expectedObject,
						  FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		boolean isOkToVerify = true;
		for (IVerificationRuleApplicationRule applicationRule : applicationRules) {
			if (!applicationRule.dataIsApplicable(actualObject, expectedObject)) {
				isOkToVerify = false;
				break;
			}
		}
		if (isOkToVerify) {
			verifyObject(actualObject, expectedObject, fieldsToCheck, verificationRules, errorMessage);
			return true;
		}
		return false;
	}

	protected abstract void verifyObject(Object actualObject, Object expectedObject,
										 FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage);
}
