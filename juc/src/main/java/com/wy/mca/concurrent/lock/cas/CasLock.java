package com.wy.mca.concurrent.lock.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自定义锁：使用CAS
 * @author wangyong01
 */
public class CasLock {

    private int initValue;

    private AtomicReference<Integer> atomicReference = new AtomicReference(initValue);

    /**
     * 自定义加锁-使用Cas
     */
    public void myLock(){
        int currValue = atomicReference.get();
        atomicReference.compareAndSet(currValue,currValue + 1);
    }

    /**
     * 自定义解锁
     */
    public void myUnLock(){
        int currValue = atomicReference.get();
        atomicReference.compareAndSet(currValue,currValue - 1);
        currValue = atomicReference.get();
        if (currValue < initValue){
            throw new RuntimeException("解锁失败");
        }
    }

}
