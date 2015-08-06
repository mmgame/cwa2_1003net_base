package com.cwa.handler.client.room;

import java.util.ArrayList;
import java.util.List;

import com.cwa.ISession;
import com.cwa.SendMessageService;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.RoomMessage.FightHeroUp;
import com.cwa.message.RoomMessage.GetRoomInfoUp;
import com.cwa.message.RoomMessage.GetRoomsDown;
import com.cwa.message.RoomMessage.RoomBaseInfoBean;

public class ClientGetRoomsSendHandler extends IClientGameHandler<GetRoomsDown> {

	@Override
	public void executeHandler(ISession session, GetRoomsDown msg) {
		List<RoomBaseInfoBean> beanlist = msg.getRoomBaseInfoBeanList();
		for (RoomBaseInfoBean roomBaseInfoBean : beanlist) {
			System.out.println("Get ROOM Id->>>>>>>>>>>>>>>" + roomBaseInfoBean.getRoomId());
		}
		// ExitRoomUp.Builder b=ExitRoomUp.newBuilder();
		// SendMessageService.send.sendMessage(b.build(), session);
		FightHeroUp.Builder bb = FightHeroUp.newBuilder();
		List<Integer> heroids = new ArrayList<Integer>();
		heroids.add(600500);
//		bb.addAllHeroId(heroids);
		SendMessageService.send.sendMessage(bb.build(), session);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		GetRoomInfoUp.Builder b = GetRoomInfoUp.newBuilder();
		b.setRid(1);
		SendMessageService.send.sendMessage(b.build(), session);
	}
}