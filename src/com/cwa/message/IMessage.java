package com.cwa.message;

/**
 * 消息接口
 * @author tzy

 *
 */
public interface IMessage {
	/**
	 * 获取指令id
	 * 
	 * @return
	 */
	int getCommandId();

	/**
	 * 获取包体字节数组
	 * 
	 * @return
	 */
	byte[] getBodyByte();

	/**
	 * 设置包体信息
	 * 
	 * @param bodyByte
	 */
	void setBodyByte(byte[] bodyByte);

	/**
	 * 写入
	 * 
	 * @param out
	 */
	void write(Object out);

	/**
	 * 读取
	 * 
	 * @param in
	 */
	void readFields(Object in);
}
