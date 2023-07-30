package com.X.search.earlybird_root;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

import com.X.search.common.decider.SearchDecider;
import com.X.search.common.metrics.Timer;
import com.X.search.common.root.LoggingSupport;
import com.X.search.earlybird.common.EarlybirdRequestPostLogger;
import com.X.search.earlybird.common.EarlybirdRequestPreLogger;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;

public class EarlybirdServiceLoggingSupport extends
    LoggingSupport.DefaultLoggingSupport<EarlybirdRequest, EarlybirdResponse> {
  private static final int LATENCY_WARN_THRESHOLD_MS = 100;

  private static final Timer DUMMY_TIMER;

  private final EarlybirdRequestPreLogger requestPreLogger;
  private final EarlybirdRequestPostLogger requestPostLogger;


  static {
    DUMMY_TIMER = new Timer(TimeUnit.MILLISECONDS);
    DUMMY_TIMER.stop();
  }

  public EarlybirdServiceLoggingSupport(SearchDecider decider) {
    requestPreLogger = EarlybirdRequestPreLogger.buildForRoot(decider.getDecider());
    requestPostLogger = EarlybirdRequestPostLogger.buildForRoot(LATENCY_WARN_THRESHOLD_MS,
                                                                decider.getDecider());
  }

  @Override
  public void prelogRequest(EarlybirdRequest req) {
    requestPreLogger.logRequest(req);
  }

  @Override
  public void postLogRequest(
      EarlybirdRequest request,
      EarlybirdResponse response,
      long latencyNanos) {

    Preconditions.checkNotNull(request);
    Preconditions.checkNotNull(response);

    response.setResponseTimeMicros(TimeUnit.NANOSECONDS.toMicros(latencyNanos));
    response.setResponseTime(TimeUnit.NANOSECONDS.toMillis(latencyNanos));

    requestPostLogger.logRequest(request, response, DUMMY_TIMER);
  }

  @Override
  public void logExceptions(EarlybirdRequest req, Throwable t) {
    ExceptionHandler.logException(req, t);
  }
}
