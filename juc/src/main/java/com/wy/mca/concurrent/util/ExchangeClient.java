package com.wy.mca.concurrent.util;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 1 Exchanger：用于进行两个线程间的数据交换；
 * 	 1.1 提供一个同步点，在这个同步点，两个线程可以彼此交换数据；
 * 	 1.2 如果一个线程优先到达，则会等待第二个线程到达同步点；
 * 2 使用场景：
 *   2.1 遗传算法：遗传算法里需要选出两个人作为交配对象，交换两个人的数据，使用交叉规则得出计算结果
 * 	 2.2 校对工作：在A和B两个岗位分别录入银行的Excel数据，然后进行数据交换，确认录入数据是否正确
 * 	 2.3 针对的是两个线程
 * 3 使用：
 * 	 3.1 定义exchange对象：Exchanger<String> exchanger = new Exchanger<String>();
 * 	 3.2 数据交换：exchanger.exchange("data01");
 * @version 2018-3-14 上午10:18:00
 * @author 王勇
 */
public class ExchangeClient {

	private static Exchanger<String> exchanger = new Exchanger<String>();

	private static ExecutorService executorService = Executors.newFixedThreadPool(2);

	public static void main(String[] args) throws Exception{
//		exchangeInfo();
		exchangeInfoWithResult();
	}

	/**
	 * 信息交换 -> 无返回值
	 */
	private static void exchangeInfo(){
		executorService.execute(()->{
			try {
				String info = "wangyongr's info";
				System.out.println("线程1交换前的数据：" + info);
				//1.1 当线程执行到这里后会暂停，等待另一个线程也执行exchange方法，然后交换数据；
				String exchangeInfo = exchanger.exchange(info);
				System.out.println("线程1交换后的数据：" + exchangeInfo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		executorService.execute(()->{
			try {
				String info = "liaoy's info";
				System.out.println("线程2交换前的数据：" + info);
				String exchangeInfo = exchanger.exchange(info);
				System.out.println("线程2交换后的数据：" + exchangeInfo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		executorService.shutdown();
	}

	/**
	 * 信息交换 -> 又返回值
	 * @throws Exception
	 */
	private static void exchangeInfoWithResult() throws Exception{
		//1	定义exchange对象
		Future<?> result1 = executorService.submit(() -> {
			System.out.println("Before exchange:result1-->data01");
			//2	进行数据交换，如果线程1优先到达，则会等待线程2
			return exchanger.exchange("data01");
		});

		Future<String> result2 = executorService.submit(() -> {
			System.out.println("Before exchange:result2-->data02");
			//2.1	等待线程2睡眠之后到达exchange.exchange临界点才进行数据交换；
			return exchanger.exchange("data02");
		});

		//3	打印交换结果
		System.out.println("After exchange,result1:" + result1.get());
		System.out.println("After exchange,result2:" + result2.get());
		executorService.shutdown();
	}
}
