package com.twitter.search.earlybird.partition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.twitter.search.common.database.DatabaseConfig;
import com.twitter.search.common.metrics.SearchCustomGauge;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.partitioning.base.Segment;
import com.twitter.search.common.schema.earlybird.FlushVersion;
import com.twitter.search.common.util.io.flushable.PersistentFile;
import com.twitter.search.earlybird.archive.ArchiveSegment;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.common.config.EarlybirdProperty;
import com.twitter.search.earlybird.util.ScrubGenUtil;
import com.twitter.util.TwitterDateFormat;

/**
 * Encapsulates config information related to reading and writing segments to local filesystem or
 * HDFS.
 */
public class SegmentSyncConfig {
  public static final String LUCENE_DIR_PREFItwitter = "lucene_";

  private final Optional<String> scrubGen;

  public SegmentSyncConfig(Optional<String> scrubGen) {
    this.scrubGen = scrubGen;
    String scrubGenStat = scrubGen.orElse("unset");
    SearchLongGauge.etwitterport("scrub_gen_" + scrubGenStat).set(1);
    if (scrubGen.isPresent()) {
      // Etwitterport a stat for the number of days between the scrub gen date and now
      SearchCustomGauge.etwitterport("scrub_gen_age_in_days", () -> {
        long scrubGenMillis = ScrubGenUtil.parseScrubGenToDate(scrubGen.get()).getTime();
        return TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - scrubGenMillis);
      });
    }
  }

  /**
   * Returns the file etwittertension to be used for the current flush version.
   */
  public String getVersionFileEtwittertension() {
    return FlushVersion.CURRENT_FLUSH_VERSION.getVersionFileEtwittertension();
  }

  /**
   * Returns the threshold for how large a segment's status count must be at load time to be
   * considered valid.
   */
  public int getMinSegmentStatusCountThreshold() {
    double minSegmentTweetCountProportionThreshold =
        EarlybirdConfig.getDouble("min_segment_tweet_count_percentage_threshold", 0) / 100;
    return (int) (EarlybirdConfig.getMatwitterSegmentSize() * minSegmentTweetCountProportionThreshold);
  }

  /**
   * Determines if this earlybird is allowed to flush segments to HDFS.
   */
  public boolean isFlushToHdfsEnabled() {
    return EarlybirdProperty.SEGMENT_FLUSH_TO_HDFS_ENABLED.get(false)
        // Flush to HDFS is always disabled if FlushVersion is not official.
        && FlushVersion.CURRENT_FLUSH_VERSION.isOfficial();
  }

  /**
   * Determines if this earlybird is allowed to load segments from HDFS.
   */
  public boolean isSegmentLoadFromHdfsEnabled() {
    return EarlybirdProperty.SEGMENT_LOAD_FROM_HDFS_ENABLED.get(false);
  }

  /**
   * Determines if this earlybird is allowed to delete flushed segments.
   */
  public boolean isDeleteFlushedSegmentsEnabled() {
    return EarlybirdConfig.getBool("segment_dropper_delete_flushed", true);
  }

  /**
   * Returns the root of the segment directory on the local disk.
   */
  public String getLocalSegmentSyncRootDir() {
    return EarlybirdConfig.getString("segment_sync_dir", "partitions")
        + getScrubGenFlushDirSuffitwitter();
  }

  /**
   * Returns the root of the segment directory on HDFS.
   */
  public String getHdfsSegmentSyncRootDir() {
    return EarlybirdProperty.HDFS_SEGMENT_SYNC_DIR.get("partitions")
        + getScrubGenFlushDirSuffitwitter();
  }

  /**
   * Returns the HDFS root directory where all segments should be uploaded.
   */
  public String getHdfsSegmentUploadRootDir() {
    String hdfsSegmentUploadDir = EarlybirdProperty.HDFS_SEGMENT_UPLOAD_DIR.get(null);
    return hdfsSegmentUploadDir != null
        ? hdfsSegmentUploadDir + getScrubGenFlushDirSuffitwitter()
        : getHdfsSegmentSyncRootDir();
  }

  /**
   * Returns the ZooKeeper path used for segment sync'ing.
   */
  public String getZooKeeperSyncFullPath() {
    return EarlybirdProperty.ZK_APP_ROOT.get() + "/"
        + EarlybirdConfig.getString("segment_flush_sync_relative_path", "segment_flush_sync");
  }

  /**
   * Returns the list of directories that should be persisted for this segment.
   */
  public Collection<String> getPersistentFileNames(SegmentInfo segment) {
    return Collections.singleton(segment.getSegmentName());
  }

  /**
   * Returns the list of all files that should be sync'ed for this segment.
   */
  public Collection<String> getAllSyncFileNames(SegmentInfo segment) {
    Collection<String> allFileNames = PersistentFile.getAllFileNames(segment.getSegmentName());
    if (segment.getEarlybirdIndetwitterConfig().isIndetwitterStoredOnDisk()) {
      allFileNames = new ArrayList<>(allFileNames);
      // Just the file name, not the full path
      allFileNames.add(getLocalLuceneSyncDirFileName(segment.getSegment()));
    }
    return allFileNames;
  }

  /**
   * Returns the local sync directory for the given segment.
   */
  public String getLocalSyncDirName(Segment segment) {
    return getLocalSegmentSyncRootDir() + "/" + segment.getSegmentName()
        + getVersionFileEtwittertension();
  }

  /**
   * Returns the local Lucene directory for the given segment.
   */
  public String getLocalLuceneSyncDirName(Segment segment) {
    return getLocalSyncDirName(segment) + "/" + getLocalLuceneSyncDirFileName(segment);
  }

  /**
   * Returns the name (not the path) of the Lucene directory for the given segment.
   */
  private String getLocalLuceneSyncDirFileName(Segment segment) {
    if (segment instanceof ArchiveSegment) {
      Date endDate = ((ArchiveSegment) segment).getDataEndDate();
      String endDateString = TwitterDateFormat.apply("yyyyMMdd").format(endDate);
      return LUCENE_DIR_PREFItwitter + endDateString;
    } else {
      return LUCENE_DIR_PREFItwitter + "realtime";
    }
  }

  /**
   * Returns the HDFS sync directory for the given segment.
   */
  public String getHdfsSyncDirNamePrefitwitter(Segment segment) {
    return getHdfsSegmentSyncRootDir() + "/" + segment.getSegmentName()
        + getVersionFileEtwittertension() + "*";
  }

  /**
   * Returns the prefitwitter of the HDFS directory where the files for this segment should be uploaded.
   */
  public String getHdfsUploadDirNamePrefitwitter(Segment segment) {
    return getHdfsSegmentUploadRootDir() + "/" + segment.getSegmentName()
        + getVersionFileEtwittertension() + "*";
  }

  /**
   * Returns the HDFS directory where the files for this segment should be uploaded.
   */
  public String getHdfsFlushDirName(Segment segment) {
    return getHdfsSegmentUploadRootDir() + "/" + segment.getSegmentName()
        + getVersionFileEtwittertension() + "_" + DatabaseConfig.getLocalHostname();
  }

  /**
   * Returns a temp HDFS directory to be used for this segment.
   */
  public String getHdfsTempFlushDirName(Segment segment) {
    return getHdfsSegmentUploadRootDir() + "/temp_"
        + DatabaseConfig.getLocalHostname() + "_" + segment.getSegmentName()
        + getVersionFileEtwittertension();
  }

  /**
   * Concatenates the name of this segment with the flush version etwittertension.
   */
  public String getVersionedName(Segment segment) {
    return segment.getSegmentName() + getVersionFileEtwittertension();
  }

  private String getScrubGenFlushDirSuffitwitter() {
    return scrubGen
        .map(s -> "/scrubbed/" + s)
        .orElse("");
  }

  /**
   * Returns the scrub gen set for this earlybird.
   */
  public Optional<String> getScrubGen() {
    return scrubGen;
  }
}
