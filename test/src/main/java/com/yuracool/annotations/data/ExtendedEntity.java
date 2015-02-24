package com.yuracool.annotations.data;

import com.yuracool.easyparcel.EasyParcel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kuhta on 05.02.2015.
 */
public class ExtendedEntity extends Entity {
	private static String[] array;
	private TestStaticClass testStaticClass;
	private TestArrays arrays;
	private List<String> stringList = new ArrayList<>();
	private List<Long> longList = new LinkedList<>();

	private ExtendedEntity(){}

	public ExtendedEntity(String[] array, String txtMember, String txtStatic){
		this.array = array;

		testStaticClass = new TestStaticClass();
		testStaticClass.str = txtStatic;
		testStaticClass.lg = 10000000000000L;
		testStaticClass.b = -20;
		testStaticClass.f = 156.123f;
		testStaticClass.d = 15948798.123456798;

		stringList.add("List");
		stringList.add("String");
		stringList.add("ArrayList");
		stringList.add("txtStatic");

		longList.add(13456L);
		longList.add(13457L);
		longList.add(13458L);
		longList.add(13459L);

		arrays = new TestArrays(txtMember, true);
		arrays.booleans = new boolean[] {true, true, true, false};
		arrays.bytes = new Byte[]{-5, 10, 127, -100};
		arrays.shorts = new short[]{1000, 1678, -3584, 0};
		arrays.chars = new char[]{'Ю', 'р', 'а', '↓', '♥'};
		arrays.integers = new int[]{9867345, -897634, 734, 0};
		arrays.longs = new Long[]{837645983726939847L, -94386586483845834L};
		arrays.floats = new float[]{876.9845f, -9084.6235f, 0.00456f};
		arrays.doubles = new Double[]{83209384.897348957, 897983457.09280390, -1234567890.0987654321};
	}

	public String[] getArray(){
		return array;
	}

	public String getInnerString(){
		return arrays.superInner.str;
	}

	public String getInnerStaticString() {
		return testStaticClass.str;
	}

	public long getLong(){
		return testStaticClass.lg;
	}

	public byte getByte(){
		return testStaticClass.b;
	}

	public float getFloat(){
		return testStaticClass.f;
	}

	public double getDouble(){
		return testStaticClass.d;
	}

	public Byte[] getBytes(){
		return arrays.bytes;
	}

	public int[] getIntegers(){
		return arrays.integers;
	}

	public Long[] getLongs(){
		return arrays.longs;
	}

	public float[] getFloats(){
		return arrays.floats;
	}

	public Double[] getDoubles(){
		return arrays.doubles;
	}

	public char[] getChars(){
		return arrays.chars;
	}

	public boolean[] getBooleans(){
		return arrays.booleans;
	}

	public short[] getShorts(){
		return arrays.shorts;
	}

	public boolean getBool(){
		return arrays.superInner.bool;
	}

	private static class TestArrays extends EasyParcel {
		private Byte[] bytes;
		private short[] shorts;
		private char[] chars;
		private int[] integers;
		private Long[] longs;
		private float[] floats;
		private Double[] doubles;
		private boolean[] booleans;
		private SuperInner superInner;

		private TestArrays(){}

		private TestArrays(String str, boolean bool){
			superInner = new SuperInner();
			superInner.str = str;
			superInner.bool = bool;
		}

		public static class SuperInner extends EasyParcel{
			private String str;
			private boolean bool = false;
		}
	}

	private static class TestStaticClass extends EasyParcel{
		private String str;
		private long lg;
		private byte b;
		private float f;
		private double d;
	}
}
