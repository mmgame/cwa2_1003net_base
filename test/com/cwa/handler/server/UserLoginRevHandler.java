package com.cwa.handler.server;

import com.cwa.ISession;
import com.cwa.SendMessageService;
import com.cwa.message.UserMessage.UserLoginDown;
import com.cwa.message.UserMessage.UserLoginUp;

public class UserLoginRevHandler extends IGameHandler<UserLoginUp> {

	@Override
	public Object executeHandler(UserLoginUp message, ISession session) {
//		long userId = Long.parseLong(message.getUserId());
		if (logger.isInfoEnabled()) {
			logger.info("", message);
		}
		UserLoginDown.Builder b = UserLoginDown.newBuilder();
		b.setLoginState(1);
		b.setLogoutTime(String.valueOf(System.currentTimeMillis()));
//		session.bindTarget(userId);
//		SendMessageService.send.sendMessageByTargetId(b.build(), userId);
		if (logger.isInfoEnabled()) {
//			logger.info("User Login success!!!", message.getUserId());
		}
		return null;
	}

}