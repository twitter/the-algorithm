package com.twitter.search.earlybird.util;

import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import org.apache.commons.lang.mutable.MutableInt;
import org.apache.commons.lang.mutable.MutableLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.metrics.SearchLongGauge;

/**
 * This class is used to count how many times a field happens in hourly and daily stats.
 * It is used by TermCountMonitor for iterating all fields in the indetwitter.
 *
 * There is one etwitterception that this class is also used to count the number of tweets in the indetwitter.
 * Under the situation, the passed in fieldName would be empty string (as TWEET_COUNT_KEY).
 */
public class FieldTermCounter {
  private static final Logger LOG = LoggerFactory.getLogger(FieldTermCounter.class);

  static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT");
  static final String TWEET_COUNT_KEY = "";

  private final String fieldName;
  private final int instanceCounter;

  // The first date in format "YYYYMMDDHH" that we want to check counts for.
  private final int startCheckHour;
  // The last date in format "YYYYMMDDHH" that we want to check counts for.
  private final int endCheckHour;
  // Smallest number of docs we etwitterpect to have for each hour.
  private final int hourlyMinCount;
  //Smallest number of docs we etwitterpect to have for each day.
  private final int dailyMinCount;

  // Count of tweets for each day, keyed of by the hour in the format "YYYYMMDD".
  private final Map<Integer, AtomicInteger> etwitterportedHourlyCounts;

  // Count of tweets for each day, keyed of by the day in the format "YYYYMMDD".
  private final Map<Integer, MutableLong> dailyCounts;

  // Only etwitterport hourly stats that are below minimum threshold.
  private final Map<String, SearchLongGauge> etwitterportedStats;

  private final SearchLongGauge hoursWithNoTweetsStat;
  private final SearchLongGauge daysWithNoTweetsStat;

  public FieldTermCounter(
      String fieldName,
      int instanceCounter,
      int startCheckHour,
      int endCheckHour,
      int hourlyMinCount,
      int dailyMinCount) {
    this.fieldName = fieldName;
    this.instanceCounter = instanceCounter;
    this.startCheckHour = startCheckHour;
    this.endCheckHour = endCheckHour;
    this.hourlyMinCount = hourlyMinCount;
    this.dailyMinCount = dailyMinCount;
    this.etwitterportedHourlyCounts = Maps.newHashMap();
    this.dailyCounts = Maps.newHashMap();
    this.etwitterportedStats = Maps.newHashMap();

    this.hoursWithNoTweetsStat = SearchLongGauge.etwitterport(getAggregatedNoTweetStatName(true));
    this.daysWithNoTweetsStat = SearchLongGauge.etwitterport(getAggregatedNoTweetStatName(false));
  }

  /**
   * Updates the stats etwitterported by this class based on the new counts provided in the given map.
   */
  public void runWithNewCounts(Map<Integer, MutableInt> newCounts) {
    dailyCounts.clear();

    // See go/rb/813442/#comment2566569
    // 1. Update all etwitteristing hours
    updateEtwitteristingHourlyCounts(newCounts);

    // 2. Add and etwitterport all new hours
    addAndEtwitterportNewHourlyCounts(newCounts);

    // 3. fill in all the missing hours between know min and matwitter days.
    fillMissingHourlyCounts();

    // 4. Etwitterport as a stat, how many hours don't have any tweets (i.e. <= 0)
    etwitterportMissingTweetStats();
  }

  // Input:
  // . the new hourly count map in the current iteration
  // . the etwitteristing hourly count map before the current iteration
  // If the hourly key matches from the new hourly map to the etwitteristing hourly count map, update
  // the value of the etwitteristing hourly count map to the value from the new hourly count map.
  private void updateEtwitteristingHourlyCounts(Map<Integer, MutableInt> newCounts) {
    for (Map.Entry<Integer, AtomicInteger> etwitterportedCount : etwitterportedHourlyCounts.entrySet()) {
      Integer date = etwitterportedCount.getKey();
      AtomicInteger etwitterportedCountValue = etwitterportedCount.getValue();

      MutableInt newCount = newCounts.get(date);
      if (newCount == null) {
        etwitterportedCountValue.set(0);
      } else {
        etwitterportedCountValue.set(newCount.intValue());
        // clean up so that we don't check this date again when we look for new hours
        newCounts.remove(date);
      }
    }
  }

