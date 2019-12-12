package wang.ismy.seeaw4.client;

import wang.ismy.seeaw4.client.netty.NettyClientConnection;
import wang.ismy.seeaw4.common.message.MessageService;
import wang.ismy.seeaw4.common.message.chain.impl.PrintMessageChain;
import wang.ismy.seeaw4.common.message.impl.TextMessage;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        MessageService.getInstance().registerMessageChain(new PrintMessageChain());
        NettyClientConnection connection = new NettyClientConnection("127.0.0.1",1999);
        connection.connect();
        NettyClientConnection connection2 = new NettyClientConnection("127.0.0.1",1999);
        connection2.connect();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            connection.sendMessage(new TextMessage(scanner.nextLine(),null));
            connection.close();
        }
        System.in.read();
    }
}
