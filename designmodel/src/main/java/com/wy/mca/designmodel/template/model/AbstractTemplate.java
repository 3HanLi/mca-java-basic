package com.wy.mca.designmodel.template.model;

/**
 * 定义模板
 * @author wangyong01
 */
public abstract class AbstractTemplate {

	/**
	 * 基本方法：尽量设计为protected类型，供子类使用
	 */
	protected abstract void step1();

	protected abstract void step2();

	protected abstract boolean isExecuteNext();// 钩子函数：控制模板方法的执行逻辑

	protected abstract void step3();

	protected abstract void step4();

	/**
	 * 模板方法
	 */
	public final void step() {
		step1();
		step2();
		if (isExecuteNext()) {
			step3();
			step4();
		}
	}

}
