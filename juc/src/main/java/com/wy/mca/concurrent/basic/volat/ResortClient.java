package com.wy.mca.concurrent.basic.volat;

/**
 * 指令重拍序
 * @author wangyong01
 */
public class ResortClient {

    private int a;

    private boolean flag;

    /**
     * 1 在多线程下执行method01和method02，可能会对method01的变量进行重拍序：flag = true; a =1
     * 2 此时可能会出现如下情况：
     *   2.1 线程Thread-01执行method01，执行flag=true，然后线程挂起
     *   2.2 线程Thread-02执行method02，由于flag=true，此时a = 5;而a的期望值为6（先修改a=1后再进行a += 5）
     */
    public void method01(){
        a = 1;
        flag = true;
    }

    public void method02(){
        if (flag){
            a += 5;
            System.out.println("a = " + a);
        }
    }

}
