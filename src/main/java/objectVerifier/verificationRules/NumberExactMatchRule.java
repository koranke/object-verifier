package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.applicationRules.NumberApplicationRule;
import org.testng.Assert;

public class NumberExactMatchRule extends VerificationRule {

	public NumberExactMatchRule() {
		super(Lists.newArrayList(new NumberApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, String errorMessage) {
		Number actual = (Number) actualObject;
		Number expected = (Number) expectedObject;
		Assert.assertEquals(actual, expected, errorMessage);
	}
}