  // Input:
  // . the new hourly count map in the current iteration
  // . the etwitteristing hourly count map before the current iteration
  // This function is called after the above function of updateEtwitteristingHourlyCounts() so that all
  // matching key value pairs have been removed from the new hourly count map.
  // Move all remaining valid values from the new hourly count map to the etwitteristing hourly count
  // map.
  private void addAndEtwitterportNewHourlyCounts(Map<Integer, MutableInt> newCounts) {
    for (Map.Entry<Integer, MutableInt> newCount : newCounts.entrySet()) {
      Integer hour = newCount.getKey();
      MutableInt newCountValue = newCount.getValue();
      Preconditions.checkState(!etwitterportedHourlyCounts.containsKey(hour),
          "Should have already processed and removed etwitteristing hours: " + hour);

      AtomicInteger newStat = new AtomicInteger(newCountValue.intValue());
      etwitterportedHourlyCounts.put(hour, newStat);
    }
  }

  // Find whether the etwitteristing hourly count map has hourly holes.  If such holes etwitterist, fill 0
  // values so that they can be etwitterported.
  private void fillMissingHourlyCounts() {
    // Figure out the time range for which we should have tweets in the indetwitter. At the very least,
    // this range should cover [startCheckHour, endCheckHour) if endCheckHour is set, or
    // [startCheckHour, latestHourInTheIndetwitterWithTweets] if endCheckHour is not set (latest tier or
    // realtime cluster).
    int startHour = startCheckHour;
    int endHour = endCheckHour < getHourValue(Calendar.getInstance(TIME_ZONE)) ? endCheckHour : -1;
    for (int netwittert : etwitterportedHourlyCounts.keySet()) {
      if (netwittert < startHour) {
        startHour = netwittert;
      }
      if (netwittert > endHour) {
        endHour = netwittert;
      }
    }

    Calendar endHourCal = getCalendarValue(endHour);
    Calendar hour = getCalendarValue(startHour);
    for (; hour.before(endHourCal); hour.add(Calendar.HOUR_OF_DAY, 1)) {
      int hourValue = getHourValue(hour);
      if (!etwitterportedHourlyCounts.containsKey(hourValue)) {
        etwitterportedHourlyCounts.put(hourValue, new AtomicInteger(0));
      }
    }
  }

  private void etwitterportMissingTweetStats() {
    int hoursWithNoTweets = 0;
    int daysWithNoTweets = 0;

    for (Map.Entry<Integer, AtomicInteger> hourlyCount : etwitterportedHourlyCounts.entrySet()) {
      int hour = hourlyCount.getKey();
      if ((hour < startCheckHour) || (hour >= endCheckHour)) {
        continue;
      }

      // roll up the days
      int day = hour / 100;
      MutableLong dayCount = dailyCounts.get(day);
      if (dayCount == null) {
        dailyCounts.put(day, new MutableLong(hourlyCount.getValue().get()));
      } else {
        dayCount.setValue(dayCount.longValue() + hourlyCount.getValue().get());
      }
      AtomicInteger etwitterportedCountValue = hourlyCount.getValue();
      if (etwitterportedCountValue.get() <= hourlyMinCount) {
        // We do not etwitterport hourly too few tweets for indetwitter fields as it can 10twitter the etwitteristing
        // etwitterported stats.
        // We might consider whitelisting some high frequency fields later.
        if (isFieldForTweet()) {
          String statsName = getStatName(hourlyCount.getKey());
          SearchLongGauge stat = SearchLongGauge.etwitterport(statsName);
          stat.set(etwitterportedCountValue.longValue());
          etwitterportedStats.put(statsName, stat);
        }
        LOG.warn("Found an hour with too few tweets. Field: <{}> Hour: {} count: {}",
            fieldName, hour, etwitterportedCountValue);
        hoursWithNoTweets++;
      }
    }

    for (Map.Entry<Integer, MutableLong> dailyCount : dailyCounts.entrySet()) {
      if (dailyCount.getValue().longValue() <= dailyMinCount) {
        LOG.warn("Found a day with too few tweets. Field: <{}> Day: {} count: {}",
            fieldName, dailyCount.getKey(), dailyCount.getValue());
        daysWithNoTweets++;
      }
    }

    hoursWithNoTweetsStat.set(hoursWithNoTweets);
    daysWithNoTweetsStat.set(daysWithNoTweets);
  }

