package com.cwa.handler.client.room;

import java.util.ArrayList;
import java.util.List;

import com.cwa.ISession;
import com.cwa.SendMessageService;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.RoomMessage.FightHeroUp;
import com.cwa.message.RoomMessage.GetRoomInfoDown;
import com.cwa.message.RoomMessage.MatchupRoomUp;
import com.cwa.message.RoomMessage.RoomDetailInfoBean;

public class ClientGetRoomInfoSendHandler extends IClientGameHandler<GetRoomInfoDown> {

	@Override
	public void executeHandler(ISession session, GetRoomInfoDown msg) {
		RoomDetailInfoBean bean=msg.getRoomDetailInfoBean();
		System.out.println("Get ROOM Id->>"+bean.getRoomId()+"  MasterID->>"+bean.getMasterId());
//		ExitRoomUp.Builder b=ExitRoomUp.newBuilder();
//		SendMessageService.send.sendMessage(b.build(), session);
//		for (int i = 0; i < 9; i++) {
//			GetRoomInfoUp.Builder b=GetRoomInfoUp.newBuilder();
//			b.setRid(i);
//			SendMessageService.send.sendMessage(b.build(), session);
//		}
	
		MatchupRoomUp.Builder b=MatchupRoomUp.newBuilder();
		SendMessageService.send.sendMessage(b.build(), session);
	}
}