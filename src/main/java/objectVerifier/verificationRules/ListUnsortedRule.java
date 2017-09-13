package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.applicationRules.ListApplicationRule;
import objectVerifier.utilities.RulesHelper;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ListUnsortedRule extends VerificationRule {

	public ListUnsortedRule() {
		super(Lists.newArrayList(new ListApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		setChildVerificationRules(RulesHelper.setRulesToDefaultValuesIfNotSet(verificationRules));

		List<?> actual = new ArrayList<>();
		actual.addAll((List)actualObject);

		List<?> expected = new ArrayList<>();
		expected.addAll((List)expectedObject);

		Assert.assertEquals(actual.size(), expected.size(),
				String.format("%s%sActual list size doesn't match expected list size.", errorMessage, System.lineSeparator()));

		for (Object expectedItem : expected) {
			boolean expectedItemFound = false;
			for (Object actualItem : actual) {
				for (VerificationRule childVerificationRule : childVerificationRules) {
					try {
						boolean ranCheck = childVerificationRule.verify(actualItem, expectedItem, fieldsToCheck, verificationRules,"");
						if (ranCheck) {
							expectedItemFound = true;
						}
					} catch (AssertionError e) {	}
				}
			}
			Assert.assertTrue(expectedItemFound, String.format("%s%sFailed to find expected item %s in %s.",
					errorMessage, System.lineSeparator(), expectedItem, actual));
		}
	}
}
