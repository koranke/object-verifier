import objectVerifier.ObjectVerifier;
import objectVerifier.Verify;
import org.testng.Assert;
import org.testng.annotations.Test;
import supportingClasses.ChildThing;
import supportingClasses.ParentThing;

public class BasicTest {


	@Test
	public void testLiteralString() {
		Verify.that("this").isEqualTo("this");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testLiteralStringFail() {
		Verify.that("this").isEqualTo("that");
	}

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

}
