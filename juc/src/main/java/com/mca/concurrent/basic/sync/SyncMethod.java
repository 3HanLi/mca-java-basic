package com.mca.concurrent.basic.sync;

import com.wy.mac.java.concurrent.util.TimeUnitUtil;

/**
 * synchronized特点总结
 * 1) 同步方法和异步方法可以同时执行
 * 2) synchronized是可重入锁
 * 3) synchronized方法抛出异常自动释放锁
 * @author wangyong01
 */
public class SyncMethod {

    public static void main(String[] args) {
        SyncMethod syncMethod = new SyncMethod();
        new Thread(syncMethod::sync).start();
        new Thread(syncMethod::general).start();
        TimeUnitUtil.sleepMinutes(1);
    }

    public synchronized void sync(){
        while (true){
            TimeUnitUtil.sleepSeconds(1);
            System.out.println("Invoke sync method");
            reSync();
        }
    }

    public synchronized void reSync(){
        System.out.println("Invoke reSync method");
    }

    public void general(){
        while (true){
            TimeUnitUtil.sleepSeconds(1);
            System.out.println("Invoke general method");
        }
    }

}
