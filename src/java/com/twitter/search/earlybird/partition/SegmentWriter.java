package com.twitter.search.earlybird.partition;

import java.io.IOEtwitterception;
import java.util.EnumMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.metrics.Percentile;
import com.twitter.search.common.metrics.PercentileUtil;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.metrics.SearchTimer;
import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.common.partitioning.snowflakeparser.SnowflakeIdParser;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEvent;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEventType;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.document.DocumentFactory;
import com.twitter.search.earlybird.document.TweetDocument;
import com.twitter.search.earlybird.indetwitter.EarlybirdSegment;
import com.twitter.util.Time;

public class SegmentWriter implements ISegmentWriter {

  // helper, used for collecting stats
  enum FailureReason {
    FAILED_INSERT,
    FAILED_FOR_TWEET_IN_INDEtwitter,
    FAILED_FOR_COMPLETE_SEGMENT
  }

  private static final String STAT_PREFItwitter = "segment_writer_";
  private static final String EVENT_COUNTER = STAT_PREFItwitter + "%s_%s_segment_%s";
  private static final String EVENT_COUNTER_ALL_SEGMENTS = STAT_PREFItwitter + "%s_%s_all_segments";
  private static final String EVENT_TIMERS = STAT_PREFItwitter + "%s_timing";
  private static final String DROPPED_UPDATES_FOR_DISABLED_SEGMENTS =
      STAT_PREFItwitter + "%s_dropped_updates_for_disabled_segments";
  private static final String INDEtwitterING_LATENCY =
      STAT_PREFItwitter + "%s_indetwittering_latency_ms";

  private final byte penguinVersion;
  private final DocumentFactory<ThriftIndetwitteringEvent> updateFactory;
  private final DocumentFactory<ThriftIndetwitteringEvent> documentFactory;
  private final SearchRateCounter missingPenguinVersion;
  private final EarlybirdSegment earlybirdSegment;
  private final SegmentInfo segmentInfo;
  // Stores per segment counters for each (indetwittering event type, result) pair
  // Etwitterample stat name
  // "segment_writer_partial_update_success_segment_twttr_search_test_start_%d_p_0_of_1"
  private final Table<ThriftIndetwitteringEventType, Result, SearchRateCounter> statsForUpdateType =
      HashBasedTable.create();
  // Stores aggregated counters for each (indetwittering event type, result) pair across all segments
  // Etwitterample stat name
  // "segment_writer_partial_update_success_all_segments"
  private final Table<ThriftIndetwitteringEventType, Result, SearchRateCounter>
      aggregateStatsForUpdateType = HashBasedTable.create();
  // Stores per segment counters for each (indetwittering event type, non-retryable failure reason) pair
  // Etwitterample stat name
  // "segment_writer_partial_update_failed_for_tweet_in_indetwitter_segment_twttr_search_t_%d_p_0_of_1"
  private final Table<ThriftIndetwitteringEventType, FailureReason, SearchRateCounter>
      failureStatsForUpdateType = HashBasedTable.create();
  // Stores aggregated counters for each (indetwittering event type, non-retryable failure reason) pair
  // Etwitterample stat name
  // "segment_writer_partial_update_failed_for_tweet_in_indetwitter_all_segments"
  private final Table<ThriftIndetwitteringEventType, FailureReason, SearchRateCounter>
      aggregateFailureStatsForUpdateType = HashBasedTable.create();
  private final EnumMap<ThriftIndetwitteringEventType, SearchTimerStats> eventTimers =
      new EnumMap<>(ThriftIndetwitteringEventType.class);
  private final EnumMap<ThriftIndetwitteringEventType, SearchRateCounter>
    droppedUpdatesForDisabledSegments = new EnumMap<>(ThriftIndetwitteringEventType.class);
  // We pass this stat from the SearchIndetwitteringMetricSet so that we can share the atomic longs
  // between all SegmentWriters and etwitterport the largest freshness value across all segments.
  private final EnumMap<ThriftIndetwitteringEventType, AtomicLong> updateFreshness;
  private final EnumMap<ThriftIndetwitteringEventType, Percentile<Long>> indetwitteringLatency =
      new EnumMap<>(ThriftIndetwitteringEventType.class);

  public SegmentWriter(
      SegmentInfo segmentInfo,
      EnumMap<ThriftIndetwitteringEventType, AtomicLong> updateFreshness
  ) {
    this.segmentInfo = segmentInfo;
    this.updateFreshness = updateFreshness;
    this.earlybirdSegment = segmentInfo.getIndetwitterSegment();
    this.penguinVersion = EarlybirdConfig.getPenguinVersionByte();
    this.updateFactory = segmentInfo.getEarlybirdIndetwitterConfig().createUpdateFactory();
    this.documentFactory = segmentInfo.getEarlybirdIndetwitterConfig().createDocumentFactory();

    String segmentName = segmentInfo.getSegmentName();
    for (ThriftIndetwitteringEventType type : ThriftIndetwitteringEventType.values()) {
      for (Result result : Result.values()) {
        String stat = String.format(EVENT_COUNTER, type, result, segmentName).toLowerCase();
        statsForUpdateType.put(type, result, SearchRateCounter.etwitterport(stat));

        String aggregateStat =
            String.format(EVENT_COUNTER_ALL_SEGMENTS, type, result).toLowerCase();
        aggregateStatsForUpdateType.put(type, result, SearchRateCounter.etwitterport(aggregateStat));
      }

      for (FailureReason reason : FailureReason.values()) {
        String stat = String.format(EVENT_COUNTER, type, reason, segmentName).toLowerCase();
        failureStatsForUpdateType.put(type, reason, SearchRateCounter.etwitterport(stat));

        String aggregateStat =
            String.format(EVENT_COUNTER_ALL_SEGMENTS, type, reason).toLowerCase();
        aggregateFailureStatsForUpdateType.put(
            type, reason, SearchRateCounter.etwitterport(aggregateStat));
      }

      eventTimers.put(type, SearchTimerStats.etwitterport(
          String.format(EVENT_TIMERS, type).toLowerCase(),
          TimeUnit.MICROSECONDS,
          false));
      droppedUpdatesForDisabledSegments.put(
          type,
          SearchRateCounter.etwitterport(
              String.format(DROPPED_UPDATES_FOR_DISABLED_SEGMENTS, type).toLowerCase()));
      indetwitteringLatency.put(
          type,
           PercentileUtil.createPercentile(
              String.format(INDEtwitterING_LATENCY, type).toLowerCase()));
    }

    this.missingPenguinVersion = SearchRateCounter.etwitterport(
        "documents_without_current_penguin_version_" + penguinVersion + "_" + segmentName);
  }

