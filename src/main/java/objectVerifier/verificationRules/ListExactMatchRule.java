package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.applicationRules.ListApplicationRule;
import objectVerifier.utilities.RulesHelper;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ListExactMatchRule extends VerificationRule {

	public ListExactMatchRule() {
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

		for (int i = 0; i < actual.size(); i++) {
			for (VerificationRule childVerificationRule : childVerificationRules) {
				childVerificationRule.verify(actual.get(i), expected.get(i), fieldsToCheck, verificationRules,
						String.format("%s%sActual list items don't match at index %d.",
								errorMessage, System.lineSeparator(), i));
			}
		}

	}
}
