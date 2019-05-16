package objectVerifier;

import objectVerifier.verificationRules.MapContainsRule;
import org.testng.annotations.Test;
import supportingClasses.ParentThing;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapTest {

	/*
	When looking at equality between maps, we can evaluate this in terms of keys
	and in terms of key values.  For example, in some cases we may want to only verify
	that expected keys exist (more than expected is ok), but that all values in each key exist
	(more than expected is not ok), whereas it's also possible to only verify that expected keys
	exist and also only verify that	expected values exist (more than expected is ok).
	 */

	@Test
	public void testMapExactMatch() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();

		Verify.that(actualThing).usingFields("codes").isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMapExactMatchFailDueToCount() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		actualThing.getCodes().put(99, "Ninety nine");

		Verify.that(actualThing).usingFields("codes").isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMapExactMatchFailDueToContentKey() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		actualThing.getCodes().put(99, "Ninety nine");
		actualThing.getCodes().remove(8);

		Verify.that(actualThing).usingFields("codes").isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMapExactMatchFailDueToContentValue() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		actualThing.getCodes().put(8, "A new eight value");

		Verify.that(actualThing).usingFields("codes").isEqualTo(expectedThing);
	}

	@Test
	public void testMapContainsMatch() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		actualThing.getCodes().put(99, "Ninety Nine");

		Verify.that(actualThing).usingFields("codes").usingRule(new MapContainsRule()).isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMapContainsMatchFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		expectedThing.getCodes().remove(8);
		expectedThing.getCodes().put(99, "Ninety Nine");

		Verify.that(actualThing).usingFields("codes").usingRule(new MapContainsRule()).isEqualTo(expectedThing);
	}

	@Test
	public void testDifferentMapTypes() {
		ParentThing actualThing = new ParentThing();
		ParentThing expectedThing = new ParentThing();

		Map<Integer, String> aMap = new HashMap<>();
		aMap.put(1, "one");
		actualThing.setCodes(aMap);

		Map<Integer, String> eMap = new LinkedHashMap<>();
		eMap.put(1, "one");
		expectedThing.setCodes(eMap);

		Verify.that(actualThing).usingFields("codes").isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testDifferentMapTypesFail() {
		ParentThing actualThing = new ParentThing();
		ParentThing expectedThing = new ParentThing();

		Map<Integer, String> aMap = new HashMap<>();
		aMap.put(1, "one");
		actualThing.setCodes(aMap);

		Map<Integer, String> eMap = new LinkedHashMap<>();
		eMap.put(1, "two");
		expectedThing.setCodes(eMap);

		Verify.that(actualThing).usingFields("codes").isEqualTo(expectedThing);
	}
}
