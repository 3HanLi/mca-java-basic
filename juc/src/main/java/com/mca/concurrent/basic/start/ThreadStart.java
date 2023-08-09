package com.mca.concurrent.basic.start;


import com.mca.concurrent.util.TimeUnitUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程启动的3中方式
 * 1) 实现Runnable接口
 * 2) 继承Thread类
 * 3) 线程池
 * @author wangyong01
 */
public class ThreadStart {

    public static void main(String[] args) {
        new Thread(()->{
            System.out.println("Thread start method-01");
        }).start();

        new MyThread().start();

        ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
        threadExecutor.execute(()->{
            System.out.println("Thread start method-03");
        });

        TimeUnitUtil.sleepSeconds(1);
    }

    private static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("Thread start method-02");
        }
    }

}
