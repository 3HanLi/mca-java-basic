package com.mca.concurrent.lock.cas;

import com.wy.mac.java.concurrent.util.TimeUnitUtil;

/**
 * @author wangyong01
 */
public class CasLockClient {

    private int shareNumber;

    public static void main(String[] args) {
        useSelfCasLock();
    }

    /**
     * 自定义锁验证
     */
    private static void useSelfCasLock(){
        CasLockClient casLockClient = new CasLockClient();

        CasLock casLock = new CasLock();
        for (int i=0; i<20; i++){
            new Thread(()->{
                casLock.myLock();
                for (int j=0; j<1000; j++){
                    casLockClient.shareNumber ++;
                }
                casLock.myUnLock();
            },"Thread-" + i).start();
        }
        TimeUnitUtil.sleepSeconds(1);
        System.out.println("CasLock shareNumber -->" + casLockClient.shareNumber);
    }

}
