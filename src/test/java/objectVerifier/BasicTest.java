package objectVerifier;

import objectVerifier.verificationRules.ListUnsortedRule;
import org.testng.annotations.Test;
import supportingClasses.ChildThing;
import supportingClasses.ParentThing;
import java.util.Collections;
import java.util.List;

public class BasicTest {

	@Test
	public void testAllNull() {
		ParentThing actualThing = null;
		ParentThing expectedThing = null;

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testActualNull() {
		ParentThing actualThing = null;
		ParentThing expectedThing = new ParentThing();

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testExpectedNull() {
		ParentThing actualThing = new ParentThing();
		ParentThing expectedThing = null;

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testActualMemberNull() {
		ParentThing actualThing = new ParentThing();

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe");

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testExpectedMemberNull() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe");

		ParentThing expectedThing = new ParentThing();

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testObjectsAreDifferentClasses() {
		ParentThing actualThing = new ParentThing();
		ChildThing expectedThing = new ChildThing();

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testInvalidField() {
		ParentThing actualThing = new ParentThing().setFirstName("Jack");
		ParentThing expectedThing = new ParentThing().setFirstName("Jack");

		Verify.that(actualThing).usingFields("fistNam").isEqualTo(expectedThing);
	}

	@Test
	public void testRuleOnly() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		List<String> words = expectedThing.getFavoriteWords();
		Collections.shuffle(words);
		expectedThing.setFavoriteWords(words);

		Verify.that(actualThing).usingRule(new ListUnsortedRule()).isEqualTo(expectedThing);
	}

	@Test
	public void testBoolean() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe")
				.setIsActive(true);

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe")
				.setIsActive(true);

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testBooleanFail() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe")
				.setIsActive(false);

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe")
				.setIsActive(true);

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

	@Test
	public void testTransient() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe")
				.setIgnoreMe("ZZZZZ");

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe")
				.setIgnoreMe("xxx");

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

}
