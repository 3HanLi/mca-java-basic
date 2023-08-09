package com.wy.mca.concurrent.lock.condition;

/**
 * 三-六模型测试
 * 
 * @author wangyong
 * @date 2018年11月19日 下午7:44:40
 */
public class ThreeSixCondition {

	public static void main(String[] args) {
		ThreeSixModel threeSixModel = new ThreeSixModel();
		for(int i=0; i<10; i++){
			new Thread(()->{
				try {
					threeSixModel.threeModel();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			},"Thread-A").start();
			
			new Thread(()->{
				try {
					threeSixModel.sixModel();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			},"Thread-B").start();
		}
	}
}
