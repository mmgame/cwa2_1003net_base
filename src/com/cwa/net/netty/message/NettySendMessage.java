package com.cwa.net.netty.message;

import com.cwa.message.AbstractMessage;

import com.cwa.net.ClientSendMessage;
import com.google.protobuf.MessageLite;
/**
 * netty创建消息体
 * @author tzy
 *
 */

public class NettySendMessage extends ClientSendMessage{
	@Override
	public AbstractMessage createMessage(Object message) {
		int msgId = configMessage.getMsgId(message.getClass());
		AbstractMessage m=new NettyMessage();
		
		m.setCommandId(msgId);
		m.setBodyByte(((MessageLite) message).toByteArray());
		return m;
	}
}
