package be.fabrice.utils;

import java.lang.reflect.Field;

public class TestUtilReflexion {
	static public void setProperty(Object target, String propertyName, Object value){
		Class<?> clazz = target.getClass();
		try {
			Field field = clazz.getDeclaredField(propertyName);
			boolean accessibilityState = field.isAccessible();
			field.setAccessible(true);
			field.set(target, value);
			field.setAccessible(accessibilityState);
		} catch (Exception e) {
			throw new RuntimeException("Impossible d'injecter la propriété "+propertyName+" dans la classe "+clazz, e);
		}
	}
}
