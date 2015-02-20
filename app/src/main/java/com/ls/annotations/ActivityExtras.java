package com.ls.annotations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import com.ls.annotations.annotation.acyivity.ActivityAnnotation;
import com.ls.annotations.annotation.acyivity.Extras;
import com.ls.annotations.annotation.acyivity.ViewId;
import com.ls.annotations.annotation.data.Entity;
import com.ls.annotations.annotation.data.ExtendedEntity;

/**
 * Created by Kuhta on 04.02.2015.
 */
public class ActivityExtras extends ActivityAnnotation{
	public static final String NUMBER_EXTRA = "NUMBER_EXTRA";
	public static final String STRING_EXTRA = "STRING_EXTRA";
	public static final String PARCELABLE_EXTRA = "PARCELABLE_EXTRA";

	public static void startThisActivity(Activity activity, int number, String str, Parcelable parcelable){
		Intent intent = new Intent(activity, ActivityExtras.class);
		intent.putExtra(NUMBER_EXTRA, number);
		intent.putExtra(STRING_EXTRA, str);
		intent.putExtra(PARCELABLE_EXTRA, parcelable);

		activity.startActivity(intent);
	}

	@Extras(key = NUMBER_EXTRA)
	private int number;
	@Extras(key = STRING_EXTRA)
	private String str;
	@Extras(key = PARCELABLE_EXTRA)
	private ExtendedEntity entity;

	@ViewId(id = R.id.txtNumber)
	private TextView tvNumber;
	@ViewId(id = R.id.txtString)
	private TextView tvString;
	@ViewId(id = R.id.txtEntityNumber)
	private TextView tvEntityNumber;
	@ViewId(id = R.id.txtEntityString)
	private TextView tvEntityString;
	@ViewId(id = R.id.txtArrayString)
	private TextView tvArrayString;
	@ViewId(id = R.id.txtInnerString)
	private TextView tvInnerString;
	@ViewId(id = R.id.txtInnerStaticString)
	private TextView tvInnerStaticString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extras);

		initView();
	}

	private void initView() {
		tvNumber.setText(String.valueOf(number));
		tvString.setText(str);
		tvEntityNumber.setText(String.valueOf(entity.getX()));
		tvEntityString.setText(entity.str);

		StringBuilder phrase = new StringBuilder();
		for(String word : entity.getArray()){
			if(phrase.length() != 0)
				phrase.append(", ");
			else{
				phrase.append("[");
			}

			phrase.append(word);
		}

		phrase.append("]");

		tvArrayString.setText(phrase);
		tvInnerString.setText(entity.getInnerString());
		tvInnerStaticString.setText(entity.getInnerStaticString());
	}
}
