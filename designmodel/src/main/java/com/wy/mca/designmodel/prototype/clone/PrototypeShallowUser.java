package com.wy.mca.designmodel.prototype.clone;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrototypeShallowUser implements Cloneable {

	private int id;

	private String name;

	public final String DESC = "描述信息";
	
	private List<String> toys = new ArrayList<String>();

	public PrototypeShallowUser(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	protected PrototypeShallowUser clone() throws CloneNotSupportedException {
		//浅克隆
		return (PrototypeShallowUser) super.clone();
	}

	public void addToy(String toy) {
		toys.add(toy);
	}

	public List<String> getToys() {
		return toys;
	}

}
