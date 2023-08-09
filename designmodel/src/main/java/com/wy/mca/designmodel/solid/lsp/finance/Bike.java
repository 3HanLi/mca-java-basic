package com.wy.mca.designmodel.solid.lsp.finance;

import com.wy.mac.java.designmodel.solid.lsp.father.Father;

public class Bike {

	Father fathcer;

	public Bike(Father fathcer) {
		super();
		this.fathcer = fathcer;
	}

	/**
	 * 传入父类对象
	 * 
	 * @param father
	 */
	public void canRide(Father father) {
		father.canRide();
	}
}
