package com.twitter.search.ingester.pipeline.twitter.thriftparse;

public final class ThriftTweetParsingException extends Exception {
  public ThriftTweetParsingException(String message) {
    super(message);
  }
}
