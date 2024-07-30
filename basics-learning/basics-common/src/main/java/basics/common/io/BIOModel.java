package basics.common.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author szf
 * @describe: 模拟 BIO 操作，同步的、阻塞式 IO
 * @Date 2022/6/1 9:59
 */
public class BIOModel {

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socket = serverSocket.accept();
            threadPool.execute(() -> {
                try {
                    handler(socket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }

    /**
     * 处理客户端请求
     */
    private static void handler(Socket socket) throws IOException {
        byte[] bytes = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        socket.close();
        while (true) {
            int read = inputStream.read(bytes);
            if (read != -1) {
                System.out.println("msg from client: " + new String(bytes, 0, read));
            } else {
                break;
            }
        }
    }
}
