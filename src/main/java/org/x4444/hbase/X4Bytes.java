package org.x4444.hbase;

public class X4Bytes {
  /**
   * Converts long to six bytes. Can be used to convert timestamp to byte[].
   * 
   * @param val long - min 2^47, max 2^47 - 1
   * @return
   */
  public static byte[] toSixBytes(long val) {
    if (val > 140737488355327L) {
      throw new IllegalArgumentException(
          "max allowed: 140737488355327, but provided: " + val);
    }
    if (val < -140737488355328L) {
      throw new IllegalArgumentException(
          "min allowed: -140737488355328, but provided: " + val);
    }
    byte[] b = new byte[6];
    for (int i = 5; i > 0; i--) {
      b[i] = (byte) val;
      val >>>= 8;
    }
    b[0] = (byte) val;
    return b;
  }

  /**
   * Takes six bytes from byte[] and convert them to long.
   * 
   * @param bytes
   * @param offset
   * @return long - min: 2^47, max: 2^47 - 1
   */
  public static long sixBytesToLong(byte[] bytes, int offset) {
    int length = 6;
    if (offset + length > bytes.length) {
      String reason =
          "offset (" + offset + ") + length (" + length + ") exceed the"
              + " capacity of the array: " + bytes.length;
      throw new IllegalArgumentException(reason);
    }
    long l = bytes[0] >= 0 ? 0L : 65535L;

    for (int i = offset; i < offset + length; i++) {
      l <<= 8;
      l ^= bytes[i] & 0xFF;
    }
    return l;
  }
}
