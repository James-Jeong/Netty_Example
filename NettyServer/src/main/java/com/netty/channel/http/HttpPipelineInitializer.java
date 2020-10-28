package com.netty.channel.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpPipelineInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpPipelineInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        if(isClient) {
            pipeline.addLast("decoder", new HttpResponseDecoder()); // 서버로부터 응답을 받음 (Inbound)
            pipeline.addLast("encoder", new HttpRequestEncoder()); // 서버로 요청을 보냄 (Outbound)
        } else {
            pipeline.addLast("decoder", new HttpRequestDecoder()); // 클라이언트로부터 요청을 받음 (Inbound)
            pipeline.addLast("encoder", new HttpResponseEncoder()); // 클라이언트로부터 응답을 보냄 (Outbound)
        }
    }
}
