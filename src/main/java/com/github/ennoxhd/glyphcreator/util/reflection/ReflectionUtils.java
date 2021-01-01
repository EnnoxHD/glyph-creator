package com.github.ennoxhd.glyphcreator.util.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Contains some Java reflections related utility functions.
 */
public final class ReflectionUtils {
	
	/**
	 * Gets the first parameterized generic type of a class.
	 * @param <T> generic type
	 * @param clazz class description of the generic to get the generic type from
	 * @return class description of the generic type or {@code null} on failure
	 */
	public static final <T> Class<T> getGenericTypeClass(final Class<?> clazz) {
		if(clazz == null) return null;
		final ParameterizedType genericSuperclass = (ParameterizedType) clazz.getGenericSuperclass();
		if(genericSuperclass == null) return null;
		Type[] genericTypes = genericSuperclass.getActualTypeArguments();
		if(genericTypes == null || genericTypes.length < 1) return null;
		Type genericType = genericTypes[0];
		if(genericType == null) return null;
		@SuppressWarnings("unchecked")
		Class<T> result = (Class<T>) genericType;
		return result;
	}
	
	/**
	 * Creates a new instance of a given type via the invocation of the standard constructor.
	 * @param <T> type to create a new instance of
	 * @param clazz class description of the type
	 * @return new instance of {@code T}
	 */
	public static final <T> T newInstance(final Class<T> clazz) {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new Error("Can't create instance.", e);
		}
	}
}
