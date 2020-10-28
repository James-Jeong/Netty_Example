package com.netty.channel.encoder.fixedLengthFrameEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
    private final int maxFrameSize;

    public AbsIntegerEncoder(int maxFrameSize) {
        if (maxFrameSize <= 0) {
            throw new IllegalArgumentException("Max Frame Size is not positive integer. ()" + maxFrameSize);
        }

        this.maxFrameSize = maxFrameSize;
    }


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (byteBuf.readableBytes() >= maxFrameSize) {
            int value = Math.abs(byteBuf.readInt());
            list.add(value);
        }
    }
}
