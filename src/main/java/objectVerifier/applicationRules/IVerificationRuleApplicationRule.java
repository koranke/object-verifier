package objectVerifier.applicationRules;

public interface IVerificationRuleApplicationRule {
	public boolean dataIsApplicable(Object actualObject, Object expectedObject);
}
