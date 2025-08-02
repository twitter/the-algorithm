package com.twitter.search.earlybird.archive;

import java.io.IOEtwitterception;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javatwitter.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.search.common.concurrent.ScheduledEtwitterecutorServiceFactory;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.common.util.GCUtil;
import com.twitter.search.common.util.io.recordreader.RecordReader;
import com.twitter.search.common.util.zktrylock.ZooKeeperTryLockFactory;
import com.twitter.search.earlybird.EarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.EarlybirdStatus;
import com.twitter.search.earlybird.ServerSetMember;
import com.twitter.search.earlybird.archive.ArchiveTimeSlicer.ArchiveTimeSlice;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.util.ScrubGenUtil;
import com.twitter.search.earlybird.document.TweetDocument;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.partition.CompleteSegmentManager;
import com.twitter.search.earlybird.partition.DynamicPartitionConfig;
import com.twitter.search.earlybird.partition.MultiSegmentTermDictionaryManager;
import com.twitter.search.earlybird.partition.PartitionConfig;
import com.twitter.search.earlybird.partition.PartitionManager;
import com.twitter.search.earlybird.partition.SearchIndetwitteringMetricSet;
import com.twitter.search.earlybird.partition.SegmentHdfsFlusher;
import com.twitter.search.earlybird.partition.SegmentInfo;
import com.twitter.search.earlybird.partition.SegmentLoader;
import com.twitter.search.earlybird.partition.SegmentManager;
import com.twitter.search.earlybird.partition.SegmentManager.Filter;
import com.twitter.search.earlybird.partition.SegmentManager.Order;
import com.twitter.search.earlybird.partition.SegmentOptimizer;
import com.twitter.search.earlybird.partition.SegmentSyncConfig;
import com.twitter.search.earlybird.partition.SegmentWarmer;
import com.twitter.search.earlybird.partition.SimpleSegmentIndetwitterer;
import com.twitter.search.earlybird.partition.UserScrubGeoEventStreamIndetwitterer;
import com.twitter.search.earlybird.partition.UserUpdatesStreamIndetwitterer;
import com.twitter.search.earlybird.querycache.QueryCacheManager;
import com.twitter.search.earlybird.segment.SegmentDataProvider;
import com.twitter.search.earlybird.thrift.EarlybirdStatusCode;
import com.twitter.search.earlybird.util.CoordinatedEarlybirdAction;
import com.twitter.search.earlybird.util.CoordinatedEarlybirdActionInterface;
import com.twitter.search.earlybird.util.CoordinatedEarlybirdActionLockFailed;

public class ArchiveSearchPartitionManager etwittertends PartitionManager {
  private static final Logger LOG =
      LoggerFactory.getLogger(ArchiveSearchPartitionManager.class);

  public static final String CONFIG_NAME = "archive";

  private static final long ONE_DAY_MILLIS = TimeUnit.DAYS.toMillis(1);

  private final ArchiveTimeSlicer timeSlicer;
  private final ArchiveSegmentDataProvider segmentDataProvider;

  private final UserUpdatesStreamIndetwitterer userUpdatesStreamIndetwitterer;
  private final UserScrubGeoEventStreamIndetwitterer userScrubGeoEventStreamIndetwitterer;

  private final SegmentWarmer segmentWarmer;
  private final EarlybirdIndetwitterConfig earlybirdIndetwitterConfig;
  private final ZooKeeperTryLockFactory zkTryLockFactory;
  private final Clock clock;
  private final SegmentSyncConfig segmentSyncConfig;
  protected final SearchCounter gcAfterIndetwittering;

  // Used for coordinating daily updated across different replicas on the same hash partition,
  // to run them one at a time, and minimize the impact on query latencies.
  private final CoordinatedEarlybirdActionInterface coordinatedDailyUpdate;

  private final SearchIndetwitteringMetricSet indetwitteringMetricSet;

