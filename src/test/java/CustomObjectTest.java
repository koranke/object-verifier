import objectVerifier.FieldsToCheck;
import objectVerifier.Verify;
import org.testng.annotations.Test;
import supportingClasses.ParentThing;

public class CustomObjectTest {

	@Test
	public void testBasicMatch() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("specialChild");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testBasicMatchFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		expectedThing.getSpecialChild().setAge(18);

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("specialChild");

		Verify.that(actualThing).usingFields(fieldsToCheck).isEqualTo(expectedThing);
	}

	@Test
	public void testAllExactMatch() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

}
