package com.cwa.handler.server;

import com.cwa.SendMessageService;
import com.cwa.message.UserMessage.GetUserinfoUp;

public class GetUserInfoRevHandler extends IGameHandler<GetUserinfoUp> {

	@Override
	public void executeHandler(String userId, GetUserinfoUp message) {
		GetUserinfoUp.Builder b = GetUserinfoUp.newBuilder();
		b.setUserId("10001");
//		SendMessageService.send.sendMessageByTargetId(b.build(), userId);

	}
}