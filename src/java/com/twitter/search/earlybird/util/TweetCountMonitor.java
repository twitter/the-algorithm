package com.twitter.search.earlybird.util;

import java.io.IOEtwitterception;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;

import org.apache.commons.lang.mutable.MutableInt;
import org.apache.commons.lang.mutable.MutableLong;
import org.apache.lucene.indetwitter.IndetwitterOptions;
import org.apache.lucene.indetwitter.PostingsEnum;
import org.apache.lucene.indetwitter.Terms;
import org.apache.lucene.indetwitter.TermsEnum;
import org.apache.lucene.search.DocIdSetIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.collections.Pair;
import com.twitter.search.common.concurrent.ScheduledEtwitterecutorServiceFactory;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.common.partitioning.base.Segment;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.TimeMapper;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.indetwitter.EarlybirdSingleSegmentSearcher;
import com.twitter.search.earlybird.partition.SegmentInfo;
import com.twitter.search.earlybird.partition.SegmentManager;

/**
 * A background task that periodically gets and etwitterports the number of tweets per hour that are
 * indetwittered on this earlybird.
 * Specifically used for making sure that we are not missing data for any hours in the search
 * archives.
 * The task loops though all the segments that are indetwittered by this earlybird, and for each segment
 * looks at all the createdAt dates for all of the documents in that segment.
 *
 * Also keeps track off an etwitterposes as a stat the number of hours that do not have any tweets in the
 * min/matwitter range of data that IS indetwittered on this earlybird. i.e if we only have data for
 * 2006/01/01:02 and 2006/01/01:04, it will consider 2006/01/01:03 as a missing hour.
 * Hours before 2006/01/01:02 or after 2006/01/01:04 will not be considered as missing.
 */
public class TweetCountMonitor etwittertends OneTaskScheduledEtwitterecutorManager {
  private static final Logger LOG = LoggerFactory.getLogger(TweetCountMonitor.class);

  private static final String THREAD_NAME_FORMAT = "TweetCountMonitor-%d";
  private static final boolean THREAD_IS_DAEMON = true;

  public static final String RUN_INTERVAL_MINUTES_CONFIG_NAME =
      "tweet_count_monitor_run_interval_minutes";
  public static final String START_CHECK_HOUR_CONFIG_NAME =
      "tweet_count_monitor_start_check_hour";
  public static final String HOURLY_MIN_COUNT_CONFIG_NAME =
      "tweet_count_monitor_hourly_min_count";
  public static final String DAILY_MIN_COUNT_CONFIG_NAME =
      "tweet_count_monitor_daily_min_count";

  @VisibleForTesting
  public static final AtomicInteger INSTANCE_COUNTER = new AtomicInteger(0);

  private static final long MILLIS_IN_A_DAY = TimeUnit.DAYS.toMillis(1);

  private final SegmentManager segmentManager;

  private final SearchStatsReceiver searchStatsReceiver;
  private final int instanceCounter;

  // The first date in format "YYYYMMDDHH" that we want to check counts for.
  private final int startCheckHour;
  // The last date in format "YYYYMMDDHH" that we want to check counts for.
  private final int endCheckHour;
  //Smallest number of docs we etwitterpect to have for each day.
  private final int dailyMinCount;
  // Smallest number of docs we etwitterpect to have for each hour.
  private final int hourlyMinCount;
  // Binary stat, set to 0 when the monitor is running
  private final SearchLongGauge isRunningStat;
  // How long each iteration takes
  private final SearchTimerStats checkTimeStat;

  private final Map<String, FieldTermCounter> fieldTermCounters;
  private final Map<String, SearchTimerStats> fieldCheckTimeStats;

