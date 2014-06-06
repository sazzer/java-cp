package uk.co.grahamcox.cpmmo.network;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Channel Initializer for the Server
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * Initialize the channel for a new connection
     * @param socketChannel the channel
     * @throws Exception if anything goes wrong
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

    }
}
