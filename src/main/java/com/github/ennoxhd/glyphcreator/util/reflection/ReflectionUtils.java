package com.github.ennoxhd.glyphcreator.util.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class ReflectionUtils {
	
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
	
	public static final <T> T newInstance(final Class<T> clazz) {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new Error("Can't create instance.", e);
		}
	}
}
