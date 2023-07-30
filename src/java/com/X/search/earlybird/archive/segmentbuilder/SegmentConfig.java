package com.X.search.earlybird.archive.segmentbuilder;

import com.X.common.quantity.Amount;
import com.X.common.quantity.Time;
import com.X.search.common.util.zktrylock.ZooKeeperTryLockFactory;
import com.X.search.earlybird.archive.ArchiveOnDiskEarlybirdIndexConfig;

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
