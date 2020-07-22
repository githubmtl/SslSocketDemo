package sslSocket;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;

/**
 * @ClassName:
 * @Description:        SSLServerSocket服务端（双向认证）
 * @author:
 * @date: 2020年07月20日 10:33
 * @Copyright:
 */
public class SSLServer {
    public static void main(String[] args) throws Exception {

        //自己的证书，公钥和私钥
        KeyStore keyStore=KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream("E:\\自制证书\\testks.jks"), "1234567".toCharArray());
        KeyManagerFactory keyManagerFactory=KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, "1234567".toCharArray());
        SSLContext sslContext=SSLContext.getInstance("SSL");

        //添加信任客户端公钥
        KeyStore clientKs=KeyStore.getInstance("JKS");
        clientKs.load(new FileInputStream("E:\\自制证书\\mtlt.jks"),"654321".toCharArray());
        TrustManagerFactory tsm=TrustManagerFactory.getInstance("SunX509");
        tsm.init(clientKs);

        sslContext.init(keyManagerFactory.getKeyManagers(),tsm.getTrustManagers(),null);
        SSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();
        SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(10086);
        serverSocket.setNeedClientAuth(true);
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
