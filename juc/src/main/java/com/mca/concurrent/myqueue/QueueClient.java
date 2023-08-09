package com.mca.concurrent.myqueue;

/**
 * 队列测试
 *
 * @author wangyong
 * @date 2018年11月20日 上午10:09:24
 */
public class QueueClient {

    public static void main(String[] args) {
        ProducerConsumerQueue queue = new ProducerConsumerQueue(10);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                    try {
                        queue.put(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            ).start();

            new Thread(() -> {
                    try {
                        queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            ).start();
        }
    }

}
