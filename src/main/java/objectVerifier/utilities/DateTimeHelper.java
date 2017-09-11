package objectVerifier.utilities;

import org.testng.Assert;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;

public class DateTimeHelper {
	/**
	 * Method for determining if one date is within the range of two other dates.
	 * For example:
	 * <pre>
	 * {@code isWithingDateRange(availabilityDate, currentDate, expirationDate);}
	 * </pre>
	 * @param startDate Date representing start of range.
	 * @param comparatorDate Date to check if in range.
	 * @param endDate Date representing end of range.
	 * @return True or False
	 */
	public static boolean isWithinDateRange(LocalDateTime startDate, LocalDateTime comparatorDate, LocalDateTime endDate) {
		return (startDate.isBefore(comparatorDate) || startDate.equals(comparatorDate)) && (comparatorDate.isBefore(endDate) || comparatorDate.equals(endDate));
	}

	public static boolean isWithinDateRange(Date startDate, Date comparatorDate, Date endDate) {
		return (startDate.before(comparatorDate) || startDate.equals(comparatorDate)) && (comparatorDate.before(endDate) || comparatorDate.equals(endDate));
	}

	/**
	 * Method for determining if a date is within a range of time (plus or minus) to another date.  For example, use this to determine
	 * if one date is within 5 minutes of another date.  For example:
	 * <pre>{@code isWithinTimeRange(actualDate, 5, ChronoUnit.MINUTES, expectedDate}</pre>
	 * @param comparatorDate The date that we want to confirm is withing a particular time range.
	 * @param checkRange The amount of time before and after the root verification time.  For example, 1, 2 or 10.
	 * @param timeUnit The unit of time.  For example, seconds, minutes or hours.  Use ChronoUnit to set unit of time.
	 * @param referenceDate The date that we want to use as our root verification time.
	 * @return True or False.
	 */
	public static boolean isWithinTimeRange(LocalDateTime comparatorDate, int checkRange, TemporalUnit timeUnit, LocalDateTime referenceDate) {
		if (comparatorDate == null || referenceDate == null) return false;
		return comparatorDate.isAfter(referenceDate.minus(checkRange, timeUnit)) && comparatorDate.isBefore(referenceDate.plus(checkRange, timeUnit));
	}

	public static boolean isWithinTimeRange(LocalDate comparatorDate, int checkRange, TemporalUnit timeUnit, LocalDate referenceDate) {
		if (comparatorDate == null || referenceDate == null) return false;
		LocalDateTime cd = comparatorDate.atStartOfDay();
		LocalDateTime rd = referenceDate.atStartOfDay();
		return isWithinTimeRange(cd, checkRange, timeUnit, rd);
	}

	public static boolean isWithinTimeRange(java.sql.Timestamp comparatorDate, int checkRange, TemporalUnit timeUnit, java.sql.Timestamp referenceDate) {
		if (comparatorDate == null || referenceDate == null) return false;
		LocalDateTime cd = comparatorDate.toLocalDateTime();
		LocalDateTime rd = referenceDate.toLocalDateTime();
		return isWithinTimeRange(cd, checkRange, timeUnit, rd);
	}

	public static boolean isWithinTimeRange(java.sql.Date comparatorDate, int checkRange, TemporalUnit timeUnit, java.sql.Date referenceDate) {
		if (comparatorDate == null || referenceDate == null) return false;
		LocalDateTime cd = comparatorDate.toLocalDate().atStartOfDay();
		LocalDateTime rd = referenceDate.toLocalDate().atStartOfDay();
		return isWithinTimeRange(cd, checkRange, timeUnit, rd);
	}

	public static boolean isWithinTimeRange(java.util.Date comparatorDate, int checkRange, TemporalUnit timeUnit, java.util.Date referenceDate) {
		if (comparatorDate == null || referenceDate == null) return false;
		LocalDateTime cd = LocalDateTime.ofInstant(comparatorDate.toInstant(), ZoneId.systemDefault());
		LocalDateTime rd = LocalDateTime.ofInstant(referenceDate.toInstant(), ZoneId.systemDefault());
		return isWithinTimeRange(cd, checkRange, timeUnit, rd);
	}

	public static boolean isWithinTimeRange(java.util.Calendar comparatorDate, int checkRange, TemporalUnit timeUnit, java.util.Calendar referenceDate) {
		if (comparatorDate == null || referenceDate == null) return false;
		LocalDateTime cd = LocalDateTime.ofInstant(comparatorDate.toInstant(), ZoneId.systemDefault());
		LocalDateTime rd = LocalDateTime.ofInstant(referenceDate.toInstant(), ZoneId.systemDefault());
		return isWithinTimeRange(cd, checkRange, timeUnit, rd);
	}

	public static <T> boolean isWithinTimeRange(Class<T> cls, T comparatorDate, int checkRange, TemporalUnit timeUnit, T referenceDate) {
		switch (cls.getCanonicalName()) {
			case "java.time.LocalDateTime":
				return isWithinTimeRange((LocalDateTime) comparatorDate, checkRange, timeUnit, (LocalDateTime) referenceDate);
			case "java.time.LocalDate":
				return isWithinTimeRange((LocalDate) comparatorDate, checkRange, timeUnit, (LocalDate) referenceDate);
			case "java.sql.Timestamp":
				return isWithinTimeRange((java.sql.Timestamp) comparatorDate, checkRange, timeUnit, (java.sql.Timestamp)referenceDate);
			case "java.sql.Date":
				return isWithinTimeRange((java.sql.Date) comparatorDate, checkRange, timeUnit, (java.sql.Date)referenceDate);
			case "java.util.Date":
				return isWithinTimeRange((java.util.Date) comparatorDate, checkRange, timeUnit, (java.util.Date)referenceDate);
			case "java.util.Calendar":
				return isWithinTimeRange((java.util.Calendar) comparatorDate, checkRange, timeUnit, (java.util.Calendar)referenceDate);
			default:
				Assert.fail(String.format("Data type %s not supported.  Update this method to add support.", cls.getCanonicalName()));
		}
		return false;
	}

}
