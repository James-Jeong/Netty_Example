package com.netty.channel.server;

import com.netty.channel.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private ServerBootstrap b;
    private NioEventLoopGroup localGroup;
    private NioEventLoopGroup clientGroup;
    ScheduledFuture<?> future;

    public NettyServer() {
    }

    public void run() {
        localGroup = new NioEventLoopGroup(1);
        clientGroup = new NioEventLoopGroup();

        try {
            b = new ServerBootstrap();
            b.group(localGroup, clientGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        public void initChannel(final NioSocketChannel ch) {
                            final ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            localGroup.shutdownGracefully().sync();
            clientGroup.shutdownGracefully().sync();
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
            channelFuture = b.bind(address, port).sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) {
                    if(channelFuture.isSuccess()) {
                        logger.debug("Success to bind");
                    }
                    else {
                        logger.debug("Fail to read");
                        channelFuture.cause().printStackTrace();
                        channelFuture.channel().close();
                    }
                }
            });

            future = channelFuture.channel().eventLoop().scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            logger.debug("Channel is alive {}", channelFuture.channel().toString());
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
