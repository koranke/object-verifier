package objectVerifier.utilities;

import org.testng.Assert;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class IntrospectionHelper {

	public static <T> PropertyDescriptor[] getPropertyDescriptors(Class<T> cls) {
		BeanInfo beanInfo = getBeanInfo(cls);
		return beanInfo.getPropertyDescriptors();
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
			if (propertyDescriptor.getReadMethod() != null) {
				return ((T) propertyDescriptor.getReadMethod().invoke(object));
			} else {
				Method method = getBooleanMethod(propertyDescriptor, object);
				if (method != null) {
					return (T) method.invoke(object);
				}
				System.out.println("WARNING: Unable to find getter method for object: " + propertyDescriptor.getName());
			}
		} catch (Exception e) {
			System.out.println("WARNING: Exception occurred while invoking getter method for object: " + propertyDescriptor.getName());
			e.printStackTrace();
			return null;
		}
		return null;
	}

	private static Method getBooleanMethod(PropertyDescriptor propertyDescriptor, Object object) {
		String propertyName = propertyDescriptor.getName();
		propertyName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
		if (propertyDescriptor.getPropertyType().getSimpleName().equals("Boolean")) {
			Method[] methods = object.getClass().getMethods();
			for (Method method : methods) {
				if (method.getName().equals("is" + propertyName)) {
					return method;
				}
			}
		}
		return null;
	}

}
