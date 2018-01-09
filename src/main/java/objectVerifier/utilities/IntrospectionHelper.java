package objectVerifier.utilities;

import org.testng.Assert;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class IntrospectionHelper {

	public static <T> PropertyDescriptor[] getPropertyDescriptors(Class<T> cls) {
		BeanInfo beanInfo = getBeanInfo(cls);
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
		return descriptors;
	}

	public static <T> BeanInfo getBeanInfo(Class<T> cls) {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(cls);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to get bean info for class.");
		}
		return beanInfo;
	}

	public static <T> T getGetterResult(PropertyDescriptor propertyDescriptor, Object object) {
		try {
			T result = ((T) propertyDescriptor.getReadMethod().invoke(object));
			return result;
		} catch (Exception e) {
			return null;
		}
	}

}
