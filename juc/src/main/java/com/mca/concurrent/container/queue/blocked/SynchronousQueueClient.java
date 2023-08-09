package com.mca.concurrent.container.queue.blocked;

import com.wy.mac.java.concurrent.util.DateFormatUtil;

import java.util.Date;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;


/**
 * SynchronousQueue详解：SynchronousQueue是一个不存储元素的队列，每一次put元素之前，必须等待take将队列中元素取出，否则一直阻塞
 * 
 * @author wangyong
 * @date 2018年12月12日 下午2:43:08
 */
public class SynchronousQueueClient {

	public static void main(String[] args) {
		SynchronousQueue<String> queue = new SynchronousQueue<String>();
		
		//每隔1s存储一个元素，结果：put之后，如果没有take操作，就会阻塞，说明SynchronousQueue不存储元素
		new Thread(()->{
			for(int i=0; i<10; i++){
				try {
					System.out.println("Put ele: wangyong "+ i + "-->" + DateFormatUtil.getFormatDate(new Date()));
					queue.put("wangyong" + i);
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		//每隔3s取出元素
		new Thread(()->{
			for(int i=0; i<10; i++){
				try {
					TimeUnit.SECONDS.sleep(3);
					String take = queue.take();
					System.out.println("Take Ele:" + take + " At " + DateFormatUtil.getFormatDate(new Date()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
}
