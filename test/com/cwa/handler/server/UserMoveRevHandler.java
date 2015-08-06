package com.cwa.handler.server;

import com.cwa.ISession;
import com.cwa.message.BattleMessage.MoveActionUp;

public class UserMoveRevHandler extends IGameHandler<MoveActionUp> {

	@Override
	public Object executeHandler(MoveActionUp message, ISession session) {


	System.out.println("22222222222222222222222");
	return session;
}
	
}