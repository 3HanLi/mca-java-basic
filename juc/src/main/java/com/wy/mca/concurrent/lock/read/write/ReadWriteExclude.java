package com.wy.mca.concurrent.lock.read.write;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁互斥
 * 	1	读和读互不影响，读和写互斥，写和写互斥
 * 	2	可中断锁：如果某一线程A正在执行锁中的代码，另一线程B正在等待获取该锁，可以让它中断自己或者在别的线程中中断它
 * 	3	synchronized不是可中断锁，而Lock是可中断锁
 * 	4	可重入(Reentrant)锁：锁具备可重入性，即：加锁后可以再次加锁则称作为可重入锁，
 * 
 * @author wangyong
 * @date 2018年11月8日 下午4:02:22
 */
public class ReadWriteExclude {

	private static ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public static void main(String[] args) throws InterruptedException {
		readLockShare();
		readWriteLockExclude();
	}

	/**
	 * 读锁共享:两个线程交替执行，或者说并行执行
	 * @throws InterruptedException 
	 */
	private static void readLockShare() throws InterruptedException{
		Runnable realLockTask = realLockTask();
		Thread thread01 = new Thread(realLockTask,"Thread01");
		Thread thread02 = new Thread(realLockTask,"Thread02");
		
		thread01.start();
		thread02.start();
		
		thread01.join();
		thread02.join();
	}
	
	/**
	 * 读写锁互斥:两个线程同一时刻只能由一个执行，也就是独占锁
	 * 
	 * @throws InterruptedException
	 */
	private static void readWriteLockExclude() throws InterruptedException{
		Runnable realLockTask = readWriteLockTask();
		Thread thread01 = new Thread(realLockTask,"Thread01");
		Thread thread02 = new Thread(realLockTask,"Thread02");
		
		thread01.start();
		thread02.start();
		
		thread01.join();
		thread02.join();
	}
	
	/**
	 * 读锁
	 * @return
	 */
	private static Runnable realLockTask(){
		return () -> {
			lock.readLock().lock();
			try{
				for(int i=0; i<10; i++){
					TimeUnit.MILLISECONDS.sleep(10);
					System.out.println(Thread.currentThread().getName() + " Read");
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				lock.readLock().unlock();
			}
		};
	}
	
	/**
	 * 写锁
	 * 
	 * @return
	 */
	private static Runnable readWriteLockTask(){
		return () -> {
			lock.writeLock().lock();
			try{
				for(int i=0; i<10; i++){
					TimeUnit.MILLISECONDS.sleep(10);
					System.out.println(Thread.currentThread().getName() + " Write");
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				lock.writeLock().unlock();
			}
		};
	}
}
