package com.twitter.search.earlybird.partition;

import java.io.IOEtwitterception;
import java.util.concurrent.TimeUnit;

import javatwitter.annotation.Nullable;

import com.google.common.base.Stopwatch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.metrics.SearchTimer;
import com.twitter.search.common.util.io.recordreader.RecordReader;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.document.TweetDocument;
import com.twitter.search.earlybird.indetwitter.EarlybirdSegment;

/**
 * SimpleSegmentIndetwitter indetwitteres all Tweets for a *complete* segment. It does not indetwitter any updates or
 * deletes.
 */
public class SimpleSegmentIndetwitterer {
  private static final Logger LOG = LoggerFactory.getLogger(SimpleSegmentIndetwitterer.class);

  /**
   * If not null, this segment is appended at the end after indetwittering finishes.
   */
  @Nullable
  private final SegmentInfo segmentToAppend;

  private final RecordReader<TweetDocument> tweetReader;
  private final SearchIndetwitteringMetricSet partitionIndetwitteringMetricSet;

  // Segment we are indetwittering.
  private EarlybirdSegment indetwitteringSegment;

  // Total number of statuses indetwittered in this segment.
  private long segmentSize = 0;

  public SimpleSegmentIndetwitterer(
      RecordReader<TweetDocument> tweetReader,
      SearchIndetwitteringMetricSet partitionIndetwitteringMetricSet) {
    this(tweetReader, partitionIndetwitteringMetricSet, null);
  }

  public SimpleSegmentIndetwitterer(RecordReader<TweetDocument> tweetReader,
                              SearchIndetwitteringMetricSet partitionIndetwitteringMetricSet,
                              @Nullable SegmentInfo segmentToAppend) {
    this.tweetReader = tweetReader;
    this.segmentToAppend = segmentToAppend;
    this.partitionIndetwitteringMetricSet = partitionIndetwitteringMetricSet;
  }

  private boolean shouldIndetwitterSegment(SegmentInfo segmentInfo) {
    if (!segmentInfo.isEnabled()) {
      return false;
    }

    if (segmentToAppend != null) {
      return true;
    }

    return !segmentInfo.isComplete()
        && !segmentInfo.isIndetwittering()
        && !segmentInfo.getSyncInfo().isLoaded();
  }

  /**
   * Indetwitteres all tweets for a complete segment.
   */
  public boolean indetwitterSegment(SegmentInfo segmentInfo) {
    LOG.info("Indetwittering segment " + segmentInfo.getSegmentName());
    if (!shouldIndetwitterSegment(segmentInfo)) {
      return false;
    }

    // If we're starting to indetwitter, we're not complete, will become complete if we
    // were successful here.
    segmentInfo.setComplete(false);

    try {
      segmentInfo.setIndetwittering(true);
      indetwitteringSegment = segmentInfo.getIndetwitterSegment();

      // if we're updating the segment, then we'll indetwitter only the new available days
      // and then append the lucene indetwitter from the old segment
      // If segmentToAppend is not null, it means we are updating a segment.
      if (indetwitteringSegment.tryToLoadEtwitteristingIndetwitter()) {
        segmentInfo.getSyncInfo().setLoaded(true);
        LOG.info("Loaded etwitteristing indetwitter for " + segmentInfo + ", not indetwittering.");
      } else {
        indetwitteringLoop();
        if (segmentToAppend != null) {
          indetwitteringSegment.append(segmentToAppend.getIndetwitterSegment());
        }
      }

      segmentInfo.setIndetwittering(false);
      segmentInfo.setComplete(true);
      segmentInfo.setWasIndetwittered(true);
      LOG.info("Successfully indetwittered segment " + segmentInfo.getSegmentName());
      return true;
    } catch (Etwitterception e) {
      LOG.error("Etwitterception while indetwittering IndetwitterSegment " + segmentInfo
          + " after " + indetwitteringSegment.getIndetwitterStats().getStatusCount() + " documents.", e);
      partitionIndetwitteringMetricSet.simpleSegmentIndetwittererEtwitterceptionCounter.increment();

      LOG.warn("Failed to load a new day into full archive. Cleaning up segment: "
          + indetwitteringSegment.getSegmentName());

      // Clean up the lucene dir if it etwitterists. Earlybird will retry loading the new day again later.
      if (!segmentInfo.deleteLocalIndetwitteredSegmentDirectoryImmediately()) {
        LOG.error("Failed to clean up indetwitter segment folder after indetwittering failures.");
      }

      return false;
    } finally {
      if (tweetReader != null) {
        tweetReader.stop();
      }
      segmentInfo.setIndetwittering(false);
    }
  }

  // Indetwitteres a document if available.  Returns true if indetwitter was updated.
  protected boolean indetwitterDocument(TweetDocument tweetDocument) throws IOEtwitterception {
    if (tweetDocument == null) {
      return false;
    }

    SearchTimer timer = partitionIndetwitteringMetricSet.statusStats.startNewTimer();
    indetwitteringSegment.addDocument(tweetDocument);
    partitionIndetwitteringMetricSet.statusStats.stopTimerAndIncrement(timer);
    segmentSize++;
    return true;
  }

  /**
   * Indetwitteres all tweets for this segment, until no more tweets are available.
   *
   * @throws InterruptedEtwitterception If the thread is interrupted while indetwittering tweets.
   * @throws IOEtwitterception If there's a problem reading or indetwittering tweets.
   */
  public void indetwitteringLoop() throws InterruptedEtwitterception, IOEtwitterception {
    Stopwatch stopwatch = Stopwatch.createStarted();

    Stopwatch readingStopwatch = Stopwatch.createUnstarted();
    Stopwatch indetwitteringStopwatch = Stopwatch.createUnstarted();

    int indetwitteredDocumentsCount = 0;
    SearchLongGauge timeToIndetwitterSegment = SearchLongGauge.etwitterport("time_to_indetwitter_segment");
    timeToIndetwitterSegment.set(0);
    if (tweetReader != null) {
      while (!tweetReader.isEtwitterhausted() && !Thread.currentThread().isInterrupted()) {
        readingStopwatch.start();
        TweetDocument tweetDocument = tweetReader.readNetwittert();
        readingStopwatch.stop();

        indetwitteringStopwatch.start();
        boolean documentIndetwittered = indetwitterDocument(tweetDocument);
        indetwitteringStopwatch.stop();

        if (!documentIndetwittered) {
          // No documents waiting to be indetwittered.  Take a nap.
          Thread.sleep(10);
        } else {
          indetwitteredDocumentsCount++;
        }

        if (segmentSize >= EarlybirdConfig.getMatwitterSegmentSize()) {
          LOG.error("Reached matwitter segment size " + segmentSize + ", stopping indetwitterer");
          partitionIndetwitteringMetricSet.matwitterSegmentSizeReachedCounter.increment();
          tweetReader.stop();
          break;
        }
      }
    }

    timeToIndetwitterSegment.set(stopwatch.elapsed(TimeUnit.MILLISECONDS));

    LOG.info("SimpleSegmentIndetwitterer finished: {}. Documents: {}",
        indetwitteringSegment.getSegmentName(), indetwitteredDocumentsCount);
    LOG.info("Time taken: {}, Reading time: {}, Indetwittering time: {}",
        stopwatch, readingStopwatch, indetwitteringStopwatch);
    LOG.info("Total Memory: {}, Free Memory: {}",
        Runtime.getRuntime().totalMemory(), Runtime.getRuntime().freeMemory());
  }
}
