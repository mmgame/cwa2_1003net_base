package com.cwa.handler;

import com.cwa.ISession;


/**
 * 
 * 处理消息接口
 * @author tzy

 */

public interface IMessageHandler<T> {
	/**
	 * 逻辑处理
	 * @param message
	 * @param session
	 */
	void handle(T message,ISession session);
}
