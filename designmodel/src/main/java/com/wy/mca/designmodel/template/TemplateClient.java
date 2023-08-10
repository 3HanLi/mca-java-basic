package com.wy.mca.designmodel.template;

import com.wy.mca.designmodel.template.impl.TemplateImpl01;
import com.wy.mca.designmodel.template.impl.TemplateImpl02;
import com.wy.mca.designmodel.template.model.AbstractTemplate;

/**
 * 模板方法单元测试
 * 
 * @version 2017-7-10 下午4:27:01
 * @author 王勇
 */
public class TemplateClient {

	public void testTemp01(){
		AbstractTemplate templateImpl01 = new TemplateImpl01();
		templateImpl01.step();
	}
	
	public void testTemp02(){
		AbstractTemplate templateImpl02 = new TemplateImpl02();
		templateImpl02.step();
	}
}
