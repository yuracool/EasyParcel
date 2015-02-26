package com.yuracool.easyparcel;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.*;
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
			writeShortArray((short[]) obj, dest);
		}else if(obj instanceof char[]){
			dest.writeCharArray((char[]) obj);
		}else if(obj instanceof float[]){
			dest.writeFloatArray((float[]) obj);
		}else if(obj instanceof double[]){
			dest.writeDoubleArray((double[]) obj);
		}else if(obj instanceof List){
			dest.writeList((List) obj);
		}else if(obj instanceof Byte[]){
			writeByteArray((Byte[]) obj, dest);
		}else if(obj instanceof Integer[]){
			writeIntArray((Integer[]) obj, dest);
		}else if(obj instanceof Short[]){
			writeShortArray((Short[]) obj, dest);
		}else if(obj instanceof Long[]){
			writeLongArray((Long[]) obj, dest);
		}else if(obj instanceof Boolean[]){
			writeBooleanArray((Boolean[]) obj, dest);
		}else if(obj instanceof Float[]){
			writeFloatArray((Float[]) obj, dest);
		}else if(obj instanceof Double[]){
			writeDoubleArray((Double[]) obj, dest);
		}else if(obj instanceof Character[]){
			writeCharArray((Character[]) obj, dest);
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

			if(type == int.class || type == Integer.class){
				value = in.readInt();
			}else if(type == long.class || type == Long.class){
				value = in.readLong();
			}else if(type == short.class || type == Short.class){
				value = (short) in.readInt();
			}else if(type == byte.class || type == Byte.class){
				value = in.readByte();
			}else if(type == char.class || type == Character.class){
				value = (char) in.readInt();
			}else if(type == float.class || type == Float.class){
				value = in.readFloat();
			}else if(type == double.class || type == Double.class){
				value = in.readDouble();
			}else if(type == boolean.class || type == Boolean.class){
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
				value = readPrimitiveShortArray(in);
			}else if(type == Byte[].class){
				value = readByteArray(in);
			}else if(type == Integer[].class){
				value = readIntArray(in);
			}else if(type == Short[].class){
				value = readShortArray(in);
			}else if(type == Long[].class){
				value = readLongArray(in);
			}else if(type == Boolean[].class){
				value = readBooleanArray(in);
			}else if(type == Float[].class){
				value = readFloatArray(in);
			}else if(type == Double[].class){
				value = readDoubleArray(in);
			}else if(type == Character[].class){
				value = readCharArray(in);
			}else if(type == List.class){
				ParameterizedType listType;

				try{
					listType = (ParameterizedType) field.getGenericType();
				}catch (ClassCastException e){
					//TODO: implement read and write unparameterized list
					throw new IllegalArgumentException("Error occurred with field " + field.getName() + " List should be parameterized");
				}

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

	private static void writeByteArray(Byte[] array, Parcel dest){
		byte[] ret = new byte[array.length];

		for (int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		dest.writeByteArray(ret);
	}

	private static Byte[] readByteArray(Parcel in){
		byte[] array = in.createByteArray();
		Byte[] ret = new Byte[array.length];

		for(int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		return ret;
	}

	private static void writeIntArray(Integer[] array, Parcel dest){
		int[] ret = new int[array.length];

		for (int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		dest.writeIntArray(ret);
	}

	private static Integer[] readIntArray(Parcel in){
		int[] array = in.createIntArray();
		Integer[] ret = new Integer[array.length];

		for(int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		return ret;
	}

	private static void writeLongArray(Long[] array, Parcel dest){
		long[] ret = new long[array.length];

		for (int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		dest.writeLongArray(ret);
	}

	private static Long[] readLongArray(Parcel in){
		long[] array = in.createLongArray();
		Long[] ret = new Long[array.length];

		for(int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		return ret;
	}

	private static void writeShortArray(Short[] array, Parcel dest){
		int[] ret = new int[array.length];

		for (int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		dest.writeIntArray(ret);
	}

	private static Short[] readShortArray(Parcel in){
		int[] array = in.createIntArray();
		Short[] ret = new Short[array.length];

		for(int i=0; i<array.length; i++){
			ret[i] = (short) array[i];
		}

		return ret;
	}

	private static void writeShortArray(short[] array, Parcel dest){
		int[] ret = new int[array.length];

		for (int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		dest.writeIntArray(ret);
	}

	private static short[] readPrimitiveShortArray(Parcel in){
		int[] array = in.createIntArray();
		short[] ret = new short[array.length];

		for(int i=0; i<array.length; i++){
			ret[i] = (short) array[i];
		}

		return ret;
	}

	private static void writeCharArray(Character[] array, Parcel dest){
		char[] ret = new char[array.length];

		for (int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		dest.writeCharArray(ret);
	}

	private static Character[] readCharArray(Parcel in){
		char[] array = in.createCharArray();
		Character[] ret = new Character[array.length];

		for(int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		return ret;
	}

	private static void writeFloatArray(Float[] array, Parcel dest){
		float[] ret = new float[array.length];

		for (int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		dest.writeFloatArray(ret);
	}

	private static Float[] readFloatArray(Parcel in){
		float[] array = in.createFloatArray();
		Float[] ret = new Float[array.length];

		for(int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		return ret;
	}

	private static void writeDoubleArray(Double[] array, Parcel dest){
		double[] ret = new double[array.length];

		for (int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		dest.writeDoubleArray(ret);
	}

	private static Double[] readDoubleArray(Parcel in){
		double[] array = in.createDoubleArray();
		Double[] ret = new Double[array.length];

		for(int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		return ret;
	}

	private static void writeBooleanArray(Boolean[] array, Parcel dest){
		boolean[] ret = new boolean[array.length];

		for (int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		dest.writeBooleanArray(ret);
	}

	private static Boolean[] readBooleanArray(Parcel in){
		boolean[] array = in.createBooleanArray();
		Boolean[] ret = new Boolean[array.length];

		for(int i=0; i<array.length; i++){
			ret[i] = array[i];
		}

		return ret;
	}
}