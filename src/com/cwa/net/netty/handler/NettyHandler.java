package com.cwa.net.netty.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.ISession;
import com.cwa.handler.IClosedSessionHandler;
import com.cwa.handler.ICreateSessionHandler;
import com.cwa.handler.IMessageHandler;
import com.cwa.net.ISessionManager;
import com.cwa.net.netty.NettySession;

/**
 * 逻辑处理
 * 
 * @author tzy
 * 
 */
@Sharable
public class NettyHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(NettyHandler.class);

	private IMessageHandler<Object> messageHandler;
	private List<IClosedSessionHandler> closeSessionHandlerList;
	private List<ICreateSessionHandler> createSessionHandlerList;

	private ISessionManager sessionManager;
	// --------------
	private AtomicLong atomicLong = new AtomicLong(0);

	private static final AttributeKey<Object> ISessionKey = AttributeKey.valueOf(ISessionManager.ISession_Key);

	// 与客户端建立连接
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		long sessionId = atomicLong.incrementAndGet();
		NettySession session = new NettySession(ctx);
		session.putKey(ISessionManager.ISession_Key, ISessionKey);

		session.setAttachment(ISessionManager.ISession_Key, session);
		session.setAttachment(ISessionManager.SessionId_Key, sessionId);
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

	// 与客户端断开连接
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ISession session = (ISession) ctx.attr(ISessionKey).get();
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
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			logger.error(cause.getMessage() + "----" + cause.getClass().getName());
		} else {
			logger.error("", cause);
		}
	}

	// 接受到客户端消息
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
		ISession session = (ISession) ctx.attr(ISessionKey).get();
		messageHandler.handle(message, session);
		// ctx.writeAndFlush(message);
	}

	// -------------------------
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
