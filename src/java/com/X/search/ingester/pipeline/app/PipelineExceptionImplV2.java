package com.X.search.ingester.pipeline.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.X.search.ingester.pipeline.util.PipelineExceptionHandler;
import com.X.util.Duration;

public class PipelineExceptionImplV2 implements PipelineExceptionHandler  {
  private static final Logger LOG = LoggerFactory.getLogger(PipelineExceptionImplV2.class);
  private RealtimeIngesterPipelineV2 pipeline;

  public PipelineExceptionImplV2(RealtimeIngesterPipelineV2 pipeline) {
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
