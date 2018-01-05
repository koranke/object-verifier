package objectVerifier;

import objectVerifier.verificationRules.VerificationRule;

import java.util.ArrayList;
import java.util.List;

public class ObjectFields {
	private boolean onlyCheckIncludedFields = true;
	private List<ObjectField> fields = new ArrayList<>();

	public boolean isCheckIncludedFieldsOnly() {
		return onlyCheckIncludedFields;
	}

	public ObjectFields setCheckIncludedFieldsOnly(boolean onlyCheckIncludedFields) {
		this.onlyCheckIncludedFields = onlyCheckIncludedFields;
		return this;
	}

	public List<String> getFieldNames()
	{
		List<String> fieldNames = new ArrayList<>();
		for (ObjectField field : fields) {
			fieldNames.add(field.getFieldName());
		}
		return fieldNames;
	}

	public List<ObjectField> getFields() {
		return fields;
	}

	public ObjectFields setFields(List<ObjectField> fields) {
		this.fields = fields;
		return this;
	}

	public ObjectFields addField(String field) {
		fields.add(new ObjectField().setFieldName(field));
		return this;
	}

	public ObjectFields addField(String field, List<VerificationRule> verificationRules) {
		fields.add(new ObjectField().setFieldName(field).setVerificationRules(verificationRules));
		return this;
	}

	public ObjectFields addField(ObjectField field) {
		fields.add(field);
		return this;
	}

	public ObjectFields excludeField(String field) {
		fields.add(new ObjectField().setFieldName(field).setExcluded(true));
		return this;
	}
}
