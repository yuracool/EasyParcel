package com.ls.annotations;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.ls.annotations.annotation.acyivity.ActivityAnnotation;
import com.ls.annotations.annotation.acyivity.OnClick;
import com.ls.annotations.annotation.acyivity.ViewId;
import com.ls.annotations.annotation.data.Entity;
import com.ls.annotations.annotation.data.ExtendedEntity;

/**
 * Created by Kuhta on 03.02.2015.
 */
public class MainActivity extends ActivityAnnotation {
	@OnClick
	@ViewId(id = R.id.btn)
	private Button btn;
	@OnClick
	@ViewId(id = R.id.btnRun)
	private Button btnRun;

	@ViewId(id = R.id.txt)
	private TextView txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();

		switch (id){
			case R.id.btn:
				txt.setGravity(Gravity.CENTER); break;
			case R.id.btnRun:
				ExtendedEntity entity = new ExtendedEntity(new String[]{"test", "String", "array", "parcel"}, "Inner class!!!", "Inner static class");
				entity.setX(-1500);
				entity.str = "Hello fom parcel";

				ActivityExtras.startThisActivity(this, 159, "Hello Annotation!!!", entity); break;
		}
	}
}
