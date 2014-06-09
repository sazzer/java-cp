package uk.co.grahamcox.cpmmo.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.nio.charset.Charset;
import java.text.ParseException;
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
    /** The UTF-8 Charset to encode as */
    public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    /** The object mapper to use */
    private ObjectMapper objectMapper;
    /** The base package to use for messages */
    private String messagesPackageBase;
    /**
     * Construct the decoder
     */
    public JsonMessageDecoder() {
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
     * Decode the provided bytes
     * @param ctx the context
     * @param msg the bytes
     * @param out the output objects
     * @throws IOException if the bytes can't be processed
     * @throws ClassNotFoundException if the specified class can't be found
     * @throws ParseException if the provided bytes aren't in the correct format
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, byte[] msg, List<Object> out)
        throws IOException, ClassNotFoundException, ParseException {
        String jsonString = new String(msg, UTF8_CHARSET);
        String[] parts = jsonString.split(" ", 2);
        if (parts.length == 2) {
            String className = parts[0];
            if (!className.contains(".") && messagesPackageBase != null) {
                className = messagesPackageBase + "." + className;
            }

            Class cls = Class.forName(className);
            Object value = objectMapper.readValue(parts[1], cls);
            LOG.debug("Decoded {} bytes of type {} as message: {}", msg.length, cls, value);
            out.add(value);
        } else {
            throw new ParseException("Provided string is not in the correct format '<classname> <json>'", -1);
        }
    }
}
