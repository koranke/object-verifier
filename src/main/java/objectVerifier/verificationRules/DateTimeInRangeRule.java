package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.applicationRules.DateApplicationRule;
import objectVerifier.applicationRules.NumberApplicationRule;
import objectVerifier.utilities.DateTimeHelper;
import org.testng.Assert;

import java.time.temporal.TemporalUnit;

public class DateTimeInRangeRule extends VerificationRule {
	private long range = 0;
	private TemporalUnit timeUnit;

	public DateTimeInRangeRule(long range, TemporalUnit timeUnit) {
		super(Lists.newArrayList(new DateApplicationRule()));
		this.range = range;
		this.timeUnit = timeUnit;
	}

	public void verifyObject(Object actualObject, Object expectedObject, String errorMessage) {
		Assert.assertTrue(DateTimeHelper.isWithinTimeRange(
				actualObject.getClass().getCanonicalName(), actualObject, range, timeUnit, expectedObject),
				String.format("%s%SActual date %s not in range of %d %s of expected date %s.",
						errorMessage, System.lineSeparator(), actualObject, range, timeUnit, expectedObject));
	}
}
