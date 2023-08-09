package com.wy.mca.designmodel.proxy.cglib;

/**
 * 需要被代理的类，也就是父类，通过字节码技术创建这个类的子类，实现动态代理
 */
public class UserDaoImpl {

	public void add(int a,int b){
		System.out.println("a + b" + (a+b));
	}
	
	public void addUser(){
		System.out.println("A user added");
	}
}
