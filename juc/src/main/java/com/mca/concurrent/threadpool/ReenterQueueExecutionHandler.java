package com.mca.concurrent.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 当队列满时，将任务重新加入队列，如果队列已满，则阻塞
 * @author wangyong
 * @date 2019年2月21日 下午5:40:44
 */
public class ReenterQueueExecutionHandler implements RejectedExecutionHandler{

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		try {
			executor.getQueue().put(r);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
