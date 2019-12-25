package wang.ismy.seeaw4.server.service;

import io.netty.channel.Channel;
import wang.ismy.seeaw4.common.connection.Connection;


import java.util.LinkedList;
import java.util.List;

/**
 * 认证服务，负责维护连接的认证状态
 * @author my
 */
public class AuthService {
    private static final AuthService INSTANCE = new AuthService();
    private List<Channel> authConnectionList = new LinkedList<>();

    private AuthService() { }

    public boolean contains(Channel channel){
        return authConnectionList.contains(channel);
    }

    public void add(Channel channel){
        authConnectionList.add(channel);
    }

    public static AuthService getInstance(){
        return INSTANCE;
    }

}
