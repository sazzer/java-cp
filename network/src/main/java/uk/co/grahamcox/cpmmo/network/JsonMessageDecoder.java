package uk.co.grahamcox.cpmmo.network;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Message Decoder that decodes Bytes as a JSON Message stream
 */
public class JsonMessageDecoder extends MessageToMessageDecoder<byte[]> {
    /** The logger to use */
    private static final Logger LOG = LoggerFactory.getLogger(JsonMessageEncoder.class);
    /** The object mapper to use */
    private ObjectMapper objectMapper;

    /**
     * Construct the decoder
     */
    public JsonMessageDecoder() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Decode the provided bytes
     * @param ctx the context
     * @param msg the bytes
     * @param out the output objects
     * @throws IOException if the bytes can't be processed
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws IOException {
        String value = objectMapper.readValue(msg, String.class);
        LOG.debug("Decoded {} bytes as message: {}", msg.length, value);
        out.add(value);
    }
}
