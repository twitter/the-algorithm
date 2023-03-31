package com.twitter.search.ingester.pipeline.util;

public class ResponseNotReturnedException extends Exception {
  ResponseNotReturnedException(Object request) {
    super("Response not returned in batch for request: " + request);
  }
}
