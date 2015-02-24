package com.yuracool.annotations.acyivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kuhta on 04.02.2015.
 */
public class ActivityAnnotation extends Activity {
	private List<Field> mFields;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFields = getFields();
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);

		initFieldIdAnnotation();
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);

		initFieldIdAnnotation();
	}

	@Override
	public void setContentView(View view, ViewGroup.LayoutParams params) {
		super.setContentView(view, params);

		initFieldIdAnnotation();
	}

	public void onClick(View view){/*empty*/}

	private void initFieldIdAnnotation() {
		long t = System.currentTimeMillis();

		for (Field field : mFields) {
			if (field.isAnnotationPresent(ViewId.class)) {
				initViewById(field);
			}

			if(field.isAnnotationPresent(OnClick.class)){
				initOnClickListener(field);
			}

			if(field.isAnnotationPresent(Extras.class)){
				initExtra(field);
			}
		}

		Log.d("setContentView", "time: " + String.valueOf(System.currentTimeMillis() - t));
	}

	private List<Field> getFields(){
		List<Field> fields = new LinkedList<>();
		Class obj = getClass();

		do{
			Field[] temp = obj.getDeclaredFields();
			fields.addAll(Arrays.asList(temp));

			obj = obj.getSuperclass();
		}while(obj != ActivityAnnotation.class);

		return fields;
	}

	private void initViewById(Field field){
		try {
			field.setAccessible(true);

			View view = findViewById(field.getAnnotation(ViewId.class).id());
			field.set(this, view);
		}
		catch (Exception e) {e.printStackTrace();}
	}

	private void initOnClickListener(Field field) {
		try {
			field.setAccessible(true);

			field.getType().getMethod("setOnClickListener", View.OnClickListener.class).invoke(field.get(this), new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					ActivityAnnotation.this.onClick(view);
				}
			});
		}
		catch (Exception e) {e.printStackTrace();}
	}

	private void initExtra(Field field) {
		Intent intent = getIntent();

		if(intent != null) {
			try {
				field.setAccessible(true);

				Class fieldType = field.getType();
				Object value = null;

				if (fieldType == int.class) {
					value = intent.getIntExtra(field.getAnnotation(Extras.class).key(), 0);
				}else if (fieldType == String.class) {
					value = intent.getStringExtra(field.getAnnotation(Extras.class).key());
				}else{
					try {
						fieldType.asSubclass(Parcelable.class);
						value = intent.getParcelableExtra(field.getAnnotation(Extras.class).key());
					}catch (Exception e) {e.printStackTrace();}
				}

				field.set(this, value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