  // This is only used in tests where no coordination is needed.
  @VisibleForTesting
  public ArchiveSearchPartitionManager(
      ZooKeeperTryLockFactory zooKeeperTryLockFactory,
      QueryCacheManager queryCacheManager,
      SegmentManager segmentManager,
      DynamicPartitionConfig dynamicPartitionConfig,
      UserUpdatesStreamIndetwitterer userUpdatesStreamIndetwitterer,
      UserScrubGeoEventStreamIndetwitterer userScrubGeoEventStreamIndetwitterer,
      SearchStatsReceiver searchStatsReceiver,
      ArchiveEarlybirdIndetwitterConfig earlybirdIndetwitterConfig,
      ScheduledEtwitterecutorServiceFactory etwitterecutorServiceFactory,
      ScheduledEtwitterecutorServiceFactory userUpdateIndetwittererScheduledEtwitterecutorFactory,
      SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      SegmentSyncConfig syncConfig,
      Clock clock,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler)
      throws IOEtwitterception {
    this(
        zooKeeperTryLockFactory,
        queryCacheManager,
        segmentManager,
        dynamicPartitionConfig,
        userUpdatesStreamIndetwitterer,
        userScrubGeoEventStreamIndetwitterer,
        searchStatsReceiver,
        earlybirdIndetwitterConfig,
        null,
        etwitterecutorServiceFactory,
        userUpdateIndetwittererScheduledEtwitterecutorFactory,
        searchIndetwitteringMetricSet,
        syncConfig,
        clock,
        criticalEtwitterceptionHandler);
  }

  public ArchiveSearchPartitionManager(
      ZooKeeperTryLockFactory zooKeeperTryLockFactory,
      QueryCacheManager queryCacheManager,
      SegmentManager segmentManager,
      DynamicPartitionConfig dynamicPartitionConfig,
      UserUpdatesStreamIndetwitterer userUpdatesStreamIndetwitterer,
      UserScrubGeoEventStreamIndetwitterer userScrubGeoEventStreamIndetwitterer,
      SearchStatsReceiver searchStatsReceiver,
      ArchiveEarlybirdIndetwitterConfig earlybirdIndetwitterConfig,
      ServerSetMember serverSetMember,
      ScheduledEtwitterecutorServiceFactory etwitterecutorServiceFactory,
      ScheduledEtwitterecutorServiceFactory userUpdateIndetwittererEtwitterecutorFactory,
      SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      SegmentSyncConfig syncConfig,
      Clock clock,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) throws IOEtwitterception {
    super(queryCacheManager, segmentManager, dynamicPartitionConfig, etwitterecutorServiceFactory,
        searchIndetwitteringMetricSet, searchStatsReceiver, criticalEtwitterceptionHandler);

    Preconditions.checkState(syncConfig.getScrubGen().isPresent());
    Date scrubGen = ScrubGenUtil.parseScrubGenToDate(syncConfig.getScrubGen().get());

    this.zkTryLockFactory = zooKeeperTryLockFactory;
    final DailyStatusBatches dailyStatusBatches = new DailyStatusBatches(
        zkTryLockFactory,
        scrubGen);
    this.earlybirdIndetwitterConfig = earlybirdIndetwitterConfig;
    PartitionConfig curPartitionConfig = dynamicPartitionConfig.getCurrentPartitionConfig();

    this.indetwitteringMetricSet = searchIndetwitteringMetricSet;

    this.timeSlicer = new ArchiveTimeSlicer(
        EarlybirdConfig.getMatwitterSegmentSize(), dailyStatusBatches,
        curPartitionConfig.getTierStartDate(), curPartitionConfig.getTierEndDate(),
        earlybirdIndetwitterConfig);
    this.segmentDataProvider =
        new ArchiveSegmentDataProvider(
            dynamicPartitionConfig,
            timeSlicer,
            this.earlybirdIndetwitterConfig);

    this.userUpdatesStreamIndetwitterer = userUpdatesStreamIndetwitterer;
    this.userScrubGeoEventStreamIndetwitterer = userScrubGeoEventStreamIndetwitterer;

    this.coordinatedDailyUpdate = new CoordinatedEarlybirdAction(
        zkTryLockFactory,
        "archive_daily_update",
        dynamicPartitionConfig,
        serverSetMember,
        criticalEtwitterceptionHandler,
        syncConfig);

    this.segmentWarmer = new SegmentWarmer(criticalEtwitterceptionHandler);
    this.clock = clock;
    this.segmentSyncConfig = syncConfig;
    this.gcAfterIndetwittering = SearchCounter.etwitterport("gc_after_indetwittering");
  }

  @Override
  public SegmentDataProvider getSegmentDataProvider() {
    return segmentDataProvider;
  }

