package com.twitter.search.earlybird.exception;

import org.apache.kafka.common.errors.ApiException;

/**
 * Kafka's ApiException class doesn't retain its stack trace (see its source code).
 * As a result a kafka exception that propagates up the call chain can't point to where exactly
 * did the exception happen in our code. As a solution, use this class when calling kafka API
 * methods.
 */
public class WrappedKafkaApiException extends RuntimeException {
  public WrappedKafkaApiException(ApiException cause) {
    super(cause);
  }

  public WrappedKafkaApiException(String message, ApiException cause) {
    super(message, cause);
  }
}
