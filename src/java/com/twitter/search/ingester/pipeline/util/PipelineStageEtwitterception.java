package com.twitter.search.ingester.pipeline.util;

public class PipelineStageEtwitterception etwittertends Etwitterception {
  public PipelineStageEtwitterception(Object location, String message, Throwable cause) {
    super(message + " In Stage : " + location.getClass(), cause);
  }

  public PipelineStageEtwitterception(Throwable cause) {
    super(cause);
  }

  public PipelineStageEtwitterception(String message) {
    super(message);
  }

  public PipelineStageEtwitterception(Object location, String message) {
    super(message + " In Stage : " + location.getClass());
  }
}
