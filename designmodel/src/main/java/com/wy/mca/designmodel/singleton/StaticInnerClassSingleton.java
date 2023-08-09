package com.wy.mca.designmodel.singleton;

/**
 * 1 静态内部类的方式创建单例对象（推荐使用）
 *   1.1 这种方式跟饿汉式方式采用的机制类似，但又有不同，两者都是采用了类装载的机制来保证初始化实例时只有一个线程；
 *   1.2 StaticInnerClassSingleton在加载的时候并不会实例化，而是在调用getInstance方法的时候，加载InnerClass，来进行对象的实例化，达到了懒加载的功能
 *
 * @author wangyong01
 */
public class StaticInnerClassSingleton {

	private StaticInnerClassSingleton(){}
	
	private static class InnerClass{
		//2、由于是final类型的，所以只会被实例化一次
		private static final StaticInnerClassSingleton singleton = new StaticInnerClassSingleton();
	}
	
	public static StaticInnerClassSingleton getInstance(){
		//1、加载内部类时候实例话StaticInnerClassSingleton对象
		return InnerClass.singleton;
	}
}
