package com.cwa.handler.client.user;

import com.cwa.ISession;

import com.cwa.SendMessageService;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.RoomMessage.CreateRoomUp;
import com.cwa.message.RoomMessage.JoinRoomUp;
import com.cwa.message.UserMessage.UserLoginDown;

public class ClientUserLogoutSendHandler extends IClientGameHandler<UserLoginDown> {
	
	@Override
	public void executeHandler(ISession session, UserLoginDown msg) {
		
		CreateRoomUp.Builder b=CreateRoomUp.newBuilder();
		
//		JoinRoomUp.Builder b=JoinRoomUp.newBuilder();
//		b.setRoomId(1);
				
		SendMessageService.send.sendMessage(b.build(), session);
//		AttachBattleUp.Builder bb = AttachBattleUp.newBuilder();
//		bb.setBattleId("222");
//		bb.setUserId("10001");
//		if (logger.isInfoEnabled()) {
//			logger.info("发送链接战场信息");
//		}
//		for (;;) {
//			GetUserinfoUp.Builder  bb=GetUserinfoUp.newBuilder();
//			bb.setUserId("dfasfaskjfaklsjaskjdkasjjdkl;asjdkl;jaskl;djas;jddaskjkl;dja;skdjakl;sdjaskl");
//			SendMessageService.send.sendMessage(bb.build(), session);
//			
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
//			UserMessage.UserLogoutRevMessage.Builder bb=UserMessage.UserLogoutRevMessage.newBuilder();
//			SendMessageService.send.sendMessage(bb.build(),session);
		}
		
//		
}