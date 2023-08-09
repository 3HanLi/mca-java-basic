package com.wy.mca.concurrent.lock.reentrant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 共享对象
 * @author wangyong
 * @date 2018年11月5日 上午10:30:08
 */
public class ShareLockTask implements Runnable{

	private int count;
	
	private Lock lock = new ReentrantLock();
	
	@Override
	public void run() {
		try{
			lock.lock();
			System.out.println("Thread:" + Thread.currentThread().getName());
			for(int i=0; i<1000; i++){
				count++;
			}
		} finally{
			lock.unlock();
		}
	}
	
	public int getCount() {
		return count;
	}
	
}
