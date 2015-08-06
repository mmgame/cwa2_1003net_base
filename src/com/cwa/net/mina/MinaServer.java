package com.cwa.net.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.cwa.INetServer;
import com.cwa.net.ISessionManager;

/**
 * mina服务器启动类代理
 * 
 * @author tzy
 * 
 *
 */
public class MinaServer implements INetServer {
	private Map<String, IoFilter> filters;
	private int port = 10001;
	private IoHandler gameHandler;
	private ISessionManager sessionManager;

	@Override
	public void startup() {
		// 过滤器
		DefaultIoFilterChainBuilder defaultIoFilterChainBuilder = new DefaultIoFilterChainBuilder();
		defaultIoFilterChainBuilder.setFilters(filters);

		// 初始化NioSocketAcceptor启动类
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setDefaultLocalAddress(new InetSocketAddress(port));// 绑定端口
		acceptor.setReuseAddress(true);
		acceptor.setHandler(gameHandler);// 配置handler
		acceptor.setFilterChainBuilder(defaultIoFilterChainBuilder);// 配置过滤器
		try {
			acceptor.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void shutdown() {
	}

	@Override
	public String toString() {
		return "MinaServer [port=" + port + "]";
	}

	// -------------------------------------------
	public void setFilters(Map<String, IoFilter> filters) {
		this.filters = filters;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setGameHandler(IoHandler gameHandler) {
		this.gameHandler = gameHandler;
	}

	@Override
	public ISessionManager getSessionManager() {
		return sessionManager;
	}
	
	public void setSessionManager(ISessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
}
