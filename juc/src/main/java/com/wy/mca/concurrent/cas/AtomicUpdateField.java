package com.wy.mca.concurrent.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 1 原子更新字段类：AtomicIntegerFieldUpdater、AtomicLongFieldUpdater、AtomicStampedReference
 *   1.1 作用：原子的更新某个类里的某个字段
 * 	 1.2 使用：
 * 		 a) 定义原子类更新器
 * 			AtomicIntegerFieldUpdater<User> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "id");
 * 		 b) 更新的字段类型必须满足：可见性和原子性
 * 			volatile int id;
 * @author wangyong
 * @date 2018年12月28日 下午3:10:38
 */
public class AtomicUpdateField {

	public static void main(String[] args) throws InterruptedException {
		AtomicIntegerFieldUpdater<AtomicFieldUser> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(AtomicFieldUser.class, "id");
		AtomicFieldUser user = new AtomicFieldUser(0,"wangyong");
		
		for(int i=0; i<10; i++){
			new Thread(() -> {
				fieldUpdater.getAndAdd(user,1);
			}).start();
		}

		TimeUnit.SECONDS.sleep(1);
		System.out.println("original user:" + user.getId());
		System.out.println("update user:" + fieldUpdater.get(user));
	}
}

