package com.wy.mca.concurrent.basic.volat;


import cn.hutool.core.util.StrUtil;
import com.wy.mca.concurrent.util.TimeUnitUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * volatile代码验证
 * @author wangyong01
 */
public class VolatileClient {

    private String notVolatile = "wangyong01";

    /**
     * volatile保证可见性
     */
    private volatile String isVolatile = "wangyong01";

    /**
     * volatile不保证原子性
     */
    private volatile int number;

    private AtomicInteger atomicNumber = new AtomicInteger();

    public void addNumber(){
        number ++;
    }

    public static void main(String[] args) throws Exception{
//        notVolatile();
//        isVolatile();
//        volatileNotAtomic();
        volatileResolve();
    }

    private static void notVolatile(){
        VolatileClient volatileClient = new VolatileClient();

        String modifiedNotVolatile = "wangyong02";

        new Thread(() -> {
            TimeUnitUtil.sleepSeconds(1);
            volatileClient.notVolatile = modifiedNotVolatile;
            System.out.println(String.format("修改volatile为: %s", volatileClient.notVolatile));
        },"AAA").start();

        while (StrUtil.equals(volatileClient.notVolatile,"wangyong01")){
        }

        //变量notVolatile不使用volatile修饰，第一个线程AAA修改变量notVolatile的值，main线程不能及时读取到notVolatile的值
        System.out.println(String.format("main线程获取变量notVolatile %s", volatileClient.notVolatile));
    }

    /**
     * volatile的可见性
     */
    private static void isVolatile(){
        VolatileClient volatileClient = new VolatileClient();
        String modifiedNotVolatile = "wangyong02";

        new Thread(() -> {
            TimeUnitUtil.sleepSeconds(1);
            volatileClient.isVolatile = modifiedNotVolatile;
            System.out.println(String.format("修改volatile为: %s", volatileClient.isVolatile));
        },"AAA").start();

        while (StrUtil.equals(volatileClient.isVolatile,"wangyong01")){
        }

        //变量isVolatile使用volatile修饰，第一个线程AAA修改变量isVolatile的值，main线程可以及时读取到isVolatile的值
        System.out.println(String.format("main线程获取变量notVolatile %s", volatileClient.isVolatile));
    }

    /**
     * Volatile不保证原子性
     * @throws Exception
     */
    private static void volatileNotAtomic() throws Exception{
        VolatileClient volatileClient = new VolatileClient();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i=0;i <20; i++){
            new Thread(()->{
                for (int j=0; j<1000; j++){
                    volatileClient.number ++;
                }
                countDownLatch.countDown();
            },"Thread-" +i).start();
        }
        countDownLatch.await();

        //如果volatile保证原子性，执行结果应该是20000，现在执行结果不为20000，说明volatile不保证原子性
        System.out.println(String.format("volatile变量number期望值:%s,实际值:%s", 20000, volatileClient.number));
    }

    /**
     * volatile不保证原子性解决方案
     * 1 使用Atomic原子类
     * 2 使用synchronized
     * @throws Exception
     */
    private static void volatileResolve() throws Exception{
        VolatileClient volatileClient = new VolatileClient();
        CountDownLatch countDownLatch = new CountDownLatch(20);

        for (int i=0;i <20; i++){
            new Thread(()->{
                for (int j=0; j<1000; j++){
                    volatileClient.atomicNumber.getAndIncrement();
                    synchronized (volatileClient){
                        volatileClient.number ++;
                    }
                }
                countDownLatch.countDown();
            },"Thread-" + i).start();
        }
        countDownLatch.await();

        //使用synchronized解决原子问题
        System.out.println(String.format("使用synchronized解决volatile的原子性问题,number期望值:%s,实际值:%s",20000,volatileClient.number));
        //使用AtomicInteger解决原子问题
        System.out.println(String.format("atomicNumber期望值:%s,实际值:%s", 20000, volatileClient.atomicNumber.get()));
    }


}
