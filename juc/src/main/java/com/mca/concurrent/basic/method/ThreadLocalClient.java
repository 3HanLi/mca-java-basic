package com.mca.concurrent.basic.method;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ThreadLocal详解：为每个线程创建私有变量，各个线程之间互不影响；
 * 
 * 使用场景：Session，数据库Connection
 * 
 * 注意：每次使用完ThreadLocal都需要进行remove，否则会导致内存泄漏，因为我们为每个线程都创建了Connection，如果不及时释放，
 * 可能导致多个线程创建的Connection过多，从而造成内存泄漏
 * 
 * 底层原理总结：相当于Map<Thread,Object> map = new HashMap<Thread,Object>();
 * 
 * @author wangyong
 * @date 2018年11月26日 上午10:16:38
 */
public class ThreadLocalClient {

	private static ThreadLocal<String> threadName = new ThreadLocal<>();
	
	public static void main(String[] args) throws InterruptedException {
		//1	为main线程设置值
		threadName.set(Thread.currentThread().getName());
		System.out.println(threadName.get());
		
		//2	为自定义线程thread-01设置值
		Thread thread01 = new Thread(()->{
			threadName.set(Thread.currentThread().getName());
			System.out.println("Thread-01 value:" + threadName.get());
		},"thread-01");
		thread01.start();
		thread01.join();
		
		System.out.println("Main thread value:" + threadName.get());
	}
	
	/**
	 * 数据库链接
	 * @author wangyong
	 * @date 2018年11月26日 上午11:21:03
	 */
	class DriveManager{
		private ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>(){
			@Override
			protected Connection initialValue() {
				try {
					return DriverManager.getConnection("db_url");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		
		public Connection getConn(){
			return connLocal.get();
		}
	}
}
