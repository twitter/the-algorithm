package com.X.search.earlybird.archive.segmentbuilder;

import com.X.search.earlybird.index.EarlybirdSegmentFactory;
import com.X.search.earlybird.partition.SegmentInfo;
import com.X.search.earlybird.partition.SegmentSyncConfig;

public class BuiltAndFinalizedSegment extends SegmentBuilderSegment {
  public BuiltAndFinalizedSegment(
      SegmentInfo segmentInfo,
      SegmentConfig segmentConfig,
      EarlybirdSegmentFactory earlybirdSegmentFactory,
      int alreadyRetriedCount,
      SegmentSyncConfig sync) {

    super(segmentInfo, segmentConfig, earlybirdSegmentFactory, alreadyRetriedCount, sync);
  }

  @Override
  public SegmentBuilderSegment handle() throws SegmentInfoConstructionException,
      SegmentUpdaterException {

    throw new IllegalStateException("Should not handle a BuildAndFinalizedSegment.");
  }

  @Override
  public boolean isBuilt() {
    return true;
  }
}
