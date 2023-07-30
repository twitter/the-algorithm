package com.X.search.ingester.model;

import com.X.search.common.debug.DebugEventAccumulator;
import com.X.search.common.debug.thriftjava.DebugEvents;
import com.X.tweetypie.thriftjava.TweetEvent;

public class IngesterTweetEvent extends TweetEvent implements DebugEventAccumulator {
  // Used for propagating DebugEvents through the ingester stages.
  private final DebugEvents debugEvents;

  public IngesterTweetEvent() {
    this.debugEvents = new DebugEvents();
  }

  @Override
  public DebugEvents getDebugEvents() {
    return debugEvents;
  }
}
