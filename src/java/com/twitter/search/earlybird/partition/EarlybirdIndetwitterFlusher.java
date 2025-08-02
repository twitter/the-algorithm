package com.twitter.search.earlybird.partition;

import java.io.File;
import java.io.IOEtwitterception;
import java.io.OutputStreamWriter;
import java.tetwittert.DateFormat;
import java.tetwittert.ParseEtwitterception;
import java.tetwittert.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeoutEtwitterception;

import scala.runtime.BotwitteredUnit;

import com.google.common.base.Preconditions;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.search.common.config.Config;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.schema.earlybird.FlushVersion;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.earlybird.common.NonPagingAssert;
import com.twitter.search.earlybird.util.ActionLogger;
import com.twitter.search.earlybird.util.CoordinatedEarlybirdActionInterface;
import com.twitter.search.earlybird.util.CoordinatedEarlybirdActionLockFailed;
import com.twitter.search.earlybird.util.ParallelUtil;

/**
 * Flushes an EarlybirdIndetwitter to HDFS, so that when Earlybird starts, it can read the indetwitter from
 * HDFS instead of indetwittering from scratch.
 *
 * The path looks like:
 * /smf1/rt2/user/search/earlybird/loadtest/realtime/indetwitteres/flush_version_158/partition_8/indetwitter_2020_02_25_02
 */
public class EarlybirdIndetwitterFlusher {
  public enum FlushAttemptResult {
    CHECKED_RECENTLY,
    FOUND_INDEtwitter,
    FLUSH_ATTEMPT_MADE,
    FAILED_LOCK_ATTEMPT,
    HADOOP_TIMEOUT
  }

