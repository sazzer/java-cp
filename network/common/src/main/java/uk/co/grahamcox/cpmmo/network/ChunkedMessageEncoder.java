package uk.co.grahamcox.cpmmo.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Channel Handler that will encode messages into chunks, using a leading length to determine chunk size
 */
public class ChunkedMessageEncoder extends MessageToByteEncoder<ByteBuf> {
    /**
     * Encode the given byte array by sending a 4-byte length then the actual bytes
     * @param ctx the context
     * @param msg the bytes to send
     * @param out the buffer to write to
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        if (msg.readableBytes() > 0) {
            out.writeInt(msg.readableBytes());
            out.writeBytes(msg);
        }
    }
}
