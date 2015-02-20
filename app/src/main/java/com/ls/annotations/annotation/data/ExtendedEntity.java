package com.ls.annotations.annotation.data;

import com.ls.annotations.annotation.parcel.ParcelHandler;

/**
 * Created by Kuhta on 05.02.2015.
 */
public class ExtendedEntity extends Entity {
	private String[] array;
	private TestInnerClass.SuperInner superInner;
	private TestStaticClass testStaticClass;

	private ExtendedEntity(){}

	public ExtendedEntity(String[] array, String txtMember, String txtStatic){
		this.array = array;

		superInner = new TestInnerClass.SuperInner();
		superInner.str = txtMember;

		testStaticClass = new TestStaticClass();
		testStaticClass.str = txtStatic;
	}

	public String[] getArray(){
		return array;
	}

	public String getInnerString(){
		return superInner.str;
	}

	public String getInnerStaticString() {
		return testStaticClass.str;
	}

	private static class TestInnerClass extends ParcelHandler{
		public static class SuperInner extends ParcelHandler{
			private String str;
		}
	}

	private static class TestStaticClass extends ParcelHandler{
		private String str;
	}
}
