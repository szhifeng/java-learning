package com.example.concurrent.netty.core;

import com.example.concurrent.netty.service.NettyTCPServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author szf
 * @describe: 初始化 netty 服务端
 * @Date 2022/5/27 15:37
 */
@Slf4j
@Component
public class NettyServiceCore {

    private NettyTCPServer nettyTCPServer;
    @Value("${device.http.port}")
    private int httpPort;
    @Value("${device.socket.port}")
    private int socketPort;

    /**
     * 监听 netty 多端通信
     *
     * @param event
     * @throws Exception
     */
    @EventListener
    public void onContextRefreshed(ContextRefreshedEvent event) throws Exception {
        log.info("容器启动完成，开始建立 netty 多端通信！");
        nettyTCPServer = new NettyTCPServer();
        nettyTCPServer.bind(httpPort, socketPort);
    }

    /**
     * 程序关闭监听
     *
     * @throws Exception
     */
    @EventListener
    public void onContextClosed(ContextClosedEvent event) throws Exception {
        log.info("容器关闭，结束 netty 多端通信！");
        nettyTCPServer.unbind();
    }

}
