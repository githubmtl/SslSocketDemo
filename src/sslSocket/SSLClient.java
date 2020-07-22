package sslSocket;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Arrays;

/**
 * @ClassName:          SSLSocket客户端（双向认证）
 * @Description:
 * @author:
 * @date: 2020年07月21日 11:11
 * @Copyright:
 */
public class SSLClient {
    public static void main(String[] args) throws Exception {

        //自己的证书（公钥和私钥）
        KeyStore keyStore=KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream("E:\\自制证书\\mtlks.jks"),"123456".toCharArray());
        KeyManagerFactory kmf=KeyManagerFactory.getInstance("SunX509");
        kmf.init(keyStore, "123456".toCharArray());

        //信任服务器公钥证书
        KeyStore keyStore2=KeyStore.getInstance("JKS");
        keyStore2.load(new FileInputStream("E:\\自制证书\\testtks.jks"),"1234567".toCharArray());
        TrustManagerFactory trustManagerFactory2=TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory2.init(keyStore2);



        SSLContext sslContext=SSLContext.getInstance("SSL");
        sslContext.init(kmf.getKeyManagers(),trustManagerFactory2.getTrustManagers(),null);
        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 10086);
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
