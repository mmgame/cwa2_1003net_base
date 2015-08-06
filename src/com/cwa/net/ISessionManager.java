package com.cwa.net;

import java.util.List;

import com.cwa.ISession;
import com.cwa.message.ISendMessage;

/**
 * session管理类
 * 
 * @author tzy
 * 
 */
public interface ISessionManager extends ISendMessage {
	public static String Target_Key = "Target_Key";
	public static String UserId_Key = "UserId_Key";
	public static String Sender_Key = "Sender_Key"; // 发送者key
	public static String SessionId_Key = "SessionId_Key"; // sessionId的key
	public static String ISession_Key = "ISession_Key"; // ISession的key

	/**
	 * 新建session
	 * 
	 * @param session
	 */
	void insertSession(ISession session);

	/**
	 * 移除session
	 * 
	 * @param sessionId
	 */
	void removeSession(long sessionId);

	/**
	 * 移除所有连接
	 * 
	 * @return
	 */
	List<Object> removeAllSession();
}
