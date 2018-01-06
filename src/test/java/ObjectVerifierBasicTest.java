import objectVerifier.ObjectVerifier;
import objectVerifier.Verify;
import org.testng.annotations.Test;
import supportingClasses.ParentThing;

public class ObjectVerifierBasicTest {

	@Test
	public void testBasic() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe");

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe");

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
