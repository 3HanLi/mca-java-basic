package com.wy.mca.designmodel.proxy.force;

/**
 * 真实对象
 * 
 * @version 2018-2-16 下午8:15:43
 * @author 王勇
 */
public class GamePlayerReal implements IGamePlayer {

	private String playerName;

	/**
	 * 代理对象
	 */
	private IGamePlayer gamePlayerProxy;
	
	public GamePlayerReal(String playerName) {
		super();
		this.playerName = playerName;
	}

	@Override
	public void playGame() {
		if(isProxy()){
			System.out.println(playerName + "正在玩游戏");
		}else{
			System.out.println("未获取到代理对象，playGame终止.....");
		}
	}

	@Override
	public void upgrate() {
		if(isProxy()){
			System.out.println(playerName + "正在打怪升级");
		}else{
			System.out.println("未获取到代理对象，update终止......");
		}
	}

	/**
	 * 通过真实角色查找到代理对象
	 */
	@Override
	public IGamePlayer gamePlayerProxy() {
		gamePlayerProxy = new GamePlayerForceProxy(this);
		return gamePlayerProxy;
	}
	
	/**
	 * 真实对象是否有代理对象
	 * @return
	 */
	private boolean isProxy(){
		return null != gamePlayerProxy;
	}

}
