package objectVerifier.utilities;

import org.testng.Assert;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class IntrospectionHelper {
	public static Method getMethod(BeanInfo beanInfo, String methodNameToFind) {
		MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
		for (MethodDescriptor methodDescriptor : methodDescriptors) {
			if (methodDescriptor.getName().equals(methodNameToFind)) {
				return methodDescriptor.getMethod();
			}
		}
		Assert.fail(String.format("Failed to find method %s.", methodNameToFind));
		return null;
	}

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

	public static void callSetter(PropertyDescriptor propertyDescriptor, Object object, Object... args) throws Exception {
		propertyDescriptor.getWriteMethod().invoke(object, args);
	}

	/**
	 * Use this to get the value for a member in an object.  For example, if you have an object of type
	 * "TrackMetadata" called trackMetaData and if this object contains a member called "genreId", then you could use this
	 * method to get the value for genreId.  The call would look like:
	 *
	 * IntrospectionHelper.getFieldValueFromObject(trackMetaData, "genreId");
	 *
	 * Why would you do this instead of just calling trackMetaData.getGenreId()?  You can use this
	 * as a generic method for getting fields values from any object and any field.  See usage
	 * in CollectionHelper.getListOfField.
	 *
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static <T> T getFieldValueFromObject(Object object, String fieldName) {
		PropertyDescriptor[] descriptors = IntrospectionHelper.getPropertyDescriptors(object.getClass());
		boolean fieldFound = false;
		Object fieldValue = null;
		for (PropertyDescriptor descriptor : descriptors) {
			if (descriptor.getName().equals(fieldName)) {
				fieldFound = true;
				fieldValue = IntrospectionHelper.getGetterResult(descriptor, object);
				break;
			}
		}
		Assert.assertTrue(fieldFound, String.format("Failed to find field %s in object %s.", fieldName, object.getClass().getSimpleName()));
		return (T) fieldValue;
	}

	public static List<?> getListFromObject(PropertyDescriptor dataItemDescriptor, Object object) {
		List<?> listFromObject;

		Object itemListForObject = IntrospectionHelper.getGetterResult(dataItemDescriptor, object);
		if (itemListForObject == null) {
			return null;
		}

		boolean isArray = itemListForObject.getClass().isArray();

		if (isArray) {
			listFromObject = itemListForObject == null ? null : Arrays.asList(IntrospectionHelper.getGetterResult(dataItemDescriptor, object));
		} else if(ObjectHelper.isSet(dataItemDescriptor.getPropertyType())) {
			if (itemListForObject == null) {
				listFromObject = null;
			} else {
				listFromObject = new ArrayList<>();
				listFromObject.addAll(IntrospectionHelper.getGetterResult(dataItemDescriptor, object));
			}
		}
		else {
			listFromObject = itemListForObject == null ? null : IntrospectionHelper.getGetterResult(dataItemDescriptor, object);
		}

		return listFromObject;
	}

	public static Map<?, ?> getMapFromObject(PropertyDescriptor propertyDescriptor, Object objectWithMap) {
		Map<?, ?> mapFromObject;
		mapFromObject = IntrospectionHelper.getGetterResult(propertyDescriptor, objectWithMap);
		return mapFromObject;
	}

}
