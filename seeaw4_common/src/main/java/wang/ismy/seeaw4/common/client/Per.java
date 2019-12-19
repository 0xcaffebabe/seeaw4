package wang.ismy.seeaw4.common.client;

import lombok.Data;
import lombok.ToString;
import wang.ismy.seeaw4.common.connection.Connection;

/**
 * 代表客户
 * @author MY
 * @date 2019/12/17 9:49
 */
@Data
@ToString
public class Per {

    private String id;
    private String ip;
    private int port;
    private long connectTime;
    /**
     * 该客户是否是自己
     */
    private boolean self=false;

    public static Per convert(Connection conn){
        Per per = new Per();
        per.id = conn.getInfo().getConnectionId();
        per.setIp(conn.getInfo().getSocketAddress().getAddress().getHostAddress());
        per.setPort(conn.getInfo().getSocketAddress().getPort());
        per.setConnectTime(conn.getInfo().getConnectedTime());
        return per;
    }
}
