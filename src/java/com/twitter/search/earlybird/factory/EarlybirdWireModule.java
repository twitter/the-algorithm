package com.twitter.search.earlybird.factory;

import java.io.IOEtwitterception;
import java.lang.management.ManagementFactory;
import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolEtwitterecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.sun.management.OperatingSystemMtwitterBean;

import org.apache.directory.api.util.Strings;
import org.apache.hadoop.fs.FileSystem;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.decider.Decider;
import com.twitter.finagle.stats.MetricsStatsReceiver;
import com.twitter.finagle.stats.StatsReceiver;
import com.twitter.search.common.aurora.AuroraSchedulerClient;
import com.twitter.search.common.concurrent.ScheduledEtwitterecutorServiceFactory;
import com.twitter.search.common.decider.DeciderUtil;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.file.FileUtils;
import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.common.metrics.SearchStatsReceiverImpl;
import com.twitter.search.common.partitioning.zookeeper.SearchZkClient;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.common.search.termination.QueryTimeoutFactory;
import com.twitter.search.common.util.io.kafka.FinagleKafkaClientUtils;
import com.twitter.search.common.util.io.kafka.ThriftDeserializer;
import com.twitter.search.common.util.ml.tensorflow_engine.TensorflowModelsManager;
import com.twitter.search.common.util.zktrylock.ZooKeeperTryLockFactory;
import com.twitter.search.common.util.zookeeper.ZooKeeperProtwittery;
import com.twitter.search.earlybird.EarlybirdCPUQualityFactor;
import com.twitter.search.earlybird.EarlybirdDarkProtwittery;
import com.twitter.search.earlybird.EarlybirdFinagleServerManager;
import com.twitter.search.earlybird.EarlybirdFuturePoolManager;
import com.twitter.search.earlybird.EarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.EarlybirdProductionFinagleServerManager;
import com.twitter.search.earlybird.EarlybirdServerSetManager;
import com.twitter.search.earlybird.EarlybirdWarmUpManager;
import com.twitter.search.earlybird.QualityFactor;
import com.twitter.search.earlybird.ServerSetMember;
import com.twitter.search.earlybird.UpdateableEarlybirdStateManager;
import com.twitter.search.earlybird.archive.ArchiveEarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.archive.ArchiveSearchPartitionManager;
import com.twitter.search.earlybird.common.CaughtUpMonitor;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.common.config.EarlybirdProperty;
import com.twitter.search.earlybird.common.userupdates.UserScrubGeoMap;
import com.twitter.search.earlybird.common.userupdates.UserUpdatesChecker;
import com.twitter.search.earlybird.common.userupdates.UserTable;
import com.twitter.search.earlybird.etwitterception.MissingKafkaTopicEtwitterception;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.indetwitter.EarlybirdSegmentFactory;
import com.twitter.search.earlybird.ml.ScoringModelsManager;
import com.twitter.search.earlybird.partition.AudioSpaceEventsStreamIndetwitterer;
import com.twitter.search.earlybird.partition.AudioSpaceTable;
import com.twitter.search.earlybird.partition.DynamicPartitionConfig;
import com.twitter.search.earlybird.partition.EarlybirdIndetwitterFlusher;
import com.twitter.search.earlybird.partition.EarlybirdIndetwitterLoader;
import com.twitter.search.earlybird.partition.EarlybirdKafkaConsumer;
import com.twitter.search.earlybird.partition.EarlybirdStartup;
import com.twitter.search.earlybird.partition.OptimizationAndFlushingCoordinationLock;
import com.twitter.search.earlybird.partition.TimeLimitedHadoopEtwitteristsCall;
import com.twitter.search.earlybird.partition.UserScrubGeoEventStreamIndetwitterer;
import com.twitter.search.earlybird.partition.freshstartup.FreshStartupHandler;
import com.twitter.search.earlybird.partition.HdfsUtil;
import com.twitter.search.earlybird.partition.KafkaStartup;
import com.twitter.search.earlybird.partition.MultiSegmentTermDictionaryManager;
import com.twitter.search.earlybird.partition.PartitionManager;
import com.twitter.search.earlybird.partition.PartitionManagerStartup;
import com.twitter.search.earlybird.partition.PartitionWriter;
import com.twitter.search.earlybird.partition.SearchIndetwitteringMetricSet;
import com.twitter.search.earlybird.partition.SegmentManager;
import com.twitter.search.earlybird.partition.SegmentSyncConfig;
import com.twitter.search.earlybird.partition.StartupUserEventIndetwitterer;
import com.twitter.search.earlybird.partition.TweetCreateHandler;
import com.twitter.search.earlybird.partition.TweetUpdateHandler;
import com.twitter.search.earlybird.partition.UserUpdatesStreamIndetwitterer;
import com.twitter.search.earlybird.querycache.QueryCacheConfig;
import com.twitter.search.earlybird.querycache.QueryCacheManager;
import com.twitter.search.earlybird.stats.EarlybirdSearcherStats;
import com.twitter.search.earlybird.util.CoordinatedEarlybirdAction;
import com.twitter.search.earlybird.util.EarlybirdDecider;
import com.twitter.search.earlybird.util.TermCountMonitor;
import com.twitter.search.earlybird.util.TweetCountMonitor;
import com.twitter.ubs.thriftjava.AudioSpaceBaseEvent;

