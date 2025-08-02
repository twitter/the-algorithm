package com.twitter.search.earlybird_root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.earlybird.thrift.EarlybirdRequest;

public final class EtwitterceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(EtwitterceptionHandler.class);

  private EtwitterceptionHandler() {
  }

  public static void logEtwitterception(EarlybirdRequest request, Throwable e) {
    LOG.error("Etwitterception while handling request: {}", request, e);
  }
}
