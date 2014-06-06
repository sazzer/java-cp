package uk.co.grahamcox.cpmmo.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual network server
 */
public class Server {
    /** The logger to use */
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    /** The port to listen on */
    private int port;
    /** The channel initializer to use */
    private ChannelInitializer<SocketChannel> channelInitializer;

    /** The Event Group to act as the boss */
    private final EventLoopGroup bossGroup;
    /** The Event Group to use for workers */
    private final EventLoopGroup workerGroup;
    /** The server bootstrap */
    private final ServerBootstrap serverBootstrap;

    /** The channel future */
    private ChannelFuture channelFuture;
    /**
     * Construct the server
     */
    public Server() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 128)
            .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    /**
     * Set the port to listen on
     * @param port the port to listen on
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Set the channel initializer to use
     * @param channelInitializer the channel initializer to use
     */
    public void setChannelInitializer(ChannelInitializer<SocketChannel> channelInitializer) {
        this.channelInitializer = channelInitializer;
    }

    /**
     * Start the server
     */
    public void start() {
        LOG.info("Starting the server on port {}", port);
        try {
            channelFuture = serverBootstrap.childHandler(channelInitializer)
                .bind(port)
                .sync();
        } catch (InterruptedException e) {
            LOG.error("The server was interrupted", e);
            stop();
            throw new RuntimeException(e);
        }
    }

    /**
     * Stop the server
     */
    public void stop() {
        LOG.info("Stopping the server");
        try {
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOG.error("The server was interrupted", e);
            throw new RuntimeException(e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        LOG.info("Stopped the server");
    }
}
