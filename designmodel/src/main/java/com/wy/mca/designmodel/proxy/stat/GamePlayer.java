package com.wy.mca.designmodel.proxy.stat;

/**
 * 真实对象
 * 
 * @version 2018-2-15 下午5:51:23
 * @author 王勇
 */
public class GamePlayer implements IGamePlayer {

	private String playerName;

	public GamePlayer(String playerName) {
		super();
		this.playerName = playerName;
	}

	@Override
	public void playGame() {
		System.out.println(playerName + "正在玩游戏");
	}

	@Override
	public void upgrate() {
		System.out.println(playerName + "正在打怪升级");
	}

}
