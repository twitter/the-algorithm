package com.twitter.search.earlybird.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import com.twitter.common.util.Clock;
import com.twitter.search.common.metrics.SearchCounter;

public abstract class ScheduledExecutorTask implements Runnable {
  private final SearchCounter counter;
  protected final Clock clock;

  public ScheduledExecutorTask(SearchCounter counter, Clock clock) {
    Preconditions.checkNotNull(counter);
    this.counter = counter;
    this.clock = clock;
  }

  @Override
  public final void run() {
    counter.increment();
    runOneIteration();
  }

  @VisibleForTesting
  protected abstract void runOneIteration();
}
