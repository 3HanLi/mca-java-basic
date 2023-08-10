package com.wy.mca.io.util;

import java.util.concurrent.TimeUnit;

public class TimeUnitUtil {
	
	public static void sleepSeconds(long time){
		try {
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void sleepMillions(long millions){
		try {
			TimeUnit.MILLISECONDS.sleep(millions);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void sleepMinutes(long minutes){
		try {
			TimeUnit.MINUTES.sleep(minutes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
