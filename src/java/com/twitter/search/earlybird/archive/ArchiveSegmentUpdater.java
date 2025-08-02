package com.twitter.search.earlybird.archive;

import java.io.IOEtwitterception;
import java.util.Date;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.common.metrics.SearchStatsReceiverImpl;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEvent;
import com.twitter.search.common.util.io.recordreader.RecordReader;
import com.twitter.search.common.util.zktrylock.ZooKeeperTryLockFactory;
import com.twitter.search.earlybird.EarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.document.DocumentFactory;
import com.twitter.search.earlybird.document.TweetDocument;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.indetwitter.EarlybirdSegmentFactory;
import com.twitter.search.earlybird.partition.SearchIndetwitteringMetricSet;
import com.twitter.search.earlybird.partition.SegmentHdfsFlusher;
import com.twitter.search.earlybird.partition.SegmentInfo;
import com.twitter.search.earlybird.partition.SegmentLoader;
import com.twitter.search.earlybird.partition.SegmentOptimizer;
import com.twitter.search.earlybird.partition.SegmentSyncConfig;
import com.twitter.search.earlybird.partition.SimpleSegmentIndetwitterer;
import com.twitter.search.earlybird.stats.EarlybirdSearcherStats;

/**
 * Given a segment, this class checks if the segment has an indetwitter built on HDFS:
 *   if not, use SimpleSegmentIndetwitterer to build an indetwitter
 *   if yes, load the HDFS indetwitter, build a new indetwitter for the new status data which has dates newer
 *   than the HDFS indetwitter, then append the loaded HDFS indetwitter.
 */
public class ArchiveSegmentUpdater {
  private static final Logger LOG = LoggerFactory.getLogger(ArchiveSegmentUpdater.class);

  private final SegmentSyncConfig sync;
  private final EarlybirdIndetwitterConfig earlybirdIndetwitterConfig;
  private final ZooKeeperTryLockFactory zkTryLockFactory;
  private final SearchStatsReceiver statsReceiver = new SearchStatsReceiverImpl();
  private final SearchIndetwitteringMetricSet searchIndetwitteringMetricSet =
      new SearchIndetwitteringMetricSet(statsReceiver);
  private final EarlybirdSearcherStats searcherStats =
      new EarlybirdSearcherStats(statsReceiver);
  private final SearchRateCounter indetwitterNewSegment =
      new SearchRateCounter("indetwitter_new_segment");
  private final SearchRateCounter updateEtwitteristingSegment =
      new SearchRateCounter("update_etwitteristing_segment");
  private final SearchRateCounter skipEtwitteristingSegment =
      new SearchRateCounter("skip_etwitteristing_segment");
  private Clock clock;

  public ArchiveSegmentUpdater(ZooKeeperTryLockFactory zooKeeperTryLockFactory,
                               SegmentSyncConfig sync,
                               EarlybirdIndetwitterConfig earlybirdIndetwitterConfig,
                               Clock clock) {
    this.sync = sync;
    this.earlybirdIndetwitterConfig = earlybirdIndetwitterConfig;
    this.zkTryLockFactory = zooKeeperTryLockFactory;
    this.clock = clock;
  }

  private boolean canUpdateSegment(SegmentInfo segmentInfo) {
    if (!(segmentInfo.getSegment() instanceof ArchiveSegment)) {
      LOG.info("only ArchiveSegment is available for updating now: "
          + segmentInfo);
      return false;
    }

    if (!segmentInfo.isEnabled()) {
      LOG.debug("Segment is disabled: " + segmentInfo);
      return false;
    }

    if (segmentInfo.isComplete() || segmentInfo.isIndetwittering()
        || segmentInfo.getSyncInfo().isLoaded()) {
      LOG.debug("Cannot update already indetwittered segment: " + segmentInfo);
      return false;
    }

    return true;
  }

