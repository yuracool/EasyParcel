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

	public static boolean isClassStaticInner(Class inner, Class parent){
		boolean ret = true;

		if(parent == null){
			return ret;
		}

		Field[] fields = inner.getDeclaredFields();
		String innerPointer = parent.getName() + ".this$";

		for(Field field : fields){
			String fieldName = field.getType().getName() + "." + field.getName();
			if(fieldName.contains(innerPointer)){
				ret = false;
				break;
			}
		}

		return ret;
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
