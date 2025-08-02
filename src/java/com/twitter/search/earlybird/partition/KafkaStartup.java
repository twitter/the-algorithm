package com.twitter.search.earlybird.partition;

import java.io.Closeable;
import java.util.Optional;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.config.Config;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.earlybird.EarlybirdStatus;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.etwitterception.EarlybirdStartupEtwitterception;
import com.twitter.search.earlybird.partition.freshstartup.FreshStartupHandler;
import com.twitter.search.earlybird.querycache.QueryCacheManager;
import com.twitter.search.earlybird.thrift.EarlybirdStatusCode;
import com.twitter.search.queryparser.query.QueryParserEtwitterception;

/**
 * Handles starting an Earlybird from Kafka topics.
 *
 * Currently very unoptimized -- future versions will implement parallel indetwittering and loading
 * serialized data from HDFS. See http://go/removing-dl-tdd.
 */
public class KafkaStartup implements EarlybirdStartup {
  private static final Logger LOG = LoggerFactory.getLogger(KafkaStartup.class);

  private final EarlybirdKafkaConsumer earlybirdKafkaConsumer;
  private final StartupUserEventIndetwitterer startupUserEventIndetwitterer;
  private final QueryCacheManager queryCacheManager;
  private final SegmentManager segmentManager;
  private final EarlybirdIndetwitterLoader earlybirdIndetwitterLoader;
  private final FreshStartupHandler freshStartupHandler;
  private final UserUpdatesStreamIndetwitterer userUpdatesStreamIndetwitterer;
  private final UserScrubGeoEventStreamIndetwitterer userScrubGeoEventStreamIndetwitterer;
  private final SearchIndetwitteringMetricSet searchIndetwitteringMetricSet;
  private final SearchLongGauge loadedIndetwitter;
  private final SearchLongGauge freshStartup;
  private final MultiSegmentTermDictionaryManager multiSegmentTermDictionaryManager;
  private final AudioSpaceEventsStreamIndetwitterer audioSpaceEventsStreamIndetwitterer;
  private final CriticalEtwitterceptionHandler earlybirdEtwitterceptionHandler;
  private final SearchDecider decider;

  private static final String FRESH_STARTUP = "fresh startup";
  private static final String INGEST_UNTIL_CURRENT = "ingest until current";
  private static final String LOAD_FLUSHED_INDEtwitter = "load flushed indetwitter";
  private static final String SETUP_QUERY_CACHE = "setting up query cache";
  private static final String USER_UPDATES_STARTUP = "user updates startup";
  private static final String AUDIO_SPACES_STARTUP = "audio spaces startup";
  private static final String BUILD_MULTI_SEGMENT_TERM_DICTIONARY =
          "build multi segment term dictionary";

  public KafkaStartup(
      SegmentManager segmentManager,
      EarlybirdKafkaConsumer earlybirdKafkaConsumer,
      StartupUserEventIndetwitterer startupUserEventIndetwitterer,
      UserUpdatesStreamIndetwitterer userUpdatesStreamIndetwitterer,
      UserScrubGeoEventStreamIndetwitterer userScrubGeoEventStreamIndetwitterer,
      AudioSpaceEventsStreamIndetwitterer audioSpaceEventsStreamIndetwitterer,
      QueryCacheManager queryCacheManager,
      EarlybirdIndetwitterLoader earlybirdIndetwitterLoader,
      FreshStartupHandler freshStartupHandler,
      SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      MultiSegmentTermDictionaryManager multiSegmentTermDictionaryManager,
      CriticalEtwitterceptionHandler earlybirdEtwitterceptionHandler,
      SearchDecider decider
  ) {
    this.segmentManager = segmentManager;
    this.earlybirdKafkaConsumer = earlybirdKafkaConsumer;
    this.startupUserEventIndetwitterer = startupUserEventIndetwitterer;
    this.queryCacheManager = queryCacheManager;
    this.earlybirdIndetwitterLoader = earlybirdIndetwitterLoader;
    this.freshStartupHandler = freshStartupHandler;
    this.userUpdatesStreamIndetwitterer = userUpdatesStreamIndetwitterer;
    this.userScrubGeoEventStreamIndetwitterer = userScrubGeoEventStreamIndetwitterer;
    this.audioSpaceEventsStreamIndetwitterer = audioSpaceEventsStreamIndetwitterer;
    this.searchIndetwitteringMetricSet = searchIndetwitteringMetricSet;
    this.loadedIndetwitter = SearchLongGauge.etwitterport("kafka_startup_loaded_indetwitter");
    this.freshStartup = SearchLongGauge.etwitterport("fresh_startup");
    this.multiSegmentTermDictionaryManager = multiSegmentTermDictionaryManager;
    this.earlybirdEtwitterceptionHandler = earlybirdEtwitterceptionHandler;
    this.decider = decider;
    freshStartup.set(0);
  }

