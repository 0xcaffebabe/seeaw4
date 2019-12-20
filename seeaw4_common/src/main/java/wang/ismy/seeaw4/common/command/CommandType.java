package wang.ismy.seeaw4.common.command;

/**
 * 命令类型，代表一个实体要求另一个实体所要执行的操作
 * @author MY
 * @date 2019/12/17 9:39
 */
public enum CommandType {

    /**
     * 服务端接收到这个消息，需要把本地所有客户端回传
     * 客户端接收到该消息，代表是服务端客户端列表发生了变化
     */
    LIST_CLIENT(),

    /**
     * 客户端接到该消息，就代表需要回传屏幕截图
     * 服务端接收到该消息，转发到客户端a所指定的客户端b,并等待客户端b的消息,当b的消息到达,转发给a
     */
    SCREEN(),


    /**
     * 接受到该消息，就需要回传摄像头图片
     *服务端同上
     */
    PHOTO(),


    /**
     * 接受到该消息，就代表需要把terminal中的SHELL buffer回传
     * 服务端同上
     */
    SHELL_BUFFER(),

    /**
     * 客户端接收到该消息，提取其中存在的per-id,当terminal中shell输出消息时，发送给per-id所指定的客户端
     * 服务端同上
     */
    SHELL_BIND(),

    /**
     * 客户端接收到该消息，就代表是其绑定的shell输出的消息
     * 服务端同上
     */
    SHELL_RECEIVE(),

    /**
     * 客户端接收到该消息，需要向终端输入命令
     */
    SHELL_CMD()
    ;
}
