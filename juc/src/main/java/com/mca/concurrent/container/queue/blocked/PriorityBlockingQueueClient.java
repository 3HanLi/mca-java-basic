package com.mca.concurrent.container.queue.blocked;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * PriorityBlockingQueue测试：
 * 特点：会按照元素的优先级对元素进行排序，按照优先级顺序出队列，默认【从小到达】，也可以指定排序策略，如：【从大到小】
 * 
 * @author wangyong
 * @date 2018年12月5日 下午5:59:18
 */
public class PriorityBlockingQueueClient {

	public static void main(String[] args) {
		// 1 定义队列：指定排序规则(x,y) -> {return -x.compareTo(y);}
		PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<Integer>(10,(x,y) -> {return -x.compareTo(y);});
		// 2 定义两个线程：进行生产和消费
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		Random random = new Random(1000);
		executorService.execute(() -> {
			while (true) {
				try {
					TimeUnit.MILLISECONDS.sleep(100);
					int nextInt = random.nextInt(100);
					System.out.println("Put element+++:" + nextInt);
					// 2.1 生产元素
					queue.put(nextInt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		executorService.execute(() -> {
			while (true) {
				try {
					TimeUnit.MILLISECONDS.sleep(400);
					// 2.2 消费元素
					Integer take = queue.take();
					System.out.println("Take element---:" + take);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
