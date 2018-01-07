package objectVerifier;

import org.testng.annotations.Test;
import supportingClasses.ChildThing;
import supportingClasses.ParentThing;

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

}