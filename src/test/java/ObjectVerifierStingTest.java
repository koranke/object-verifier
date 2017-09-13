import objectVerifier.Verify;
import objectVerifier.verificationRules.StringContainsRule;
import org.testng.annotations.Test;
import supportingClasses.ParentThing;

public class ObjectVerifierStingTest {

	@Test
	public void testExactMatch() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe");

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe");

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testExactMatchFail() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe");

		ParentThing expectedThing = new ParentThing()
				.setFirstName("John");

		Verify.that(actualThing).isEqualTo(expectedThing);
	}

	@Test
	public void testContainsMatch() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe Smith");

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe");

		Verify.that(actualThing).usingRule(new StringContainsRule()).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testContainsMatchFail() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("John Smith");

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe");

		Verify.that(actualThing).usingRule(new StringContainsRule()).isEqualTo(expectedThing);
	}

}
