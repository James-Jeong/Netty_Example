package com.udpMonitor.decoder;

import com.udpMonitor.model.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf data = datagramPacket.content();
        int dataIndex = data.indexOf(0, data.readableBytes(), LogEvent.SEPARATOR);

        String fileName = data.slice(0, dataIndex).toString(CharsetUtil.UTF_8);
        System.out.println(fileName);

        String logMsg = data.slice(dataIndex + 1, data.readableBytes()).toString(CharsetUtil.UTF_8);
        System.out.println(logMsg);

        LogEvent event = new LogEvent(datagramPacket.sender(), fileName, logMsg, System.currentTimeMillis());
        list.add(event);
    }
}
