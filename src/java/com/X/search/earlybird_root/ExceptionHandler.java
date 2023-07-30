package com.X.search.earlybird_root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.X.search.earlybird.thrift.EarlybirdRequest;

public final class ExceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

  private ExceptionHandler() {
  }

  public static void logException(EarlybirdRequest request, Throwable e) {
    LOG.error("Exception while handling request: {}", request, e);
  }
}
