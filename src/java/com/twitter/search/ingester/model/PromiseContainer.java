package com.twitter.search.ingester.model;

import com.twitter.util.Promise;

public class PromiseContainer<T, U> {
  private final Promise<T> promise;
  private final U obj;

  public PromiseContainer(Promise<T> promise, U obj) {
    this.promise = promise;
    this.obj = obj;
  }

  public Promise<T> getPromise() {
    return promise;
  }

  public U getObj() {
    return obj;
  }
}
