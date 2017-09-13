package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.applicationRules.ListApplicationRule;
import objectVerifier.applicationRules.MapApplicationRule;
import objectVerifier.utilities.RulesHelper;
import org.testng.Assert;

import java.util.*;

public class MapExactMatchRule extends VerificationRule {

	public MapExactMatchRule() {
		super(Lists.newArrayList(new MapApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		setChildVerificationRules(RulesHelper.setRulesToDefaultValuesIfNotSet(verificationRules));

		Map<?, ?> actual = new HashMap<>();
		actual.putAll((Map)actualObject);

		Map<?, ?> expected = new HashMap<>();
		expected.putAll((Map)expectedObject);

		Assert.assertEquals(actual.size(), expected.size(),
				String.format("%s%sActual map size doesn't match expected map size.", errorMessage, System.lineSeparator()));

		Iterator iterator = expected.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			Assert.assertTrue(actual.containsKey(key), String.format("%s%sKey %s not found in actual map %s.",
					errorMessage, System.lineSeparator(), key.toString(), actual.toString()));
			Object actualMapObject = actual.get(key);
			Object expectedMapObject = expected.get(key);

			for (VerificationRule childVerificationRule : childVerificationRules) {
				childVerificationRule.verify(actualMapObject, expectedMapObject, fieldsToCheck, verificationRules,
						String.format("%s%sActual map items don't match for key %s.",
								errorMessage, System.lineSeparator(), key.toString()));
			}
		}
	}
}
