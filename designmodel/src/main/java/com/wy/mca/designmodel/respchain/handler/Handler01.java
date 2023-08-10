package com.wy.mca.designmodel.respchain.handler;

import com.wy.mca.designmodel.respchain.req.Request;
import com.wy.mca.designmodel.respchain.resp.Response;

/**
 * 责任链入口：第一个请求处理器
 * 
 * @version 2018-1-7 下午5:45:03
 * @author 王勇
 */
public class Handler01 extends AbstractHandlerTemplate{

	public Handler01() {
		this.setReqStatus(300);
	}
	
	@Override
	public Response requestProcess(Request request) {
		Response response = new Response();
		response.setRespInfo(request.getReqInfo() + "：Handler From-->" + this.getClass());
		return response;
	}

}
