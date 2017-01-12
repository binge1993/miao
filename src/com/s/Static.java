package com.s;

public class Static {
	public static long shicha = 0;
	public static long currentTimeMillis(){
		return System.currentTimeMillis()+shicha;
	}
}