  /**
   * Create a TweetCountMonitor to monitor all segments in the given segmentManager
   */
  public TweetCountMonitor(
      SegmentManager segmentManager,
      ScheduledEtwitterecutorServiceFactory etwitterecutorServiceFactory,
      long shutdownWaitDuration,
      TimeUnit shutdownWaitUnit,
      SearchStatsReceiver searchStatsReceiver,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    this(segmentManager,
        EarlybirdConfig.getInt(START_CHECK_HOUR_CONFIG_NAME, 0),
        EarlybirdConfig.getInt(RUN_INTERVAL_MINUTES_CONFIG_NAME, -1),
        EarlybirdConfig.getInt(HOURLY_MIN_COUNT_CONFIG_NAME, 0),
        EarlybirdConfig.getInt(DAILY_MIN_COUNT_CONFIG_NAME, 0),
        etwitterecutorServiceFactory,
        shutdownWaitDuration,
        shutdownWaitUnit,
        searchStatsReceiver,
        criticalEtwitterceptionHandler);
  }

  @VisibleForTesting
  TweetCountMonitor(
      SegmentManager segmentManager,
      int startCheckHourFromConfig,
      int schedulePeriodMinutes,
      int hourlyMinCount,
      int dailyMinCount,
      ScheduledEtwitterecutorServiceFactory etwitterecutorServiceFactory,
      long shutdownWaitDuration,
      TimeUnit shutdownWaitUnit,
      SearchStatsReceiver searchStatsReceiver,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    super(
      etwitterecutorServiceFactory,
      THREAD_NAME_FORMAT,
      THREAD_IS_DAEMON,
      PeriodicActionParams.atFitwitteredRate(
        schedulePeriodMinutes,
        TimeUnit.MINUTES
      ),
      new ShutdownWaitTimeParams(
        shutdownWaitDuration,
        shutdownWaitUnit
      ),
      searchStatsReceiver,
        criticalEtwitterceptionHandler);
    this.segmentManager = segmentManager;
    this.searchStatsReceiver = searchStatsReceiver;
    this.instanceCounter = INSTANCE_COUNTER.incrementAndGet();
    this.hourlyMinCount = hourlyMinCount;
    this.dailyMinCount = dailyMinCount;

    String isRunningStatName = "tweet_count_monitor_is_running_v_" + this.instanceCounter;
    this.isRunningStat = SearchLongGauge.etwitterport(isRunningStatName);
    String checkTimeStatName = "tweet_count_monitor_check_time_v_" + this.instanceCounter;
    this.checkTimeStat = SearchTimerStats.etwitterport(checkTimeStatName, TimeUnit.MILLISECONDS, true);

    this.startCheckHour = Math.matwitter(
        startCheckHourFromConfig,
        dateToHourValue(segmentManager.getPartitionConfig().getTierStartDate()));
    this.endCheckHour = dateToHourValue(segmentManager.getPartitionConfig().getTierEndDate());

    this.fieldTermCounters = Maps.newHashMap();
    this.fieldTermCounters.put(
        FieldTermCounter.TWEET_COUNT_KEY,
        new FieldTermCounter(
            FieldTermCounter.TWEET_COUNT_KEY,
            instanceCounter,
            startCheckHour,
            endCheckHour,
            hourlyMinCount,
            dailyMinCount));
    this.fieldCheckTimeStats = Maps.newHashMap();
  }

  private int dateToHourValue(Date date) {
    Calendar cal = Calendar.getInstance(FieldTermCounter.TIME_ZONE);
    cal.setTime(date);
    return FieldTermCounter.getHourValue(cal);
  }

  private void updateHourlyCounts() {
    // Iterate the current indetwitter to count all tweets anf field hits.
    Map<String, Map<Integer, MutableInt>> newCountMap = getNewTweetCountMap();

    for (Map.Entry<String, Map<Integer, MutableInt>> newCounts : newCountMap.entrySet()) {
      final String fieldName = newCounts.getKey();
      FieldTermCounter termCounter = fieldTermCounters.get(fieldName);
      if (termCounter == null) {
        termCounter = new FieldTermCounter(
            fieldName,
            instanceCounter,
            startCheckHour,
            endCheckHour,
            hourlyMinCount,
            dailyMinCount);
        fieldTermCounters.put(fieldName, termCounter);
      }
      termCounter.runWithNewCounts(newCounts.getValue());
    }
  }

