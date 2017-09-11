package objectVerifier;

import objectVerifier.applicationRules.IVerificationRuleApplicationRule;
import java.util.ArrayList;
import java.util.List;

public class ObjectFields {
	private boolean onlyCheckAddedFields = true;
	private List<ObjectField> fields = new ArrayList<>();

	public boolean isOnlyCheckAddedFields() {
		return onlyCheckAddedFields;
	}

	public ObjectFields setOnlyCheckAddedFields(boolean onlyCheckAddedFields) {
		this.onlyCheckAddedFields = onlyCheckAddedFields;
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

	public ObjectFields addField(String field, List<IVerificationRuleApplicationRule> applicationRules) {
		fields.add(new ObjectField().setFieldName(field).setApplicationRules(applicationRules));
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
