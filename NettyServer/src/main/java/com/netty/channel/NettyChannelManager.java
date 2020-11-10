package com.netty.channel;

import com.netty.channel.server.NettyServer;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class NettyChannelManager {
    private static final Logger logger = LoggerFactory.getLogger(NettyChannelManager.class);
    private static NettyServer server;
    private static NettyChannelManager manager = null;
    private final List<Channel> channelList;
    private final List<String> clientList;

    private NettyChannelManager() {
        channelList = new ArrayList<>();
        clientList = new ArrayList<>();
    }

    public static NettyChannelManager getInstance() {
        if (manager == null) {
            manager = new NettyChannelManager();
        }
        return manager;
    }

    public void startServer(int port) {
        openChannel(port);
    }

    public void stopServer() {
        closeChannel();
        server.stop();
    }

    private void openChannel(int port) {
        server = new NettyServer();
        server.run();

        Channel channel = server.openChannel("127.0.0.1", port);
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
            server.closeChannel(ch);
            logger.debug("Success to close Channel({})", ch.toString());
        }
        channelList.clear();
    }

    public void addClient(String clientName) {
        clientList.add(clientName);
    }

    public void deleteClient(String clientName) {
        clientList.remove(clientName);
    }

    public String getClientList() {
        String clients = "";
        for (String client : clientList) {
            clients += client + "\n";
        }
        return clients;
    }

    public boolean findClient(String clientName) {
        if(clientList.contains(clientName)) {
            return true;
        }
        return false;
    }
}
