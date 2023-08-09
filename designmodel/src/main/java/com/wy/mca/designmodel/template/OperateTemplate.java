package com.wy.mca.designmodel.template;

import com.wy.mac.java.designmodel.project.template.around.AroundProcessor;
import com.wy.mac.java.designmodel.project.template.itf.IOperator;

/**
 * 模板
 * 
 * @version 2017-7-19 下午2:34:20
 * @author 王勇
 */
public class OperateTemplate<E> implements IOperator<E> {

	// 核心逻辑
	private IOperator<E> operator;

	/**
	 * 核心逻辑执行的前后规则类
	 */
	private AroundProcessor<E> aroundProcessor = new AroundProcessor<E>();

	public OperateTemplate(IOperator<E> operator) {
		super();
		this.operator = operator;
	}

	public AroundProcessor<E> getAroundProcessor() {
		return aroundProcessor;
	}

	@Override
	public E[] operate(E[] vos) {
		E[] results = vos;

		// 1 核心逻辑前规则
		results = aroundProcessor.before(vos);

		/**
		 * 钩子函数：根据operator的值决定是否执行核心逻辑
		 */
		if (null != operator) {
			// 2 核心逻辑
			results = operator.operate(vos);
		}

		// 3 核心逻辑后规则
		results = aroundProcessor.after(vos);
		return results;
	}

}
