package com.cwa.netty;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.FileSystemResource;

import com.cwa.ManagerService;
import com.cwa.SendMessageService;
import com.cwa.handler.IClosedSessionHandler;
import com.cwa.handler.ICreateSessionHandler;
import com.cwa.handler.server.GetUserInfoRevHandler;
import com.cwa.handler.server.IGameHandler;
import com.cwa.handler.server.UserLoginRevHandler;
import com.cwa.handler.server.UserLogoutRevHandler;
import com.cwa.message.ConfigMessage;
import com.cwa.message.IMessageType;
import com.cwa.message.MessageConfig;
import com.cwa.message.ProBuffMessageHandler;
import com.cwa.message.UserMessage.GetUserinfoDown;
import com.cwa.message.UserMessage.GetUserinfoUp;
import com.cwa.message.UserMessage.UserLoginDown;
import com.cwa.message.UserMessage.UserLoginUp;
import com.cwa.message.UserMessage.UserLogoutUp;
import com.cwa.net.ClientSendMessage;
import com.cwa.net.netty.NettyServer;
import com.cwa.net.netty.handler.NettyHandler;
import com.cwa.net.netty.message.NettyMessageType;
import com.cwa.net.netty.message.NettySendMessage;

public class NettyStartServer {
	protected static final Logger logger = LoggerFactory.getLogger(NettyStartServer.class);

	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("propertiesconfig/log4j.xml");
		NettyStartServer start = new NettyStartServer();
//		 代码注入
		start.stratByJava();

		// spring注入
//		 start.startServerBySpring();
	}

	private void stratByJava() {

		// 消息配置
		List<MessageConfig> messageConfigs = new ArrayList<MessageConfig>();
		IGameHandler hand2 = new UserLoginRevHandler();
		IGameHandler hand3 = new UserLogoutRevHandler();
		IGameHandler hand4 = new GetUserInfoRevHandler();
		
//		MessageConfig m1 = new MessageConfig();
//		m1.setCommonId(100001);
//		m1.setMessage(UserRegisterUp.class);
//		m1.setMessageHandler(hand1);

		MessageConfig m2 = new MessageConfig();
		m2.setCommonId(100003);
		m2.setMessage(UserLoginUp.class);
		m2.setMessageHandler(hand2);

		MessageConfig m3 = new MessageConfig();
		m3.setCommonId(100005);
		m3.setMessage(UserLogoutUp.class);
		m3.setMessageHandler(hand3);

//		MessageConfig m4 = new MessageConfig();
//		m4.setCommonId(100002);
//		m4.setMessage(UserRegisterDown.class);

		MessageConfig m5 = new MessageConfig();
		m5.setCommonId(100004);
		m5.setMessage(UserLoginDown.class);
		
		MessageConfig m6 = new MessageConfig();
		m6.setCommonId(100006);
		m6.setMessage(GetUserinfoUp.class);
		m6.setMessageHandler(hand4);

		MessageConfig m7 = new MessageConfig();
		m7.setCommonId(100007);
		m7.setMessage(GetUserinfoDown.class);

//		messageConfigs.add(m1);
		messageConfigs.add(m2);
		messageConfigs.add(m3);
//		messageConfigs.add(m4);
		messageConfigs.add(m5);
		messageConfigs.add(m6);
		messageConfigs.add(m7);
		ProBuffMessageHandler protohandler = new ProBuffMessageHandler();
		protohandler.setMessageConfigs(messageConfigs);

		ConfigMessage configMessage=new ConfigMessage();
		configMessage.setMessageConfigs(messageConfigs);
		
		ClientSendMessage sessionManager = new NettySendMessage();
		sessionManager.setConfigMessage(configMessage);
		SendMessageService send = new SendMessageService();
		send.setSend(sessionManager);

		ManagerService managerService = new ManagerService();
		managerService.setSessionManager(sessionManager);
		// 建立连接处理类
		List<ICreateSessionHandler> createSessionHandlerList = new ArrayList<ICreateSessionHandler>();

		// 断开连接处理类
		List<IClosedSessionHandler> closeSessionHandlerList = new ArrayList<IClosedSessionHandler>();


		NettyHandler gameHandler = new NettyHandler();
		gameHandler.setMessageHandler(protohandler);
		gameHandler.setCreateSessionHandlerList(createSessionHandlerList);
		gameHandler.setCloseSessionHandlerList(closeSessionHandlerList);
		gameHandler.setSessionManager(sessionManager);

		IMessageType messageType = new NettyMessageType();

		NettyServer nettyServer = new NettyServer();
		nettyServer.setPort(8099);
		nettyServer.setSubReactorThreadNum(5);
		nettyServer.setHandler(gameHandler);
		nettyServer.setMessageType(messageType);
		nettyServer.setAllIdleTimeSeconds(600);
		nettyServer.setReaderIdleTimeSeconds(300);
		nettyServer.setWriterIdleTimeSeconds(300);
		nettyServer.startup();

	}

	// spring注入启动
	private void startServerBySpring() {
		GenericApplicationContext ctx = new GenericApplicationContext();
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
		xmlReader.loadBeanDefinitions(new FileSystemResource("config/root.xml"));
		ctx.refresh();
		// 启动各个服务
		NettyServer nettyServer = (NettyServer) ctx.getBean("netty");
		nettyServer.startup();
	}

}
