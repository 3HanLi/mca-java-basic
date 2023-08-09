package com.wy.mca.designmodel.prototype.serialize;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 原型模式
 * 1	核心：克隆对象
 * 2	扩展：可以通过序列化和反序列化来实现对象的克隆
 * 
 * @version 2017-11-12 下午9:15:42
 * @author 王勇
 */
@Data
public class PrototypeUser implements Serializable {

	private static final long serialVersionUID = 1059260244443378637L;

	private int id;

	private String name;

	public final String DESC = "描述信息";
	
	private List<String> toys = new ArrayList<String>();

	public PrototypeUser(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void addToy(String toy) {
		toys.add(toy);
	}

	public List<String> getToys() {
		return toys;
	}

}
