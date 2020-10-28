package com.netty.channel.test;

import com.netty.channel.decoder.fixedLengthFrameDecoder.FixedLengthFrameDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

public class FixedLengthFrameDecoderTest {
    @Test
    public static void testFrameDecoded() {
        ByteBuf buf = Unpooled.buffer();

        // 9 바이트 버퍼
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();

        // 3 바이트 프레임
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertTrue(embeddedChannel.writeInbound(input.retain()));
        assertTrue(embeddedChannel.finish());

        // 앞에 3 바이트 만큼 잘라서 보내고 확인한다.
        ByteBuf readBuf = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), readBuf);
        System.out.println(readBuf.toString());
        readBuf.release();

        readBuf = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), readBuf);
        System.out.println(readBuf.toString());
        readBuf.release();

        readBuf = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), readBuf);
        System.out.println(readBuf.toString());
        readBuf.release();

        assertNull(embeddedChannel.readInbound());
        buf.release();
    }

    @Test
    public static void testFrameDecoded2() {
        ByteBuf buf = Unpooled.buffer();

        // 9 바이트 버퍼
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();

        // 3 바이트 프레임
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        // 정해진 바이트 프레임 크기 만큼 읽지 못하였기 때문에(readInbound) false 반환
        assertFalse(embeddedChannel.writeInbound(input.readBytes(2)));
        assertTrue(embeddedChannel.writeInbound(input.readBytes(7)));
        assertTrue(embeddedChannel.finish());

        // 앞에 3 바이트 만큼 잘라서 보내고 확인한다.
        ByteBuf readBuf = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), readBuf);
        System.out.println(readBuf.toString());
        readBuf.release();

        readBuf = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), readBuf);
        System.out.println(readBuf.toString());
        readBuf.release();

        readBuf = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), readBuf);
        System.out.println(readBuf.toString());
        readBuf.release();

        assertNull(embeddedChannel.readInbound());
        buf.release();
    }
}
