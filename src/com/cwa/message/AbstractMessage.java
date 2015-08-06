package com.cwa.message;

import java.util.Arrays;

import com.cwa.net.mina.message.MinaMessageType;

/**
 * 消息由四部分组成：
 * 1、MARK（cwa，三字节）
 * 2、消息ID（四字节）
 * 3、后续长度（四字节）
 * 4、后续内容（Protobuf）
 * ----------------------------------------------
 * | cwa | MSG ID | BODY LEN |      BODY       |
 * ----------------------------------------------
 */
public abstract class AbstractMessage implements IMessage {
	/**
	 * 命令号 4byte
	 */
	protected int commandId;
	/**
	 * 包体总长度 4byte
	 */
	protected int packSize;
	/**
	 * 包体数据
	 */
	protected byte[] bodyByte = new byte[0];
 
	@Override
	public abstract void readFields(Object d);
	@Override
	public abstract void write(Object d);

	@Override
	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	@Override
	public byte[] getBodyByte() {
		return bodyByte;
	}

	public int getPackSize() {
		return packSize;
	}

	public void setPackSize(int packSize) {
		this.packSize = packSize;
	}

	public void setBodyByte(byte[] bodyByte) {
		this.bodyByte = bodyByte;
	}

	@Override
	public String toString() {
		return "Message [mark=" + new String(MinaMessageType.MARK) + ", commandId=" + commandId + ",body" + Arrays.toString(bodyByte)
				+ "]";
	}
	
	public static void main(String[] args) {
		byte[] bodyByte = new byte[10];
		for (int i = 0; i < bodyByte.length; i++) {
			bodyByte[i]=(byte) i;
		}
		System.out.println(bodyByte[4]);
	}
}
