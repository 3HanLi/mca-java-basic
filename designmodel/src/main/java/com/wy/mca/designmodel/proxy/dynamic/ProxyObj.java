package com.wy.mca.designmodel.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类：动态代理类ProxyObj需要实现InvocationHandler接口
 * 类结构：
 * 1	定义真实对象
 * 2	invoke()：代理对象通过调用该方法，从而调用真实对象(realObj)的方法
 */
public class ProxyObj implements InvocationHandler{

	//1	定义真实对象
	private Object realObj;
	
	public ProxyObj(Object realObj) {
		super();
		this.realObj = realObj;
	}
	
	/**
	 * proxy：代理对象(proxy != realObj)
	 * method：指代的是我们所要调用真实对象的某个方法的Method对象
	 * args:调用真实对象某个方法时接受的参数
	 * 
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		//1	添加方法执行前动作
		System.out.println("Before invoke " + method + " execute operation1");
		
		//2	调用真实对象的方法，并返回调用结果
		Object result = method.invoke(realObj, args);
		
		//3	添加方法执行后动作
		System.out.println("After invoke " + method + " execute operation2");
		
		return result;
	}

}
