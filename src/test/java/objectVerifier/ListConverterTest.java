package objectVerifier;

import objectVerifier.utilities.ListConverter;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListConverterTest {

	@Test
	public void testListConvertFromArray() {
		Integer[] sourceData = new Integer[]{1, 2};
		List<Integer> finalList = ListConverter.getAsList(sourceData);

		Assert.assertEquals(finalList.size(), sourceData.length);
		Assert.assertEquals(finalList.get(0), sourceData[0]);
		Assert.assertEquals(finalList.get(1), sourceData[1]);
	}

	@Test
	public void testListConvertFromList() {
		List<Integer> sourceData = new ArrayList<>();
		sourceData.add(1);
		sourceData.add(2);

		List<Integer> finalList = ListConverter.getAsList(sourceData);

		Assert.assertEquals(finalList.size(), sourceData.size());
		Assert.assertEquals(finalList.get(0), sourceData.get(0));
		Assert.assertEquals(finalList.get(1), sourceData.get(1));
	}

	@Test
	public void testListConvertFromSet() {
		Set<Integer> sourceData = new HashSet<>();
		sourceData.add(100);
		sourceData.add(200);

		List<Integer> finalList = ListConverter.getAsList(sourceData);

		Assert.assertEquals(finalList.size(), sourceData.size());
		Assert.assertTrue(sourceData.contains(finalList.get(0)));
		Assert.assertTrue(sourceData.contains(finalList.get(1)));
	}

	@Test void testListFromArrayOfNativeInt() {
		int[] sourceData = new int[] {1, 2};
		List<Integer> finalList = ListConverter.getAsList(sourceData);

		Assert.assertEquals(finalList.size(), sourceData.length);
		Assert.assertEquals((int) finalList.get(0), sourceData[0]);
		Assert.assertEquals((int) finalList.get(1), sourceData[1]);
	}

	@Test void testListFromArrayOfNativeLong() {
		long[] sourceData = new long[] {1, 2};
		List<Long> finalList = ListConverter.getAsList(sourceData);

		Assert.assertEquals(finalList.size(), sourceData.length);
		Assert.assertEquals((long) finalList.get(0), sourceData[0]);
		Assert.assertEquals((long) finalList.get(1), sourceData[1]);
	}

	@Test
	public void testListConvertFromNull() {
		Set<Integer> sourceData = null;
		List<Integer> finalList = ListConverter.getAsList(sourceData);

		Assert.assertNull(finalList);
	}

	@Test
	public void testListConvertFromEmpty() {
		Integer[] sourceData = new Integer[]{};
		List<Integer> finalList = ListConverter.getAsList(sourceData);

		Assert.assertEquals(finalList.size(), 0);
	}

}
