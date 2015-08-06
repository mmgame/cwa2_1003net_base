package com.cwa;

import com.cwa.net.ISessionManager;

/**
 * 服务接口
 * @author tzy

 *
 */
public interface INetServer {
	
	/**
	 * 启动服务
	 * 
	 */
	void startup();

	
	/**
	 * 关闭服务
	 * 
	 */
	void shutdown();
	
	/**
	 * 获取SessionManager
	 * @return
	 */
	ISessionManager getSessionManager();
}
