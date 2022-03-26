package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.ObjectVerifier;
import objectVerifier.applicationRules.MapApplicationRule;
import org.testng.Assert;

import java.util.*;

public class MapExactMatchRule extends VerificationRule {

	public MapExactMatchRule() {
		super(Lists.newArrayList(new MapApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		Map<?, ?> actual = new HashMap<>();
		actual.putAll((Map)actualObject);

		Map<?, ?> expected = new HashMap<>();
		expected.putAll((Map)expectedObject);

		Assert.assertEquals(actual.size(), expected.size(),
				String.format("%s%sActual map size doesn't match expect'd map size.", errorMessage, System.lineSeparator()));

		Iterator iterator = expected.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			Assert.assertTrue(actual.containsKey(key), String.format("%s%sKey %s not found in actual map %s.",
					errorMessage, System.lineSeparator(), key == null ? "null" : key.toString(), actual.toString()));
			Object actualMapObject = actual.get(key);
			Object expectedMapObject = expected.get(key);

			errorMessage += String.format("\nKey: %s", key == null ? "null" : key.toString());

			ObjectVerifier.verifyObject(actualMapObject, expectedMapObject, fieldsToCheck, verificationRules, errorMessage);
		}
	}
}
