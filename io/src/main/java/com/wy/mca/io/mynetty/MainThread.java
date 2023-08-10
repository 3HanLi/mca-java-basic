package com.wy.mca.io.mynetty;

/**
 * @Description 模仿Netty主启动类
 * @Author wangyong01
 * @Date 2022/4/8 7:31 下午
 * @Version 1.0
 */
public class MainThread {

    public static void main(String[] args) {
        //1 创建boss，负责监听
        EventGroupManager boss = new EventGroupManager(3);

        //2 创建worker，负责处理 read , write
        EventGroupManager worker = new EventGroupManager(4, false);
        boss.setWorker(worker);

        //3 绑定端口号
        boss.bind(10001);
        boss.bind(10002);
        boss.bind(10003);
    }

}
