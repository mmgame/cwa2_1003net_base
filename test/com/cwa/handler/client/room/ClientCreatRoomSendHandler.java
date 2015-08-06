package com.cwa.handler.client.room;

import com.cwa.ISession;

import com.cwa.SendMessageService;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.RoomMessage.CreateRoomDown;
import com.cwa.message.RoomMessage.GetRoomsUp;

public class ClientCreatRoomSendHandler extends IClientGameHandler<CreateRoomDown> {

	@Override
	public void executeHandler(ISession session, CreateRoomDown msg) {
		System.out.println("GET CTEAT ROOM->>>>>>>>>>>>>>>"+msg.getRoomId());
		GetRoomsUp.Builder b=GetRoomsUp.newBuilder();
		SendMessageService.send.sendMessage(b.build(), session);
	}
}