package com.twitter.search.earlybird.index;

import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.search.common.partitioning.base.Segment;
import com.twitter.search.earlybird.EarlybirdIndexConfig;
import com.twitter.search.earlybird.partition.SearchIndexingMetricSet;
import com.twitter.search.earlybird.partition.SegmentSyncInfo;
import com.twitter.search.earlybird.stats.EarlybirdSearcherStats;

public class EarlybirdSegmentFactory {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdSegmentFactory.class);

  private final EarlybirdIndexConfig earlybirdIndexConfig;
  private final SearchIndexingMetricSet searchIndexingMetricSet;
  private final EarlybirdSearcherStats searcherStats;
  private Clock clock;

  public EarlybirdSegmentFactory(
      EarlybirdIndexConfig earlybirdIndexConfig,
      SearchIndexingMetricSet searchIndexingMetricSet,
      EarlybirdSearcherStats searcherStats,
      Clock clock) {
    this.earlybirdIndexConfig = earlybirdIndexConfig;
    this.searchIndexingMetricSet = searchIndexingMetricSet;
    this.searcherStats = searcherStats;
    this.clock = clock;
  }

  public EarlybirdIndexConfig getEarlybirdIndexConfig() {
    return earlybirdIndexConfig;
  }

  /**
   * Creates a new earlybird segment.
   */
  public EarlybirdSegment newEarlybirdSegment(Segment segment, SegmentSyncInfo segmentSyncInfo)
      throws IOException {
    Directory dir = earlybirdIndexConfig.newLuceneDirectory(segmentSyncInfo);

    LOG.info("Creating EarlybirdSegment on " + dir.toString());

    return new EarlybirdSegment(
        segment.getSegmentName(),
        segment.getTimeSliceID(),
        segment.getMaxSegmentSize(),
        dir,
        earlybirdIndexConfig,
        searchIndexingMetricSet,
        searcherStats,
        clock);
  }
}
