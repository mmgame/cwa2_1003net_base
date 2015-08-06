/**
 * 
 */
package com.cwa.handler.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.ISession;
import com.cwa.handler.IMessageHandler;

/**
 * @author mali
 * 
 */
public abstract class IClientGameHandler<T> implements IMessageHandler<T> {
	protected static final Logger logger = LoggerFactory.getLogger(IClientGameHandler.class);
	@Override
	public void handle(T message,ISession session) {
		executeHandler(session, message);
	}

	public void executeHandler(ISession session,T message) {
		logger.error("Error user state! no login!" + message);
	}
}