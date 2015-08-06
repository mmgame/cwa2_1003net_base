package com.cwa.handler.client.user;

import com.cwa.ISession;

import com.cwa.SendMessageService;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.RoomMessage.CreateRoomUp;
import com.cwa.message.RoomMessage.MatchupRoomUp;
import com.cwa.message.UserMessage.UserLoginDown;

public class ClientUserLoginSendHandler extends IClientGameHandler<UserLoginDown> {

	@Override
	public void executeHandler(ISession session, UserLoginDown msg) {
		CreateRoomUp.Builder b = CreateRoomUp.newBuilder();
         b.setBattleKeyId(1001);
//		 JoinRoomUp.Builder b=JoinRoomUp.newBuilder();
//		 b.setRoomId(2);
         
//     	MatchupRoomUp.Builder b=MatchupRoomUp.newBuilder();

		SendMessageService.send.sendMessage(b.build(), session);
	}
}