package com.netty.channel.test;

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
