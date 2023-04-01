package com.twitter.search.ingester.pipeline.util;

import java.util.concurrent.CompletableFuture;

public class BatchedElement<T, R> {
  private CompletableFuture<R> completableFuture;
  private T item;

  public BatchedElement(T item, CompletableFuture<R> completableFuture) {
    this.item = item;
    this.completableFuture = completableFuture;
  }

  public T getItem() {
    return item;
  }

  public CompletableFuture<R> getCompletableFuture() {
    return completableFuture;
  }
}
