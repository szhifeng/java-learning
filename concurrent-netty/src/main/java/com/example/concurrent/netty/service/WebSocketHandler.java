package com.example.concurrent.netty.service;

import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author szf
 * @describe: 处理web-socket
 * @Date 2022/5/27 14:57
 *
 */
@Slf4j
@Service
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 建立连接
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channel.id();
        log.info("与客户端建立连接，通道开启！channelId:{}", channel.id());
    }

    /**
     * 客户端与服务器关闭连接的时候触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("与客户端建立连接，通道关闭！");
    }

    /**
     * 服务器接受客户端的数据信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("服务器收到的数据：" + msg.text());
    }

    /**
     * 给固定的人发消息
     */
    private void sendMessage(ChannelHandlerContext ctx) {
        log.info("服务器回复：0");
        ctx.channel().writeAndFlush(new TextWebSocketFrame("0")).addListener((ChannelFutureListener) future -> {
            log.info("WEB-SOCKET 心跳回复:0");
            log.info("WEB SOCKET DONE:{}", future.isDone());
            log.info("WEB SOCKET SUCCESS:{}", future.isSuccess());
        });
    }
}
