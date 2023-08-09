package com.wy.mca.designmodel.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 功能：创建子类对象(代理对象) 和 方法调用(intercept)
 */
public class CglibProxyObj implements MethodInterceptor{

	/**
	 * 通过父类创建子类对象，子类对象为代理对象
	 */
	public Object getInstance(Class<?> superClass){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(superClass);
		enhancer.setCallback(this);
		//创建子类实例
		Object childClass = enhancer.create();
		//代理对象：com.wy.dynamic.proxy.cglib.UserDaoImpl$$EnhancerByCGLIB$$880ea8d5
		System.out.println(childClass.getClass().getName());
		//true
		System.out.println(childClass instanceof UserDaoImpl);
		return childClass;
	}
	
	/**
	 * 功能：通过代理对象(子类)调用父类的方法
	 * 
	 * obj:目标类的实例
	 * method:目标类方法的反射对象
	 * args:方法的动态入参
	 * methodProxy:代理类实例
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		addBefore(); //织入方法
		//调用父类的方法
		Object returnObj = methodProxy.invokeSuper(obj, args);
		addAfter();	//织入方法
		return returnObj;
	}
	
	public void addBefore(){
		System.out.println("前规则");
	}
	
	public void addAfter(){
		System.out.println("后规则");
	}
}
