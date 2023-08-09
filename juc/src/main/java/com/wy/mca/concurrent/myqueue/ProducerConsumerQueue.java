package com.wy.mca.concurrent.myqueue;

import lombok.Data;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者-消费者-队列
 * @author wangyong
 * @date 2018年11月19日 下午7:41:12
 */
@Data
public class ProducerConsumerQueue {

	//重入锁
	private Lock lock = new ReentrantLock();
	
	//非满等待条件
	private Condition notFull = lock.newCondition();
	
	//非空等待条件
	private Condition notEmpty = lock.newCondition();
	
	private int[] queue;
	
	private int currIndex;
	
	private int count;
	
	public ProducerConsumerQueue(int size) {
		queue = new int[size];
	}
	
	public void put(int element) throws InterruptedException{
		lock.lock();
		try{
			System.out.println("put ele:" + element);
			while(count == queue.length){
				notFull.await();
			}
			queue[currIndex++] = element;
			if(queue.length == currIndex){
				currIndex = 0;
			}
			count++;
			notEmpty.signal();
		}finally{
			lock.unlock();
		}
	}
	
	public Integer take() throws InterruptedException{
		Integer takeEle = null;
		lock.lock();
		try{
			while(count == 0){
				notEmpty.await();
			}
			takeEle = queue[--currIndex];
			count--;
			notFull.signal();
			System.out.println("Take ele:" + takeEle);
		}finally{
			lock.unlock();
		}
		return takeEle;
	}
	
}
