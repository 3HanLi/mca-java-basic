package com.wy.mca.designmodel.singleton;

/**
 * 懒汉模式【和LazySingleton2的效果一样，不推荐这种写法】
 * 
 * @version 2018-1-18 下午7:56:04
 * @author 王勇
 */
public class LazySingleton {

	private static LazySingleton singleton;

	private LazySingleton() {

	}

	public static LazySingleton getInstance() {
		if(null != singleton){
			return singleton;
		}
		synchronized (LazySingleton.class) {
			if (null == singleton) {
				/**
				 * 懒汉模式：在获取实例对象的时候才创建
				 */
				singleton = new LazySingleton();
			}
		}
		return singleton;
	}
}
