package uk.co.grahamcox.cpmmo.network;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * Channel Initializer that gets the handlers from Spring
 */
public class SpringChannelInitializer extends ChannelInitializer<SocketChannel> implements ApplicationContextAware {

    /** The logger to use */
    private static final Logger LOG = LoggerFactory.getLogger(SpringChannelInitializer.class);

    /** The application context to use */
    private ApplicationContext applicationContext;

    /** The Spring Bean names for the handlers to use */
    private List<String> handlerBeanNames;

    /**
     * Set the application context to use
     * @param applicationContext the application context
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Set the handler bean names to use
     * @param handlerBeanNames the handler bean names
     */
    public void setHandlerBeanNames(List<String> handlerBeanNames) {
        this.handlerBeanNames = handlerBeanNames;
    }

    /**
     * Initialize the channel for a new connection
     * @param socketChannel the channel
     * @throws Exception if anything goes wrong
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        LOG.info("New connection: {}", socketChannel);
        for (String beanName : handlerBeanNames) {
            ChannelHandler handler = applicationContext.getBean(beanName, ChannelHandler.class);
            socketChannel.pipeline().addLast(handler);
        }
    }
}
