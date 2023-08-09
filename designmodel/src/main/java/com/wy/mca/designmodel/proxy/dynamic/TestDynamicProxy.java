package com.wy.mca.designmodel.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Description: 动态代理详解
 * 	类和接口：
 * 		在java的动态代理机制中，有两个重要的类或接口，一个是 InvocationHandler(Interface)、另一个则是 Proxy(Class)，
 * 		这一个类和接口是实现我们动态代理所必须用到的
 * 	InvocationHandler：
 * 		1	每一个"动态代理类"都必须要实现InvocationHandler这个接口，如：
 * 			public class ProxyObj implements InvocationHandler
 * 		2	每个代理类的"实例"都关联到了一个handler，当我们通过"代理对象"调用一个方法的时候，这个方法的调用就会被转发为
 * 			由InvocationHandler这个接口的 invoke方法来进行调用
 * 	Proxy:生成代理对象,常用的方法为：newProxyInstance
 * 		1	获取代理对象
 * 			Animal animalProxy = (Animal) Proxy.newProxyInstance(handler.getClass().getClassLoader(), animal.getClass().getInterfaces(), handler);
 *		2	调用真实对象的方法 从而进入ProxyObj.invoke()方法执行"1 前规则  2方法  3 后规则"
 *			animalProxy.bite();
 * 	应用场景：
 * 
 * 	参考资料：
 * 	http://www.cnblogs.com/xiaoluo501395377/p/3383130.html
 * 	http://www.cnblogs.com/flyoung2008/archive/2013/08/11/3251148.html
 * @author wy
 * @date 2016-3-2 下午4:22:40
 */
public class TestDynamicProxy {

	public static void main(String[] args) {
		//1	创建真实对象
		Cat animal = new Cat();
		//2	处理器接口
		InvocationHandler handler = new ProxyObj(animal);
		/*
		 * 方法：
		 * 	Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces,  InvocationHandler handler)
		 * 
		 * 参数说明：
		 * 	loader:一个ClassLoader对象，定义了由哪个ClassLoader对象来加载"代理类"：需要通过代理类来获取ClassLoader,如：
		 * 		   handler.getClass().getClassLoader()
		 * 	
		 * 	interfaces："真实对象"所实现的接口,用于代理对象调用接口中的方法（realSubject.getClass().getInterfaces()）,如：	
		 * 				获取真实对象的接口，animal.getClass().getInterfaces()
		 * 
		 * 	handler:表示代理对象(animalProxy)在调用方法的时候会关联到那个handler实例上，如：
		 * 			
		 */
		//3	通过代理类Proxy获取代理对象:com.sun.proxy.$Proxy0
//		Animal animalProxy = (Animal) Proxy.newProxyInstance(handler.getClass().getClassLoader(), animal.getClass().getInterfaces(), handler);
		Animal animalProxy = (Animal) Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class[]{Animal.class}, handler);
		
		System.out.println("Proxy class :" + animalProxy.getClass().getName());
		/*
		 * 	4	代理对象调用真实对象的方法，从而调用代理类(ProxyObj)的invoke方法，可以在该方法中添加"前规则"和"后规则"
		 */
		animalProxy.bite();
		animalProxy.eat();
	}
}
