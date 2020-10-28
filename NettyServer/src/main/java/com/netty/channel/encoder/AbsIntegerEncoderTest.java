package com.netty.channel.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbsIntegerEncoderTest {
    @Test
    public static void testEncoded() {
        ByteBuf buf = Unpooled.buffer();

        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new AbsIntegerEncoder(4));

        assertTrue(embeddedChannel.writeOutbound(buf));
        assertTrue(embeddedChannel.finish());

        for (int i = 1; i < 10; i++) {
            assertEquals(i, embeddedChannel.readOutbound());
        }

        assertNull(embeddedChannel.readOutbound());
    }

}
