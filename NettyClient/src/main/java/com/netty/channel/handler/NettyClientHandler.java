package com.netty.channel.handler;

import com.gui.FrameManager;
import com.gui.model.ClientFrame;
import com.netty.channel.NettyChannelManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);
    private static NettyClientHandler handler = null;
    private ChannelHandlerContext chx;

    public void sendMessage(String inputStr) {
        final ByteBuf message = Unpooled.buffer(256);
        byte[] str = inputStr.getBytes();
        message.writeBytes(str);
        chx.writeAndFlush(message);
    }

    private NettyClientHandler() {}

    public static NettyClientHandler getInstance() {
        if (handler == null) {
            handler = new NettyClientHandler();
        }
        return handler;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) {
        chx = channelHandlerContext;
        logger.debug("Channel Registered : {}", chx.channel().toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        chx = channelHandlerContext;

        String content = "Connected with " + chx.channel().remoteAddress() + " (" + chx.channel().localAddress() + ")";
        logger.debug("{}", content);
        sendMessage(content);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        chx = channelHandlerContext;
        logger.debug("Handler added : {}", chx.channel().toString());
    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf buf) {
        chx = channelHandlerContext;
        int rBytes = buf.readableBytes();
        logger.debug("msg:{}({})", buf.toString(CharsetUtil.UTF_8), rBytes);

        String content = buf.toString(CharsetUtil.UTF_8) + "(" + rBytes + ")\n";

        ClientFrame clientFrame = FrameManager.getInstance().getFrame("Client");
        if(clientFrame.readText().equals("none")) {
            clientFrame.writeText(content);
        }
        else {
            clientFrame.appendText(content);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        chx = channelHandlerContext;
        //NettyChannelManager.getInstance().stopClient();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        chx = channelHandlerContext;
        logger.debug("Handler removed : {}", chx.channel().toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        chx = channelHandlerContext;
        logger.debug("Disconnected with : {}", chx.channel().remoteAddress());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) {
        chx = channelHandlerContext;
        logger.debug("Channel Unregistered : {}", chx.channel().toString());

        String content = "Disconnected with : " + chx.channel().remoteAddress() + "\n";
        ClientFrame clientFrame = FrameManager.getInstance().getFrame("Client");
        if(clientFrame.readText().equals("none")) {
            clientFrame.writeText(content);
        }
        else {
            clientFrame.appendText(content);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        chx = channelHandlerContext;
        cause.printStackTrace();
        chx.close();
    }
}
