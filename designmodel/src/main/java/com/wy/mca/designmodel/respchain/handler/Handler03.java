package com.wy.mca.designmodel.respchain.handler;

import com.wy.mac.java.designmodel.respchain.req.Request;
import com.wy.mac.java.designmodel.respchain.resp.Response;

/**
 * 责任链：第三个处理器
 * 
 * @version 2018-1-7 下午5:47:01
 * @author 王勇
 */
public class Handler03 extends AbstractHandlerTemplate {

	public Handler03() {
		this.setReqStatus(500);
	}
	
	@Override
	public Response requestProcess(Request request) {
		Response response = new Response();
		response.setRespInfo(request.getReqInfo() + "：Handler From-->" + this.getClass());
		return response;
	}

}
