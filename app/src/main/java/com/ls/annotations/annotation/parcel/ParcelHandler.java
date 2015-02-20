package com.ls.annotations.annotation.parcel;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kuhta on 04.02.2015.
 */
public class ParcelHandler implements Parcelable {
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		List<Field> fields = getFields(this.getClass());

		dest.writeString(this.getClass().getName());

		for (Field field : fields) {
			field.setAccessible(true);

			try {
				if (field.getType() == int.class) {
					dest.writeInt(field.getInt(this));
				}else if (field.getType() == String.class) {
					dest.writeString((String) field.get(this));
				}else if (field.getType() == String[].class){
					dest.writeStringArray((String[]) field.get(this));
				}else{
					try {
						Class parcelHandler = field.getType().asSubclass(ParcelHandler.class);
						Method writeToParcel = parcelHandler.getMethod("writeToParcel", Parcel.class, int.class);
						Object tmp = field.get(this);
						writeToParcel.invoke(tmp, dest, flags);
					}catch (Exception e){e.printStackTrace();}
				}
			}catch (Exception e) {e.printStackTrace();}
		}
	}

	private static List<Field> getFields(Class cl) {
		List<Field> fields = new LinkedList<>();
		Class obj = cl;

		do {
			Field[] temp = obj.getDeclaredFields();
			fields.addAll(Arrays.asList(temp));

			obj = obj.getSuperclass();
		} while (obj != ParcelHandler.class);

		Iterator<Field> it = fields.iterator();
		while (it.hasNext()){
			Field field = it.next();
			String name = field.getName();
			if(name.contains("this$"))
				it.remove();
		}

		return fields;
	}

	public static final Creator CREATOR = new Creator() {

		@Override
		public Object createFromParcel(Parcel in) {
			String className = in.readString();

			Object ret = createObjectByName(className, null);

			if(ret != null)
				fillFromParcel(ret, in);

			return ret;
		}

		@Override
		public Object[] newArray(int size) {
			return new Object[size];
		}

	};

	private static Object createObjectByName(String className, Object instance){
		Object ret = null;

		try {
			Class cl = Class.forName(className);

			if(instance == null) {
				Constructor constructor = cl.getDeclaredConstructor();
				constructor.setAccessible(true);
				ret = constructor.newInstance();
			}else{
				Constructor constructor = cl.getDeclaredConstructor(instance.getClass());
				constructor.setAccessible(true);
				ret = constructor.newInstance(instance);
			}
		}
		catch (Exception e) {e.printStackTrace();}

		return ret;
	}

	private static void fillFromParcel(Object obj, Parcel in){
		List<Field> fields = getFields(obj.getClass());

		for (Field field : fields) {
			field.setAccessible(true);
			Object value = null;

			if (field.getType() == int.class) {
				value = in.readInt();
			}else if (field.getType() == String.class) {
				value = in.readString();
			}else if (field.getType() == String[].class){
				value = in.createStringArray();
			}else{
				try {
					field.getType().asSubclass(ParcelHandler.class);
					int dataPosition = in.dataPosition();
					String className = in.readString();

					if (isClassInner(className, obj.getClass())) {
						value = createObjectByName(className, obj);
						fillFromParcel(value, in);
					}
					else {
						in.setDataPosition(dataPosition);
						value = ParcelHandler.CREATOR.createFromParcel(in);
					}
				}catch (Exception e){e.printStackTrace();}
			}

			try {
				field.set(obj, value);
			}catch (Exception e) {e.printStackTrace();}
		}
	}

	private static boolean isClassInner(String className, Class parent){
		boolean ret = false;

		try {
			Class cl = Class.forName(className);
			Field[] fields = cl.getDeclaredFields();
			String innerPointer = parent.getName() + ".this$";

			for(Field field : fields){
				String fieldName = field.getType().getName() + "." + field.getName();
				if(fieldName.contains(innerPointer)){
					ret = true;
					break;
				}
			}
		}
		catch (Exception e) {e.printStackTrace();}

		return ret;
	}
}
