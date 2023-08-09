package com.mca.concurrent.basic.sync;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 	等待-通知 详解
 * 	1	等待通知机制指的是线程A调用了对象O的wait方法而进入等待状态，线程B调用了O的notify方法来唤醒线程A，线程A收到通知后从方法返回继续执行wait方法后面的代码
 * 	2	wait和notify方法的使用：
 * 		2.1	wait和notify方法在调用前必须先获取对象的锁，这是Jdk强制要求的
 * 		2.2	wait和notify在放弃对象监视器的时候区别：
 * 				a)	wait方法立即释放监视器
 * 				b)	notify方法会等待线程执行完剩余代码在放弃对象监视器
 * 				c)	从wait方法返回的前提是：等待线程重新获取锁
 * 	3	等待/通知 经典范式
 * 		3.1	等待方遵循的规则，代码demo：
 * 
 * 		synchronized(对象) {
 *				while(条件不满足) {
 *					对象.wait();
 *				}
 *				对应的处理逻辑
 *		}
 * 
 * 		3.2	通知方遵循规则，代码demo：
 * 
 * 		synchronized(对象) {
 *		    改变条件
 *		    对象.notifyAll();
 *		}
 *	4	wait和sleep的区别
 *		4.1	sleep是Thread的方法，而wait是Object的方法
 *		4.2	sleep会阻塞当前线程，并持有锁对象，而wait方法会释放锁
 * 
 * @author wangyong
 * @date 2018年11月23日 下午2:53:51
 */
public class WaitNotifyClient {

	private static final Object lock = new Object();

	private static volatile boolean on = true;

	private static DateFormat format = new SimpleDateFormat("HH:mm:ss");

	public static void main(String[] args) throws InterruptedException {
		new Thread(new WaitTask()).start();
		TimeUnit.SECONDS.sleep(1);
		new Thread(new NotifyTask()).start();
	}

	private static class WaitTask implements Runnable {

		@Override
		public void run() {
			synchronized (lock) {
				while (on) {
					System.out.println("Enter waiting:" + format.format(new Date()));
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Leave waiting:" + format.format(new Date()));
			}
		}
	}

	private static class NotifyTask implements Runnable {

		@Override
		public void run() {
			synchronized (lock) {
				try {
					lock.notify();
					TimeUnit.SECONDS.sleep(3);
					System.out.println("Notify after 3 seconds:" + format.format(new Date()));
					on = false;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			synchronized (lock) {
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Notify reLock after 3 seconds:" + format.format(new Date()));
			}
		}

	}
}
