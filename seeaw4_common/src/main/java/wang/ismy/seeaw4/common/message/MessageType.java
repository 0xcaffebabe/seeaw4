package wang.ismy.seeaw4.common.message;

/**
 * 消息类型
 * @author my
 */

public enum MessageType {
    TEXT(0),IMG(1);
    private int code;

    MessageType(int code){this.code = code;}

    public int getCode() {
        return code;
    }
}
