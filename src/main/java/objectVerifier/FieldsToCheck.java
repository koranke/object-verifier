package objectVerifier;

import objectVerifier.applicationRules.IVerificationRuleApplicationRule;
import org.testng.Assert;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldsToCheck {
	private Map<Class, ObjectFields> fieldsToCheck = new HashMap<>();
	private Class<?> currentKey;

	public FieldsToCheck withKey(Class<?> cls) {
		currentKey = cls;
		return this;
	}

	public FieldsToCheck checkAllFieldsForCurrentKey() {
		if (fieldsToCheck.containsKey(currentKey)) {
			fieldsToCheck.get(currentKey).setOnlyCheckAddedFields(false);
		} else {
			Assert.fail(String.format("Unable to set checkAllFieldsForCurrentKey.  Key %s does not exist yet.  Call this method after adding fields for key.", currentKey));
		}
		return this;
	}

	public FieldsToCheck checkOnlyAddedFieldsForCurrentKey() {
		if (fieldsToCheck.containsKey(currentKey)) {
			fieldsToCheck.get(currentKey).setOnlyCheckAddedFields(true);
		} else {
			Assert.fail(String.format("Unable to set checkOnlyAddedFieldsForCurrentKey.  Key %s does not exist yet.  Call this method after adding fields for key.", currentKey));
		}
		return this;
	}

	public FieldsToCheck addField(String field) {
		ObjectFields fieldList = fieldsToCheck.get(currentKey);
		if (fieldList == null) {
			fieldList = new ObjectFields();
			fieldsToCheck.put(currentKey, fieldList);
		}
		fieldList.addField(field);
		return this;
	}

	public FieldsToCheck addField(String field, List<IVerificationRuleApplicationRule> applicationRules) {
		ObjectFields fieldList = fieldsToCheck.get(currentKey);
		if (fieldList == null) {
			fieldList = new ObjectFields();
			fieldsToCheck.put(currentKey, fieldList);
		}
		fieldList.addField(field, applicationRules);
		return this;
	}

	public FieldsToCheck excludeField(String field) {
		ObjectFields fieldList = fieldsToCheck.get(currentKey);
		if (fieldList == null) {
			fieldList = new ObjectFields();
			fieldsToCheck.put(currentKey, fieldList);
		}
		fieldList.excludeField(field);
		this.checkAllFieldsForCurrentKey();
		return this;
	}

	public List<String> getFieldListForClass(Class<?> cls) {
		if (fieldsToCheck.containsKey(cls)) {
			return fieldsToCheck.get(cls).getFieldNames();
		} else {
			return null;
		}
	}

	public boolean fieldListForClassIsRestricted(Class<?> cls) {
		if (fieldsToCheck.containsKey(cls)) {
			return fieldsToCheck.get(cls).isOnlyCheckAddedFields();
		}
		return false;
	}

	public boolean fieldIsExcluded(Class<?> cls, String fieldName) {
		ObjectField field = getField(cls, fieldName);
		if (field == null) return false;
		return field.isExcluded();
	}

	public boolean fieldHasValidatorOverrides(Class cls, String fieldName) {
		ObjectField field = getField(cls, fieldName);
		if (field == null) return false;
		return field.getApplicationRules() != null;
	}

	public List<IVerificationRuleApplicationRule> getFieldValidatorParameters(Class cls, String fieldName) {
		ObjectField field = getField(cls, fieldName);
		if( field == null) return null;
		return field.getApplicationRules();
	}

	private ObjectField getField(Class cls, String fieldName) {
		if (fieldsToCheck.containsKey(cls)) {
			ObjectFields fields = fieldsToCheck.get(cls);
			for (ObjectField ObjectField : fields.getFields()) {
				if (ObjectField.getFieldName().equals(fieldName)) {
					return ObjectField;
				}
			}
		}
		return null;
	}
}
