package com.wy.mca.designmodel.respchain.handler;

import com.wy.mca.designmodel.respchain.req.Request;
import com.wy.mca.designmodel.respchain.resp.Response;

/**
 * 责任链：第二个处理器
 * 
 * @version 2018-1-7 下午5:45:25
 * @author 王勇
 */
public class Handler02 extends AbstractHandlerTemplate{

	public Handler02() {
		this.setReqStatus(400);
	}
	
	@Override
	public Response requestProcess(Request request) {
		Response response = new Response();
		response.setRespInfo(request.getReqInfo() + "：Handler From-->" + this.getClass());
		return response;
	}

}