  /**
   * Loops through all segments, and all documents in each segment, and for each document
   * gets the createdAt timestamp (in seconds) from the TimeMapper.
   * Based on that, returns a map with the count of:
   * . the number of tweets for each hour
   * . the number of tweets corresponding to each field for each hour
   */
  private Map<String, Map<Integer, MutableInt>> getNewTweetCountMap() {
    Iterable<SegmentInfo> segmentInfos = segmentManager.getSegmentInfos(
        SegmentManager.Filter.Enabled, SegmentManager.Order.NEW_TO_OLD);
    Map<String, Map<Integer, MutableInt>> newCountMap = Maps.newHashMap();

    Map<Integer, MutableInt> newCounts = Maps.newHashMap();
    newCountMap.put(FieldTermCounter.TWEET_COUNT_KEY, newCounts);

    ImmutableSchemaInterface schemaSnapshot =
        segmentManager.getEarlybirdIndetwitterConfig().getSchema().getSchemaSnapshot();
    Calendar cal = Calendar.getInstance(FieldTermCounter.TIME_ZONE);
    for (SegmentInfo segmentInfo : segmentInfos) {
      try {
        EarlybirdSingleSegmentSearcher searcher = segmentManager.getSearcher(
            segmentInfo.getTimeSliceID(), schemaSnapshot);
        if (searcher != null) {
          EarlybirdIndetwitterSegmentAtomicReader reader = searcher.getTwitterIndetwitterReader();
          TimeMapper timeMapper = reader.getSegmentData().getTimeMapper();
          List<Pair<String, Integer>> outsideEndDateRangeDocList = new ArrayList<>();

          // Get the number of tweets for each hour.
          int docsOutsideEndDateRange = getNewTweetCountsForSegment(
              segmentInfo, reader, timeMapper, cal, newCounts);
          if (docsOutsideEndDateRange > 0) {
            outsideEndDateRangeDocList.add(new Pair<>(
                FieldTermCounter.TWEET_COUNT_KEY, docsOutsideEndDateRange));
          }

          // Get the number of tweets with corresponding field for each hour.
          for (Schema.FieldInfo fieldInfo : schemaSnapshot.getFieldInfos()) {
            if (fieldInfo.getFieldType().indetwitterOptions() == IndetwitterOptions.NONE) {
              continue;
            }

            String fieldName = fieldInfo.getName();
            docsOutsideEndDateRange = getNewFieldTweetCountsForSegment(
                segmentInfo, reader, timeMapper, cal, fieldName, newCountMap);
            if (docsOutsideEndDateRange > 0) {
              outsideEndDateRangeDocList.add(new Pair<>(fieldName, docsOutsideEndDateRange));
            }
          }

          LOG.info("Inspected segment: " + segmentInfo + " found "
              + outsideEndDateRangeDocList.size()
              + " fields with documents outside of segment end date.");
          for (Pair<String, Integer> outsideEndRange : outsideEndDateRangeDocList) {
            LOG.info("  outside end date range - segment: " + segmentInfo.getSegmentName()
                + " field: " + outsideEndRange.toString());
          }
        }
      } catch (IOEtwitterception e) {
        LOG.error("Etwitterception getting daily tweet counts for timeslice: " + segmentInfo, e);
      }
    }
    return newCountMap;
  }

  private void incrementNumDocsWithIllegalTimeCounter(String segmentName, String fieldSuffitwitter) {
    String statName = String.format(
        "num_docs_with_illegal_time_for_segment_%s%s_counter", segmentName, fieldSuffitwitter);
    SearchCounter counter = SearchCounter.etwitterport(statName);
    counter.increment();
  }

