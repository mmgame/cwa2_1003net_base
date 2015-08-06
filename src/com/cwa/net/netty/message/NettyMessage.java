package com.cwa.net.netty.message;

import io.netty.buffer.ByteBuf;

import com.cwa.message.AbstractMessage;

/**
 * 消息类型
 * 
 * @author tzy
 */
public class NettyMessage extends AbstractMessage {
	@Override
	public void readFields(Object d) {
		ByteBuf data = (ByteBuf) d;
		commandId = data.readInt();
		packSize = data.readInt();
		bodyByte = new byte[packSize];
		data.readBytes(bodyByte);
	}

	@Override
	public void write(Object d) {
		ByteBuf data = (ByteBuf) d;
		data.writeBytes(NettyMessageType.MARK);
		data.writeInt(commandId);
		packSize = bodyByte.length;
		data.writeInt(packSize);
		data.writeBytes(bodyByte);
	}
}
