package com.twitter.search.common.relevance.text;

public class VisibleTokenRatioNormalizer {

  private static final int NORMALIZE_TO_BITS = 4;
  private final int normalizeToSize;

  /**
   * constructor
   */
  public VisibleTokenRatioNormalizer(int normalizeToBits) {
    int size = 2 << (normalizeToBits - 1);
    // Let's say normalizeSize is set to 16....
    // If you multiply 1.0 * 16, it is 16
    // If you multiply 0.0 * 16, it is 0
    // That would be occupying 17 ints, not 16, so we subtract 1 here...
    this.normalizeToSize = size - 1;
  }

  /**
   * method
   */
  public int normalize(double percent) {
    if (percent > 1 || percent < 0) {
      throw new IllegalArgumentException("percent should be less than 1 and greater than 0");
    }
    int bucket = (int) (percent * normalizeToSize);
    return normalizeToSize - bucket;
  }

  public double denormalize(int reverseBucket) {
    int bucket = normalizeToSize - reverseBucket;
    return bucket / (double) normalizeToSize;
  }

  public static VisibleTokenRatioNormalizer createInstance() {
    return new VisibleTokenRatioNormalizer(NORMALIZE_TO_BITS);
  }
}
