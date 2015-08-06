package com.cwa.message;

import com.cwa.handler.IMessageHandler;
/**
 * 指令、消息、处理类封装
 * @author tzy
 *
 */
public class MessageConfig {
	private int commonId;
	private Class<?> message;
	private IMessageHandler<Object> messageHandler;

	public int getCommonId() {
		return commonId;
	}

	public void setCommonId(int commonId) {
		this.commonId = commonId;
	}

	public Class<?> getMessage() {
		return message;
	}

	public void setMessage(Class<?> message) {
		this.message = message;
	}

	public IMessageHandler<Object> getMessageHandler() {
		return messageHandler;
	}

	public void setMessageHandler(IMessageHandler<Object> messageHandler) {
		this.messageHandler = messageHandler;
	}
}
