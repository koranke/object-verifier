package objectVerifier.verificationRules;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.applicationRules.DateApplicationRule;
import objectVerifier.applicationRules.ListApplicationRule;
import objectVerifier.applicationRules.MapApplicationRule;
import objectVerifier.applicationRules.NumberApplicationRule;
import objectVerifier.applicationRules.StringApplicationRule;
import org.testng.Assert;

import java.util.List;

public class IsNotNullRule extends VerificationRule {

	public IsNotNullRule() {
		super(Lists.newArrayList(new StringApplicationRule(), new NumberApplicationRule(), new DateApplicationRule(), new ListApplicationRule(), new MapApplicationRule()));
	}

	public void verifyObject(Object actualObject, Object expectedObject, FieldsToCheck fieldsToCheck, List<VerificationRule> verificationRules, String errorMessage) {
		String actual = (String) actualObject;
		Assert.assertNotNull(actual, errorMessage);
	}
}
