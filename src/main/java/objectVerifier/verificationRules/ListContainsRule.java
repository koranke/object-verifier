package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.ObjectVerifier;
import objectVerifier.applicationRules.ListApplicationRule;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;

public class ListContainsRule extends VerificationRule {

	public ListContainsRule() {
		super(Lists.newArrayList(new ListApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		List<?> actual = new ArrayList<>();
		actual.addAll((List)actualObject);

		List<?> expected = new ArrayList<>();
		expected.addAll((List)expectedObject);

		for (Object expectedItem : expected) {
			boolean expectedItemFound = false;
			for (Object actualItem : actual) {
				try {
					ObjectVerifier.verifyObject(actualItem, expectedItem, fieldsToCheck, verificationRules, errorMessage);
					expectedItemFound = true;
					break;
				} catch (AssertionError e) {}
			}
			Assert.assertTrue(expectedItemFound, String.format("%s%sFailed to find expected item %s in %s.",
					errorMessage, System.lineSeparator(), expectedItem, actual));
		}
	}
}
