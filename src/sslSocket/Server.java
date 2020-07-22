package sslSocket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName:      普通Socket服务端（用于和SSLSocket抓包比较）
 * @Description:
 * @author:
 * @date: 2020年07月21日 11:25
 * @Copyright:
 */
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket=new ServerSocket(10086);
        while (true){
            System.out.println("等待连接...");
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            byte[] buf=new byte[512];
            int read=0;
            StringBuilder msg=new StringBuilder();
            read=inputStream.read(buf);
            msg.append(new String(buf,0,read));
            System.out.println("服务器接收到消息："+msg.toString());
            outputStream.write("i'am server!".getBytes());
            outputStream.flush();
            socket.close();
        }
    }
}
