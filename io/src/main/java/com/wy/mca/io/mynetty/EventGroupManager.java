package com.wy.mca.io.mynetty;

import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description boss和worker组
 * @Author wangyong01
 * @Date 2022/4/8 7:32 下午
 * @Version 1.0
 */
public class EventGroupManager {

    /**
     * boss/worker的Selector集合
     */
    public EventGroup[] eventGroups;

    /**
     * worker
     */
    public EventGroupManager worker;

    public boolean isBoss;

    private AtomicInteger incr = new AtomicInteger();

    public EventGroupManager(int selectorNum) {
        eventGroups = new EventGroup[selectorNum];
        for (int i=0; i<eventGroups.length; i++){
            eventGroups[i] = new EventGroup(this);
            new Thread(eventGroups[i]).start();
        }
        this.isBoss = true;
    }

    public EventGroupManager(int selectorNum, boolean isBoss) {
        eventGroups = new EventGroup[selectorNum];
        for (int i=0; i<eventGroups.length; i++){
            eventGroups[i] = new EventGroup(this);
            new Thread(eventGroups[i]).start();
        }
        this.isBoss = isBoss;
    }

    public void bind(int port){
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            System.out.println("Server start up on port :" + port);
            registerBossSelector(server);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void registerBossSelector(SelectableChannel channel) throws Exception {
        //1.1 需要唤醒select.select
        if (channel instanceof ServerSocketChannel){
            int length = this.eventGroups.length;
            int index = incr.getAndIncrement() % length;
            EventGroup eventGroup = eventGroups[index];
            eventGroup.linkedBlockingQueue.put(channel);

            eventGroup.selector.wakeup();
        }else {
            int length = this.worker.eventGroups.length;
            int index = incr.getAndIncrement() % length;
            EventGroup eventGroup = this.worker.eventGroups[index];
            eventGroup.linkedBlockingQueue.put(channel);

            eventGroup.selector.wakeup();
        }
    }

    public void setWorker(EventGroupManager worker) {
        this.worker = worker;
    }
}
