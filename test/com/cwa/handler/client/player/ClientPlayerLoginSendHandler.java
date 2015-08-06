package com.cwa.handler.client.player;

import com.cwa.ISession;

import com.cwa.SendMessageService;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.PlayerMessage.PlayerLoginDown;
import com.cwa.message.RoomMessage.CreateRoomUp;

public class ClientPlayerLoginSendHandler extends IClientGameHandler<PlayerLoginDown> {

	@Override
	public void executeHandler(ISession session, PlayerLoginDown msg) {
		CreateRoomUp.Builder b = CreateRoomUp.newBuilder();
         b.setBattleKeyId(1001);
//		 JoinRoomUp.Builder b=JoinRoomUp.newBuilder();
//		 b.setRoomId(2);
         
//     	MatchupRoomUp.Builder b=MatchupRoomUp.newBuilder();

		SendMessageService.send.sendMessage(b.build(), session);
	}
}