package wang.ismy.seeaw4.common.client;

import lombok.Data;
import lombok.ToString;

/**
 * 代表客户
 * @author MY
 * @date 2019/12/17 9:49
 */
@Data
@ToString
public class Per {

    private String ip;
    private int port;
    private long connectTime;
}