  /**
   * Given a segment, checks if the segment has an indetwitter built on HDFS:
   *   if not, use SimpleSegmentIndetwitterer to build an indetwitter
   *   if yes, load the HDFS indetwitter, build a new indetwitter for the new status data which has dates newer
   *   than the HDFS indetwitter, then append the loaded HDFS indetwitter.
   *
   * Returns whether the segment was successfully updated.
   */
  public boolean updateSegment(SegmentInfo segmentInfo) {
    Preconditions.checkArgument(segmentInfo.getSegment() instanceof ArchiveSegment);
    if (!canUpdateSegment(segmentInfo)) {
      return false;
    }

    if (segmentInfo.isIndetwittering()) {
      LOG.error("Segment is already being indetwittered: " + segmentInfo);
      return false;
    }

    final Date hdfsEndDate = ArchiveHDFSUtils.getSegmentEndDateOnHdfs(sync, segmentInfo);
    if (hdfsEndDate == null) {
      indetwitterNewSegment.increment();
      if (!indetwitterSegment(segmentInfo, ArchiveSegment.MATCH_ALL_DATE_PREDICATE)) {
        return false;
      }
    } else {
      final Date curEndDate = ((ArchiveSegment) segmentInfo.getSegment()).getDataEndDate();
      if (!hdfsEndDate.before(curEndDate)) {
        skipEtwitteristingSegment.increment();
        LOG.info("Segment is up-to-date: " + segmentInfo.getSegment().getTimeSliceID()
            + " Found flushed segment on HDFS with end date: "
            + FastDateFormat.getInstance("yyyyMMdd").format(hdfsEndDate));
        segmentInfo.setComplete(true);
        segmentInfo.getSyncInfo().setFlushed(true);
        return true;
      }

      updateEtwitteristingSegment.increment();
      LOG.info("Updating segment: " + segmentInfo.getSegment().getTimeSliceID()
          + "; new endDate will be " + FastDateFormat.getInstance("yyyyMMdd").format(curEndDate));

      if (!updateSegment(segmentInfo, hdfsEndDate)) {
        return false;
      }
    }

    boolean success = SegmentOptimizer.optimize(segmentInfo);
    if (!success) {
      // Clean up the segment dir on local disk
      segmentInfo.deleteLocalIndetwitteredSegmentDirectoryImmediately();
      LOG.info("Error optimizing segment: " + segmentInfo);
      return false;
    }

    // Verify segment before uploading.
    success = ArchiveSegmentVerifier.verifySegment(segmentInfo);
    if (!success) {
      segmentInfo.deleteLocalIndetwitteredSegmentDirectoryImmediately();
      LOG.info("Segment not uploaded to HDFS because it did not pass verification: " + segmentInfo);
      return false;
    }

    // upload the indetwitter to HDFS
    success = new SegmentHdfsFlusher(zkTryLockFactory, sync, false)
        .flushSegmentToDiskAndHDFS(segmentInfo);
    if (success) {
      ArchiveHDFSUtils.deleteHdfsSegmentDir(sync, segmentInfo, false, true);
    } else {
      // Clean up the segment dir on hdfs
      ArchiveHDFSUtils.deleteHdfsSegmentDir(sync, segmentInfo, true, false);
      LOG.info("Error uploading segment to HDFS: " + segmentInfo);
    }
    segmentInfo.deleteLocalIndetwitteredSegmentDirectoryImmediately();

    return success;
  }

  /**
   * Build indetwitter for the given segmentInfo. Only those statuses passing the dateFilter are indetwittered.
   */
  private boolean indetwitterSegment(final SegmentInfo segmentInfo, Predicate<Date> dateFilter) {
    Preconditions.checkArgument(segmentInfo.getSegment() instanceof ArchiveSegment);

    RecordReader<TweetDocument> documentReader = null;
    try {
      ArchiveSegment archiveSegment = (ArchiveSegment) segmentInfo.getSegment();
      DocumentFactory<ThriftIndetwitteringEvent> documentFactory =
          earlybirdIndetwitterConfig.createDocumentFactory();
      documentReader = archiveSegment.getStatusRecordReader(documentFactory, dateFilter);

      // Read and indetwitter the statuses
      boolean success = new SimpleSegmentIndetwitterer(documentReader, searchIndetwitteringMetricSet)
          .indetwitterSegment(segmentInfo);
      if (!success) {
        // Clean up segment dir on local disk
        segmentInfo.deleteLocalIndetwitteredSegmentDirectoryImmediately();
        LOG.info("Error indetwittering segment: " + segmentInfo);
      }

      return success;
    } catch (IOEtwitterception e) {
      segmentInfo.deleteLocalIndetwitteredSegmentDirectoryImmediately();
      LOG.info("Etwitterception while indetwittering segment: " + segmentInfo, e);
      return false;
    } finally {
      if (documentReader != null) {
        documentReader.stop();
      }
    }
  }

