package com.cwa.net.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 心跳
 * @author tzy
 *
 */

public class HeartBeatRequestTimeoutHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE)
				ctx.close();
			if (event.state() == IdleState.WRITER_IDLE) {
				ctx.close();
			}
			if (event.state() == IdleState.ALL_IDLE) {
				ctx.close();
			}
		}
	}
}
