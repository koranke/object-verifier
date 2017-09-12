package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.ObjectVerifier;
import objectVerifier.applicationRules.CustomObjectApplicationRule;
import objectVerifier.utilities.RulesHelper;

import java.util.List;

public class CustomObjectMatchRule extends VerificationRule {

	public CustomObjectMatchRule() {
		super(Lists.newArrayList(new CustomObjectApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject,
							 FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {

		setChildVerificationRules(RulesHelper.setRulesToDefaultValuesIfNotSet(verificationRules));

		ObjectVerifier.verifyDomainObject(
				actualObject.getClass(),
				actualObject,
				expectedObject,
				fieldsToCheck,
				verificationRules,
				errorMessage
		);
	}
}
