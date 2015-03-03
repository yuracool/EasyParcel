package com.yuracool.data;

import com.yuracool.easyparcel.EasyParcel;

/**
 * Created by Kuhta on 03.03.2015.
 */
public class EasyParcelTest extends EasyParcel {
	Test[] test;

	public void init(){
		test = new Test[2];

		test[0] = new Test();
		test[0].str = "Parcel";
		test[0].i = -256;

		test[1] = new Test();
		test[1].str = "Parcel2";
		test[1].i = -255;
	}

	private static class Test extends EasyParcel{
		String str;
		int i;
	}
}
