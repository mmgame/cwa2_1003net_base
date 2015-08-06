package com.cwa.handler.server;

import com.cwa.ManagerService;
import com.cwa.message.UserMessage.UserLogoutUp;

public class UserLogoutRevHandler extends IGameHandler<UserLogoutUp> {

	@Override
	public void executeHandler(String userId, UserLogoutUp message) {
		if (logger.isInfoEnabled()) {
			logger.info("关闭session，用户登出！！！");
		}

		System.out.println(userId);

//		ManagerService.sessionManager.getSession(userId).close(true);

	}
}