/**
 * Production module that provides Earlybird components.
 */
public class EarlybirdWireModule {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdWireModule.class);
  private static final int MAtwitter_POLL_RECORDS = 1000;

  /**
   * How many threads we will use for building up the query cache during startup.
   * The number of threads will be set to 1 after this earlybird is current.
   */
  private static final int QUERY_CACHE_NUM_WORKER_THREADS_AT_STARTUP =
      EarlybirdConfig.getInt("query_cache_updater_startup_threads", 1);

  /**
   * Scheduled etwitterecutor service factory can be re-used in production.
   * All the managers can share the same etwitterecutor service factory.
   */
  private final ScheduledEtwitterecutorServiceFactory sharedEtwitterecutorServiceFactory =
      new ScheduledEtwitterecutorServiceFactory();

  private final SearchStatsReceiver sharedSearchStatsReceiver = new SearchStatsReceiverImpl();
  private final StatsReceiver sharedFinagleStatsReceiver = new MetricsStatsReceiver();

  private final SearchIndetwitteringMetricSet searchIndetwitteringMetricSet =
      new SearchIndetwitteringMetricSet(sharedSearchStatsReceiver);

  private final EarlybirdSearcherStats tweetsSearcherStats =
      new EarlybirdSearcherStats(sharedSearchStatsReceiver);

  private final CaughtUpMonitor indetwitterCaughtUpMonitor = new CaughtUpMonitor("dl_indetwitter");

  public CaughtUpMonitor provideIndetwitterCaughtUpMonitor() {
    return indetwitterCaughtUpMonitor;
  }

  private final CaughtUpMonitor kafkaIndetwitterCaughtUpMonitor = new CaughtUpMonitor("kafka_indetwitter");

  public CaughtUpMonitor provideKafkaIndetwitterCaughtUpMonitor() {
    return kafkaIndetwitterCaughtUpMonitor;
  }

  private final OptimizationAndFlushingCoordinationLock optimizationAndFlushingCoordinationLock =
      new OptimizationAndFlushingCoordinationLock();

  public OptimizationAndFlushingCoordinationLock provideOptimizationAndFlushingCoordinationLock() {
    return optimizationAndFlushingCoordinationLock;
  }

  public QueryTimeoutFactory provideQueryTimeoutFactory() {
    return new QueryTimeoutFactory();
  }

  public static class ZooKeeperClients {
    public ZooKeeperProtwittery discoveryClient;
    public ZooKeeperProtwittery stateClient;

    public ZooKeeperClients() {
      this(
          SearchZkClient.getServiceDiscoveryZooKeeperClient(),
          SearchZkClient.getSZooKeeperClient());
    }

    public ZooKeeperClients(ZooKeeperProtwittery discoveryClient, ZooKeeperProtwittery stateClient) {
      this.discoveryClient = discoveryClient;
      this.stateClient = stateClient;
    }
  }

  /**
   * Provides the earlybird decider.
   */
  public Decider provideDecider() {
    return EarlybirdDecider.initialize();
  }

  /**
   * Provides the set of ZooKeeper clients to be used by earlybird.
   */
  public ZooKeeperClients provideZooKeeperClients() {
    return new ZooKeeperClients();
  }

  /**
   * Provides the query cache config.
   */
  public QueryCacheConfig provideQueryCacheConfig(SearchStatsReceiver searchStatsReceiver) {
    return new QueryCacheConfig(searchStatsReceiver);
  }

  /**
   * Provides the earlybird indetwitter config.
   */
  public EarlybirdIndetwitterConfig provideEarlybirdIndetwitterConfig(
      Decider decider, SearchIndetwitteringMetricSet indetwitteringMetricSet,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    return EarlybirdIndetwitterConfigUtil.createEarlybirdIndetwitterConfig(decider, indetwitteringMetricSet,
        criticalEtwitterceptionHandler);
  }

  public DynamicPartitionConfig provideDynamicPartitionConfig() {
    return new DynamicPartitionConfig(PartitionConfigUtil.initPartitionConfig());
  }

  /**
   * Provides the segment manager to be used by this earlybird.
   */
  public SegmentManager provideSegmentManager(
      DynamicPartitionConfig dynamicPartitionConfig,
      EarlybirdIndetwitterConfig earlybirdIndetwitterConfig,
      SearchIndetwitteringMetricSet partitionIndetwitteringMetricSet,
      EarlybirdSearcherStats searcherStats,
      SearchStatsReceiver earlybirdServerStats,
      UserUpdatesChecker userUpdatesChecker,
      SegmentSyncConfig segmentSyncConfig,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      Clock clock,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    return new SegmentManager(
        dynamicPartitionConfig,
        earlybirdIndetwitterConfig,
        partitionIndetwitteringMetricSet,
        searcherStats,
        earlybirdServerStats,
        userUpdatesChecker,
        segmentSyncConfig,
        userTable,
        userScrubGeoMap,
        clock,
        EarlybirdConfig.getMatwitterSegmentSize(),
        criticalEtwitterceptionHandler,
        provideKafkaIndetwitterCaughtUpMonitor());
  }

  public QueryCacheManager provideQueryCacheManager(
      QueryCacheConfig config,
      EarlybirdIndetwitterConfig indetwitterConfig,
      int matwitterEnabledSegments,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      ScheduledEtwitterecutorServiceFactory queryCacheUpdaterScheduledEtwitterecutorFactory,
      SearchStatsReceiver searchStatsReceiver,
      EarlybirdSearcherStats searcherStats,
      Decider decider,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler,
      Clock clock) {
    return new QueryCacheManager(config, indetwitterConfig, matwitterEnabledSegments, userTable,
        userScrubGeoMap, queryCacheUpdaterScheduledEtwitterecutorFactory, searchStatsReceiver,
        searcherStats, decider, criticalEtwitterceptionHandler, clock);
  }

  public TermCountMonitor provideTermCountMonitor(
      SegmentManager segmentManager, ScheduledEtwitterecutorServiceFactory etwitterecutorServiceFactory,
      SearchStatsReceiver searchStatsReceiver,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    return new TermCountMonitor(segmentManager, etwitterecutorServiceFactory, 500, TimeUnit.MILLISECONDS,
        searchStatsReceiver, criticalEtwitterceptionHandler);
  }

  public TweetCountMonitor provideTweetCountMonitor(
      SegmentManager segmentManager,
      ScheduledEtwitterecutorServiceFactory etwitterecutorServiceFactory,
      SearchStatsReceiver searchStatsReceiver,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    return new TweetCountMonitor(segmentManager, etwitterecutorServiceFactory, 500,
        TimeUnit.MILLISECONDS, searchStatsReceiver, criticalEtwitterceptionHandler);
  }

  /**
   * Returns a manager that keeps track of earlybird's global state while it runs.
   */
  public UpdateableEarlybirdStateManager provideUpdateableEarlybirdStateManager(
      EarlybirdIndetwitterConfig earlybirdIndetwitterConfig,
      DynamicPartitionConfig dynamicPartitionConfig,
      ZooKeeperProtwittery zooKeeperClient,
      AuroraSchedulerClient schedulerClient,
      ScheduledEtwitterecutorServiceFactory etwitterecutorServiceFactory,
      ScoringModelsManager scoringModelsManager,
      TensorflowModelsManager tensorflowModelsManager,
      SearchStatsReceiver searchStatsReceiver,
      SearchDecider searchDecider,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    Clock clock = provideClockForStateManager();

    return new UpdateableEarlybirdStateManager(
        earlybirdIndetwitterConfig, dynamicPartitionConfig, zooKeeperClient, schedulerClient,
        etwitterecutorServiceFactory, scoringModelsManager, tensorflowModelsManager, searchStatsReceiver,
        searchDecider, criticalEtwitterceptionHandler,
        clock);
  }

  public Clock provideClockForStateManager() {
    return this.provideClock();
  }

  public ScheduledEtwitterecutorServiceFactory providePartitionManagerEtwitterecutorFactory() {
    return sharedEtwitterecutorServiceFactory;
  }

  public ScheduledEtwitterecutorServiceFactory provideStateUpdateManagerEtwitterecutorFactory() {
    return sharedEtwitterecutorServiceFactory;
  }

  public ScheduledEtwitterecutorServiceFactory provideTermCountMonitorScheduledEtwitterecutorFactory() {
    return sharedEtwitterecutorServiceFactory;
  }

  public ScheduledEtwitterecutorServiceFactory provideTweetCountMonitorScheduledEtwitterecutorFactory() {
    return sharedEtwitterecutorServiceFactory;
  }

  /**
   * Provides the ScheduledEtwitterecutorServiceFactory that will be used to schedule all query cache
   * update tasks.
   */
  public ScheduledEtwitterecutorServiceFactory provideQueryCacheUpdateTaskScheduledEtwitterecutorFactory() {
    return new ScheduledEtwitterecutorServiceFactory() {
      @Override
      public QueryCacheUpdaterScheduledEtwitterecutorService<ScheduledThreadPoolEtwitterecutor> build(
          String threadNameFormat, boolean isDaemon) {
        ScheduledThreadPoolEtwitterecutor threadpoolEtwitterecutor =
            new ScheduledThreadPoolEtwitterecutor(QUERY_CACHE_NUM_WORKER_THREADS_AT_STARTUP,
                buildThreadFactory(threadNameFormat, isDaemon));
        threadpoolEtwitterecutor.setMatwitterimumPoolSize(QUERY_CACHE_NUM_WORKER_THREADS_AT_STARTUP);
        threadpoolEtwitterecutor.setCorePoolSize(QUERY_CACHE_NUM_WORKER_THREADS_AT_STARTUP);
        threadpoolEtwitterecutor.setEtwitterecuteEtwitteristingDelayedTasksAfterShutdownPolicy(false);
        threadpoolEtwitterecutor.setContinueEtwitteristingPeriodicTasksAfterShutdownPolicy(false);
        threadpoolEtwitterecutor.setRemoveOnCancelPolicy(true);
        LOG.info("Starting query cache etwitterecutor with {} thread.",
            QUERY_CACHE_NUM_WORKER_THREADS_AT_STARTUP);

        return new QueryCacheUpdaterScheduledEtwitterecutorService<ScheduledThreadPoolEtwitterecutor>(
            threadpoolEtwitterecutor) {
          @Override public void setWorkerPoolSizeAfterStartup() {
            delegate.setCorePoolSize(1);
            delegate.setMatwitterimumPoolSize(1);
            LOG.info("Reset query cache etwitterecutor to be single threaded.");
          }
        };
      }
    };
  }

  public ScheduledEtwitterecutorServiceFactory provideSimpleUserUpdateIndetwittererScheduledEtwitterecutorFactory() {
    return sharedEtwitterecutorServiceFactory;
  }

  /**
   * Returns the manager that manages the pool of searcher threads.
   */
  public EarlybirdFuturePoolManager provideFuturePoolManager() {
    return new EarlybirdFuturePoolManager("SearcherWorker");
  }

  /**
   * Returns the manager that manages all earlybird finagle servers (warm up and production).
   */
  public EarlybirdFinagleServerManager provideFinagleServerManager(
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    return new EarlybirdProductionFinagleServerManager(criticalEtwitterceptionHandler);
  }

  /**
   * Creates the production serverset manager.
   */
  public EarlybirdServerSetManager provideServerSetManager(
      ZooKeeperProtwittery discoveryClient,
      DynamicPartitionConfig dynamicPartitionConfig,
      SearchStatsReceiver searchStatsReceiver,
      int port,
      String serverSetNamePrefitwitter) {
    return new EarlybirdServerSetManager(
        searchStatsReceiver,
        discoveryClient,
        dynamicPartitionConfig.getCurrentPartitionConfig(),
        port,
        serverSetNamePrefitwitter);
  }

  /**
   * Creates the warm up serverset manager.
   */
  public EarlybirdWarmUpManager provideWarmUpManager(
      ZooKeeperProtwittery discoveryClient,
      DynamicPartitionConfig dynamicPartitionConfig,
      SearchStatsReceiver searchStatsReceiver,
      Decider decider,
      Clock clock,
      int port,
      String serverSetNamePrefitwitter) {
    return new EarlybirdWarmUpManager(
        new EarlybirdServerSetManager(
            searchStatsReceiver,
            discoveryClient,
            dynamicPartitionConfig.getCurrentPartitionConfig(),
            port,
            serverSetNamePrefitwitter),
        dynamicPartitionConfig.getCurrentPartitionConfig(),
        searchIndetwitteringMetricSet,
        decider,
        clock);
  }

  /**
   * Returns a dark protwittery that knows how to send dark traffic to the warm up earlybird serverset.
   */
  public EarlybirdDarkProtwittery provideEarlybirdDarkProtwittery(
      SearchDecider searchDecider,
      StatsReceiver finagleStatsReceiver,
      EarlybirdServerSetManager earlybirdServerSetManager,
      EarlybirdWarmUpManager earlybirdWarmUpManager,
      String clusterName) {
    return new EarlybirdDarkProtwittery(searchDecider,
                                  finagleStatsReceiver.scope("dark_protwittery"),
                                  earlybirdServerSetManager,
                                  earlybirdWarmUpManager,
                                  clusterName);
  }


  /**
   * Returns the manager for all (non-Tensorflow) scoring models.
   */
  public ScoringModelsManager provideScoringModelsManager(
      SearchStatsReceiver serverStats,
      EarlybirdIndetwitterConfig earlybirdIndetwitterConfig) {
    boolean modelsEnabled = EarlybirdConfig.getBool("scoring_models_enabled", false);
    if (!modelsEnabled) {
      LOG.info("Scoring Models - Disabled in the config. Not loading any models.");
      serverStats.getCounter("scoring_models_disabled_in_config").increment();
      return ScoringModelsManager.NO_OP_MANAGER;
    }

    String hdfsNameNode = EarlybirdConfig.getString("scoring_models_namenode");
    String hdfsModelsPath = EarlybirdConfig.getString("scoring_models_basedir");
    try {
      return ScoringModelsManager.create(
          serverStats, hdfsNameNode, hdfsModelsPath, earlybirdIndetwitterConfig.getSchema());
    } catch (IOEtwitterception e) {
      LOG.error("Scoring Models - Error creating ScoringModelsManager", e);
      serverStats.getCounter("scoring_models_initialization_errors").increment();
      return ScoringModelsManager.NO_OP_MANAGER;
    }
  }

  /**
   * Provides the manager for all Tensorflow models.
   */
  public TensorflowModelsManager provideTensorflowModelsManager(
      SearchStatsReceiver serverStats,
      String statsPrefitwitter,
      Decider decider,
      EarlybirdIndetwitterConfig earlybirdIndetwitterConfig) {

    boolean modelsEnabled = EarlybirdProperty.TF_MODELS_ENABLED.get(false);

    if (!modelsEnabled) {
      LOG.info("Tensorflow Models - Disabled in the config. Not loading any models.");
      serverStats.getCounter("tf_models_disabled_in_config").increment();
      return TensorflowModelsManager.createNoOp(statsPrefitwitter);
    }

    String modelsConfigPath =
        Preconditions.checkNotNull(EarlybirdProperty.TF_MODELS_CONFIG_PATH.get());


    int intraOpThreads = Preconditions.checkNotNull(EarlybirdProperty.TF_INTRA_OP_THREADS.get(0));
    int interOpThreads = Preconditions.checkNotNull(EarlybirdProperty.TF_INTER_OP_THREADS.get(0));

    TensorflowModelsManager.initTensorflowThreadPools(intraOpThreads, interOpThreads);

    return TensorflowModelsManager.createUsingConfigFile(
        FileUtils.getFileHandle(modelsConfigPath),
        true,
        statsPrefitwitter,
        () -> DeciderUtil.isAvailableForRandomRecipient(
          decider, "enable_tf_serve_models"),
        () -> decider.isAvailable("enable_tf_load_models"),
        earlybirdIndetwitterConfig.getSchema());
  }

  public SearchStatsReceiver provideEarlybirdServerStatsReceiver() {
    return sharedSearchStatsReceiver;
  }

  public StatsReceiver provideFinagleStatsReceiver() {
    return sharedFinagleStatsReceiver;
  }

  public SearchIndetwitteringMetricSet provideSearchIndetwitteringMetricSet() {
    return searchIndetwitteringMetricSet;
  }

  public EarlybirdSearcherStats provideTweetsSearcherStats() {
    return tweetsSearcherStats;
  }

  /**
   * Provides the clock to be used by this earlybird.
   */
  public Clock provideClock() {
    return Clock.SYSTEM_CLOCK;
  }

  /**
   * Provides the config for the multi-segment term dictionary manager.
   */
  public MultiSegmentTermDictionaryManager.Config provideMultiSegmentTermDictionaryManagerConfig() {
    return new MultiSegmentTermDictionaryManager.Config(
        Lists.newArrayList(
            EarlybirdFieldConstant.FROM_USER_ID_FIELD.getFieldName()));
  }

  /**
   * Provides the manager for the term dictionary that spans all segments.
   */
  public MultiSegmentTermDictionaryManager provideMultiSegmentTermDictionaryManager(
      MultiSegmentTermDictionaryManager.Config termDictionaryConfig,
      SegmentManager segmentManager,
      SearchStatsReceiver statsReceiver,
      Decider decider,
      EarlybirdCluster earlybirdCluster) {
    return new MultiSegmentTermDictionaryManager(
        termDictionaryConfig, segmentManager, statsReceiver, decider, earlybirdCluster);
  }

  /**
   * Returns the partition manager to be used by the archive earlybirds.
   */
  public PartitionManager provideFullArchivePartitionManager(
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
      SearchIndetwitteringMetricSet earlybirdSearchIndetwitteringMetricSet,
      Clock clock,
      SegmentSyncConfig segmentSyncConfig,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) throws IOEtwitterception {

    return new ArchiveSearchPartitionManager(
        zooKeeperTryLockFactory,
        queryCacheManager,
        segmentManager,
        dynamicPartitionConfig,
        userUpdatesStreamIndetwitterer,
        userScrubGeoEventStreamIndetwitterer,
        searchStatsReceiver,
        earlybirdIndetwitterConfig,
        serverSetMember,
        etwitterecutorServiceFactory,
        userUpdateIndetwittererEtwitterecutorFactory,
        earlybirdSearchIndetwitteringMetricSet,
        segmentSyncConfig,
        clock,
        criticalEtwitterceptionHandler);
  }

  /**
   * Provides the SegmentSyncConfig instance to be used by earlybird.
   */
  public SegmentSyncConfig provideSegmentSyncConfig(EarlybirdCluster cluster) {
    String scrubGen = null;
    if (cluster == EarlybirdCluster.FULL_ARCHIVE) {
      scrubGen = EarlybirdProperty.EARLYBIRD_SCRUB_GEN.get();
      LOG.info("The scrubGen provided from Aurora is: {}", scrubGen);
      Preconditions.checkState(Strings.isNotEmpty(scrubGen));
    }
    return new SegmentSyncConfig(Optional.ofNullable(scrubGen));
  }

  protected void storeEarlybirdStartupProducts(
      TweetCreateHandler tweetCreateHandler,
      PartitionWriter partitionWriter,
      EarlybirdIndetwitterFlusher earlybirdIndetwitterFlusher
  ) {
    // TestWireModule wants to store these for further use.
  }

  /**
   * What directory are we going to load segments from on startup.
   *
   * When you're running loadtests or stagingN instances and they don't have a recent indetwitter
   * flushed, it can take hours to generate a new indetwitter with a fresh startup. This slows
   * down development. If the read_indetwitter_from_prod_location flag is set to true, we will read
   * the indetwitter from the location where prod instances are flushing their indetwitter to.
   * Unset it if you want to generate your own indetwitter.
   *
   * @return a string with the directory.
   */
  public String getIndetwitterLoadingDirectory() {
    boolean readIndetwitterFromProdLocation = EarlybirdProperty.READ_INDEtwitter_FROM_PROD_LOCATION.get(false);
    String environment = EarlybirdProperty.ENV.get("no_env_specified"); // default value for tests.
    String readIndetwitterDir = EarlybirdProperty.HDFS_INDEtwitter_SYNC_DIR.get();

    if (readIndetwitterFromProdLocation) {
      LOG.info("Will attempt to read indetwitter from prod locations");
      LOG.info("Indetwitter directory provided: {}", readIndetwitterDir);
      // Replacing the path is a bit hacky, but it works ok.
      readIndetwitterDir = readIndetwitterDir.replace("/" + environment + "/", "/prod/");
      LOG.info("Will instead use indetwitter directory: {}", readIndetwitterDir);
    }

    return readIndetwitterDir;
  }

  /**
   * Indetwitterer for audio space events.
   */
  public AudioSpaceEventsStreamIndetwitterer provideAudioSpaceEventsStreamIndetwitterer(
      AudioSpaceTable audioSpaceTable,
      Clock clock) {
    try {
      return new AudioSpaceEventsStreamIndetwitterer(
          FinagleKafkaClientUtils.newKafkaConsumerForAssigning(
              "",
              new ThriftDeserializer<>(AudioSpaceBaseEvent.class),
              "",
              20
          ), audioSpaceTable, clock);
    } catch (MissingKafkaTopicEtwitterception etwitter) {
      LOG.error("Missing kafka stream", etwitter);
      return null;
    }
  }

  /**
   * Returns a class to start the Earlybird. See {@link EarlybirdStartup}.
   */
  public EarlybirdStartup provideEarlybirdStartup(
      PartitionManager partitionManager,
      UserUpdatesStreamIndetwitterer userUpdatesStreamIndetwitterer,
      UserScrubGeoEventStreamIndetwitterer userScrubGeoEventStreamIndetwitterer,
      AudioSpaceEventsStreamIndetwitterer audioSpaceEventsStreamIndetwitterer,
      DynamicPartitionConfig dynamicPartitionConfig,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler,
      SegmentManager segmentManager,
      MultiSegmentTermDictionaryManager multiSegmentTermDictionaryManager,
      QueryCacheManager queryCacheManager,
      ZooKeeperTryLockFactory zooKeeperTryLockFactory,
      ServerSetMember serverSetMember,
      Clock clock,
      SegmentSyncConfig segmentSyncConfig,
      EarlybirdSegmentFactory earlybirdSegmentFactory,
      EarlybirdCluster cluster,
      SearchDecider decider) throws IOEtwitterception {
    if (cluster == EarlybirdCluster.FULL_ARCHIVE) {
      return new PartitionManagerStartup(clock, partitionManager);
    }

    // Check that the earlybird name is what we're etwitterpecting so we can build the kafka topics.
    String earlybirdName = EarlybirdProperty.EARLYBIRD_NAME.get();
    Preconditions.checkArgument("earlybird-realtime".equals(earlybirdName)
        || "earlybird-protected".equals(earlybirdName)
        || "earlybird-realtime-etwitterp0".equals(earlybirdName)
        || "earlybird-realtime_cg".equals(earlybirdName));

    StartupUserEventIndetwitterer startupUserEventIndetwitterer = new StartupUserEventIndetwitterer(
        provideSearchIndetwitteringMetricSet(),
        userUpdatesStreamIndetwitterer,
        userScrubGeoEventStreamIndetwitterer,
        segmentManager,
        clock);

    // Coordinate leaving the serverset to flush segments to HDFS.
    CoordinatedEarlybirdAction actionCoordinator = new CoordinatedEarlybirdAction(
        zooKeeperTryLockFactory,
        "segment_flusher",
        dynamicPartitionConfig,
        serverSetMember,
        criticalEtwitterceptionHandler,
        segmentSyncConfig);
    actionCoordinator.setShouldSynchronize(true);

    FileSystem hdfsFileSystem = HdfsUtil.getHdfsFileSystem();
    EarlybirdIndetwitterFlusher earlybirdIndetwitterFlusher = new EarlybirdIndetwitterFlusher(
        actionCoordinator,
        hdfsFileSystem,
        EarlybirdProperty.HDFS_INDEtwitter_SYNC_DIR.get(),
        segmentManager,
        dynamicPartitionConfig.getCurrentPartitionConfig(),
        clock,
        new TimeLimitedHadoopEtwitteristsCall(hdfsFileSystem),
        provideOptimizationAndFlushingCoordinationLock());

    String baseTopicName = "search_ingester_%s_events_%s_%s";

    String earlybirdType;

    if ("earlybird-protected".equals(earlybirdName)) {
      earlybirdType = "protected";
    } else if ("earlybird-realtime_cg".equals(earlybirdName)) {
      earlybirdType = "realtime_cg";
    } else {
      earlybirdType = "realtime";
    }

    String tweetTopicName = String.format(
        baseTopicName,
        "indetwittering",
        earlybirdType,
        EarlybirdProperty.KAFKA_ENV.get());

    String updateTopicName = String.format(
        baseTopicName,
        "update",
        earlybirdType,
        EarlybirdProperty.KAFKA_ENV.get());

    LOG.info("Tweet topic: {}", tweetTopicName);
    LOG.info("Update topic: {}", updateTopicName);

    TopicPartition tweetTopic = new TopicPartition(
        tweetTopicName,
        dynamicPartitionConfig.getCurrentPartitionConfig().getIndetwitteringHashPartitionID());
    TopicPartition updateTopic = new TopicPartition(
        updateTopicName,
        dynamicPartitionConfig.getCurrentPartitionConfig().getIndetwitteringHashPartitionID());

    EarlybirdKafkaConsumersFactory earlybirdKafkaConsumersFactory =
        provideEarlybirdKafkaConsumersFactory();
    FreshStartupHandler freshStartupHandler = new FreshStartupHandler(
        clock,
        earlybirdKafkaConsumersFactory,
        tweetTopic,
        updateTopic,
        segmentManager,
        EarlybirdConfig.getMatwitterSegmentSize(),
        EarlybirdConfig.getLateTweetBuffer(),
        criticalEtwitterceptionHandler
    );

    TweetUpdateHandler updateHandler = new TweetUpdateHandler(segmentManager);

    CoordinatedEarlybirdAction postOptimizationRebuilds = new CoordinatedEarlybirdAction(
            zooKeeperTryLockFactory,
            "post_optimization_rebuilds",
            dynamicPartitionConfig,
            serverSetMember,
            criticalEtwitterceptionHandler,
            segmentSyncConfig
    );
    postOptimizationRebuilds.setShouldSynchronize(true);
    CoordinatedEarlybirdAction gcAction = new CoordinatedEarlybirdAction(
            zooKeeperTryLockFactory,
            "gc_before_optimization",
            dynamicPartitionConfig,
            serverSetMember,
            criticalEtwitterceptionHandler,
            segmentSyncConfig
    );
    gcAction.setShouldSynchronize(true);

    TweetCreateHandler createHandler = new TweetCreateHandler(
        segmentManager,
        provideSearchIndetwitteringMetricSet(),
        criticalEtwitterceptionHandler,
        multiSegmentTermDictionaryManager,
        queryCacheManager,
        postOptimizationRebuilds,
        gcAction,
        EarlybirdConfig.getLateTweetBuffer(),
        EarlybirdConfig.getMatwitterSegmentSize(),
        provideKafkaIndetwitterCaughtUpMonitor(),
        provideOptimizationAndFlushingCoordinationLock());

    PartitionWriter partitionWriter = new PartitionWriter(
        createHandler,
        updateHandler,
        criticalEtwitterceptionHandler,
        PenguinVersion.versionFromByteValue(EarlybirdConfig.getPenguinVersionByte()),
        clock);

    KafkaConsumer<Long, ThriftVersionedEvents> rawKafkaConsumer =
        earlybirdKafkaConsumersFactory.createKafkaConsumer(
            "earlybird_tweet_kafka_consumer");

    EarlybirdKafkaConsumer earlybirdKafkaConsumer = provideKafkaConsumer(
        criticalEtwitterceptionHandler,
        rawKafkaConsumer,
        tweetTopic,
        updateTopic,
        partitionWriter,
        earlybirdIndetwitterFlusher);

    EarlybirdIndetwitterLoader earlybirdIndetwitterLoader = new EarlybirdIndetwitterLoader(
        hdfsFileSystem,
        getIndetwitterLoadingDirectory(), // See SEARCH-32839
        EarlybirdProperty.ENV.get("default_env_value"),
        dynamicPartitionConfig.getCurrentPartitionConfig(),
        earlybirdSegmentFactory,
        segmentSyncConfig,
        clock);

    this.storeEarlybirdStartupProducts(
        createHandler,
        partitionWriter,
        earlybirdIndetwitterFlusher
    );

    return new KafkaStartup(
        segmentManager,
        earlybirdKafkaConsumer,
        startupUserEventIndetwitterer,
        userUpdatesStreamIndetwitterer,
        userScrubGeoEventStreamIndetwitterer,
        audioSpaceEventsStreamIndetwitterer,
        queryCacheManager,
        earlybirdIndetwitterLoader,
        freshStartupHandler,
        provideSearchIndetwitteringMetricSet(),
        multiSegmentTermDictionaryManager,
        criticalEtwitterceptionHandler,
        decider
    );
  }

  public QualityFactor provideQualityFactor(
      Decider decider,
      SearchStatsReceiver searchStatsReceiver
  ) {
    return new EarlybirdCPUQualityFactor(decider,
        ManagementFactory.getPlatformMtwitterBean(OperatingSystemMtwitterBean.class),
        searchStatsReceiver);
  }

  /**
   * Returns a new UserUpdatesKafkaConsumer to read user updates.
   */
  public UserUpdatesStreamIndetwitterer provideUserUpdatesKafkaConsumer(
      SegmentManager segmentManager) {
    try {
      return new UserUpdatesStreamIndetwitterer(
          UserUpdatesStreamIndetwitterer.provideKafkaConsumer(),
          EarlybirdProperty.USER_UPDATES_KAFKA_TOPIC.get(),
          provideSearchIndetwitteringMetricSet(),
          segmentManager);
    } catch (MissingKafkaTopicEtwitterception etwitter) {
      // Yes, it will crash the server. We've never seen this topic missing, but
      // we've seen some others, so we had to build this functionality in the
      // constructor. If one day this one goes missing, we'll have to figure out
      // how to handle it. For now, we crash.
      throw new RuntimeEtwitterception(etwitter);
    }
  }

  /**
   * Returns a new UserScrubGeosKafkaConsumer to read geo scrubbing events.
   */
  public UserScrubGeoEventStreamIndetwitterer provideUserScrubGeoEventKafkaConsumer(
      SegmentManager segmentManager) {
    try {
      return new UserScrubGeoEventStreamIndetwitterer(
          UserScrubGeoEventStreamIndetwitterer.provideKafkaConsumer(),
          EarlybirdProperty.USER_SCRUB_GEO_KAFKA_TOPIC.get(),
          provideSearchIndetwitteringMetricSet(),
          segmentManager);
    } catch (MissingKafkaTopicEtwitterception etwitter) {
      /**
       * See {@link #provideUserUpdatesKafkaConsumer}
       */
      throw new RuntimeEtwitterception(etwitter);
    }
  }

  /**
   * Returns a new ProductionEarlybirdKafkaConsumer to read ThriftVersionedEvents.
   */
  public EarlybirdKafkaConsumersFactory provideEarlybirdKafkaConsumersFactory() {
    return new ProductionEarlybirdKafkaConsumersFactory(
        EarlybirdProperty.KAFKA_PATH.get(),
        MAtwitter_POLL_RECORDS
    );
  }

  /**
   * Returns a class to read Tweets in the Earlybird. See {@link EarlybirdKafkaConsumer}.
   */
  public EarlybirdKafkaConsumer provideKafkaConsumer(
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler,
      KafkaConsumer<Long, ThriftVersionedEvents> rawKafkaConsumer,
      TopicPartition tweetTopic,
      TopicPartition updateTopic,
      PartitionWriter partitionWriter,
      EarlybirdIndetwitterFlusher earlybirdIndetwitterFlusher
  ) {
    return new EarlybirdKafkaConsumer(
        rawKafkaConsumer,
        provideSearchIndetwitteringMetricSet(),
        criticalEtwitterceptionHandler,
        partitionWriter,
        tweetTopic,
        updateTopic,
        earlybirdIndetwitterFlusher,
        provideKafkaIndetwitterCaughtUpMonitor());
  }
}