  @Override
  protected void startUp() throws Etwitterception {
    LOG.info("Using CompleteSegmentManager to indetwitter complete segments.");

    // deferring handling of multi-segment term dictionary for the archive.
    // SEARCH-11952
    CompleteSegmentManager completeSegmentManager = new CompleteSegmentManager(
        zkTryLockFactory,
        segmentDataProvider,
        userUpdatesStreamIndetwitterer,
        userScrubGeoEventStreamIndetwitterer,
        segmentManager,
        null,
        indetwitteringMetricSet,
        clock,
        MultiSegmentTermDictionaryManager.NOOP_INSTANCE,
        segmentSyncConfig,
        criticalEtwitterceptionHandler);

    completeSegmentManager.indetwitterUserEvents();
    completeSegmentManager.indetwitterCompleteSegments(
        () -> segmentManager.getSegmentInfos(Filter.NeedsIndetwittering, Order.OLD_TO_NEW));

    // In the archive cluster, the current segment needs to be loaded too.
    List<SegmentInfo> allSegments =
        Lists.newArrayList(segmentManager.getSegmentInfos(Filter.All, Order.OLD_TO_NEW));
    completeSegmentManager.loadCompleteSegments(allSegments);

    completeSegmentManager.buildMultiSegmentTermDictionary();

    completeSegmentManager.warmSegments(allSegments);

    LOG.info("Starting to run UserUpdatesKafkaConsumer");
    new Thread(userUpdatesStreamIndetwitterer::run, "userupdates-stream-indetwitterer").start();

    if (EarlybirdConfig.consumeUserScrubGeoEvents()) {
      LOG.info("Starting to run UserScrubGeoEventKafkaConsumer");
      new Thread(userScrubGeoEventStreamIndetwitterer::run,
          "userScrubGeoEvent-stream-indetwitterer").start();
    }
  }

  private static List<ArchiveTimeSlice> truncateSegmentList(List<ArchiveTimeSlice> segmentList,
                                                            int matwitterNumSegments) {
    // Maybe cut-off the beginning of the sorted list of IDs.
    if (matwitterNumSegments > 0 && matwitterNumSegments < segmentList.size()) {
      return segmentList.subList(segmentList.size() - matwitterNumSegments, segmentList.size());
    } else {
      return segmentList;
    }
  }


  @Override
  protected void indetwitteringLoop(boolean firstLoop) throws Etwitterception {
    if (firstLoop) {
      EarlybirdStatus.beginEvent(
          INDEtwitter_CURRENT_SEGMENT, getSearchIndetwitteringMetricSet().startupInCurrentSegment);
    }

    List<ArchiveTimeSlice> timeSlices = timeSlicer.getTimeSlicesInTierRange();
    PartitionConfig curPartitionConfig = dynamicPartitionConfig.getCurrentPartitionConfig();
    timeSlices = truncateSegmentList(timeSlices, curPartitionConfig.getMatwitterEnabledLocalSegments());

    for (final ArchiveTimeSlice timeSlice : timeSlices) {
      // If any timeslice build failed, do not try to build timeslice after that to prevent
      // possible holes between timeslices.
      try {
        if (!processArchiveTimeSlice(timeSlice)) {
          LOG.warn("Building timeslice {} has failed, stopping future builds.",
              timeSlice.getDescription());
          indetwitteringMetricSet.archiveTimeSliceBuildFailedCounter.increment();
          return;
        }
      } catch (CoordinatedEarlybirdActionLockFailed e) {
        // If the timeslice build failed because of lock coordination, we can wait for the netwittert
        // iteration to build again.
        return;
      }
    }

    if (firstLoop) {
      EarlybirdStatus.endEvent(
          INDEtwitter_CURRENT_SEGMENT, getSearchIndetwitteringMetricSet().startupInCurrentSegment);
      LOG.info("First indetwittering loop complete. Setting up query cache...");
      EarlybirdStatus.beginEvent(
          SETUP_QUERY_CACHE, getSearchIndetwitteringMetricSet().startupInQueryCacheUpdates);
    }
    setupQueryCacheIfNeeded();

    if (EarlybirdStatus.isStarting() && queryCacheManager.allTasksRan()) {
      LOG.info("Query cache setup complete. Becoming current now...");
      EarlybirdStatus.endEvent(
          SETUP_QUERY_CACHE, getSearchIndetwitteringMetricSet().startupInQueryCacheUpdates);

      becomeCurrent();
      EarlybirdStatus.recordEarlybirdEvent("Archive Earlybird is current");
    }

    updateIndetwitterFreshnessStats(timeSlices);
  }

  @VisibleForTesting
  protected boolean processArchiveTimeSlice(final ArchiveTimeSlice timeSlice)
      throws CoordinatedEarlybirdActionLockFailed, IOEtwitterception {
    PartitionConfig curPartitionConfig = dynamicPartitionConfig.getCurrentPartitionConfig();
    long minStatusID = timeSlice.getMinStatusID(curPartitionConfig.getIndetwitteringHashPartitionID());
    SegmentInfo segmentInfo = segmentManager.getSegmentInfo(minStatusID);
    if (segmentInfo == null) {
      return indetwitterSegmentFromScratch(timeSlice);
    } else if (etwitteristingSegmentNeedsUpdating(timeSlice, segmentInfo)) {
      return indetwitterNewDayAndAppendEtwitteristingSegment(timeSlice, segmentInfo);
    }
    return true;
  }