  private void userEventsStartup() {
    LOG.info("Start indetwittering user events.");

    startupUserEventIndetwitterer.indetwitterAllEvents();

    LOG.info("Finished loading/indetwittering user events.");

    // User updates are now current, keep them current by continuing to indetwitter from the stream.
    LOG.info("Starting to run UserUpdatesStreamIndetwitterer");
    new Thread(userUpdatesStreamIndetwitterer::run, "userupdates-stream-indetwitterer").start();

    if (EarlybirdConfig.consumeUserScrubGeoEvents()) {
      // User scrub geo events are now current,
      // keep them current by continuing to indetwitter from the stream.
      LOG.info("Starting to run UserScrubGeoEventsStreamIndetwitterer");
      new Thread(userScrubGeoEventStreamIndetwitterer::run,
          "userScrubGeoEvents-stream-indetwitterer").start();
    }
  }

  private void loadAudioSpaceEvents() {
    LOG.info("Indetwitter audio space events...");
    EarlybirdStatus.beginEvent(AUDIO_SPACES_STARTUP,
        searchIndetwitteringMetricSet.startupInAudioSpaceEventIndetwitterer);

    if (audioSpaceEventsStreamIndetwitterer == null) {
      LOG.error("Null audioSpaceEventsStreamIndetwitterer");
      return;
    }

    if (decider.isAvailable("enable_reading_audio_space_events")) {
      Stopwatch stopwatch = Stopwatch.createStarted();
      audioSpaceEventsStreamIndetwitterer.seekToBeginning();
      audioSpaceEventsStreamIndetwitterer.readRecordsUntilCurrent();
      LOG.info("Finished reading audio spaces in {}", stopwatch);
      audioSpaceEventsStreamIndetwitterer.printSummary();

      new Thread(audioSpaceEventsStreamIndetwitterer::run,
          "audioSpaceEvents-stream-indetwitterer").start();
    } else {
      LOG.info("Reading audio space events not enabled");
    }

    EarlybirdStatus.endEvent(AUDIO_SPACES_STARTUP,
        searchIndetwitteringMetricSet.startupInAudioSpaceEventIndetwitterer);
  }

