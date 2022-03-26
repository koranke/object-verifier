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
		return List.class.isAssignableFrom(cls);
	}

	public static boolean isSet(Class<?> cls) {
		if (cls == null) {
			return false;
		}
		return Set.class.isAssignableFrom(cls);
	}

	public static boolean isMap(Class<?> cls) {
		if (cls == null) {
			return false;
		}
		return Map.class.isAssignableFrom(cls);
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
				cls == java.time.LocalDate.class ||
				cls == java.time.Instant.class;
	}

}
