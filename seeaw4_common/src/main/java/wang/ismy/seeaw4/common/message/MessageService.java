package wang.ismy.seeaw4.common.message;

import com.google.gson.Gson;

import java.util.Arrays;

/**
 * 消息服务,用来处理消息
 * 在这里规定前4个字节代表消息类型
 * 第5-8个字节代表附加消息的偏移量n
 * 第9-n+8个字节代表附加消息
 * n+9-结束代表有效载荷
 *
 * @author my
 */
public class MessageService {
    private Gson gson = new Gson();

    public byte[] build(Message message) {
        byte[] type = intToByte4(message.messageType().getCode());
        byte[] offset;
        byte[] addition = new byte[0];
        if (message.addition() == null) {
            offset = intToByte4(0);
        } else {
            String json = gson.toJson(message.addition());
            addition = json.getBytes();
            offset = intToByte4(addition.length);
        }
        byte[] payload = message.getPayload();
        byte[] ret = new byte[type.length + offset.length + addition.length + payload.length];
        // 拷贝消息类型
        System.arraycopy(type, 0, ret, 0, type.length);
        // 拷贝偏移量
        System.arraycopy(offset, 0, ret, type.length + 1, offset.length);
        // 拷贝附加消息
        System.arraycopy(addition, 0, ret, type.length + offset.length + 1, addition.length);
        // 拷贝载荷
        System.arraycopy(payload, 0, ret, type.length +offset.length + addition.length + 1, payload.length);
        return ret;
    }

    public Message resolve(byte[] bytes) {
        return null;
    }

    private static byte[] intToByte4(int i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF);
        targets[2] = (byte) (i >> 8 & 0xFF);
        targets[1] = (byte) (i >> 16 & 0xFF);
        targets[0] = (byte) (i >> 24 & 0xFF);
        return targets;
    }

    private static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

}
