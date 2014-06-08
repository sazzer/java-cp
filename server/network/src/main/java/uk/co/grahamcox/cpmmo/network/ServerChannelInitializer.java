package uk.co.grahamcox.cpmmo.network;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Channel Initializer for the Server
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> implements ApplicationContextAware {

    /** The logger to use */
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    /** The application context to use */
    private ApplicationContext applicationContext;

    /**
     * Set the application context to use
     * @param applicationContext the application context
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Initialize the channel for a new connection
     * @param socketChannel the channel
     * @throws Exception if anything goes wrong
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        LOG.info("New connection: {}", socketChannel);
        ServerHandler serverChannelHandler = applicationContext.getBean("serverChannelHandler", ServerHandler.class);
        socketChannel.pipeline().addLast(new ChunkedMessageDecoder());
        socketChannel.pipeline().addLast(new JsonMessageDecoder());

        socketChannel.pipeline().addLast(new JsonMessageEncoder());
        socketChannel.pipeline().addLast(new ChunkedMessageEncoder());

        socketChannel.pipeline().addLast(serverChannelHandler);
    }
}
