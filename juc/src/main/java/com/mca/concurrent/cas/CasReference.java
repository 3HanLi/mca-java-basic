package com.mca.concurrent.cas;

import com.mca.concurrent.util.TimeUnitUtil;
import com.mca.concurrent.vo.ReferenceUser;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子引用
 * @author wangyong01
 */
public class CasReference {

    public static void main(String[] args) {
//        atomicReference();
//        showABA();
        atomicReferenceStamp();
    }

    /**
     * 原子引用AtomicReference
     */
    private static void atomicReference(){
        ReferenceUser referenceUser01 = new ReferenceUser(1,"wangyong01");
        ReferenceUser referenceUser02 = new ReferenceUser(2,"wangyong02");

        AtomicReference<ReferenceUser> atomicReference = new AtomicReference<>(referenceUser01);
        //expectValue：referenceUser01,updateValue：referenceUser02
        atomicReference.compareAndSet(referenceUser01,referenceUser02);
        ReferenceUser updateReferenceUser = atomicReference.get();
        System.out.println(updateReferenceUser);
    }

    /**
     * ABA问题
     */
    private static void showABA(){
        AtomicInteger atomicInteger = new AtomicInteger(1);

        new Thread(()->{
            atomicInteger.compareAndSet(1,2);
            atomicInteger.compareAndSet(2,1);
        },"Thread-01").start();

        TimeUnitUtil.sleepMillions(1);
        new Thread(()->{
            boolean replaceFlag = atomicInteger.compareAndSet(1, 2);
            System.out.println("替换" + (replaceFlag ? "成功" : "失败"));
        },"Thread-01").start();
    }

    /**
     * 使用AtomicReferenceStamp解决ABA问题
     */
    private static void atomicReferenceStamp(){
        int initValue = 1;
        int initVersion = 1;
        AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference(initValue,initVersion);

        new Thread(()->{
            stampedReference.compareAndSet(initValue,initValue + 1,stampedReference.getStamp(),stampedReference.getStamp()+1);
            stampedReference.compareAndSet(initValue + 1,initValue,stampedReference.getStamp() +1,stampedReference.getStamp() + 2);
        },"Thread-01").start();

        TimeUnitUtil.sleepMillions(1);
        new Thread(()->{
            boolean replaceFlag = stampedReference.compareAndSet(initValue,initValue+1,stampedReference.getStamp(),stampedReference.getStamp()+1);
            System.out.println("替换" + (replaceFlag ? "成功" : "失败"));
        },"Thread-01").start();
    }

}
