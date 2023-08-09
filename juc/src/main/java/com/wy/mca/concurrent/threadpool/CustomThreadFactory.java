package com.wy.mca.concurrent.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂：给每个创建出来的线程设置有意义的名字
 * @author wangyong
 * @date 2019年2月21日 下午5:08:20
 */
public class CustomThreadFactory implements ThreadFactory{

	private static AtomicInteger count = new AtomicInteger(1);
	
	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		thread.setName("Thread" + count.getAndIncrement());
		return thread;
	}

}
