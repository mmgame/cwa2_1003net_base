package com.cwa.handler.client.room;

import com.cwa.ISession;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.RoomMessage.QuickJoinRoomDown;

public class ClientQuickJoinRoomSendHandler extends IClientGameHandler<QuickJoinRoomDown> {

	@Override
	public void executeHandler(ISession session, QuickJoinRoomDown msg) {
//		System.out.println("GET CTEAT ROOM->>>>>>>>>>>>>>>"+msg.getRoomId());
//		GetRoomsUp.Builder b=GetRoomsUp.newBuilder();
//		SendMessageService.send.sendMessage(b.build(), session);
	}
}