  @VisibleForTesting
  SegmentInfo newSegmentInfo(ArchiveTimeSlice timeSlice) throws IOEtwitterception {
    return new SegmentInfo(segmentDataProvider.newArchiveSegment(timeSlice),
        segmentManager.getEarlybirdSegmentFactory(), segmentSyncConfig);
  }

  private boolean indetwitterNewDayAndAppendEtwitteristingSegment(final ArchiveTimeSlice timeSlice,
                                                      SegmentInfo segmentInfo)
      throws CoordinatedEarlybirdActionLockFailed, IOEtwitterception {

    LOG.info("Updating segment: {}; new endDate will be {} segmentInfo: {}",
        segmentInfo.getSegment().getTimeSliceID(), timeSlice.getEndDate(), segmentInfo);

    // Create another new SegmentInfo for indetwittering
    final SegmentInfo newSegmentInfoForIndetwittering = newSegmentInfo(timeSlice);
    // make a final reference of the old segment info to be passed into closure.
    final SegmentInfo oldSegmentInfo = segmentInfo;

    // Sanity check: the old and new segment should not share the same lucene directory.
    Preconditions.checkState(
        !newSegmentInfoForIndetwittering.getSyncInfo().getLocalLuceneSyncDir().equals(
            oldSegmentInfo.getSyncInfo().getLocalLuceneSyncDir()));

    Preconditions.checkState(
        !newSegmentInfoForIndetwittering.getSyncInfo().getLocalSyncDir().equals(
            oldSegmentInfo.getSyncInfo().getLocalSyncDir()));

    final ArchiveSegment oldSegment = (ArchiveSegment) segmentInfo.getSegment();

    return indetwitterSegment(newSegmentInfoForIndetwittering, oldSegmentInfo, input -> {
      // we're updating the segment - only indetwitter days after the old end date, but only if
      // we're in the on-disk archive, and we're sure that the previous days have already
      // been indetwittered.
      return !earlybirdIndetwitterConfig.isIndetwitterStoredOnDisk()
          // First time around, and the segment has not been indetwittered and optimized yet,
          // we will want to add all the days
          || !oldSegmentInfo.isOptimized()
          || oldSegmentInfo.getIndetwitterSegment().getIndetwitterStats().getStatusCount() == 0
          || !oldSegment.getDataEndDate().before(timeSlice.getEndDate())
          // Indetwitter any new days
          || input.after(oldSegment.getDataEndDate());
    });
  }

  private boolean etwitteristingSegmentNeedsUpdating(ArchiveTimeSlice timeSlice,
                                               SegmentInfo segmentInfo) {
    return ((ArchiveSegment) segmentInfo.getSegment())
        .getDataEndDate().before(timeSlice.getEndDate())
        // First time around, the end date is the same as the timeSlice end date, but
        // the segment has not been indetwittered and optimized yet
        || (!segmentInfo.isOptimized() && !segmentInfo.wasIndetwittered())
        // If indetwittering failed, this indetwitter will not be marked as complete, and we will want
        // to reindetwitter
        || !segmentInfo.isComplete();
  }

  private boolean indetwitterSegmentFromScratch(ArchiveTimeSlice timeSlice) throws
      CoordinatedEarlybirdActionLockFailed, IOEtwitterception {

    SegmentInfo segmentInfo = newSegmentInfo(timeSlice);
    LOG.info("Creating segment: " + segmentInfo.getSegment().getTimeSliceID()
        + "; new endDate will be " + timeSlice.getEndDate() + " segmentInfo: " + segmentInfo);

    return indetwitterSegment(segmentInfo, null, ArchiveSegment.MATCH_ALL_DATE_PREDICATE);
  }

  private void updateIndetwitterFreshnessStats(List<ArchiveTimeSlice> timeSlices) {
    if (!timeSlices.isEmpty()) {
      ArchiveTimeSlice lastTimeslice = timeSlices.get(timeSlices.size() - 1);

      // Add ~24 hours to start of end date to estimate freshest tweet time.
      indetwitteringMetricSet.freshestTweetTimeMillis.set(
          lastTimeslice.getEndDate().getTime() + ONE_DAY_MILLIS);

      PartitionConfig curPartitionConfig = dynamicPartitionConfig.getCurrentPartitionConfig();
      long matwitterStatusId = lastTimeslice.getMatwitterStatusID(
          curPartitionConfig.getIndetwitteringHashPartitionID());
      if (matwitterStatusId > indetwitteringMetricSet.highestStatusId.get()) {
        indetwitteringMetricSet.highestStatusId.set(matwitterStatusId);
      }
    }
  }

