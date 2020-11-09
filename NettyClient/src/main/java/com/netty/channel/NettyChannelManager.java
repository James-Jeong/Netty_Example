package com.netty.channel;

import com.netty.channel.client.NettyClient;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class NettyChannelManager {
    private static final Logger logger = LoggerFactory.getLogger(NettyChannelManager.class);
    private static NettyClient client;
    private static NettyChannelManager manager = null;
    private final List<Channel> channelList;

    private NettyChannelManager() {
        channelList = new ArrayList<>();
    }

    public static NettyChannelManager getInstance() {
        if (manager == null) {
            manager = new NettyChannelManager();
        }
        return manager;
    }

    public void startClient(int port) {
        openChannel(port);
    }

    public void stopClient() {
        closeChannel();
        client.stop();
    }

    private void openChannel(int port) {
        client = new NettyClient();
        client.run();

        Channel channel = client.openChannel("127.0.0.1", port);
        channelList.add(channel);
        logger.debug("Success to add Channel({})", channel.toString());

        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void closeChannel() {
        for (Channel ch : channelList) {
            client.closeChannel(ch);
            logger.debug("Success to close Channel({})", ch.toString());
        }
        channelList.clear();
    }
}
