package com.netty.channel.test;

import com.netty.channel.handler.NettyServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class NettyServerTest {
    @Test
    public void test() {
        String msg = "Netty Test\n";
        EmbeddedChannel ch = new EmbeddedChannel(new NettyServerHandler());

        ByteBuf wBuf = Unpooled.wrappedBuffer(msg.getBytes());
        ch.writeInbound(wBuf);

        ByteBuf rBuf = ch.readOutbound();
        Assert.assertNotNull(rBuf);
    }
}
