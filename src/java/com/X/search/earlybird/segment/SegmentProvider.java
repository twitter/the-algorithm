package com.X.search.earlybird.segment;

import java.io.IOException;
import java.util.List;

import com.X.search.common.partitioning.base.Segment;

public interface SegmentProvider {
  /**
   * Returns a *new* sorted list of all available segments on disk / db / hdfs / etc.
   */
  List<Segment> newSegmentList() throws IOException;
}
