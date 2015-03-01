package com.yuracool.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

public abstract class JsonSerializable {

    public String toJson() {
        return createBasicJson();
    }

    public JSONObject toJsonObject() {
        try {
            return new JSONObject(createBasicJson());
        } catch (JSONException e) {
            Log.e("toJsonObject", e.getMessage(), e);
        }

        return null;
    }

    private String createBasicJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);

        return json;
    }

    @Nullable
    public static <T> T fromJson(@NonNull Class<T> classOfT, @Nullable String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        String jsonInner = json;

        T result = null;

        Gson gson = new Gson();

        try {
            result = gson.fromJson(jsonInner, classOfT);
        } catch (Exception e) {
            Log.d("fromJson", e.getMessage(), e);

            //json string can include extra BOM symbol, we trying to exclude this symbol

			//get starting position of json object
			int jBeginPosition = jsonInner.indexOf("{");

			if (jBeginPosition >= 0) {
				jsonInner = jsonInner.substring(Math.min(jBeginPosition, jsonInner.length()));
				try {
					result = gson.fromJson(jsonInner, classOfT);
				} catch (Exception eIn) {
					Log.d("fromJson", e.getMessage(), eIn);
				}
			}
		}

        return result;
    }
}