  @Override
  public void shutDownIndetwittering() {
    LOG.info("Shutting down.");
    userUpdatesStreamIndetwitterer.close();
    userScrubGeoEventStreamIndetwitterer.close();
    LOG.info("Closed User Event Kafka Consumers. Now Shutting down reader set.");
    getSegmentDataProvider().getSegmentDataReaderSet().stopAll();
  }

  /**
   * Attempts to indetwitter new days of data into the provided segment, indetwittering only the days that
   * match the "dateFilter" predicate.
   * @return true iff indetwittering succeeded, false otherwise.
   */
  @VisibleForTesting
  protected boolean indetwitterSegment(final SegmentInfo segmentInfo,
                                 @Nullable final SegmentInfo segmentToAppend,
                                 final Predicate<Date> dateFilter)
      throws CoordinatedEarlybirdActionLockFailed, IOEtwitterception {
    // Don't coordinate while we're starting up
    if (!EarlybirdStatus.isStarting()) {
      return coordinatedDailyUpdate.etwitterecute(segmentInfo.getSegmentName(),
          isCoordinated -> innerIndetwitterSegment(segmentInfo, segmentToAppend, dateFilter));
    } else {
      return innerIndetwitterSegment(segmentInfo, segmentToAppend, dateFilter);
    }
  }

  private boolean innerIndetwitterSegment(SegmentInfo segmentInfo,
                                    @Nullable SegmentInfo segmentToAppend,
                                    Predicate<Date> dateFilter)
      throws IOEtwitterception {

    // First try to load the new day from HDFS / Local disk
    if (new SegmentLoader(segmentSyncConfig, criticalEtwitterceptionHandler).load(segmentInfo)) {
      LOG.info("Successful loaded segment for new day: " + segmentInfo);
      segmentManager.putSegmentInfo(segmentInfo);
      gcAfterIndetwittering.increment();
      GCUtil.runGC();
      return true;
    }

    LOG.info("Failed to load segment for new day. Will indetwitter segment: " + segmentInfo);
    RecordReader<TweetDocument> tweetReader = ((ArchiveSegment) segmentInfo.getSegment())
        .getStatusRecordReader(earlybirdIndetwitterConfig.createDocumentFactory(), dateFilter);
    try {
      // Read and indetwitter the statuses
      boolean success = newSimpleSegmentIndetwitterer(tweetReader, segmentToAppend)
          .indetwitterSegment(segmentInfo);
      if (!success) {
        return false;
      }
    } finally {
      tweetReader.stop();
    }

    if (!SegmentOptimizer.optimize(segmentInfo)) {
      // We consider the whole indetwittering event as failed if we fail to optimize.
      LOG.error("Failed to optimize segment: " + segmentInfo);
      segmentInfo.deleteLocalIndetwitteredSegmentDirectoryImmediately();
      return false;
    }

    if (!segmentWarmer.warmSegmentIfNecessary(segmentInfo)) {
      // We consider the whole indetwittering event as failed if we failed to warm (because we open
      // indetwitter readers in the warmer).
      LOG.error("Failed to warm segment: " + segmentInfo);
      segmentInfo.deleteLocalIndetwitteredSegmentDirectoryImmediately();
      return false;
    }

    // Flush and upload segment to HDFS. If this fails, we just log a warning and return true.
    boolean success = new SegmentHdfsFlusher(zkTryLockFactory, segmentSyncConfig)
        .flushSegmentToDiskAndHDFS(segmentInfo);
    if (!success) {
      LOG.warn("Failed to flush segment to HDFS: " + segmentInfo);
    }

    segmentManager.putSegmentInfo(segmentInfo);
    gcAfterIndetwittering.increment();
    GCUtil.runGC();
    return true;
  }

  @VisibleForTesting
  protected SimpleSegmentIndetwitterer newSimpleSegmentIndetwitterer(
      RecordReader<TweetDocument> tweetReader, SegmentInfo segmentToAppend) {
    return new SimpleSegmentIndetwitterer(tweetReader, indetwitteringMetricSet, segmentToAppend);
  }

  @Override
  public boolean isCaughtUpForTests() {
    return EarlybirdStatus.getStatusCode() == EarlybirdStatusCode.CURRENT;
  }

  public CoordinatedEarlybirdActionInterface getCoordinatedOptimizer() {
    return this.coordinatedDailyUpdate;
  }

  public ArchiveTimeSlicer getTimeSlicer() {
    return timeSlicer;
  }
}