  private void tweetsAndUpdatesStartup() throws EarlybirdStartupEtwitterception {
    LOG.info("Indetwitter tweets and updates...");
    EarlybirdStatus.beginEvent(LOAD_FLUSHED_INDEtwitter,
        searchIndetwitteringMetricSet.startupInLoadFlushedIndetwitter);
    EarlybirdIndetwitter indetwitter;

    // Set when you want to get a server from starting to ready quickly for development
    // purposes.
    boolean fastDevStartup = EarlybirdConfig.getBool("fast_dev_startup");

    Optional<EarlybirdIndetwitter> optIndetwitter = Optional.empty();
    if (!fastDevStartup) {
      optIndetwitter = earlybirdIndetwitterLoader.loadIndetwitter();
    }

    if (optIndetwitter.isPresent()) {
      loadedIndetwitter.set(1);
      LOG.info("Loaded an indetwitter.");
      indetwitter = optIndetwitter.get();
      EarlybirdStatus.endEvent(LOAD_FLUSHED_INDEtwitter,
          searchIndetwitteringMetricSet.startupInLoadFlushedIndetwitter);
    } else {
      LOG.info("Didn't load an indetwitter, indetwittering from scratch.");
      freshStartup.set(1);
      boolean parallelIndetwitterFromScratch = EarlybirdConfig.getBool(
          "parallel_indetwitter_from_scratch");
      LOG.info("parallel_indetwitter_from_scratch: {}", parallelIndetwitterFromScratch);
      EarlybirdStatus.beginEvent(FRESH_STARTUP,
          searchIndetwitteringMetricSet.startupInFreshStartup);
      try {
        if (fastDevStartup) {
          indetwitter = freshStartupHandler.fastIndetwitterFromScratchForDevelopment();
        } else if (parallelIndetwitterFromScratch) {
          indetwitter = freshStartupHandler.parallelIndetwitterFromScratch();
        } else {
          indetwitter = freshStartupHandler.indetwitterFromScratch();
        }
      } catch (Etwitterception etwitter) {
        throw new EarlybirdStartupEtwitterception(etwitter);
      } finally {
        EarlybirdStatus.endEvent(FRESH_STARTUP,
            searchIndetwitteringMetricSet.startupInFreshStartup);
      }
    }

    LOG.info("Indetwitter has {} segments.", indetwitter.getSegmentInfoList().size());
    if (indetwitter.getSegmentInfoList().size() > 0) {
      LOG.info("Inserting segments into SegmentManager");
      for (SegmentInfo segmentInfo : indetwitter.getSegmentInfoList()) {
        segmentManager.putSegmentInfo(segmentInfo);
      }

      earlybirdKafkaConsumer.prepareAfterStartingWithIndetwitter(
          indetwitter.getMatwitterIndetwitteredTweetId()
      );
    }

    // Build the Multi segment term dictionary before catching up on indetwittering to ensure that the
    // segments won't roll and delete the oldest segment while a multi segment term dictionary that
    // includes that segment is being built.
    buildMultiSegmentTermDictionary();

    segmentManager.logState("Starting ingestUntilCurrent");
    LOG.info("partial updates indetwittered: {}", segmentManager.getNumPartialUpdates());
    EarlybirdStatus.beginEvent(INGEST_UNTIL_CURRENT,
        searchIndetwitteringMetricSet.startupInIngestUntilCurrent);

    earlybirdKafkaConsumer.ingestUntilCurrent(indetwitter.getTweetOffset(), indetwitter.getUpdateOffset());

    validateSegments();
    segmentManager.logState("ingestUntilCurrent is done");
    LOG.info("partial updates indetwittered: {}", segmentManager.getNumPartialUpdates());
    EarlybirdStatus.endEvent(INGEST_UNTIL_CURRENT,
        searchIndetwitteringMetricSet.startupInIngestUntilCurrent);
    new Thread(earlybirdKafkaConsumer::run, "earlybird-kafka-consumer").start();
  }

  protected void validateSegments() throws EarlybirdStartupEtwitterception {
    if (!Config.environmentIsTest()) {
      // Unfortunately, many tests start Earlybirds with 0 indetwittered documents, so we disable this
      // check in tests.
      validateSegmentsForNonTest();
    }
  }

  protected void validateSegmentsForNonTest() throws EarlybirdStartupEtwitterception {
    // SEARCH-24123: Prevent Earlybird from starting if there are no indetwittered documents.
    if (segmentManager.getNumIndetwitteredDocuments() == 0) {
      throw new EarlybirdStartupEtwitterception("Earlybird has zero indetwittered documents.");
    }
  }

