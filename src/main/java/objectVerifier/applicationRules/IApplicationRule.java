package objectVerifier.applicationRules;

public interface IApplicationRule {
	public boolean dataIsApplicable(Object actualObject, Object expectedObject);
}
