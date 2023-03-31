package com.twitter.search.common.relevance.features;

import com.twitter.search.common.encoding.features.ByteNormalizer;
import com.twitter.search.common.encoding.features.SingleBytePositiveFloatNormalizer;
import com.twitter.search.common.encoding.features.SmartIntegerNormalizer;

/**
 * Byte value normalizers used to push feature values into earlybird db.
 */
public abstract class MutableFeatureNormalizers {
  // The max value we support in SMART_INTEGER_NORMALIZER below, this should be enough for all kinds
  // of engagements we see on Twitter, anything larger than this would be represented as the same
  // value (255, if using a byte).
  private static final int MAX_COUNTER_VALUE_SUPPORTED = 50000000;

  // Avoid using this normalizer for procesing any new data, always use SmartIntegerNormalizer
  // below.
  public static final SingleBytePositiveFloatNormalizer BYTE_NORMALIZER =
      new SingleBytePositiveFloatNormalizer();

  public static final ByteNormalizer SMART_INTEGER_NORMALIZER =
      new SmartIntegerNormalizer(MAX_COUNTER_VALUE_SUPPORTED, 8);
}
