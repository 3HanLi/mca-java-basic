package com.mca.concurrent.basic.start;

import java.util.concurrent.TimeUnit;

/**
 * 1	理解中断:中断可以理解为线程的一个标识位属性，表示一个运行中的线程是否被其他线程进行了中断操作；可以通过调用线程的interrupt()方法来中断线程
 * 		1.1	对于抛出InterruptedException异常的方法，Java虚拟机会先将该线程的中断标识位清除，然后抛出InterruptedException，此时调用isInterrupted()方法将会返回false，
 * 			常见的抛出InterruptedException异常的方法如下：
 * 				a)	wait：线程会进入阻塞队列等待被唤醒（notify、notifyAll），如果此时调用interrupt方法，线程会优先尝试获取锁，然后抛出InterruptedException异常，
 * 					在未获取到锁的情况下是不会抛出异常的
 * 				b)	sleep：休眠指定的时间，如果此时调用interrupt方法，会直接抛出异常
 * 				c)	join：等待调用join方法的线程执行完成，如果此时调用interrupt方法，会直接抛出异常
 * 
 * 2	Interrupt的原理：只是改变了中断状态，在wait、sleep、join方法内部会不断检查中断状态，当调用interrupt方法时，会抛出异常；
 * 		如果线程没有执行wait、sleep、join等声明了抛出InterruptedException异常的方法，是不会抛出异常的
 * 		2.1	对于非阻塞线程（没有wait、sleep、join的方法），如果调用interrupt方法，线程的中断状态会置为true，但是线程依然会继续执行，并没有什么实质性的影响
 * 		2.2	对于阻塞的线程（也就是wait、sleep、join那些），如果调用interrupt方法，Java虚拟机会先将该线程的中断标识位清除，然后抛出InterruptedException，相当于没有中断，线程依然继续执行
 * 		2.3	总结：调用线程的interrupt方法只是改变了线程的状态，不会对线程的执行造成实质性影响，如果需要终止线程，需要人为去控制；
 * 
 * @author wangyong
 * @date 2018年11月22日 下午4:51:44
 */
public class ThreadInteruptClient {

	public static void main(String[] args) throws InterruptedException {
		Thread sleepThread = new Thread(new SleepTask(),"SleepThread");
		sleepThread.start();
		TimeUnit.SECONDS.sleep(1);
		//1	非正常中断：线程的中断状态为false，Java虚拟机会先将该线程的中断标识位清除，然后抛出InterruptedException，相当于没有中断，线程依然继续执行
		sleepThread.interrupt();
		System.out.println("Sleep interrupted status:" + sleepThread.isInterrupted());
		
		Thread busyThread = new Thread(new BusyTask(),"BusyThread");
		busyThread.start();
		TimeUnit.SECONDS.sleep(1);
		//2	正常中断，不抛出异常，线程的中断状态会置为true，但是线程依然会继续执行，并没有什么实质性的影响
		busyThread.interrupt();
		System.out.println("Busy interrputed status:" + busyThread.isInterrupted());
		
	}
	
	private static class SleepTask implements Runnable{

		@Override
		public void run() {
			while(true){
				try {
					System.out.println("Sleep Thread is interrupted : " + Thread.currentThread().isInterrupted());
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private static class BusyTask implements Runnable{

		@Override
		public void run() {
			while(true){
				System.out.println("Busy Thread is interrupted : " + Thread.currentThread().isInterrupted());
				for (long i=0; i<100 * 100 * 100 * 100 * 100; i++){
					
				}
			}
		}
		
	}
}
