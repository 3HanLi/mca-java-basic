package com.mca.concurrent.basic.introduce;

import com.wy.mac.java.concurrent.util.TimeUnitUtil;

import java.util.ArrayList;

/**
 * 线程优先级
 *
 * @author WangyongR
 * @date 2016-12-13 下午10:27:38
 */
public class ThreadPriority {

	/*
	 * volatile使用详解：参考volatile的理解；
	 */
	private static volatile boolean notStart = true;

	private static volatile boolean notEnd = true;
	
	public static void main(String[] args) {
		ArrayList<Job> jobList = new ArrayList<Job>();//new自动补全前置变量：ctrl + 2;
		//1 创建10个线程并启动,此时线程为等待状态;
		for(int i=0; i<10; i++){
			int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
			Job job = new Job(priority);
			jobList.add(job);//用于统计job优先级和count变化
			Thread thread = new Thread(job, "thread-" + i);
			thread.setPriority(priority);
			thread.start();
		}
		
		//2 通知线程执行count++
		System.out.println("CurrentThreadName:" + Thread.currentThread().getName());
		notStart = false;
		TimeUnitUtil.sleepSeconds(10);//3 执行10秒count
		notEnd = false;//4 线程终止
		
		for(Job job : jobList){
			System.out.println("job.priproty=" + job.priority + ",account=" + job.count);
		}
	}
	
	static class Job implements Runnable{

		private int priority;
		
		private int count;
		
		public Job(int priority) {
			super();
			this.priority = priority;
		}

		public Job() {
			
		}
		
		@Override
		public void run() {
			while(notStart){
				Thread.yield();
				System.out.println("CurrentThreadName:" + Thread.currentThread().getName());
			}
			while(notEnd){
				/**
				 * Thread.yield()：线程让步
				 */
				Thread.yield();
				count ++;
			}
		}
		
	}
}
