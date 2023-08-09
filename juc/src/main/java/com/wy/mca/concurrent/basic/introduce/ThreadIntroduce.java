package com.wy.mca.concurrent.basic.introduce;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 	1 线程： 
 * 		1.1 启动一个Java程序就会创建一个java进程，一个进程可以有多个线程；
 * 		1.2	main方法的执行是main线程和其他线程共同执
 * 	2	多线程的好处：
 * 		2.1	更多的处理器
 * 		2.2	更快的响应时间
 * 		2.3	更好的编程模型
 * 	3	线程优先级：
 * 		3.1	可以指定线程运行的优先级，有的操作系统会忽略优先级，这里不深入学习【了解】
 * 
 * @author wangyong
 * @date 2019年1月18日 上午11:52:49
 */
public class ThreadIntroduce {

	/**
	 *	main方法的执行是main线程和其他线程共同执
	 *	[4]Signal Dispatcher
	 *	[3]Finalizer
	 *	[2]Reference Handler
	 *	[1]main
	 * @param args
	 */
	public static void main(String[] args) {
		// 获取Java线程管理MXBean
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		// 获取线程和线程堆栈信息
		ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
		// 遍历线程线程，仅打印线程ID和线程名称信息
		for (ThreadInfo threadInfo : threadInfos) {
			System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
		}
	}
}
