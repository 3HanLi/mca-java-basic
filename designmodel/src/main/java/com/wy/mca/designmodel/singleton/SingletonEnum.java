package com.wy.mca.designmodel.singleton;

/**
 * 使用枚举方式类创建对象
 * @author wangyong01
 */
public enum SingletonEnum {

	INSTANCE;
	
	public static SingletonEnum getInstance(){
		return SingletonEnum.INSTANCE;
	}
	
}
