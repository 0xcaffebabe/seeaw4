package wang.ismy.seeaw4.common.connection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.UUID;

/**
 * 一个存放连接信息的POJO
 * @author my
 */
@Getter
public class ConnectionInfo {

    private String connectionId;

    /**
     * 连接地址信息
     */
    private final InetSocketAddress socketAddress;

    /**
     * 连接时间
     */
    private final long connectedTime;

    public ConnectionInfo(SocketAddress socketAddress, long connectedTime) {

        if (socketAddress instanceof InetSocketAddress){
            this.socketAddress = (InetSocketAddress) socketAddress;
        }else{
            throw new IllegalArgumentException("socket address 无法解析!");
        }
        this.connectionId = UUID.randomUUID().toString();
        this.connectedTime = connectedTime;

    }

    @Override
    public String toString() {
        return "ConnectionInfo{" +
                "socketAddress=" + socketAddress +
                '}';
    }
}
