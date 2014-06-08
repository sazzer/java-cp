package uk.co.grahamcox.cpmmo.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Server Handler class to use
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    /** The logger to use */
    private static final Logger LOG = LoggerFactory.getLogger(ServerHandler.class);

    /**
     * Handle a message
     * @param ctx the context
     * @param msg the message
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOG.debug("Received message: {}", msg);
        ctx.write("Hello");
        ctx.flush();
    }
}
