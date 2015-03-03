package com.yuracool.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import com.yuracool.annotation.ActivityAnnotation;
import com.yuracool.annotation.Extras;
import com.yuracool.data.EasyParcelTest;

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
		setContentView(new View(this));

		if(entity != null)
			Log.d("onCreate", "success");
		else
			Log.d("onCreate", "error");
	}
}
