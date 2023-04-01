package com.twitter.search.earlybird.partition;

import java.util.EnumMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.common.schema.thriftjava.ThriftIndexingEventType;
import com.twitter.search.earlybird.util.ScheduledExecutorManager;

/**
 * Collection of common metrics used in the indexing, and related code.
 * We create a set/holder for them as we want to create all counters only one time, and these
 * counters can be used by both SimpleUpdateIndexer, PartitionIndexer, EarlybirdSegment, and others.
 */
public class SearchIndexingMetricSet {
  /**
   * A proxy for the creation time of the "freshest" tweet that we have in the index.
   * It is used in computing the index freshness stat "earlybird_index_freshness_millis".
   * - In the realtme clusters, this should match the creation time of highestStatusId.
   * - In the archive clusters, this should match the timestamp of the latest indexed day.
   */
  public final SearchLongGauge freshestTweetTimeMillis;

  /** The highest indexed tweet ID. Used to compute index freshness. */
  public final SearchLongGauge highestStatusId;

  /**
   * The current timeslice's ID. We can compare this to indexer's exported current timeslice ID to
   * identify stuck timeslice rolls.
   */
  public final SearchLongGauge currentTimesliceId;

  /** The number of archive timeslices that we failed to process. */
  public final SearchCounter archiveTimeSliceBuildFailedCounter;

  /** The number of times we checked a segment's size on disk. */
  public final SearchCounter segmentSizeCheckCount;

  /** The number of segments that have reached their max size. */
  public final SearchCounter maxSegmentSizeReachedCounter;

  /** The number of indexed tweets and the aggregate indexing latencies in microseconds. */
  public final SearchTimerStats statusStats;
  /** The number of applied updates and the aggregate indexing latencies in microseconds. */
  public final SearchTimerStats updateStats;
  /** The number of retried updates and the aggregate indexing latencies in microseconds. */
  public final SearchTimerStats updateRetryStats;
  /** The number of applied user updates and the aggregate indexing latencies in microseconds. */
  public final SearchTimerStats userUpdateIndexingStats;
  /** The number of applied userGeoScrubEvents and the aggregate indexing latencies in
   * microseconds. */
  public final SearchTimerStats userScrubGeoIndexingStats;
  /** The number of updates attempted on missing tweets. */
  public final SearchRateCounter updateOnMissingTweetCounter;
  /** The number of updates dropped. */
  public final SearchRateCounter droppedUpdateEvent;

  /** The latencies in microseconds of the PartitionIndexer loop. */
  public final SearchTimerStats partitionIndexerRunLoopCounter;
  /** The latencies in microseconds of the PartitionIndexer.indexFromReaders() calls. */
  public final SearchTimerStats partitionIndexerIndexFromReadersCounter;
  /** The number of invocations of the PartitionIndexer task. */
  public final SearchCounter partitionIndexerIterationCounter;

  /** The number of unsorted updates handled by SimpleUpdateIndexer. */
  public final SearchCounter simpleUpdateIndexerUnsortedUpdateCounter;
  /** The number of unsorted updates with the wrong segment handled by SimpleUpdateIndexer. */
  public final SearchCounter simpleUpdateIndexerUnsortedUpdateWithWrongSegmentCounter;

  /** The number of invocations of the SimpleUserUpdateIndexer task. */
  public final SearchCounter simpleUserUpdateIndexerIterationCounter;

  /** The number of exceptions encountered by SimpleSegmentIndexer while indexing a segment. */
  public final SearchCounter simpleSegmentIndexerExceptionCounter;

  /**
   * A map from TIE update type to the creation time of the updated tweet in milliseconds of the
   * freshest update we have indexed.
   */
  public final EnumMap<ThriftIndexingEventType, AtomicLong> updateFreshness =
      new EnumMap<>(ThriftIndexingEventType.class);

  public final SearchStatsReceiver searchStatsReceiver;

  public static class StartupMetric {
    // Switched from 0 to 1 during the event.
    private SearchLongGauge duringGauge;
    // Switched from 0 to time it takes, in milliseconds.
    private SearchLongGauge durationMillisGauge;

    StartupMetric(String name) {
      this.duringGauge = SearchLongGauge.export(name);
      this.durationMillisGauge = SearchLongGauge.export("duration_of_" + name);
    }

    public void begin() {
      duringGauge.set(1);
    }

    public void end(long durationInMillis) {
      duringGauge.set(0);
      durationMillisGauge.set(durationInMillis);
    }
  }

  public final StartupMetric startupInProgress;
  public final StartupMetric startupInIndexCompletedSegments;
  public final StartupMetric startupInLoadCompletedSegments;
  public final StartupMetric startupInIndexUpdatesForCompletedSegments;
  public final StartupMetric startupInCurrentSegment;
  public final StartupMetric startupInUserUpdates;
  public final StartupMetric startupInQueryCacheUpdates;
  public final StartupMetric startupInMultiSegmentTermDictionaryUpdates;
  public final StartupMetric startupInWarmUp;

  // Kafka metrics
  public final StartupMetric startupInLoadFlushedIndex;
  public final StartupMetric startupInFreshStartup;
  public final StartupMetric startupInIngestUntilCurrent;
  public final StartupMetric startupInUserUpdatesStartup;
  public final StartupMetric startupInUserEventIndexer;
  public final StartupMetric startupInAudioSpaceEventIndexer;

