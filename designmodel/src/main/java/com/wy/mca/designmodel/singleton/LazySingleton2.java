package com.wy.mca.designmodel.singleton;

/**
 * 懒汉模式（推荐使用：线程安全；延迟加载；效率较高）
 * 
 * @version 2018-1-18 下午7:56:04
 * @author 王勇
 */
public class LazySingleton2 {

	private static volatile LazySingleton2 singleton;

	private LazySingleton2() {

	}

	/**
	 * 双重检查
	 * @return
	 */
	public static LazySingleton2 getInstance() {
		if(null == singleton) {
			synchronized (LazySingleton2.class) {
				if(null == singleton) {
					singleton = new LazySingleton2();
				}
			}
		}
		return singleton;
	}
}
