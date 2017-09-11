package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.applicationRules.StringApplicationRule;
import org.testng.Assert;

public class StringContainsRule extends VerificationRule {

	public StringContainsRule() {
		super(Lists.newArrayList(new StringApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, String errorMessage) {
		String actual = (String) actualObject;
		String expected = (String) expectedObject;
		Assert.assertTrue(actual.contains(expected), String.format("Expected '%s' to contain '%s'.", actual, expected));
	}
}
