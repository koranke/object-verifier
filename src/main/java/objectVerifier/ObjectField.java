package objectVerifier;

import objectVerifier.verificationRules.VerificationRule;
import java.util.List;

public class ObjectField {
	private String fieldName;
	private List<VerificationRule> verificationRules;
	private boolean excluded = false;

	public String getFieldName() {
		return fieldName;
	}

	public ObjectField setFieldName(String fieldName) {
		this.fieldName = fieldName;
		return this;
	}

	public List<VerificationRule> getVerificationRules() {
		return verificationRules;
	}

	public ObjectField setVerificationRules(List<VerificationRule> verificationRules) {
		this.verificationRules = verificationRules;
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
