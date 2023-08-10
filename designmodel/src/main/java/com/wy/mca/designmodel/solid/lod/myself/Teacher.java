package com.wy.mca.designmodel.solid.lod.myself;


import com.wy.mca.designmodel.solid.lod.friend.GroupLeader;
import com.wy.mca.designmodel.solid.lod.friend.Member;

import java.util.List;

/**
 * 迪米特法则：只和朋友交流；
 * 朋友类定义：出现在成员变量、方法的输入输出参数中的类称为成员朋友类，而出现在方法体内部的类不属于朋友类；
 * 
 * @version 2017-11-15 上午11:36:13
 * @author 王勇
 */
public class Teacher {

	/**
	 * 1 	Teacher对象需要统计成员信息，但是不能直接和成员对象交流，通过朋友类GroupLeader进行交流；
	 * 2	GroupLeader对象和Member进行耦合，统计详细信息反馈给Teacher对象
	 */
	GroupLeader leader = new GroupLeader();

	public List<Member> accountMembers() {
		List<Member> memberList = leader.accountMember();
		return memberList;
	}
}
