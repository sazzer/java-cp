package uk.co.grahamcox.cpmmo.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the Chunked Message Decoder
 */
public class ChunkedMessageDecoderTest {
    /**
     * Decode less bytes than needed for the length
     */
    @Test
    public void decodeLessThanLength() {
        ChunkedMessageDecoder decoder = new ChunkedMessageDecoder();
        List<Object> decoded = decode(decoder, (byte)0, (byte)0, (byte)0);
        Assert.assertEquals(0, decoded.size());
    }
    /**
     * Decode enough bytes needed for the length but not the message
     */
    @Test
    public void decodeMoreThanLengthLessThanMessage() {
        ChunkedMessageDecoder decoder = new ChunkedMessageDecoder();
        List<Object> decoded = new ArrayList<>();
        decoded.addAll(decode(decoder, (byte)0, (byte)0, (byte)0, (byte)3));
        Assert.assertEquals(0, decoded.size());

        decoded.addAll(decode(decoder, (byte)1, (byte)2));
        Assert.assertEquals(0, decoded.size());
    }
    /**
     * Decode enough bytes needed for the length and the message
     */
    @Test
    public void decodeMessage() {
        ChunkedMessageDecoder decoder = new ChunkedMessageDecoder();
        List<Object> decoded = new ArrayList<>();
        decoded.addAll(decode(decoder, (byte)0, (byte)0, (byte)0, (byte)2));
        Assert.assertEquals(0, decoded.size());

        decoded.addAll(decode(decoder, (byte)1, (byte)2));
        Assert.assertEquals(1, decoded.size());
        Assert.assertArrayEquals(new byte[]{1, 2}, (byte[])decoded.get(0));
    }
    /**
     * Decode enough bytes needed for the length and the message, but of length 0
     */
    @Test
    public void decodeMessageLength0() {
        ChunkedMessageDecoder decoder = new ChunkedMessageDecoder();
        List<Object> decoded = new ArrayList<>();
        decoded.addAll(decode(decoder, (byte)0, (byte)0, (byte)0, (byte)0));
        Assert.assertEquals(0, decoded.size());

        decoded.addAll(decode(decoder, (byte)1, (byte)2));
        Assert.assertEquals(0, decoded.size());
    }

    /**
     * Decode the given bytes
     *
     * @param decoder
     * @param bytes the bytes
     * @return the decoded objects
     */
    private List<Object> decode(ChunkedMessageDecoder decoder, byte... bytes) {
        ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeBytes(bytes);

        List<Object> decoded = new ArrayList<>();
        decoder.decode(null, byteBuf, decoded);

        return decoded;

    }
}