package com.wy.mca.designmodel.proxy.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 代理对象
 * 
 * @version 2018-2-15 下午5:51:35
 * @author 王勇
 */
public class GamePlayerProxy implements IGamePlayer {

	/**
	 * 代理对象要代理的真实角色集合；map可以根据key值获取要代理的真实对象
	 */
	private Map<String, IGamePlayer> realMap = new HashMap<String, IGamePlayer>();

	/**
	 * 真实角色
	 */
	private IGamePlayer gamePlayer;

	/**
	 * 代理对象持有真实对象，高层模块不知道真实对象的存在；
	 * 
	 * @param key
	 */
	public GamePlayerProxy(String key) {
		super();
		this.gamePlayer = realMap.get(key);
		if (null == this.gamePlayer) {
			this.gamePlayer = new GamePlayer("wangyongr");
		}
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
