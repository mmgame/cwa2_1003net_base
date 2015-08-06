package com.cwa.mina;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.FileSystemResource;

import com.cwa.ManagerService;
import com.cwa.SendMessageService;
import com.cwa.handler.IClosedSessionHandler;
import com.cwa.handler.ICreateSessionHandler;
import com.cwa.handler.server.IGameHandler;
import com.cwa.handler.server.UserLoginRevHandler;
import com.cwa.handler.server.UserLogoutRevHandler;
import com.cwa.message.ConfigMessage;
import com.cwa.message.MessageConfig;
import com.cwa.message.ProBuffMessageHandler;
import com.cwa.message.UserMessage.UserLoginDown;
import com.cwa.message.UserMessage.UserLoginUp;
import com.cwa.message.UserMessage.UserLogoutUp;
import com.cwa.net.ClientSendMessage;
import com.cwa.net.mina.MinaServer;
import com.cwa.net.mina.filter.MessageCodecFactory;
import com.cwa.net.mina.filter.MessageReceiveDecoder;
import com.cwa.net.mina.filter.MessageSendEncoder;
import com.cwa.net.mina.handler.MinaHandler;
import com.cwa.net.mina.heartbeat.HeartBeatMessageFactory;
import com.cwa.net.mina.heartbeat.HeartBeatRequestTimeoutHandler;
import com.cwa.net.mina.message.MinaMessageType;
import com.cwa.net.mina.message.MinaSendMessage;

public class MinaStartServer {

	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("propertiesconfig/log4j.xml");
		MinaStartServer gameServer = new MinaStartServer();
		// 代码初始化
		gameServer.startByJava();

		// spring注入
		 gameServer.startServerBySpring();
	}

	private void startByJava() {

	
		// 消息配置
		List<MessageConfig> messageConfigs = new ArrayList<MessageConfig>();
		IGameHandler hand2 = new UserLoginRevHandler();
		IGameHandler hand3 = new UserLogoutRevHandler();
		MessageConfig m1 = new MessageConfig();
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

		messageConfigs.add(m1);
		messageConfigs.add(m2);
		messageConfigs.add(m3);
//		messageConfigs.add(m4);
		messageConfigs.add(m5);
		ProBuffMessageHandler protohandler = new ProBuffMessageHandler();
		protohandler.setMessageConfigs(messageConfigs);
		ConfigMessage configMessage=new ConfigMessage();
		configMessage.setMessageConfigs(messageConfigs);
		
		ClientSendMessage sessionManager = new MinaSendMessage();
		sessionManager.setConfigMessage(configMessage);
		SendMessageService send = new SendMessageService();
		send.setSend(sessionManager);
		
		ManagerService managerService = new ManagerService();
		managerService.setSessionManager(sessionManager);
		// 建立连接处理类
		List<ICreateSessionHandler> createSessionHandlerList = new ArrayList<ICreateSessionHandler>();


		// 断开连接处理类
		List<IClosedSessionHandler> closeSessionHandlerList = new ArrayList<IClosedSessionHandler>();


		MinaHandler gameHandler = new MinaHandler();
		gameHandler.setCreateSessionHandlerList(createSessionHandlerList);
		gameHandler.setCloseSessionHandlerList(closeSessionHandlerList);
		gameHandler.setMessageHandler(protohandler);

		// 过滤器
		IoFilter executorFilter = new ExecutorFilter(5, 10);
		IoFilter loggingFilter = new LoggingFilter();

		// 心跳设置过滤器
		HeartBeatMessageFactory heartBeatMessageFactory = new HeartBeatMessageFactory();
		heartBeatMessageFactory.setHeartbeatId(100);
		HeartBeatRequestTimeoutHandler heartBeatRequestTimeoutHandler = new HeartBeatRequestTimeoutHandler();
		KeepAliveFilter keepAliveFilter = new KeepAliveFilter(heartBeatMessageFactory, IdleStatus.BOTH_IDLE,
				heartBeatRequestTimeoutHandler);
		keepAliveFilter.setForwardEvent(false);
		keepAliveFilter.setRequestInterval(300);
		keepAliveFilter.setRequestTimeout(600);

		// 编码解码过滤器
		MessageSendEncoder messageSendEncoder = new MessageSendEncoder();
		MessageReceiveDecoder messageReceiveDecoder = new MessageReceiveDecoder();
		messageReceiveDecoder.setMaxPackSize(1024);
		MinaMessageType messageType = new MinaMessageType();
		messageReceiveDecoder.setMessageType(messageType);

		MessageCodecFactory messageCodecFactory = new MessageCodecFactory(messageSendEncoder, messageReceiveDecoder);
		ProtocolCodecFilter protocolCodecFilter = new ProtocolCodecFilter(messageCodecFactory);

		Map<String, IoFilter> filters = new LinkedHashMap<String, IoFilter>();

		filters.put("protocolCodecFilter", protocolCodecFilter);
		filters.put("logger", loggingFilter);
		filters.put("heartbeat", keepAliveFilter);
		filters.put("exceutor", executorFilter);

		MinaServer acceptor = new MinaServer();
		acceptor.setFilters(filters);
		acceptor.setGameHandler(gameHandler);
		acceptor.setPort(10001);

		acceptor.startup();

	}

	// spring注入启动
	private void startServerBySpring() {
		try {
			GenericApplicationContext ctx = new GenericApplicationContext();
			XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
			xmlReader.loadBeanDefinitions(new FileSystemResource("config/root.xml"));
			ctx.refresh();
			// 启动各个服务
			MinaServer acceptor = (MinaServer) ctx.getBean("mina");
			acceptor.startup();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
