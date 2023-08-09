package com.wy.mca.designmodel.solid.lod;

import com.wy.mac.java.designmodel.solid.lod.friend.Member;
import com.wy.mac.java.designmodel.solid.lod.myself.Teacher;

import java.util.List;

public class LodClient {

	public void testLOD() {
		Teacher teacher = new Teacher();
		List<Member> memberList = teacher.accountMembers();
		System.out.println("Member size：" + memberList.size());
	}
}
