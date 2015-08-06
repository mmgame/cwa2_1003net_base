package com.cwa.net.mina.heartbeat;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.message.AbstractMessage;
import com.cwa.net.mina.message.MinaMessage;

/**
 * @author Administrator
 * 
 */
public class HeartBeatMessageFactory implements KeepAliveMessageFactory {
	private static final Logger logger = LoggerFactory.getLogger(HeartBeatMessageFactory.class);

	// 心跳指令号
	private int heartbeatId;

	@Override
	public boolean isRequest(IoSession session, Object message) {
		AbstractMessage m = (AbstractMessage) message;
		if (m.getCommandId() == heartbeatId) {
			if (logger.isDebugEnabled()) {
				logger.debug("isRequest true");
			}
			return true;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("isRequest false");
		}
		return false;
	}

	@Override
	public boolean isResponse(IoSession session, Object message) {
		AbstractMessage m = (AbstractMessage) message;
		if (m.getCommandId() == heartbeatId) {
			if (logger.isDebugEnabled()) {
				logger.debug("isResponse true");
			}
			return true;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("isResponse false");
		}
		return false;
	}

	@Override
	public Object getRequest(IoSession session) {
		AbstractMessage message = new MinaMessage();
		message.setBodyByte(new byte[0]);
		message.setCommandId(heartbeatId);
		return message;
	}

	@Override
	public Object getResponse(IoSession session, Object request) {
		if (logger.isInfoEnabled()) {
			logger.info("getResponse request=" + request);
		}
		return request;
	}

	// -------------------------------------------------
	public void setHeartbeatId(int heartbeatId) {
		this.heartbeatId = heartbeatId;
	}

}
