package wang.ismy.seeaw4.common.message;

import java.io.Serializable;
import java.util.Map;

/**
 * 所有的消息都需要实现这个接口，以实现消息传输1
 * @author my
 */
public interface Message extends Serializable{

	/**
	 * 获取本条消息的有效载荷
	 * @return 字节数组
	 */
	byte[] getPayload();

	/**
	 * 获取本条消息的消息类型
	 * @return 消息类型枚举
	 */
	MessageType messageType();

	/**
	 * 该条消息附加内容
	 * @return
	 */
	Map<String,Object> addition();
}
