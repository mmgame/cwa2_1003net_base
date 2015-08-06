package com.cwa.handler.client.battle;

import com.cwa.ISession;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.BattleMessage.MoveActionDown;

public class ClientMoveSendHandler extends IClientGameHandler<MoveActionDown> {

	@Override
	public void executeHandler(ISession session, MoveActionDown msg) {

		if (logger.isInfoEnabled()) {
			logger.info("接收到移动消息：目标id="+msg.getPerformerId()+"  目标位置坐标["+msg.getActionTarget().getAreaTarget().getC(0)+","+msg.getActionTarget().getAreaTarget().getC(1)+"]");
		}

	}
}