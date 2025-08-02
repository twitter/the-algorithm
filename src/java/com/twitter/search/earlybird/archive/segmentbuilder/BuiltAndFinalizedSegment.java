package com.twitter.search.earlybird.archive.segmentbuilder;

import com.twitter.search.earlybird.indetwitter.EarlybirdSegmentFactory;
import com.twitter.search.earlybird.partition.SegmentInfo;
import com.twitter.search.earlybird.partition.SegmentSyncConfig;

public class BuiltAndFinalizedSegment etwittertends SegmentBuilderSegment {
  public BuiltAndFinalizedSegment(
      SegmentInfo segmentInfo,
      SegmentConfig segmentConfig,
      EarlybirdSegmentFactory earlybirdSegmentFactory,
      int alreadyRetriedCount,
      SegmentSyncConfig sync) {

    super(segmentInfo, segmentConfig, earlybirdSegmentFactory, alreadyRetriedCount, sync);
  }

  @Override
  public SegmentBuilderSegment handle() throws SegmentInfoConstructionEtwitterception,
      SegmentUpdaterEtwitterception {

    throw new IllegalStateEtwitterception("Should not handle a BuildAndFinalizedSegment.");
  }

  @Override
  public boolean isBuilt() {
    return true;
  }
}
