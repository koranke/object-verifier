package objectVerifier.utilities;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ListConverter {

	public static List getAsList(Object object) {
		if (object == null) return null;
		if (ObjectHelper.isList(object.getClass())) {
			return (List<?>) object;
		}
		if (ObjectHelper.isSet(object.getClass())) {
			Set<?> set = (Set<?>) object;
			List<?> list = new ArrayList<>(set);
			return list;
		}
		if (ObjectHelper.isArray(object.getClass())) {
			try {
				return Arrays.asList((Object[]) object);
			} catch (Exception e) { }

			if (object instanceof long[]) {
				long[] array = (long[]) object;
				List<Long> convertedList = new ArrayList<>();
				for (long item : array) {
					convertedList.add(item);
				}
				return convertedList;
			}
			if (object instanceof int[]) {
				int[] array = (int[]) object;
				List<Integer> convertedList = new ArrayList<>();
				for (int item : array) {
					convertedList.add(item);
				}
				return convertedList;
			}
			if (object instanceof byte[]) {
				byte[] array = (byte[]) object;
				List<Byte> convertedList = new ArrayList<>();
				for (byte item : array) {
					convertedList.add(item);
				}
				return convertedList;
			}
			if (object instanceof boolean[]) {
				boolean[] array = (boolean[]) object;
				List<Boolean> convertedList = new ArrayList<>();
				for (boolean item : array) {
					convertedList.add(item);
				}
				return convertedList;
			}
			if (object instanceof short[]) {
				short[] array = (short[]) object;
				List<Short> convertedList = new ArrayList<>();
				for (short item : array) {
					convertedList.add(item);
				}
				return convertedList;
			}
			if (object instanceof double[]) {
				double[] array = (double[]) object;
				List<Double> convertedList = new ArrayList<>();
				for (double item : array) {
					convertedList.add(item);
				}
				return convertedList;
			}
			if (object instanceof float[]) {
				float[] array = (float[]) object;
				List<Float> convertedList = new ArrayList<>();
				for (float item : array) {
					convertedList.add(item);
				}
				return convertedList;
			}
			if (object instanceof char[]) {
				char[] array = (char[]) object;
				List<Character> convertedList = new ArrayList<>();
				for (char item : array) {
					convertedList.add(item);
				}
				return convertedList;
			}
		}
		Assert.fail("Failed to convert object to list. " + object.toString());
		return null;
	}
}
