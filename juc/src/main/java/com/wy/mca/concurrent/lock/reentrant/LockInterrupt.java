package com.wy.mca.concurrent.lock.reentrant;


import com.wy.mca.concurrent.util.TimeUnitUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 响应中断：主线程main结束，子线程也能够执行完
 * 
 * @author wangyong
 * @date 2018年11月7日 下午3:25:17
 */
public class LockInterrupt {

	private static ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args) throws InterruptedException{
		Runnable runnable = () -> {
			try{
				//尝试获取锁，可以通过调用thread.interrupt中断，被中断后锁自动释放
				lock.lockInterruptibly();
				TimeUnit.SECONDS.sleep(5);
			}catch(InterruptedException ex){
				//线程中断:线程2一直未获取到锁，执行中断操作
				System.out.println(Thread.currentThread().getName() + "中断");
			}finally{
				//如果是未中断的线程持有锁，释放锁
				//线程中断后，会自动释放锁
				if(lock.isHeldByCurrentThread()){
					lock.unlock();
					System.out.println(Thread.currentThread().getName() + "释放锁");
				}
			}
		};
		
		Thread thread01 = new Thread(runnable,"Thread-01");
		Thread thread02 = new Thread(runnable,"Thread-02");
		
		//保证线程1优先获取锁
		thread01.start();
		thread01.interrupt();

		//线程2获取锁，如果获取不到，会阻塞资源，三秒后手动中断，防止造成资源浪费
		thread02.start();
		thread02.interrupt();
		TimeUnitUtil.sleepSeconds(10);
	}
}
