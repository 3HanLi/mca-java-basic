package com.wy.mca.concurrent.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 拒绝策略：当队列满时，将任务记录在日志里
 * @author wangyong
 * @date 2019年2月21日 下午5:13:58
 */
public class LoggingExecutionHandler implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println("Task:" + r + " Discard ");
	}

}
