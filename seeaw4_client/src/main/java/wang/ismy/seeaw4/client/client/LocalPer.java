package wang.ismy.seeaw4.client.client;

import lombok.Data;
import wang.ismy.seeaw4.client.terminal.TerminalProxy;
import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.common.client.Per;
import wang.ismy.seeaw4.common.connection.Connection;

import java.io.IOException;

/**
 * 本地包装的客户端
 * @author MY
 * @date 2019/12/21 16:24
 */
@Data
public class LocalPer {
    private String id;
    private Per per;
    private TerminalProxy terminalProxy;

    public LocalPer(Per per, String selfId, Connection connection) throws IOException {
        this.id = per.getId();
        this.per = per;
        if (!per.isSelf()){
            this.terminalProxy =  new TerminalProxy(connection, per.getId(), selfId);
        }
    }

    public boolean isSelf(){
        return per.isSelf();
    }
}
