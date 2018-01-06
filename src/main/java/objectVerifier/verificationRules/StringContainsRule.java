package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.applicationRules.StringApplicationRule;
import org.testng.Assert;

import java.util.List;

public class StringContainsRule extends VerificationRule {
	private boolean ignoreCase = false;

	public StringContainsRule() {
		super(Lists.newArrayList(new StringApplicationRule()));
	}

	public StringContainsRule(boolean ignoreCase) {
		super(Lists.newArrayList(new StringApplicationRule()));
		this.ignoreCase = ignoreCase;
	}

	public void verifyObject(Object actualObject, Object expectedObject,
							 FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		String actual = (String) actualObject;
		String expected = (String) expectedObject;
		if (ignoreCase) {
			actual = actual.toLowerCase();
			expected = expected.toLowerCase();
		}
		Assert.assertTrue(actual.contains(expected), String.format("%s%sExpected '%s' to contain '%s'.",
				errorMessage, System.lineSeparator(), actual, expected));
	}
}
