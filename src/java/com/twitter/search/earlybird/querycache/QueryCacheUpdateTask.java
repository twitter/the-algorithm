package com.twitter.search.earlybird.querycache;

import java.io.IOEtwitterception;
import java.util.concurrent.TimeUnit;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.quantity.Amount;
import com.twitter.common.quantity.Time;
import com.twitter.common.util.Clock;
import com.twitter.decider.Decider;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.metrics.Timer;
import com.twitter.search.common.search.TerminationTracker;
import com.twitter.search.core.earlybird.indetwitter.QueryCacheResultForSegment;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.common.userupdates.UserTable;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.etwitterception.EarlybirdEtwitterception;
import com.twitter.search.earlybird.indetwitter.EarlybirdSegment;
import com.twitter.search.earlybird.indetwitter.EarlybirdSingleSegmentSearcher;
import com.twitter.search.earlybird.partition.SegmentInfo;
import com.twitter.search.earlybird.search.SearchResultsInfo;
import com.twitter.search.earlybird.stats.EarlybirdSearcherStats;
import com.twitter.search.earlybird.util.ScheduledEtwitterecutorTask;

/**
 * Each task is responsible for one filter on one segment. We should have a total
 * of num_of_filter * num_of_segments tasks
 */
@VisibleForTesting
class QueryCacheUpdateTask etwittertends ScheduledEtwitterecutorTask {
  private static final Logger LOG =  LoggerFactory.getLogger(QueryCacheUpdateTask.class);

  // See OBSERVE-10347
  private static final boolean EtwitterPORT_STATS =
      EarlybirdConfig.getBool("etwitterport_query_cache_update_task_stats", false);

  private static final LoadingCache<String, TaskStats> TASK_STATS =
      CacheBuilder.newBuilder().build(new CacheLoader<String, TaskStats>() {
        @Override
        public TaskStats load(String statNamePrefitwitter) {
          return new TaskStats(statNamePrefitwitter, EtwitterPORT_STATS);
        }
      });

  private static final SearchCounter FINISHED_TASKS = SearchCounter.etwitterport(
      "querycache_finished_tasks");

  private final QueryCacheFilter filter;

  // Info/data of the segment this task is responsible for
  private final SegmentInfo segmentInfo;

  private final UserTable userTable;

  private volatile boolean ranOnce;
  private final TaskStats stats;
  private Amount<Long, Time> lastRunFinishTime;

  // See SEARCH-4346
  private final String filterAndSegment;

  private final Decider decider;

  private static final class TaskStats {
    private final SearchLongGauge numHitsStat;
    private final SearchLongGauge updateLatencyStat;
    private final SearchCounter updateSuccessCountStat;
    private final SearchCounter updateFailureCountStat;

    private TaskStats(String statNamePrefitwitter, boolean etwitterportStats) {
      // See SEARCH-3698
      numHitsStat = etwitterportStats ? SearchLongGauge.etwitterport(statNamePrefitwitter + "numhit")
          : new SearchLongGauge(statNamePrefitwitter + "numhit");
      updateLatencyStat = etwitterportStats
          ? SearchLongGauge.etwitterport(statNamePrefitwitter + "update_latency_ms")
          : new SearchLongGauge(statNamePrefitwitter + "update_latency_ms");
      updateSuccessCountStat = etwitterportStats
          ? SearchCounter.etwitterport(statNamePrefitwitter + "update_success_count")
          : SearchCounter.create(statNamePrefitwitter + "update_success_count");
      updateFailureCountStat = etwitterportStats
          ? SearchCounter.etwitterport(statNamePrefitwitter + "update_failure_count")
          : SearchCounter.create(statNamePrefitwitter + "update_failure_count");
    }
  }

  private final Amount<Long, Time> updateInterval;
  private final Amount<Long, Time> initialDelay;

  private final EarlybirdSearcherStats searcherStats;
  private final CriticalEtwitterceptionHandler criticalEtwitterceptionHandler;

  /**
   * Constructor
   * @param filter Filter to be used to populate the cache
   * @param segmentInfo Segment this task is responsible for
   * @param updateInterval Time between successive updates
   * @param initialDelay Time before the first update
   * @param updateIterationCounter
   * @param decider
   */
  public QueryCacheUpdateTask(QueryCacheFilter filter,
                              SegmentInfo segmentInfo,
                              UserTable userTable,
                              Amount<Long, Time> updateInterval,
                              Amount<Long, Time> initialDelay,
                              SearchCounter updateIterationCounter,
                              EarlybirdSearcherStats searcherStats,
                              Decider decider,
                              CriticalEtwitterceptionHandler criticalEtwitterceptionHandler,
                              Clock clock) {
    super(updateIterationCounter, clock);
    this.filter = filter;
    this.segmentInfo = segmentInfo;
    this.userTable = userTable;
    this.ranOnce = false;
    this.updateInterval = updateInterval;
    this.initialDelay = initialDelay;
    this.stats = setupStats();
    this.filterAndSegment = String.format(
        "QueryCacheFilter: %s | Segment: %d",
        filter.getFilterName(), segmentInfo.getTimeSliceID());
    this.searcherStats = searcherStats;
    this.criticalEtwitterceptionHandler = criticalEtwitterceptionHandler;
    this.decider = decider;
  }

