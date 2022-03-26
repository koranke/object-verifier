package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.applicationRules.DateApplicationRule;
import objectVerifier.utilities.DateTimeHelper;
import org.testng.Assert;
import java.util.List;

public class DateTimeComparisonRule extends VerificationRule {
	public enum ComparisonType { before, after }

	private ComparisonType comparisonType;

	public DateTimeComparisonRule(ComparisonType comparisonType) {
		super(Lists.newArrayList(new DateApplicationRule()));
		this.comparisonType = comparisonType;
	}

	public void verifyObject(Object actualObject, Object expectedObject,
							 FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {

		if (comparisonType == ComparisonType.before) {
			Assert.assertTrue(DateTimeHelper.isBefore(
							actualObject.getClass().getCanonicalName(), actualObject, expectedObject),
					String.format("%s%sActual date %s is not before expect'd date %s.",
							errorMessage, System.lineSeparator(), actualObject, expectedObject));
		} else {
			Assert.assertTrue(DateTimeHelper.isAfter(
							actualObject.getClass().getCanonicalName(), actualObject, expectedObject),
					String.format("%s%sActual date %s is not after expect'd date %s.",
							errorMessage, System.lineSeparator(), actualObject, expectedObject));

		}
	}
}
