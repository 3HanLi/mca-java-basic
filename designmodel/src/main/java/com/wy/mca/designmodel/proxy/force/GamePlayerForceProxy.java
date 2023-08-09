package com.wy.mca.designmodel.proxy.force;

/**
 * 代理对象
 * 
 * @version 2018-2-16 下午8:18:20
 * @author 王勇
 */
public class GamePlayerForceProxy implements IGamePlayer {

	/**
	 * 真实对象
	 */
	private IGamePlayer gamePlayerReal;

	public GamePlayerForceProxy(IGamePlayer gamePlayerReal) {
		super();
		this.gamePlayerReal = gamePlayerReal;
	}

	@Override
	public void playGame() {
		gamePlayerReal.playGame();
	}

	@Override
	public void upgrate() {
		gamePlayerReal.upgrate();
	}

	@Override
	public IGamePlayer gamePlayerProxy() {
		return this;
	}

}
