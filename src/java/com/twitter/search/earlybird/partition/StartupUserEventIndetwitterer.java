package com.twitter.search.earlybird.partition;

import java.sql.Timestamp;
import java.tetwittert.DateFormat;
import java.tetwittert.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;

import com.google.common.annotations.VisibleForTesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.search.common.metrics.SearchTimer;
import com.twitter.search.earlybird.EarlybirdStatus;
import com.twitter.search.earlybird.common.NonPagingAssert;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.common.config.EarlybirdProperty;
import com.twitter.search.earlybird.common.userupdates.UserScrubGeoMap;
import com.twitter.search.earlybird.common.userupdates.UserTableBuilderFromSnapshot;
import com.twitter.search.earlybird.common.userupdates.UserTable;
import com.twitter.search.earlybird.factory.EarlybirdIndetwitterConfigUtil;

/**
 * Indetwitterer class responsible for getting the the {@link UserTable} and {@link UserScrubGeoMap}
 * indetwittered up until the current moment.
 */
public class StartupUserEventIndetwitterer {
  private static final Logger LOG = LoggerFactory.getLogger(StartupUserEventIndetwitterer.class);
  private static final String LOAD_USER_UPDATE_SNAPSHOT =
      "loading user update snapshot";
  private static final String INDEtwitter_ALL_USER_EVENTS =
      "indetwittering all user events";
  private static final NonPagingAssert FAILED_USER_TABLE_HDFS_LOAD
      = new NonPagingAssert("failed_user_table_hdfs_load");

  private static final long MAtwitter_RETRY_MILLIS_FOR_SEEK_TO_TIMESTAMP =
      Duration.ofMinutes(1).toMillis();
  private static final long SLEEP_MILLIS_BETWEEN_RETRIES_FOR_SEEK_TO_TIMESTAMP =
      Duration.ofSeconds(1).toMillis();

  private static final long MILLIS_IN_FOURTEEN_DAYS = 1209600000;
  private static final long MILLIS_IN_ONE_DAY = 86400000;

  private final SearchIndetwitteringMetricSet searchIndetwitteringMetricSet;
  private final UserUpdatesStreamIndetwitterer userUpdatesStreamIndetwitterer;
  private final UserScrubGeoEventStreamIndetwitterer userScrubGeoEventStreamIndetwitterer;
  private final SegmentManager segmentManager;
  private final Clock clock;

  public StartupUserEventIndetwitterer(
      SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      UserUpdatesStreamIndetwitterer userUpdatesStreamIndetwitterer,
      UserScrubGeoEventStreamIndetwitterer userScrubGeoEventStreamIndetwitterer,
      SegmentManager segmentManager,
      Clock clock) {
    this.searchIndetwitteringMetricSet = searchIndetwitteringMetricSet;
    this.userUpdatesStreamIndetwitterer = userUpdatesStreamIndetwitterer;
    this.userScrubGeoEventStreamIndetwitterer = userScrubGeoEventStreamIndetwitterer;
    this.segmentManager = segmentManager;
    this.clock = clock;
  }

  /**
   * Indetwitter all user events.
   */
  public void indetwitterAllEvents() {
    EarlybirdStatus.beginEvent(
        INDEtwitter_ALL_USER_EVENTS, searchIndetwitteringMetricSet.startupInUserEventIndetwitterer);

    indetwitterUserUpdates();
    if (EarlybirdConfig.consumeUserScrubGeoEvents()) {
      indetwitterUserScrubGeoEvents();
    }

    EarlybirdStatus.endEvent(
        INDEtwitter_ALL_USER_EVENTS, searchIndetwitteringMetricSet.startupInUserEventIndetwitterer);
  }

  /**
   * Indetwitter user updates until current.
   */
  public void indetwitterUserUpdates() {
    EarlybirdStatus.beginEvent(
        LOAD_USER_UPDATE_SNAPSHOT, searchIndetwitteringMetricSet.startupInUserUpdates);

    Optional<UserTable> userTable = buildUserTable();
    if (userTable.isPresent()) {
      segmentManager.getUserTable().setTable(userTable.get());
      LOG.info("Set new user table.");

      if (!seekToTimestampWithRetriesIfNecessary(
          userTable.get().getLastRecordTimestamp(),
          userUpdatesStreamIndetwitterer)) {
        LOG.error("User Updates stream indetwitterer unable to seek to timestamp. "
            + "Will seek to beginning.");
        userUpdatesStreamIndetwitterer.seekToBeginning();
      }
    } else {
      LOG.info("Failed to load user update snapshot. Will reindetwitter user updates from scratch.");
      FAILED_USER_TABLE_HDFS_LOAD.assertFailed();
      userUpdatesStreamIndetwitterer.seekToBeginning();
    }

    userUpdatesStreamIndetwitterer.readRecordsUntilCurrent();
    LOG.info("Finished catching up on user updates via Kafka");

    EarlybirdStatus.endEvent(
        LOAD_USER_UPDATE_SNAPSHOT, searchIndetwitteringMetricSet.startupInUserUpdates);
  }

