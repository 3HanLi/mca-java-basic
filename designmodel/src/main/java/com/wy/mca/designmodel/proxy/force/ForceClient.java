package com.wy.mca.designmodel.proxy.force;

/**
 * 强制代理测试
 * 
 * @version 2018-2-16 下午8:21:49
 * @author 王勇
 */
public class ForceClient {

	public void forceProxy(){
		IGamePlayer gamePlayerReal = new GamePlayerReal("王勇");
		IGamePlayer proxy = gamePlayerReal.gamePlayerProxy();
		proxy.playGame();
		proxy.upgrate();
	}
}
