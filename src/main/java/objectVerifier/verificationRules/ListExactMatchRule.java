package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.applicationRules.ListApplicationRule;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ListExactMatchRule extends VerificationRule {

	public ListExactMatchRule() {
		super(Lists.newArrayList(new ListApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, String errorMessage) {
		List<?> actual = new ArrayList<>();
		actual.addAll((List)actualObject);

		List<?> expected = new ArrayList<>();
		expected.addAll((List)expectedObject);

		Assert.assertEquals(actual.size(), expected.size(),
				String.format("%s%sActual list size doesn't match expected list size.", errorMessage, System.lineSeparator()));

		for (int i = 0; i < actual.size(); i++) {
			for (VerificationRule childVerificationRule : childVerificationRules) {
				childVerificationRule.verify(actual.get(i), expected.get(i), errorMessage);
			}
		}

	}
}
