package wang.ismy.seeaw4.client;

import wang.ismy.seeaw4.client.netty.NettyClientConnection;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {
        NettyClientConnection connection = new NettyClientConnection("127.0.0.1",1999);
        connection.connect();
        System.in.read();
    }
}
