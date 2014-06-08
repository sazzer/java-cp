package uk.co.grahamcox.cpmmo.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Message Encoder that encodes into JSON Strings
 */
public class JsonMessageEncoder extends MessageToByteEncoder<Object> {
    /** The logger to use */
    private static final Logger LOG = LoggerFactory.getLogger(JsonMessageEncoder.class);
    /** The object mapper to use */
    private ObjectMapper objectMapper;

    /**
     * Construct the encoder
     */
    public JsonMessageEncoder() {
        objectMapper = new ObjectMapper();
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
        byte[] bytes = objectMapper.writeValueAsBytes(msg);
        LOG.debug("Encoded message {} as {} bytes", msg, bytes.length);
        out.writeBytes(bytes);
    }
}
