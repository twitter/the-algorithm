package com.twitter.search.earlybird.factory;

import java.io.IOEtwitterception;

import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.decider.Decider;
import com.twitter.search.common.aurora.AuroraInstanceKey;
import com.twitter.search.common.aurora.AuroraSchedulerClient;
import com.twitter.search.common.concurrent.ScheduledEtwitterecutorServiceFactory;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.common.util.ml.tensorflow_engine.TensorflowModelsManager;
import com.twitter.search.common.util.zktrylock.ZooKeeperTryLockFactory;
import com.twitter.search.earlybird.EarlybirdDarkProtwittery;
import com.twitter.search.earlybird.EarlybirdFinagleServerManager;
import com.twitter.search.earlybird.EarlybirdFuturePoolManager;
import com.twitter.search.earlybird.EarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.EarlybirdServer;
import com.twitter.search.earlybird.EarlybirdServerSetManager;
import com.twitter.search.earlybird.EarlybirdWarmUpManager;
import com.twitter.search.earlybird.QualityFactor;
import com.twitter.search.earlybird.UpdateableEarlybirdStateManager;
import com.twitter.search.earlybird.archive.ArchiveEarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.common.userupdates.UserScrubGeoMap;
import com.twitter.search.earlybird.common.userupdates.UserUpdatesChecker;
import com.twitter.search.earlybird.common.userupdates.UserTable;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.indetwitter.EarlybirdSegmentFactory;
import com.twitter.search.earlybird.ml.ScoringModelsManager;
import com.twitter.search.earlybird.partition.AudioSpaceEventsStreamIndetwitterer;
import com.twitter.search.earlybird.partition.AudioSpaceTable;
import com.twitter.search.earlybird.partition.DynamicPartitionConfig;
import com.twitter.search.earlybird.partition.EarlybirdStartup;
import com.twitter.search.earlybird.partition.MultiSegmentTermDictionaryManager;
import com.twitter.search.earlybird.partition.PartitionConfig;
import com.twitter.search.earlybird.partition.PartitionManager;
import com.twitter.search.earlybird.partition.SegmentManager;
import com.twitter.search.earlybird.partition.SegmentSyncConfig;
import com.twitter.search.earlybird.partition.UserScrubGeoEventStreamIndetwitterer;
import com.twitter.search.earlybird.partition.UserUpdatesStreamIndetwitterer;
import com.twitter.search.earlybird.querycache.QueryCacheConfig;
import com.twitter.search.earlybird.querycache.QueryCacheManager;
import com.twitter.search.earlybird.stats.EarlybirdSearcherStats;
import com.twitter.search.earlybird.util.TermCountMonitor;
import com.twitter.search.earlybird.util.TweetCountMonitor;

/**
 * This is the wiring file that builds EarlybirdServers.
 * Production and test code share this same wiring file.
 * <p/>
 * To supply mocks for testing, one can do so by supplying a different
 * EarlybirdWiringModule to this wiring file.
 */
