package uk.co.grahamcox.cpmmo.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the Chunked Message Encoder
 */
public class ChunkedMessageEncoderTest {
    /**
     * Test encoding a byte stream
     */
    @Test
    public void test() {
        ByteBuf bytes = UnpooledByteBufAllocator.DEFAULT.buffer();
        bytes.writeBytes(new byte[]{1, 2, 3, 4, 5, 6, 7});
        ChunkedMessageEncoder encoder = new ChunkedMessageEncoder();

        ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer();
        encoder.encode(null, bytes, byteBuf);

        Assert.assertEquals(11, byteBuf.readableBytes());

        byte[] expected = new byte[]{0, 0, 0, 7, 1, 2, 3, 4, 5, 6, 7};
        byte[] written = new byte[11];
        byteBuf.readBytes(written);
        Assert.assertArrayEquals(expected, written);

    }
    /**
     * Test encoding a byte stream
     */
    @Test
    public void testLength0() {
        ByteBuf bytes = UnpooledByteBufAllocator.DEFAULT.buffer();
        bytes.writeBytes(new byte[]{});
        ChunkedMessageEncoder encoder = new ChunkedMessageEncoder();

        ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer();
        encoder.encode(null, bytes, byteBuf);

        Assert.assertEquals(0, byteBuf.readableBytes());
    }

}