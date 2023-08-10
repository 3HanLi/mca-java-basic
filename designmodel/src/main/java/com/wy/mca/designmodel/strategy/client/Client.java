package com.wy.mca.designmodel.strategy.client;

import com.wy.mca.designmodel.strategy.context.Travel;
import com.wy.mca.designmodel.strategy.impl.ByTrain;

/**
 * 高层业务模块
 * 
 * @version 2017-10-11 下午6:05:47
 * @author 王勇
 */
public class Client {

	public void travel(){
		//01	指定具体策略
		ByTrain byTrain = new ByTrain();
		
		//02	封装：傳入策略
		Travel travel = new Travel(byTrain);
		
		//03	算法调用
		travel.travel();
	}
}
