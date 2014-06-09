package uk.co.grahamcox.cpmmo.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Message Encoder that encodes into JSON Strings
 */
public class JsonMessageEncoder extends MessageToByteEncoder<Object> {
    /** The logger to use */
    private static final Logger LOG = LoggerFactory.getLogger(JsonMessageEncoder.class);
    /** The UTF-8 Charset to encode as */
    public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    /** The object mapper to use */
    private ObjectMapper objectMapper;
    /** The base package to use for messages */
    private String messagesPackageBase;

    /**
     * Construct the encoder
     */
    public JsonMessageEncoder() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Set the package base to use for messages
     * @param messagesPackageBase the package base
     */
    public void setMessagesPackageBase(String messagesPackageBase) {
        this.messagesPackageBase = messagesPackageBase;
    }

    /**
     * Encode the given message
     * @param ctx the context
     * @param msg the message
     * @param out the byte buffer to write to
     * @throws JsonProcessingException if the message fails to encode
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws JsonProcessingException {
        String className;

        Class<?> msgClass = msg.getClass();
        if (msgClass.getPackage().getName().equals(messagesPackageBase)) {
            className = msgClass.getSimpleName();
        } else {
            className = msgClass.getName();
        }
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(msg);
        } catch (Exception e) {
            LOG.error("Error writing JSON string", e);
            throw e;
        }

        String result = className + " " + jsonString;
        byte[] bytes = result.getBytes(UTF8_CHARSET);
        LOG.debug("Encoded message {} of type {} as {} bytes", msg, className, bytes.length);
        out.writeBytes(bytes);
    }
}
