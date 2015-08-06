package com.cwa.message;

/**
 * 消息类型接口
 * 
 * @author tzy
 * 
 *
 */
public interface IMessageType {
	/**
	 * mark长度
	 * 
	 * @return
	 */
	int getMarkLength();

	/**
	 * 检查mark
	 * 
	 * @param data
	 * @return
	 */
	boolean checkMark(Object data);

	/**
	 * 头长度
	 * 
	 * @return
	 */
	int getHeaderLen();

	/**
	 * 包大小位置
	 * 
	 * @return
	 */
	int getPackSizePosition();

	/**
	 * 包大小长度
	 * 
	 * @return
	 */
	int getPackSize();

	/**
	 * 创建消息
	 * 
	 * @return
	 */
	IMessage createMessage();
}
