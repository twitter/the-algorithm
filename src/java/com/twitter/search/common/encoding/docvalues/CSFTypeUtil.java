package com.twitter.search.common.encoding.docvalues;

public final class CSFTypeUtil {
  private CSFTypeUtil() {
  }

  /**
   * Convert a long into a byte array, stored into dest.
   */
  public static void convertToBytes(byte[] dest, int valueIndex, int value) {
    int offset = valueIndex * Integer.BYTES;
    dest[offset] = (byte) (value >>> 24);
    dest[offset + 1] = (byte) (value >>> 16);
    dest[offset + 2] = (byte) (value >>> 8);
    dest[offset + 3] = (byte) value;
  }

  /**
   * Convert bytes into a long value. Inverse function of convertToBytes.
   */
  public static int convertFromBytes(byte[] data, int startOffset, int valueIndex) {
    // This should rarely happen, eg. when we get a corrupt ThriftIndexingEvent, we insert a new
    // Document which is blank. Such a document results in a length 0 BytesRef.
    if (data.length == 0) {
      return 0;
    }

    int offset = startOffset + valueIndex * Integer.BYTES;
    return ((data[offset] & 0xFF) << 24)
        | ((data[offset + 1] & 0xFF) << 16)
        | ((data[offset + 2] & 0xFF) << 8)
        | (data[offset + 3] & 0xFF);
  }
}
