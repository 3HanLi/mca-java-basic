package com.mca.concurrent.lock.reentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LockAPI测试
 * @author wangyong
 * @date 2018年11月5日 上午10:33:31
 */
public class LockClient {

	public static void main(String[] args) throws Exception{
		LockClient lockClient = new LockClient();
//		lockClient.lock();
		lockClient.tryLock();
	}

	/**
	 * 获取锁：替代synchronized进行加锁
	 * @throws InterruptedException 
	 */
	public void lock() throws InterruptedException{
		ShareLockTask shareLockTask = new ShareLockTask();
		Thread thread01 = new Thread(shareLockTask,"Thread-01");
		Thread thread02 = new Thread(shareLockTask,"Thread-02");
		Thread thread03 = new Thread(shareLockTask,"Thread-03");
		
		thread01.start();
		thread02.start();
		thread03.start();
		
		thread01.join();
		thread02.join();
		thread03.join();
		
		System.out.println(shareLockTask.getCount());
	}
	
	/**
	 * 尝试获取锁
	 * 	1	tryLock
	 * 	2	tryLock(long time, TimeUnit unit)
	 * 	3	场景：两个线程竞争同一资源，哪个先获取到锁，则优先执行，未获取到锁的线程执行其他逻辑，并等待通知；
	 * 		当获取通知后，则重新获取锁
	 * 	4	结合condition实现需求场景
	 */
	public void tryLock() throws Exception{
		ReentrantLock reenterLock = new ReentrantLock();

		Runnable runnable = () -> {
			try {
				// tryLock()方法会返回一个布尔值，获取锁成功则为true
				if (reenterLock.tryLock(3, TimeUnit.SECONDS)) {
					System.out.println(Thread.currentThread().getName() + "获取锁成功");
					Thread.sleep(2000);
				} else {
					System.out.println(Thread.currentThread().getName() + "获取锁失败");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				// 最后，如果当前前程在持有锁，则释放锁
				if (reenterLock.isHeldByCurrentThread()) {
					System.out.println(Thread.currentThread().getName() + "释放锁了");
					reenterLock.unlock();
				}
			}
		};

		Thread thread1 = new Thread(runnable, "thread-1");
		Thread thread2 = new Thread(runnable, "thread-2");

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();
	}
	
	/**
	 * 可中断的获取锁：在锁的获取过程中，可以中断当前线程，线程终中断，则抛出异常，并释放锁
	 */
	public void lockInterruptibly(){
		
	}

	/**
	 * 公平锁和非公平锁
	 */
	public void lockFair(){

	}

}
