package com.wy.mca.concurrent.basic.start;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 1	终止线程(不建议使用，了解即可)，常用的方法如下
 * 		1.1	suspend：暂停，在调用后，线程不会释放已经占有的资源（比如锁），而是占有着资源进入睡眠状态，这样容易引发死锁问题
 * 		1.2	resume：恢复
 * 		1.3	stop：终止线程，在终结一个线程时不保证线程的资源正常释放，通常是没有给予线程完成资源释放工作的机会，因此会导致程序可能工作在不确定状态下
 * 			stop不能使用的原因：加入当前线程t开辟了子线程t1，那么突然执行t.stop会立即释放子线程t1的锁，就可能会导致t1锁定的数据出现线程不安全的情况
 * 
 * 2	总结：以上方法不建议使用，了解即可，建议采用其他方式终止线程；@See 	ThreadStopGentle
 * 
 * @author wangyong
 * @date 2018年11月22日 下午4:48:36
 */
public class ThreadStopUnrecommend {

	private static DateFormat format = new SimpleDateFormat("HH:mm:ss");

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new Task());
		thread.start();
		System.out.println("Thread start ");
		// 1 运行3秒
		TimeUnit.SECONDS.sleep(3);
		// 2 暂停三秒
		System.out.println("Thread Suspend for 3 seconds");
		thread.suspend();
		TimeUnit.SECONDS.sleep(3);
		// 3 重新运行3秒
		System.out.println("Thread Resume and run for 3 seconds");
		thread.resume();
		TimeUnit.SECONDS.sleep(3);
		// 4 终止线程
		thread.stop();
		System.out.println("Thread stop...");
	}

	private static class Task implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					System.out.println("Run at:" + format.format(new Date()));
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