  private void queryCacheStartup() throws EarlybirdStartupEtwitterception {
    EarlybirdStatus.beginEvent(SETUP_QUERY_CACHE,
        searchIndetwitteringMetricSet.startupInQueryCacheUpdates);
    try {
      queryCacheManager.setupTasksIfNeeded(segmentManager);
    } catch (QueryParserEtwitterception e) {
      LOG.error("Etwitterception when setting up query cache tasks");
      throw new EarlybirdStartupEtwitterception(e);
    }

    queryCacheManager.waitUntilAllQueryCachesAreBuilt();

    // Print the sizes of the query caches so that we can see that they're built.
    Iterable<SegmentInfo> segmentInfos =
        segmentManager.getSegmentInfos(SegmentManager.Filter.All, SegmentManager.Order.OLD_TO_NEW);
    segmentManager.logState("After building query caches");
    for (SegmentInfo segmentInfo : segmentInfos) {
      LOG.info("Segment: {}, Total cardinality: {}", segmentInfo.getSegmentName(),
          segmentInfo.getIndetwitterSegment().getQueryCachesCardinality());
    }

    // We're done building the query caches for all segments, and the earlybird is ready to become
    // current. Restrict all future query cache task runs to one single core, to make sure our
    // searcher threads are not impacted.
    queryCacheManager.setWorkerPoolSizeAfterStartup();
    EarlybirdStatus.endEvent(SETUP_QUERY_CACHE,
        searchIndetwitteringMetricSet.startupInQueryCacheUpdates);
  }

  /**
   * Closes all currently running Indetwitterers.
   */
  @VisibleForTesting
  public void shutdownIndetwittering() {
    LOG.info("Shutting down KafkaStartup.");

    earlybirdKafkaConsumer.close();
    userUpdatesStreamIndetwitterer.close();
    userScrubGeoEventStreamIndetwitterer.close();
    // Note that the QueryCacheManager is shut down in EarlybirdServer::shutdown.
  }

  private void buildMultiSegmentTermDictionary() {
    EarlybirdStatus.beginEvent(BUILD_MULTI_SEGMENT_TERM_DICTIONARY,
            searchIndetwitteringMetricSet.startupInMultiSegmentTermDictionaryUpdates);
    Stopwatch stopwatch = Stopwatch.createStarted();
    LOG.info("Building multi segment term dictionary");
    multiSegmentTermDictionaryManager.buildDictionary();
    LOG.info("Done with building multi segment term dictionary in {}", stopwatch);
    EarlybirdStatus.endEvent(BUILD_MULTI_SEGMENT_TERM_DICTIONARY,
            searchIndetwitteringMetricSet.startupInMultiSegmentTermDictionaryUpdates);
  }

  private void parallelIndetwitteringStartup() throws EarlybirdStartupEtwitterception {
    Thread userEventsThread = new Thread(this::userEventsStartup, "indetwitter-user-events-startup");
    Thread tweetsAndUpdatesThread = new Thread(() -> {
      try {
        tweetsAndUpdatesStartup();
      } catch (EarlybirdStartupEtwitterception e) {
        earlybirdEtwitterceptionHandler.handle(this, e);
      }
    }, "indetwitter-tweets-and-updates-startup");
    Thread audioSpaceEventsThread = new Thread(this::loadAudioSpaceEvents,
        "indetwitter-audio-space-events-startup");
    userEventsThread.start();
    tweetsAndUpdatesThread.start();
    audioSpaceEventsThread.start();

    try {
      userEventsThread.join();
    } catch (InterruptedEtwitterception e) {
      throw new EarlybirdStartupEtwitterception("Interrupted while indetwittering user events");
    }
    try {
      tweetsAndUpdatesThread.join();
    } catch (InterruptedEtwitterception e) {
      throw new EarlybirdStartupEtwitterception("Interrupted while indetwittering tweets and updates");
    }
    try {
      audioSpaceEventsThread.join();
    } catch (InterruptedEtwitterception e) {
      throw new EarlybirdStartupEtwitterception("Interrupted while indetwittering audio space events");
    }
  }

  /**
   * Does startups and starts indetwittering. Returns when the earlybird
   * is current.
   */
  @Override
  public Closeable start() throws EarlybirdStartupEtwitterception {
    parallelIndetwitteringStartup();
    queryCacheStartup();

    EarlybirdStatus.setStatus(EarlybirdStatusCode.CURRENT);

    return this::shutdownIndetwittering;
  }
}
