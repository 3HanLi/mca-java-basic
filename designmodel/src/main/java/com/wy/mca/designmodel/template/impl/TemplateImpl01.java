package com.wy.mca.designmodel.template.impl;

import com.wy.mca.designmodel.template.model.AbstractTemplate;

/**
 * 具体逻辑由子类实现
 * 
 * 缺点：子类影响父类
 * 
 * @version 2017-7-10 下午4:21:28
 * @author 王勇
 */
public class TemplateImpl01 extends AbstractTemplate {

	@Override
	protected void step1() {
		System.out.println("Temp01-->Step1");
	}

	@Override
	protected void step2() {
		System.out.println("Temp01-->Step2");
	}

	@Override
	protected boolean isExecuteNext() {
		return false;
	}

	@Override
	protected void step3() {
		System.out.println("Temp01-->Step3");
	}

	@Override
	protected void step4() {
		System.out.println("Temp01-->Step4");
	}

}
