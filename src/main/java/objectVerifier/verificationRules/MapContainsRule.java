package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.ObjectVerifier;
import objectVerifier.applicationRules.MapApplicationRule;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapContainsRule extends VerificationRule {

	public MapContainsRule() {
		super(Lists.newArrayList(new MapApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		Map<?, ?> actual = new HashMap<>();
		actual.putAll((Map)actualObject);

		Map<?, ?> expected = new HashMap<>();
		expected.putAll((Map)expectedObject);

		Assert.assertTrue(actual.size() >= expected.size(),
				String.format("%s%sActual map size less than expect'd map size.", errorMessage, System.lineSeparator()));

		Iterator iterator = expected.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			Assert.assertTrue(actual.containsKey(key), String.format("%s%sKey %s not found in actual map %s.",
					errorMessage, System.lineSeparator(), key.toString(), actual.toString()));
			Object actualMapObject = actual.get(key);
			Object expectedMapObject = expected.get(key);

			ObjectVerifier.verifyObject(actualMapObject, expectedMapObject, fieldsToCheck, verificationRules, errorMessage);
		}
	}
}
