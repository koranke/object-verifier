import com.google.common.collect.Lists;
import objectVerifier.Assert;
import objectVerifier.ObjectVerifier;
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

		Assert.objectsAreEqual(actualThing, expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testExactMatchFail() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe");

		ParentThing expectedThing = new ParentThing()
				.setFirstName("John");

		Assert.objectsAreEqual(actualThing, expectedThing);
	}

	@Test
	public void testContainsMatch() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe Smith");

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe");

		Assert.objectsAreEqual(actualThing, expectedThing, Lists.newArrayList(new StringContainsRule()));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testContainsMatchFail() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("John Smith");

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe");

		Assert.objectsAreEqual(actualThing, expectedThing, Lists.newArrayList(new StringContainsRule()));
	}

}
