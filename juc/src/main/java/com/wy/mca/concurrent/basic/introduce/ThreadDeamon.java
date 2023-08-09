package com.wy.mca.concurrent.basic.introduce;


import com.wy.mca.concurrent.util.TimeUnitUtil;

/**
 * Deamon线程详解：
 * 
 * 线程分类：Java支持两种线程："用户线程"和"守护线程","用户线程"就是我们创建的普通线程,而"守护线程"就是Deamon线程;
 * 如何区分：当线程只剩下守护线程的时候，JVM就会退出.但是如果还有其他的任意一个用户线程还在，JVM就不会退出
 * 
 * 作用：主要被用作程序中后台调度，一般不需要用户关心；在程序中没有"用户线程"后,Deamon线程退出；
 * 1) Daemon线程产生的线程也是Daemon线程
 * 2) Daemon线程的优先级很低，JVM垃圾回收的线程就是Daemon线程，当系统运行的时候会不断的产生垃圾，JVM垃圾回收线程就定时清理垃圾；当程序退出时，
 * 	    便不会产生垃圾，垃圾回收线程也自动终止
 * 
 * 使用：Deamon属性需要在启动线程之前设置;
 * 
 * @author WangyongR
 * @date 2016-12-12 下午4:57:48
 */
public class ThreadDeamon {

	/**
	 * 分析：这里程序运行之后，会启动两个线程：
	 *  程序------------
	 *   |		       	|
	 *   |			   	|
	 *   |            	|
	 * main线程	      Deamon线程
	 * 
	 * 当main线程执行完之后，JVM中不存在"用户线程"就会退出，那么Deamon线程就可能来不及执行，会导致code1和code2不会执行
	 * 
	 */
	public static void main(String[] args) {
		Thread thread = new Thread(new Deamon(), "Thread-Deamon");
		thread.setDaemon(true);//设置线程为"守护线程"
		thread.start();
	}
	
	static class Deamon implements Runnable{

		@Override
		public void run() {
			try{
				TimeUnitUtil.sleepSeconds(10);
				System.out.println("Thread-->Deamon");//不会执行:code1，此时main方法已经执行完毕，该线程会被“回收”掉；
			}finally{
				System.out.println("Thread-->Ended");//不会执行:code2
			}
		}
		
	}
}
