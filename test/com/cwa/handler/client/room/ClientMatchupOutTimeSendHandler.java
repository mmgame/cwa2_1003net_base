package com.cwa.handler.client.room;

import com.cwa.ISession;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.RoomMessage.MatchupOutTimeDown;

public class ClientMatchupOutTimeSendHandler extends IClientGameHandler<MatchupOutTimeDown> {

	@Override
	public void executeHandler(ISession session, MatchupOutTimeDown msg) {
		
		System.out.println("MATCHUP OUT TIME!!!!!!!!!!!!!!!!!!!!!!");
//		GetRoomsUp.Builder b=GetRoomsUp.newBuilder();
//		SendMessageService.send.sendMessage(b.build(), session);
	}
}