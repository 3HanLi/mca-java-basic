package com.mca.concurrent.threadpool;

import com.wy.mac.java.concurrent.util.DateFormatUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;


/**
 * 线程池详解：
 * 1 创建线程池：
 * 2 提交任务：
 *   2.1 execute：提交没有返回值的任务
 *   2.2 submit：
 *   		 提交带返回值的任务，线程池会返回future对象
 *       可以用来判断任务是否执行成功，也可以使用future.get()方法获取执行结果
 *       如果未返回结果，则阻塞当前线程
 * 3 关闭线程池：
 *   3.1 shutdown：调用之后，会等待线程池中的任务执行完成
 *   3.2 shutdownNow：立即停止所有正在执行或者将要执行的任务
 * 4 配置线程池，主要有一下几个参数决定：
 *   4.1 tasks ：每秒的任务数，假设为500~1000
 *   4.2 taskcost：每个任务花费时间，假设为0.1s
 *   4.3 responsetime：系统允许容忍的最大响应时间，假设为1s
 *   
 *   如：coreSize = max(tasks) / (response / taskcost) * 80% 
 *   @see https://www.cnblogs.com/waytobestcoder/p/5323130.html
 *   
 * 5 监控线程池：如果在系统中大量使用线程池，有必要对其进行监控，可以通过线程池的如下参数进行监控，如：
 *   5.1 taskCount：线程池需要指定的任务数量
 *   5.2 completedTaskCount：已经完成的任务数量
 *   5.3 largestpoolSize：线程池曾经创建过的最大线程数
 *   5.4 getPoolSize：获取当前线程池的线程数量
 *   5.5 getActiveCount：获取线程池中活动的线程数量
 * 
 * @author wangyong
 * @date 2019年2月21日 下午4:56:30
 */
public class PoolClient {

	private int corePoolSize = 5;

	private int maximumPoolSize = 10;

	private int keepAliveTime = 10;

	private TimeUnit unit = TimeUnit.SECONDS;

	public static void main(String[] args) throws Exception{
//		new PoolClient().executeLoggingRedundantTask();
//		new PoolClient().executeReExecuteTask();
//		new PoolClient().submitTask();
//		new PoolClient().calCostMaxTime();
//			new PoolClient().monitorThreadPool();
		new PoolClient().executorServicePool();
	}
	
	/**
	 * 队列满时，将任务记录日志
	 * 
	 * 注意：使用单元测试，Runnable任务没有执行完毕就结束，建议使用main
	 */
	public void executeLoggingRedundantTask() {
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);

