package com.example.concurrent.netty.client;

import com.example.concurrent.netty.utils.NettyUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author szf
 * @describe: 客户端协议数据处理
 * @Date 2022/5/27 14:55
 */
@Slf4j
@Service
@ChannelHandler.Sharable
public class ClientHeartBeatHandler extends ChannelInboundHandlerAdapter {

    /**
     * 接收报文
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        int len = byteBuf.readableBytes();
        byte[] old = new byte[len];
        for (int i = 0; i < len; i++) {
            old[i] = byteBuf.readByte();
        }
        String message = NettyUtils.bytesToHexString(old);
        log.info(String.format("message:%s", message));
        //相关处理
        System.out.println("收到服务端" + message + "的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 异常信息
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof Exception) {
            log.info("异常捕获");
            cause.printStackTrace();
        }
    }

    /**
     * 接入连接
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("CLIENT" + getRemoteAddress(ctx) + " 接入连接");
        //发送消息到服务端
        ctx.writeAndFlush(Unpooled.copiedBuffer("客户端发起请求，over！", CharsetUtil.UTF_8));
    }

    /**
     * 断开连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("CLIENT" + getRemoteAddress(ctx) + " 断开连接");
        ctx.close();
    }

    /**
     * 获取设备IP地址
     */
    public static String getRemoteAddress(ChannelHandlerContext ctx) {
        String address = ctx.channel().remoteAddress().toString();
        log.info("设备地址：" + address);
        return address;
    }
}
