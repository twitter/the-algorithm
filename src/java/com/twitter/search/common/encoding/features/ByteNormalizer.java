package com.twitter.search.common.encoding.features;

/**
 * Interface for compressing unbounded float values to a signed byte. It includes both
 * normalization of values and encoding of values in a byte.
 */
public abstract class ByteNormalizer {
  public static byte intToUnsignedByte(int i) {
    return (byte) i;
  }

  public static int unsignedByteToInt(byte b) {
    return (int) b & 0xFF;
  }

  /**
   * Returns the byte-compressed value of {@code val}.
   */
  public abstract byte normalize(double val);

  /**
   * Returns a lower bound to the unnormalized range of {@code norm}.
   */
  public abstract double unnormLowerBound(byte norm);

  /**
   * Returns an upper bound to the unnormalized range of {@code norm}.
   */
  public abstract double unnormUpperBound(byte norm);

  /**
   * Returns true if the normalized value of {@code val} is different than the normalized value of
   * {@code val - 1}
   */
  public boolean changedNorm(double val) {
    return normalize(val) != normalize(val - 1);
  }
}
