package com.wy.mca.designmodel.respchain.client;

import com.wy.mac.java.designmodel.respchain.handler.AbstractHandlerTemplate;
import com.wy.mac.java.designmodel.respchain.handler.Handler01;
import com.wy.mac.java.designmodel.respchain.handler.Handler02;
import com.wy.mac.java.designmodel.respchain.handler.Handler03;
import com.wy.mac.java.designmodel.respchain.req.Request;
import com.wy.mac.java.designmodel.respchain.resp.Response;

/**
 * 职责链测试
 * 
 * @version 2018-1-7 下午5:51:39
 * @author 王勇
 */
public class Client {

	public void builderChain() {
		//1	构建请求体
		Request request = new Request();
		request.setReqInfo("I LOVE BB");
		request.setReqStatus(400);
		
		AbstractHandlerTemplate handler01 = new Handler01();
		AbstractHandlerTemplate handler02 = new Handler02();
		AbstractHandlerTemplate handler03 = new Handler03();
		
		//2	构建职责链
		handler01.setNextHandler(handler02);
		handler02.setNextHandler(handler03);
		
		//3	消息处理：从职责链第一个处理器开始处理
		Response response = handler01.handlerRequest(request);
		if(null != response){
			System.out.println(response.getRespInfo());
		}
	}
}
