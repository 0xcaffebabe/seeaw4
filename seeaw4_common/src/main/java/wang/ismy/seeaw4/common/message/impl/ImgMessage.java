package wang.ismy.seeaw4.common.message.impl;

import lombok.ToString;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageType;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;


/**
 * @author MY
 * @date 2019/12/17 16:52
 */

public class ImgMessage extends Message {

    public ImgMessage(byte[] payload, Map<String, Object> addition) {
        super(payload, addition);
    }

    public ImgMessage(byte[] imgSrc, String format){
        super();
        this.payload = imgSrc;
        addition.put("format",format);
    }

    public String getFormat() {
        return addition.get("format").toString();
    }


    @Override
    public MessageType messageType() {
        return MessageType.IMG;
    }

    @Override
    public String toString() {
        return "ImgMessage{" +
                "format='" + addition.get("format") + '\'' +
                ", payload=" + Arrays.toString(payload) +
                ", addition=" + addition +
                '}';
    }
}
