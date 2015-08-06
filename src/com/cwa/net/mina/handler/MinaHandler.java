package com.cwa.net.mina.handler;

import java.io.IOException;
import java.util.List;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.ISession;
import com.cwa.handler.IClosedSessionHandler;
import com.cwa.handler.ICreateSessionHandler;
import com.cwa.handler.IMessageHandler;
import com.cwa.net.ISessionManager;
import com.cwa.net.mina.MinaSession;

/**
 * 逻辑处理
 * 
 * @author Administrator
 * 
 */
public class MinaHandler extends IoHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MinaHandler.class);

	private IMessageHandler<Object> messageHandler;
	private List<IClosedSessionHandler> closeSessionHandlerList;
	private List<ICreateSessionHandler> createSessionHandlerList;
	private ISessionManager sessionManager;

	@Override
	public void sessionCreated(IoSession s) throws Exception {
		MinaSession session = new MinaSession(s);
		session.setAttachment(ISessionManager.Sender_Key, sessionManager);
		if (logger.isInfoEnabled()) {
			logger.info("sessionCreated sessionId=" + session.getSessionId());
		}
		sessionManager.insertSession(session);
		if (createSessionHandlerList != null) {
			for (ICreateSessionHandler handler : createSessionHandlerList) {
				// 清除本地缓存等操作
				handler.execute(session);
			}
		}
	}

	@Override
	public void messageReceived(IoSession s, Object message) throws Exception {
		ISession session = (ISession) s.getAttribute(ISessionManager.ISession_Key);
		System.out.println("接受到客户端消息");
		messageHandler.handle(message, session);
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("[messageSent!]");
		}
	}

	@Override
	public void sessionClosed(IoSession s) throws Exception {
		ISession session = (ISession) s.getAttribute(ISessionManager.ISession_Key);
		if (logger.isInfoEnabled()) {
			logger.info("[session closed!]");
		}
		if (closeSessionHandlerList != null) {
			for (IClosedSessionHandler handler : closeSessionHandlerList) {
				// 清除本地缓存等操作
				handler.execute(session);
			}
		}
		sessionManager.removeSession(session.getSessionId());
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			logger.error(cause.getMessage() + "----" + cause.getClass().getName());
		} else {
			logger.error("", cause);
		}
	}

	public void sessionIdle(IoSession session, IdleStatus status) {
		if (logger.isInfoEnabled()) {
			logger.info("[sessionIdle] sessionId=" + session.getId() + " status=" + status);
		}
	}

	// ----------------------------------------------------

	public void setCloseSessionHandlerList(List<IClosedSessionHandler> closeSessionHandlerList) {
		this.closeSessionHandlerList = closeSessionHandlerList;
	}

	public void setCreateSessionHandlerList(List<ICreateSessionHandler> createSessionHandlerList) {
		this.createSessionHandlerList = createSessionHandlerList;
	}

	public void setMessageHandler(IMessageHandler<Object> messageHandler) {
		this.messageHandler = messageHandler;
	}

	public void setSessionManager(ISessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
}
