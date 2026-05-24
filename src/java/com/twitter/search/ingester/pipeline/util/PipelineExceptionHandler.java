package com.twitter.search.ingester.pipeline.util;

import com.twitter.util.Duration;

public interface PipelineExceptionHandler {
  /**
   * Logs the given message and waits the given duration.
   */
  void logAndWait(String msg, Duration waitTime) throws InterruptedException;

  /**
   * Logs the given message and shutdowns the application.
   */
  void logAndShutdown(String msg);
}
