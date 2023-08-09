package com.wy.mca.designmodel.singleton;

/**
 * 单列模式（可用）
 * 
 * @version 2018年9月10日 上午11:53:37
 * @author 王勇
 */
public class HungerSingleton2 {

	/**
	 * 饿汉模式：在类加载的时候就实例化对象； 安全性：很高；
	 */
	private static final HungerSingleton2 INSTANCE;

	static {
		INSTANCE = new HungerSingleton2();
	}

	public static HungerSingleton2 getInstance() {
		return HungerSingleton2.INSTANCE;
	}
}
