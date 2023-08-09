package com.wy.mca.designmodel.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 多例模式：创建指定数量的对象（了解即可）
 * 
 * @version 2018-1-18 下午7:58:23
 * @author 王勇
 */
public class Multiton {

	/**
	 * 创建指定数量的实例
	 */
	private static final int instanceNum = 2;

	private static List<Multiton> list = new ArrayList<Multiton>();

	static {
		for (int i = 0; i < instanceNum; i++) {
			list.add(new Multiton());
		}
	}

	private Multiton() {

	}

	public static Multiton getInstance() {
		int nextInt = new Random().nextInt(2);
		return list.get(nextInt);
	}
}