  @Override
  public synchronized Result indetwitterThriftVersionedEvents(ThriftVersionedEvents tve)
      throws IOEtwitterception {
    if (!tve.getVersionedEvents().containsKey(penguinVersion)) {
      missingPenguinVersion.increment();
      return Result.FAILURE_NOT_RETRYABLE;
    }

    ThriftIndetwitteringEvent tie = tve.getVersionedEvents().get(penguinVersion);
    ThriftIndetwitteringEventType eventType = tie.getEventType();

    if (!segmentInfo.isEnabled()) {
      droppedUpdatesForDisabledSegments.get(eventType).increment();
      return Result.SUCCESS;
    }

    SearchTimerStats timerStats = eventTimers.get(eventType);
    SearchTimer timer = timerStats.startNewTimer();

    long tweetId = tve.getId();
    Result result = tryApplyIndetwitteringEvent(tweetId, tie);

    if (result == Result.SUCCESS) {
      long tweetAgeInMs = SnowflakeIdParser.getTimestampFromTweetId(tweetId);

      AtomicLong freshness = updateFreshness.get(tie.getEventType());
      // Note that this is racy at startup because we don't do an atomic swap, but it will be
      // approtwitterimately accurate, and this stat doesn't matter until we are current.
      if (freshness.get() < tweetAgeInMs) {
        freshness.set(tweetAgeInMs);
      }

      if (tie.isSetCreateTimeMillis()) {
        long age = Time.now().inMillis() - tie.getCreateTimeMillis();
        indetwitteringLatency.get(tie.getEventType()).record(age);
      }
    }

    statsForUpdateType.get(eventType, result).increment();
    aggregateStatsForUpdateType.get(eventType, result).increment();
    timerStats.stopTimerAndIncrement(timer);

    return result;
  }

  public SegmentInfo getSegmentInfo() {
    return segmentInfo;
  }

  public boolean hasTweet(long tweetId) throws IOEtwitterception {
    return earlybirdSegment.hasDocument(tweetId);
  }

  private Result tryApplyIndetwitteringEvent(long tweetId, ThriftIndetwitteringEvent tie) throws IOEtwitterception {
    if (applyIndetwitteringEvent(tie, tweetId)) {
      return Result.SUCCESS;
    }

    if (tie.getEventType() == ThriftIndetwitteringEventType.INSERT) {
      // We don't retry inserts
      incrementFailureStats(tie, FailureReason.FAILED_INSERT);
      return Result.FAILURE_NOT_RETRYABLE;
    }

    if (earlybirdSegment.hasDocument(tweetId)) {
      // An update fails to be applied for a tweet that is in the indetwitter.
      incrementFailureStats(tie, FailureReason.FAILED_FOR_TWEET_IN_INDEtwitter);
      return Result.FAILURE_NOT_RETRYABLE;
    }

    if (segmentInfo.isComplete()) {
      // An update is directed at a tweet that is not in the segment (hasDocument(tweetId) failed),
      // and the segment is complete (i.e. there will never be new tweets for this segment).
      incrementFailureStats(tie, FailureReason.FAILED_FOR_COMPLETE_SEGMENT);
      return Result.FAILURE_NOT_RETRYABLE;
    }

    // The tweet may arrive later for this event, so it's possible a later try will succeed
    return Result.FAILURE_RETRYABLE;
  }

  private void incrementFailureStats(ThriftIndetwitteringEvent tie, FailureReason failureReason) {
    failureStatsForUpdateType.get(tie.getEventType(), failureReason).increment();
    aggregateFailureStatsForUpdateType.get(tie.getEventType(), failureReason).increment();
  }

  private boolean applyIndetwitteringEvent(ThriftIndetwitteringEvent tie, long tweetId) throws IOEtwitterception {
    switch (tie.getEventType()) {
      case OUT_OF_ORDER_APPEND:
        return earlybirdSegment.appendOutOfOrder(updateFactory.newDocument(tie), tweetId);
      case PARTIAL_UPDATE:
        return earlybirdSegment.applyPartialUpdate(tie);
      case DELETE:
        return earlybirdSegment.delete(tweetId);
      case INSERT:
        earlybirdSegment.addDocument(buildInsertDocument(tie, tweetId));
        return true;
      default:
        throw new IllegalArgumentEtwitterception("Unetwitterpected update type: " + tie.getEventType());
    }
  }

  private TweetDocument buildInsertDocument(ThriftIndetwitteringEvent tie, long tweetId) {
    return new TweetDocument(
        tweetId,
        segmentInfo.getTimeSliceID(),
        tie.getCreateTimeMillis(),
        documentFactory.newDocument(tie));
  }
}
