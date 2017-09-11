package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.applicationRules.StringApplicationRule;
import org.testng.Assert;

public class StringExactMatchRule extends VerificationRule {

	public StringExactMatchRule() {
		super(Lists.newArrayList(new StringApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, String errorMessage) {
		String actual = (String) actualObject;
		String expected = (String) expectedObject;
		Assert.assertEquals(actual, expected, errorMessage);
	}
}
