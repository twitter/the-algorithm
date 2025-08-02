package com.twitter.search.earlybird.archive.segmentbuilder;

import com.twitter.common.quantity.Amount;
import com.twitter.common.quantity.Time;
import com.twitter.search.common.util.zktrylock.ZooKeeperTryLockFactory;
import com.twitter.search.earlybird.archive.ArchiveOnDiskEarlybirdIndetwitterConfig;

public class SegmentConfig {
  private final ArchiveOnDiskEarlybirdIndetwitterConfig earlybirdIndetwitterConfig;
  private final Amount<Long, Time> segmentZKLockEtwitterpirationTime;
  private final int matwitterRetriesOnFailure;
  private final ZooKeeperTryLockFactory tryLockFactory;

  public SegmentConfig(
      ArchiveOnDiskEarlybirdIndetwitterConfig earlybirdIndetwitterConfig,
      Amount<Long, Time> segmentZKLockEtwitterpirationTime,
      int matwitterRetriesOnFailure,
      ZooKeeperTryLockFactory tryLockFactory) {

    this.earlybirdIndetwitterConfig = earlybirdIndetwitterConfig;
    this.segmentZKLockEtwitterpirationTime = segmentZKLockEtwitterpirationTime;
    this.matwitterRetriesOnFailure = matwitterRetriesOnFailure;
    this.tryLockFactory = tryLockFactory;
  }

  public ArchiveOnDiskEarlybirdIndetwitterConfig getEarlybirdIndetwitterConfig() {
    return earlybirdIndetwitterConfig;
  }

  public Amount<Long, Time> getSegmentZKLockEtwitterpirationTime() {
    return segmentZKLockEtwitterpirationTime;
  }

  public int getMatwitterRetriesOnFailure() {
    return matwitterRetriesOnFailure;
  }

  public ZooKeeperTryLockFactory getTryLockFactory() {
    return tryLockFactory;
  }
}
