package com.ls.annotations.annotation.data;

import com.ls.annotations.annotation.parcel.ParcelHandler;

/**
 * Created by Kuhta on 05.02.2015.
 */
public class Entity extends ParcelHandler {
	public String str;
	private int x;

	public void setX(int x){
		this.x = x;
	}

	public int getX(){
		return x;
	}
}
