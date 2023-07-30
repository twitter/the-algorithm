package com.X.search.ingester.model;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;

import com.X.common_internal.text.version.PenguinVersion;
import com.X.search.common.debug.thriftjava.DebugEvents;
import com.X.search.common.partitioning.base.HashPartitionFunction;
import com.X.search.common.partitioning.base.Partitionable;
import com.X.search.common.relevance.entities.XMessage;

/**
 * A X "status" object (e.g. a message)
 *
 */
public class IngesterXMessage extends XMessage
    implements Comparable<IndexerStatus>, IndexerStatus, Partitionable {
  private final DebugEvents debugEvents;

  public IngesterXMessage(Long XId, List<PenguinVersion> supportedPenguinVersions) {
    this(XId, supportedPenguinVersions, null);
  }

  public IngesterXMessage(
      Long XId,
      List<PenguinVersion> penguinVersions,
      @Nullable DebugEvents debugEvents) {
    super(XId, penguinVersions);
    this.debugEvents = debugEvents == null ? new DebugEvents() : debugEvents.deepCopy();
  }

  @Override
  public int compareTo(IndexerStatus o) {
    return Longs.compare(getId(), o.getId());
  }

  @Override
  public boolean equals(Object o) {
    return (o instanceof IngesterXMessage)
        && compareTo((IngesterXMessage) o) == 0;
  }

  @Override
  public int hashCode() {
    return HashPartitionFunction.hashCode(getId());
  }

  public boolean isIndexable(boolean indexProtectedTweets) {
    return getFromUserScreenName().isPresent()
        && getId() != INT_FIELD_NOT_PRESENT
        && (indexProtectedTweets || !isUserProtected());
  }

  @Override
  public long getTweetId() {
    return this.getId();
  }

  @Override
  public long getUserId() {
    Preconditions.checkState(getFromUserXId().isPresent(), "The author user ID is missing");
    return getFromUserXId().get();
  }

  @Override
  public DebugEvents getDebugEvents() {
    return debugEvents;
  }
}
