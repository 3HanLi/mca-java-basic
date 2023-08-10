package com.wy.mca.designmodel.solid.dip.impl;

import com.wy.mca.designmodel.solid.dip.itf.ICar;
import com.wy.mca.designmodel.solid.dip.itf.IDriver;

/**
 * 构造函数传递依赖对象
 * 
 * @version 2017-7-6 下午9:16:38
 * @author 王勇
 */
public class ConstructDriver implements IDriver {

	private ICar car;

	public ConstructDriver(ICar car) {
		super();
		this.car = car;
	}

	@Override
	public void drive() {
		car.run();
	}

}
