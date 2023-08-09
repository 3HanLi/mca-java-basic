package com.wy.mca.designmodel.solid.dip.impl;

import com.wy.mac.java.designmodel.solid.dip.itf.ICar;
import com.wy.mac.java.designmodel.solid.dip.itf.IDriver;

/**
 * Setter传递依赖对象
 * 
 * @version 2017-7-6 下午9:17:37
 * @author 王勇
 */
public class SetterDriver implements IDriver {

	private ICar car;

	public void setCar(ICar car) {
		this.car = car;
	}

	@Override
	public void drive() {
		car.run();
	}

}
