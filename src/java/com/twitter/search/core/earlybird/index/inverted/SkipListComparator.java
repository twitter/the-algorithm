package com.twitter.search.core.earlybird.index.inverted;

/**
 * Comparator interface for {@link SkipListContainer},
 * see sample implementation {@link SkipListIntegerComparator}.
 *
 * Notice: less/equal/greater here refer to the order precedence, instead of numerical value.
 */
public interface SkipListComparator<K> {

  /**
   * Determine the order between the given key and the key of the given targetValue.
   * Notice, usually key of a value could be derived from the value along.
   *
   * Implementation of this method should consider sentinel value, see {@link #getSentinelValue()}.
   *
   * Can include position data (primarily for text posting lists). Position should be ignored if
   * the skip list was constructed without positions enabled.
   *
   * @return negative, zero, or positive to indicate if first value is
   *         less than, equal to, or greater than the second value, respectively.
   */
  int compareKeyWithValue(K key, int targetValue, int targetPosition);

  /**
   * Determine the order of two given values based on their keys.
   * Notice, usually key of a value could be derived from the value along.
   *
   * Implementation of this method should consider sentinel value, see {@link #getSentinelValue()}.
   *
   * @return negative, zero, or positive to indicate if first value is
   *         less than, equal to, or greater than the second value, respectively.
   */
  int compareValues(int v1, int v2);

  /**
   * Return a sentinel value, sentinel value should be considered by this comparator
   * as an ADVISORY GREATEST value, which should NOT be actually inserted into the skip list.
   *
   * @return the sentinel value.
   */
  int getSentinelValue();
}
