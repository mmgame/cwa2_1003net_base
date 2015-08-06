package com.cwa.message;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.ISession;
import com.cwa.handler.IMessageHandler;
import com.cwa.net.ISessionManager;
import com.google.protobuf.Parser;

/**
 * probuff消息处理器
 * 
 * @author mausmars
 * 
 */
public class ProBuffMessageHandler implements IMessageHandler<Object>{
	private static final Logger logger = LoggerFactory.getLogger(ProBuffMessageHandler.class);

	private List<MessageConfig> messageConfigs;
	// ---------------------------------
	private Map<Integer, MessageConfig> mcMap = new HashMap<Integer, MessageConfig>();

	@Override
	public void handle(Object msg, ISession session) {
		long startTime = System.currentTimeMillis();
		IMessage m = (IMessage) msg;

		MessageConfig messageConfig = mcMap.get(m.getCommandId());
		if (messageConfig == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("None command handler! command=" + m.getCommandId());
			}
			return;
		}
		try {
			IMessageHandler<Object> messageHandler = messageConfig.getMessageHandler();

			Parser<?> parser = getParser(messageConfig);
			// 用Reader，避免拷贝字节数组
			InputStream is = new ByteArrayInputStream(m.getBodyByte());
			Object message = parser.parseFrom(is);
			if (logger.isInfoEnabled()) {
				logger.info("receiveMessage :"+ m.getCommandId() +"-->" + message.getClass().getSimpleName()+"  UserId:"+ session.getAttachment(ISessionManager.UserId_Key));
			}
			messageHandler.handle(message, session);
			long executeTime = System.currentTimeMillis() - startTime;
			if (executeTime > 500) {
				if (logger.isInfoEnabled()) {
					logger.info("执行慢 :" + (System.currentTimeMillis() - startTime) + "ms! ");
				}
			} else {
				if (logger.isInfoEnabled()) {
//					logger.info("执行时间 :" + (System.currentTimeMillis() - startTime) + "ms! ");
				}
			}
		} catch (Exception e) {
			logger.error("receive Error message:-->"+m.getCommandId(),e);
		}
	}

	private void init() {
		for (MessageConfig messageConfig : messageConfigs) {
			mcMap.put(messageConfig.getCommonId(), messageConfig);
		}
	}
	private Parser<?> getParser(MessageConfig messageConfig) throws Exception {
		Class<?> cls = messageConfig.getMessage();
		Field field = cls.getField("PARSER");
		Parser<?> parser = (Parser<?>) field.get(cls);
		return parser;
	}
	// ------------------------------------------------------------------------------
	public void setMessageConfigs(List<MessageConfig> messageConfigs) {
		this.messageConfigs = messageConfigs;
		init();
	}
}
