package concurrent.api.basics;

import org.junit.jupiter.api.Test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class SocketTest {

//    @Test
    void connectTest(){
        try {
            InetSocketAddress socketAddress = new InetSocketAddress("172.16.3.10", 161);
            DatagramSocket datagramSocket = new DatagramSocket(socketAddress);
            if(!datagramSocket.isClosed()) {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                datagramSocket.receive(packet);
                String orgIp = packet.getAddress().getHostAddress();
                String orgName = packet.getAddress().getHostName();
                String info = new String(packet.getData(), 0, packet.getLength(), "GBK");
                System.out.printf("Host:%s;IP:%s;content:%s",orgName, orgIp, info);
            }
        }catch (Exception e){
            System.err.println("连接错误！"+ e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void connectTest1(){
        HashMap map = new HashMap<String,Object>();
        System.out.println((String) map.get("a"));
    }
}
