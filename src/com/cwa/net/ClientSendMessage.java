package com.cwa.net;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.ISession;
import com.cwa.message.AbstractMessage;
import com.cwa.message.IConfigMessage;

/**
 * 发送客户端
 * 
 * @author mali
 * 
 */

public abstract class ClientSendMessage implements ISessionManager {
	private static final Logger logger = LoggerFactory.getLogger(ClientSendMessage.class);

	// sessionID:session
	private ConcurrentHashMap<Long, ISession> sessionMap = new ConcurrentHashMap<Long, ISession>();

	protected IConfigMessage configMessage;
	protected IConnectCallBack callBack;

	@Override
	public void sendBroadcastMessage(Object message) {
		for (ISession session : sessionMap.values()) {
			AbstractMessage m = createMessage(message);
			session.write(m);
		}
		if (logger.isInfoEnabled()) {
			logger.info("sendBroadcastMessage message=" + message.getClass());
		}
	}

	@Override
	public void sendMessage(Object message, ISession session) {

		AbstractMessage m = createMessage(message);
		session.write(m);
		if (logger.isInfoEnabled()) {
			logger.info("sendMessage to sessionId=" + session.getSessionId() + "  message: " + m.getCommandId() + "-->"
					+ message.getClass().getSimpleName()+ "  UserId:"+session.getAttachment(ISessionManager.UserId_Key));
		}

	}

	@Override
	public void sendMessageBySessionId(Object message, long sessionId) {
		ISession session = sessionMap.get(sessionId);
		if (session != null) {
			AbstractMessage m = createMessage(message);
			session.write(m);
		}
		if (logger.isInfoEnabled()) {
			logger.info("sendMessageBySessionId to sessionId=" + sessionId + "  message=" + message.getClass());
		}
	}

	@Override
	public void sendMessageBySessionIds(Object message, List<Long> sessionIds) {
		for (long sessionId : sessionIds) {
			ISession session = sessionMap.get(sessionId);
			if (session == null) {
				continue;
			}
			AbstractMessage m = createMessage(message);
			session.write(m);
		}
		if (logger.isInfoEnabled()) {
			logger.info("sendMessageBySessionIds  message=" + message.getClass());
		}
	}

	@Override
	public void insertSession(ISession session) {
		sessionMap.put(session.getSessionId(), session);
		// 回调处理
		if (callBack != null) {
			callBack.connectExcute(session);
		}

	}

	@Override
	public void removeSession(long sessionId) {
		ISession session = sessionMap.remove(sessionId);
		if (session == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("remove session session is null sessionId= " + sessionId);
			}
			return;
		}
	}

	private Collection<ISession> getAllSession() {
		return sessionMap.values();
	}

	@Override
	public List<Object> removeAllSession() {
		List<Object> targets = new LinkedList<Object>();
		Collection<ISession> sessions = getAllSession();
		for (ISession session : sessions) {
			// 关闭session
			Object target = session.getAttachment(ISessionManager.Target_Key);
			if (target != null) {
				targets.add(target);
			}
			session.close(true);
		}
		return targets;
	}

	/**
	 * 创建消息体
	 * 
	 * @param message
	 * @return
	 */
	public abstract AbstractMessage createMessage(Object message);

	public void setConfigMessage(IConfigMessage configMessage) {
		this.configMessage = configMessage;
	}

	public void setCallBack(IConnectCallBack callBack) {
		this.callBack = callBack;
	}

}
