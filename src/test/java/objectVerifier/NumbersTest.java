package objectVerifier;

import objectVerifier.enums.NumericComparison;
import objectVerifier.verificationRules.NumberGreaterThanRule;
import objectVerifier.verificationRules.NumberInRangeRule;
import objectVerifier.verificationRules.NumberLessThanRule;
import objectVerifier.verificationRules.NumberMatchRule;
import org.testng.annotations.Test;
import supportingClasses.ParentThing;

public class NumbersTest {

	@Test
	public void testBasicInteger() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe")
				.setAge(10);

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe")
				.setAge(10);

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testBasicIntegerFail() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe")
				.setAge(10);

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe")
				.setAge(11);

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

	@Test
	public void testGreaterThanRule() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing().setAge(1);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("age", new NumberGreaterThanRule());

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testGreaterThanRuleFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing().setAge(111);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("age", new NumberGreaterThanRule());

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test
	public void testLessThanRule() {
		ParentThing actualThing = ParentThing.getPopulatedParent().setAge(10);
		ParentThing expectedThing = new ParentThing().setAge(11);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("age", new NumberLessThanRule());

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testLessThanRuleFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent().setAge(10);
		ParentThing expectedThing = new ParentThing().setAge(10);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("age", new NumberLessThanRule());

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test
	public void testInRangeRule() {
		ParentThing actualThing = ParentThing.getPopulatedParent().setAge(15);
		ParentThing expectedThing = new ParentThing().setAge(12);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("age", new NumberInRangeRule(10));

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);

		expectedThing.setAge(24);
		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testInRangeRuleFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent().setAge(15);
		ParentThing expectedThing = new ParentThing().setAge(26);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("age", new NumberInRangeRule(10));

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test
	public void testMatchRuleUsingEquals() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("id", new NumberMatchRule(NumericComparison.equalTo));

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatchRuleUsingEqualsFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		expectedThing.setId(99887766L);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("id", new NumberMatchRule(NumericComparison.equalTo));

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

}
