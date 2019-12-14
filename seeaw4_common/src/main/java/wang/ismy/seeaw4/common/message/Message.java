package wang.ismy.seeaw4.common.message;

import wang.ismy.seeaw4.common.message.converter.MessageConverter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有的消息都继承这个抽象类，以实现消息传输
 * @author my
 */
public abstract class Message{

	protected byte[] payload;
	protected Map<String,Object> addition;

	public Message(byte[] payload,Map<String,Object> addition){
		this.payload = payload;
		this.addition = addition;
	}

	public Message(){
		payload = new byte[0];
		addition = new HashMap<>();
	}

	/**
	 * 获取本条消息的有效载荷
	 * @return 字节数组
	 */
	public byte[] getPayload(){
		return payload;
	}

	/**
	 * 获取本条消息的消息类型
	 * @return 消息类型枚举
	 */
	public abstract MessageType messageType();

	/**
	 * 该条消息附加内容
	 * @return
	 */
	public Map<String,Object> addition(){
		return addition;
	}

	@Override
	public String toString() {
		return "Message{" +
				"payload=" + Arrays.toString(payload) +
				", addition=" + addition +
				'}';
	}
}
