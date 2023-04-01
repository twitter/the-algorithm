package com.twitter.search.ingester.pipeline.util;
import java.util.concurrent.TimeUnit;
import com.twitter.common.base.MorePreconditions;
import com.twitter.search.common.metrics.SearchTimerStats;
import org.apache.commons.pipeline.stage.StageTimer;
/**
 * Adds science stats export to StageTimer
 */
public class IngesterStageTimer extends StageTimer {
  private final String name;
  private final SearchTimerStats timer;

  public IngesterStageTimer(String statName) {
    name = MorePreconditions.checkNotBlank(statName);
    timer = SearchTimerStats.export(name, TimeUnit.NANOSECONDS, true);
  }

  public String getName() {
    return name;
  }

  @Override
  public void start() {
    // This override is not necessary; it is added for code readability.
    // super.start puts the current time in startTime
    super.start();
  }

  @Override
  public void stop() {
    super.stop();
    long runTime = System.nanoTime() - startTime.get();
    timer.timerIncrement(runTime);
  }
}
