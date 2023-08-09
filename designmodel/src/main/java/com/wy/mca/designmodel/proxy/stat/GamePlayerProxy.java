package com.wy.mca.designmodel.proxy.stat;

/**
 * 代理对象
 * 
 * @version 2018-2-15 下午5:51:35
 * @author 王勇
 */
public class GamePlayerProxy implements IGamePlayer {

	/**
	 * 真实对象：被代理对象（需要高层模块将真实角色传递给代理对象），高层模块需要知道真实对象的存在
	 */
	private IGamePlayer gamePlayer;

	public GamePlayerProxy(IGamePlayer gamePlayer) {
		super();
		this.gamePlayer = gamePlayer;
	}

	@Override
	public void playGame() {
		gamePlayer.playGame();
	}

	@Override
	public void upgrate() {
		gamePlayer.upgrate();
	}

}
