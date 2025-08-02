package com.twitter.search.earlybird.partition;

import java.io.Closeable;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.ApiEtwitterception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.metrics.SearchTimer;
import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.common.util.LogFormatUtil;
import com.twitter.search.earlybird.EarlybirdStatus;
import com.twitter.search.earlybird.common.CaughtUpMonitor;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.etwitterception.WrappedKafkaApiEtwitterception;
import com.twitter.search.earlybird.thrift.EarlybirdStatusCode;

/**
 * Reads TVEs from Kafka and writes them to a PartitionWriter.
 */
public class EarlybirdKafkaConsumer implements Closeable {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdKafkaConsumer.class);

  private static final Duration POLL_TIMEOUT = Duration.ofSeconds(1);
  private static final String STATS_PREFItwitter = "earlybird_kafka_consumer_";

  // See SEARCH-31827
  private static final SearchCounter INGESTING_DONE =
      SearchCounter.etwitterport(STATS_PREFItwitter + "ingesting_done");
  private static final SearchRateCounter POLL_LOOP_EtwitterCEPTIONS =
      SearchRateCounter.etwitterport(STATS_PREFItwitter + "poll_loop_etwitterceptions");
  private static final SearchRateCounter FLUSHING_EtwitterCEPTIONS =
      SearchRateCounter.etwitterport(STATS_PREFItwitter + "flushing_etwitterceptions");

  private static final SearchTimerStats TIMED_POLLS =
      SearchTimerStats.etwitterport(STATS_PREFItwitter + "timed_polls");
  private static final SearchTimerStats TIMED_INDEtwitter_EVENTS =
      SearchTimerStats.etwitterport(STATS_PREFItwitter + "timed_indetwitter_events");

  private final AtomicBoolean running = new AtomicBoolean(true);
  private final BalancingKafkaConsumer balancingKafkaConsumer;
  private final PartitionWriter partitionWriter;
  protected final TopicPartition tweetTopic;
  protected final TopicPartition updateTopic;
  private final KafkaConsumer<Long, ThriftVersionedEvents> underlyingKafkaConsumer;
  private final CriticalEtwitterceptionHandler criticalEtwitterceptionHandler;
  private final EarlybirdIndetwitterFlusher earlybirdIndetwitterFlusher;
  private final SearchIndetwitteringMetricSet searchIndetwitteringMetricSet;
  private boolean finishedIngestUntilCurrent;
  private final CaughtUpMonitor indetwitterCaughtUpMonitor;

  protected class ConsumeBatchResult {
    private boolean isCaughtUp;
    private long readRecordsCount;

    public ConsumeBatchResult(boolean isCaughtUp, long readRecordsCount) {
      this.isCaughtUp = isCaughtUp;
      this.readRecordsCount = readRecordsCount;
    }

    public boolean isCaughtUp() {
      return isCaughtUp;
    }

    public long getReadRecordsCount() {
      return readRecordsCount;
    }
  }

  public EarlybirdKafkaConsumer(
      KafkaConsumer<Long, ThriftVersionedEvents> underlyingKafkaConsumer,
      SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler,
      PartitionWriter partitionWriter,
      TopicPartition tweetTopic,
      TopicPartition updateTopic,
      EarlybirdIndetwitterFlusher earlybirdIndetwitterFlusher,
      CaughtUpMonitor kafkaIndetwitterCaughtUpMonitor
  ) {
    this.partitionWriter = partitionWriter;
    this.underlyingKafkaConsumer = underlyingKafkaConsumer;
    this.criticalEtwitterceptionHandler = criticalEtwitterceptionHandler;
    this.searchIndetwitteringMetricSet = searchIndetwitteringMetricSet;
    this.tweetTopic = tweetTopic;
    this.updateTopic = updateTopic;
    this.earlybirdIndetwitterFlusher = earlybirdIndetwitterFlusher;

    LOG.info("Reading from Kafka topics: tweetTopic={}, updateTopic={}", tweetTopic, updateTopic);
    underlyingKafkaConsumer.assign(ImmutableList.of(updateTopic, tweetTopic));

    this.balancingKafkaConsumer =
        new BalancingKafkaConsumer(underlyingKafkaConsumer, tweetTopic, updateTopic);
    this.finishedIngestUntilCurrent = false;
    this.indetwitterCaughtUpMonitor = kafkaIndetwitterCaughtUpMonitor;
  }

  /**
   * Run the consumer, indetwittering from Kafka.
   */
  @VisibleForTesting
  public void run() {
    while (isRunning()) {
      ConsumeBatchResult result = consumeBatch(true);
      indetwitterCaughtUpMonitor.setAndNotify(result.isCaughtUp());
    }
  }

  /**
   * Reads from Kafka, starting at the given offsets, and applies the events until we are caught up
   * with the current streams.
   */
  public void ingestUntilCurrent(long tweetOffset, long updateOffset) {
    Preconditions.checkState(!finishedIngestUntilCurrent);
    Stopwatch stopwatch = Stopwatch.createStarted();
    LOG.info("Ingest until current: seeking to Kafka offset {} for tweets and {} for updates.",
        tweetOffset, updateOffset);

    try {
      underlyingKafkaConsumer.seek(tweetTopic, tweetOffset);
      underlyingKafkaConsumer.seek(updateTopic, updateOffset);
    } catch (ApiEtwitterception kafkaApiEtwitterception) {
      throw new WrappedKafkaApiEtwitterception("Can't seek to tweet and update offsets",
          kafkaApiEtwitterception);
    }

    Map<TopicPartition, Long> endOffsets;
    try {
      endOffsets = underlyingKafkaConsumer.endOffsets(ImmutableList.of(tweetTopic, updateTopic));
    } catch (ApiEtwitterception kafkaApiEtwitterception) {
      throw new WrappedKafkaApiEtwitterception("Can't find end offsets",
          kafkaApiEtwitterception);
    }

    if (endOffsets.size() > 0) {
      LOG.info(String.format("Records until current: tweets=%,d, updates=%,d",
          endOffsets.get(tweetTopic) - tweetOffset + 1,
          endOffsets.get(updateTopic) - updateOffset + 1));
    }

    consumeBatchesUntilCurrent(true);

    LOG.info("ingestUntilCurrent finished in {}.", stopwatch);

    partitionWriter.logState();
    INGESTING_DONE.increment();
    finishedIngestUntilCurrent = true;
  }

  /**
   * Consume tweets and updates from streams until we're up to date.
   *
   * @return total number of read records.
   */
  private long consumeBatchesUntilCurrent(boolean flushingEnabled) {
    long totalRecordsRead = 0;
    long batchesConsumed = 0;

    while (isRunning()) {
      ConsumeBatchResult result = consumeBatch(flushingEnabled);
      batchesConsumed++;
      totalRecordsRead += result.getReadRecordsCount();
      if (isCurrent(result.isCaughtUp())) {
        break;
      }
    }

    LOG.info("Processed batches: {}", batchesConsumed);

    return totalRecordsRead;
  }

  // This method is overriden in MockEarlybirdKafkaConsumer.
  public boolean isCurrent(boolean current) {
    return current;
  }

  /**
   * We don't indetwitter during flushing, so after the flush is done, the indetwitter is stale.
   * We need to get to current, before we rejoin the serverset so that upon rejoining we're
   * not serving a stale indetwitter.
   */
  @VisibleForTesting
  void getToCurrentPostFlush() {
    LOG.info("Getting to current post flush");
    Stopwatch stopwatch = Stopwatch.createStarted();

    long totalRecordsRead = consumeBatchesUntilCurrent(false);

    LOG.info("Post flush, became current in: {}, after reading {} records.",
        stopwatch, LogFormatUtil.formatInt(totalRecordsRead));
  }

  /*
   * @return true if we are current after indetwittering this batch.
   */
  @VisibleForTesting
  protected ConsumeBatchResult consumeBatch(boolean flushingEnabled) {
    long readRecordsCount = 0;
    boolean isCaughtUp = false;

    try {
      // Poll.
      SearchTimer pollTimer = TIMED_POLLS.startNewTimer();
      ConsumerRecords<Long, ThriftVersionedEvents> records =
          balancingKafkaConsumer.poll(POLL_TIMEOUT);
      readRecordsCount += records.count();
      TIMED_POLLS.stopTimerAndIncrement(pollTimer);

      // Indetwitter.
      SearchTimer indetwitterTimer = TIMED_INDEtwitter_EVENTS.startNewTimer();
      isCaughtUp = partitionWriter.indetwitterBatch(records);
      TIMED_INDEtwitter_EVENTS.stopTimerAndIncrement(indetwitterTimer);
    } catch (Etwitterception etwitter) {
      POLL_LOOP_EtwitterCEPTIONS.increment();
      LOG.error("Etwitterception in poll loop", etwitter);
    }

    try {
      // Possibly flush the indetwitter.
      if (isCaughtUp && flushingEnabled) {
        long tweetOffset = 0;
        long updateOffset = 0;

        try {
          tweetOffset = underlyingKafkaConsumer.position(tweetTopic);
          updateOffset = underlyingKafkaConsumer.position(updateTopic);
        } catch (ApiEtwitterception kafkaApiEtwitterception) {
          throw new WrappedKafkaApiEtwitterception("can't get topic positions", kafkaApiEtwitterception);
        }

        EarlybirdIndetwitterFlusher.FlushAttemptResult flushAttemptResult =
            earlybirdIndetwitterFlusher.flushIfNecessary(
                tweetOffset, updateOffset, this::getToCurrentPostFlush);

        if (flushAttemptResult == EarlybirdIndetwitterFlusher.FlushAttemptResult.FLUSH_ATTEMPT_MADE) {
          // Viz might show this as a fairly high number, so we're printing it here to confirm
          // the value on the server.
          LOG.info("Finished flushing. Indetwitter freshness in ms: {}",
              LogFormatUtil.formatInt(searchIndetwitteringMetricSet.getIndetwitterFreshnessInMillis()));
        }

        if (!finishedIngestUntilCurrent) {
          LOG.info("Became current on startup. Tried to flush with result: {}",
              flushAttemptResult);
        }
      }
    } catch (Etwitterception etwitter) {
      FLUSHING_EtwitterCEPTIONS.increment();
      LOG.error("Etwitterception while flushing", etwitter);
    }

    return new ConsumeBatchResult(isCaughtUp, readRecordsCount);
  }

  public boolean isRunning() {
    return running.get() && EarlybirdStatus.getStatusCode() != EarlybirdStatusCode.STOPPING;
  }

  public void prepareAfterStartingWithIndetwitter(long matwitterIndetwitteredTweetId) {
    partitionWriter.prepareAfterStartingWithIndetwitter(matwitterIndetwitteredTweetId);
  }

  public void close() {
    balancingKafkaConsumer.close();
    running.set(false);
  }
}
