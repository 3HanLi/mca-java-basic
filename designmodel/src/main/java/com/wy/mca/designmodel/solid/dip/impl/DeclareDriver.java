package com.wy.mca.designmodel.solid.dip.impl;

import com.wy.mac.java.designmodel.solid.dip.itf.ICar;
import com.wy.mac.java.designmodel.solid.dip.itf.IDriver02;

/**
 * 通过接口声明的方式
 * 
 * @version 2018-1-18 下午3:32:05
 * @author 王勇
 */
public class DeclareDriver implements IDriver02 {

	@Override
	public void run(ICar car) {
		car.run();
	}

}
