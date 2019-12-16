package wang.ismy.seeaw4.client;

import wang.ismy.seeaw4.client.netty.NettyClientConnection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageService;
import wang.ismy.seeaw4.common.message.chain.impl.PrintMessageChain;
import wang.ismy.seeaw4.common.message.chain.impl.PromiseMessageChain;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.message.impl.TextMessage;
import wang.ismy.seeaw4.common.promise.ConnectionPromise;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        MessageService.getInstance().registerMessageChain(new PrintMessageChain()
                , PromiseMessageChain.getInstance());

        NettyClientConnection connection = new NettyClientConnection("127.0.0.1", 1999);
        connection.connect();
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);
        CommandMessage message = new CommandMessage();
        message.setCommand("list-client");
        ConnectionPromise promise = new ConnectionPromise(message)
                .success((conn,msg)->{
                    System.out.println(conn+"回复了"+msg);
                })
                .async();

        connection.sendMessage(message);
        System.in.read();
    }
}
