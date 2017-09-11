package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.applicationRules.NumberApplicationRule;
import org.testng.Assert;

public class NumberGreaterThanRule extends VerificationRule {

	public NumberGreaterThanRule() {
		super(Lists.newArrayList(new NumberApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, String errorMessage) {
		Number actual = (Number) actualObject;
		Number expected = (Number) expectedObject;
		Assert.assertTrue(actual.longValue() > expected.longValue(), String.format("Actual number %d is not greater than %d.", actual, expected));
	}
}
