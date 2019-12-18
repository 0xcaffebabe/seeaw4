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
    LIST_CLIENT(),

    /**
     * 接到该消息，就代表需要回传屏幕截图
     */
    SCREEN(),


    /**
     * 接受到该消息，就需要回传摄像头图片
     */
    PHOTO(),


    /**
     * 接受到该消息，就代表需要把terminal中的SHELL buffer回传
     */
    SHELL_BUFFER()
    ;
}
