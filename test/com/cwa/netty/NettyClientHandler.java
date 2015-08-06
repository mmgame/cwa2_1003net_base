package com.cwa.netty;

import java.util.concurrent.atomic.AtomicLong;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import com.cwa.ISession;
import com.cwa.SendMessageService;
import com.cwa.message.ProBuffMessageHandler;
import com.cwa.message.UserMessage.UserLoginUp;
import com.cwa.net.ISessionManager;
import com.cwa.net.netty.NettySession;

/**
 * 
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	private ProBuffMessageHandler ph;
	private static final AttributeKey<Object> ISessionKey = AttributeKey.valueOf(ISessionManager.ISession_Key);

	private AtomicLong atomicLong = new AtomicLong(0);
	
	public NettyClientHandler(ProBuffMessageHandler ph) {
		this.ph = ph;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ISession session = (ISession) ctx.attr(ISessionKey).get();
		ph.handle(msg, session);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		long sessionId = atomicLong.incrementAndGet();
		
		NettySession session = new NettySession(ctx);
		session.putKey(ISessionManager.ISession_Key, ISessionKey);

		session.setAttachment(ISessionManager.ISession_Key, session);
		session.setAttachment(ISessionManager.SessionId_Key, sessionId);
		
		System.out.println("client向服务器发送注册账号消息");
		// UserRegisterUp.Builder b=UserRegisterUp.newBuilder();
		// b.setAccount("niepan_CWA");
		// b.setAccountType(1);
		UserLoginUp.Builder b = UserLoginUp.newBuilder();
		b.setToken("12345678912l");
		b.setUserIdStr("10001");
		b.setRid(1);
		b.setAsid(1);
		SendMessageService.send.sendMessage(b.build(), session);

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("断网啦");
		super.channelInactive(ctx);
	}
}