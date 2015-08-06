package com.cwa.message;

import com.cwa.handler.IMessageHandler;

/**
 * 消息配置接口
 * @author tzy

 *
 */
public interface IConfigMessage{
	/**
	 * 通过message类型获取消息id
	 * @param msgClass
	 * @return
	 */
	int getMsgId(Class<?> msgClass);
}
