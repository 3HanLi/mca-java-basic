package com.mca.concurrent.basic.sync;


import com.mca.concurrent.util.TimeUnitUtil;

/**
 * synchronized锁优化
 * 1) 尽量保证锁的粒度小一些，可以把锁的粒度放在方法中指定块
 * 2) 如果锁竞争比较频繁的话，不建议加细粒度的锁，还是加粗粒度的锁
 * @author wangyong01
 */
public class SyncBlock {

    private int count;

    public static void main(String[] args) {
        SyncBlock syncBlock = new SyncBlock();
        for (int i=0; i<100; i++){
            new Thread(()->{
                for (int j=0; j<100; j++){
                    syncBlock.syncMinBlock();
                }
            }).start();
        }
        TimeUnitUtil.sleepSeconds(5);
        System.out.println("count-->" + syncBlock.count);
    }

    public void syncMinBlock(){
        System.out.println(Thread.currentThread().getName() + "-->enter ");
        synchronized (this){
            this.count ++;
        }
        System.out.println(Thread.currentThread().getName() + "-->add ");
    }

}
