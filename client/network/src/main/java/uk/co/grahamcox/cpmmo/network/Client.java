/****************************************************************************************************************
 *
 *  Copyright (c) 2014 OCLC, Inc. All Rights Reserved.
 *
 *  OCLC proprietary information: the enclosed materials contain
 *  proprietary information of OCLC, Inc. and shall not be disclosed in whole or in
 *  any part to any third party or used by any person for any purpose, without written
 *  consent of OCLC, Inc.  Duplication of any portion of these materials shall include this notice.
 *
 ******************************************************************************************************************/
package uk.co.grahamcox.cpmmo.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.ConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual network client
 */
public class Client {
    /** The logger to use */
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    /** The Event Group to use for workers */
    private final EventLoopGroup workerGroup;
    /** The server bootstrap */
    private final Bootstrap clientBootstrap;

    /** The channel future */
    private ChannelFuture channelFuture;
    /** The channel initializer to use */
    private ChannelInitializer<SocketChannel> channelInitializer;

    /**
     * Construct the server
     */
    public Client() {
        workerGroup = new NioEventLoopGroup();

        clientBootstrap = new Bootstrap();
        clientBootstrap.group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.SO_KEEPALIVE, true);
    }

    /**
     * Set the channel initializer to use
     * @param channelInitializer the channel initializer to use
     */
    public void setChannelInitializer(ChannelInitializer<SocketChannel> channelInitializer) {
        this.channelInitializer = channelInitializer;
    }

    /**
     * Connect to the server
     * @param host the host
     * @param port the port
     * @throws ConnectException if we failed to connect
     */
    public void connect(String host, int port) throws ConnectException {
        try {
            LOG.info("Connecting to server on {}:{}", host, port);
            clientBootstrap.handler(channelInitializer);
            channelFuture = clientBootstrap.connect(host, port).sync();
            if (!channelFuture.isSuccess()) {
                LOG.info("Failed to connect to server on {}:{}", host, port);
                throw new ConnectException();
            }
            LOG.info("Connected to server on {}:{}", host, port);
        } catch (InterruptedException e) {
            LOG.error("The client was interrupted", e);
            stop();
            throw new RuntimeException(e);
        }
    }

    /**
     * Stop the client
     */
    public void stop() {
        LOG.info("Stopping the client");
        workerGroup.shutdownGracefully();
        if (channelFuture != null) {
            try {
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                LOG.error("The client was interrupted", e);
                throw new RuntimeException(e);
            }
        }
    }
}
