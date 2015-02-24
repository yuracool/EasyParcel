package com.yuzeeeer.annotations.data;

import com.yuzeeeer.easyparcel.EasyParcel;

/**
 * Created by Kuhta on 05.02.2015.
 */
public class Entity extends EasyParcel {
	public String str;
	private int x;
	char cr = 'ї';
	short sh = (short) 6500;

	public void setX(int x){
		this.x = x;
	}

	public int getX(){
		return x;
	}

	public char getChar(){
		return cr;
	}

	public short getShort(){
		return sh;
	}
}
