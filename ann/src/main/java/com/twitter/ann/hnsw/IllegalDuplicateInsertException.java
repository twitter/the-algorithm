package com.twitter.ann.hnsw;

public class IllegalDuplicateInsertException extends Exception {
  public IllegalDuplicateInsertException(String message) {
    super(message);
  }
}
