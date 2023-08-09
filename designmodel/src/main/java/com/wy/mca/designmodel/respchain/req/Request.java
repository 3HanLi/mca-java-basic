package com.wy.mca.designmodel.respchain.req;

import lombok.Data;

/**
 * 责任链：请求体
 * 
 * @version 2018-1-7 下午5:28:47
 * @author 王勇
 */
@Data
public class Request {

	/**
	 * 请求状态
	 */
	private int reqStatus;

	/**
	 * 请求信息
	 */
	private String reqInfo;

}
