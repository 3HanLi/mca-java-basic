package com.wy.mca.concurrent.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 带返回着的任务
 * @author wangyong
 * @date 2019年2月22日 上午11:10:39
 */
public class ResultTask implements Callable<String>{

	@Override
	public String call() throws Exception {
		TimeUnit.SECONDS.sleep(1);
		return Thread.currentThread().getName();
	}

}
