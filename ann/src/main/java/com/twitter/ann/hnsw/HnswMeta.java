package com.twitter.ann.hnsw;

import java.util.Objects;
import java.util.Optional;

class HnswMeta<T> {
  private final int maxLevel;
  private final Optional<T> entryPoint;

  HnswMeta(int maxLevel, Optional<T> entryPoint) {
    this.maxLevel = maxLevel;
    this.entryPoint = entryPoint;
  }

  public int getMaxLevel() {
    return maxLevel;
  }

  public Optional<T> getEntryPoint() {
    return entryPoint;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HnswMeta<?> hnswMeta = (HnswMeta<?>) o;
    return maxLevel == hnswMeta.maxLevel
        && Objects.equals(entryPoint, hnswMeta.entryPoint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxLevel, entryPoint);
  }

  @Override
  public String toString() {
    return "HnswMeta{maxLevel=" + maxLevel + ", entryPoint=" + entryPoint + '}';
  }
}