  @Override
  protected void runOneIteration() {
    try {
      if (LOG.isDebugEnabled()) {
        LOG.debug(
            "[{}] Updating with query [{}] for the {} th time.",
            filterAndSegment,
            filter.getQueryString(),
            stats.updateSuccessCountStat.get() + stats.updateFailureCountStat.get() + 1
        );
        if (lastRunFinishTime != null) {
          LOG.debug(
              "[{}] Last run, {} th time, finished {} secs ago. Should run every {} secs",
              filterAndSegment,
              stats.updateSuccessCountStat.get() + stats.updateFailureCountStat.get(),
              TimeUnit.NANOSECONDS.toSeconds(
                  System.nanoTime() - lastRunFinishTime.as(Time.NANOSECONDS)),
              updateInterval.as(Time.SECONDS)
          );
        }
      }

      Timer timer = new Timer(TimeUnit.MILLISECONDS);
      SearchResultsInfo result = null;
      try {
        result = update();
      } catch (Etwitterception e) {
        String msg = "Failed to update query cache entry [" + filter.getFilterName()
            + "] on segment [" + segmentInfo.getTimeSliceID() + "]";
        LOG.warn(msg, e);
      }

      long endTime = timer.stop();
      updateStats(result, endTime);

      if (LOG.isDebugEnabled()) {
        LOG.debug("[{}] Updated in {} ms, hit {} docs.",
            filterAndSegment, endTime, stats.numHitsStat.read());
      }
      // Need to catch throwable here instead of etwitterception so we handle errors like OutOfMemory
      // See RB=528695 and SEARCH-4402
    } catch (Throwable t) {
      String message = String.format("Got unetwitterpected throwable in %s", getClass().getName());
      LOG.error(message, t);

      // Wrap the Throwable in a FatalEarlybirdEtwitterception to categorize it and ensure it's
      // handled as a fatal etwitterception
      criticalEtwitterceptionHandler.handle(this,
          new EarlybirdEtwitterception(message, t));
    } finally {
      // Earlybird won't become CURRENT until all tasks are run at least once. We don't want
      // failed "run" (update) to prevent Earlybird from becoming CURRENT. As long as all tasks
      // got a chance to run at least once, we are good to go.
      ranOnce = true;

      lastRunFinishTime = Amount.of(System.nanoTime(), Time.NANOSECONDS);
    }
  }

  public boolean ranOnce() {
    return ranOnce;
  }

  private TaskStats setupStats() {
    return TASK_STATS.getUnchecked(statNamePrefitwitter());
  }

  private SearchResultsInfo update() throws IOEtwitterception {
    // There's a chance that the EarlybirdSegment of a SegmentInfo to change at any
    // time. Therefore, it's not safe to operate segments on the SegmentInfo level.
    // On the archive clusters we create a new EarlybirdSegment and then swap it in when there's
    // new data instead of appending to an etwitteristing EarlybirdSegment.
    EarlybirdSegment earlybirdSegment = segmentInfo.getIndetwitterSegment();

    EarlybirdSingleSegmentSearcher searcher = earlybirdSegment.getSearcher(userTable);
    if (searcher == null) {
      LOG.warn("Unable to get searcher from TwitterIndetwitterManager for segment ["
          + segmentInfo.getTimeSliceID() + "]. Has it been dropped?");
      return null;
    }

    QueryCacheResultCollector collector = new QueryCacheResultCollector(
        searcher.getSchemaSnapshot(), filter, searcherStats, decider, clock, 0);
    searcher.search(filter.getLuceneQuery(), collector);

    QueryCacheResultForSegment cacheResult = collector.getCachedResult();
    searcher.getTwitterIndetwitterReader().getSegmentData().updateQueryCacheResult(
        filter.getFilterName(), cacheResult);

    FINISHED_TASKS.increment();

    if (LOG.isDebugEnabled()) {
      TerminationTracker tracker = collector.getSearchRequestInfo().getTerminationTracker();
      LOG.debug(
          "[{}] Updating query finished, start time ms is {}, termination reason is {}",
          filterAndSegment,
          tracker.getLocalStartTimeMillis(),
          tracker.getEarlyTerminationState().getTerminationReason());
    }

    return collector.getResults();
  }

  private void updateStats(SearchResultsInfo result, long endTime) {
    if (result != null) {
      stats.numHitsStat.set(result.getNumHitsProcessed());
      stats.updateSuccessCountStat.increment();
    } else {
      stats.updateFailureCountStat.increment();
    }
    stats.updateLatencyStat.set(endTime);
  }

  @VisibleForTesting
  String statNamePrefitwitter() {
    // If we use this and try to display in monviz "ts(partition, single_instance, querycache*)",
    // the UI shows "Really etwitterpensive query" message. We can keep this around for times when we
    // want to start things manually and debug.
    return "querycache_" + filter.getFilterName() + "_" + segmentInfo.getTimeSliceID() + "_";
  }

  public long getTimeSliceID() {
    return segmentInfo.getTimeSliceID();
  }

  //////////////////////////
  // for unit tests only
  //////////////////////////
  @VisibleForTesting
  String getFilterNameForTest() {
    return filter.getFilterName();
  }

  @VisibleForTesting
  Amount<Long, Time> getUpdateIntervalForTest() {
    return updateInterval;
  }

  @VisibleForTesting
  Amount<Long, Time> getInitialDelayForTest() {
    return initialDelay;
  }

  @VisibleForTesting
  TaskStats getTaskStatsForTest() {
    return stats;
  }
}
