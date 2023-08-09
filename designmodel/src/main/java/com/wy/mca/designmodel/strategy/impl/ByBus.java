package com.wy.mca.designmodel.strategy.impl;

import com.wy.mac.java.designmodel.strategy.itf.IRoute;

/**
 * 坐汽车：通过坐汽车
 * 
 * @version 2017-10-11 下午6:00:15
 * @author 王勇
 */
public class ByBus implements IRoute {

	@Override
	public void travel() {
		System.out.println("Travel by Bus....");
	}

}
