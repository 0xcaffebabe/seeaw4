package wang.ismy.seeaw4.server.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import wang.ismy.seeaw4.common.Message;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.connection.ConnectionInfo;
import wang.ismy.seeaw4.common.listener.OnMessageArrivalListener;

import java.io.IOException;
import java.util.Objects;

/**
 * netty通道连接
 * @author my
 */
public class NettyConnection implements Connection {

    private final Channel channel;
    private final ConnectionInfo connectionInfo;

    public NettyConnection(Channel channel) {
        this.channel = channel;
        connectionInfo =
                new ConnectionInfo(channel.remoteAddress(), System.currentTimeMillis());
    }

    @Override
    public void close() throws IOException { channel.close(); }

    @Override
    public ConnectionInfo getInfo() {
        return connectionInfo;
    }

    @Override
    public void sendMessage(Message message) throws IOException {
        channel.writeAndFlush(Unpooled.wrappedBuffer(message.getPayload()));
    }

    @Override
    public void bindMessageArrivalListener(OnMessageArrivalListener listener) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NettyConnection)) {
            return false;
        }
        NettyConnection that = (NettyConnection) o;
        return channel.equals(that.channel) &&
                connectionInfo.equals(that.connectionInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channel, connectionInfo);
    }
}
