package com.example.concurrent.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * netty 中 channel 通道的类型：
 * NioSocketChannel, 代表异步的客户端 TCP Socket 连接.
 * NioServerSocketChannel, 异步的服务器端 TCP Socket 连接.
 * NioDatagramChannel, 异步的 UDP 连接
 * NioSctpChannel, 异步的客户端 Sctp 连接.
 * NioSctpServerChannel, 异步的 Sctp 服务器端连接.
 * OioSocketChannel, 同步的客户端 TCP Socket 连接.
 * OioServerSocketChannel, 同步的服务器端 TCP Socket 连接.
 * OioDatagramChannel, 同步的 UDP 连接
 * OioSctpChannel, 同步的 Sctp 服务器端连接.
 * OioSctpServerChannel, 同步的客户端 TCP Socket 连接
 *
 * @author szf
 * @describe: netty 客户端
 * @Date 2022/5/27 14:44
 */
@Slf4j
@Service
public class NettyTCPClient {
    private EventLoopGroup bossGroup = null;

    public NettyTCPClient() {
        this.bossGroup = new NioEventLoopGroup(2);
    }

    /**
     * 启动并绑定 端口
     */
    public void bindTCP(String inetHost, int tcp) throws Exception {
        //创建 异步的客户端 TCP Socket 客户端的启动对象，设置参数
        Bootstrap TCPBootstrap = new Bootstrap();
        TCPBootstrap.group(bossGroup)
                //设置客户端通道实现类型(异步的服务器端 TCP Socket 连接)
                .channel(NioSocketChannel.class)
                //初始化客户端可连接队列,指定了队列的大小128
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        //添加客户端通道的处理器
                        ch.pipeline().addLast(new ClientHeartBeatHandler());
                    }
                });
        //绑定监听端口，调用sync同步阻塞方法等待绑定操作完成，完成后返回ChannelFuture类似于JDK中Future
        ChannelFuture futureDevice = TCPBootstrap.connect(inetHost, tcp).sync();
        if (futureDevice.isSuccess()) {
            log.info("TCP 客户端启动成功");
        } else {
            log.info("TCP 客户端启动失败");
            futureDevice.cause().printStackTrace();
            bossGroup.shutdownGracefully(); //关闭线程组
        }
        //成功绑定到端口之后,给channel增加一个 管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程。
        futureDevice.channel().closeFuture().sync();
    }

    /**
     * 启动并绑定 端口
     */
    public void bindWebSocket(String inetHost, int socket) throws Exception {
        Bootstrap webBootstrap = new Bootstrap();
        webBootstrap.group(bossGroup)
                //设置客户端通道实现类型(异步的服务器端 TCP Socket 连接)
                .channel(NioSocketChannel.class)
                //初始化客户端可连接队列,指定了队列的大小128
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        //添加客户端通道的处理器
                        ch.pipeline().addLast(new HttpServerCodec())
                                .addLast(new ChunkedWriteHandler())
                                .addLast(new HttpObjectAggregator(65535))
                                .addLast(new WebSocketServerProtocolHandler("/ws", "WebSocket", true, 65535))
                                .addLast(new ClientWebSocketHandler());
                    }
                });
        //绑定监听端口，调用sync同步阻塞方法等待绑定操作完成，完成后返回ChannelFuture类似于JDK中Future
        ChannelFuture futureWebSocket = webBootstrap.connect(inetHost, socket).sync();
        if (futureWebSocket.isSuccess()) {
            log.info("WEB-SOCKET客户端启动成功");
        } else {
            log.info("WEB-SOCKET客户端启动失败");
            futureWebSocket.cause().printStackTrace();
            bossGroup.shutdownGracefully(); //关闭线程组
        }
        //成功绑定到端口之后,给channel增加一个 管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程。
        futureWebSocket.channel().closeFuture().sync();
    }

    /**
     * 关闭客户端
     */
    public void cloce() {
        if (null != bossGroup && !bossGroup.isShutdown()) {
            bossGroup.shutdownGracefully();
            bossGroup = null;
        }
    }
}
