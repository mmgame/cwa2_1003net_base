package com.cwa.mina;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.cwa.SendMessageService;
import com.cwa.handler.client.IClientGameHandler;
import com.cwa.handler.client.user.ClientUserLoginSendHandler;
import com.cwa.handler.client.user.ClientUserLogoutSendHandler;
import com.cwa.message.ConfigMessage;
import com.cwa.message.IMessageType;
import com.cwa.message.MessageConfig;
import com.cwa.message.ProBuffMessageHandler;
import com.cwa.message.UserMessage.UserLoginDown;
import com.cwa.message.UserMessage.UserLoginUp;
import com.cwa.message.UserMessage.UserLogoutUp;
import com.cwa.net.ClientSendMessage;
import com.cwa.net.mina.filter.MessageCodecFactory;
import com.cwa.net.mina.filter.MessageReceiveDecoder;
import com.cwa.net.mina.filter.MessageSendEncoder;
import com.cwa.net.mina.heartbeat.HeartBeatMessageFactory;
import com.cwa.net.mina.heartbeat.HeartBeatRequestTimeoutHandler;
import com.cwa.net.mina.message.MinaMessageType;
import com.cwa.net.mina.message.MinaSendMessage;

/**
 * Hello world!
 */
public class MinaClientServer {
	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("propertiesconfig/log4j.xml");

		final IMessageType messageType=new MinaMessageType();
//	// 消息配置
		
		IClientGameHandler hand1 = new ClientUserLoginSendHandler();
		IClientGameHandler hand2 = new ClientUserLogoutSendHandler();
		List<MessageConfig> messageConfigs = new ArrayList<MessageConfig>();
//		MessageConfig m1 = new MessageConfig();
//		m1.setCommonId(100001);
//		m1.setMessage(UserRegisterUp.class);

		MessageConfig m2 = new MessageConfig();
		m2.setCommonId(100003);
		m2.setMessage(UserLoginUp.class);

//		MessageConfig m3 = new MessageConfig();
//		m3.setCommonId(100002);
//		m3.setMessage(UserRegisterDown.class);
//		m3.setMessageHandler(hand1);

		MessageConfig m4 = new MessageConfig();
		m4.setCommonId(100004);
		m4.setMessage(UserLoginDown.class);
		m4.setMessageHandler(hand2);
		
		MessageConfig m5 = new MessageConfig();
		m5.setCommonId(100005);
		m5.setMessage(UserLogoutUp.class);
//		messageConfigs.add(m1);
		messageConfigs.add(m2);
//		messageConfigs.add(m3);
		messageConfigs.add(m4);
		messageConfigs.add(m5);

		
		ProBuffMessageHandler protohandler = new ProBuffMessageHandler();
		protohandler.setMessageConfigs(messageConfigs);
		ConfigMessage configMessage=new ConfigMessage();
		configMessage.setMessageConfigs(messageConfigs);
		
		ClientSendMessage sessionManager = new MinaSendMessage();
		sessionManager.setConfigMessage(configMessage);
		SendMessageService send=new SendMessageService();
		send.setSend(sessionManager);
		
		IoFilter executorFilter = new ExecutorFilter(5, 10);

		IoFilter loggingFilter = new LoggingFilter();
		MessageSendEncoder messageSendEncoder = new MessageSendEncoder();
		MessageReceiveDecoder messageReceiveDecoder = new MessageReceiveDecoder();
		messageReceiveDecoder.setMaxPackSize(1024);
		messageReceiveDecoder.setMessageType(messageType);

		
		// 心跳设置过滤器
		HeartBeatMessageFactory heartBeatMessageFactory = new HeartBeatMessageFactory();
		heartBeatMessageFactory.setHeartbeatId(100);
		HeartBeatRequestTimeoutHandler heartBeatRequestTimeoutHandler = new HeartBeatRequestTimeoutHandler();
		KeepAliveFilter keepAliveFilter = new KeepAliveFilter(heartBeatMessageFactory, IdleStatus.BOTH_IDLE,
				heartBeatRequestTimeoutHandler);
		keepAliveFilter.setForwardEvent(false);
		keepAliveFilter.setRequestInterval(30);
		keepAliveFilter.setRequestTimeout(40);
		
		
		
		
		
		MessageCodecFactory messageCodecFactory = new MessageCodecFactory(messageSendEncoder, messageReceiveDecoder);
		ProtocolCodecFilter protocolCodecFilter = new ProtocolCodecFilter(messageCodecFactory);

		DefaultIoFilterChainBuilder defaultIoFilterChainBuilder = new DefaultIoFilterChainBuilder();
		Map<String, IoFilter> filters = new LinkedHashMap<String, IoFilter>();
		filters.put("protocolCodecFilter", protocolCodecFilter);
		filters.put("logger", loggingFilter);
//		filters.put("heartbeat", keepAliveFilter);
		filters.put("exceutor", executorFilter);
		defaultIoFilterChainBuilder.setFilters(filters);

		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(30000);
		connector.setFilterChainBuilder(defaultIoFilterChainBuilder);

		connector.setHandler(new MinaClientHandler(protohandler));

		connector.connect(new InetSocketAddress("localhost", 10001));

	}
}