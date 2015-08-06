package com.cwa.net.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.INetServer;
import com.cwa.message.IMessageType;
import com.cwa.net.ISessionManager;
import com.cwa.net.netty.filter.MessageDecoder;
import com.cwa.net.netty.filter.MessageEncoder;
import com.cwa.net.netty.heartbeat.HeartBeatRequestTimeoutHandler;

/**
 * netty启动类代理
 * 
 * @author tzy
 * 
 */
public class NettyServer implements INetServer {
	private static final Logger logger = LoggerFactory.getLogger(INetServer.class);

	private int port = 10001; // 端口
	// 主反应器线程数量
	private int mainReactorThreadNum = Runtime.getRuntime().availableProcessors() * 2; // 默认
	private int subReactorThreadNum = 4; // 子反应器线程数量
	private int handlerThreadNum = 5; // 业务线程数量
	private IMessageType messageType;//
	private ChannelHandler handler;
	private int readerIdleTimeSeconds; // 读超时
	private int writerIdleTimeSeconds; // 写超时
	private int allIdleTimeSeconds;// 全部超时
	private ISessionManager sessionManager;

	// --------------------------------------------
	@Override
	public void startup() {
		// 初始化ServerBootstrap启动类
		// 第一个NioEventLoopGroup通常被称为'parentGroup'，用于接收所有连接到服务器端的客户端连接
		EventLoopGroup bossGroup = new NioEventLoopGroup(mainReactorThreadNum);// 用于接收发来的连接请求
		// 第二个被称为'childGroup',当有新的连接进来时将会被注册到worker中
		EventLoopGroup workerGroup = new NioEventLoopGroup(subReactorThreadNum); // 用于处理parentGroup接收并注册给child的连接中的信息
		// 业务线程池
		final EventExecutorGroup handlerGroup = new DefaultEventExecutorGroup(handlerThreadNum);

		ServerBootstrap sb = new ServerBootstrap();
		sb.group(bossGroup, workerGroup);
		sb.channel(NioServerSocketChannel.class);
		sb.localAddress(new InetSocketAddress(port));
		sb.childHandler(new ChannelInitializer<SocketChannel>() {
			// 4.0不允许加入加入一个ChannelHandler超过一次，除非它由@Sharable注解。
			// Be aware that sub-classes of ByteToMessageDecoder MUST NOT
			// annotated with @Sharable
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				// ch.pipeline().addLast(new LoggingHandler(LogLevel.ERROR));
				ch.pipeline().addLast(new LoggingHandler());
				ch.pipeline().addLast(new MessageDecoder(messageType));
				ch.pipeline().addLast(new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
				ch.pipeline().addLast(new HeartBeatRequestTimeoutHandler());
				ch.pipeline().addLast(new MessageEncoder());
				ch.pipeline().addLast(handlerGroup, handler);
			}
		});
		// option() 方法用于设置监听套接字
		// ServerSocket的最大客户端等待队列
		sb.option(ChannelOption.SO_BACKLOG, 128);
		// childOption()则用于设置连接到服务器的客户端套接字
		sb.childOption(ChannelOption.SO_KEEPALIVE, true);
		sb.childOption(ChannelOption.TCP_NODELAY, true);// 禁用negal算法
		sb.childOption(ChannelOption.SO_REUSEADDR, true); // 重用地址

		// 那么close会等到发送的数据已经确认了才返回。但是如果对方宕机，超时，那么会根据linger设定的时间返回。
		sb.childOption(ChannelOption.SO_LINGER, 1000);
		// 设置socket调用InputStream读数据的超时时间，以毫秒为单位，如果超过这个时候，会抛出java.net.SocketTimeoutException。
		sb.childOption(ChannelOption.SO_TIMEOUT, 1000);

		// heap buf 's better
		sb.childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false));
		sb.childOption(ChannelOption.SO_RCVBUF, 1048576);
		sb.childOption(ChannelOption.SO_SNDBUF, 1048576);
		sb.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1048576);

		try {
			ChannelFuture cf = sb.bind().sync();
			if (logger.isInfoEnabled()) {
				logger.info("Start server Success!");
			}
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error("Start server ERROR!", e);
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	@Override
	public void shutdown() {

	}

	@Override
	public String toString() {
		return "NettyServer [port=" + port + ", mainReactorThreadNum=" + mainReactorThreadNum + " subReactorThreadNum="
				+ subReactorThreadNum + " handlerThreadNum=" + handlerThreadNum + "]";
	}

	public int getPort() {
		return port;
	}

	@Override
	public ISessionManager getSessionManager() {
		return sessionManager;
	}

	// -----------------------------------------
	public void setPort(int port) {
		this.port = port;
	}

	public void setSubReactorThreadNum(int subReactorThreadNum) {
		this.subReactorThreadNum = subReactorThreadNum;
	}

	public void setMainReactorThreadNum(int mainReactorThreadNum) {
		this.mainReactorThreadNum = mainReactorThreadNum;
	}

	public void setHandlerThreadNum(int handlerThreadNum) {
		this.handlerThreadNum = handlerThreadNum;
	}

	public void setMessageType(IMessageType messageType) {
		this.messageType = messageType;
	}

	public void setHandler(ChannelHandler handler) {
		this.handler = handler;
	}

	public void setReaderIdleTimeSeconds(int readerIdleTimeSeconds) {
		this.readerIdleTimeSeconds = readerIdleTimeSeconds;
	}

	public void setWriterIdleTimeSeconds(int writerIdleTimeSeconds) {
		this.writerIdleTimeSeconds = writerIdleTimeSeconds;
	}

	public void setAllIdleTimeSeconds(int allIdleTimeSeconds) {
		this.allIdleTimeSeconds = allIdleTimeSeconds;
	}

	public void setSessionManager(ISessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
}
