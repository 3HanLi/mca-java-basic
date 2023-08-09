package com.mca.concurrent.util;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Phase 多层栅栏，相当于多个CyclicBarrier，每个栅栏对应一个方法，当多个线程到达指定栅栏时需要等待其他线程到达，才能继续到下个栅栏
 * 1 自定义栅栏 MarriagePhase extends Phaser
 * 2 定义每个栅栏对应的方法 Person
 * 3 自定义栅栏的使用
 *   3.1 指定参与的线程数量：
 *       phase.bulkRegister(parties)
 *   3.2 当线程组到达栅栏时等待，直到线程全部到达：
 *       phase.arriveAndAwaitAdvance()
 *
 * ->| ->| ->|
 * ->| ->| ->|
 * ->| ->| ->|
 *
 * @author wangyong01
 */
public class PhaseClient {

    public static void main(String[] args) throws Exception{
        int parties = 5;

        MarriagePhase phase = new MarriagePhase();
        phase.bulkRegister(parties);

        for (int i=0; i<parties; i++){
            final int j = i;
            new Thread(()->{
                Person person = new Person();
                person.arrive("name -" + j);
                phase.arriveAndAwaitAdvance();

                person.eat("name -" + j);
                phase.arriveAndAwaitAdvance();

                person.leave("name -" + j);
                phase.arriveAndAwaitAdvance();
            }).start();
        }
        TimeUnit.SECONDS.sleep(3);
    }

}

class MarriagePhase extends Phaser {
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        if (phase == 0){
            System.out.println("第0阶段：大家都到达");
            return false;
        }else if (phase == 1){
            System.out.println("第1阶段：大家都吃饭");
            return false;
        }else if (phase == 2){
            System.out.println("第2阶段：大家有序离开");
        }
        return true;
    }
}

class Person{

    public void arrive(String name){
        System.out.println(name + "-->到达");
    }

    public void eat(String name){
        System.out.println(name + "-->开吃");
    }

    public void leave(String name){
        System.out.println(name + "-->离开");
    }

}
