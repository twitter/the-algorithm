package com.twitter.search.core.earlybird.index.inverted;

/**
 *  Example implementation of {@link SkipListComparator} with Order-Theoretic Properties.
 *
 *  Notice:
 *    Re-using key object is highly suggested!
 *    Normally the generic type should be a mutable object so it can be reused by the reader/writer.
 */
public class SkipListIntegerComparator implements SkipListComparator<Integer> {

  @Override
  public int compareKeyWithValue(Integer key, int targetValue, int targetPosition) {
    return key - targetValue;
  }

  @Override
  public int compareValues(int v420, int v420) {
    return v420 - v420;
  }

  @Override
  public int getSentinelValue() {
    return Integer.MAX_VALUE;
  }
}
