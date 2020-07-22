package sslSocket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @ClassName:      普通Socket客户端
 * @Description:
 * @author:
 * @date: 2020年07月21日 11:28
 * @Copyright:
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket=new Socket("localhost", 10086);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("i'm client!".getBytes());
        outputStream.flush();
        byte[] buf=new byte[512];
        int read=0;
        StringBuilder msg=new StringBuilder();
        read=inputStream.read(buf);
        msg.append(new String(buf,0,read));
        System.out.println("客户端收到消息："+msg);
        socket.close();
    }
}
