package com.yuzeeeer.annotations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.TextView;
import com.yuzeeeer.annotations.acyivity.ActivityAnnotation;
import com.yuzeeeer.annotations.acyivity.Extras;
import com.yuzeeeer.annotations.acyivity.ViewId;
import com.yuzeeeer.annotations.data.ExtendedEntity;

/**
 * Created by Kuhta on 04.02.2015.
 */
public class ActivityExtras extends ActivityAnnotation {
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
	@ViewId(id = R.id.txtChar)
	private TextView tvChar;
	@ViewId(id = R.id.txtShort)
	private TextView tvShort;
	@ViewId(id = R.id.txtLong)
	private TextView tvLong;
	@ViewId(id = R.id.txtByte)
	private TextView tvByte;
	@ViewId(id = R.id.txtFloat)
	private TextView tvFloat;
	@ViewId(id = R.id.txtDouble)
	private TextView tvDouble;
	@ViewId(id = R.id.txtCharArray)
	private TextView tvCharArray;
	@ViewId(id = R.id.txtIntArray)
	private TextView tvIntArray;
	@ViewId(id = R.id.txtShortArray)
	private TextView tvShortArray;
	@ViewId(id = R.id.txtLongArray)
	private TextView tvLongArray;
	@ViewId(id = R.id.txtByteArray)
	private TextView tvByteArray;
	@ViewId(id = R.id.txtFloatArray)
	private TextView tvFloatArray;
	@ViewId(id = R.id.txtDoubleArray)
	private TextView tvDoubleArray;
	@ViewId(id = R.id.txtBooleanArray)
	private TextView tvBooleanArray;
	@ViewId(id = R.id.txtBoolean)
	private TextView tvBoolean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extras);

		initView();
	}

	private void initView() {
		tvNumber.setText(String.valueOf(number));
		tvString.setText(str);

		if(entity != null) {
			tvEntityNumber.setText(String.valueOf(entity.getX()));
			tvChar.setText(String.valueOf(entity.getChar()));
			tvShort.setText(String.valueOf(entity.getShort()));
			tvLong.setText(String.valueOf(entity.getLong()));
			tvByte.setText(String.valueOf(entity.getByte()));
			tvFloat.setText(String.valueOf(entity.getFloat()));
			tvDouble.setText(String.valueOf(entity.getDouble()));
			tvBoolean.setText(String.valueOf(entity.getBool()));

			if(!TextUtils.isEmpty(entity.str)) {
				tvEntityString.setText(entity.str);
			}else{
				tvEntityString.setText("null");
			}

			if(entity.getArray() != null) {
				StringBuilder phrase = new StringBuilder();
				for (String word : entity.getArray()) {
					composePhrase(phrase, String.valueOf(word));
				}

				phrase.append("]");

				tvArrayString.setText(phrase);
			}

			if(entity.getChars() != null) {
				StringBuilder phrase = new StringBuilder();
				for (char c : entity.getChars()) {
					composePhrase(phrase, String.valueOf(c));
				}

				phrase.append("]");

				tvCharArray.setText(phrase);
			}

			if(entity.getIntegers() != null) {
				StringBuilder phrase = new StringBuilder();
				for (int c : entity.getIntegers()) {
					composePhrase(phrase, String.valueOf(c));
				}

				phrase.append("]");

				tvIntArray.setText(phrase);
			}

			if(entity.getShorts() != null) {
				StringBuilder phrase = new StringBuilder();
				for (short c : entity.getShorts()) {
					composePhrase(phrase, String.valueOf(c));
				}

				phrase.append("]");

				tvShortArray.setText(phrase);
			}

			if(entity.getLongs() != null) {
				StringBuilder phrase = new StringBuilder();
				for (long c : entity.getLongs()) {
					composePhrase(phrase, String.valueOf(c));
				}

				phrase.append("]");

				tvLongArray.setText(phrase);
			}

			if(entity.getBytes() != null) {
				StringBuilder phrase = new StringBuilder();
				for (byte c : entity.getBytes()) {
					composePhrase(phrase, String.valueOf(c));
				}

				phrase.append("]");

				tvByteArray.setText(phrase);
			}

			if(entity.getFloats() != null) {
				StringBuilder phrase = new StringBuilder();
				for (float c : entity.getFloats()) {
					composePhrase(phrase, String.valueOf(c));
				}

				phrase.append("]");

				tvFloatArray.setText(phrase);
			}

			if(entity.getDoubles() != null) {
				StringBuilder phrase = new StringBuilder();
				for (double c : entity.getDoubles()) {
					composePhrase(phrase, String.valueOf(c));
				}

				phrase.append("]");

				tvDoubleArray.setText(phrase);
			}

			if(entity.getBooleans() != null) {
				StringBuilder phrase = new StringBuilder();
				for (boolean c : entity.getBooleans()) {
					composePhrase(phrase, String.valueOf(c));
				}

				phrase.append("]");

				tvBooleanArray.setText(phrase);
			}

			try {
				tvInnerString.setText(entity.getInnerString());
			}catch (NullPointerException e){
				e.printStackTrace();
				tvInnerString.setText("null");
			}

			try {
				tvInnerStaticString.setText(entity.getInnerStaticString());
			}catch (NullPointerException e){
				e.printStackTrace();
				tvInnerStaticString.setText("null");
			}
		}
	}

	private void composePhrase(StringBuilder phrase, String value){
		if (phrase.length() != 0)
			phrase.append(", ");
		else {
			phrase.append("[");
		}

		phrase.append(value);
	}
}
