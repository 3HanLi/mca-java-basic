package com.wy.mca.concurrent.fiber;//package com.wy.mac.java.concurrent.fiber;
//
//import co.paralleluniverse.fibers.Fiber;
//import co.paralleluniverse.strands.SuspendableRunnable;
//
///**
// * @Description 测试线程 和 纤程 的效率
// * @Author wangyong01
// * @Date 2022/8/8 5:15 下午
// * @Version 1.0
// */
//public class FiberClient {
//
//    public static void main(String[] args) throws Exception {
//        int size = 10000;
//        Runnable runnable = () -> {
//            int result = 0;
//            for (int m = 0; m < 10000; m++) {
//                for (int i = 0; i < 200; i++) {
//                    result += i;
//                }
//            }
//        };
//        threadExecute(size, runnable);
//
//        SuspendableRunnable suspendableRunnable = () -> {
//            int result = 0;
//            for (int m = 0; m < 10000; m++) {
//                for (int i = 0; i < 200; i++) {
//                    result += i;
//                }
//            }
//        };
//        fiberExecute(size, suspendableRunnable);
//    }
//
//    public static void threadExecute(int size, Runnable runnable) throws Exception {
//        long start = System.currentTimeMillis();
//        Thread[] threads = new Thread[size];
//        for (int i = 0; i < size; i++) {
//            threads[i] = new Thread(runnable, "Thread-" + i);
//        }
//
//        for (int i = 0; i < size; i++) {
//            threads[i].start();
//        }
//
//        for (int i = 0; i < size; i++) {
//            threads[i].join();
//        }
//
//        long end = System.currentTimeMillis();
//        System.out.println("Thread Costs:" + (end - start));
//    }
//
//    public static void fiberExecute(int size, SuspendableRunnable runnable) throws Exception {
//        long start = System.currentTimeMillis();
//        Fiber<Void>[] fibers = new Fiber[size];
//
//        for (int i = 0; i < fibers.length; i++) {
//            fibers[i] = new Fiber("Fiber:" + i, runnable);
//        }
//        for (int i = 0; i < fibers.length; i++) {
//            fibers[i].start();
//        }
//
//        for (int i = 0; i < fibers.length; i++) {
//            fibers[i].join();
//        }
//
//        long end = System.currentTimeMillis();
//        System.out.println("Fiber costs:" + (end - start));
//    }
//
//}
