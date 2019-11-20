package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import objectVerifier.FieldsToCheck;
import objectVerifier.ObjectVerifier;
import objectVerifier.applicationRules.ListApplicationRule;
import objectVerifier.utilities.ListConverter;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;

public class ListDoesNotContainsRule extends VerificationRule {

	public ListDoesNotContainsRule() {
		super(Lists.newArrayList(new ListApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		List<?> actual = new ArrayList<>();
		actual.addAll(ListConverter.getAsList(actualObject));

		List<?> expected = new ArrayList<>();
		expected.addAll(ListConverter.getAsList(expectedObject));

		for (Object expectedItem : expected) {
			boolean expectedItemFound = false;
			for (Object actualItem : actual) {
				try {
					ObjectVerifier.verifyObject(actualItem, expectedItem, fieldsToCheck, verificationRules, errorMessage);
					expectedItemFound = true;
					break;
				} catch (AssertionError e) {}
			}
			String expectedItemToString = new Gson().toJson(expectedItem);
			String actualToString = new Gson().toJson(actual);
			Assert.assertFalse(expectedItemFound, String.format("%s%sFound unexpected item %s\n...in list...\n%s.",
					errorMessage, System.lineSeparator(), expectedItemToString, actualToString));

		}
	}
}
