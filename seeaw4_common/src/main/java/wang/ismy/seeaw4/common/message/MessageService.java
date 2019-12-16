package wang.ismy.seeaw4.common.message;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.chain.MessageChain;
import wang.ismy.seeaw4.common.utils.BytesUtils;

import java.io.IOException;
import java.util.*;

/**
 * 消息服务,用来处理消息
 * 在这里规定前4个字节代表消息类型
 * 第5-8个字节代表附加消息的偏移量n
 * 第9-n+8个字节代表附加消息
 * n+9-结束代表有效载荷
 *
 * @author my
 */
@Slf4j
public class MessageService {
    private static final MessageService INSTANCE = new MessageService();
    private Gson gson = new Gson();
    private List<MessageChain> messageChainList = new LinkedList<>();

    private MessageService(){}

    public void registerMessageChain(MessageChain... messageChain){
        messageChainList.addAll(Arrays.asList(messageChain));
    }

    /**
     * 将一条消息转化为字节数组
     * @param message 消息
     * @return 字节数组
     */
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
        int retLength = type.length + offset.length + addition.length + payload.length;
        byte[] ret = new byte[retLength];
        // 拷贝消息类型
        System.arraycopy(type, 0, ret, 0, type.length);
        // 拷贝偏移量
        System.arraycopy(offset, 0, ret, type.length, offset.length);
        // 拷贝附加消息
        if (addition.length != 0) {

            System.arraycopy(addition, 0, ret, type.length + offset.length, addition.length);
        }
        // 拷贝载荷
        System.arraycopy(payload, 0, ret, type.length + offset.length + addition.length, payload.length);
        return ret;
    }

    /**
     * 将一个字节数组转化为消息
     * @param bytes 字节数
     * @return 消息
     */
    public Message resolve(byte[] bytes) {
        byte[] type = BytesUtils.subBytes(bytes, 0, 4);
        byte[] offset = BytesUtils.subBytes(bytes, 4, 4);
        int offsetInt = byteArrayToInt(offset);
        byte[] additions = BytesUtils.subBytes(bytes, 8, offsetInt);
        byte[] payload = BytesUtils.subBytes(bytes, offsetInt+8, bytes.length - offsetInt-8);
        MessageType messageType = MessageType.valueOf(byteArrayToInt(type));
        Map map = gson.fromJson(new String(additions), Map.class);
        return messageType.getMessageConverter().convert(payload, map);
    }

    /**
     * 交由系统的消息处理链处理消息
     * @param connection
     * @param message
     */
    public void process(Connection connection,Message message){
        for (MessageChain messageChain : messageChainList) {
            try {
                messageChain.process(connection,message);
            } catch (IOException e) {
                log.info("消息链发生异常,{}",e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static MessageService getInstance(){
        return INSTANCE;
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
