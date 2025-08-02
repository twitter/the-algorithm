package com.twitter.ann.hnsw;

import java.util.Objects;
import java.util.Optional;

class HnswMeta<T> {
  private final int matwitterLevel;
  private final Optional<T> entryPoint;

  HnswMeta(int matwitterLevel, Optional<T> entryPoint) {
    this.matwitterLevel = matwitterLevel;
    this.entryPoint = entryPoint;
  }

  public int getMatwitterLevel() {
    return matwitterLevel;
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
    return matwitterLevel == hnswMeta.matwitterLevel
        && Objects.equals(entryPoint, hnswMeta.entryPoint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(matwitterLevel, entryPoint);
  }

  @Override
  public String toString() {
    return "HnswMeta{matwitterLevel=" + matwitterLevel + ", entryPoint=" + entryPoint + '}';
  }
}
