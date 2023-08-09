package com.wy.mca.designmodel.proxy.common;

/**
 * 普通代理
 * 
 * @version 2018-2-15 下午9:05:16
 * @author 王勇
 */
public class CommonClient {

	public void commonProxy() {
		/**
		 * 高层模块不知道真实角色是否存在，只知道代理对象
		 */
		IGamePlayer proxy = new GamePlayerProxy("proxy01");
		proxy.playGame();
		proxy.upgrate();
	}
}
