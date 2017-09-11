package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.applicationRules.NumberApplicationRule;
import org.testng.Assert;

public class NumberInRangeRule extends VerificationRule {
	private long range = 0;

	public NumberInRangeRule(long range) {
		super(Lists.newArrayList(new NumberApplicationRule()));
		this.range = range;
	}

	public void verifyObject(Object actualObject, Object expectedObject, String errorMessage) {
		Number actual = (Number) actualObject;
		Number expected = (Number) expectedObject;
		Assert.assertTrue(actual.longValue() > expected.longValue() - range && actual.longValue() < expected.longValue() + range,
				String.format("Actual number %d not in range of %d plus or minus %d.", actual, expected, range));
	}
}
