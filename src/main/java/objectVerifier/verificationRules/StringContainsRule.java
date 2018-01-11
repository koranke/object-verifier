package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.applicationRules.StringApplicationRule;
import objectVerifier.enums.CaseComparison;
import org.testng.Assert;

import java.util.List;

public class StringContainsRule extends VerificationRule {
	private CaseComparison caseComparison = CaseComparison.caseSensitive;

	public StringContainsRule() {
		super(Lists.newArrayList(new StringApplicationRule()));
	}

	public StringContainsRule(CaseComparison caseComparison) {
		super(Lists.newArrayList(new StringApplicationRule()));
		this.caseComparison = caseComparison;
	}

	public void verifyObject(Object actualObject, Object expectedObject,
							 FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		String actual = (String) actualObject;
		String expected = (String) expectedObject;
		if (caseComparison == CaseComparison.caseInsensitive) {
			actual = actual.toLowerCase();
			expected = expected.toLowerCase();
		}
		Assert.assertTrue(actual.contains(expected), String.format("%s%sExpected '%s' to contain '%s'.",
				errorMessage, System.lineSeparator(), actual, expected));
	}
}
