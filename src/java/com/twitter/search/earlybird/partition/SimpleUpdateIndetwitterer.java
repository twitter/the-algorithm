package com.twitter.search.earlybird.partition;

import java.io.IOEtwitterception;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.metrics.SearchTimer;
import com.twitter.search.common.util.io.dl.DLRecordTimestampUtil;
import com.twitter.search.common.util.io.recordreader.RecordReader;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.segment.SegmentDataReaderSet;

/**
 * Indetwitteres all updates for a complete segment at startup.
 */
public class SimpleUpdateIndetwitterer {
  private static final Logger LOG = LoggerFactory.getLogger(SimpleUpdateIndetwitterer.class);

  private final SegmentDataReaderSet readerSet;
  private final SearchIndetwitteringMetricSet partitionIndetwitteringMetricSet;
  private final InstrumentedQueue<ThriftVersionedEvents> retryQueue;
  private final CriticalEtwitterceptionHandler criticalEtwitterceptionHandler;

  public SimpleUpdateIndetwitterer(SegmentDataReaderSet readerSet,
                             SearchIndetwitteringMetricSet partitionIndetwitteringMetricSet,
                             InstrumentedQueue<ThriftVersionedEvents> retryQueue,
                             CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    this.readerSet = readerSet;
    this.partitionIndetwitteringMetricSet = partitionIndetwitteringMetricSet;
    this.retryQueue = retryQueue;
    this.criticalEtwitterceptionHandler = criticalEtwitterceptionHandler;
  }

  /**
   * Indetwitteres all updates for the given segment.
   */
  public void indetwitterAllUpdates(SegmentInfo segmentInfo) {
    Preconditions.checkState(
        segmentInfo.isEnabled() && segmentInfo.isComplete() && !segmentInfo.isIndetwittering());

    try {
      readerSet.attachUpdateReaders(segmentInfo);
    } catch (IOEtwitterception e) {
      throw new RuntimeEtwitterception("Could not attach readers for segment: " + segmentInfo, e);
    }

    RecordReader<ThriftVersionedEvents> reader =
        readerSet.getUpdateEventsReaderForSegment(segmentInfo);
    if (reader == null) {
      return;
    }

    LOG.info("Got updates reader (starting timestamp = {}) for segment {}: {}",
             DLRecordTimestampUtil.recordIDToTimestamp(reader.getOffset()),
             segmentInfo.getSegmentName(),
             reader);

    // The segment is complete (we check this in indetwitterAllUpdates()), so we can safely get
    // the smallest and largest tweet IDs in this segment.
    long lowestTweetId = segmentInfo.getIndetwitterSegment().getLowestTweetId();
    long highestTweetId = segmentInfo.getIndetwitterSegment().getHighestTweetId();
    Preconditions.checkArgument(
        lowestTweetId > 0,
        "Could not get the lowest tweet ID in segment " + segmentInfo.getSegmentName());
    Preconditions.checkArgument(
        highestTweetId > 0,
        "Could not get the highest tweet ID in segment " + segmentInfo.getSegmentName());

    SegmentWriter segmentWriter =
        new SegmentWriter(segmentInfo, partitionIndetwitteringMetricSet.updateFreshness);

    LOG.info("Starting to indetwitter updates for segment: {}", segmentInfo.getSegmentName());
    Stopwatch stopwatch = Stopwatch.createStarted();

    while (!Thread.currentThread().isInterrupted() && !reader.isCaughtUp()) {
      applyUpdate(segmentInfo, reader, segmentWriter, lowestTweetId, highestTweetId);
    }

    LOG.info("Finished indetwittering updates for segment {} in {} seconds.",
             segmentInfo.getSegmentName(),
             stopwatch.elapsed(TimeUnit.SECONDS));
  }

  private void applyUpdate(SegmentInfo segmentInfo,
                           RecordReader<ThriftVersionedEvents> reader,
                           SegmentWriter segmentWriter,
                           long lowestTweetId,
                           long highestTweetId) {
    ThriftVersionedEvents update;
    try {
      update = reader.readNetwittert();
    } catch (IOEtwitterception e) {
      LOG.error("Etwitterception while reading update for segment: " + segmentInfo.getSegmentName(), e);
      criticalEtwitterceptionHandler.handle(this, e);
      return;
    }
    if (update == null) {
      LOG.warn("Update is not available but reader was not caught up. Segment: {}",
               segmentInfo.getSegmentName());
      return;
    }

    try {
      // If the indetwitterer put this update in the wrong timeslice, add it to the retry queue, and
      // let PartitionIndetwitterer retry it (it has logic to apply it to the correct segment).
      if ((update.getId() < lowestTweetId) || (update.getId() > highestTweetId)) {
        retryQueue.add(update);
        return;
      }

      // At this point, we are updating a segment that has every tweet it will ever have,
      // (the segment is complete), so there is no point queueing an update to retry it.
      SearchTimer timer = partitionIndetwitteringMetricSet.updateStats.startNewTimer();
      segmentWriter.indetwitterThriftVersionedEvents(update);
      partitionIndetwitteringMetricSet.updateStats.stopTimerAndIncrement(timer);

      updateUpdatesStreamTimestamp(segmentInfo);
    } catch (IOEtwitterception e) {
      LOG.error("Etwitterception while indetwittering updates for segment: " + segmentInfo.getSegmentName(), e);
      criticalEtwitterceptionHandler.handle(this, e);
    }
  }

  private void updateUpdatesStreamTimestamp(SegmentInfo segmentInfo) {
    Optional<Long> offset = readerSet.getUpdateEventsStreamOffsetForSegment(segmentInfo);
    if (!offset.isPresent()) {
      LOG.info("Unable to get updates stream offset for segment: {}", segmentInfo.getSegmentName());
    } else {
      long offsetTimeMillis = DLRecordTimestampUtil.recordIDToTimestamp(offset.get());
      segmentInfo.setUpdatesStreamOffsetTimestamp(offsetTimeMillis);
    }
  }
}
