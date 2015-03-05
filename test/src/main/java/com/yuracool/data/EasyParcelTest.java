package com.yuracool.data;

import com.yuracool.easyparcel.EasyParcel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kuhta on 03.03.2015.
 */
public class EasyParcelTest extends EasyParcel {
	Test test;
	Map<String, Test.Test2> map;

	public void init(){
		test = new Test();
		test.str = "Parcel";
		test.i = -256;

		Test.Test2 t1 = new Test.Test2();
		t1.val = 1000;

		Test.Test2 t2 = new Test.Test2();
		t2.val = -1000;

		map = new HashMap<>();
		map.put("item1", t1);
		map.put("item2", t2);
	}

	private static class Test extends EasyParcel{
		String str;
		int i;

		private static class Test2 extends EasyParcel{
			long val;
		}
	}
}