package objectVerifier;

import com.google.common.collect.Lists;
import objectVerifier.FieldsToCheck;
import objectVerifier.Verify;
import objectVerifier.verificationRules.ListContainsRule;
import objectVerifier.verificationRules.ListDoesNotContainsRule;
import objectVerifier.verificationRules.StringExactMatchRule;
import org.testng.Assert;
import org.testng.annotations.Test;
import supportingClasses.ParentThing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListTest {

	@Test
	public void testListExactMatch() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(actualThing.getFavoriteWords());

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("favoriteWords");

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
				.includeField("favoriteWords");

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
				.includeField("favoriteWords");

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
				.includeField("favoriteWords");

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
				.includeField("favoriteWords");

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
				.includeField("favoriteWords");

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
				.includeField("favoriteWords");

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
				.includeField("favoriteWords");

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
				.includeField("favoriteWords");

		Verify.that(actualThing)
				.usingFields(fieldsToCheck)
				.usingRule(new ListDoesNotContainsRule())
				.isEqualTo(expectedThing);
	}

	@Test
	public void testListExactMatchStringCaseInsensitiveForListOnly() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(actualThing.getFavoriteWords().stream().map(item->item.toUpperCase()).collect(Collectors.toList()));

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("favoriteWords", new StringExactMatchRule(true));

		Verify.that(actualThing)
				.usingFields(fieldsToCheck)
				.isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testFieldSpecificRuleForListDoesNotOverrideGlobalRuleForOtherItems() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(actualThing.getFavoriteWords().stream().map(item->item.toUpperCase()).collect(Collectors.toList()))
				.setFirstName(actualThing.getFirstName().toUpperCase());

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.includeField("firstName")
				.includeField("favoriteWords", new StringExactMatchRule(true));

		Verify.that(actualThing)
				.usingFields(fieldsToCheck)
				.isEqualTo(expectedThing);
	}

	@Test
	public void testWithArray() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();

		Verify.that(actualThing).usingFields("favoriteColors").isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testWithArrayFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		expectedThing.getFavoriteColors()[0] = "pinkish blue";

		Verify.that(actualThing).usingFields("favoriteColors").isEqualTo(expectedThing);
	}

	@Test
	public void testWithSet() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();

		Verify.that(actualThing).usingFields("aliases").isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testWithSetFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		expectedThing.getAliases().add("The Dude");

		Verify.that(actualThing).usingFields("aliases").isEqualTo(expectedThing);
	}

	@Test
	public void testListOfObjects() {
		List<ParentThing> actualParentThings = new ArrayList<>();
		actualParentThings.add(ParentThing.getPopulatedParent().setFirstName("Jill"));
		actualParentThings.add(ParentThing.getPopulatedParent().setFirstName("John"));

		List<ParentThing> expectedParentThings = new ArrayList<>();
		expectedParentThings.add(ParentThing.getPopulatedParent().setFirstName("Jill"));
		expectedParentThings.add(ParentThing.getPopulatedParent().setFirstName("John"));

		Verify.that(actualParentThings).isEqualTo(expectedParentThings);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testListOfObjectsFail() {
		List<ParentThing> actualParentThings = new ArrayList<>();
		actualParentThings.add(ParentThing.getPopulatedParent().setFirstName("Jill"));
		actualParentThings.add(ParentThing.getPopulatedParent().setFirstName("John"));

		List<ParentThing> expectedParentThings = new ArrayList<>();
		expectedParentThings.add(ParentThing.getPopulatedParent().setFirstName("Jill"));
		expectedParentThings.add(ParentThing.getPopulatedParent().setFirstName("Jackson"));

		Verify.that(actualParentThings).isEqualTo(expectedParentThings);
	}

	@Test
	public void testArrayOfObjects() {
		ParentThing[] actualParentThings = new ParentThing[2];
		actualParentThings[0] = ParentThing.getPopulatedParent().setFirstName("Jill");
		actualParentThings[1] = ParentThing.getPopulatedParent().setFirstName("John");

		ParentThing[] expectedParentThings = new ParentThing[2];
		expectedParentThings[0] = ParentThing.getPopulatedParent().setFirstName("Jill");
		expectedParentThings[1] = ParentThing.getPopulatedParent().setFirstName("John");

		Verify.that(actualParentThings).isEqualTo(expectedParentThings);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testArrayOfObjectsFail() {
		ParentThing[] actualParentThings = new ParentThing[2];
		actualParentThings[0] = ParentThing.getPopulatedParent().setFirstName("Jill");
		actualParentThings[1] = ParentThing.getPopulatedParent().setFirstName("John");

		ParentThing[] expectedParentThings = new ParentThing[2];
		expectedParentThings[0] = ParentThing.getPopulatedParent().setFirstName("Jill");
		expectedParentThings[1] = ParentThing.getPopulatedParent().setFirstName("John").setAge(100);

		Verify.that(actualParentThings).isEqualTo(expectedParentThings);
	}
}
