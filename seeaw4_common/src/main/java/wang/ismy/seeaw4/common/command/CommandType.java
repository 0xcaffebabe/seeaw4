package wang.ismy.seeaw4.common.command;

/**
 * 命令类型，代表一个实体要求另一个实体所要执行的操作
 * @author MY
 * @date 2019/12/17 9:39
 */
public enum CommandType {

    /**
     * 接收到这个消息，需要把本地所有客户端回传
     */
    LIST_CLIENT()
    ;

}