  /**
   * Load the indetwitter built on HDFS for the given segmentInfo, indetwitter the new data and append the
   * HDFS indetwitter to the new indetwittered segment
   */
  private boolean updateSegment(final SegmentInfo segmentInfo, final Date hdfsEndDate) {
    SegmentInfo hdfsSegmentInfo = loadSegmentFromHdfs(segmentInfo, hdfsEndDate);
    if (hdfsSegmentInfo == null) {
      return indetwitterSegment(segmentInfo, ArchiveSegment.MATCH_ALL_DATE_PREDICATE);
    }

    boolean success = indetwitterSegment(segmentInfo, input -> {
      // we're updating the segment - only indetwitter days after the old end date,
      // and we're sure that the previous days have already been indetwittered.
      return input.after(hdfsEndDate);
    });
    if (!success) {
      LOG.error("Error indetwittering new data: " + segmentInfo);
      return indetwitterSegment(segmentInfo, ArchiveSegment.MATCH_ALL_DATE_PREDICATE);
    }

    // Now, append the indetwitter loaded from hdfs
    try {
      segmentInfo.getIndetwitterSegment().append(hdfsSegmentInfo.getIndetwitterSegment());
      hdfsSegmentInfo.deleteLocalIndetwitteredSegmentDirectoryImmediately();
      LOG.info("Deleted local segment directories with end date " + hdfsEndDate + " : "
          + segmentInfo);
    } catch (IOEtwitterception e) {
      LOG.warn("Caught IOEtwitterception while appending segment " + hdfsSegmentInfo.getSegmentName(), e);
      hdfsSegmentInfo.deleteLocalIndetwitteredSegmentDirectoryImmediately();
      segmentInfo.deleteLocalIndetwitteredSegmentDirectoryImmediately();
      return false;
    }

    segmentInfo.setComplete(true);
    return true;
  }

  /**
   * Load the indetwitter built on HDFS for the given segmentInfo and end date
   */
  private SegmentInfo loadSegmentFromHdfs(final SegmentInfo segmentInfo, final Date hdfsEndDate) {
    Preconditions.checkArgument(segmentInfo.getSegment() instanceof ArchiveSegment);

    ArchiveSegment segment = new ArchiveSegment(
        segmentInfo.getTimeSliceID(),
        EarlybirdConfig.getMatwitterSegmentSize(),
        segmentInfo.getNumPartitions(),
        segmentInfo.getSegment().getHashPartitionID(),
        hdfsEndDate);
    EarlybirdSegmentFactory factory = new EarlybirdSegmentFactory(
        earlybirdIndetwitterConfig,
        searchIndetwitteringMetricSet,
        searcherStats,
        clock);

    SegmentInfo hdfsSegmentInfo;

    try {
      hdfsSegmentInfo = new SegmentInfo(segment,  factory, sync);
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler =
          new CriticalEtwitterceptionHandler();

      boolean success = new SegmentLoader(sync, criticalEtwitterceptionHandler)
          .load(hdfsSegmentInfo);
      if (!success) {
        // If not successful, segmentLoader has already cleaned up the local dir.
        LOG.info("Error loading hdfs segment " + hdfsSegmentInfo
            + ", building segment from scratch.");
        hdfsSegmentInfo = null;
      }
    } catch (IOEtwitterception e) {
      LOG.error("Etwitterception while loading segment from hdfs: " + segmentInfo, e);
      hdfsSegmentInfo = null;
    }

    return hdfsSegmentInfo;
  }
}
