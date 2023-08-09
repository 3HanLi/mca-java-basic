package com.mca.concurrent.container.queue.blocked;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ArrayLBlockingQueue测试
 * 1	定义队列必须指定长度：
 * 		ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
 * 
 * 2	定义生产者和消费者
 * 		2.1	queue.put(nextInt):生产
 * 		2.2	queue.take()：消费
 * 
 * 3	源码分析：
 * 		3.1	queue.put(nextInt)：底层相当于使用await和notify来进行通信
 * 		3.2	核心原理：相当于将“等待-通知”进行了封装
 * 
 * 4	特点：先进先出
 * 
 * 扩展：Random详解
 * @author wangyong
 * @date 2018年12月3日 下午3:30:00
 */
public class ArrayBlockingQueueClient {

	public static void main(String[] args) {
		//1	定义队列
		ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
		//2	定义两个线程：进行生产和消费
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		Random random = new Random(1000);
		executorService.execute(()->{
			while(true){
				try {
					TimeUnit.MILLISECONDS.sleep(20);
					int nextInt = random.nextInt(100);
					System.out.println("Put element+++:" + nextInt);
					//2.1	生产元素
					queue.put(nextInt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		executorService.execute(()->{
			while(true){
				try {
					TimeUnit.MILLISECONDS.sleep(400);
					//2.2	消费元素
					Integer take = queue.take();
					System.out.println("Take element---:" + take);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
