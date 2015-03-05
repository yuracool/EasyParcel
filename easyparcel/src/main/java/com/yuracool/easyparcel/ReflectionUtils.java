package com.yuracool.easyparcel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kuhta on 23.02.2015.
 */
public class ReflectionUtils {
	private ReflectionUtils(){}

	public static boolean isClassStaticInner(Class clazz){
		if(!clazz.isMemberClass())
			return true;

		int modifiers = clazz.getModifiers();
		if(Modifier.isStatic(modifiers))
			return true;

		return false;
	}

	public static Object instantiateObject(Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Constructor constructor = clazz.getDeclaredConstructor();
		constructor.setAccessible(true);
		return constructor.newInstance();
	}

	public static List<Field> getFields(Class clazz, Class downTo){
		List<Field> ret = new LinkedList<>();

		Class tmp = clazz;

		do{
			Field[] fields = tmp.getDeclaredFields();
			ret.addAll(Arrays.asList(fields));
			tmp = tmp.getSuperclass();
		}while(tmp != downTo);

		return ret;
	}

	public static void removeStaticFields(List<Field> fields) {
		Iterator<Field> iterator = fields.iterator();

		while (iterator.hasNext()){
			int modifiers = iterator.next().getModifiers();
			if(Modifier.isStatic(modifiers)){
				iterator.remove();
			}
		}
	}
}
