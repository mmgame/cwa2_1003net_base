package com.cwa.net.mina.filter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.message.IMessage;
import com.cwa.message.IMessageType;

/**
 * 将接收到的信息解码
 * 
 * @
 */
public class MessageReceiveDecoder extends CumulativeProtocolDecoder {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private int maxPackSize;
	private IMessageType messageType;

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		// 考虑以下几种情况：
		// 1. 一个ip包中只包含一个完整消息
		// 2. 一个ip包中包含一个完整消息和另一个消息的一部分
		// 3. 一个ip包中包含一个消息的一部分
		// 4. 一个ip包中包含两个完整的数据消息或更多（循环处理在父类的decode中）

		if (in.remaining() < messageType.getMarkLength()) {
			return false;
		}

		// 检验标记
		if (!messageType.checkMark(in)) {
			in.clear();
			session.close(true);
			throw new RuntimeException("协议错误! [Address=" + session.getRemoteAddress().toString() + "]");
		}

		if (!isCompleted(in)) {
			// 恢复到开头cwa
			in.position(in.position() - messageType.getMarkLength());
			// 网络读取
			return false;
		}

		// 获取会话节点与消息节点的传输信息
		IMessage msg = messageType.createMessage();
		msg.readFields(in);
		out.write(msg);

		if (in.remaining() > 0) {
			// 进行下一次解析
			return true;
		}
		// 网络读取
		return false;
	}

	public boolean isCompleted(IoBuffer in) {
		// 协议头还不完整
		if (in.remaining() < messageType.getHeaderLen()) {
			return false;
		}
		in.mark();
		in.position(in.position() + messageType.getPackSizePosition());
		boolean available = in.prefixedDataAvailable(messageType.getPackSize(), maxPackSize);
		in.reset();

		return available;
	}

	public static void main(String args[]) {
		IoBuffer buff = IoBuffer.allocate(14);
		buff.putChar('1');
		buff.putChar('2');
		buff.putChar('3');
		buff.putChar('4');
		buff.putChar('5');
		buff.putChar('6');
		buff.putChar('7');

		buff.flip();
		System.out.println(buff.getChar());
		System.out.println(buff.getChar());
		buff.mark();
		buff.position(buff.position() + 2);
		System.out.println("------------------");
		System.out.println(buff.getChar());
		System.out.println(buff.getChar());
		buff.position(buff.position() - 2);
		System.out.println("------------------");
		System.out.println(buff.getChar());
		System.out.println(buff.getChar());
		System.out.println("=====================");
		buff.reset();

		IoBuffer buff2 = IoBuffer.allocate(14);
		buff2.setAutoExpand(true);
		buff2.put(buff);

		buff2.flip();

		System.out.println(buff2.getChar());
		System.out.println(buff2.getChar());
	}

	// ----------------------------------------------------------
	public void setMaxPackSize(int maxPackSize) {
		this.maxPackSize = maxPackSize;
	}

	public void setMessageType(IMessageType messageType) {
		this.messageType = messageType;
	}

}
