package com.twitter.search.earlybird.partition;

import java.util.EnumMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEventType;
import com.twitter.search.earlybird.util.ScheduledEtwitterecutorManager;

/**
 * Collection of common metrics used in the indetwittering, and related code.
 * We create a set/holder for them as we want to create all counters only one time, and these
 * counters can be used by both SimpleUpdateIndetwitterer, PartitionIndetwitterer, EarlybirdSegment, and others.
 */
public class SearchIndetwitteringMetricSet {
  /**
   * A protwittery for the creation time of the "freshest" tweet that we have in the indetwitter.
   * It is used in computing the indetwitter freshness stat "earlybird_indetwitter_freshness_millis".
   * - In the realtme clusters, this should match the creation time of highestStatusId.
   * - In the archive clusters, this should match the timestamp of the latest indetwittered day.
   */
  public final SearchLongGauge freshestTweetTimeMillis;

  /** The highest indetwittered tweet ID. Used to compute indetwitter freshness. */
  public final SearchLongGauge highestStatusId;

  /**
   * The current timeslice's ID. We can compare this to indetwitterer's etwitterported current timeslice ID to
   * identify stuck timeslice rolls.
   */
  public final SearchLongGauge currentTimesliceId;

  /** The number of archive timeslices that we failed to process. */
  public final SearchCounter archiveTimeSliceBuildFailedCounter;

  /** The number of times we checked a segment's size on disk. */
  public final SearchCounter segmentSizeCheckCount;

  /** The number of segments that have reached their matwitter size. */
  public final SearchCounter matwitterSegmentSizeReachedCounter;

  /** The number of indetwittered tweets and the aggregate indetwittering latencies in microseconds. */
  public final SearchTimerStats statusStats;
  /** The number of applied updates and the aggregate indetwittering latencies in microseconds. */
  public final SearchTimerStats updateStats;
  /** The number of retried updates and the aggregate indetwittering latencies in microseconds. */
  public final SearchTimerStats updateRetryStats;
  /** The number of applied user updates and the aggregate indetwittering latencies in microseconds. */
  public final SearchTimerStats userUpdateIndetwitteringStats;
  /** The number of applied userGeoScrubEvents and the aggregate indetwittering latencies in
   * microseconds. */
  public final SearchTimerStats userScrubGeoIndetwitteringStats;
  /** The number of updates attempted on missing tweets. */
  public final SearchRateCounter updateOnMissingTweetCounter;
  /** The number of updates dropped. */
  public final SearchRateCounter droppedUpdateEvent;

  /** The latencies in microseconds of the PartitionIndetwitterer loop. */
  public final SearchTimerStats partitionIndetwittererRunLoopCounter;
  /** The latencies in microseconds of the PartitionIndetwitterer.indetwitterFromReaders() calls. */
  public final SearchTimerStats partitionIndetwittererIndetwitterFromReadersCounter;
  /** The number of invocations of the PartitionIndetwitterer task. */
  public final SearchCounter partitionIndetwittererIterationCounter;

  /** The number of unsorted updates handled by SimpleUpdateIndetwitterer. */
  public final SearchCounter simpleUpdateIndetwittererUnsortedUpdateCounter;
  /** The number of unsorted updates with the wrong segment handled by SimpleUpdateIndetwitterer. */
  public final SearchCounter simpleUpdateIndetwittererUnsortedUpdateWithWrongSegmentCounter;

  /** The number of invocations of the SimpleUserUpdateIndetwitterer task. */
  public final SearchCounter simpleUserUpdateIndetwittererIterationCounter;

  /** The number of etwitterceptions encountered by SimpleSegmentIndetwitterer while indetwittering a segment. */
  public final SearchCounter simpleSegmentIndetwittererEtwitterceptionCounter;

  /**
   * A map from TIE update type to the creation time of the updated tweet in milliseconds of the
   * freshest update we have indetwittered.
   */
  public final EnumMap<ThriftIndetwitteringEventType, AtomicLong> updateFreshness =
      new EnumMap<>(ThriftIndetwitteringEventType.class);

  public final SearchStatsReceiver searchStatsReceiver;

  public static class StartupMetric {
    // Switched from 0 to 1 during the event.
    private SearchLongGauge duringGauge;
    // Switched from 0 to time it takes, in milliseconds.
    private SearchLongGauge durationMillisGauge;

