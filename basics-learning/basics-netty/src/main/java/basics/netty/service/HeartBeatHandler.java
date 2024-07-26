package basics.netty.service;

import basics.netty.utils.NettyUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * @author szf
 * @describe: 协议数据处理
 * @Date 2022/5/27 14:55
 */
@Service
@ChannelHandler.Sharable
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = Logger.getLogger(HeartBeatHandler.class.getName());
    /**
     * 接收报文
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        int len = byteBuf.readableBytes();
        log.info("---------------start process msg--------------------");
        log.info("readable bytes is:" + len);
        byte[] old = new byte[len];
        for (int i = 0; i < len; i++) {
            old[i] = byteBuf.readByte();
        }
        String message = NettyUtils.bytesToHexString(old);
        log.info(String.format("message:%s", message));
        //相关处理
    }

    /**
     * 接收到客户端信息完成
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("接收到客户端信息完成");
        //ctx.flush();
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已收到消息，并返回友好的问候！", CharsetUtil.UTF_8));
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
