package com.wy.mca.concurrent.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *1	CountDownLatch详解：允许一个或多个线程等待其他线程完成操作
 * 	1.1	CountDownLatch的构造函数接收一个int类型的参数作为计数器，如果你想等待N个点完成，这里就传入N。
 * 	1.2	当我们调用CountDownLatch的countDown方法时，N就会减1，CountDownLatch的await方法会阻塞当前线程，直到N变成零。
 * 	1.3	由于countDown方法可以用在任何地方，所以这里说的N个点，可以是N个线程，也可以是1个线程里的N个执行步骤。
 *2	使用：
 *	2.1	构造函数：CountDownLatch countDownLatch = new CountDownLatch(10);
 *	2.2	计数：countDownLatch.countDown();
 *	2.3	阻塞当前线程直到countDown所在的线程【或者任务】计数器为零:
 *		countDownLatch.await()
 *		await（long time，TimeUnit unit），这个方法等待特定时间后，就会不再阻塞当前线程
 *3	也可以使用join来替代countDown
 *4 总结
 *  4.1 CountDownLatch是一个计数器，相当于做减法，当计数器减到零才能执行主线程
 *  4.2 CyclicBarrier也是一个计数器，是做加法，当线程数量累加到指定值时，才执行主线程
 *  4.3 Semaphore作用是控制同时最多有几个执行任务，当其中一个线程执行完任务后，释放资源，其他线程立马抢占
 * @author wangyong
 * @date 2018年11月20日 下午2:18:53
 */
public class CountDownLatchClient {
	
	public static void main(String[] args) throws InterruptedException {
		threadJoin();
		countDownLatch();
	}

	private static void threadJoin() throws InterruptedException{
		Thread[] threads = new Thread[10];
		CountDownTask countDownTask = new CountDownTask();
		for (int i=0; i<threads.length; i++){
			threads[i] = new Thread(countDownTask);
		}

		for (int i=0; i<threads.length; i++){
			threads[i].start();
		}

		for (int i=0; i<threads.length; i++){
			threads[i].join();
		}

		System.out.println("Thread-Join sum -- >" + countDownTask.sum.get());
	}

	/**
	 * 使用CountdownLatch递减
	 * @throws InterruptedException
	 */
	private static void countDownLatch() throws InterruptedException{
		CountDownLatch countDownLatch = new CountDownLatch(10);
		CountDownTask countDownTask = new CountDownTask(countDownLatch);
		for(int i=0; i<10; i++){
			new Thread(countDownTask).start();
		}
		//3	等待线程执行完
		countDownLatch.await();
		System.out.println("Thread-CountdownLatch sum -- >" + countDownTask.sum.get());
	}

	/**
	 * 任务
	 */
	public static class CountDownTask implements Runnable{

		private CountDownLatch countDownLatch;
		
		private AtomicInteger sum = new AtomicInteger();

		public CountDownTask() {

		}

		public CountDownTask(CountDownLatch countDownLatch) {
			super();
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			for(int i=1; i<=100; i++){
				sum.addAndGet(i);
			}
			//2	计数--倒计时
			if (null != countDownLatch){
				countDownLatch.countDown();
			}
		}
		
	}
}