		/**
		 * 线程池的核心参数：
		 * 1 corePoolSize（线程池的基本大小）：当提交一个任务到线程池时，线程池创建一个线程来执行任务（即使存在空闲线程也会创建），当线程池中线程的数量
		 * 	 等于corePoolSize就不再创建。也可以调用executor.prestartAllCoreThreads()提前启动并创建corePoolSize数量的线程
		 * 2 maximumPoolSize（最大线程数量）：如果队列满了，并且线程池中的线程数量 小于 maximumPoolSize，则创建线程执行任务
		 * 3 keepAliveTime（空闲线程的存活时间）：当实际线程数量超过corePoolSize时，若线程空闲的时间超过该值，就会被停止
		 *   当任务很多，且任务执行时间很短的情况下，可以将该值调大，提高线程利用率
		 * 4 unit（空闲线程的存活时间单位），如：TimeUnit.SECONDS
		 * 5 workQueue（任务队列）：用于保存等待执行的任务，常用的队列如下；
		 * 	 5.1 ArrayBlockingQueue：一个基于数组的有界阻塞队列
		 *   5.2 LinkedBlockingQueue：一个基于链表的有界阻塞队列，吞吐量高于ArrayBlockingQueue；Executors.newFixedThreadPool用了这个队列
		 *   5.3 SynchronousQueue：不存储元素的阻塞队列，吞吐量高于LinkedBlockingQueue，Executors.newCachedThreadPool用了这个队列
		 * 6 threadFactory：用于设置创建线程的工厂，可以通过线程工厂给每个创建出来的线程设置有意义的名字用于定位问题
		 * 7 RejectedExecutionHandler（饱和策略）：当线程池和队列都满了，采用饱和策略来处理任务，可以让任务重新执行，也可以直接丢弃，默认的策略如下：
		 * 	 7.1 AbortPolicy（默认）：直接抛出异常
		 *   7.2 CallerRunsPolicy：只用调用者所在的线程执行任务
		 *   7.3 DiscardOldestPolicy：丢弃任务队列中时间最长的任务，并执行当前任务
		 *   7.4 DiscardPolicy：丢弃当前任务
		 */
		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				new CustomThreadFactory(), new LoggingExecutionHandler());
		for(int i=1; i<=30; i++){
			executor.execute(new RunTask(i));
		}
		executor.shutdown();
	}
	
	/**
	 * 当队列满时，将任务重新加入队列，如果队列已满，则阻塞
	 */
	public void executeReExecuteTask(){
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);

		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				new CustomThreadFactory(), new ReenterQueueExecutionHandler());
		for(int i=1; i<=30; i++){
			executor.execute(new RunTask(i));
		}
		executor.shutdown();
	}
	
	/**
	 * Submit：提交需要返回值的任务。等待获取执行结果，获取不到，则阻塞线程
	 */
	public void submitTask(){
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);

		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				new CustomThreadFactory(), new ReenterQueueExecutionHandler());
		for(int i=1; i<=30; i++){
			Future<String> future = executor.submit(new ResultTask());
			try {
				//阻塞main线程
				String result = future.get(2, TimeUnit.SECONDS);
				System.out.println("Result : " + result + "; time：" + DateFormatUtil.getFormatDate(new Date()));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Cannot get result in limit time");
			}
		}
		executor.shutdown();
	}
	
	/**
	 * 架构设计：同时执行多个任务，保证执行的时间最短
	 */
	public void calCostMaxTime(){
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);

		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				new CustomThreadFactory(), new ReenterQueueExecutionHandler());
		List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();
		for(int i=1; i<=20; i++){
			Future<Integer> future = executor.submit(new TimeResultTask());
			futureList.add(future);
		}
		executor.shutdown();
		Collections.shuffle(futureList);
		
		System.out.println("Calculate Max Time Start At :" + DateFormatUtil.getFormatDate(new Date()));
		
		for(Future<Integer> future : futureList){
			try {
				Integer result = future.get();
				System.out.println("Result:" + result);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Calculate Max Time End At :" + DateFormatUtil.getFormatDate(new Date()));
		
	}
	
	/**
	 * 监控线程池
	 */
	public void monitorThreadPool(){
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				new CustomThreadFactory(), new LoggingExecutionHandler());
		for(int i=1; i<=30; i++){
			executor.execute(new RunTask(i));
		}
		executor.shutdown();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("------------------------------------>");
		System.out.println("TaskCount:" + executor.getTaskCount());
		System.out.println("completedTaskCount:" + executor.getCompletedTaskCount());
		System.out.println("largestpoolSize:" + executor.getLargestPoolSize());
		System.out.println("getPoolSize:" + executor.getPoolSize());
		System.out.println("getActiveCount:" + executor.getActiveCount());
		System.out.println("------------------------------------>");
	}

	/**
	 * 线程池工具类
	 */
	public void executorServicePool() throws Exception{
		singleThreadExecutor();

		fixedThreadPool();

		cachedThreadPool();

		scheduledThreadExecutor();
	}

	private void singleThreadExecutor() throws Exception{
		CountDownLatch countDownLatch = new CountDownLatch(1);

		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		System.out.println("singleThreadExecutor");
		singleThreadExecutor.execute(()->{
			System.out.println("singleThreadExecutor");
			countDownLatch.countDown();
		});

		countDownLatch.await();
		singleThreadExecutor.shutdown();
	}

	private void fixedThreadPool() throws Exception{
		CountDownLatch countDownLatch = new CountDownLatch(1);

		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
		System.out.println("fixedThreadPool");
		fixedThreadPool.execute(()->{
			System.out.println("fixedThreadPool");
			countDownLatch.countDown();
		});

		countDownLatch.await();
		fixedThreadPool.shutdown();
	}

	private void cachedThreadPool() throws Exception{
		CountDownLatch countDownLatch = new CountDownLatch(1);

		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		System.out.println("fixedThreadPool");
		cachedThreadPool.execute(()->{
			System.out.println("fixedThreadPool");
			countDownLatch.countDown();
		});

		countDownLatch.await();
		cachedThreadPool.shutdown();
	}

	private void scheduledThreadExecutor() throws Exception{
		CountDownLatch countDownLatch = new CountDownLatch(1);
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

		System.out.println("Current Time-->" + DateFormatUtil.getFormatDate(new Date()));
		scheduledExecutorService.schedule(()->{
			System.out.println("5秒后执行");
			countDownLatch.countDown();
		}, 5, TimeUnit.SECONDS);

		countDownLatch.await();
		scheduledExecutorService.shutdown();
		System.out.println("Current Time-->" + DateFormatUtil.getFormatDate(new Date()));
	}
	
}
