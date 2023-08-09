package com.mca.concurrent.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三-六模型： 第一个线程进行计数，如果达到3的倍数，等待；通知第二个线程进行累加； 第二个线程进行计数，如果达到6的倍数，等待；通知第一个线程进行累加；
 * 
 * @author wangyong
 * @date 2018年11月20日 上午10:50:28
 */
public class ThreeSixModel {

	private static int count = 0;

	private volatile boolean flag = true;

	private Lock lock = new ReentrantLock();

	private Condition threeWaitCon = lock.newCondition();

	private Condition sixWaitCon = lock.newCondition();

	/**
	 * 线程通信代码范式：
	 * if(条件不满足){
	 * 	 //逻辑处理
	 * 	 //等待
	 * 	 currCondition.await()
	 * }
	 * //处理其他逻辑
	 * //通知
	 * otherCondition.signal();
	 * @throws InterruptedException
	 */
	public void threeModel() throws InterruptedException {
		lock.lock();
		try {
			while (flag) {
				count ++;
				if (count % 3 != 0) {
					if (flag) {
						continue;
					}
					threeWaitCon.await();
				}
				System.out.println(Thread.currentThread().getName() +  "：" + count);
				flag = false;
				sixWaitCon.signal();
			}
		} finally {
			lock.unlock();
		}
	}

	public void sixModel() throws InterruptedException {
		lock.lock();
		try {
			while (!flag) {
				count ++;
				if (count % 6 != 0) {
					if(!flag){
						continue;
					}
					sixWaitCon.await();
				} 
				System.out.println(Thread.currentThread().getName() +  "：" + count);
				flag = true;
				threeWaitCon.signal();
			}
		} finally {
			lock.unlock();
		}
	}

}
