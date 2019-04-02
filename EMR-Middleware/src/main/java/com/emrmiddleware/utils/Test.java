package com.emrmiddleware.utils;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test test = new Test();
      test.testHashing();
	}
	
	private void testHashing(){
		String hashedpassword = null;
		hashedpassword=EmrUtils.get_SHA_512_SecurePassword("Susan123", "7aa91458e25d7314f4549b82c049d3d162bac00c216c7c0484e00df345ef8f3dd997d185bb8f61035caa3a7a4d50995eb9678d971d557cef3447d0a8012ed8f4");
		System.out.println(hashedpassword);
	}

}
