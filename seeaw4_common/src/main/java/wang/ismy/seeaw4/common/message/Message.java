package wang.ismy.seeaw4.common.message;

import java.io.Serializable;

/**
 * 所有的消息都需要实现这个接口，以实现消息传输
 * @author my
 */
public interface Message extends Serializable{

	/**
	 * 获取本条消息的有效载荷
	 * @return 字节数组
	 */
	byte[] getPayload();


}
