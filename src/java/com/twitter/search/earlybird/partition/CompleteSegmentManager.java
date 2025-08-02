package com.twitter.search.earlybird.partition;

import java.io.IOEtwitterception;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.common.util.io.recordreader.RecordReader;
import com.twitter.search.common.util.zktrylock.ZooKeeperTryLockFactory;
import com.twitter.search.earlybird.EarlybirdStatus;
import com.twitter.search.earlybird.common.config.EarlybirdProperty;
import com.twitter.search.earlybird.document.TweetDocument;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.segment.SegmentDataProvider;

/**
 * CompleteSegmentManager is used to parallelize indetwittering of complete (not partial) segments
 * on startup.  It also populates the fields used by the PartitionManager.
 */
public class CompleteSegmentManager {
  private static final Logger LOG = LoggerFactory.getLogger(CompleteSegmentManager.class);

  private static final String INDEtwitter_COMPLETED_SEGMENTS =
      "indetwittering, optimizing and flushing complete segments";
  private static final String LOAD_COMPLETED_SEGMENTS = "loading complete segments";
  private static final String INDEtwitter_UPDATES_FOR_COMPLETED_SEGMENTS =
      "indetwittering updates for complete segments";
  private static final String BUILD_MULTI_SEGMENT_TERM_DICT =
      "build multi segment term dictionaries";

  // Matwitter number of segments being loaded / indetwittered concurrently.
  private final int matwitterConcurrentSegmentIndetwitterers =
      EarlybirdProperty.MAtwitter_CONCURRENT_SEGMENT_INDEtwitterERS.get(3);

  // The state we are building.
  protected final SegmentDataProvider segmentDataProvider;
  private final InstrumentedQueue<ThriftVersionedEvents> retryQueue;

  private final UserUpdatesStreamIndetwitterer userUpdatesStreamIndetwitterer;
  private final UserScrubGeoEventStreamIndetwitterer userScrubGeoEventStreamIndetwitterer;

  private final SegmentManager segmentManager;
  private final ZooKeeperTryLockFactory zkTryLockFactory;
  private final SearchIndetwitteringMetricSet searchIndetwitteringMetricSet;
  private final Clock clock;
  private MultiSegmentTermDictionaryManager multiSegmentTermDictionaryManager;
  private final SegmentSyncConfig segmentSyncConfig;

  private final CriticalEtwitterceptionHandler criticalEtwitterceptionHandler;

  private boolean interrupted = false;

  public CompleteSegmentManager(
      ZooKeeperTryLockFactory zooKeeperTryLockFactory,
      SegmentDataProvider segmentDataProvider,
      UserUpdatesStreamIndetwitterer userUpdatesStreamIndetwitterer,
      UserScrubGeoEventStreamIndetwitterer userScrubGeoEventStreamIndetwitterer,
      SegmentManager segmentManager,
      InstrumentedQueue<ThriftVersionedEvents> retryQueue,
      SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      Clock clock,
      MultiSegmentTermDictionaryManager multiSegmentTermDictionaryManager,
      SegmentSyncConfig segmentSyncConfig,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    this.zkTryLockFactory = zooKeeperTryLockFactory;
    this.segmentDataProvider = segmentDataProvider;
    this.userUpdatesStreamIndetwitterer = userUpdatesStreamIndetwitterer;
    this.userScrubGeoEventStreamIndetwitterer = userScrubGeoEventStreamIndetwitterer;
    this.segmentManager = segmentManager;
    this.searchIndetwitteringMetricSet = searchIndetwitteringMetricSet;
    this.clock = clock;
    this.multiSegmentTermDictionaryManager = multiSegmentTermDictionaryManager;
    this.segmentSyncConfig = segmentSyncConfig;
    this.retryQueue = retryQueue;
    this.criticalEtwitterceptionHandler = criticalEtwitterceptionHandler;
  }

  /**
   * Indetwitteres all user events.
   */
  public void indetwitterUserEvents() {
    LOG.info("Loading/indetwittering user events.");
    StartupUserEventIndetwitterer startupUserEventIndetwitterer = new StartupUserEventIndetwitterer(
        searchIndetwitteringMetricSet,
        userUpdatesStreamIndetwitterer,
        userScrubGeoEventStreamIndetwitterer,
        segmentManager,
        clock
    );

    startupUserEventIndetwitterer.indetwitterAllEvents();
    LOG.info("Finished loading/indetwittering user events.");
  }

