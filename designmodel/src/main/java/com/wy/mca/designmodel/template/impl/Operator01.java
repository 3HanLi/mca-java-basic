package com.wy.mca.designmodel.template.impl;

import com.wy.mac.java.designmodel.project.template.itf.IOperator;

/**
 * 核心逻辑01
 * 
 * @version 2017-7-19 下午2:39:26
 * @author 王勇
 */
public class Operator01<E> implements IOperator<E> {

	@Override
	public E[] operate(E[] vos) {
		System.out.println("Operate--->01");
		return vos;
	}

}
