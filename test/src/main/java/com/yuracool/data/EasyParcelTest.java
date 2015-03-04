package com.yuracool.data;

import android.os.Parcelable;
import com.yuracool.easyparcel.EasyParcel;

/**
 * Created by Kuhta on 03.03.2015.
 */
public class EasyParcelTest extends EasyParcel {
	Test test;

	public void init(){
		test = new Test();
		test.str = "Parcel";
		test.i = -256;
		test.test2 = test.new Test2();
		test.test2.val = 100L;
	}

	private static class Test extends EasyParcel{
		String str;
		int i;
		Test2 test2;

		private class Test2 extends EasyParcel{
			long val;
		}
	}
}
