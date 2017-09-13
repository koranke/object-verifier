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
}
