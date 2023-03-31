package com.twitter.search.ingester.pipeline.util;

public class PipelineStageRuntimeException extends RuntimeException {
  public PipelineStageRuntimeException(String msg) {
    super(msg);
  }
}
