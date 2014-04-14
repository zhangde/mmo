package com.tongwan.common.lang;

/**
 * @author zhangde
 * 
 * @date 2014年1月17日
 */
public class TypeX {
	public static Object convertTo(Object value, String valueType) {
		if (value.getClass().equals(Double.class)
				|| value.getClass().equals(double.class)) {
			Double double1 = (Double) value;
			return doubleTo(double1, valueType);
		} else if (value.getClass().equals(Integer.class)
				|| value.getClass().equals(int.class)) {
			return value;
		} else if (value.getClass().equals(Long.class)
				|| value.getClass().equals(long.class)) {
			Double double1 = (Double) value;
			return double1.longValue();
		} else if (value.getClass().equals(String.class)) {
			return value;
		}
		return null;
	}

	public static Object doubleTo(Double d, String type) {
		if (type.equals(int.class.getSimpleName())
				|| type.equals(Integer.class.getSimpleName())) {
			return d.intValue();
		} else if (type.equals(int.class.getSimpleName())
				|| type.equals(Integer.class.getSimpleName())) {
			return d;
		} else if (type.getClass().equals(Long.class)
				|| type.getClass().equals(long.class)) {
			return d.longValue();
		} else if (type.getClass().equals(String.class)) {
			return d.toString();
		}
		return null;
	}

	public static boolean isInt(String type){
		return type.equals("int") || type.equals("Integer");
	}
	public static boolean isLong(String type){
		return type.equals("long") || type.equals("Long");
	}
	public static boolean isDouble(String type){
		return type.equals("double") || type.equals("Double");
	}
	public static boolean isFloat(String type){
		return type.equals("float") || type.equals("Float");
	}
	public static boolean isBoolean(String type){
		return type.equals("boolean") || type.equals("Boolean");
	}
	public static boolean isString(String type){
		return type.equals("String");
	}
	public static boolean isList(String type){
		return type.equals("List");
	}
	public static boolean isMap(String type){
		return type.equals("Map");
	}
	public static boolean isIntArray(String type){
		return type.equals("int[]");
	}
	public static void main(String[] args) {
		System.out.println(Integer.valueOf("1.0"));
	}
}
