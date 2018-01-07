package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.applicationRules.NumberApplicationRule;
import objectVerifier.enums.NumericComparison;
import org.testng.Assert;

import java.util.List;

public class NumberMatchRule extends VerificationRule {
	private NumericComparison numericComparison;

	public NumberMatchRule(NumericComparison numericComparison) {
		super(Lists.newArrayList(new NumberApplicationRule()));
		this.numericComparison = numericComparison;
	}

	public void verifyObject(Object actualObject, Object expectedObject, FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		Number actual = (Number) actualObject;
		Number expected = (Number) expectedObject;
		switch (numericComparison) {
			case equalTo:
				Assert.assertEquals(actual, expected, errorMessage);
				break;
			case lessThan:
				Assert.assertTrue(actual.longValue() < expected.longValue(), errorMessage);
				break;
			case lessThanOrEqualTo:
				Assert.assertTrue(actual.longValue() <= expected.longValue(), errorMessage);
				break;
			case greaterThan:
				Assert.assertTrue(actual.longValue() > expected.longValue(), errorMessage);
				break;
			case greaterThanOrEqualTo:
				Assert.assertTrue(actual.longValue() >= expected.longValue(), errorMessage);
				break;
		}
	}
}
