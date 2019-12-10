package wang.ismy.seeaw4.server;

import wang.ismy.seeaw4.common.Connector;

/**
 * @author my
 */
public class Server {
	
	private Connector connector  = new NettyConnector();

	public static void main(String[] args) {
		Server server = new Server();

	}
}
