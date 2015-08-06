package com.cwa.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;

import com.cwa.SendMessageService;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.handler.client.battle.ClientMagicSendHandler;
import com.cwa.handler.client.battle.ClientMoveSendHandler;
import com.cwa.handler.client.battle.ClientSyncBattleSendHandler;
import com.cwa.handler.client.player.ClientPlayerLoginSendHandler;
import com.cwa.handler.client.room.ClientCreatRoomSendHandler;
import com.cwa.handler.client.room.ClientGetRoomInfoSendHandler;
import com.cwa.handler.client.room.ClientGetRoomsSendHandler;
import com.cwa.handler.client.room.ClientJoinRoomSendHandler;
import com.cwa.handler.client.room.ClientMatchupOutTimeSendHandler;
import com.cwa.handler.client.room.ClientMatchupSuccessSendHandler;
import com.cwa.handler.client.room.ClientQuickJoinRoomSendHandler;
import com.cwa.handler.client.room.ClientUserStateChangeSendHandler;
import com.cwa.handler.client.user.ClientUserLoginSendHandler;
import com.cwa.handler.client.user.ClientUserLogoutSendHandler;
import com.cwa.message.BattleMessage.AttachBattleUp;
import com.cwa.message.BattleMessage.MagicActionDown;
import com.cwa.message.BattleMessage.MagicActionUp;
import com.cwa.message.BattleMessage.MoveActionDown;
import com.cwa.message.BattleMessage.MoveActionUp;
import com.cwa.message.BattleMessage.SyncBattleDown;
import com.cwa.message.BattleMessage.SyncBattleUp;
import com.cwa.message.ConfigMessage;
import com.cwa.message.IMessageType;
import com.cwa.message.MessageConfig;
import com.cwa.message.PlayerMessage.PlayerLoginDown;
import com.cwa.message.PlayerMessage.PlayerLoginUp;
import com.cwa.message.PlayerMessage.PlayerLogoutUp;
import com.cwa.message.ProBuffMessageHandler;
import com.cwa.message.RoomMessage.CreateRoomDown;
import com.cwa.message.RoomMessage.CreateRoomUp;
import com.cwa.message.RoomMessage.ExitRoomUp;
import com.cwa.message.RoomMessage.FightHeroUp;
import com.cwa.message.RoomMessage.GetRoomInfoDown;
import com.cwa.message.RoomMessage.GetRoomInfoUp;
import com.cwa.message.RoomMessage.GetRoomsDown;
import com.cwa.message.RoomMessage.GetRoomsUp;
import com.cwa.message.RoomMessage.JoinRoomDown;
import com.cwa.message.RoomMessage.JoinRoomUp;
import com.cwa.message.RoomMessage.MatchupOutTimeDown;
import com.cwa.message.RoomMessage.MatchupRoomUp;
import com.cwa.message.RoomMessage.MatchupSuccessDown;
import com.cwa.message.RoomMessage.QuickJoinRoomDown;
import com.cwa.message.RoomMessage.QuickJoinRoomUp;
import com.cwa.message.RoomMessage.UserStateChangeDown;
import com.cwa.message.UserMessage.GetUserinfoUp;
import com.cwa.message.UserMessage.UserLoginDown;
import com.cwa.message.UserMessage.UserLoginUp;
import com.cwa.message.UserMessage.UserLogoutUp;
import com.cwa.net.ClientSendMessage;
import com.cwa.net.netty.filter.MessageDecoder;
import com.cwa.net.netty.filter.MessageEncoder;
import com.cwa.net.netty.message.NettyMessageType;
import com.cwa.net.netty.message.NettySendMessage;

/**
 * Hello world!
 */
public class NettyClientServer {

