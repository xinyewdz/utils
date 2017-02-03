package org.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 * 
 * @author Aaron
 * @date 2016年9月27日
 */
public class ObjectUtil {

	public static void copy(Object src, Object target) throws ReflectiveOperationException {
		copy(src, target, false);
	}

	public static void copy(Object src, Object target, boolean copySuper) throws ReflectiveOperationException {
		if (src == null || target == null) {
			return;
		}
		Class<? extends Object> srcClass = src.getClass();
		Class<? extends Object> targetClass = target.getClass();
		Field[] targetFields = targetClass.getDeclaredFields();
		Method[] srcMethods = srcClass.getMethods();
		Method[] targetMethods = targetClass.getMethods();
		Map<String, Method> srcGetMethodMap = new HashMap<String, Method>();
		Map<String, Method> targetSetMethodMap = new HashMap<String, Method>();

		getMethod(srcMethods, "get", srcGetMethodMap);
		getMethod(targetMethods, "set", targetSetMethodMap);

		if (copySuper) {
			Class<? extends Object> targetSuperClass = targetClass.getSuperclass();
			Class<? extends Object> srcSuperClass = srcClass.getSuperclass();
			if (targetSuperClass != null) {
				Field[] superFields = targetSuperClass.getDeclaredFields();
				int fieldSize = targetFields.length + superFields.length;
				Field[] _fields = new Field[fieldSize];
				System.arraycopy(targetFields, 0, _fields, 0, targetFields.length);
				System.arraycopy(superFields, 0, _fields, targetFields.length, superFields.length);
				targetFields = _fields;
				Method[] superMethods = targetSuperClass.getMethods();
				getMethod(superMethods, "set", targetSetMethodMap);
			}

			if (srcSuperClass != null) {
				Method[] superMethods = srcSuperClass.getMethods();
				getMethod(superMethods, "get", srcGetMethodMap);
			}

		}

		for (int i = 0, len = targetFields.length; i < len; i++) {
			String fieldName = targetFields[i].getName();
			String setMethodName = getSetMethod(fieldName);
			String getMethodName = getGetMethod(fieldName);
			Method setMethod = targetSetMethodMap.get(setMethodName);
			Method getMethod = srcGetMethodMap.get(getMethodName);
			if (setMethod != null && getMethod != null) {
				Object val = getMethod.invoke(src);
				if (val != null) {
					setMethod.invoke(target, val);
				}
			}

		}

	}

	private static void getMethod(Method[] methods, String namePrefix, Map<String, Method> methodMap) {
		for (int i = 0, len = methods.length; i < len; i++) {
			Method method = methods[i];
			String name = method.getName();
			if (name.startsWith(namePrefix)) {
				methodMap.put(name, method);
			}
		}
	}

	private static String getSetMethod(String fieldName) {
		StringBuilder builder = new StringBuilder("set");
		builder.append(Character.toUpperCase(fieldName.charAt(0)));
		builder.append(fieldName.substring(1));
		return builder.toString();
	}

	private static String getGetMethod(String fieldName) {
		StringBuilder builder = new StringBuilder("get");
		builder.append(Character.toUpperCase(fieldName.charAt(0)));
		builder.append(fieldName.substring(1));
		return builder.toString();
	}

}
