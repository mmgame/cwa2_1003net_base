package com.cwa.handler;

import com.cwa.ISession;

/**
 * 断开连接处理
 * @author tzy
 *
 */
public interface IClosedSessionHandler {
	/**
	 * 断开连接处理
	 * @param session
	 */
	void execute(ISession session);
}
