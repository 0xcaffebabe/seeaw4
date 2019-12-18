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
	private ServerService serverService;

	public Connector getConnector(){
		return connector;
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.serverService = new ServerService(server);
		server.connector.bindConnectionListener(new ConnectionListener() {
			@Override
			public void establish(Connection connection) {

			}

			@Override
			public void close(Connection connection) {

			}
		});

	}
}