  /**
   * Loads or indetwitteres from scratch all complete segments.
   *
   * @param segmentsToIndetwitterProvider A supplier that provides the list of all complete segments.
   */
  public void indetwitterCompleteSegments(
      Supplier<Iterable<SegmentInfo>> segmentsToIndetwitterProvider) throws Etwitterception {
    List<Thread> segmentIndetwitterers = Lists.newArrayList();

    EarlybirdStatus.beginEvent(
        INDEtwitter_COMPLETED_SEGMENTS, searchIndetwitteringMetricSet.startupInIndetwitterCompletedSegments);
    while (!interrupted && !Thread.currentThread().isInterrupted()) {
      try {
        // Get the refreshed list of local segment databases.
        segmentManager.updateSegments(segmentDataProvider.newSegmentList());
        Iterator<SegmentInfo> segmentsToIndetwitter = segmentsToIndetwitterProvider.get().iterator();

        // Start up to matwitter concurrent segment indetwitterers.
        segmentIndetwitterers.clear();
        while (segmentsToIndetwitter.hasNetwittert() && segmentIndetwitterers.size() < matwitterConcurrentSegmentIndetwitterers) {
          SegmentInfo netwittertSegment = segmentsToIndetwitter.netwittert();
          if (!netwittertSegment.isComplete()) {
            Thread thread = new Thread(new SingleSegmentIndetwitterer(netwittertSegment),
                                       "startup-segment-indetwitterer-" + netwittertSegment.getSegmentName());
            thread.start();
            segmentIndetwitterers.add(thread);
          }
        }

        // No remaining indetwitterer threads, we're done.
        if (segmentIndetwitterers.size() == 0) {
          LOG.info("Finished indetwittering complete segments");
          EarlybirdStatus.endEvent(
              INDEtwitter_COMPLETED_SEGMENTS, searchIndetwitteringMetricSet.startupInIndetwitterCompletedSegments);
          break;
        }

        // Wait for threads to complete fully.
        LOG.info("Started {} indetwittering threads", segmentIndetwitterers.size());
        for (Thread thread : segmentIndetwitterers) {
          thread.join();
        }
        LOG.info("Joined all {} indetwittering threads", segmentIndetwitterers.size());
      } catch (IOEtwitterception e) {
        LOG.error("IOEtwitterception in SegmentStartupManager loop", e);
      } catch (InterruptedEtwitterception e) {
        interrupted = true;
        LOG.error("Interrupted joining segment indetwitterer thread", e);
      }
    }
  }

  /**
   * Loads all given complete segments.
   *
   * @param completeSegments The list of all complete segments to be loaded.
   */
  public void loadCompleteSegments(List<SegmentInfo> completeSegments) throws Etwitterception {
    if (!interrupted && !Thread.currentThread().isInterrupted()) {
      LOG.info("Starting to load {} complete segments.", completeSegments.size());
      EarlybirdStatus.beginEvent(
          LOAD_COMPLETED_SEGMENTS, searchIndetwitteringMetricSet.startupInLoadCompletedSegments);

      List<Thread> segmentThreads = Lists.newArrayList();
      List<SegmentInfo> segmentsToBeLoaded = Lists.newArrayList();
      for (SegmentInfo segmentInfo : completeSegments) {
        if (segmentInfo.isEnabled()) {
          segmentsToBeLoaded.add(segmentInfo);
          Thread segmentLoaderThread = new Thread(
              () -> new SegmentLoader(segmentSyncConfig, criticalEtwitterceptionHandler)
                  .load(segmentInfo),
              "startup-segment-loader-" + segmentInfo.getSegmentName());
          segmentThreads.add(segmentLoaderThread);
          segmentLoaderThread.start();
        } else {
          LOG.info("Will not load segment {} because it's disabled.", segmentInfo.getSegmentName());
        }
      }

      for (Thread segmentLoaderThread : segmentThreads) {
        segmentLoaderThread.join();
      }

      for (SegmentInfo segmentInfo : segmentsToBeLoaded) {
        if (!segmentInfo.getSyncInfo().isLoaded()) {
          // Throw an etwitterception if a segment could not be loaded: We do not want earlybirds to
          // startup with missing segments.
          throw new RuntimeEtwitterception("Could not load segment " + segmentInfo.getSegmentName());
        }
      }

      LOG.info("Loaded all complete segments, starting indetwittering all updates.");
      EarlybirdStatus.beginEvent(
          INDEtwitter_UPDATES_FOR_COMPLETED_SEGMENTS,
          searchIndetwitteringMetricSet.startupInIndetwitterUpdatesForCompletedSegments);

      // Indetwitter all updates for all complete segments until we're fully caught up.
      if (!EarlybirdCluster.isArchive(segmentManager.getEarlybirdIndetwitterConfig().getCluster())) {
        segmentThreads.clear();
        for (SegmentInfo segmentInfo : completeSegments) {
          if (segmentInfo.isEnabled()) {
            Thread segmentUpdatesThread = new Thread(
                () -> new SimpleUpdateIndetwitterer(
                    segmentDataProvider.getSegmentDataReaderSet(),
                    searchIndetwitteringMetricSet,
                    retryQueue,
                    criticalEtwitterceptionHandler).indetwitterAllUpdates(segmentInfo),
                "startup-complete-segment-update-indetwitterer-" + segmentInfo.getSegmentName());
            segmentThreads.add(segmentUpdatesThread);
            segmentUpdatesThread.start();
          } else {
            LOG.info("Will not indetwitter updates for segment {} because it's disabled.",
                     segmentInfo.getSegmentName());
          }
        }

        for (Thread segmentUpdatesThread : segmentThreads) {
          segmentUpdatesThread.join();
        }
      }
      LOG.info("Indetwittered updates for all complete segments.");
      EarlybirdStatus.endEvent(
          INDEtwitter_UPDATES_FOR_COMPLETED_SEGMENTS,
          searchIndetwitteringMetricSet.startupInIndetwitterUpdatesForCompletedSegments);

      EarlybirdStatus.endEvent(
          LOAD_COMPLETED_SEGMENTS, searchIndetwitteringMetricSet.startupInLoadCompletedSegments);
    }
  }

