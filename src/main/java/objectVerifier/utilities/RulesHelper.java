package objectVerifier.utilities;

import com.google.common.collect.Lists;
import objectVerifier.applicationRules.IVerificationRuleApplicationRule;
import objectVerifier.verificationRules.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RulesHelper {

	public static List<VerificationRule> setRulesToDefaultValuesIfNotSet(List<VerificationRule> existingRules) {
		List<VerificationRule> defaultRules = getDefaultRules();

		if (existingRules == null || existingRules.size() == 0) {
			return Lists.newArrayList(defaultRules);
		} else {
			return getMergedRules(existingRules, defaultRules);
		}
	}

	public static List<VerificationRule> getMergedRules(List<VerificationRule> priorityRules, List<VerificationRule> gapFillerRules) {
		for (VerificationRule fillerRule : gapFillerRules) {
			boolean matchingRuleFound = false;
			for (VerificationRule priorityRule : priorityRules) {
				if (matchingApplicationRuleFound(fillerRule.getApplicationRules(), priorityRule.getApplicationRules())) {
					matchingRuleFound = true;
				}
			}
			if (!matchingRuleFound) {
				priorityRules.add(fillerRule);
			}
		}
		return Lists.newArrayList(priorityRules);
	}

	public static List<VerificationRule> getDefaultRules() {
		List<VerificationRule> defaultRules = new ArrayList<>();
		defaultRules.add(new DateTimeInRangeRule(5, ChronoUnit.MINUTES ));
		defaultRules.add(new ListExactMatchRule());
		defaultRules.add(new MapExactMatchRule());
		defaultRules.add(new CustomObjectMatchRule());
		return defaultRules;
	}

	public static boolean verificationRuleExistsForObjectDataType(Object actualObject, Object expectedObject, List<VerificationRule> rules) {
		for (VerificationRule rule : rules) {
			for (IVerificationRuleApplicationRule applicationRule : rule.getApplicationRules()) {
				if (applicationRule.dataIsApplicable(actualObject, expectedObject)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean matchingApplicationRuleFound(
			List<IVerificationRuleApplicationRule> ruleList1,
			List<IVerificationRuleApplicationRule> ruleList2) {

		for (IVerificationRuleApplicationRule ruleFromList1 : ruleList1) {
			for (IVerificationRuleApplicationRule ruleFromList2 : ruleList2) {
				if (ruleFromList1.getClass() == ruleFromList2.getClass()) {
					return true;
				}
			}
		}
		return false;
	}

}
