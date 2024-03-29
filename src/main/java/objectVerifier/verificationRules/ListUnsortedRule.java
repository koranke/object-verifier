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

public class ListUnsortedRule extends VerificationRule {

	public ListUnsortedRule() {
		super(Lists.newArrayList(new ListApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		List<?> actual = new ArrayList<>();
		actual.addAll(ListConverter.getAsList(actualObject));

		List<?> expected = new ArrayList<>();
		expected.addAll(ListConverter.getAsList(expectedObject));

		Assert.assertEquals(actual.size(), expected.size(),
				String.format("%s%sActual list size doesn't match expect'd list size.", errorMessage, System.lineSeparator()));

		//TODO: fix edge case failure to fail
		/*
		Current check could fail to catch a failure if the two lists are the same size, but there are duplicate values
		in the actual and the expected, but the duplicate values are not the same.  It's not enough to assert
		that expected items are found.  We also need to assert that any duplicate items match in count.
		 */

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
			Assert.assertTrue(expectedItemFound, String.format("%s%sFailed to find expect'd item %s\n...in list...\n%s.",
					errorMessage, System.lineSeparator(), expectedItemToString, actualToString));
		}
	}
}
