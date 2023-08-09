package com.wy.mca.designmodel.template.rule.itf;

/**
 * 前/后规则接口
 * 
 * @version 2017-7-19 上午11:06:14
 * @author 王勇
 */
public interface IRule<E> {

	E[] process(E[] vos);
}
