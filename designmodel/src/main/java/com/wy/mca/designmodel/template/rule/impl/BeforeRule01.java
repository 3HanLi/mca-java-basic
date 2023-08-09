package com.wy.mca.designmodel.template.rule.impl;

import com.wy.mac.java.designmodel.project.template.rule.itf.IRule;

/**
 * 前规则
 * 
 * @version 2017-7-19 下午2:40:10
 * @author 王勇
 */
public class BeforeRule01<E> implements IRule<E> {

	@Override
	public E[] process(E[] vos) {
		System.out.println("Before----->01");
		return vos;
	}

}
