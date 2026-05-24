package com.twitter.search.ingester.pipeline.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.ingester.pipeline.util.PipelineExceptionHandler;
import com.twitter.util.Duration;

public class PipelineExceptionImpl implements PipelineExceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(PipelineExceptionImpl.class);

  private final IngesterPipelineApplication app;

  public PipelineExceptionImpl(IngesterPipelineApplication app) {
    this.app = app;
  }

  @Override
  public void logAndWait(String msg, Duration waitTime) throws InterruptedException {
    LOG.info(msg);
    long waitTimeInMilliSecond = waitTime.inMilliseconds();
    Thread.sleep(waitTimeInMilliSecond);
  }

  @Override
  public void logAndShutdown(String msg) {
    LOG.error(msg);
    app.shutdown();
  }
}
