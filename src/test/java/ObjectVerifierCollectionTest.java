import com.google.common.collect.Lists;
import objectVerifier.Assert;
import objectVerifier.FieldsToCheck;
import org.testng.annotations.Test;
import supportingClasses.ParentThing;

public class ObjectVerifierCollectionTest {

	@Test
	public void testListExactMatch() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(actualThing.getFavoriteWords());

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.addField("favoriteWords");

		Assert.objectsAreEqual(actualThing, expectedThing, fieldsToCheck);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testListExactMatchFailOnCount() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = new ParentThing()
				.setFavoriteWords(Lists.newArrayList("zippy"));

		FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(ParentThing.class)
				.addField("favoriteWords");

		Assert.objectsAreEqual(actualThing, expectedThing, fieldsToCheck);
	}


}
