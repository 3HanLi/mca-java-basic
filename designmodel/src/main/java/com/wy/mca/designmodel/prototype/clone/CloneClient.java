package com.wy.mca.designmodel.prototype.clone;

/**
 * @author wangyong01
 */
public class CloneClient {

	public static void main(String[] args)throws Exception {
		testShallowClone();

		deepCopyUser();
	}

	/**
	 * 浅克隆
	 * @throws CloneNotSupportedException
	 */
	public static void testShallowClone() throws CloneNotSupportedException{
		PrototypeShallowUser user = new PrototypeShallowUser(1, "wangyongr");
		//1	克隆
		copyUser(user);
		//2	浅克隆：没有克隆引用类型toys，导致克隆出来的对象的toys和原对象的toys属性指向同一个内存地址；
		shallowCopyUser(user);
	}
	
	/**
	 * 用户的克隆：克隆
	 * @param user
	 * @throws CloneNotSupportedException
	 */
	public static void copyUser(PrototypeShallowUser user) throws CloneNotSupportedException {
		PrototypeShallowUser cloneUser = user.clone();
		System.out.println("id：" + cloneUser.getId() + ";name："
				+ cloneUser.getName());
	}
	
	/**
	 * 原型克隆：浅克隆PrototypeShallowUser，浅克隆不会克隆对象内部的引用类型数据
	 * @param user
	 * @throws CloneNotSupportedException
	 */
	public static void shallowCopyUser(PrototypeShallowUser user) throws CloneNotSupportedException{
		user.addToy("小飞机");
		PrototypeShallowUser cloneUser = user.clone();
		cloneUser.addToy("小火车");
		System.out.println(user.getToys());
	}
	
	/**
	 * 原型克隆：深度克隆
	 * @throws CloneNotSupportedException
	 */
	public static void deepCopyUser() throws CloneNotSupportedException{
		PrototypeDeepUser liaoy = new PrototypeDeepUser(2, "liaoying");
		liaoy.addToy("toy01");
		System.out.println("Liaoy'toys：" + liaoy.getToys());
		PrototypeDeepUser liaoy2 = liaoy.clone();
		liaoy2.addToy("toy02");
		System.out.println("Liaoy02'toys：" + liaoy2.getToys());
	}

}
