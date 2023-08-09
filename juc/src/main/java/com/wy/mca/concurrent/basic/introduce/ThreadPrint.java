package com.wy.mca.concurrent.basic.introduce;


import com.wy.mca.concurrent.util.TimeUnitUtil;

public class ThreadPrint {

	public static void main(String[] args) {
		
		new Thread(()->{
			while(true){
				System.out.println("ThreadA");
				TimeUnitUtil.sleepMillions(200);
			}
		},"threadA").start();
		TimeUnitUtil.sleepSeconds(10);
	}
	
	
}