  public SearchIndexingMetricSet(SearchStatsReceiver searchStatsReceiver) {
    this.freshestTweetTimeMillis = searchStatsReceiver.getLongGauge(
        "earlybird_freshest_tweet_timestamp_millis");
    this.highestStatusId = searchStatsReceiver.getLongGauge("highest_indexed_status_id");
    this.currentTimesliceId = searchStatsReceiver.getLongGauge("earlybird_current_timeslice_id");
    this.archiveTimeSliceBuildFailedCounter = searchStatsReceiver.getCounter(
        "archive_time_slice_build_failed");
    this.segmentSizeCheckCount = searchStatsReceiver.getCounter("segment_size_check_count");
    this.maxSegmentSizeReachedCounter = searchStatsReceiver.getCounter("max_segment_reached");

    this.statusStats = searchStatsReceiver.getTimerStats(
        "index_status", TimeUnit.MICROSECONDS, false, false, false);
    this.updateStats = searchStatsReceiver.getTimerStats(
        "updates", TimeUnit.MICROSECONDS, false, false, false);
    this.updateRetryStats = searchStatsReceiver.getTimerStats(
        "update_retries", TimeUnit.MICROSECONDS, false, false, false);
    this.userUpdateIndexingStats = searchStatsReceiver.getTimerStats(
        "user_updates", TimeUnit.MICROSECONDS, false, false, false);
    this.userScrubGeoIndexingStats = searchStatsReceiver.getTimerStats(
        "user_scrub_geo", TimeUnit.MICROSECONDS, false, false, false);
    this.updateOnMissingTweetCounter = searchStatsReceiver.getRateCounter(
        "index_update_on_missing_tweet");
    this.droppedUpdateEvent = searchStatsReceiver.getRateCounter("dropped_update_event");

    this.partitionIndexerRunLoopCounter = searchStatsReceiver.getTimerStats(
        "partition_indexer_run_loop", TimeUnit.MICROSECONDS, false, true, false);
    this.partitionIndexerIndexFromReadersCounter = searchStatsReceiver.getTimerStats(
        "partition_indexer_indexFromReaders", TimeUnit.MICROSECONDS, false, true, false);
    this.partitionIndexerIterationCounter = searchStatsReceiver.getCounter(
        ScheduledExecutorManager.SCHEDULED_EXECUTOR_TASK_PREFIX + "PartitionIndexer");

    this.simpleUpdateIndexerUnsortedUpdateCounter = searchStatsReceiver.getCounter(
        "simple_update_indexer_unsorted_update_count");
    this.simpleUpdateIndexerUnsortedUpdateWithWrongSegmentCounter = searchStatsReceiver.getCounter(
        "simple_update_indexer_unsorted_update_with_wrong_segment_count");

    this.simpleUserUpdateIndexerIterationCounter = searchStatsReceiver.getCounter(
        ScheduledExecutorManager.SCHEDULED_EXECUTOR_TASK_PREFIX + "SimpleUserUpdateIndexer");

    this.simpleSegmentIndexerExceptionCounter = searchStatsReceiver.getCounter(
        "exception_while_indexing_segment");

    for (ThriftIndexingEventType type : ThriftIndexingEventType.values()) {
      AtomicLong freshness = new AtomicLong(0);
      updateFreshness.put(type, freshness);
      String statName = ("index_freshness_" + type + "_age_millis").toLowerCase();
      searchStatsReceiver.getCustomGauge(statName,
          () -> System.currentTimeMillis() - freshness.get());
    }

    this.startupInProgress = new StartupMetric("startup_in_progress");
    this.startupInIndexCompletedSegments = new StartupMetric("startup_in_index_completed_segments");
    this.startupInLoadCompletedSegments = new StartupMetric("startup_in_load_completed_segments");
    this.startupInIndexUpdatesForCompletedSegments =
        new StartupMetric("startup_in_index_updates_for_completed_segments");
    this.startupInCurrentSegment = new StartupMetric("startup_in_current_segment");
    this.startupInUserUpdates = new StartupMetric("startup_in_user_updates");
    this.startupInQueryCacheUpdates = new StartupMetric("startup_in_query_cache_updates");
    this.startupInMultiSegmentTermDictionaryUpdates =
        new StartupMetric("startup_in_multi_segment_dictionary_updates");
    this.startupInWarmUp = new StartupMetric("startup_in_warm_up");

    this.startupInLoadFlushedIndex = new StartupMetric("startup_in_load_flushed_index");
    this.startupInFreshStartup = new StartupMetric("startup_in_fresh_startup");
    this.startupInIngestUntilCurrent = new StartupMetric("startup_in_ingest_until_current");
    this.startupInUserUpdatesStartup = new StartupMetric("startup_in_user_updates_startup");
    this.startupInUserEventIndexer = new StartupMetric("startup_in_user_events_indexer");
    this.startupInAudioSpaceEventIndexer =
        new StartupMetric("startup_in_audio_space_events_indexer");

    searchStatsReceiver.getCustomGauge("earlybird_index_freshness_millis",
        this::getIndexFreshnessInMillis);

    this.searchStatsReceiver = searchStatsReceiver;
  }

  long getIndexFreshnessInMillis() {
    return System.currentTimeMillis() - freshestTweetTimeMillis.get();
  }
}