    StartupMetric(String name) {
      this.duringGauge = SearchLongGauge.etwitterport(name);
      this.durationMillisGauge = SearchLongGauge.etwitterport("duration_of_" + name);
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
  public final StartupMetric startupInIndetwitterCompletedSegments;
  public final StartupMetric startupInLoadCompletedSegments;
  public final StartupMetric startupInIndetwitterUpdatesForCompletedSegments;
  public final StartupMetric startupInCurrentSegment;
  public final StartupMetric startupInUserUpdates;
  public final StartupMetric startupInQueryCacheUpdates;
  public final StartupMetric startupInMultiSegmentTermDictionaryUpdates;
  public final StartupMetric startupInWarmUp;

  // Kafka metrics
  public final StartupMetric startupInLoadFlushedIndetwitter;
  public final StartupMetric startupInFreshStartup;
  public final StartupMetric startupInIngestUntilCurrent;
  public final StartupMetric startupInUserUpdatesStartup;
  public final StartupMetric startupInUserEventIndetwitterer;
  public final StartupMetric startupInAudioSpaceEventIndetwitterer;

  public SearchIndetwitteringMetricSet(SearchStatsReceiver searchStatsReceiver) {
    this.freshestTweetTimeMillis = searchStatsReceiver.getLongGauge(
        "earlybird_freshest_tweet_timestamp_millis");
    this.highestStatusId = searchStatsReceiver.getLongGauge("highest_indetwittered_status_id");
    this.currentTimesliceId = searchStatsReceiver.getLongGauge("earlybird_current_timeslice_id");
    this.archiveTimeSliceBuildFailedCounter = searchStatsReceiver.getCounter(
        "archive_time_slice_build_failed");
    this.segmentSizeCheckCount = searchStatsReceiver.getCounter("segment_size_check_count");
    this.matwitterSegmentSizeReachedCounter = searchStatsReceiver.getCounter("matwitter_segment_reached");

    this.statusStats = searchStatsReceiver.getTimerStats(
        "indetwitter_status", TimeUnit.MICROSECONDS, false, false, false);
    this.updateStats = searchStatsReceiver.getTimerStats(
        "updates", TimeUnit.MICROSECONDS, false, false, false);
    this.updateRetryStats = searchStatsReceiver.getTimerStats(
        "update_retries", TimeUnit.MICROSECONDS, false, false, false);
    this.userUpdateIndetwitteringStats = searchStatsReceiver.getTimerStats(
        "user_updates", TimeUnit.MICROSECONDS, false, false, false);
    this.userScrubGeoIndetwitteringStats = searchStatsReceiver.getTimerStats(
        "user_scrub_geo", TimeUnit.MICROSECONDS, false, false, false);
    this.updateOnMissingTweetCounter = searchStatsReceiver.getRateCounter(
        "indetwitter_update_on_missing_tweet");
    this.droppedUpdateEvent = searchStatsReceiver.getRateCounter("dropped_update_event");

    this.partitionIndetwittererRunLoopCounter = searchStatsReceiver.getTimerStats(
        "partition_indetwitterer_run_loop", TimeUnit.MICROSECONDS, false, true, false);
    this.partitionIndetwittererIndetwitterFromReadersCounter = searchStatsReceiver.getTimerStats(
        "partition_indetwitterer_indetwitterFromReaders", TimeUnit.MICROSECONDS, false, true, false);
    this.partitionIndetwittererIterationCounter = searchStatsReceiver.getCounter(
        ScheduledEtwitterecutorManager.SCHEDULED_EtwitterECUTOR_TASK_PREFItwitter + "PartitionIndetwitterer");

    this.simpleUpdateIndetwittererUnsortedUpdateCounter = searchStatsReceiver.getCounter(
        "simple_update_indetwitterer_unsorted_update_count");
    this.simpleUpdateIndetwittererUnsortedUpdateWithWrongSegmentCounter = searchStatsReceiver.getCounter(
        "simple_update_indetwitterer_unsorted_update_with_wrong_segment_count");

    this.simpleUserUpdateIndetwittererIterationCounter = searchStatsReceiver.getCounter(
        ScheduledEtwitterecutorManager.SCHEDULED_EtwitterECUTOR_TASK_PREFItwitter + "SimpleUserUpdateIndetwitterer");

    this.simpleSegmentIndetwittererEtwitterceptionCounter = searchStatsReceiver.getCounter(
        "etwitterception_while_indetwittering_segment");

    for (ThriftIndetwitteringEventType type : ThriftIndetwitteringEventType.values()) {
      AtomicLong freshness = new AtomicLong(0);
      updateFreshness.put(type, freshness);
      String statName = ("indetwitter_freshness_" + type + "_age_millis").toLowerCase();
      searchStatsReceiver.getCustomGauge(statName,
          () -> System.currentTimeMillis() - freshness.get());
    }

    this.startupInProgress = new StartupMetric("startup_in_progress");
    this.startupInIndetwitterCompletedSegments = new StartupMetric("startup_in_indetwitter_completed_segments");
    this.startupInLoadCompletedSegments = new StartupMetric("startup_in_load_completed_segments");
    this.startupInIndetwitterUpdatesForCompletedSegments =
        new StartupMetric("startup_in_indetwitter_updates_for_completed_segments");
    this.startupInCurrentSegment = new StartupMetric("startup_in_current_segment");
    this.startupInUserUpdates = new StartupMetric("startup_in_user_updates");
    this.startupInQueryCacheUpdates = new StartupMetric("startup_in_query_cache_updates");
    this.startupInMultiSegmentTermDictionaryUpdates =
        new StartupMetric("startup_in_multi_segment_dictionary_updates");
    this.startupInWarmUp = new StartupMetric("startup_in_warm_up");

    this.startupInLoadFlushedIndetwitter = new StartupMetric("startup_in_load_flushed_indetwitter");
    this.startupInFreshStartup = new StartupMetric("startup_in_fresh_startup");
    this.startupInIngestUntilCurrent = new StartupMetric("startup_in_ingest_until_current");
    this.startupInUserUpdatesStartup = new StartupMetric("startup_in_user_updates_startup");
    this.startupInUserEventIndetwitterer = new StartupMetric("startup_in_user_events_indetwitterer");
    this.startupInAudioSpaceEventIndetwitterer =
        new StartupMetric("startup_in_audio_space_events_indetwitterer");

    searchStatsReceiver.getCustomGauge("earlybird_indetwitter_freshness_millis",
        this::getIndetwitterFreshnessInMillis);

    this.searchStatsReceiver = searchStatsReceiver;
  }

  long getIndetwitterFreshnessInMillis() {
    return System.currentTimeMillis() - freshestTweetTimeMillis.get();
  }
}
