package com.cwa;

import com.cwa.net.ISessionManager;



public class ManagerService {

	public static ISessionManager sessionManager;

	public  void setSessionManager(ISessionManager sessionManager) {
		ManagerService.sessionManager = sessionManager;
	}
}
