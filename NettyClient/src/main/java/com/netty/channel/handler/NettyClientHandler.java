package com.netty.channel.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);
    private final ByteBuf message;
    ;

    public NettyClientHandler() {
        message = Unpooled.buffer(256);
        byte[] str = "Hello world!".getBytes();
        message.writeBytes(str);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) {
        logger.info("Channel Registered : {}", channelHandlerContext.channel().toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        logger.info("Connected with {}", channelHandlerContext.channel().remoteAddress());
        channelHandlerContext.writeAndFlush(message);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        logger.info("Handler added : {}", channelHandlerContext.channel().toString());
    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf buf) {
        int rBytes = buf.readableBytes();
        logger.info("msg:{}({})", buf.toString(CharsetUtil.UTF_8), rBytes);
        ///channelHandlerContext.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        //NettyChannelManager.getInstance().stopClient();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        logger.info("Handler removed : {}", channelHandlerContext.channel().toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        logger.info("Disconnected with : {}", channelHandlerContext.channel().remoteAddress());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) {
        logger.info("Channel Unregistered : {}", channelHandlerContext.channel().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        cause.printStackTrace();
        channelHandlerContext.close();
    }
}
