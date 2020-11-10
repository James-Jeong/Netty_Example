package com.netty.channel.handler;

import com.gui.FrameManager;
import com.gui.model.ServerFrame;
import com.netty.channel.NettyChannelManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) {
        logger.debug("Channel Registered : {}", channelHandlerContext.channel().toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        String remoteAddress = channelHandlerContext.channel().remoteAddress().toString();
        logger.debug("Connected with : {}", remoteAddress);

        NettyChannelManager.getInstance().addClient(remoteAddress);

        ServerFrame serverFrame = FrameManager.getInstance().getFrame("Server");
        serverFrame.updateClientText();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        logger.debug("Handler added : {}", channelHandlerContext.channel().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        int rBytes = buf.readableBytes();
        channelHandlerContext.write(buf);
        logger.debug("msg : {}({})", buf.toString(CharsetUtil.UTF_8), rBytes);

        String content = buf.toString(CharsetUtil.UTF_8) + "\n";

        ServerFrame serverFrame = FrameManager.getInstance().getFrame("Server");
        if(serverFrame.readText().equals("none")) {
            serverFrame.writeText(content);
        }
        else {
            serverFrame.appendText(content);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.flush();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        logger.debug("Handler removed : {}", channelHandlerContext.channel().toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        String remoteAddress = channelHandlerContext.channel().remoteAddress().toString();
        logger.debug("Disconnected with : {}", remoteAddress);

        NettyChannelManager.getInstance().deleteClient(remoteAddress);

        ServerFrame serverFrame = FrameManager.getInstance().getFrame("Server");
        String content = "Disconnected with : " + channelHandlerContext.channel().remoteAddress();
        serverFrame.appendText(content + "\n");
        serverFrame.updateClientText();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) {
        logger.debug("Channel Unregistered : {}", channelHandlerContext.channel().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        cause.printStackTrace();
        channelHandlerContext.close();
    }
}
