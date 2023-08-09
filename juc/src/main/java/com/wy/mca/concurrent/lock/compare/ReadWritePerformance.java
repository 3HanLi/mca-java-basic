package com.wy.mca.concurrent.lock.compare;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁和syncronized性能对比
 * 
 * @author wangyong
 * @date 2018年11月8日 上午11:10:58
 */
public class ReadWritePerformance {
	
	private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock(); 

	public static void main(String[] args) throws InterruptedException {
		traditionSync();
		readWritePerformance();
	}
	
	/**
	 * 读写锁		 ReadWriteLock Cost time : 231
	 * @throws InterruptedException 
	 */
	private static void readWritePerformance() throws InterruptedException{
		Runnable runnable = getReadLockTask();
		
		countTime(runnable, "ReadWriteLock");
	}
	
	/**
	 * 传统sync：Sync Cost time : 472
	 * 
	 * @throws InterruptedException 
	 */
	public static void traditionSync() throws InterruptedException{
		
		Runnable runnable = getSyncTask();
		
		countTime(runnable, "Sync");
		
	}
	
	private static void countTime(Runnable runnable,String lockType) throws InterruptedException{
		long startTime = System.currentTimeMillis();

		Thread thread01 = new Thread(runnable,"Thread01");
		Thread thread02 = new Thread(runnable,"Thread02");
		
		thread01.start();
		thread02.start();
		
		thread01.join();
		thread02.join();
		
		long endTime = System.currentTimeMillis();
		System.out.println(lockType + " Cost time : " + (endTime-startTime));
	}
	
	private static Runnable getSyncTask(){
		return () -> {
			synchronized (ReadWritePerformance.class) {
				for(int i=0;i<10; i++){
					try {
						TimeUnit.MILLISECONDS.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private static Runnable getReadLockTask(){
		return  () -> {
			readWriteLock.readLock().lock();
			try{
				for(int i=0;i<10; i++){
					TimeUnit.MILLISECONDS.sleep(20);
				}
			}catch(InterruptedException e){
				e.printStackTrace();
			}finally{
				readWriteLock.readLock().unlock();
			}
		};
	}
}
