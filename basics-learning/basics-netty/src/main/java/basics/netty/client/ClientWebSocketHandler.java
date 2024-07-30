package basics.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 * @author szf
 * @describe: 处理web-socket
 * @Date 2022/5/27 14:57
 */

@Service
@ChannelHandler.Sharable
public class ClientWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger log = Logger.getLogger(ClientWebSocketHandler.class.getName());
    /**
     * 建立连接
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channel.id();
        log.info("与服务端建立连接，通道开启！channelId:"+ channel.id());
    }

    /**
     * 服务端与服务器关闭连接的时候触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("与服务端建立连接，通道关闭！");
    }

    /**
     * 服务器接受服务端的数据信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        log.info("服务器收到的数据：" + msg.text());
    }
}
