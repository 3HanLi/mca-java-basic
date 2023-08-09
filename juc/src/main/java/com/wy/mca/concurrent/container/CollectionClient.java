package com.wy.mca.concurrent.container;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 并发容器简介
 * @author wangyong01
 */
public class CollectionClient {

    public static void main(String[] args) {
//        showNotSafeList();
        showSafeList();
    }

    /**
     * List线程不安全
     */
    public static void showNotSafeList(){
        List<Integer> numberList = Lists.newArrayList();
        for (int i=0; i<10; i++){
            new Thread(()->{
                for (int j=0; j<100; j++){
                    numberList.add(j);
                }
            },"Thread-" + i).start();
        }
    }

    /**
     * 创建安全的List
     */
    public static void showSafeList(){
        List<Integer> numberList = Collections.synchronizedList(new ArrayList<>());
        for (int i=0; i<10; i++){
            new Thread(()->{
                for (int j=0; j<100; j++){
                    numberList.add(j);
                }
            },"Thread-" + i).start();
        }
        System.out.println("Collections.synchronizedList()-->Safe");

        //添加元素时加读写锁，获取元素时不加锁。底层维护了一个volatile数组，可以保证写成功立即可读
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        for (int i=0; i<10; i++){
            new Thread(()->{
                for (int j=0; j<100; j++){
                    copyOnWriteArrayList.add(j);
                }
            },"Thread-" + i).start();
        }
        System.out.println("CopyOnWriteArrayList --> safe");
    }

    /**
     * 创建安全的Map
     */
    public static void showSafeMap(){
        Hashtable hashtable = new Hashtable();
        Map<Object, Object> synchronizedMap = Collections.synchronizedMap(new HashMap<>(16));
        ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
    }
}
