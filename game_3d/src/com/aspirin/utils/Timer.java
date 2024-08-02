package com.aspirin.utils;

public class Timer {
	public static final long SECOND=1000000000l;
	public static long getNow() {
		return System.nanoTime();
	}
}
