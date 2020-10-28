package com.netty.channel.handler;

import com.netty.channel.decoder.fixedLengthFrameDecoder.ByteToIntegerDecoder;
import com.netty.channel.encoder.fixedLengthFrameEncoder.IntegerToByteEncoder;

public class CombinedChannelDuplexHandler extends io.netty.channel.CombinedChannelDuplexHandler<ByteToIntegerDecoder, IntegerToByteEncoder> {
    public CombinedChannelDuplexHandler () {
        super(new ByteToIntegerDecoder(), new IntegerToByteEncoder());
    }
}
