package com.cwa.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.cwa.ISession;
import com.cwa.message.ProBuffMessageHandler;
import com.cwa.net.ISessionManager;
import com.cwa.net.mina.MinaSession;

public class MinaClientHandler extends IoHandlerAdapter {
	private ProBuffMessageHandler ph;
	public MinaClientHandler(ProBuffMessageHandler ph) {
		this.ph=ph;
	}
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		MinaSession s = new MinaSession(session);
		System.out.println("client向服务器发送注册账号消息");
//		UserRegisterUp.Builder b=UserRegisterUp.newBuilder();
//		b.setAccountType(1);
//		b.setAccount("niepan_CWA");
//		SendMessageService.send.sendMessage(b.build(),s);
//		SendMessageService.send.sendMessage(b.build(), session);
//		session.write(b.build());
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// session.write(vaString);


	}

	@Override
	public void messageReceived(IoSession s, Object message) throws Exception {
		ISession session =(ISession) s.getAttribute(ISessionManager.ISession_Key);
		ph.handle(message, session);
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {

	}

}
