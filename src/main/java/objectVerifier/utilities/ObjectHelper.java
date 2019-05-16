package objectVerifier.utilities;

import java.lang.reflect.Method;

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

	public static boolean isCollection(Class<?> cls) {
		return isList(cls) || isArray(cls) || isSet(cls);
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
				cls == java.util.ArrayList.class ||
				cls == java.util.LinkedList.class ||
				cls == java.util.Vector.class;
	}

	public static boolean isSet(Class<?> cls) {
		if (cls == null) {
			return false;
		}
		return cls == java.util.Set.class ||
				cls == java.util.HashSet.class ||
				cls == java.util.LinkedHashSet.class ||
				cls == java.util.TreeSet.class;
	}

	public static boolean isMap(Class<?> cls) {
		if (cls == null) {
			return false;
		}
		return cls == java.util.Map.class ||
				cls == java.util.HashMap.class ||
				cls == java.util.LinkedHashMap.class ||
				cls == java.util.TreeMap.class;
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

}
