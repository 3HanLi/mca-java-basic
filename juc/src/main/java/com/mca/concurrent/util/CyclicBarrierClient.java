package com.mca.concurrent.util;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1 CyclicBarrier详解：让一组线程到达指定点进行等待，直到最后一个线程到达，才继续向下执行
 * 	 1.1 创建CyclicBarrier对象：CyclicBarrier cyclicBarrier = new CyclicBarrier(int n,Runnable priorityTarget)
 * 	 	 a)	参数1：指定n个线程到指定点进行等待，如果n个线程没有全部到达，则已经到达的线程进行阻塞
 * 		 b)	参数2: 当n个线程都到达指定点后，优先执行priorityTarget，然后执行阻塞的线程
 * 	 1.2 优先到达的线程进行阻塞：cyclicBarrier.await();
 * 	 1.3 常用方法：getNumberWaiting，获取等待的线程
 * 2 使用场景：
 * 	 2.1 用于多线程进行数据运算，然后进行数据归纳的情况，如：让n个线程进行结果计算，然后让target任务进行汇总处理结果
 * 		 CyclicBarrier cyclicBarrier = new CyclicBarrier(int n,Runnable priorityTarget)
 * 3 和CountDownlatch对比：
 * 	 3.1 countDownLatch相当于Thread.join，一般用于阻塞当前线程（阻塞一个线程），只有当计数器减为0，才继续执行；
 * 	 3.2 cyclicbarrier用于阻塞一组线程，直到线程数量达到指定数，才继续向下执行；
 * 
 * @author wangyong
 * @date 2018年11月22日 下午12:00:39
 */
public class CyclicBarrierClient {
	
	public static void main(String[] args) {
		//1	创建cyclicBarrier对象
		CyclicBarrier cyclicBarrier = new CyclicBarrier(10,()->{
			System.out.println("Executing first...");
		});
		ExecutorService executorService = Executors.newCachedThreadPool();
		for(int i=0; i<10; i++){
			executorService.execute(()->{
				try {
					System.out.println("Current Thread-->" + Thread.currentThread().getName());
					cyclicBarrier.await();
					System.out.println("Batch run");
				} catch (Exception ex){
					ex.printStackTrace();
				}
			});
		}
		executorService.shutdown();
	}

}
