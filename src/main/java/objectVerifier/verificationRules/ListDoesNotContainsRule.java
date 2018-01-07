package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.ObjectVerifier;
import objectVerifier.applicationRules.ListApplicationRule;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;

public class ListDoesNotContainsRule extends VerificationRule {

	public ListDoesNotContainsRule() {
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
			Assert.assertFalse(expectedItemFound, String.format("%s%sFound unexpected item %s in %s.",
					errorMessage, System.lineSeparator(), expectedItem, actual));

		}
	}
}