	public static void main(String[] args) throws Exception {
		DOMConfigurator.configureAndWatch("propertiesconfig/log4j.xml");

		final IMessageType messageType = new NettyMessageType();
		// // 消息配置
		IClientGameHandler hand1 = new ClientUserLoginSendHandler();
		IClientGameHandler hand2 = new ClientUserLogoutSendHandler();
		IClientGameHandler hand3 = new ClientMoveSendHandler();
		IClientGameHandler hand4 = new ClientSyncBattleSendHandler();
		IClientGameHandler hand5 = new ClientMagicSendHandler();
		IClientGameHandler hand6 = new ClientGetRoomsSendHandler();
		IClientGameHandler hand8 = new ClientGetRoomInfoSendHandler();
		IClientGameHandler hand10 = new ClientCreatRoomSendHandler();
		IClientGameHandler hand11 = new ClientJoinRoomSendHandler();
		IClientGameHandler hand12 = new ClientUserStateChangeSendHandler();
		IClientGameHandler hand13 = new ClientMatchupSuccessSendHandler();
		IClientGameHandler hand14 = new ClientMatchupOutTimeSendHandler();
		IClientGameHandler hand15 = new ClientQuickJoinRoomSendHandler();
		IClientGameHandler hand16 = new ClientPlayerLoginSendHandler();
		List<MessageConfig> messageConfigs = new ArrayList<MessageConfig>();
		// sync
//		MessageConfig m16 = new MessageConfig();
//		m16.setCommonId(1999001);
//		m16.setMessage(ErrorDown.class);
//		m16.setMessageHandler(hand7);
		// user
//		MessageConfig m1 = new MessageConfig();
//		m1.setCommonId(1100001);
//		m1.setMessage(UserRegisterUp.class);

		MessageConfig m2 = new MessageConfig();
		m2.setCommonId(1100003);
		m2.setMessage(UserLoginUp.class);

//		MessageConfig m3 = new MessageConfig();
//		m3.setCommonId(1100002);
//		m3.setMessage(UserRegisterDown.class);
//		m3.setMessageHandler(hand0);

		MessageConfig m4 = new MessageConfig();
		m4.setCommonId(1100004);
		m4.setMessage(UserLoginDown.class);
		m4.setMessageHandler(hand1);

		MessageConfig m5 = new MessageConfig();
		m5.setCommonId(1100005);
		m5.setMessage(UserLogoutUp.class);

		MessageConfig m13 = new MessageConfig();
		m13.setCommonId(1100006);
		m13.setMessage(GetUserinfoUp.class);
		// battle
		MessageConfig m6 = new MessageConfig();
		m6.setCommonId(2100101);
		m6.setMessage(AttachBattleUp.class);

		MessageConfig m7 = new MessageConfig();
		m7.setCommonId(2100102);
		m7.setMessage(SyncBattleUp.class);

		MessageConfig m8 = new MessageConfig();
		m8.setCommonId(2100103);
		m8.setMessage(SyncBattleDown.class);
		m8.setMessageHandler(hand4);

		MessageConfig m9 = new MessageConfig();
		m9.setCommonId(2100104);
		m9.setMessage(MagicActionUp.class);

		MessageConfig m10 = new MessageConfig();
		m10.setCommonId(2100105);
		m10.setMessage(MagicActionDown.class);
		m10.setMessageHandler(hand5);

		MessageConfig m11 = new MessageConfig();
		m11.setCommonId(2100106);
		m11.setMessage(MoveActionUp.class);

		MessageConfig m12 = new MessageConfig();
		m12.setCommonId(2100107);
		m12.setMessage(MoveActionDown.class);
		m12.setMessageHandler(hand3);

		// room
		MessageConfig m17 = new MessageConfig();
		m17.setCommonId(1100201);
		m17.setMessage(GetRoomsUp.class);

		MessageConfig m18 = new MessageConfig();
		m18.setCommonId(1100202);
		m18.setMessage(GetRoomsDown.class);
		m18.setMessageHandler(hand6);

		MessageConfig m14 = new MessageConfig();
		m14.setCommonId(1100203);
		m14.setMessage(CreateRoomUp.class);

		MessageConfig m15 = new MessageConfig();
		m15.setCommonId(1100204);
		m15.setMessage(CreateRoomDown.class);
		m15.setMessageHandler(hand10);

		MessageConfig m19 = new MessageConfig();
		m19.setCommonId(1100213);
		m19.setMessage(ExitRoomUp.class);

		MessageConfig m20 = new MessageConfig();
		m20.setCommonId(1100221);
		m20.setMessage(GetRoomInfoUp.class);

		MessageConfig m21 = new MessageConfig();
		m21.setCommonId(1100222);
		m21.setMessage(GetRoomInfoDown.class);
		m21.setMessageHandler(hand8);
		
		MessageConfig m22 = new MessageConfig();
		m22.setCommonId(1100205);
		m22.setMessage(JoinRoomUp.class);

		MessageConfig m23 = new MessageConfig();
		m23.setCommonId(1100206);
		m23.setMessage(JoinRoomDown.class);
		m23.setMessageHandler(hand11);

		MessageConfig m24 = new MessageConfig();
		m24.setCommonId(1100208);
		m24.setMessage(UserStateChangeDown.class);
		m24.setMessageHandler(hand12);
		
		MessageConfig m25 = new MessageConfig();
		m25.setCommonId(1100220);
		m25.setMessage(MatchupSuccessDown.class);
		m25.setMessageHandler(hand13);
		
		MessageConfig m26 = new MessageConfig();
		m26.setCommonId(1100218);
		m26.setMessage(MatchupOutTimeDown.class);
		m26.setMessageHandler(hand14);
		
		MessageConfig m27 = new MessageConfig();
		m27.setCommonId(1100223);
		m27.setMessage(QuickJoinRoomUp.class);

		MessageConfig m28 = new MessageConfig();
		m28.setCommonId(1100224);
		m28.setMessage(QuickJoinRoomDown.class);
		m28.setMessageHandler(hand15);
		
		MessageConfig m29 = new MessageConfig();
		m29.setCommonId(1100215);
		m29.setMessage(MatchupRoomUp.class);

		MessageConfig m30 = new MessageConfig();
		m30.setCommonId(1100227);
		m30.setMessage(FightHeroUp.class);
		
		//player
		MessageConfig m31 = new MessageConfig();
		m31.setCommonId(2100001);
		m31.setMessage(PlayerLoginUp.class);
		
		MessageConfig m32 = new MessageConfig();
		m32.setCommonId(2100002);
		m32.setMessage(PlayerLoginDown.class);
		m32.setMessageHandler(hand16);

		MessageConfig m33 = new MessageConfig();
		m33.setCommonId(2100003);
		m33.setMessage(PlayerLogoutUp.class);
		
//		messageConfigs.add(m1);
		messageConfigs.add(m2);
//		messageConfigs.add(m3);
		messageConfigs.add(m4);
		messageConfigs.add(m5);
		messageConfigs.add(m6);
		messageConfigs.add(m7);
		messageConfigs.add(m8);
		messageConfigs.add(m9);
		messageConfigs.add(m10);
		messageConfigs.add(m11);
		messageConfigs.add(m12);
		messageConfigs.add(m13);
		messageConfigs.add(m14);
		messageConfigs.add(m15);
//		messageConfigs.add(m16);
		messageConfigs.add(m17);
		messageConfigs.add(m18);
		messageConfigs.add(m19);
		messageConfigs.add(m20);
		messageConfigs.add(m21);
		messageConfigs.add(m22);
		messageConfigs.add(m23);
		messageConfigs.add(m24);
		messageConfigs.add(m25);
		messageConfigs.add(m26);
		messageConfigs.add(m27);
		messageConfigs.add(m28);
		messageConfigs.add(m29);
		messageConfigs.add(m30);
		messageConfigs.add(m31);
		messageConfigs.add(m32);
		messageConfigs.add(m33);
		final ProBuffMessageHandler protohandler = new ProBuffMessageHandler();
		protohandler.setMessageConfigs(messageConfigs);
		ConfigMessage configMessage = new ConfigMessage();
		configMessage.setMessageConfigs(messageConfigs);

		ClientSendMessage sessionManager = new NettySendMessage();
		sessionManager.setConfigMessage(configMessage);
		SendMessageService send = new SendMessageService();
		send.setSend(sessionManager);
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(group);
			b.channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("decoder", new MessageDecoder(messageType));
					pipeline.addLast("encoder", new MessageEncoder()); // shared!
					pipeline.addLast("handler", new NettyClientHandler(protohandler));
				}
			});
			ChannelFuture f = b.connect("127.0.0.1", 8099).sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {
		} finally {
			group.shutdownGracefully();
		}
	}
}