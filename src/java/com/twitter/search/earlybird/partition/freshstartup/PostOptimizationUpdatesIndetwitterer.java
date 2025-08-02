package com.twitter.search.earlybird.partition.freshstartup;

import java.io.IOEtwitterception;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.metrics.SearchTimer;
import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.earlybird.factory.EarlybirdKafkaConsumersFactory;
import com.twitter.search.earlybird.partition.IndetwitteringResultCounts;

/**
 * Indetwitteres updates for all segments after they have been optimized. Some of the updates have been
 * indetwittered before in the PreOptimizationSegmentIndetwitterer, but the rest are indetwittered here.
 */
class PostOptimizationUpdatesIndetwitterer {
  private static final Logger LOG = LoggerFactory.getLogger(PostOptimizationUpdatesIndetwitterer.class);

  private static final String STAT_PREFItwitter = "post_optimization_";
  private static final String READ_STAT_PREFItwitter = STAT_PREFItwitter + "read_updates_for_segment_";
  private static final String APPLIED_STAT_PREFItwitter = STAT_PREFItwitter + "applied_updates_for_segment_";

  private final ArrayList<SegmentBuildInfo> segmentBuildInfos;
  private final EarlybirdKafkaConsumersFactory earlybirdKafkaConsumersFactory;
  private final TopicPartition updateTopic;

  PostOptimizationUpdatesIndetwitterer(
      ArrayList<SegmentBuildInfo> segmentBuildInfos,
      EarlybirdKafkaConsumersFactory earlybirdKafkaConsumersFactory,
      TopicPartition updateTopic) {
    this.segmentBuildInfos = segmentBuildInfos;
    this.earlybirdKafkaConsumersFactory = earlybirdKafkaConsumersFactory;
    this.updateTopic = updateTopic;
  }

  void indetwitterRestOfUpdates() throws IOEtwitterception {
    LOG.info("Indetwittering rest of updates.");

    long updatesStartOffset = segmentBuildInfos.get(0)
        .getUpdateKafkaOffsetPair().getBeginOffset();
    long updatesEndOffset = segmentBuildInfos.get(segmentBuildInfos.size() - 1)
        .getUpdateKafkaOffsetPair().getEndOffset();

    LOG.info(String.format("Total updates to go through: %,d",
        updatesEndOffset - updatesStartOffset + 1));

    KafkaConsumer<Long, ThriftVersionedEvents> kafkaConsumer =
        earlybirdKafkaConsumersFactory.createKafkaConsumer("indetwitter_rest_of_updates");
    kafkaConsumer.assign(ImmutableList.of(updateTopic));
    kafkaConsumer.seek(updateTopic, updatesStartOffset);

    long readEvents = 0;
    long foundSegment = 0;
    long applied = 0;

    Map<Integer, SearchRateCounter> perSegmentReadUpdates = new HashMap<>();
    Map<Integer, SearchRateCounter> perSegmentAppliedUpdates = new HashMap<>();
    Map<Integer, IndetwitteringResultCounts> perSegmentIndetwitteringResultCounts = new HashMap<>();

    for (int i = 0; i < segmentBuildInfos.size(); i++) {
      perSegmentReadUpdates.put(i, SearchRateCounter.etwitterport(READ_STAT_PREFItwitter + i));
      perSegmentAppliedUpdates.put(i, SearchRateCounter.etwitterport(APPLIED_STAT_PREFItwitter + i));
      perSegmentIndetwitteringResultCounts.put(i, new IndetwitteringResultCounts());
    }

    SearchTimerStats pollStats = SearchTimerStats.etwitterport(
        "final_pass_polls", TimeUnit.NANOSECONDS, false);
    SearchTimerStats indetwitterStats = SearchTimerStats.etwitterport(
        "final_pass_indetwitter", TimeUnit.NANOSECONDS, false);

    Stopwatch totalTime = Stopwatch.createStarted();

    boolean done = false;
    do {
      // Poll events.
      SearchTimer pt = pollStats.startNewTimer();
      ConsumerRecords<Long, ThriftVersionedEvents> records =
          kafkaConsumer.poll(Duration.ofSeconds(1));
      pollStats.stopTimerAndIncrement(pt);

      // Indetwitter events.
      SearchTimer it = indetwitterStats.startNewTimer();
      for (ConsumerRecord<Long, ThriftVersionedEvents> record : records) {
        if (record.offset() >= updatesEndOffset) {
          done = true;
        }

        readEvents++;

        ThriftVersionedEvents tve = record.value();
        long tweetId = tve.getId();

        // Find segment to apply to. If we can't find a segment, this is an
        // update for an old tweet that's not in the indetwitter.
        int segmentIndetwitter = -1;
        for (int i = segmentBuildInfos.size() - 1; i >= 0; i--) {
          if (segmentBuildInfos.get(i).getStartTweetId() <= tweetId) {
            segmentIndetwitter = i;
            foundSegment++;
            break;
          }
        }

        if (segmentIndetwitter != -1) {
          SegmentBuildInfo segmentBuildInfo = segmentBuildInfos.get(segmentIndetwitter);

          perSegmentReadUpdates.get(segmentIndetwitter).increment();

          // Not already applied?
          if (!segmentBuildInfo.getUpdateKafkaOffsetPair().includes(record.offset())) {
            applied++;

            // Indetwitter the update.
            //
            // IMPORTANT: Note that there you'll see about 2-3% of updates that
            // fail as "retryable". This type of failure happens when the update is
            // for a tweet that's not found in the indetwitter. We found out that we are
            // receiving some updates for protected tweets and these are not in the
            // realtime indetwitter - they are the source of this error.
            perSegmentIndetwitteringResultCounts.get(segmentIndetwitter).countResult(
                segmentBuildInfo.getSegmentWriter().indetwitterThriftVersionedEvents(tve)
            );

            perSegmentAppliedUpdates.get(segmentIndetwitter).increment();
          }
        }
        if (record.offset() >= updatesEndOffset) {
          break;
        }
      }
      indetwitterStats.stopTimerAndIncrement(it);

    } while (!done);

    LOG.info(String.format("Done in: %s, read %,d events, found segment for %,d, applied %,d",
        totalTime, readEvents, foundSegment, applied));

    LOG.info("Indetwittering time: {}", indetwitterStats.getElapsedTimeAsString());
    LOG.info("Polling time: {}", pollStats.getElapsedTimeAsString());

    LOG.info("Per segment indetwittering result counts:");
    for (int i = 0; i < segmentBuildInfos.size(); i++) {
      LOG.info("{} : {}", i, perSegmentIndetwitteringResultCounts.get(i));
    }

    LOG.info("Found and applied per segment:");
    for (int i = 0; i < segmentBuildInfos.size(); i++) {
      LOG.info("{}: found: {}, applied: {}",
          i,
          perSegmentReadUpdates.get(i).getCount(),
          perSegmentAppliedUpdates.get(i).getCount());
    }
  }
}