  /**
   * Indetwitter UserScrubGeoEvents until current.
   */
  public void indetwitterUserScrubGeoEvents() {
    seekUserScrubGeoEventKafkaConsumer();

    SearchTimer timer = new SearchTimer();
    timer.start();
    userScrubGeoEventStreamIndetwitterer.readRecordsUntilCurrent();
    timer.stop();

    LOG.info("Finished catching up on user scrub geo events via Kafka");
    LOG.info("UserScrubGeoMap contains {} users and finished in {} milliseconds",
        segmentManager.getUserScrubGeoMap().getNumUsersInMap(), timer.getElapsed());
  }

  /**
   * Seeks UserScrubGeoEventKafkaConsumer using timestamp derived from
   * getTimestampForUserScrubGeoEventKafkaConsumer().
   */
  @VisibleForTesting
  public void seekUserScrubGeoEventKafkaConsumer() {
    long seekTimestamp = getTimestampForUserScrubGeoEventKafkaConsumer();
    if (seekTimestamp == -1) {
      userScrubGeoEventStreamIndetwitterer.seekToBeginning();
    } else {
      if (!seekToTimestampWithRetriesIfNecessary(seekTimestamp, userScrubGeoEventStreamIndetwitterer)) {
        LOG.error("User Scrub Geo stream indetwitterer unable to seek to timestamp. "
            + "Will seek to beginning.");
        userScrubGeoEventStreamIndetwitterer.seekToBeginning();
      }
    }
  }

  /**
   * Get timestamp to seek UserScrubGeoEventKafkaConsumer to.
   * @return
   */
  public long getTimestampForUserScrubGeoEventKafkaConsumer() {
    if (EarlybirdIndetwitterConfigUtil.isArchiveSearch()) {
      return getTimestampForArchive();
    } else {
      return getTimestampForRealtime();
    }
  }

  /**
   * For archive: grab scrub gen from config file and convert date into a timestamp. Add buffer of
   * one day. We need all UserScrubGeoEvents since the date of the current scrub gen.
   *
   * See go/realtime-geo-filtering
   * @return
   */
  public long getTimestampForArchive() {
    try {
      String scrubGenString = EarlybirdProperty.EARLYBIRD_SCRUB_GEN.get();

      DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
      Date date = dateFormat.parse(scrubGenString);
      return new Timestamp(date.getTime()).getTime() - MILLIS_IN_ONE_DAY;

    } catch (Etwitterception e) {
      LOG.error("Could not derive timestamp from scrub gen. "
          + "Will seek User Scrub Geo Kafka consumer to beginning of topic");
    }
    return -1;
  }

  /**
   * For realtime/protected: Compute the timestamp 14 days from the current time. This will account
   * for all events that have occurred during the lifecylce of the current indetwitter.
   *
   * See go/realtime-geo-filtering
   */
  public long getTimestampForRealtime() {
   return System.currentTimeMillis() - MILLIS_IN_FOURTEEN_DAYS;
  }

  private boolean seekToTimestampWithRetriesIfNecessary(
      long lastRecordTimestamp,
      SimpleStreamIndetwitterer streamIndetwitterer) {
    long initialTimeMillis = clock.nowMillis();
    int numFailures = 0;
    while (shouldTrySeekToTimestamp(initialTimeMillis, numFailures)) {
      try {
        streamIndetwitterer.seekToTimestamp(lastRecordTimestamp);
        LOG.info("Seeked consumer to timestamp {} after {} failures",
            lastRecordTimestamp, numFailures);
        return true;
      } catch (Etwitterception e) {
        numFailures++;
        LOG.info("Caught etwitterception when seeking to timestamp. Num failures: {}. Etwitterception: {}",
            numFailures, e);
        // Sleep before attempting to retry
        try {
          clock.waitFor(SLEEP_MILLIS_BETWEEN_RETRIES_FOR_SEEK_TO_TIMESTAMP);
        } catch (InterruptedEtwitterception interruptedEtwitterception) {
          LOG.warn("Interrupted while sleeping between seekToTimestamp retries",
              interruptedEtwitterception);
          // Preserve interrupt status.
          Thread.currentThread().interrupt();
          break;
        }
      }
    }
    // Failed to seek to timestamp
    return false;
  }

  private boolean shouldTrySeekToTimestamp(long initialTimeMillis, int numFailures) {
    if (numFailures == 0) {
      // no attempts have been made yet, so we should try to seek to timestamp
      return true;
    } else {
      return clock.nowMillis() - initialTimeMillis < MAtwitter_RETRY_MILLIS_FOR_SEEK_TO_TIMESTAMP;
    }
  }

  protected Optional<UserTable> buildUserTable() {
    UserTableBuilderFromSnapshot builder = new UserTableBuilderFromSnapshot();
    return builder.build(segmentManager.getUserTable().getUserIdFilter());
  }
}
