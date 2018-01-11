package objectVerifier;

import objectVerifier.enums.CaseComparison;
import objectVerifier.verificationRules.StringContainsRule;
import objectVerifier.verificationRules.StringExactMatchRule;
import org.testng.annotations.Test;
import supportingClasses.ParentThing;

public class StringTest {

	@Test
	public void testLiteralString() {
		Verify.that("this").isEqualTo("this");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testLiteralStringFail() {
		Verify.that("this").isEqualTo("that");
	}


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
	public void testExactMatchCaseInsensitive() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe");

		ParentThing expectedThing = new ParentThing()
				.setFirstName("joe");

		Verify.that(actualThing).usingRule(new StringExactMatchRule(CaseComparison.caseInsensitive)).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testExactMatchCaseInsensitiveFail() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe");

		ParentThing expectedThing = new ParentThing()
				.setFirstName("john");

		Verify.that(actualThing).usingRule(new StringExactMatchRule(CaseComparison.caseInsensitive)).isEqualTo(expectedThing);
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
