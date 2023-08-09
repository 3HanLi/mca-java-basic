package com.mca.concurrent.container.queue.blocked;

/**
 * 阻塞队列
 * 1	特点：
 * 		1.1	支持阻塞的插入方法：当队列满时，队列会阻塞插入元素的线程，直到队列不满
 * 		1.2	支持阻塞的移除方法：在队列为空时，获取元素的线程会等待队列变为非空
 * 2	使用场景：
 * 		2.1	常用于生产者和消费者的场景，生产者是向队列里添加元素的线程，消费者是从队列里取元素的线程；阻塞队列其实就是将生产者/消费者的核心代码
 * 				封装起来，进行使用
 * 3	方法：
 * 		3.1	非阻塞队列中的几个主要方法
 * 				add(E e):将元素e插入到队列末尾，如果插入成功，则返回true；如果插入失败（即队列已满），则会【抛出异常】；
 * 				remove()：移除队首元素，若移除成功，则返回true；如果移除失败（队列为空），则会【抛出异常】；
 * 
 * 				offer(E e)：将元素e插入到队列末尾，如果插入成功，则返回true；如果插入失败（即队列已满），则返回false；
 * 				poll()：移除并获取队首元素，若成功，则返回队首元素；否则返回null；
 * 
 * 				peek()：获取队首元素，若成功，则返回队首元素；否则返回null：
 * 
 * 		建议：建议使用offer、poll和peek三个方法，不建议使用add和remove方法；
 * 				因为使用offer、poll和peek三个方法可以通过返回值判断操作成功与否，而使用add和remove方法却不能达到这样的效果
 * 		3.2	阻塞队列中的几个主要方法
 * 				put(E e):向队尾存入元素，如果队列满，则等待；
 * 				take():从队首取元素，如果队列为空，则等待；
 * 
 * 				offer(E e,long timeout, TimeUnit unit):向队尾存入元素，如果队列满，则等待一定的时间，当时间期限达到时，如果还没有插入成功，则返回false；
 * 				poll(long timeout, TimeUnit unit):用来从队首取元素，如果队列空，则等待一定的时间，当时间期限达到时，如果取到，则返回null；否则返回取得的元素
 * 
 * 4	阻塞队列
 * 		4.1	ArrayBlockingQueue：一个由数组结构组成的【有界】阻塞队列。 
 * 		4.2	LinkedBlockingQueue：一个由链表结构组成的【有界】阻塞队列
 * 		4.3	PriorityBlockingQueue：一个支持优先级排序的【无界】阻塞队列,以上2种队列都是先进先出队列，而PriorityBlockingQueue却不是，它会按照元素的优先级对元素进行排序，按照优先级顺序出队
 * 		4.4	DelayQueue：一个使用优先级队列实现的【无界】阻塞队列
 * 		4.5	SynchronousQueue：一个【不存储元素】的阻塞队列
 * 		4.6	LinkedTransferQueue：一个由链表结构组成的【无界】阻塞队列
 * 		4.7	LinkedBlockingDeque：一个由链表结构组成的【双向】阻塞队列
 * 
 * @author wangyong
 * @date 2018年12月3日 下午3:30:52
 */
public class BlockingQueueClient {

}
