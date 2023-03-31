package com.twitter.ann.hnsw;

/**
 * An item associated with a float distance
 * @param <T> The type of the item.
 */
public class DistancedItem<T> {
  private final T item;
  private final float distance;

  public DistancedItem(T item, float distance) {
    this.item = item;
    this.distance = distance;
  }

  public T getItem() {
    return item;
  }

  public float getDistance() {
    return distance;
  }
}
