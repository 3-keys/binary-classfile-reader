package keys3.classfile.binary.utils;

import java.nio.ByteBuffer;

public class ByteUtils {

    public static short MAKE_SHORT(byte[] b) {
        return MAKE_SHORT(b, 0);
    }

    public static short MAKE_SHORT(byte[] b, int n) {
        return (short) ((b[n] & 0xff) << 8 | (b[n + 1] & 0xff));
    }

    public static int MAKE_INT(byte[] b) {
        return MAKE_INT(b, 0);
    }

    public static int MAKE_INT(byte[] b, int n) {
        return (MAKE_SHORT(b, n) & 0xffff) << 16 | (MAKE_SHORT(b, n + 2) & 0xffff);
    }

    public static long MAKE_LONG(byte[] b) {
        return MAKE_LONG(b, 0);
    }

    public static long MAKE_LONG(byte[] b, int n) {
        return (MAKE_INT(b, n) & 0xffffffffL) << 32 | (MAKE_INT(b, n + 4)) & 0xffffffffL;
    }

    public static byte[] SHORT_TO_BYTES(short val) {
        ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
        buffer.putShort(val);
        return buffer.array();
    }

    public static byte[] INT_TO_BYTES(int val) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(val);
        return buffer.array();
    }

    public static byte[] LONG_TO_BYTES(long val) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(val);
        return buffer.array();
    }

}
