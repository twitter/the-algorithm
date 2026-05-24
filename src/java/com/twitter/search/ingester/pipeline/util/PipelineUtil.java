package com.twitter.search.ingester.pipeline.util;

import com.google.common.base.Preconditions;

import org.apache.commons.pipeline.Feeder;
import org.apache.commons.pipeline.stage.InstrumentedBaseStage;

public final class PipelineUtil {

  /**
   * Feed an object to a specified stage.  Used for stages that follow the pattern of
   * looping indefinitely in the first call to process() and don't care what the object passed
   * in is, but still needs at least one item fed to the stage to start processing.
   *
   * Examples of stages like this are: EventBusReaderStage and KafkaBytesReaderStage
   *
   * @param stage stage to enqueue an arbitrary object to.
   */
  public static void feedStartObjectToStage(InstrumentedBaseStage stage) {
    Feeder stageFeeder = stage.getStageContext().getStageFeeder(stage);
    Preconditions.checkNotNull(stageFeeder);
    stageFeeder.feed("off to the races");
  }

  private PipelineUtil() { /* prevent instantiation */ }
}
