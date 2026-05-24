package com.twitter.search.earlybird.exception;

public class MissingKafkaTopicException extends Exception {
  public MissingKafkaTopicException(String message) {
    super(message);
  }

  public MissingKafkaTopicException(String message, Throwable cause) {
    super(message, cause);
  }
}
