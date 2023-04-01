package com.twitter.search.earlybird.archive.segmentbuilder;

import com.twitter.common.quantity.Amount;
import com.twitter.common.quantity.Time;
import com.twitter.search.common.util.zktrylock.ZooKeeperTryLockFactory;
import com.twitter.search.earlybird.archive.ArchiveOnDiskEarlybirdIndexConfig;

public class SegmentConfig {
  private final ArchiveOnDiskEarlybirdIndexConfig earlybirdIndexConfig;
  private final Amount<Long, Time> segmentZKLockExpirationTime;
  private final int maxRetriesOnFailure;
  private final ZooKeeperTryLockFactory tryLockFactory;

  public SegmentConfig(
      ArchiveOnDiskEarlybirdIndexConfig earlybirdIndexConfig,
      Amount<Long, Time> segmentZKLockExpirationTime,
      int maxRetriesOnFailure,
      ZooKeeperTryLockFactory tryLockFactory) {

    this.earlybirdIndexConfig = earlybirdIndexConfig;
    this.segmentZKLockExpirationTime = segmentZKLockExpirationTime;
    this.maxRetriesOnFailure = maxRetriesOnFailure;
    this.tryLockFactory = tryLockFactory;
  }

  public ArchiveOnDiskEarlybirdIndexConfig getEarlybirdIndexConfig() {
    return earlybirdIndexConfig;
  }

  public Amount<Long, Time> getSegmentZKLockExpirationTime() {
    return segmentZKLockExpirationTime;
  }

  public int getMaxRetriesOnFailure() {
    return maxRetriesOnFailure;
  }

  public ZooKeeperTryLockFactory getTryLockFactory() {
    return tryLockFactory;
  }
}
