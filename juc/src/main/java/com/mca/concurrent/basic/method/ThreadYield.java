package com.mca.concurrent.basic.method;

import com.wy.mac.java.concurrent.util.TimeUnitUtil;

/**
 * Thread.yield：线程让步
 * 就是说当一个线程使用了这个方法之后，它就会把自己CPU执行的时间让掉，让自己或者其它的线程运行，注意是让自己或者其他线程运行，并不是单纯的让给其他线程
 * @author 王勇
 * @date 2020年3月7日 下午6:04:49
 */
public class ThreadYield {

	public static void main(String[] args) {
		new Thread(()->{
			for(int i=1; i<=10; i++) {
				System.out.println(Thread.currentThread().getName() + " print -->" + i);
				TimeUnitUtil.sleepMillions(10);
				Thread.currentThread().yield();
			}
		},"ThreadA").start();
		
		new Thread(()->{
			for(int i=1; i<=10; i++) {
				System.out.println(Thread.currentThread().getName() + " print -->" + i);
				TimeUnitUtil.sleepMillions(10);
				Thread.currentThread().yield();
			}
		},"ThreadB").start();
	}
}