  /**
   * Builds the term dictionary that spans all earlybird segments. Some fields share the term
   * dictionary across segments as an optimization.
   */
  public void buildMultiSegmentTermDictionary() {
    EarlybirdStatus.beginEvent(
        BUILD_MULTI_SEGMENT_TERM_DICT,
        searchIndetwitteringMetricSet.startupInMultiSegmentTermDictionaryUpdates);
    if (!interrupted && !Thread.currentThread().isInterrupted()) {
      LOG.info("Building multi segment term dictionaries.");
      boolean built = multiSegmentTermDictionaryManager.buildDictionary();
      LOG.info("Done building multi segment term dictionaries, result: {}", built);
    }
    EarlybirdStatus.endEvent(
        BUILD_MULTI_SEGMENT_TERM_DICT,
        searchIndetwitteringMetricSet.startupInMultiSegmentTermDictionaryUpdates);
  }

  /**
   * Warms up the data in the given segments. The warm up will usually make sure that all necessary
   * is loaded in RAM and all relevant data structures are created before the segments starts
   * serving real requests.
   *
   * @param segments The list of segments to warm up.
   */
  public final void warmSegments(Iterable<SegmentInfo> segments) throws InterruptedEtwitterception {
    int threadId = 1;
    Iterator<SegmentInfo> it = segments.iterator();

    try {
      List<Thread> segmentWarmers = Lists.newLinkedList();
      while (it.hasNetwittert()) {

        segmentWarmers.clear();
        while (it.hasNetwittert() && segmentWarmers.size() < matwitterConcurrentSegmentIndetwitterers) {
          final SegmentInfo segment = it.netwittert();
          Thread t = new Thread(() ->
            new SegmentWarmer(criticalEtwitterceptionHandler).warmSegmentIfNecessary(segment),
              "startup-warmer-" + threadId++);

          t.start();
          segmentWarmers.add(t);
        }

        for (Thread t : segmentWarmers) {
          t.join();
        }
      }
    } catch (InterruptedEtwitterception e) {
      LOG.error("Interrupted segment warmer thread", e);
      Thread.currentThread().interrupt();
      throw e;
    }
  }

  /**
   * Indetwitteres a complete segment.
   */
  private class SingleSegmentIndetwitterer implements Runnable {
    private final SegmentInfo segmentInfo;

    public SingleSegmentIndetwitterer(SegmentInfo segmentInfo) {
      this.segmentInfo = segmentInfo;
    }

    @Override
    public void run() {
      // 0) Check if the segment can be loaded. This might copy the segment from HDFS.
      if (new SegmentLoader(segmentSyncConfig, criticalEtwitterceptionHandler)
          .downloadSegment(segmentInfo)) {
        LOG.info("Will not indetwitter segment {} because it was downloaded from HDFS.",
                 segmentInfo.getSegmentName());
        segmentInfo.setComplete(true);
        return;
      }

      LOG.info("SingleSegmentIndetwitterer starting for segment: " + segmentInfo);

      // 1) Indetwitter all tweets in this segment.
      RecordReader<TweetDocument> tweetReader;
      try {
        tweetReader = segmentDataProvider.getSegmentDataReaderSet().newDocumentReader(segmentInfo);
        if (tweetReader != null) {
          tweetReader.setEtwitterhaustStream(true);
        }
      } catch (Etwitterception e) {
        throw new RuntimeEtwitterception("Could not create tweet reader for segment: " + segmentInfo, e);
      }

      new SimpleSegmentIndetwitterer(tweetReader, searchIndetwitteringMetricSet).indetwitterSegment(segmentInfo);

      if (!segmentInfo.isComplete() || segmentInfo.isIndetwittering()) {
        throw new RuntimeEtwitterception("Segment does not appear to be complete: " + segmentInfo);
      }

      // 2) Indetwitter all updates in this segment (archive earlybirds don't have updates).
      if (!EarlybirdCluster.isArchive(segmentManager.getEarlybirdIndetwitterConfig().getCluster())) {
        new SimpleUpdateIndetwitterer(
            segmentDataProvider.getSegmentDataReaderSet(),
            searchIndetwitteringMetricSet,
            retryQueue,
            criticalEtwitterceptionHandler).indetwitterAllUpdates(segmentInfo);
      }

      // 3) Optimize the segment.
      SegmentOptimizer.optimize(segmentInfo);

      // 4) Flush to HDFS if necessary.
      new SegmentHdfsFlusher(zkTryLockFactory, segmentSyncConfig)
          .flushSegmentToDiskAndHDFS(segmentInfo);

      // 5) Unload the segment from memory.
      segmentInfo.getIndetwitterSegment().close();
    }
  }

}
