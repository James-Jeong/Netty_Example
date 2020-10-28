package com.netty.channel.test;

import com.netty.channel.decoder.fixedLengthFrameDecoder.FixedLengthFrameDecoderTest;
import com.netty.channel.encoder.*;
import org.junit.Test;

public class NettyServerTest {
    @Test
    public void test() {
        // Decoder
        FixedLengthFrameDecoderTest.testFrameDecoded();
        FixedLengthFrameDecoderTest.testFrameDecoded2();

        //FrameChunkDecoderTest.testFramesDecoded();

        // Encoder
        AbsIntegerEncoderTest.testEncoded();
    }
}
