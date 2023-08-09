package com.wy.mca.designmodel.prototype.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeClient {

	public static void main(String[] args) throws Exception{
		testSerialize();
	}

	public static void testSerialize() throws Exception{
		//1	原始用户对象
		PrototypeUser user01 = new PrototypeUser(1, "wangyong");
		user01.addToy("toy01");
		
		//2	克隆后的用户对象
		PrototypeUser user02 = cloneUserBySerialize(user01);
		user02.addToy("toy02");
		
		//3	对比引用型对象：toys
		System.out.println("User01'toys：" + user01.getToys());
		System.out.println("User02'toys：" + user02.getToys());
	}
	
	/**
	 * 克隆用户对象：通过序列化的方式
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static PrototypeUser cloneUserBySerialize(PrototypeUser user) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(user);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream bis = new ObjectInputStream(bais);
		PrototypeUser user02 = (PrototypeUser) bis.readObject();
		return user02;
	}
}
