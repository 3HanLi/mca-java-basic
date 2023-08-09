package com.wy.mca.concurrent.basic.method;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Thread.join方法详解:如果在线程A中执行了thread.join()意味着线程A需要等待thread执行完毕才能继续执行，
 * 也就是join优先执行并阻塞当前线程；
 * 
 * @author wangyong
 * @date 2018年11月23日 下午3:22:38
 */
public class ThreadJoin {

	private static DateFormat format = new SimpleDateFormat("HH:mm:ss");

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Time start:" + format.format(new Date()));

		JoinTask task = new JoinTask();
		Thread thread = new Thread(task, "Thread01");
		Thread thread2 = new Thread(task, "Thread02");
		
		thread.start();
		thread2.start();

		//main线程需要等待thread和thread02执行完毕才能继续执行
		thread.join();
		thread2.join();

		System.out.println("Time end:" + format.format(new Date()));
	}

	private static class JoinTask implements Runnable {

		@Override
		public void run() {
			synchronized (JoinTask.class) {
				try {
					System.out.println(Thread.currentThread().getName() + " Entering..");
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
