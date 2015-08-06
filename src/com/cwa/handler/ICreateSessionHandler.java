package com.cwa.handler;

import com.cwa.ISession;

/**
 * 建立连接处理
 * 
 * @author tzy
 */
public interface ICreateSessionHandler {
	
	/**
	 * 建立连接处理
	 * @param session
	 */
	void execute(ISession session);
}
