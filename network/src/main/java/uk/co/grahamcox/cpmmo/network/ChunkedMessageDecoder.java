package uk.co.grahamcox.cpmmo.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Channel Handler that will decode messages that turn up in chunks, using a leading length to determine chunk size
 */
public class ChunkedMessageDecoder extends ByteToMessageDecoder {
    /** The logger to use */
    private static final Logger LOG = LoggerFactory.getLogger(ChunkedMessageDecoder.class);
    /** The number of bytes we are expecting */
    private Optional<Integer> bytesExpected = Optional.empty();
    /**
     * Decode the bytes currently available.
     * If we still need to know how many bytes to read, we will wait until we have that. Then when that is available we
     * will wait until we have that many bytes, and then that will be the message to pass on.
     * @param channelHandlerContext The channel context
     * @param byteBuf the bytes to decode
     * @param objects the bytes that have been decoded
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> objects) {
        boolean keepGoing = true;
        while (keepGoing) {
            int readableBytes = byteBuf.readableBytes();
            LOG.debug("Bytes available: {}", readableBytes);
            
            if (bytesExpected.isPresent()) {
                Integer bytesToRead = bytesExpected.get();
                LOG.debug("Looking for {} bytes of data", bytesToRead);
                if (readableBytes >= bytesToRead) {
                    byte[] bytes = new byte[bytesToRead];
                    // We have a message we can read
                    byteBuf.readBytes(bytes);
                    objects.add(bytes);
                    bytesExpected = Optional.empty();
                    LOG.debug("Read {} bytes of data", bytesToRead);
                } else {
                    keepGoing = false;
                }
            } else {
                LOG.debug("Looking for 4 bytes for length");
                if (readableBytes > 4) {
                    int len = byteBuf.readInt();
                    bytesExpected = Optional.of(len);
                    LOG.debug("Read length: {}", len);
                } else {
                    keepGoing = false;
                }
            }
        }
    }
}
