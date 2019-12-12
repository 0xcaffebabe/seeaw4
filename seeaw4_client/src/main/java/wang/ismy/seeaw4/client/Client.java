package wang.ismy.seeaw4.client;

import wang.ismy.seeaw4.client.netty.NettyClientConnection;
import wang.ismy.seeaw4.common.message.impl.TextMessage;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        NettyClientConnection connection = new NettyClientConnection("127.0.0.1",1999);
        connection.connect();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            connection.sendMessage(new TextMessage(scanner.nextLine(),null));
        }
        System.in.read();
    }
}
