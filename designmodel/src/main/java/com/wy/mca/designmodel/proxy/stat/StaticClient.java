package com.wy.mca.designmodel.proxy.stat;

/**
 * 静态代理
 * 
 * @version 2018-2-15 下午5:47:39
 * @author 王勇
 */
public class StaticClient {

	public void staticProxy() {
		// 1 定义真实对象
		IGamePlayer player = new GamePlayer("王勇");
		// 2 定义代理对象
		IGamePlayer proxy = new GamePlayerProxy(player);
		// 3 执行行为：通过代理对象执行真实对象的行为
		proxy.playGame();
		proxy.upgrate();
	}
}
