package com.twitter.search.ingester.pipeline.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.ingester.pipeline.util.PipelineEtwitterceptionHandler;
import com.twitter.util.Duration;

public class PipelineEtwitterceptionImplV2 implements PipelineEtwitterceptionHandler  {
  private static final Logger LOG = LoggerFactory.getLogger(PipelineEtwitterceptionImplV2.class);
  private RealtimeIngesterPipelineV2 pipeline;

  public PipelineEtwitterceptionImplV2(RealtimeIngesterPipelineV2 pipeline) {
    this.pipeline = pipeline;
  }

  @Override
  public void logAndWait(String msg, Duration waitTime) throws InterruptedEtwitterception {
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
