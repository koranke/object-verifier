package objectVerifier.utilities;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObjectHelper {

	public static boolean implementsEquals(Class<?> cls) {
		if (cls == null) {
			return false;
		}
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals("equals")) {
				return true;
			}
		}
		return false;
	}

	private static boolean isComparable(Class<?> cls) {
		if (cls == null) {
			return false;
		}
		Class<?>[] classes = cls.getInterfaces();
		for (Class<?> currentClass : classes){
			if (currentClass == java.lang.Comparable.class) {
				return true;
			}
		}
		return false;
	}

	public static boolean isArray(Class<?> cls) {
		if (cls == null) {
			return false;
		}
		return cls.isArray();
	}

	public static boolean isList(Class<?> cls) {
		if (cls == null) {
			return false;
		}
		return cls == java.util.List.class ||
				cls == java.util.ArrayList.class;
	}

	public static boolean isSet(Class<?> cls) {
		if (cls == null) {
			return false;
		}
		return cls == java.util.Set.class ||
				cls == java.util.HashSet.class;
	}

	public static boolean isMap(Class<?> cls) {
		if (cls == null) {
			return false;
		}
		return cls == java.util.Map.class ||
				cls == java.util.HashMap.class;
	}

	public static boolean isDateType(Class<?> cls) {
		if (cls == null) {
			return false;
		}
		return cls == java.util.Date.class ||
				cls == java.sql.Date.class ||
				cls == java.sql.Timestamp.class ||
				cls == java.util.Calendar.class ||
				cls == java.time.LocalDateTime.class ||
				cls == java.time.LocalDate.class;
	}

	public static Class<?> getClassForMap(Object object) {
		if (object == null) {
			return null;
		}
		Map<?, ?> mapOfObjects = (Map<?, ?>) object;
		if (mapOfObjects == null) return null;
		if (mapOfObjects.size() > 0) {
			return mapOfObjects.values().iterator().next().getClass();
		} else {
			return null;
		}
	}

	public static Class<?> getClassForArray(Object object) {
		if (object == null) {
			return null;
		}
		Object[] arrayOfObjects = (Object[]) object;
		if (arrayOfObjects == null) return null;
		if (arrayOfObjects.length > 0) {
			return arrayOfObjects[0].getClass();
		} else {
			return null;
		}
	}

	public static Class<?> getClassForSet(Object object) {
		if (object == null) {
			return null;
		}
		Set<?> listOfObjects = (Set<?>) object;
		if (listOfObjects != null && listOfObjects.size() > 0) {
			return listOfObjects.iterator().next().getClass();
		} else {
			return null;
		}
	}

	public static Class<?> getClassForList(Object object) {
		if (object == null) {
			return null;
		}
		List<?> listOfObjects = (List<?>) object;
		if (listOfObjects != null && listOfObjects.size() > 0) {
			return listOfObjects.get(0).getClass();
		} else {
			return null;
		}
	}

}
