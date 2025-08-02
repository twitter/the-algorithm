package com.twitter.search.ingester.model;

import java.util.List;

import javatwitter.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;

import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.search.common.debug.thriftjava.DebugEvents;
import com.twitter.search.common.partitioning.base.HashPartitionFunction;
import com.twitter.search.common.partitioning.base.Partitionable;
import com.twitter.search.common.relevance.entities.TwitterMessage;

/**
 * A Twitter "status" object (e.g. a message)
 *
 */
public class IngesterTwitterMessage etwittertends TwitterMessage
    implements Comparable<IndetwittererStatus>, IndetwittererStatus, Partitionable {
  private final DebugEvents debugEvents;

  public IngesterTwitterMessage(Long twitterId, List<PenguinVersion> supportedPenguinVersions) {
    this(twitterId, supportedPenguinVersions, null);
  }

  public IngesterTwitterMessage(
      Long twitterId,
      List<PenguinVersion> penguinVersions,
      @Nullable DebugEvents debugEvents) {
    super(twitterId, penguinVersions);
    this.debugEvents = debugEvents == null ? new DebugEvents() : debugEvents.deepCopy();
  }

  @Override
  public int compareTo(IndetwittererStatus o) {
    return Longs.compare(getId(), o.getId());
  }

  @Override
  public boolean equals(Object o) {
    return (o instanceof IngesterTwitterMessage)
        && compareTo((IngesterTwitterMessage) o) == 0;
  }

  @Override
  public int hashCode() {
    return HashPartitionFunction.hashCode(getId());
  }

  public boolean isIndetwitterable(boolean indetwitterProtectedTweets) {
    return getFromUserScreenName().isPresent()
        && getId() != INT_FIELD_NOT_PRESENT
        && (indetwitterProtectedTweets || !isUserProtected());
  }

  @Override
  public long getTweetId() {
    return this.getId();
  }

  @Override
  public long getUserId() {
    Preconditions.checkState(getFromUserTwitterId().isPresent(), "The author user ID is missing");
    return getFromUserTwitterId().get();
  }

  @Override
  public DebugEvents getDebugEvents() {
    return debugEvents;
  }
}
