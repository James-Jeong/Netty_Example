package com.netty.channel.test;

import com.netty.channel.test.FrameDecoder.FixedLengthFrameDecoderTest;
import org.junit.Test;

public class NettyServerTest {
    @Test
    public void test() {
        FixedLengthFrameDecoderTest.testFrameDecoded();
    }
}
