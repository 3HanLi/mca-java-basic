package com.wy.mca.concurrent.lock;


import com.wy.mca.concurrent.util.TimeUnitUtil;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport：是Lock的底层实现，用于阻塞当前线程
 * 1) 主线程通过指定LockSupport.unpark(t)来让线程t继续执行
 * 2) 如果LockSupport.unpark(t)在LockSupport.park之前执行的话，那么park会不生效
 * @author wangyong01
 */
public class LockSupportClient {

    public static void main(String[] args) throws Exception{
        lockSupport();
    }

    private static void lockSupport() throws Exception{
        Thread thread = new Thread(() -> {
            for (int i=0; i<10; i++){
                if (i == 5){
                    System.out.println("LockSupport park");
                    LockSupport.park();
                }
                TimeUnitUtil.sleepSeconds(1);
                System.out.println("Print-->" + i);
            }
        });

        thread.start();
        TimeUnitUtil.sleepSeconds(10);
        LockSupport.unpark(thread);
        System.out.println("main thread end");
    }
}
