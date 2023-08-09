package com.wy.mca.designmodel.prototype.clone;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 原型模式的核心：克隆对象；
 * 	1	相当于创建了一个对象，且不会调用构造函数；
 * 	2	克隆是直接从内存中读取二进制流进行对象的创建的，效率很高；
 * 	3	克隆的时候不会克隆对象内部的引用类型，如：数组，对象；
 * 
 * 浅克隆：只克隆对象内部的基本成员变量，如：String,int,double等；
 * 
 * 深克隆：需要自己实现以此克隆对象内部的引用类型，如：数组，对象；
 * 注意：克隆时，final类型的变量是不可以被克隆的；
 * 
 * @version 2017-8-25 下午2:06:57
 * @author 王勇
 */
@Data
public class PrototypeDeepUser implements Cloneable {

	private int id;

	private String name;

	public final String DESC = "描述信息";

	//这里toys没有向上转型，是为了方便克隆
	private ArrayList<String> toys = new ArrayList<>();

	public PrototypeDeepUser(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	protected PrototypeDeepUser clone() throws CloneNotSupportedException {
		//浅克隆
		PrototypeDeepUser user = (PrototypeDeepUser) super.clone();
		//深克隆
		user.toys = (ArrayList<String>) toys.clone();
		return user;
	}

	public void addToy(String toy) {
		toys.add(toy);
	}

	public void setToys(ArrayList<String> toys) {
		this.toys = toys;
	}
	
	public List<String> getToys() {
		return toys;
	}

}
