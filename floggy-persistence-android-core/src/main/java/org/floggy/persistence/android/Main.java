package org.floggy.persistence.android;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new Main().getGetterMethodName("name"));

	}
	
	protected String getGetterMethodName(String fieldName) {
		char ch = fieldName.charAt(0);
		ch = Character.toUpperCase(ch);
		return ch + fieldName.substring(1);
	}


}