public final class EarlybirdServerFactory {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdServerFactory.class);

  /**
   * Creates the EarlybirdServer based on the bindings in the given wire module.
   *
   * @param earlybirdWireModule The wire module that specifies all required bindings.
   */
  public EarlybirdServer makeEarlybirdServer(EarlybirdWireModule earlybirdWireModule)
      throws IOEtwitterception {
    LOG.info("Started making an Earlybird server");
    CriticalEtwitterceptionHandler criticalEtwitterceptionHandler = new CriticalEtwitterceptionHandler();
    Decider decider = earlybirdWireModule.provideDecider();
    SearchDecider searchDecider = new SearchDecider(decider);

    EarlybirdWireModule.ZooKeeperClients zkClients = earlybirdWireModule.provideZooKeeperClients();
    ZooKeeperTryLockFactory zkTryLockFactory =
        zkClients.stateClient.createZooKeeperTryLockFactory();

    EarlybirdIndetwitterConfig earlybirdIndetwitterConfig =
        earlybirdWireModule.provideEarlybirdIndetwitterConfig(
            decider, earlybirdWireModule.provideSearchIndetwitteringMetricSet(),
            criticalEtwitterceptionHandler);

    SearchStatsReceiver earlybirdServerStats =
        earlybirdWireModule.provideEarlybirdServerStatsReceiver();

    EarlybirdSearcherStats tweetsSearcherStats =
        earlybirdWireModule.provideTweetsSearcherStats();

    DynamicPartitionConfig dynamicPartitionConfig =
        earlybirdWireModule.provideDynamicPartitionConfig();

    PartitionConfig partitionConfig = dynamicPartitionConfig.getCurrentPartitionConfig();
    LOG.info("Partition config info [Cluster: {}, Tier: {}, Partition: {}, Replica: {}]",
            partitionConfig.getClusterName(),
            partitionConfig.getTierName(),
            partitionConfig.getIndetwitteringHashPartitionID(),
            partitionConfig.getHostPositionWithinHashPartition());

    Clock clock = earlybirdWireModule.provideClock();
    UserUpdatesChecker userUpdatesChecker =
        new UserUpdatesChecker(clock, decider, earlybirdIndetwitterConfig.getCluster());

    UserTable userTable = UserTable.newTableWithDefaultCapacityAndPredicate(
        earlybirdIndetwitterConfig.getUserTableFilter(partitionConfig)::apply);

    UserScrubGeoMap userScrubGeoMap = new UserScrubGeoMap();

    AudioSpaceTable audioSpaceTable = new AudioSpaceTable(clock);

    SegmentSyncConfig segmentSyncConfig =
        earlybirdWireModule.provideSegmentSyncConfig(earlybirdIndetwitterConfig.getCluster());

    SegmentManager segmentManager = earlybirdWireModule.provideSegmentManager(
        dynamicPartitionConfig,
        earlybirdIndetwitterConfig,
        earlybirdWireModule.provideSearchIndetwitteringMetricSet(),
        tweetsSearcherStats,
        earlybirdServerStats,
        userUpdatesChecker,
        segmentSyncConfig,
        userTable,
        userScrubGeoMap,
        clock,
        criticalEtwitterceptionHandler);

    QueryCacheConfig config = earlybirdWireModule.provideQueryCacheConfig(earlybirdServerStats);

    QueryCacheManager queryCacheManager = earlybirdWireModule.provideQueryCacheManager(
        config,
        earlybirdIndetwitterConfig,
        partitionConfig.getMatwitterEnabledLocalSegments(),
        userTable,
        userScrubGeoMap,
        earlybirdWireModule.provideQueryCacheUpdateTaskScheduledEtwitterecutorFactory(),
        earlybirdServerStats,
        tweetsSearcherStats,
        decider,
        criticalEtwitterceptionHandler,
        clock);

    EarlybirdServerSetManager serverSetManager = earlybirdWireModule.provideServerSetManager(
        zkClients.discoveryClient,
        dynamicPartitionConfig,
        earlybirdServerStats,
        EarlybirdConfig.getThriftPort(),
        "");

    EarlybirdWarmUpManager warmUpManager =
        earlybirdWireModule.provideWarmUpManager(zkClients.discoveryClient,
                                                 dynamicPartitionConfig,
                                                 earlybirdServerStats,
                                                 decider,
                                                 clock,
                                                 EarlybirdConfig.getWarmUpThriftPort(),
                                                 "warmup_");

    EarlybirdDarkProtwittery earlybirdDarkProtwittery = earlybirdWireModule.provideEarlybirdDarkProtwittery(
        new SearchDecider(decider),
        earlybirdWireModule.provideFinagleStatsReceiver(),
        serverSetManager,
        warmUpManager,
        partitionConfig.getClusterName());

    UserUpdatesStreamIndetwitterer userUpdatesStreamIndetwitterer =
        earlybirdWireModule.provideUserUpdatesKafkaConsumer(segmentManager);

    UserScrubGeoEventStreamIndetwitterer userScrubGeoEventStreamIndetwitterer =
        earlybirdWireModule.provideUserScrubGeoEventKafkaConsumer(segmentManager);

    AudioSpaceEventsStreamIndetwitterer audioSpaceEventsStreamIndetwitterer =
        earlybirdWireModule.provideAudioSpaceEventsStreamIndetwitterer(audioSpaceTable, clock);

    MultiSegmentTermDictionaryManager.Config termDictionaryConfig =
        earlybirdWireModule.provideMultiSegmentTermDictionaryManagerConfig();
    MultiSegmentTermDictionaryManager multiSegmentTermDictionaryManager =
        earlybirdWireModule.provideMultiSegmentTermDictionaryManager(
            termDictionaryConfig,
            segmentManager,
            earlybirdServerStats,
            decider,
            earlybirdIndetwitterConfig.getCluster());

    TermCountMonitor termCountMonitor =
        earlybirdWireModule.provideTermCountMonitor(
            segmentManager, earlybirdWireModule.provideTermCountMonitorScheduledEtwitterecutorFactory(),
            earlybirdServerStats,
            criticalEtwitterceptionHandler);
    TweetCountMonitor tweetCountMonitor =
        earlybirdWireModule.provideTweetCountMonitor(
            segmentManager, earlybirdWireModule.provideTweetCountMonitorScheduledEtwitterecutorFactory(),
            earlybirdServerStats,
            criticalEtwitterceptionHandler);

    ScoringModelsManager scoringModelsManager = earlybirdWireModule.provideScoringModelsManager(
        earlybirdServerStats,
        earlybirdIndetwitterConfig
    );

    TensorflowModelsManager tensorflowModelsManager =
        earlybirdWireModule.provideTensorflowModelsManager(
            earlybirdServerStats,
            "tf_loader",
            decider,
            earlybirdIndetwitterConfig
        );

    AuroraSchedulerClient schedulerClient = null;
    AuroraInstanceKey auroraInstanceKey = EarlybirdConfig.getAuroraInstanceKey();
    if (auroraInstanceKey != null) {
      schedulerClient = new AuroraSchedulerClient(auroraInstanceKey.getCluster());
    }

    UpdateableEarlybirdStateManager earlybirdStateManager =
        earlybirdWireModule.provideUpdateableEarlybirdStateManager(
            earlybirdIndetwitterConfig,
            dynamicPartitionConfig,
            zkClients.stateClient,
            schedulerClient,
            earlybirdWireModule.provideStateUpdateManagerEtwitterecutorFactory(),
            scoringModelsManager,
            tensorflowModelsManager,
            earlybirdServerStats,
            new SearchDecider(decider),
            criticalEtwitterceptionHandler);

    EarlybirdFuturePoolManager futurePoolManager = earlybirdWireModule.provideFuturePoolManager();
    EarlybirdFinagleServerManager finagleServerManager =
        earlybirdWireModule.provideFinagleServerManager(criticalEtwitterceptionHandler);

    PartitionManager partitionManager = null;
    if (EarlybirdIndetwitterConfigUtil.isArchiveSearch()) {
      partitionManager = buildArchivePartitionManager(
          earlybirdWireModule,
          userUpdatesStreamIndetwitterer,
          userScrubGeoEventStreamIndetwitterer,
          zkTryLockFactory,
          earlybirdIndetwitterConfig,
          dynamicPartitionConfig,
          segmentManager,
          queryCacheManager,
          earlybirdServerStats,
          serverSetManager,
          earlybirdWireModule.providePartitionManagerEtwitterecutorFactory(),
          earlybirdWireModule.provideSimpleUserUpdateIndetwittererScheduledEtwitterecutorFactory(),
          clock,
          segmentSyncConfig,
          criticalEtwitterceptionHandler);
    } else {
      LOG.info("Not creating PartitionManager");
    }

    EarlybirdSegmentFactory earlybirdSegmentFactory = new EarlybirdSegmentFactory(
        earlybirdIndetwitterConfig,
        earlybirdWireModule.provideSearchIndetwitteringMetricSet(),
        tweetsSearcherStats,
        clock);

    EarlybirdStartup earlybirdStartup = earlybirdWireModule.provideEarlybirdStartup(
        partitionManager,
        userUpdatesStreamIndetwitterer,
        userScrubGeoEventStreamIndetwitterer,
        audioSpaceEventsStreamIndetwitterer,
        dynamicPartitionConfig,
        criticalEtwitterceptionHandler,
        segmentManager,
        multiSegmentTermDictionaryManager,
        queryCacheManager,
        zkTryLockFactory,
        serverSetManager,
        clock,
        segmentSyncConfig,
        earlybirdSegmentFactory,
        earlybirdIndetwitterConfig.getCluster(),
        searchDecider);

    QualityFactor qualityFactor = earlybirdWireModule.provideQualityFactor(
        decider,
        earlybirdServerStats);

    EarlybirdServer earlybirdServer = new EarlybirdServer(
        queryCacheManager,
        zkClients.stateClient,
        decider,
        earlybirdIndetwitterConfig,
        dynamicPartitionConfig,
        partitionManager,
        segmentManager,
        audioSpaceTable,
        termCountMonitor,
        tweetCountMonitor,
        earlybirdStateManager,
        futurePoolManager,
        finagleServerManager,
        serverSetManager,
        warmUpManager,
        earlybirdServerStats,
        tweetsSearcherStats,
        scoringModelsManager,
        tensorflowModelsManager,
        clock,
        multiSegmentTermDictionaryManager,
        earlybirdDarkProtwittery,
        segmentSyncConfig,
        earlybirdWireModule.provideQueryTimeoutFactory(),
        earlybirdStartup,
        qualityFactor,
        earlybirdWireModule.provideSearchIndetwitteringMetricSet());

    earlybirdStateManager.setEarlybirdServer(earlybirdServer);
    criticalEtwitterceptionHandler.setShutdownHook(earlybirdServer::shutdown);

    return earlybirdServer;
  }

  private PartitionManager buildArchivePartitionManager(
      EarlybirdWireModule earlybirdWireModule,
      UserUpdatesStreamIndetwitterer userUpdatesStreamIndetwitterer,
      UserScrubGeoEventStreamIndetwitterer userScrubGeoEventStreamIndetwitterer,
      ZooKeeperTryLockFactory zkTryLockFactory,
      EarlybirdIndetwitterConfig earlybirdIndetwitterConfig,
      DynamicPartitionConfig dynamicPartitionConfig,
      SegmentManager segmentManager,
      QueryCacheManager queryCacheManager,
      SearchStatsReceiver searchStatsReceiver,
      EarlybirdServerSetManager serverSetManager,
      ScheduledEtwitterecutorServiceFactory partitionManagerEtwitterecutorServiceFactory,
      ScheduledEtwitterecutorServiceFactory simpleUserUpdateIndetwittererEtwitterecutorFactory,
      Clock clock,
      SegmentSyncConfig segmentSyncConfig,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler)
      throws IOEtwitterception {

      Preconditions.checkState(earlybirdIndetwitterConfig instanceof ArchiveEarlybirdIndetwitterConfig);
      LOG.info("Creating ArchiveSearchPartitionManager");
      return earlybirdWireModule.provideFullArchivePartitionManager(
          zkTryLockFactory,
          queryCacheManager,
          segmentManager,
          dynamicPartitionConfig,
          userUpdatesStreamIndetwitterer,
          userScrubGeoEventStreamIndetwitterer,
          searchStatsReceiver,
          (ArchiveEarlybirdIndetwitterConfig) earlybirdIndetwitterConfig,
          serverSetManager,
          partitionManagerEtwitterecutorServiceFactory,
          simpleUserUpdateIndetwittererEtwitterecutorFactory,
          earlybirdWireModule.provideSearchIndetwitteringMetricSet(),
          clock,
          segmentSyncConfig,
          criticalEtwitterceptionHandler);
  }
}
