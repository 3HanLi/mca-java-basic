package com.wy.mca.designmodel.singleton;

/**
 * 单例模式（可用）
 * 
 * @version 2018-1-18 下午7:54:57
 * @author 王勇
 */
public class HungerSingleton {

	/**
	 * 饿汉模式：在类加载的时候就实例化对象； 安全性：很高；
	 */
	private static final HungerSingleton INSTANCE = new HungerSingleton();

	private HungerSingleton() {

	}

	public static HungerSingleton getInstance() {
		return HungerSingleton.INSTANCE;
	}
}
