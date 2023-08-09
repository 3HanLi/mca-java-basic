package com.wy.mca.designmodel.template.itf;

/**
 * 模板设计模式：核心接口
 * 
 * @version 2017-7-19 上午9:01:52
 * @author 王勇
 */
public interface IOperator<E> {

	E[] operate(E[] vos);
}
