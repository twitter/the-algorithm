package com.twitter.search.common.encoding.features;

/**
 * Normalizes using the logic described in {@link SingleBytePositiveFloatUtil}.
 */
public class SingleBytePositiveFloatNormalizer extends ByteNormalizer {

  @Override
  public byte normalize(double val) {
    return SingleBytePositiveFloatUtil.toSingleBytePositiveFloat((float) val);
  }

  @Override
  public double unnormLowerBound(byte norm) {
    return SingleBytePositiveFloatUtil.toJavaFloat(norm);
  }

  /**
   * Get the upper bound of the raw value for a normalized byte.
   * @deprecated This is wrongly implemented, always use unnormLowerBound(),
   * or use SmartIntegerNormalizer.
   */
  @Override @Deprecated
  public double unnormUpperBound(byte norm) {
    return 1 + SingleBytePositiveFloatUtil.toJavaFloat(norm);
  }

  /**
   * Return the the post-log2 unnormalized value. This is only used for some legacy Earlybird
   * features and scoring functions.
   */
  public double unnormAndLog2(byte norm) {
    return SingleBytePositiveFloatUtil.toLog2Double(norm);
  }
}
