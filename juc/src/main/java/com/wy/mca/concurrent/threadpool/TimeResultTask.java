package com.wy.mca.concurrent.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 带返回值的任务：让每个任务执行不同的时间
 * @author wangyong
 * @date 2019年2月22日 上午9:45:55
 */
public class TimeResultTask implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		Integer threadNum = Integer.parseInt(Thread.currentThread().getName().substring(6, 7));
		TimeUnit.SECONDS.sleep(threadNum);
		return threadNum;
	}

}
