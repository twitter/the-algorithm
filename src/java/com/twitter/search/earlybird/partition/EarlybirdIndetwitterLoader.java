package com.twitter.search.earlybird.partition;

import java.io.BufferedInputStream;
import java.io.IOEtwitterception;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;

import com.google.common.base.Stopwatch;

import org.apache.commons.compress.utils.Lists;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.search.common.partitioning.base.TimeSlice;
import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.earlybird.common.NonPagingAssert;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.indetwitter.EarlybirdSegmentFactory;
import com.twitter.search.earlybird.util.ActionLogger;
import com.twitter.search.earlybird.util.ParallelUtil;

/**
 * Loads an indetwitter from HDFS, if possible, or indetwitteres all tweets from scratch using a
 * FreshStartupHandler.
 */
public class EarlybirdIndetwitterLoader {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdIndetwitterLoader.class);

  public static final String ENV_FOR_TESTS = "test_env";

  // To determine whether we should or should not load the most recent indetwitter from HDFS if available.
  public static final long INDEtwitter_FRESHNESS_THRESHOLD_MILLIS = Duration.ofDays(1).toMillis();

  private static final NonPagingAssert LOADING_TOO_MANY_NON_OPTIMIZED_SEGMENTS =
          new NonPagingAssert("loading_too_many_non_optimized_segments");

  private final FileSystem fileSystem;
  private final Path indetwitterPath;
  private final PartitionConfig partitionConfig;
  private final EarlybirdSegmentFactory earlybirdSegmentFactory;
  private final SegmentSyncConfig segmentSyncConfig;
  private final Clock clock;
  // Aurora environment we're running in: "prod", "loadtest", "staging2" etc. etc
  private final String environment;

  public EarlybirdIndetwitterLoader(
      FileSystem fileSystem,
      String indetwitterHDFSPath,
      String environment,
      PartitionConfig partitionConfig,
      EarlybirdSegmentFactory earlybirdSegmentFactory,
      SegmentSyncConfig segmentSyncConfig,
      Clock clock
  ) {
    this.fileSystem = fileSystem;
    this.partitionConfig = partitionConfig;
    this.earlybirdSegmentFactory = earlybirdSegmentFactory;
    this.segmentSyncConfig = segmentSyncConfig;
    this.indetwitterPath = EarlybirdIndetwitterFlusher.buildPathToIndetwitteres(indetwitterHDFSPath, partitionConfig);
    this.clock = clock;
    this.environment = environment;
  }

  /**
   * Tries to load an indetwitter from HDFS for this FlushVersion/Partition/Cluster. Returns an empty
   * option if there is no indetwitter found.
   */
  public Optional<EarlybirdIndetwitter> loadIndetwitter() {
    try {
      Optional<EarlybirdIndetwitter> loadedIndetwitter =
          ActionLogger.call("Load indetwitter from HDFS.", this::loadFromHDFS);

      if (loadedIndetwitter.isPresent()) {
        EarlybirdIndetwitter indetwitter = loadedIndetwitter.get();
        int numOfNonOptimized = indetwitter.numOfNonOptimizedSegments();
        if (numOfNonOptimized > EarlybirdIndetwitter.MAtwitter_NUM_OF_NON_OPTIMIZED_SEGMENTS) {
          // We should never have too many unoptimized segments. If this happens we likely have a
          // bug somewhere that caused another Earlybird to flush too many unoptimized segments.
          // Use NonPagingAssert to alert the oncall if this happens so they can look into it.
          LOG.error("Found {} non-optimized segments when loading from disk!", numOfNonOptimized);
          LOADING_TOO_MANY_NON_OPTIMIZED_SEGMENTS.assertFailed();

          // If there are too many unoptimized segments, optimize the older ones until there are
          // only MAtwitter_NUM_OF_NON_OPTIMIZED_SEGMENTS left in the unoptimized state. The segment info
          // list is always in order, so we will never try to optimize the most recent segments
          // here.
          int numSegmentsToOptimize =
              numOfNonOptimized - EarlybirdIndetwitter.MAtwitter_NUM_OF_NON_OPTIMIZED_SEGMENTS;
          LOG.info("Will try to optimize {} segments", numSegmentsToOptimize);
          for (SegmentInfo segmentInfo : indetwitter.getSegmentInfoList()) {
            if (numSegmentsToOptimize > 0 && !segmentInfo.isOptimized()) {
              Stopwatch optimizationStopwatch = Stopwatch.createStarted();
              LOG.info("Starting to optimize segment: {}", segmentInfo.getSegmentName());
              segmentInfo.getIndetwitterSegment().optimizeIndetwitteres();
              numSegmentsToOptimize--;
              LOG.info("Optimization of segment {} finished in {}.",
                  segmentInfo.getSegmentName(), optimizationStopwatch);
            }
          }
        }

        int newNumOfNonOptimized = indetwitter.numOfNonOptimizedSegments();
        LOG.info("Loaded {} segments. {} are unoptimized.",
                indetwitter.getSegmentInfoList().size(),
                newNumOfNonOptimized);

        return loadedIndetwitter;
      }
    } catch (Throwable e) {
      LOG.error("Error loading indetwitter from HDFS, will indetwitter from scratch.", e);
    }

    return Optional.empty();
  }

  private Optional<EarlybirdIndetwitter> loadFromHDFS() throws Etwitterception {
    SortedMap<Long, Path> pathsByTime =
        EarlybirdIndetwitterFlusher.getIndetwitterPathsByTime(indetwitterPath, fileSystem);

    if (pathsByTime.isEmpty()) {
      LOG.info("Could not load indetwitter from HDFS (path: {}), will indetwitter from scratch.", indetwitterPath);
      return Optional.empty();
    }

    long mostRecentIndetwitterTimeMillis = pathsByTime.lastKey();
    Path mostRecentIndetwitterPath = pathsByTime.get(mostRecentIndetwitterTimeMillis);

    if (clock.nowMillis() - mostRecentIndetwitterTimeMillis > INDEtwitter_FRESHNESS_THRESHOLD_MILLIS) {
      LOG.info("Most recent indetwitter in HDFS (path: {}) is old, will do a fresh startup.",
              mostRecentIndetwitterPath);
      return Optional.empty();
    }

    EarlybirdIndetwitter indetwitter = ActionLogger.call(
        "loading indetwitter from " + mostRecentIndetwitterPath,
        () -> loadIndetwitter(mostRecentIndetwitterPath));

    return Optional.of(indetwitter);
  }

  private EarlybirdIndetwitter loadIndetwitter(Path flushPath) throws Etwitterception {
    Path indetwitterInfoPath = flushPath.suffitwitter("/" + EarlybirdIndetwitterFlusher.INDEtwitter_INFO);

    FlushInfo indetwitterInfo;
    try (FSDataInputStream infoInputStream = fileSystem.open(indetwitterInfoPath)) {
      indetwitterInfo = FlushInfo.loadFromYaml(infoInputStream);
    }

    FlushInfo segmentsFlushInfo = indetwitterInfo.getSubProperties(EarlybirdIndetwitterFlusher.SEGMENTS);
    List<String> segmentNames = Lists.newArrayList(segmentsFlushInfo.getKeyIterator());

    // This should only happen if you're running in stagingN and loading a prod indetwitter through
    // the read_indetwitter_from_prod_location flag. In this case, we point to a directory that has
    // a lot more than the number of segments we want in staging and we trim this list to the
    // desired number.
    if (environment.matches("staging\\d")) {
      if (segmentNames.size() > partitionConfig.getMatwitterEnabledLocalSegments()) {
        LOG.info("Trimming list of loaded segments from size {} to size {}.",
            segmentNames.size(), partitionConfig.getMatwitterEnabledLocalSegments());
        segmentNames = segmentNames.subList(
            segmentNames.size() - partitionConfig.getMatwitterEnabledLocalSegments(),
            segmentNames.size());
      }
    }

    List<SegmentInfo> segmentInfoList = ParallelUtil.parmap("load-indetwitter", name -> {
      FlushInfo subProperties = segmentsFlushInfo.getSubProperties(name);
      long timesliceID = subProperties.getLongProperty(EarlybirdIndetwitterFlusher.TIMESLICE_ID);
      return ActionLogger.call(
          "loading segment " + name,
          () -> loadSegment(flushPath, name, timesliceID));
    }, segmentNames);

    return new EarlybirdIndetwitter(
        segmentInfoList,
        indetwitterInfo.getLongProperty(EarlybirdIndetwitterFlusher.TWEET_KAFKA_OFFSET),
        indetwitterInfo.getLongProperty(EarlybirdIndetwitterFlusher.UPDATE_KAFKA_OFFSET));
  }

  private SegmentInfo loadSegment(
      Path flushPath,
      String segmentName,
      long timesliceID
  ) throws IOEtwitterception {
    Path segmentPrefitwitter = flushPath.suffitwitter("/" + segmentName);
    Path segmentPath = segmentPrefitwitter.suffitwitter(EarlybirdIndetwitterFlusher.DATA_SUFFItwitter);

    TimeSlice timeSlice = new TimeSlice(
        timesliceID,
        EarlybirdConfig.getMatwitterSegmentSize(),
        partitionConfig.getIndetwitteringHashPartitionID(),
        partitionConfig.getNumPartitions());

    SegmentInfo segmentInfo = new SegmentInfo(
        timeSlice.getSegment(),
        earlybirdSegmentFactory,
        segmentSyncConfig);

    Path infoPath = segmentPrefitwitter.suffitwitter(EarlybirdIndetwitterFlusher.INFO_SUFFItwitter);
    FlushInfo flushInfo;
    try (FSDataInputStream infoInputStream = fileSystem.open(infoPath)) {
      flushInfo = FlushInfo.loadFromYaml(infoInputStream);
    }

    FSDataInputStream inputStream = fileSystem.open(segmentPath);

    // It's significantly slower to read from the FSDataInputStream on demand, so we
    // use a buffered reader to pre-read bigger chunks.
    int bufferSize = 1 << 22; // 4MB
    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, bufferSize);

    DataDeserializer in = new DataDeserializer(bufferedInputStream, segmentName);
    segmentInfo.getIndetwitterSegment().load(in, flushInfo);

    return segmentInfo;
  }
}