  private int getNewTweetCountsForSegment(
      SegmentInfo segmentInfo,
      EarlybirdIndetwitterSegmentAtomicReader reader,
      TimeMapper timeMapper,
      Calendar cal,
      Map<Integer, MutableInt> newTweetCounts) {
    DocIDToTweetIDMapper tweetIdMapper = reader.getSegmentData().getDocIDToTweetIDMapper();
    long dataEndTimeEtwitterclusiveMillis = getDataEndTimeEtwitterclusiveMillis(segmentInfo);
    int docsOutsideEndDateRange = 0;
    int docId = Integer.MIN_VALUE;
    while ((docId = tweetIdMapper.getNetwittertDocID(docId)) != DocIDToTweetIDMapper.ID_NOT_FOUND) {
      UpdateCountType updateCountType =
          updateTweetCount(timeMapper, docId, dataEndTimeEtwitterclusiveMillis, cal, newTweetCounts);
      if (updateCountType == UpdateCountType.ILLEGAL_TIME) {
        incrementNumDocsWithIllegalTimeCounter(segmentInfo.getSegmentName(), "");
      } else if (updateCountType == UpdateCountType.OUT_OF_RANGE_TIME) {
        docsOutsideEndDateRange++;
      }
    }
    return docsOutsideEndDateRange;
  }

  private int getNewFieldTweetCountsForSegment(
      SegmentInfo segmentInfo,
      EarlybirdIndetwitterSegmentAtomicReader reader,
      TimeMapper timeMapper,
      Calendar cal,
      String field,
      Map<String, Map<Integer, MutableInt>> newCountMap) throws IOEtwitterception {
    int docsOutsideEndDateRange = 0;
    Map<Integer, MutableInt> fieldTweetCounts =
        newCountMap.computeIfAbsent(field, k -> Maps.newHashMap());

    Terms terms = reader.terms(field);
    if (terms == null) {
      LOG.warn("Field <" + field + "> is missing terms in segment: "
          + segmentInfo.getSegmentName());
      return 0;
    }
    long startTimeMillis = System.currentTimeMillis();

    long dataEndTimeEtwitterclusiveMillis = getDataEndTimeEtwitterclusiveMillis(segmentInfo);
    for (TermsEnum termsEnum = terms.iterator(); termsEnum.netwittert() != null;) {
      DocIdSetIterator docsIterator = termsEnum.postings(null, PostingsEnum.NONE);
      for (int docId = docsIterator.netwittertDoc();
           docId != DocIdSetIterator.NO_MORE_DOCS; docId = docsIterator.netwittertDoc()) {
        UpdateCountType updateCountType = updateTweetCount(
            timeMapper, docId, dataEndTimeEtwitterclusiveMillis, cal, fieldTweetCounts);
        if (updateCountType == UpdateCountType.ILLEGAL_TIME) {
          incrementNumDocsWithIllegalTimeCounter(
              segmentInfo.getSegmentName(), "_and_field_" + field);
        } else if (updateCountType == UpdateCountType.OUT_OF_RANGE_TIME) {
          docsOutsideEndDateRange++;
        }
      }
    }
    updateFieldRunTimeStats(field, System.currentTimeMillis() - startTimeMillis);

    return docsOutsideEndDateRange;
  }

  private enum UpdateCountType {
    OK_TIME,
    ILLEGAL_TIME,
    OUT_OF_RANGE_TIME,
  }

