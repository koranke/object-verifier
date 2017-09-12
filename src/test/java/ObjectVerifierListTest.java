import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.Verify;
import objectVerifier.verificationRules.ListContainsRule;
import objectVerifier.verificationRules.ListDoesNotContainsRule;
import org.testng.annotations.Test;
import supportingClasses.ParentThing;

public class ObjectVerifierListTest {

	@Test
	public void testListExactMatch() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(actualThing.getFavoriteWords());

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.addField("favoriteWords");

		Verify.that(actualThing)
				.usingFields(fieldsToCheck)
				.isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testListExactMatchFailOnCount() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(Lists.newArrayList("zippy"));

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.addField("favoriteWords");

		Verify.that(actualThing)
				.usingFields(fieldsToCheck)
				.isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testListExactMatchFailOnItems() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(Lists.newArrayList("cellar", "shoe"));

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.addField("favoriteWords");

		Verify.that(actualThing)
				.usingFields(fieldsToCheck)
				.isEqualTo(expectedThing);
	}

	@Test
	public void testListContainsMatchAllMatch() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(actualThing.getFavoriteWords());

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.addField("favoriteWords");

		Verify.that(actualThing)
				.usingFields(fieldsToCheck)
				.usingRule(new ListContainsRule())
				.isEqualTo(expectedThing);
	}

	@Test
	public void testListContainsMatchPartialMatch() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(Lists.newArrayList("cellar"));

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.addField("favoriteWords");

		Verify.that(actualThing)
				.usingFields(fieldsToCheck)
				.usingRule(new ListContainsRule())
				.isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testListContainsMatchFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(Lists.newArrayList("banana"));

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.addField("favoriteWords");

		Verify.that(actualThing)
				.usingFields(fieldsToCheck)
				.usingRule(new ListContainsRule())
				.isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testListContainsMatchPartialFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(Lists.newArrayList("cellar", "banana"));

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.addField("favoriteWords");

		Verify.that(actualThing)
				.usingFields(fieldsToCheck)
				.usingRule(new ListContainsRule())
				.isEqualTo(expectedThing);
	}

	@Test
	public void testListDoesNotContain() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(Lists.newArrayList("cheddar"));

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.addField("favoriteWords");

		Verify.that(actualThing)
				.usingFields(fieldsToCheck)
				.usingRule(new ListDoesNotContainsRule())
				.isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testListDoesNotContainFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(Lists.newArrayList("cheddar", "cellar"));

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.addField("favoriteWords");

		Verify.that(actualThing)
				.usingFields(fieldsToCheck)
				.usingRule(new ListDoesNotContainsRule())
				.isEqualTo(expectedThing);
	}

}
