package com.wy.mca.concurrent.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 1 Semaphore信号量：用来控制同时访问特定资源的线程数量，通过协调各个线程，保证合理的使用公共资源
 * 2 使用：
 * 	 2.1 创建Semaphore对象：Semaphore semaphore = new Semaphore(10);
 * 	 2.2 限流：semaphore.acquire();
 * 	 2.3 放行：semaphore.release();
 * 3 Semaphore的其他方法：
 * 	 3.1 void acquire(int permits) ：获取指定数目的许可，如果无可用许可前也将会一直阻塞等待。
 * 	 3.2 假如现在有10个线程，如果使用semaphore.acquire(2)，那么可能会发生这样一种情况：10个线程中有一个任务优先执行完成，此时semaphore有9个许可，
 * 		 semaphore.acquire(2)会尝试一下获取两个许可，而我们的semaphore只能允许10个许可，此时就会导致死锁
 * 			
 * @author wangyong
 * @date 2018年11月21日 下午12:00:58
 */
public class SemaphoreClient {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		//1	创建Semaphore
		Semaphore semaphore = new Semaphore(5);
		for(int i=0; i<10; i++){
			executorService.execute(() ->{ 
				try {
					//2	限流	
					semaphore.acquire();
					System.out.println("Entring " + Thread.currentThread().getName());
					TimeUnitUtil.sleepSeconds(1);
					System.out.println("---------" + Thread.currentThread().getName() + "；Avaliable permits---->" + semaphore.availablePermits());
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					//3	放行，会有新的线程到达semaphore.accquire,但是semaphore总能保证最多只有十个线程同时执行
					semaphore.release();
				}
			});
		}
		executorService.shutdown();
	}

}