  private static UpdateCountType updateTweetCount(
      TimeMapper timeMapper,
      int docId,
      long dataEndTimeEtwitterclusiveMillis,
      Calendar cal,
      Map<Integer, MutableInt> newTweetCounts) {
    int timeSecs = timeMapper.getTime(docId);
    if (timeSecs == TimeMapper.ILLEGAL_TIME) {
      return UpdateCountType.ILLEGAL_TIME;
    }
    if (dataEndTimeEtwitterclusiveMillis == Segment.NO_DATA_END_TIME
        || timeSecs * 1000L < dataEndTimeEtwitterclusiveMillis) {
      Integer hourlyValue = FieldTermCounter.getHourValue(cal, timeSecs);
      MutableInt count = newTweetCounts.get(hourlyValue);
      if (count == null) {
        count = new MutableInt(0);
        newTweetCounts.put(hourlyValue, count);
      }
      count.increment();
      return UpdateCountType.OK_TIME;
    } else {
      return UpdateCountType.OUT_OF_RANGE_TIME;
    }
  }

  /**
   * If a segment has an end date, return the last timestamp (etwitterclusive, and in millis) for which
   * we etwitterpect it to have data.
   * @return Segment.NO_DATA_END_TIME if the segment does not have an end date.
   */
  private long getDataEndTimeEtwitterclusiveMillis(SegmentInfo segmentInfo) {
    long dataEndDate = segmentInfo.getSegment().getDataEndDateInclusiveMillis();
    if (dataEndDate == Segment.NO_DATA_END_TIME) {
      return Segment.NO_DATA_END_TIME;
    } else {
      return dataEndDate + MILLIS_IN_A_DAY;
    }
  }

  private void updateFieldRunTimeStats(String fieldName, long runTimeMs) {
    SearchTimerStats timerStats = fieldCheckTimeStats.get(fieldName);
    if (timerStats == null) {
      final String statName = "tweet_count_monitor_check_time_field_" + fieldName;
      timerStats = searchStatsReceiver.getTimerStats(
          statName, TimeUnit.MILLISECONDS, false, false, false);
      fieldCheckTimeStats.put(fieldName, timerStats);
    }
    timerStats.timerIncrement(runTimeMs);
  }

  @VisibleForTesting
  String getStatName(String fieldName, Integer date) {
    return FieldTermCounter.getStatName(fieldName, instanceCounter, date);
  }

  @VisibleForTesting
  Map<Integer, AtomicInteger> getEtwitterportedCounts(String fieldName) {
    if (fieldTermCounters.get(fieldName) == null) {
      return null;
    } else {
      return fieldTermCounters.get(fieldName).getEtwitterportedCounts();
    }
  }

  @VisibleForTesting
  Map<Integer, MutableLong> getDailyCounts(String fieldName) {
    if (fieldTermCounters.get(fieldName) == null) {
      return null;
    } else {
      return fieldTermCounters.get(fieldName).getDailyCounts();
    }
  }

  @VisibleForTesting
  long getHoursWithNoTweets(String fieldName) {
    return fieldTermCounters.get(fieldName).getHoursWithNoTweets();
  }

  @VisibleForTesting
  long getDaysWithNoTweets(String fieldName) {
    return fieldTermCounters.get(fieldName).getDaysWithNoTweets();
  }

  @VisibleForTesting
  Map<String, SearchLongGauge> getEtwitterportedHourlyCountStats(String fieldName) {
    return fieldTermCounters.get(fieldName).getEtwitterportedHourlyCountStats();
  }

  @Override
  protected void runOneIteration() {
    LOG.info("Starting to get hourly tweet counts");
    final long startTimeMillis = System.currentTimeMillis();

    isRunningStat.set(1);
    try {
      updateHourlyCounts();
    } catch (Etwitterception etwitter) {
      LOG.error("Unetwitterpected etwitterception while getting hourly tweet counts", etwitter);
    } finally {
      isRunningStat.set(0);

      long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
      checkTimeStat.timerIncrement(elapsedTimeMillis);
      LOG.info("Done getting daily tweet counts. Hours without tweets: "
          + getHoursWithNoTweets(FieldTermCounter.TWEET_COUNT_KEY));
      LOG.info("Updating tweet count takes " + (elapsedTimeMillis / 1000) + " secs.");
    }
  }
}
