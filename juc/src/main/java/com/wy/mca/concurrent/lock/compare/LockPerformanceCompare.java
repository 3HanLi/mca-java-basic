package com.wy.mca.concurrent.lock.compare;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * 锁性能对比 LongAdder 优于 atomicInteger 优于 synchronized
 * 1) synchronized
 * 2) cas
 * 3) longAdder：底层相当于做了分段锁，效率较高。LongAdder会把线程数量分割成几段，对数据做递增操作，然后对结果进行汇总
 * @author wangyong01
 */
public class LockPerformanceCompare {

    private static int count;

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) throws Exception{
        syncAtomicLongAdder();
    }

    public static void syncAtomicLongAdder() throws Exception{
        Thread[] threads = new Thread[1000];

        sync(threads);
        atomic(threads);
        longAdder(threads);
    }

    private static void sync(Thread[] threads) throws Exception{
        for (int i=0; i<threads.length; i++){
            threads[i] = new Thread(()->{
                for (int j=0; j<100000; j++){
                    synchronized (LockPerformanceCompare.class){
                        count ++;
                    }
                }
            });
        }

        long start = System.currentTimeMillis();
//        for (Thread thread : threads){
//            thread.start();
//            //注意：如果在同一个for循环用join，会阻塞当前线程，相当于串型执行
//            thread.join();
//        }
        for (Thread thread : threads){
            thread.start();
        }
        for (Thread thread : threads){
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.println("Sync cost-->" + (end - start) + ";value-->" + count);
    }

    private static void atomic(Thread[] threads) throws Exception{
        for (int i=0; i<threads.length; i++){
            threads[i] = new Thread(()->{
                for (int j=0; j<100000; j++){
                    atomicInteger.getAndIncrement();
                }
            });
        }

        long start = System.currentTimeMillis();
        for (Thread thread : threads){
            thread.start();
        }
        for (Thread thread : threads){
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.println("Atomic cost-->" + (end - start) + ";value-->" + atomicInteger.get());
    }

    private static void longAdder(Thread[] threads) throws Exception{
        for (int i=0; i<threads.length; i++){
            threads[i] = new Thread(()->{
                for (int j=0; j<100000; j++){
                    longAdder.increment();
                }
            });
        }

        long start = System.currentTimeMillis();
        for (Thread thread : threads){
            thread.start();
        }
        for (Thread thread : threads){
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.println("LongAdder cost-->" + (end - start) + ";value-->" + longAdder.longValue());
    }
}
