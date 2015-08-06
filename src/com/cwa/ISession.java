package com.cwa;

/**
 * seesion 接口
 * 
 * @author tzy
 * 
 * 
 */
public interface ISession {
	/**
	 * 写入
	 * 
	 * @param msg
	 */
	void write(Object msg);

	/**
	 * 关闭
	 * 
	 * @param immediately
	 */
	void close(boolean immediately);

	/**
	 * 获取sessionId
	 * 
	 * @return
	 */
	long getSessionId();

	/**
	 * 设置附加属性
	 * 
	 * @param key
	 * @param attachment
	 * @return
	 */
	void setAttachment(Object key, Object attachment);

	/**
	 * 
	 * 获取附加值
	 * 
	 * @param key
	 * @return
	 */
	Object getAttachment(Object key);

	/**
	 * 直接发送消息
	 * 
	 * @param key
	 * @return
	 */
	void send(Object msg);
}
