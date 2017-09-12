package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.applicationRules.NumberApplicationRule;
import org.testng.Assert;

import java.util.List;

public class NumberExactMatchRule extends VerificationRule {

	public NumberExactMatchRule() {
		super(Lists.newArrayList(new NumberApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		Number actual = (Number) actualObject;
		Number expected = (Number) expectedObject;
		Assert.assertEquals(actual, expected, errorMessage);
	}
}
