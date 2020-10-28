package com.udpBroadCaster.broadCaster;

import com.udpBroadCaster.encoder.LogEventEncoder;
import com.udpBroadCaster.model.LogEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

public class LogEventBroadCaster {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;


    public LogEventBroadCaster(InetSocketAddress address, File file) {
        this.file = file;
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address));
    }

    public void run() throws Exception {
        Channel channel = bootstrap.bind(0).sync().channel();

        long filePointer = 0;
        for (; ; ) {
            long fileLength = file.length();
            if (fileLength < filePointer) {
                filePointer = fileLength;
            } else if (fileLength > filePointer) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                randomAccessFile.seek(filePointer);

                String fileLine;
                while ((fileLine = randomAccessFile.readLine()) != null) {
                    System.out.println("sending... " + fileLine);
                    channel.writeAndFlush(new LogEvent(null, file.getAbsolutePath(), fileLine, -1));
                }

                filePointer = randomAccessFile.getFilePointer();
                randomAccessFile.close();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }
}
