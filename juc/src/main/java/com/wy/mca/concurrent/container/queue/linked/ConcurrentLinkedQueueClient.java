package com.wy.mca.concurrent.container.queue.linked;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 队列分类：
 * 	1	线程安全的无界队列（ConrurrentLinkedQueue），底层采用CAS算法
 * 
 * 
 * 	2	线程安全的阻塞队列（BlockingQueue），底层采用Lock的方式保证安全【@See BlockingQueueClient 】
 * 
 *  3 线程安全的队列 和 阻塞队列 使用场景：
 *    3.1 单生产者，单消费者 用 LinkedBlockingqueue 
 *		3.2 多生产者，单消费者 用 LinkedBlockingqueue 
 *		3.3 单生产者 ，多消费者 用 ConcurrentLinkedQueue 
 *		3.4 多生产者 ，多消费者 用 ConcurrentLinkedQueue
 * @author wangyong
 * @date 2018年12月12日 下午3:48:41
 */
public class ConcurrentLinkedQueueClient {

	public static void main(String[] args) {
		//1	创建线程安全的队列
		ConcurrentLinkedDeque<String> queue = new ConcurrentLinkedDeque<String>();
		for(int i=0; i<5; i++){
			//2	开启多线程并发访问
			new Thread(() ->{
				for(int k=0; k<10; k++){
					queue.offer("wangyong" + k);
				}
			}).start();
		}
		
		for(int i=0; i<queue.size(); i++){
			System.out.println(queue.poll());
		}
	}
}
