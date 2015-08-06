package com.cwa.message;

import java.util.List;

import com.cwa.ISession;

/**
 * 消息发送接口
 * 
 * @author tzy
 * 
 */
public interface ISendMessage {
	/**
	 * 发送消息到所有人
	 * 
	 * @param sessionId
	 * @param message
	 */
	void sendBroadcastMessage(Object message);

	/**
	 * 发送消息
	 * 
	 * @param sessionId
	 * @param message
	 */
	void sendMessage(Object message, ISession session);

	/**
	 * 发送消息到sessionId
	 * 
	 * @param sessionId
	 * @param message
	 */
	void sendMessageBySessionId(Object message, long sessionId);

	/**
	 * 发送给指定人
	 * 
	 * @param sessionId
	 * @param message
	 */
	void sendMessageBySessionIds(Object message, List<Long> sessionIds);
}