  @FunctionalInterface
  public interface PostFlushOperation {
    /**
     * Run this after we finish flushing an indetwitter, before we rejoin the serverset.
     */
    void etwitterecute();
  }

  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdIndetwitterFlusher.class);

  private static final SearchCounter FLUSH_SUCCESS_COUNTER =
      SearchCounter.etwitterport("successfully_flushed_indetwitter");

  public static final String TWEET_KAFKA_OFFSET = "tweet_kafka_offset";
  public static final String UPDATE_KAFKA_OFFSET = "update_kafka_offset";
  public static final String FLUSHED_FROM_REPLICA = "flushed_from_replica";
  public static final String SEGMENTS = "segments";
  public static final String TIMESLICE_ID = "timeslice_id";

  public static final String DATA_SUFFItwitter = ".data";
  public static final String INFO_SUFFItwitter = ".info";
  public static final String INDEtwitter_INFO = "earlybird_indetwitter.info";

  private static final String INDEtwitter_PATH_FORMAT = "%s/flush_version_%d/partition_%d";
  public static final DateFormat INDEtwitter_DATE_SUFFItwitter = new SimpleDateFormat("yyyy_MM_dd_HH");
  public static final String INDEtwitter_PREFItwitter = "indetwitter_";
  public static final String TMP_PREFItwitter = "tmp_";

  // Check if we need to flush every five minutes.
  private static final long FLUSH_CHECK_PERIOD = Duration.ofMinutes(5).toMillis();

  // Make sure we don't keep more than 3 copies of the indetwitter in HDFS, so that we don't run out of
  // HDFS space.
  private static final int INDEtwitter_COPIES = 3;

  private static final NonPagingAssert FLUSHING_TOO_MANY_NON_OPTIMIZED_SEGMENTS =
          new NonPagingAssert("flushing_too_many_non_optimized_segments");

  private final CoordinatedEarlybirdActionInterface actionCoordinator;
  private final FileSystem fileSystem;
  private final Path indetwitterPath;
  private final Clock clock;
  private final SegmentManager segmentManager;
  private final int replicaId;
  private final TimeLimitedHadoopEtwitteristsCall timeLimitedHadoopEtwitteristsCall;
  private final OptimizationAndFlushingCoordinationLock optimizationAndFlushingCoordinationLock;

  private long checkedAt = 0;

  public EarlybirdIndetwitterFlusher(
      CoordinatedEarlybirdActionInterface actionCoordinator,
      FileSystem fileSystem,
      String indetwitterHDFSPath,
      SegmentManager segmentManager,
      PartitionConfig partitionConfig,
      Clock clock,
      TimeLimitedHadoopEtwitteristsCall timeLimitedHadoopEtwitteristsCall,
      OptimizationAndFlushingCoordinationLock optimizationAndFlushingCoordinationLock
  ) {
    this.actionCoordinator = actionCoordinator;
    this.fileSystem = fileSystem;
    this.indetwitterPath = buildPathToIndetwitteres(indetwitterHDFSPath, partitionConfig);
    this.segmentManager = segmentManager;
    this.clock = clock;
    this.replicaId = partitionConfig.getHostPositionWithinHashPartition();
    this.timeLimitedHadoopEtwitteristsCall = timeLimitedHadoopEtwitteristsCall;
    this.optimizationAndFlushingCoordinationLock = optimizationAndFlushingCoordinationLock;
  }

  /**
   * Periodically checks if an indetwitter needs to be uploaded to HDFS, and uploads it if necessary.
   * Skips flush if unable to acquire the optimizationAndFlushingCoordinationLock.
   */
  public FlushAttemptResult flushIfNecessary(
      long tweetOffset,
      long updateOffset,
      PostFlushOperation postFlushOperation) throws Etwitterception {
    long now = clock.nowMillis();
    if (now - checkedAt < FLUSH_CHECK_PERIOD) {
      return FlushAttemptResult.CHECKED_RECENTLY;
    }

    checkedAt = now;

    // Try to aqcuire lock to ensure that we are not in the gc_before_optimization or the
    // post_optimization_rebuilds step of optimization. If the lock is not available, then skip
    // flushing.
    if (!optimizationAndFlushingCoordinationLock.tryLock()) {
      return FlushAttemptResult.FAILED_LOCK_ATTEMPT;
    }
    // Acquired the lock, so wrap the flush in a try/finally block to ensure we release the lock
    try {
      Path flushPath = pathForHour();

      try {
        // If this doesn't etwitterecute on time, it will throw an etwitterception and this function
        // finishes its etwitterecution.
        boolean result = timeLimitedHadoopEtwitteristsCall.etwitterists(flushPath);

        if (result) {
          return FlushAttemptResult.FOUND_INDEtwitter;
        }
      } catch (TimeoutEtwitterception e) {
        LOG.warn("Timeout while calling hadoop", e);
        return FlushAttemptResult.HADOOP_TIMEOUT;
      }

      boolean flushedIndetwitter = false;
      try {
        // this function returns a boolean.
        actionCoordinator.etwitterecute("indetwitter_flushing", isCoordinated ->
            flushIndetwitter(flushPath, isCoordinated, tweetOffset, updateOffset, postFlushOperation));
        flushedIndetwitter = true;
      } catch (CoordinatedEarlybirdActionLockFailed e) {
        // This only happens when we fail to grab the lock, which is fine because another Earlybird
        // is already working on flushing this indetwitter, so we don't need to.
        LOG.debug("Failed to grab lock", e);
      }

      if (flushedIndetwitter) {
        // We don't return with a guarantee that we actually flushed something. It's possible
        // that the .etwitterecute() function above was not able to leave the server set to flush.
        return FlushAttemptResult.FLUSH_ATTEMPT_MADE;
      } else {
        return FlushAttemptResult.FAILED_LOCK_ATTEMPT;
      }
    } finally {
      optimizationAndFlushingCoordinationLock.unlock();
    }
  }

  /**
   * Create a subpath to the directory with many indetwitteres in it. Will have an indetwitter for each hour.
   */
  public static Path buildPathToIndetwitteres(String root, PartitionConfig partitionConfig) {
    return new Path(String.format(
        INDEtwitter_PATH_FORMAT,
        root,
        FlushVersion.CURRENT_FLUSH_VERSION.getVersionNumber(),
        partitionConfig.getIndetwitteringHashPartitionID()));
  }


  /**
   * Returns a sorted map from the unitwitter time in millis an indetwitter was flushed to the path of an indetwitter.
   * The last element will be the path of the most recent indetwitter.
   */
  public static SortedMap<Long, Path> getIndetwitterPathsByTime(
      Path indetwitterPath,
      FileSystem fileSystem
  ) throws IOEtwitterception, ParseEtwitterception {
    LOG.info("Getting indetwitter paths from file system: {}", fileSystem.getUri().toASCIIString());

    SortedMap<Long, Path> pathByTime = new TreeMap<>();
    Path globPattern = indetwitterPath.suffitwitter("/" + EarlybirdIndetwitterFlusher.INDEtwitter_PREFItwitter + "*");
    LOG.info("Lookup glob pattern: {}", globPattern);

    for (FileStatus indetwitterDir : fileSystem.globStatus(globPattern)) {
      String name = new File(indetwitterDir.getPath().toString()).getName();
      String dateString = name.substring(EarlybirdIndetwitterFlusher.INDEtwitter_PREFItwitter.length());
      Date date = EarlybirdIndetwitterFlusher.INDEtwitter_DATE_SUFFItwitter.parse(dateString);
      pathByTime.put(date.getTime(), indetwitterDir.getPath());
    }
    LOG.info("Found {} files matching the pattern.", pathByTime.size());

    return pathByTime;
  }

  private boolean flushIndetwitter(
      Path flushPath,
      boolean isCoordinated,
      long tweetOffset,
      long updateOffset,
      PostFlushOperation postFlushOperation
  ) throws Etwitterception {
    Preconditions.checkState(isCoordinated);

    if (fileSystem.etwitterists(flushPath)) {
      return false;
    }

    LOG.info("Starting indetwitter flush");

    // In case the process is killed suddenly, we wouldn't be able to clean up the temporary
    // directory, and we don't want other processes to reuse it, so add some randomness.
    Path tmpPath = indetwitterPath.suffitwitter("/" + TMP_PREFItwitter + RandomStringUtils.randomAlphabetic(8));
    boolean creationSucceed = fileSystem.mkdirs(tmpPath);
    if (!creationSucceed) {
      throw new IOEtwitterception("Couldn't create HDFS directory at " + flushPath);
    }

    LOG.info("Temp path: {}", tmpPath);
    try {
      ArrayList<SegmentInfo> segmentInfos = Lists.newArrayList(segmentManager.getSegmentInfos(
          SegmentManager.Filter.Enabled, SegmentManager.Order.NEW_TO_OLD).iterator());
      segmentManager.logState("Before flushing");
      EarlybirdIndetwitter indetwitter = new EarlybirdIndetwitter(segmentInfos, tweetOffset, updateOffset);
      ActionLogger.run(
          "Flushing indetwitter to " + tmpPath,
          () -> flushIndetwitter(tmpPath, indetwitter));
    } catch (Etwitterception e) {
      LOG.error("Etwitterception while flushing indetwitter. Rethrowing.");

      if (fileSystem.delete(tmpPath, true)) {
        LOG.info("Successfully deleted temp output");
      } else {
        LOG.error("Couldn't delete temp output");
      }

      throw e;
    }

    // We flush it to a temporary directory, then rename the temporary directory so that it the
    // change is atomic, and other Earlybirds will either see the old indetwitteres, or the new, complete
    // indetwitter, but never an in progress indetwitter.
    boolean renameSucceeded = fileSystem.rename(tmpPath, flushPath);
    if (!renameSucceeded) {
      throw new IOEtwitterception("Couldn't rename HDFS from " + tmpPath + " to " + flushPath);
    }
    LOG.info("Flushed indetwitter to {}", flushPath);

    cleanupOldIndetwitteres();

    FLUSH_SUCCESS_COUNTER.increment();

    LOG.info("Etwitterecuting post flush operation...");
    postFlushOperation.etwitterecute();

    return true;
  }

  private void cleanupOldIndetwitteres() throws Etwitterception {
    LOG.info("Looking up whether we need to clean up old indetwitteres...");
    SortedMap<Long, Path> pathsByTime =
        EarlybirdIndetwitterFlusher.getIndetwitterPathsByTime(indetwitterPath, fileSystem);

    while (pathsByTime.size() > INDEtwitter_COPIES) {
      Long key = pathsByTime.firstKey();
      Path oldestHourPath = pathsByTime.remove(key);
      LOG.info("Deleting old indetwitter at path '{}'.", oldestHourPath);

      if (fileSystem.delete(oldestHourPath, true)) {
        LOG.info("Successfully deleted old indetwitter");
      } else {
        LOG.error("Couldn't delete old indetwitter");
      }
    }
  }

  private Path pathForHour() {
    Date date = new Date(clock.nowMillis());
    String time = INDEtwitter_DATE_SUFFItwitter.format(date);
    return indetwitterPath.suffitwitter("/" + INDEtwitter_PREFItwitter + time);
  }

  private void flushIndetwitter(Path flushPath, EarlybirdIndetwitter indetwitter) throws Etwitterception {
    int numOfNonOptimized = indetwitter.numOfNonOptimizedSegments();
    if (numOfNonOptimized > EarlybirdIndetwitter.MAtwitter_NUM_OF_NON_OPTIMIZED_SEGMENTS) {
      LOG.error(
              "Found {} non-optimized segments when flushing to disk!", numOfNonOptimized);
      FLUSHING_TOO_MANY_NON_OPTIMIZED_SEGMENTS.assertFailed();
    }

    int numSegments = indetwitter.getSegmentInfoList().size();
    int flushingThreadPoolSize = numSegments;

    if (Config.environmentIsTest()) {
      // SEARCH-33763: Limit the thread pool size for tests to avoid using too much memory on scoot.
      flushingThreadPoolSize = 2;
    }

    LOG.info("Flushing indetwitter using a thread pool size of {}", flushingThreadPoolSize);

    ParallelUtil.parmap("flush-indetwitter", flushingThreadPoolSize, si -> ActionLogger.call(
        "Flushing segment " + si.getSegmentName(),
        () -> flushSegment(flushPath, si)), indetwitter.getSegmentInfoList());

    FlushInfo indetwitterInfo = new FlushInfo();
    indetwitterInfo.addLongProperty(UPDATE_KAFKA_OFFSET, indetwitter.getUpdateOffset());
    indetwitterInfo.addLongProperty(TWEET_KAFKA_OFFSET, indetwitter.getTweetOffset());
    indetwitterInfo.addIntProperty(FLUSHED_FROM_REPLICA, replicaId);

    FlushInfo segmentFlushInfos = indetwitterInfo.newSubProperties(SEGMENTS);
    for (SegmentInfo segmentInfo : indetwitter.getSegmentInfoList()) {
      FlushInfo segmentFlushInfo = segmentFlushInfos.newSubProperties(segmentInfo.getSegmentName());
      segmentFlushInfo.addLongProperty(TIMESLICE_ID, segmentInfo.getTimeSliceID());
    }

    Path indetwitterInfoPath = flushPath.suffitwitter("/" + INDEtwitter_INFO);
    try (FSDataOutputStream infoOutputStream = fileSystem.create(indetwitterInfoPath)) {
      OutputStreamWriter infoFileWriter = new OutputStreamWriter(infoOutputStream);
      FlushInfo.flushAsYaml(indetwitterInfo, infoFileWriter);
    }
  }

  private BotwitteredUnit flushSegment(Path flushPath, SegmentInfo segmentInfo) throws Etwitterception {
    Path segmentPrefitwitter = flushPath.suffitwitter("/" + segmentInfo.getSegmentName());
    Path segmentPath = segmentPrefitwitter.suffitwitter(DATA_SUFFItwitter);

    FlushInfo flushInfo = new FlushInfo();

    try (FSDataOutputStream outputStream = fileSystem.create(segmentPath)) {
      DataSerializer out = new DataSerializer(segmentPath.toString(), outputStream);
      segmentInfo.getIndetwitterSegment().flush(flushInfo, out);
    }

    Path infoPath = segmentPrefitwitter.suffitwitter(INFO_SUFFItwitter);

    try (FSDataOutputStream infoOutputStream = fileSystem.create(infoPath)) {
      OutputStreamWriter infoFileWriter = new OutputStreamWriter(infoOutputStream);
      FlushInfo.flushAsYaml(flushInfo, infoFileWriter);
    }
    return BotwitteredUnit.UNIT;
  }
}
