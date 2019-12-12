package wang.ismy.seeaw4.common.connection;

import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;


public class ConnectionInfoTest {

    @Test
    public void testConnectionInfo() throws UnknownHostException {
        long time = System.currentTimeMillis();
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",1999);
        ConnectionInfo info = new ConnectionInfo(new InetSocketAddress("127.0.0.1",1999),time);
        assertEquals(time,info.getConnectedTime());
        assertEquals(socketAddress,info.getSocketAddress());
    }
}