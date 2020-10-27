package com.netty.channel.handler;

import com.netty.channel.NettyChannelManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        logger.info("Server address : {}", channelHandlerContext.channel().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        int rBytes = buf.readableBytes();
        channelHandlerContext.write(buf);
        logger.info("msg : {}({})", buf.toString(CharsetUtil.UTF_8), rBytes);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        cause.printStackTrace();
        channelHandlerContext.close();
    }
}
