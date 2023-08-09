package com.wy.mca.concurrent.cas;


import com.wy.mca.concurrent.util.TimeUnitUtil;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 1 原子更新数组
 * 	 1.1 基本类：AtomicIntegerArray、AtomicLongArray、AtomicReferenceArray
 * 	 1.2 AtomicIntegerArray使用：
 * 		 a) 定义数组：int[] original = new int[]{10,12};
 * 		 b) 将数组作为参数传递给AtomicIntegerArray：
 * 			AtomicIntegerArray later = new AtomicIntegerArray(original);
 *			AtomicIntegerArray会将当前数组复制一份，所以对AtomicIntergerArray修改不会影响原数组的值
 * @author wangyong
 * @date 2018年12月28日 上午11:22:38
 */
public class AtomicUpdateArray {

	public static void main(String[] args) {
		//1.1	定义初始数组
		int[] original = new int[]{10,12};
		//1.2	AtomicIntegerArray将当前数组拷贝一份
		AtomicIntegerArray later = new AtomicIntegerArray(original);

		for(int i=0; i<10; i++){
			new Thread(() -> {
				later.getAndAdd(0, 1);
				later.getAndAdd(1,1);
			}).start();
		}
		TimeUnitUtil.sleepMillions(1);
		//3	对AtomicIntegerArray修改，不会影响原数组对值
		System.out.println("original index[0]->" + original[0] + "；original index[1]->" + original[1]);
		System.out.println("later index[0]->" + later.get(0) + "；later index[1]->" + later.get(1));
	}
	
}

