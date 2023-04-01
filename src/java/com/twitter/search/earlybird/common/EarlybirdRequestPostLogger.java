package com.twitter.search.earlybird.common;

import com.twitter.decider.Decider;
import com.twitter.search.common.metrics.Timer;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;

public final class EarlybirdRequestPostLogger {
  private final EarlybirdRequestLogger logger;

  public static EarlybirdRequestPostLogger buildForRoot(
      int latencyWarnThreshold, Decider decider) {

    EarlybirdRequestLogger requestLogger = EarlybirdRequestLogger.buildForRoot(
        EarlybirdRequestPostLogger.class.getName(), latencyWarnThreshold, decider);

    return new EarlybirdRequestPostLogger(requestLogger);
  }

  public static EarlybirdRequestPostLogger buildForShard(
      int latencyWarnThreshold, Decider decider) {

    EarlybirdRequestLogger requestLogger = EarlybirdRequestLogger.buildForShard(
        EarlybirdRequestPostLogger.class.getName(), latencyWarnThreshold, decider);

    return new EarlybirdRequestPostLogger(requestLogger);
  }

  private EarlybirdRequestPostLogger(EarlybirdRequestLogger logger) {
    this.logger = logger;
  }

  public void logRequest(EarlybirdRequest request, EarlybirdResponse response, Timer timer) {
    EarlybirdRequestUtil.updateHitsCounters(request);
    logger.logRequest(request, response, timer);
  }
}
