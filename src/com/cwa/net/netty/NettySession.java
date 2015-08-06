package com.cwa.net.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.HashMap;
import java.util.Map;

import com.cwa.ISession;
import com.cwa.message.ISendMessage;
import com.cwa.net.ISessionManager;

/**
 * netty session 封装
 * 
 * @author tzy
 * 
 * 
 */
public class NettySession implements ISession {
	// {key:AttributeKey}
	private static Map<String, AttributeKey<Object>> attributeKeyMap = new HashMap<String, AttributeKey<Object>>();

	private ChannelHandlerContext ctx;

	public NettySession(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public void putKey(String key, AttributeKey<Object> akey) {
		if (!attributeKeyMap.containsKey(key)) {
			attributeKeyMap.put(key, akey);
		}
	}

	private AttributeKey<Object> getAndCreateKey(String key) {
		AttributeKey<Object> k = attributeKeyMap.get(key);
		if (k == null) {
			synchronized (NettySession.class) {
				if (!attributeKeyMap.containsKey(key)) {
					k = AttributeKey.valueOf(key.toString());
					// 改为同步put
					attributeKeyMap.put(key, k);
				}
			}
		}
		return k;
	}

	@Override
	public void write(Object msg) {
		ctx.writeAndFlush(msg);
	}

	@Override
	public long getSessionId() {
		AttributeKey<Object> sessionIdKey = getAndCreateKey(ISessionManager.SessionId_Key);
		return (Long) ctx.attr(sessionIdKey).get();
	}

	@Override
	public void close(boolean immediately) {
		ctx.close();

	}

	@Override
	public void setAttachment(Object key, Object attachment) {
		AttributeKey<Object> k = getAndCreateKey(key.toString());
		ctx.attr(k).set(attachment);
	}

	@Override
	public Object getAttachment(Object key) {
		AttributeKey<Object> k = getAndCreateKey(key.toString());
		if (k == null) {
			return null;
		}
		return ctx.attr(k).get();
	}

	@Override
	public void send(Object msg) {
		ISendMessage sender = (ISendMessage) getAttachment(ISessionManager.Sender_Key);
		sender.sendMessage(msg, this);
	}
}
