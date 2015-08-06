package com.cwa.handler.client.room;

import com.cwa.ISession;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.RoomMessage.UserStateChangeDown;

public class ClientUserStateChangeSendHandler extends IClientGameHandler<UserStateChangeDown> {

	@Override
	public void executeHandler(ISession session, UserStateChangeDown msg) {
		
		System.out.println("User->>"+msg.getUserId()+"Change TO->>"+msg.getState());
//		GetRoomsUp.Builder b=GetRoomsUp.newBuilder();
//		SendMessageService.send.sendMessage(b.build(), session);
		
	}
}