package com.cwa.net.mina.message;

import com.cwa.message.AbstractMessage;
import com.cwa.net.ClientSendMessage;
import com.google.protobuf.MessageLite;
/**
 * mina创建消息体
 * @author tzy
 *
 */
public class MinaSendMessage extends ClientSendMessage{
	
	@Override
	public AbstractMessage createMessage(Object message) {
		int msgId = configMessage.getMsgId(message.getClass());
		AbstractMessage m=new MinaMessage();
		
		m.setCommandId(msgId);
		m.setBodyByte(((MessageLite) message).toByteArray());
		return m;
	}
}
