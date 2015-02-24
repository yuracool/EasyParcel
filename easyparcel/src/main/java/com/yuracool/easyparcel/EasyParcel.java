package com.yuracool.easyparcel;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kuhta on 04.02.2015.
 */
public abstract class EasyParcel implements Parcelable {
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		long time = System.currentTimeMillis();
		writeInstanceToParcel(this, dest, flags);
		System.out.println("writeToParcel time = " + String.valueOf(System.currentTimeMillis() - time));
	}

	private static void writeInstanceToParcel(Object instance, Parcel dest, int flags){
		List<Field> fields = ReflectionUtils.getFields(instance.getClass(), EasyParcel.class);
		ReflectionUtils.removeStaticFields(fields);

		dest.writeString(instance.getClass().getName());

		for (Field field : fields) {
			field.setAccessible(true);
			writeField(dest, flags, instance, field);
		}
	}

	private static void writeField(Parcel dest, int flags, Object instance, Field field){
		Object obj = null;

		try {
			obj = field.get(instance);
		}catch (IllegalAccessException e) {/*ignore shouldn't appear*/}

		if(obj instanceof Integer){
			dest.writeInt((int) obj);
		}else if(obj instanceof Long){
			dest.writeLong((long) obj);
		}else if(obj instanceof Short){
			dest.writeInt(((short) obj));
		}else if(obj instanceof Byte){
			dest.writeByte((byte) obj);
		}else if(obj instanceof Character){
			dest.writeInt((char) obj);
		}else if(obj instanceof Float){
			dest.writeFloat((float) obj);
		}else if(obj instanceof Double){
			dest.writeDouble((double) obj);
		}else if(obj instanceof Boolean){
			dest.writeInt(((boolean) obj) ? 1 : 0);
		}else if(obj instanceof boolean[]){
			dest.writeBooleanArray((boolean[]) obj);
		}else if(obj instanceof String){
			dest.writeString((String) obj);
		}else if(obj instanceof String[]){
			dest.writeStringArray((String[]) obj);
		}else if(obj instanceof byte[]){
			dest.writeByteArray((byte[]) obj);
		}else if(obj instanceof int[]){
			dest.writeIntArray((int[]) obj);
		}else if(obj instanceof long[]){
			dest.writeLongArray((long[]) obj);
		}else if(obj instanceof short[]){
			short[] array = (short[]) obj;
			Short[] temp = new Short[array.length];

			for (int i = 0; i < array.length; i++) {
				temp[i] = array[i];
			}

			dest.writeList(Arrays.asList(temp));
		}else if(obj instanceof char[]){
			dest.writeCharArray((char[]) obj);
		}else if(obj instanceof float[]){
			dest.writeFloatArray((float[]) obj);
		}else if(obj instanceof double[]){
			dest.writeDoubleArray((double[]) obj);
		}else if(obj instanceof List){
			dest.writeList((List) obj);
		}else if(obj instanceof Integer[] || obj instanceof Byte[] || obj instanceof Short[] || obj instanceof Long[] ||
				obj instanceof Boolean[] || obj instanceof Float[] || obj instanceof Double[] || obj instanceof Character[]){
			List list = Arrays.asList((Object[])obj);
			dest.writeList(list);
		}else if(obj instanceof EasyParcel){
			if(ReflectionUtils.isClassStaticInner(obj.getClass(), instance.getClass())) {
				writeInstanceToParcel(obj, dest, flags);
			}else{
				throw new UnsupportedOperationException("Only nested static classes are supported");
			}
		}
	}

	public static final Creator CREATOR = new Creator() {

		@Override
		public Object createFromParcel(Parcel in) {
			long time = System.currentTimeMillis();
			Object ret = EasyParcel.createInstanceFromParcel(in);
			System.out.println("createFromParcel time = " + String.valueOf(System.currentTimeMillis() - time));
			return ret;
		}

		@Override
		public Object[] newArray(int size) {
			return new Object[size];
		}

	};

	private static Object createInstanceFromParcel(Parcel in){
		String className = in.readString();

		Class clazz;
		try {
			clazz = Class.forName(className);
		}catch (ClassNotFoundException e) {/*shouldn't appear*/ return null;}

		Object ret = null;
		try {
			ret = ReflectionUtils.instantiateObject(clazz);
			fillFromParcel(ret, in);
		}
		catch (IllegalAccessException e) {
			/*ignore shouldn't appear*/
		}
		catch (InvocationTargetException e) {
			/*can appear when calling a constructor exception has been occurred*/
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
			/*can appear when class is abstract*/
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
			/*can appear when class doesn't have empty constructor*/
		}

		return ret;
	}

	private static void fillFromParcel(Object instance, Parcel in) {
		List<Field> fields = ReflectionUtils.getFields(instance.getClass(), EasyParcel.class);
		ReflectionUtils.removeStaticFields(fields);

		for (Field field : fields) {
			field.setAccessible(true);
			Class type = field.getType();
			Object value = null;

			if(type == int.class){
				value = in.readInt();
			}else if(type == long.class){
				value = in.readLong();
			}else if(type == short.class){
				value = (short) in.readInt();
			}else if(type == byte.class){
				value = in.readByte();
			}else if(type == char.class){
				value = (char) in.readInt();
			}else if(type == float.class){
				value = in.readFloat();
			}else if(type == double.class){
				value = in.readDouble();
			}else if(type == boolean.class){
				value = in.readInt() == 1;
			}else if(type == boolean[].class){
				value = in.createBooleanArray();
			}else if(type == String.class){
				value = in.readString();
			}else if(type == String[].class){
				value = in.createStringArray();
			}else if(type == int[].class){
				value = in.createIntArray();
			}else if(type == byte[].class){
				value = in.createByteArray();
			}else if(type == long[].class){
				value = in.createLongArray();
			}else if(type == float[].class){
				value = in.createFloatArray();
			}else if(type == double[].class){
				value = in.createDoubleArray();
			}else if(type == char[].class){
				value = in.createCharArray();
			}else if(type == short[].class){
				List<Short> list = in.readArrayList(short.class.getClassLoader());
				short[] tmp = new short[list.size()];

				for(int i=0; i<tmp.length; i++){
					tmp[i] = list.get(i);
				}

				value = tmp;
			}else if(type == Integer[].class || type == Byte[].class || type == Short[].class || type == Long[].class ||
					type == Boolean[].class || type == Float[].class || type == Double[].class || type == Character[].class){
				List list = in.readArrayList(type.getClassLoader());
				value = Array.newInstance(type.getComponentType(), list.size());
				list.toArray((Object[])value);
			}else if(type == List.class){
				ParameterizedType listType = (ParameterizedType) field.getGenericType();
				Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
				value = in.readArrayList(listClass.getClassLoader());
			}else if(EasyParcel.class.isAssignableFrom(type)){
				try {
					int dataPosition = in.dataPosition();
					String className = in.readString();
					Class clazz = Class.forName(className);

					if(ReflectionUtils.isClassStaticInner(clazz, instance.getClass())){
						in.setDataPosition(dataPosition);
						value = createInstanceFromParcel(in);
					}
				}catch (ClassNotFoundException e) {/*ignore*/}
			}

			if(value != null) {
				try {
					field.set(instance, value);
				}
				catch (IllegalAccessException e) {/*ignore*/}
			}
		}
	}
}
