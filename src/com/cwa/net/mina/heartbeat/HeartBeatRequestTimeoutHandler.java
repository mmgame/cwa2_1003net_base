package com.cwa.net.mina.heartbeat;



import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

/**
 * @author Administrator
 * 
 */
public class HeartBeatRequestTimeoutHandler implements KeepAliveRequestTimeoutHandler {
	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter filter, IoSession session) throws Exception {
		session.close(true);
	}
}
