package com.cwa.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfigMessage implements IConfigMessage{
	private List<MessageConfig> messageConfigs;
	private Map<Class<?>, MessageConfig> msgIdMap = new HashMap<Class<?>, MessageConfig>();

	@Override
	public int getMsgId(Class<?> msgClass) {
		MessageConfig messageConfig = msgIdMap.get(msgClass);
		return messageConfig.getCommonId();
	}
	private void init() {
		for (MessageConfig messageConfig : messageConfigs) {
			msgIdMap.put(messageConfig.getMessage(), messageConfig);
		}
	}
	// ------------------------------------------------------------------------------
	public void setMessageConfigs(List<MessageConfig> messageConfigs) {
		this.messageConfigs = messageConfigs;
		init();
	}

}
