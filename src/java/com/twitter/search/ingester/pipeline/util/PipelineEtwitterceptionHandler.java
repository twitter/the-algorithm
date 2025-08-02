package com.twitter.search.ingester.pipeline.util;

import com.twitter.util.Duration;

public interface PipelineEtwitterceptionHandler {
  /**
   * Logs the given message and waits the given duration.
   */
  void logAndWait(String msg, Duration waitTime) throws InterruptedEtwitterception;

  /**
   * Logs the given message and shutdowns the application.
   */
  void logAndShutdown(String msg);
}
