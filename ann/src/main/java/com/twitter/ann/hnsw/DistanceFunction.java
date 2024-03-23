package com.ExTwitter.ann.hnsw;

public interface DistanceFunction<T, Q> {
  /**
   * Distance between two items.
   */
  float distance(T t, Q q);
}
