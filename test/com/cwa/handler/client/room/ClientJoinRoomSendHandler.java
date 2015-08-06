package com.cwa.handler.client.room;

import com.cwa.ISession;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.RoomMessage.JoinRoomDown;

public class ClientJoinRoomSendHandler extends IClientGameHandler<JoinRoomDown> {

	@Override
	public void executeHandler(ISession session, JoinRoomDown msg) {
		
		System.out.println("Join ROOM->>"+msg.getIsSuccess()+"Room-->"+msg.getRoomDetailInfoBean().getRoomId());
//		GetRoomsUp.Builder b=GetRoomsUp.newBuilder();
//		SendMessageService.send.sendMessage(b.build(), session);
	}
}