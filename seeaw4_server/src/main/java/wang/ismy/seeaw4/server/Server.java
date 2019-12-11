package wang.ismy.seeaw4.server;

import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.common.Connector;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.connection.ConnectionListener;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.impl.TextMessage;

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
				Message msg = new TextMessage("你好，客户端", Map.of("name","utf-8"));
				try {
					connection.sendMessage(msg);
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
