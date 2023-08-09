package com.mca.concurrent.threadpool;


import com.mca.concurrent.util.DateFormatUtil;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 自定义任务：
 * @author wangyong
 * @date 2019年2月22日 上午11:09:05
 */
public class RunTask implements Runnable {

	private int taskNumber;

	public RunTask() {

	}

	public RunTask(int taskNumber) {
		super();
		this.taskNumber = taskNumber;
	}

	@Override
	public void run() {
		System.out.println("Task:" + taskNumber + " Entered,Time:" + DateFormatUtil.getFormatDate(new Date()));
		try {
			//线程睡眠，目的是防止任务过快执行，让任务充满填满线程池和队列，观察现象
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Task:" + taskNumber + " Leave");
	}

	@Override
	public String toString() {
		return "RunTask [taskNumber=" + taskNumber + "]";
	}

}
