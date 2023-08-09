package com.wy.mca.designmodel.solid.lod.friend;

import java.util.ArrayList;
import java.util.List;

/**
 * 朋友类
 * 
 * @version 2017-11-15 上午11:36:19
 * @author 王勇
 */
public class GroupLeader {

	public List<Member> accountMember(){
		List<Member> memberList = new ArrayList<Member>();
		for(int i=0; i<10; i++){
			memberList.add(new Member());
		}
		return memberList;
	}
}
