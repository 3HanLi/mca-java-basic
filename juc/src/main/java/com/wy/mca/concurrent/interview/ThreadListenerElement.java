package com.wy.mca.concurrent.interview;

import com.google.common.collect.Lists;
import com.wy.mca.concurrent.util.TimeUnitUtil;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：线程t1对集合list添加元素，t2监听list中元素的个数，如果list.size()=5，则t2执行结束
 * 1) 使用volatile：无法解决
 * @author wangyong01
 */
public class ThreadListenerElement {

    private static volatile List<Integer> valueList = Lists.newArrayList();

    public static void main(String[] args) {
//        volatileTerminate();
//        waitNotify();
//        countDownLatch();
//        lockSupport();
        lockCondition();
    }

    /**
     * 使用volatile修饰List集合   -->无法解决
     * 原因：Thread t1执行过快，Thread t2来不及监听
     * 测试：
     * 1) 让Thread t1每添加一次元素，就进行暂停，保证线程t2能进行读取
     * 2) 将监听的元素数量定义的大一些(比如：5000)
     *
     */
    private static void volatileTerminate(){
        new Thread(()->{
            for (int i=0; i<10; i++){
                valueList.add(i);
//                TimeUnitUtil.sleepMillions(1);
            }
        }).start();

        new Thread(()->{
            while (true){
                if (valueList.size() == 5){
                    break;
                }
            }
            System.out.println("Thread terminate");
        }).start();
    }

    /**
     * 通过wait-notify通知
     * 1) 线程t2向list添加元素，线程t1监听list元素个数
     * 2) 当list个数为5时，线程t1结束
     */
    private static void waitNotify(){
        List<Integer> waitEleList = Lists.newArrayList();
        final Object lock = new Object();
        final Random random = new Random();

        new Thread(() ->{
            synchronized (lock){
                while (waitEleList.size() != 5){
                    try {
                        lock.wait();
                    } catch (Exception ex){
                        System.out.println("Wait thread...");
                    }
                }
                lock.notify();
                System.out.println(Thread.currentThread().getName() + " terminate waitNotify");
            }
        },"t1").start();

        new Thread(()->{
            synchronized (lock){
                while (true){
                    int ele = random.nextInt(100);
                    System.out.println(Thread.currentThread().getName() + " add ele ->" + ele);
                    waitEleList.add(ele);
                    TimeUnitUtil.sleepSeconds(1);
                    if (waitEleList.size() == 5){
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (Exception ex){
                            System.out.println(Thread.currentThread().getName() + " release ");
                        }
                    }
                }
            }
        },"t2").start();
    }

    /**
     * 使用两个CountdownLatch解决
     */
    private static void countDownLatch(){
        List<Integer> waitEleList = Lists.newArrayList();
        CountDownLatch countDownLatch01 = new CountDownLatch(1);
        CountDownLatch countDownLatch02 = new CountDownLatch(1);

        final Random random = new Random();

        new Thread(() ->{
            while (waitEleList.size() != 5){
                try {
                    countDownLatch01.await();
                } catch (Exception ex){
                    System.out.println("CountdownLatch thread t1...");
                } finally {
                    countDownLatch02.countDown();
                }
            }
            System.out.println(Thread.currentThread().getName() + " terminate countDownLatch");
        },"t1").start();

        new Thread(()->{
            while (true){
                int ele = random.nextInt(100);
                waitEleList.add(ele);
                if (waitEleList.size() == 5){
                    countDownLatch01.countDown();
                    try {
                        countDownLatch02.await();
                    } catch (Exception ex){
                        System.out.println("CountdownLatch thread t2...");
                    }
                }
            }
        },"t2").start();
    }

    /**
     * 使用LockSupport
     */
    private static Thread thread02;

    private static void lockSupport(){
        List<Integer> waitEleList = Lists.newArrayList();
        final Random random = new Random();

        Thread thread01 = new Thread(() -> {
            System.out.println("t1--> enter");
            if (waitEleList.size() != 5){
                LockSupport.park();
            }
            System.out.println("t1--> continue");
            LockSupport.unpark(thread02);
        }, "t1");

        thread02 = new Thread(() -> {
            while (true){
                int ele = random.nextInt(100);
                TimeUnitUtil.sleepSeconds(1);
                System.out.println("Add element -->" + ele);
                waitEleList.add(ele);
                if (waitEleList.size() == 5){
                    LockSupport.unpark(thread01);
                    LockSupport.park();
                    System.out.println("t2 unpark");
                }
            }
        }, "t2");

        thread01.start();
        thread02.start();
    }

    /**
     * 使用Lock.newCondition
     */
    private static void lockCondition(){
        List<Integer> waitEleList = Lists.newArrayList();
        final Random random = new Random();

        ReentrantLock lock = new ReentrantLock();
        Condition condition01 = lock.newCondition();
        Condition condition02 = lock.newCondition();

        new Thread(()->{
            try {
                lock.lock();
                System.out.println("t1 enter");
                while (waitEleList.size() != 5){
                    condition01.await();
                }
                System.out.println("t1 leave");
                condition02.signal();
            } catch (Exception ex){

            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(()->{
            try {
                lock.lock();
                while (true){
                    int ele = random.nextInt(100);
                    TimeUnitUtil.sleepMillions(ele);
                    System.out.println("Add element -->" + ele);
                    waitEleList.add(ele);
                    if (waitEleList.size() == 5){
                        condition01.signal();
                        condition02.await();
                    }
                }
            } catch (Exception ex){

            } finally {
                lock.unlock();
            }
        }).start();

    }

}
