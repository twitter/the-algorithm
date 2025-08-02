package com.twitter.search.earlybird.partition;

import java.io.IOEtwitterception;
import java.time.Duration;

import com.google.common.annotations.VisibleForTesting;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.partitioning.snowflakeparser.SnowflakeIdParser;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEvent;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEventType;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;

/**
 * PartitionWriter writes Tweet events and Tweet update events to an Earlybird indetwitter. It is
 * responsible for creating new segments, adding Tweets to the correct segment, and applying updates
 * to the correct segment.
 */
public class PartitionWriter {
  private static final Logger LOG = LoggerFactory.getLogger(PartitionWriter.class);
  private static final String STATS_PREFItwitter = "partition_writer_";

  private static final SearchRateCounter MISSING_PENGUIN_VERSION =
      SearchRateCounter.etwitterport(STATS_PREFItwitter + "missing_penguin_version");
  private static final Duration CAUGHT_UP_FRESHNESS = Duration.ofSeconds(5);
  private static final SearchRateCounter EVENTS_CONSUMED =
      SearchRateCounter.etwitterport(STATS_PREFItwitter + "events_consumed");

  private final PenguinVersion penguinVersion;
  private final TweetUpdateHandler updateHandler;
  private final TweetCreateHandler createHandler;
  private final Clock clock;
  private final CriticalEtwitterceptionHandler criticalEtwitterceptionHandler;



  public PartitionWriter(
      TweetCreateHandler tweetCreateHandler,
      TweetUpdateHandler tweetUpdateHandler,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler,
      PenguinVersion penguinVersion,
      Clock clock
  ) {
    LOG.info("Creating PartitionWriter.");
    this.createHandler = tweetCreateHandler;
    this.updateHandler = tweetUpdateHandler;
    this.criticalEtwitterceptionHandler = criticalEtwitterceptionHandler;
    this.penguinVersion = penguinVersion;
    this.clock = clock;
  }

  /**
   * Indetwitter a batch of TVE records.
   */
  public boolean indetwitterBatch(Iterable<ConsumerRecord<Long, ThriftVersionedEvents>> records)
      throws Etwitterception {
    long minTweetAge = Long.MAtwitter_VALUE;
    for (ConsumerRecord<Long, ThriftVersionedEvents> record : records) {
      ThriftVersionedEvents tve = record.value();
      indetwitterTVE(tve);
      EVENTS_CONSUMED.increment();
      long tweetAgeInMs = SnowflakeIdParser.getTweetAgeInMs(clock.nowMillis(), tve.getId());
      minTweetAge = Math.min(tweetAgeInMs, minTweetAge);
    }

    return minTweetAge < CAUGHT_UP_FRESHNESS.toMillis();
  }

  /**
   * Indetwitter a ThriftVersionedEvents struct.
   */
  @VisibleForTesting
  public void indetwitterTVE(ThriftVersionedEvents tve) throws IOEtwitterception {
    ThriftIndetwitteringEvent tie = tve.getVersionedEvents().get(penguinVersion.getByteValue());
    if (tie == null) {
      LOG.error("Could not find a ThriftIndetwitteringEvent for PenguinVersion {} in "
          + "ThriftVersionedEvents: {}", penguinVersion, tve);
      MISSING_PENGUIN_VERSION.increment();
      return;
    }

    // An `INSERT` event is used for new Tweets. These are generated from Tweet Create Events from
    // TweetyPie.
    if (tie.getEventType() == ThriftIndetwitteringEventType.INSERT) {
      createHandler.handleTweetCreate(tve);
      updateHandler.retryPendingUpdates(tve.getId());
    } else {
      updateHandler.handleTweetUpdate(tve, false);
    }
  }

  public void prepareAfterStartingWithIndetwitter(long matwitterIndetwitteredTweetId) {
    createHandler.prepareAfterStartingWithIndetwitter(matwitterIndetwitteredTweetId);
  }

  void logState() {
    LOG.info("PartitionWriter state:");
    LOG.info(String.format("  Events indetwittered: %,d", EVENTS_CONSUMED.getCount()));
    createHandler.logState();
    updateHandler.logState();
  }
}
