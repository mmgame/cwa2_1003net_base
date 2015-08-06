package com.cwa.handler.client.battle;

import com.cwa.ISession;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.BattleMessage.MagicActionDown;

public class ClientMagicSendHandler extends IClientGameHandler<MagicActionDown> {

	@Override
	public void executeHandler(ISession session, MagicActionDown msg) {

		if (logger.isInfoEnabled()) {
			logger.info("接收到技能消息：技能id="+msg.getSkillId());
		}

	}
}