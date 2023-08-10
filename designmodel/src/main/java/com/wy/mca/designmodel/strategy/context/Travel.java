package com.wy.mca.designmodel.strategy.context;

import com.wy.mca.designmodel.strategy.itf.IRoute;

/**
 * 具体封装类Context：对算法进行封装
 * 
 * @version 2017-10-11 下午6:01:39
 * @author 王勇
 */
public class Travel {

	//策略对象的持有者
	private IRoute route;

	/**
	 * 通过构造器传入指定策略
	 * @param route
	 */
	public Travel(IRoute route) {
		super();
		this.route = route;
	}
	
	public void travel(){
		route.travel();
	}
}
