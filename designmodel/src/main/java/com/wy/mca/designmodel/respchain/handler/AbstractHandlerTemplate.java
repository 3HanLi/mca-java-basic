package com.wy.mca.designmodel.respchain.handler;

import com.wy.mca.designmodel.respchain.req.Request;
import com.wy.mca.designmodel.respchain.resp.Response;

/**
 * 责任链：请求处理器抽象模板类
 * 	1	在抽象类中封装如下信息：
 * 		1.1	当前处理器的下一处理器
 * 		1.2	公共方法
 * 		1.3	抽象的请求处理方法
 * 
 * 	2	请求体携带状态信息，处理器在初始化的时候就需要指定状态信息，约束该执行器的职责范围
 * 
 * 	3	在client中构建职责链，在实际项目中将client作为中间层，专门处理职责链的构建；
 * 		如：中间层接收一个List集合，根据集合的元素构建职责链；
 * @version 2018-1-7 下午5:30:01
 * @author 王勇
 */
public abstract class AbstractHandlerTemplate {

	/**
	 * 定义下一个处理器
	 */
	private AbstractHandlerTemplate nextHandler;

	private int reqStatus;

	public final Response handlerRequest(Request request) {
		if (request.getReqStatus() == reqStatus) {
			return requestProcess(request);
		} else {
			if (null != nextHandler) {
				return nextHandler.handlerRequest(request);
			}
		}
		return null;
	}

	public void setNextHandler(AbstractHandlerTemplate nextHandler) {
		this.nextHandler = nextHandler;
	}

	public void setReqStatus(int reqStatus) {
		this.reqStatus = reqStatus;
	}

	public abstract Response requestProcess(Request request);

}
