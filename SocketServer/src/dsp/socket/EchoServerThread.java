package dsp.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 一个简单的socket服务器，能接受客户端请求，并将请求返回给客户端
 * 每接受一个客户，都新建对应的EchoThread作为反应。
 */
public class EchoServerThread {
    // 服务端侦听的Socket
    ServerSocket serverSkt = null;

    // 构造方法
    public EchoServerThread(int port) {
        System.out.println("服务器代理正在监听，端口：" + port);
        try {
            // 创建监听socket
            serverSkt = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(EchoServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                // 接收连接请求
                Socket clientSocket = serverSkt.accept();
                new EchoThread(clientSocket).start();
            } catch (IOException e) {
                System.out.println("无法接受当前客户连接请求！");
                break;
            }
        }
    }

}
