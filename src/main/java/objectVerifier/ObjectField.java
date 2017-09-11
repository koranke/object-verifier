package objectVerifier;

import objectVerifier.applicationRules.IVerificationRuleApplicationRule;

import java.util.List;

public class ObjectField {
	private String fieldName;
	private List<IVerificationRuleApplicationRule> applicationRules;
	private boolean excluded = false;

	public String getFieldName() {
		return fieldName;
	}

	public ObjectField setFieldName(String fieldName) {
		this.fieldName = fieldName;
		return this;
	}

	public List<IVerificationRuleApplicationRule> getApplicationRules() {
		return applicationRules;
	}

	public ObjectField setApplicationRules(List<IVerificationRuleApplicationRule> applicationRules) {
		this.applicationRules = applicationRules;
		return this;
	}

	public boolean isExcluded() {
		return excluded;
	}

	public ObjectField setExcluded(boolean excluded) {
		this.excluded = excluded;
		return this;
	}
}
