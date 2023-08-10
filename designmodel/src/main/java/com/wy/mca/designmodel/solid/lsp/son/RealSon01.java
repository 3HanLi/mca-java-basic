package com.wy.mca.designmodel.solid.lsp.son;

import com.wy.mca.designmodel.solid.lsp.father.Father;

/**
 * 继承父类：拥有父类的属性、方法
 * 
 * @version 2017-8-3 上午10:05:56
 * @author 王勇
 */
public class RealSon01 extends Father {

	@Override
	public void canRide() {
		System.out.println("I am real :I can ride!!!");
	}

}
