package com.netty.channel.decoder.frameChunkDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FrameChunkDecoderTest {
    @Test
    public static void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer();

        // 9 바이트 버퍼
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();

        // 3 바이트 프레임
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FrameChunkDecoder(3));

        assertTrue(embeddedChannel.writeInbound(input.readBytes(2)));

        try {
            embeddedChannel.writeInbound(input.readBytes(4));
            Assert.fail();
        } catch (TooLongFrameException e) {
        }

        assertTrue(embeddedChannel.writeInbound(input.readBytes(3)));
        assertTrue(embeddedChannel.finish());

        ByteBuf readBuf = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), readBuf);
        System.out.println(readBuf.toString());
        readBuf.release();

        readBuf = embeddedChannel.readInbound();
        assertEquals(buf.skipBytes(4).readSlice(3), readBuf);
        System.out.println(readBuf.toString());
        readBuf.release();

        assertNull(embeddedChannel.readInbound());
        buf.release();
    }
}
