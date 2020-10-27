package com.netty.channel.handler;

import io.netty.channel.socket.DatagramPacket;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class NettyServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        logger.info("Server address : {}", channelHandlerContext.channel().localAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) {
        ByteBuf buf = datagramPacket.content();
        int rBytes = buf.readableBytes();
        String response = buf.toString(CharsetUtil.UTF_8);

        channelHandlerContext.write(new DatagramPacket(
                Unpooled.copiedBuffer(response, CharsetUtil.UTF_8),
                datagramPacket.sender()
        ));

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
