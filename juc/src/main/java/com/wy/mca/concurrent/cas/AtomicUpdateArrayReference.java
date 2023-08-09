package com.wy.mca.concurrent.cas;

import com.google.common.collect.Lists;
import com.wy.mca.concurrent.vo.User;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 原子引用数组
 *
 * @author wangyong01
 */
public class AtomicUpdateArrayReference {

	public static void main(String[] args) throws InterruptedException {
		List<User> userList = Lists.newArrayList();
		for (int i = 0; i < 3; i++) {
			User user = new User();
			user.setId(i);
			user.setName("name" + i);
			userList.add(user);
		}

		//1	原子更新数组-引用类型
		AtomicReferenceArray<User> referenceArray = new AtomicReferenceArray<>(userList.toArray(new User[0]));
		for(int i=0; i<10; i++){
			new Thread(() -> {
				int length = referenceArray.length();
				for (int j=0; j<length; j++){
					referenceArray.get(j).setId(j * 2);
					referenceArray.get(j).setName("--->" + j);
				}
			}).start();
		}
		
		TimeUnit.SECONDS.sleep(1);
		
		System.out.println(referenceArray);
	}
}

