package com.yuracool.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import com.yuracool.annotation.ActivityAnnotation;
import com.yuracool.annotation.Extras;
import com.yuracool.annotation.ViewId;
import com.yuracool.annotations.R;
import com.yuracool.data.EasyParcelEntity;
import com.yuracool.data.JsonSerializableEntity;
import com.yuracool.data.ParcelableEntity;
import com.yuracool.data.SerializableEntity;
import com.yuracool.utils.JsonSerializable;

import java.io.Serializable;

/**
 * Created by Kuhta on 04.02.2015.
 */
public class ActivityExtras extends ActivityAnnotation {
	public static final String ENTITY_EXTRA = "ENTITY_EXTRA";
    public static final String TIME_EXTRA = "TIME_EXTRA";

    public static void startThisActivity(Activity activity, Parcelable[] entity, long time){
        Intent intent = new Intent(activity, ActivityExtras.class);
        intent.putExtra(ENTITY_EXTRA, entity);
        intent.putExtra(TIME_EXTRA, time);

        activity.startActivity(intent);
    }

    public static void startThisActivity(Activity activity, Serializable[] entity, long time){
        Intent intent = new Intent(activity, ActivityExtras.class);
        intent.putExtra(ENTITY_EXTRA, entity);
        intent.putExtra(TIME_EXTRA, time);

        activity.startActivity(intent);
    }

    public static void startThisActivity(Activity activity, String[] entity, long time){
        Intent intent = new Intent(activity, ActivityExtras.class);
        intent.putExtra(ENTITY_EXTRA, entity);
        intent.putExtra(TIME_EXTRA, time);

        activity.startActivity(intent);
    }

    public static void startThisActivity(Activity activity, long time){
        Intent intent = new Intent(activity, ActivityExtras.class);
        intent.putExtra(TIME_EXTRA, time);

        activity.startActivity(intent);
    }

//    @Extras(key = ENTITY_EXTRA)
//    private String[] entities;

//    @Extras(key = ENTITY_EXTRA)
//    private Parcelable[] entities;

//    @Extras(key = ENTITY_EXTRA)
//    private Object[] entities;

    @Extras(key = TIME_EXTRA)
    private long time;

    @ViewId(id = R.id.txt)
    private TextView txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extras);

		initView();
	}

	private void initView() {
//		JsonSerializableEntity[] entities = new JsonSerializableEntity[this.entities.length];
//        for(int i=0; i<entities.length; i++){
//            entities[i] = JsonSerializable.fromJson(JsonSerializableEntity.class, this.entities[i]);
//        }

//		EasyParcelEntity[] entities = new EasyParcelEntity[this.entities.length];
//		for (int i=0; i<entities.length; i++){
//			entities[i] = (EasyParcelEntity) this.entities[i];
//		}

//		ParcelableEntity[] entities = new ParcelableEntity[this.entities.length];
//		for (int i=0; i<entities.length; i++){
//			entities[i] = (ParcelableEntity) this.entities[i];
//		}

//		SerializableEntity[] entities = new SerializableEntity[this.entities.length];
//		for (int i=0; i<entities.length; i++){
//			entities[i] = (SerializableEntity) this.entities[i];
//		}

//      if(entities != null) {
//			txt.setText("Success!!!\n Time = " + String.valueOf(System.currentTimeMillis() - time));
//		}else{
//			txt.setText("Error!!!\n Time = " + String.valueOf(System.currentTimeMillis() - time));
//		}

        txt.setText("Success!!!\n Time = " + String.valueOf(System.currentTimeMillis() - time));
	}
}
