package com.cwa.net.mina;

import org.apache.mina.core.session.IoSession;

import com.cwa.ISession;
import com.cwa.message.ISendMessage;
import com.cwa.net.ISessionManager;

/**
 * mina session封装类
 * 
 * @author tzy
 * 
 * 
 */
public class MinaSession implements ISession {
	private IoSession session;

	public MinaSession(IoSession session) {
		this.session = session;
		this.session.setAttribute(ISessionManager.ISession_Key, this);
	}

	@Override
	public void write(Object msg) {
		session.write(msg);
	}

	@Override
	public long getSessionId() {
		return session.getId();
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	@Override
	public void close(boolean immediately) {
		session.close(immediately);

	}

	@Override
	public void setAttachment(Object key, Object attachment) {
		session.setAttribute(key, attachment);
	}

	@Override
	public Object getAttachment(Object key) {
		return session.getAttribute(key);
	}

	@Override
	public void send(Object msg) {
		ISendMessage sender = (ISendMessage) getAttachment(ISessionManager.Sender_Key);
		sender.sendMessage(msg, this);
	}
}
