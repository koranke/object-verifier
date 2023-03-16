package objectVerifier.applicationRules;

public interface IApplicationRule {
	boolean dataIsApplicable(Object actualObject, Object expectedObject);
}
