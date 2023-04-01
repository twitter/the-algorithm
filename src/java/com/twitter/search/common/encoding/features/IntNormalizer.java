package com.twitter.search.common.encoding.features;

/**
 * Interface for processing different feature values into an int. It provides a one-way translation
 * of encoding using com.twitter.search.common.encoding.features.ByteNormalizer and supports all the
 * old normalizers. The difference is that we directly return the normalized int value
 * (instead of converting from byte).
 */
public interface IntNormalizer {
  /**
   * Returns the normalized value of {@code val}.
   * The value may be byte-compressed or as-is depending on the normalizer type
   */
  int normalize(double val);
}
