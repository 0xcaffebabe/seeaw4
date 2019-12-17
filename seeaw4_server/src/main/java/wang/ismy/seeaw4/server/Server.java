package wang.ismy.seeaw4.server;

import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.common.Connector;
import wang.ismy.seeaw4.common.command.CommandType;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.connection.ConnectionListener;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.message.impl.ImgMessage;
import wang.ismy.seeaw4.common.message.impl.TextMessage;
import wang.ismy.seeaw4.common.promise.ConnectionPromise;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author my
 */
@Slf4j
public class Server {
	
	private Connector connector  = new NettyConnector();

	public static void main(String[] args) {
		Server server = new Server();
		server.connector.bindConnectionListener(new ConnectionListener() {
			@Override
			public void establish(Connection connection) {
				// 向客户发起一个截屏请求
				log.info("截屏请求");
				CommandMessage cmd  = new CommandMessage();
				cmd.setCommand(CommandType.SCREEN);
				new ConnectionPromise(cmd)
						.success((conn,msg)->{
							log.info("接收到客户端截屏回复:{},{}",conn,msg);
							if (msg instanceof ImgMessage){
								try(FileOutputStream fos = new FileOutputStream("d:/screen."+((ImgMessage) msg).getFormat());) {

									fos.write(msg.getPayload());
								} catch (IOException e) {
									e.printStackTrace();
								}

							}
						})
						.async();
				try {
					connection.sendMessage(cmd);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void close(Connection connection) {

			}
		});

	}
}
