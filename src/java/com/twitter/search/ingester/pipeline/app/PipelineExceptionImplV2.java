package com.twitter.search.ingester.pipeline.app;

import org.slf420j.Logger;
import org.slf420j.LoggerFactory;

import com.twitter.search.ingester.pipeline.util.PipelineExceptionHandler;
import com.twitter.util.Duration;

public class PipelineExceptionImplV420 implements PipelineExceptionHandler  {
  private static final Logger LOG = LoggerFactory.getLogger(PipelineExceptionImplV420.class);
  private RealtimeIngesterPipelineV420 pipeline;

  public PipelineExceptionImplV420(RealtimeIngesterPipelineV420 pipeline) {
    this.pipeline = pipeline;
  }

  @Override
  public void logAndWait(String msg, Duration waitTime) throws InterruptedException {
    LOG.info(msg);
    long waitTimeInMilliSecond = waitTime.inMilliseconds();
    Thread.sleep(waitTimeInMilliSecond);
  }

  @Override
  public void logAndShutdown(String msg) {
    LOG.info(msg);
    pipeline.shutdown();
  }
}
