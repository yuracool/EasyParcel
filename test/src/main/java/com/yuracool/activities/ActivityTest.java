package com.yuracool.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.yuracool.annotation.ActivityAnnotation;
import com.yuracool.annotation.Extras;
import com.yuracool.annotation.OnClick;
import com.yuracool.annotation.ViewId;
import com.yuracool.annotations.R;
import com.yuracool.data.EasyParcelTest;
import com.yuracool.easyparcel.EasyParcel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuhta on 03.03.2015.
 */
public class ActivityTest extends ActivityAnnotation {
	public static final String PARCEL_EXTRA = "PARCEL_EXTRA";

	@Extras(key = PARCEL_EXTRA)
    private EasyParcelTest entity;

	public static void startThisActivity(Context context, Parcelable parcel){
		Intent intent = new Intent(context, ActivityTest.class);
		intent.putExtra(PARCEL_EXTRA, parcel);

		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acyivity_test);

		if(entity != null)
			Log.d("onCreate", "success");
		else
			Log.d("onCreate", "error");
	}
}
