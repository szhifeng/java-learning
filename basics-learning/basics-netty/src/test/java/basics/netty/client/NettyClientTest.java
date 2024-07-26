package basics.netty.client;

/**
 * @author szf
 * @describe: netty 测试类
 * @Date 2022/5/31 13:51
 */

public class NettyClientTest {
    private final static int httpPort = 8070;

//    @Test
    public void testPort() throws Exception {
        NettyTCPClient tcpClient = new NettyTCPClient();
        tcpClient.bindTCP("127.0.0.1", httpPort);
        tcpClient.cloce();
    }
}
