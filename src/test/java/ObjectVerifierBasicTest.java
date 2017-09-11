import objectVerifier.ObjectVerifier;
import org.testng.annotations.Test;
import supportingClasses.ParentThing;

public class ObjectVerifierBasicTest {

	@Test
	public void testBasic() {
		ParentThing actualThing = new ParentThing()
				.setFirstName("Joe");

		ParentThing expectedThing = new ParentThing()
				.setFirstName("Joe");

		ObjectVerifier.verifyDomainObject(
				ParentThing.class,
				actualThing,
				expectedThing,
				null,
				null,
				"");
	}
}
