package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.applicationRules.DateApplicationRule;
import objectVerifier.utilities.DateTimeHelper;
import org.testng.Assert;

import java.time.temporal.TemporalUnit;
import java.util.List;

public class DateTimeInRangeRule extends VerificationRule {
	private final long range;
	private final TemporalUnit timeUnit;

	public DateTimeInRangeRule(long range, TemporalUnit timeUnit) {
		super(Lists.newArrayList(new DateApplicationRule()));
		this.range = range;
		this.timeUnit = timeUnit;
	}

	public void verifyObject(Object actualObject, Object expectedObject,
							 FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		Assert.assertTrue(DateTimeHelper.isWithinTimeRange(
						actualObject.getClass().getCanonicalName(), actualObject, range, timeUnit, expectedObject),
				String.format("%s%sActual date %s not in range of %d %s of expect'd date %s.",
						errorMessage, System.lineSeparator(), actualObject, range, timeUnit, expectedObject));
	}
}
