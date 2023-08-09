package com.mca.concurrent.container.queue.blocked;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * DelayedQueue使用详解(了解)
 * 1	基于PriorityQueue，一种延时阻塞队列，DelayQueue中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素
 * 2	使用场景：
 * 		2.1	缓存：使用DelayedQueue存储元素，让线程轮训，一旦能够获取元素，说明元素时间到期，缓存失效；
 * 		2.2	定时任务：DelayedQueue保存当天要执行的任务和时间，一旦时间到了，就执行任务；
 * 
 * @author wangyong
 * @date 2018年12月12日 上午10:36:40
 */
public class DelayQueueClient {

	public static void main(String[] args) {
		DelayQueue<DelayedElement> delayQueue = new DelayQueue<DelayedElement>();

		// 生产者
		producer(delayQueue);

		// 消费者
		consumer(delayQueue);

		while (true) {
			try {
				TimeUnit.HOURS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 每100毫秒创建一个对象，放入延迟队列，延迟时间1毫秒
	 * 
	 * @param delayQueue
	 */
	private static void producer(final DelayQueue<DelayedElement> delayQueue) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					DelayedElement element = new DelayedElement(1000, "test");
					delayQueue.offer(element);
				}
			}
		}).start();

		/**
		 * 每秒打印延迟队列中的对象个数
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						TimeUnit.MILLISECONDS.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("delayQueue size:" + delayQueue.size());
				}
			}
		}).start();
	}

	/**
	 * 消费者，从延迟队列中获得数据,进行处理
	 * 
	 * @param delayQueue
	 */
	private static void consumer(final DelayQueue<DelayedElement> delayQueue) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					DelayedElement element = null;
					try {
						element = delayQueue.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(System.currentTimeMillis() + "---" + element);
				}
			}
		}).start();
	}
}

