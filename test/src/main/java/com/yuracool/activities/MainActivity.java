package com.yuracool.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.yuracool.annotation.ActivityAnnotation;
import com.yuracool.annotation.OnClick;
import com.yuracool.annotation.ViewId;
import com.yuracool.annotations.R;
import com.yuracool.data.EasyParcelEntity;
import com.yuracool.data.JsonSerializableEntity;
import com.yuracool.data.ParcelableEntity;
import com.yuracool.data.SerializableEntity;
import com.yuracool.utils.JsonSerializable;

/**
 * Created by Kuhta on 03.02.2015.
 */
public class MainActivity extends ActivityAnnotation {
    private static final int ENTITIES_COUNT = 6;

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
				long time = System.currentTimeMillis();

//				String[] entities = new String[ENTITIES_COUNT];
//				for(int i=0; i<ENTITIES_COUNT; i++){
//					JsonSerializableEntity entity = new JsonSerializableEntity();
//					entity.init();
//					entities[i] = entity.toJson();
//				}

//				EasyParcelEntity[] entities = new EasyParcelEntity[ENTITIES_COUNT];
//                for(int i=0; i<ENTITIES_COUNT; i++){
//                    entities[i] = new EasyParcelEntity();
//                    entities[i].init();
//                }

//				ParcelableEntity[] entities = new ParcelableEntity[ENTITIES_COUNT];
//				for(int i=0; i<ENTITIES_COUNT; i++){
//					entities[i] = new ParcelableEntity();
//					entities[i].init();
//				}

				SerializableEntity[] entities = new SerializableEntity[ENTITIES_COUNT];
				for(int i=0; i<ENTITIES_COUNT; i++){
					entities[i] = new SerializableEntity();
					entities[i].init();
				}

				ActivityExtras.startThisActivity(this, entities, time);
                break;
		}
	}
}
