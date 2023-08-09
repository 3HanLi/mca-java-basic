package com.wy.mca.designmodel.proxy.dynamic;

/**
 * 实现类：这个类就是我们的真实对象
 */
public class Cat implements Animal{

	private String name;
	
	@Override
	public void bite() {
		System.out.println("Cat will bite you");
	}

	@Override
	public void eat() {
		System.out.println("Cat will eat rat");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
