package com.udpMonitor.handler;

import com.udpMonitor.model.LogEvent;
import com.udpMonitor.recorder.FileRecorder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogEvent logEvent) throws Exception {
        StringBuilder builder = new StringBuilder();

        builder.append(logEvent.getRecieved());
        builder.append(" [");
        builder.append(logEvent.getSource());
        builder.append("] [");
        builder.append(logEvent.getLogFile());
        builder.append("] : ");
        builder.append(logEvent.getMsg());

        System.out.println(builder.toString());

        FileRecorder fileRecorder = new FileRecorder(logEvent.getLogFile(), logEvent.getRecieved());
        fileRecorder.writeData(builder.toString().getBytes(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext channelHandlerContext, Throwable cause) {
        cause.printStackTrace();
        channelHandlerContext.close();
    }
}
