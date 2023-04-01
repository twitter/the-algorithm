package com.twitter.ann.hnsw;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class HnswNode<T> {
  public final int level;
  public final T item;

  public HnswNode(int level, T item) {
    this.level = level;
    this.item = item;
  }

  /**
   * Create a hnsw node.
   */
  public static <T> HnswNode<T> from(int level, T item) {
    return new HnswNode<>(level, item);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof HnswNode)) {
      return false;
    }

    HnswNode<?> that = (HnswNode<?>) o;
    return new EqualsBuilder()
        .append(this.item, that.item)
        .append(this.level, that.level)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(item)
        .append(level)
        .toHashCode();
  }
}