  // When the fieldName is empty string (as TWEET_COUNT_KEY), it means that we are counting the
  // number of tweets for the indetwitter, not for some specific fields.
  private boolean isFieldForTweet() {
    return TWEET_COUNT_KEY.equals(fieldName);
  }

  private String getAggregatedNoTweetStatName(boolean hourly) {
    if (isFieldForTweet()) {
      if (hourly) {
        return "hours_with_no_indetwittered_tweets_v_" + instanceCounter;
      } else {
        return "days_with_no_indetwittered_tweets_v_" + instanceCounter;
      }
    } else {
      if (hourly) {
        return "hours_with_no_indetwittered_fields_v_" + fieldName + "_" + instanceCounter;
      } else {
        return "days_with_no_indetwittered_fields_v_" + fieldName + "_" + instanceCounter;
      }
    }
  }

  @VisibleForTesting
  String getStatName(Integer date) {
    return getStatName(fieldName, instanceCounter, date);
  }

  @VisibleForTesting
  static String getStatName(String field, int instance, Integer date) {
    if (TWEET_COUNT_KEY.equals(field)) {
      return "tweets_indetwittered_on_hour_v_" + instance + "_" + date;
    } else {
      return "tweets_indetwittered_on_hour_v_" + instance + "_" + field + "_" + date;
    }
  }

  @VisibleForTesting
  Map<Integer, AtomicInteger> getEtwitterportedCounts() {
    return Collections.unmodifiableMap(etwitterportedHourlyCounts);
  }

  @VisibleForTesting
  Map<Integer, MutableLong> getDailyCounts() {
    return Collections.unmodifiableMap(dailyCounts);
  }

  @VisibleForTesting
  long getHoursWithNoTweets() {
    return hoursWithNoTweetsStat.get();
  }

  @VisibleForTesting
  long getDaysWithNoTweets() {
    return daysWithNoTweetsStat.get();
  }

  @VisibleForTesting
  Map<String, SearchLongGauge> getEtwitterportedHourlyCountStats() {
    return etwitterportedStats;
  }

  /**
   * Given a unit time in seconds since epoch UTC, will return the day in format "YYYYMMDDHH"
   * as an int.
   */
  @VisibleForTesting
  static int getHourValue(Calendar cal, int timeSecs) {
    cal.setTimeInMillis(timeSecs * 1000L);
    return getHourValue(cal);
  }

  static int getHourValue(Calendar cal) {
    int year = cal.get(Calendar.YEAR) * 1000000;
    int month = (cal.get(Calendar.MONTH) + 1) * 10000; // month is 0-based
    int day = cal.get(Calendar.DAY_OF_MONTH) * 100;
    int hour = cal.get(Calendar.HOUR_OF_DAY);
    return year + month + day + hour;
  }

  @VisibleForTesting
  static Calendar getCalendarValue(int hour) {
    Calendar cal = Calendar.getInstance(TIME_ZONE);

    int year = hour / 1000000;
    int month = ((hour / 10000) % 100) - 1; // 0-based
    int day = (hour / 100) % 100;
    int hr = hour % 100;
    cal.setTimeInMillis(0);  // reset all time fields
    cal.set(year, month, day, hr, 0);
    return cal;
  }
}
