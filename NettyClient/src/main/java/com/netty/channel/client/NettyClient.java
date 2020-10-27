package com.netty.channel.client;

import com.netty.channel.handler.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private static final int NETTY_BUFFER_SIZE = 262144;
    private Bootstrap b;
    private NioEventLoopGroup group;
    ScheduledFuture<?> future;

    public NettyClient() {
    }

    public void run() {
        group = new NioEventLoopGroup(1);

        try {
            b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_SNDBUF, NETTY_BUFFER_SIZE)
                    .option(ChannelOption.SO_RCVBUF, NETTY_BUFFER_SIZE)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        public void initChannel(final NioSocketChannel ch) {
                            final ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            group.shutdownGracefully().sync();
            future.cancel(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Channel openChannel(String ip, int port) {
        InetAddress address;
        final ChannelFuture channelFuture;

        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            logger.warn("UnknownHostException is occurred. ip={} {}", ip, e.getMessage());
            return null;
        }

        try {
            channelFuture = b.connect(address, port).sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) {
                    if(channelFuture.isSuccess()) {
                        logger.debug("Success to connect");
                    }
                    else {
                        logger.debug("Fail to write");
                        channelFuture.cause().printStackTrace();
                        channelFuture.channel().close();
                    }
                }
            });

            future = channelFuture.channel().eventLoop().scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            if(channelFuture.channel().isOpen() && channelFuture.channel().isWritable()) {
                                logger.debug("Channel is alive {}", channelFuture.channel().toString());
                            }
                            else {
                                logger.debug("Channel is dead");
                            }
                        }
                    }
                    , 0, 2, TimeUnit.SECONDS);

            return channelFuture.channel();
        } catch (InterruptedException e) {
            logger.warn("InterruptedException is occurred. ip={} {}", ip, e.getMessage());
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public void closeChannel(Channel ch) {
        if (ch != null) {
            ch.close();
        }
    }
}
