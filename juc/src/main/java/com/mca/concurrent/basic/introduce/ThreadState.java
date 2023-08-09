package com.mca.concurrent.basic.introduce;


import com.mca.concurrent.util.TimeUnitUtil;

/**
 * @Description: 线程状态详解:
 * 
 * 线程在运行的生命周期中有6中状态如下：
 * 1) New：初始状态，线程被创建,未调用start
 * 
 * 2) Running：运行状态,
 * 3) Blocked：多个线程同时争夺一块资源，导致阻塞
 * 4) Waiting：线程进入等待状态,直到其他线程唤醒该线程
 * 	
 * 5) Time_Waiting：等待超时,注意和Waiting区分,可以在指定时间内自行返回
 * 
 * 6) Terminated：线程终止，执行完成;
 * 
 * start和run的区别：
 * 1) 线程通过start来启动线程，此时线程处于就绪状态，并没有运行
 * 2) run方法是线程的执行体，当线程通过start方法启动后执行run方法时，线程才处于运行状态	
 * 
 * Extend Knowledge;
 * Jdk自带命令行工具：
 * 1) jps
 * 2) jstack
 * 
 * 3) jmap
 * 4) jhat
 * 
 * 5) jinfo
 * 6) jstat
 * 
 * 使用命令工具Jps和Jstack分析线程状态：
 * 1) 使用jps -l命令查看运行中的java进程pid
 * 2) 使用jstack pid查看Java程序执行情况，查看线程状态
 * 
 * @author WangyongR
 * @date 2016-12-12 下午4:37:42
 */
public class ThreadState {

	public static void main(String[] args) {
		// Thread1：Running
		new Thread(new Sleep(), "sleep-thread").start();

		// Thread2：Waiting
		Thread waitThread = new Thread(new Waiting(), "waiting-thread");
		System.out.println(waitThread.getName() + "-->Thread-state-->" + waitThread.getState());
		waitThread.start();
		System.out.println(waitThread.getName() + "-->Thread-state-->" + waitThread.getState());

		// Thread3和Thread4一个获取到锁，另一个被阻塞
		Thread blockThread01 = new Thread(new Block(), "block-thread-01");
		blockThread01.start();
		System.out.println(blockThread01.getName() + "-->Thread-state-->" + blockThread01.getState());

		Thread blockThread02 = new Thread(new Block(), "block-thread-02");
		blockThread02.start();
		System.out.println(blockThread02.getName() + "-->Thread-state-->" + blockThread02.getState());
	}

	/**
	 * 线程：睡眠
	 */
	static class Sleep implements Runnable {
		@Override
		public void run() {
			Thread currentThread = Thread.currentThread();

			while (true) {
				System.out.println(currentThread.getName() + "-->Thread-state-->" + currentThread.getState());
				TimeUnitUtil.sleepSeconds(1);
			}
		}
	}

	/**
	 * 线程：等待
	 * 
	 * 关于Object的wait/notify/notifyAll使用规范:
	 * 
	 * 1)永远在synchronized的函数或对象里使用wait、notify和notifyAll，不然Java虚拟机会生成 IllegalMonitorStateException
	 * 2)永远在while循环里而不是if语句下使用wait.这样,循环会在线程睡眠前后都检查wait的条件，并在条件实际上并未改变的情况下处理唤醒通知
	 * 3)永远在多线程间共享的对象（在生产者消费者模型里即缓冲区队列）上使用wait
	 * 4)建议使用notifyAll而不是notify
	 * 
	 * 	@see Object.wait
	 *  As in the one argument version, interrupts and spurious wakeups are
     * 	possible, and this method should always be used in a loop:
     * 	<pre>
     *     synchronized (obj) {
     *         while (loop){
     *             obj.wait();
     *         }
     *     }
     * 	</pre>
	 * 
	 */
	static class Waiting implements Runnable {

		@Override
		public void run() {
			synchronized (Waiting.class) {
				while (true) {
					try {
						Waiting.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * 线程：阻塞
	 */
	static class Block implements Runnable {

		@Override
		public void run() {
			synchronized (Block.class) {
				while (true) {
					TimeUnitUtil.sleepSeconds(1);
				}
			}
		}

	}
}
