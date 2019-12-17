package wang.ismy.seeaw4.common.client;

import lombok.Data;

/**
 * 代表客户
 * @author MY
 * @date 2019/12/17 9:49
 */
@Data
public class Client {

    private String ip;
    private int port;
    private long connectTime;
}
