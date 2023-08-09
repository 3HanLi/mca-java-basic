package com.wy.mca.concurrent.basic.start;

import java.util.concurrent.TimeUnit;

/**
 * 优雅的终止线程：
 * 	1	通过interrupt改变线程状态，通过指定逻辑终止线程
 * 	2	自定义逻辑终止线程，如：定义控制run方法是否运行的开关 private boolean volatile on = true;
 * 	3	总结：优雅的终止线程需要人为干预，不建议暴力终止；线程终止的标志是：执行run方法完毕
 * 
 * @author wangyong
 * @date 2018年11月22日 下午4:48:17
 */
public class ThreadStopGentle {
	
	public static void main(String[] args) throws InterruptedException {
		//1	终止线程：通过interrupt改变线程状态，然后使用指定逻辑终止线程
		GentleTask interuptTask = new GentleTask();
		Thread interuptThread = new Thread(interuptTask,"InteruptThread");
		interuptThread.start();
		TimeUnit.SECONDS.sleep(1);
		interuptThread.interrupt();
		
		//2	终止线程：自定义逻辑
		GentleTask cancelTask = new GentleTask();
		Thread cancelThread = new Thread(cancelTask,"CancelThread");
		cancelThread.start();
		TimeUnit.SECONDS.sleep(1);
		cancelTask.cancel();
	}

	private static class GentleTask implements Runnable{

		//2	自定义开关控制线程何时终止
		private volatile boolean on = true;
		
		private int count;
		
		@Override
		public void run() {
			while(on && !Thread.currentThread().isInterrupted()){
				count ++;
			}
			System.out.println("count = " + count);
		}
		
		public void cancel(){
			on = false;
		}
	}
}
