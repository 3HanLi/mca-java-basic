package com.wy.mca.concurrent.basic.volat;

/**
 * @author wangyong01
 */
public class VolatileSingleton {

    /**
     * 1 创建对象的三个步骤：
     *   1.1 memory=allocate()：分配对象内存空间
     *   1.2 instance(memory)：初始化对象
     *   1.3 instance=memory：设置instance指向刚刚分配的内存地址, 此时instance != null (重点)
     *
     * 2 如果没有volatile修饰的话，那么instance在创建的时候，可能发生指令重排序，如果1.1和1.3发生重排序的话，那么就混存在这种情况
     *   2.1 第一个线程执行1.1分配内存空间之后，设置instance在内存中的地址，然后线程被挂起
     *   2.2 第二个线程在获取对象的时候，会拿到一个没有初始化完成的对象，导致访问错误
     *
     * 3 解决方案：使用volatile修饰，禁止指令重排序
     */
    private static volatile VolatileSingleton instance;

    private VolatileSingleton(){
        System.out.println("单例模式创建对象---->");
    }

    public static VolatileSingleton getInstance(){
        if (null == instance){
            synchronized (VolatileSingleton.class){
                if (null == instance){
                    instance = new VolatileSingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i=0; i<10000; i++){
            new Thread(() ->{
                VolatileSingleton.getInstance();
            },"Thread-" + i).start();
        }
    }
}
