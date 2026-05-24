package com.twitter.search.ingester.pipeline.util;

public class PipelineStageException extends Exception {
  public PipelineStageException(Object location, String message, Throwable cause) {
    super(message + " In Stage : " + location.getClass(), cause);
  }

  public PipelineStageException(Throwable cause) {
    super(cause);
  }

  public PipelineStageException(String message) {
    super(message);
  }

  public PipelineStageException(Object location, String message) {
    super(message + " In Stage : " + location.getClass());
  }
}
