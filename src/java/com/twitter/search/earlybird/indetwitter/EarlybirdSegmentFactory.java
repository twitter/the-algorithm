package com.twitter.search.earlybird.indetwitter;

import java.io.IOEtwitterception;

import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.search.common.partitioning.base.Segment;
import com.twitter.search.earlybird.EarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.partition.SearchIndetwitteringMetricSet;
import com.twitter.search.earlybird.partition.SegmentSyncInfo;
import com.twitter.search.earlybird.stats.EarlybirdSearcherStats;

public class EarlybirdSegmentFactory {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdSegmentFactory.class);

  private final EarlybirdIndetwitterConfig earlybirdIndetwitterConfig;
  private final SearchIndetwitteringMetricSet searchIndetwitteringMetricSet;
  private final EarlybirdSearcherStats searcherStats;
  private Clock clock;

  public EarlybirdSegmentFactory(
      EarlybirdIndetwitterConfig earlybirdIndetwitterConfig,
      SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      EarlybirdSearcherStats searcherStats,
      Clock clock) {
    this.earlybirdIndetwitterConfig = earlybirdIndetwitterConfig;
    this.searchIndetwitteringMetricSet = searchIndetwitteringMetricSet;
    this.searcherStats = searcherStats;
    this.clock = clock;
  }

  public EarlybirdIndetwitterConfig getEarlybirdIndetwitterConfig() {
    return earlybirdIndetwitterConfig;
  }

  /**
   * Creates a new earlybird segment.
   */
  public EarlybirdSegment newEarlybirdSegment(Segment segment, SegmentSyncInfo segmentSyncInfo)
      throws IOEtwitterception {
    Directory dir = earlybirdIndetwitterConfig.newLuceneDirectory(segmentSyncInfo);

    LOG.info("Creating EarlybirdSegment on " + dir.toString());

    return new EarlybirdSegment(
        segment.getSegmentName(),
        segment.getTimeSliceID(),
        segment.getMatwitterSegmentSize(),
        dir,
        earlybirdIndetwitterConfig,
        searchIndetwitteringMetricSet,
        searcherStats,
        clock);
  }
}
