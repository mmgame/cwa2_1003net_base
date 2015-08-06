package com.cwa.handler.client.room;

import com.cwa.ISession;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.RoomMessage.MatchupSuccessDown;

public class ClientMatchupSuccessSendHandler extends IClientGameHandler<MatchupSuccessDown> {

	@Override
	public void executeHandler(ISession session, MatchupSuccessDown msg) {
		System.out.println("MatchupSuccess to->>>>>>>>>>>>>>>"+msg.getBattleInfoBean().getIp()+":"+msg.getBattleInfoBean().getPort());
		System.out.println("BattleId-->"+msg.getBattleInfoBean().getBattleId());
		System.out.println("Attack-->"+msg.getBattleInfoBean().getAttackRoomid());
		System.out.println("Defind-->"+msg.getBattleInfoBean().getDefineRoomid());
//		GetRoomsUp.Builder b=GetRoomsUp.newBuilder();
//		SendMessageService.send.sendMessage(b.build(), session);
	}
}