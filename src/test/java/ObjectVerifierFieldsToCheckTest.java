import objectVerifier.FieldsToCheck;
import objectVerifier.Verify;
import objectVerifier.verificationRules.StringContainsRule;
import org.testng.annotations.Test;
import supportingClasses.ChildThing;
import supportingClasses.ParentThing;

public class ObjectVerifierFieldsToCheckTest {

	@Test
	public void testExcludeField() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		expectedThing.setAge(30);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.excludeField("age");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testExcludeFieldFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		expectedThing.setAge(30).setFirstName("Heather");

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.excludeField("firstName");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test
	public void testExcludeChildField() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		expectedThing.getSpecialChild().setAge(310);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ChildThing.class)
				.excludeField("age");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testExcludeChildFieldFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		expectedThing.getSpecialChild().setAge(310).setCallSign("catz");

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ChildThing.class)
				.excludeField("age");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test
	public void testIncludeField() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing().setAge(40);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("age");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testIncludeFieldFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing().setAge(40);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ChildThing.class)
				.includeField("age");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test
	public void testIncludeMultipleFields() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing().setAge(40).setFirstName("Joe");

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("age")
				.includeField("firstName");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test
	public void testIncludeMultipleKeys() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setAge(40);
		expectedThing.setSpecialChild(new ChildThing().setCallSign("TrueBlue"));


		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("age")
				.includeField("specialChild")
				.withKey(ChildThing.class)
				.includeField("callSign");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testIncludeMultipleKeysFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setAge(40);
		expectedThing.setSpecialChild(new ChildThing().setCallSign("Turkey"));

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("age")
				.includeField("specialChild")
				.withKey(ChildThing.class)
				.includeField("callSign");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test
	public void testFieldSpecificVerificationRule() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setAge(40);
		expectedThing.setSpecialChild(
				new ChildThing()
						.setCallSign("Blue")
						.setQuote("I invented the Internet.")
		);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("age")
				.includeField("specialChild")
				.withKey(ChildThing.class)
				.includeField("callSign", new StringContainsRule())
				.includeField("quote");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testFieldSpecificVerificationRuleFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setAge(40);
		expectedThing.setSpecialChild(
				new ChildThing()
						.setCallSign("Flue")
						.setQuote("I invented the Internet.")
		);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("age")
				.includeField("specialChild")
				.withKey(ChildThing.class)
				.includeField("callSign", new StringContainsRule())
				.includeField("quote");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test
	public void testFieldToCheckShortcut() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing().setAge(40);

		Verify.that(actualThing).usingFields("age").isEqualTo(expectedThing);
	}

	@Test
	public void testFieldToCheckShortcuts() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setAge(40)
				.setFirstName("Joe");

		Verify.that(actualThing).usingFields("age", "firstName").isEqualTo(expectedThing);
	}

}
