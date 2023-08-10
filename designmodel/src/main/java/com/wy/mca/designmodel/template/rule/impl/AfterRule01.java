package com.wy.mca.designmodel.template.rule.impl;

import com.wy.mca.designmodel.template.rule.itf.IRule;

/**
 * 后规则
 * 
 * @version 2017-7-19 下午2:39:56
 * @author 王勇
 */
public class AfterRule01<E> implements IRule<E> {

	@Override
	public E[] process(E[] vos) {
		System.out.println("After----->01");
		return vos;
	}

}
