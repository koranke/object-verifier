package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.ObjectVerifier;
import objectVerifier.applicationRules.ListApplicationRule;
import objectVerifier.utilities.ListConverter;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;

public class ListExactMatchRule extends VerificationRule {

	public ListExactMatchRule() {
		super(Lists.newArrayList(new ListApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		List<?> actual = new ArrayList<>();
		actual.addAll(ListConverter.getAsList(actualObject));

		List<?> expected = new ArrayList<>();
		expected.addAll(ListConverter.getAsList(expectedObject));

		Assert.assertEquals(actual.size(), expected.size(),
				String.format("%s%sActual list size doesn't match expected list size.", errorMessage, System.lineSeparator()));

		for (int i = 0; i < actual.size(); i++) {
			ObjectVerifier.verifyObject(actual.get(i), expected.get(i), fieldsToCheck, verificationRules, errorMessage);
		}

	}
}
