package com.cwa.handler.client.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.cwa.ISession;
import com.cwa.SendMessageService;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.message.BattleMessage.ActionTargetBean;
import com.cwa.message.BattleMessage.BattleBean;
import com.cwa.message.BattleMessage.CoordinateBean;
import com.cwa.message.BattleMessage.HeroBean;
import com.cwa.message.BattleMessage.MagicActionUp;
import com.cwa.message.BattleMessage.MoveActionUp;
import com.cwa.message.BattleMessage.SyncBattleDown;

public class ClientSyncBattleSendHandler extends IClientGameHandler<SyncBattleDown> {

	@Override
	public void executeHandler(ISession session, SyncBattleDown msg) {

		BattleBean battleBean=msg.getBattleBean();
		int battleKeyId =battleBean.getBattleKeyId();
		int currentTime=battleBean.getCurrentTime();
		
	
		
		List<HeroBean> heroList=msg.getHeroBeansList();
		Map<Integer, Integer> heroIdMap=new HashMap<Integer, Integer>();
		for (HeroBean heroIdBean : heroList) {
			heroIdMap.put(heroIdBean.getHeroEntityBean().getId(),heroIdBean.getPerformerId());
		}
		
	
		for (int i = 0; i < 1; i++) {
			int v=i%2;
			if(v==0){
				
//				MagicActionUp.Builder bb=MagicActionUp.newBuilder();
//				ActionTargetBean.Builder actionTargetBean=ActionTargetBean.newBuilder();
//				bb.setPerformerId(heroIdMap.get(600100));
//				bb.setSkillId(6110001);
//				actionTargetBean.setTargetType(1);
//				actionTargetBean.setTargetId(heroIdMap.get(600200));
//				bb.setActionTarget(actionTargetBean);
//				SendMessageService.send.sendMessage(bb.build(),session);
//				logger.info("切换到施法");
				
				
				
				Random r=new Random();
				MoveActionUp.Builder b=MoveActionUp.newBuilder();
				ActionTargetBean.Builder actionTargetBean=ActionTargetBean.newBuilder();
				b.setPerformerId(heroIdMap.get(600100));
				CoordinateBean.Builder value=CoordinateBean.newBuilder();
				value.addC(r.nextInt(500));
				value.addC(r.nextInt(500));
				actionTargetBean.setTargetType(3);
				actionTargetBean.setAreaTarget(value);
				b.setActionTarget(actionTargetBean);
				SendMessageService.send.sendMessage(b.build(),session);
				logger.info("切换到移动");
			}else{
				Random r=new Random();
				MoveActionUp.Builder b=MoveActionUp.newBuilder();
				ActionTargetBean.Builder actionTargetBean=ActionTargetBean.newBuilder();
				b.setPerformerId(heroIdMap.get(600100));
				CoordinateBean.Builder value=CoordinateBean.newBuilder();
				value.addC(r.nextInt(500));
				value.addC(r.nextInt(500));
				actionTargetBean.setTargetType(3);
				actionTargetBean.setAreaTarget(value);
				b.setActionTarget(actionTargetBean);
				SendMessageService.send.sendMessage(b.build(),session);
				logger.info("切换到移动");
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		MagicActionUp.Builder bb=MagicActionUp.newBuilder();
//		
//		bb.setPerformerId(heroIdMap.get(600100));
//		bb.setSkillId(6110001);
//		bb.setTargetType(1);
//		bb.setTargetId(heroIdMap.get(600100));
//		SendMessageService.send.sendMessage(bb.build(),session);
		
		
		
//		MoveActionUp.Builder b=MoveActionUp.newBuilder();
//		b.setPerformerId(heroIdMap.get(600100));
//		CoordinateBean.Builder value=CoordinateBean.newBuilder();
//		value.addC(3000);
//		value.addC(1000);
//		b.setTracks(value);
//		SendMessageService.send.sendMessage(b.build(),session);

	}
}