package com.wy.mca.designmodel.solid.lsp;

import com.wy.mca.designmodel.solid.lsp.father.Father;
import com.wy.mca.designmodel.solid.lsp.finance.Bike;
import com.wy.mca.designmodel.solid.lsp.son.RealSon01;

public class LspClient {

	public void test() {
		Father realSon01 = new RealSon01();
		Bike bike = new Bike(realSon01);
		bike.canRide(realSon01);
	}
}
