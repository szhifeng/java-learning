package com.example.concurrent.netty;

import com.example.concurrent.netty.service.NettyTCPServer;
import org.junit.jupiter.api.Test;

/**
 * @author szf
 * @describe: netty 测试类
 * @Date 2022/5/31 13:51
 */

public class NettyServiceTest {
    private final static int httpPort = 8070;
    private final static int socketPort = 10001;

//    @Test
    public void testPort() throws Exception {
        NettyTCPServer tcpServer = new NettyTCPServer();
        tcpServer.bind(httpPort, socketPort);
    }
}
