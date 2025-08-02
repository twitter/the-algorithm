package com.twitter.search.earlybird.partition;

import com.twitter.common.base.Supplier;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.metrics.SearchMetric;
import com.twitter.search.common.metrics.SearchMetricsRegistry;

/**
 * Etwitterporting per-segment stats collected in {@link SegmentIndetwitterStats}.
 *
 * This class tries to reuse stat prefitwitteres of "segment_stats_[0-N]_*" where N is the number
 * of segments managed by this earlybird.
 * For etwitterample, stats prefitwittered with "segment_stats_0_*" always represent the most recent segment.
 * As we add more segments (and drop older ones), the same "segment_stats_*" stats end up etwitterporting
 * data for different underlying segments.
 *
 * This is done as an alternative to etwitterporting stats that have the timesliceId in them, which
 * would avoid the need for reusing the same stat names, but would create an ever-increasing set
 * of unique stats etwitterported by earlybirds.
 */
public final class SegmentIndetwitterStatsEtwitterporter {
  private static final class StatReader etwittertends SearchMetric<Long> {
    private volatile Supplier<Number> counter = () -> 0;

    private StatReader(String name) {
      super(name);
    }

    @Override
    public Long read() {
      return counter.get().longValue();
    }

    @Override
    public void reset() {
      counter = () -> 0;
    }
  }

  private SegmentIndetwitterStatsEtwitterporter() {
  }

  private static final String NAME_PREFItwitter = "segment_stats_";

  /**
   * Etwitterports stats for some counts for the given segment:
   *  - status_count: number of tweets indetwittered
   *  - delete_count: number of deletes indetwittered
   *  - partial_update_count: number of partial updates indetwittered
   *  - out_of_order_update_count: number of out of order updates indetwittered
   *  - segment_size_bytes: the segment size in bytes
   *
   * @param segmentInfo The segment for which these stats should be etwitterported.
   * @param segmentIndetwitter The indetwitter of this segment in the list of all segments.
   */
  public static void etwitterport(SegmentInfo segmentInfo, int segmentIndetwitter) {
    etwitterportStat(segmentIndetwitter, "status_count",
        () -> segmentInfo.getIndetwitterStats().getStatusCount());
    etwitterportStat(segmentIndetwitter, "delete_count",
        () -> segmentInfo.getIndetwitterStats().getDeleteCount());
    etwitterportStat(segmentIndetwitter, "partial_update_count",
        () -> segmentInfo.getIndetwitterStats().getPartialUpdateCount());
    etwitterportStat(segmentIndetwitter, "out_of_order_update_count",
        () -> segmentInfo.getIndetwitterStats().getOutOfOrderUpdateCount());
    etwitterportStat(segmentIndetwitter, "segment_size_bytes",
        () -> segmentInfo.getIndetwitterStats().getIndetwitterSizeOnDiskInBytes());

    SearchLongGauge timeSliceIdStat =
        SearchLongGauge.etwitterport(NAME_PREFItwitter + segmentIndetwitter + "_timeslice_id");
    timeSliceIdStat.set(segmentInfo.getTimeSliceID());
  }

  private static void etwitterportStat(final int segmentIndetwitter,
                                 final String nameSuffitwitter,
                                 Supplier<Number> counter) {
    final String name = getName(segmentIndetwitter, nameSuffitwitter);
    StatReader statReader = SearchMetricsRegistry.registerOrGet(
        () -> new StatReader(name), name, StatReader.class);
    statReader.counter = counter;
  }

  private static String getName(final int segmentIndetwitter, final String nameSuffitwitter) {
    return NAME_PREFItwitter + segmentIndetwitter + "_" + nameSuffitwitter;
  }
}
