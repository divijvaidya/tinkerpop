package org.apache.tinkerpop.gremlin.driver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class AlwaysCloseFrameWebSockerServer extends TestWebSocketServerInitializer {
    @Override
    public void postInit(ChannelPipeline ch) {
        ch.addLast(new AlwaysCloseFrameHandler());
    }

    public class AlwaysCloseFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
            ctx.channel().writeAndFlush(new CloseWebSocketFrame());
        }
    }
}
