package com.wy.mca.designmodel.proxy.cglib;

/**
 * @Description: Cglib实现动态代理
 * 	JKD实现动态代理：
 * 		动态代理的对象必须实现一个或多个接口，对于没有定义接口的类无法实现动态代理
 * 	
 * 	CGLib动态代理：
 * 		当仅仅定义了类而没有定义接口时，JDK就不能满足动态代理了，需要用到CGLib;CGLib采用了非常底层的字节码技术，
 * 	其原理是通过字节码技术为 一个类创建子类(代理对象)，并在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑
 * 
 * 	JDK动态代理/CGLib动态代理 对比：
 * 		CGLib创建的动态代理对象性能比JDK创建的动态代理对象的性能高不少，但是CGLib在创建代理对象时所花费的时间却比JDK多得多，
 * 	所以对于单例的对象，因为无需频繁创建对象，用CGLib合适
 * 	总结：CGLib(性能高：耗时长) JDK(性能相对较低，耗时短)
 * @author wy
 * @date 2016-3-8 下午9:49:58
 */
public class TestDynamicProxyByCglib {

	public static void main(String[] args) {
		CglibProxyObj proxyObj = new CglibProxyObj();
		//获取代理对象(子类对象)
		UserDaoImpl userDao = (UserDaoImpl) proxyObj.getInstance(UserDaoImpl.class);
		System.out.println("userDao -- >" + userDao.getClass().getName());
		userDao.addUser();
	}
}
