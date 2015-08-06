/**
 * 
 */
package com.cwa.handler.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.ISession;
import com.cwa.handler.IMessageHandler;
import com.cwa.net.ISessionManager;
import com.cwa.net.netty.NettySession;

/**
 * @author tzy
 * 
 */
public abstract class IGameHandler<T> implements IMessageHandler<T> {
	protected static final Logger logger = LoggerFactory.getLogger(IGameHandler.class);

	@Override
	public void handle(T message, ISession session) {
		String userId = (String) session.getAttachment(ISessionManager.Target_Key);
		try {
			if (userId == null) {// 用户注册或者登陆
				Object obj = executeHandler(message, session);
				if (obj != null) {
					String targetId = (String) obj;
					if (logger.isInfoEnabled()) {
						logger.info("User register userId=" + targetId);
					}
				}
				return;
			} else {
				executeHandler(userId, message);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		}
	}

	public Object executeHandler(T message, ISession session) {
		logger.error("Error user state! no login!" + message);
		return null;
	}

	public void executeHandler(String userId, T message) {
		logger.error("Error user state! no login!" + message);
	}
}