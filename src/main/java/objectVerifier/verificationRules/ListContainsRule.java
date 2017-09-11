package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.applicationRules.ListApplicationRule;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ListContainsRule extends VerificationRule {

	public ListContainsRule() {
		super(Lists.newArrayList(new ListApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, String errorMessage) {
		List<?> actual = new ArrayList<>();
		actual.addAll((List)actualObject);

		List<?> expected = new ArrayList<>();
		expected.addAll((List)expectedObject);

		for (Object expectedItem : expected) {
			boolean expectedItemFound = false;
			for (Object actualItem : actual) {
				for (VerificationRule childVerificationRule : childVerificationRules) {
					try {
						childVerificationRule.verify(actualItem, expectedItem, "");
						expectedItemFound = true;
					} catch (AssertionError e) {	}
				}
			}
			Assert.assertTrue(expectedItemFound, String.format("Failed to find expected item %s in %s.", expectedItem, actual));
		}
	}
}
