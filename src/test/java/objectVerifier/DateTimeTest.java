package objectVerifier;

import objectVerifier.verificationRules.DateTimeInRangeRule;
import org.testng.annotations.Test;
import supportingClasses.ParentThing;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeTest {

	@Test
	public void testDateTimeInRange() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();

		Verify.that(actualThing)
				.usingFields("dateJoined")
				.usingRule(new DateTimeInRangeRule(5, ChronoUnit.MINUTES))
				.isEqualTo(expectedThing);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testDateTimeInRangeFail() {
		ParentThing actualThing = ParentThing.getPopulatedParent();
		ParentThing expectedThing = ParentThing.getPopulatedParent();
		actualThing.setDateJoined(LocalDateTime.now().minusMinutes(10));

		Verify.that(actualThing)
				.usingFields("dateJoined")
				.usingRule(new DateTimeInRangeRule(5, ChronoUnit.MINUTES))
				.isEqualTo(expectedThing);
	}
}
