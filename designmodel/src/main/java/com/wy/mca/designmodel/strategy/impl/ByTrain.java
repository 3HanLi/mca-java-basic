package com.wy.mca.designmodel.strategy.impl;

import com.wy.mac.java.designmodel.strategy.itf.IRoute;

/**
 * 具体策略：通过坐火车
 * 
 * @version 2017-10-11 下午5:59:33
 * @author 王勇
 */
public class ByTrain implements IRoute {

	@Override
	public void travel() {
		System.out.println("Travel by Train....");
	}

}
