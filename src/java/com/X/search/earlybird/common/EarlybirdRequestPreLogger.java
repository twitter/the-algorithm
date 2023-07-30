package com.X.search.earlybird.common;

import com.X.decider.Decider;
import com.X.search.earlybird.thrift.EarlybirdRequest;

public final class EarlybirdRequestPreLogger {
  private final EarlybirdRequestLogger logger;

  public static EarlybirdRequestPreLogger buildForRoot(Decider decider) {
    EarlybirdRequestLogger requestLogger = EarlybirdRequestLogger.buildForRoot(
        EarlybirdRequestPreLogger.class.getName(), Integer.MAX_VALUE, decider);

    return new EarlybirdRequestPreLogger(requestLogger);
  }

  public static EarlybirdRequestPreLogger buildForShard(
      int latencyWarnThreshold, Decider decider) {

    EarlybirdRequestLogger requestLogger = EarlybirdRequestLogger.buildForShard(
        EarlybirdRequestPreLogger.class.getName(), latencyWarnThreshold, decider);

    return new EarlybirdRequestPreLogger(requestLogger);
  }

  private EarlybirdRequestPreLogger(EarlybirdRequestLogger logger) {
    this.logger = logger;
  }

  public void logRequest(EarlybirdRequest request) {
    logger.logRequest(request, null, null);
  }
}
