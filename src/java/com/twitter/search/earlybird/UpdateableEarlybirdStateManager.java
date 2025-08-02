package com.twitter.search.earlybird;

import java.io.File;
import java.io.IOEtwitterception;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javatwitter.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;

import org.apache.thrift.TEtwitterception;
import org.apache.zookeeper.KeeperEtwitterception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.common.zookeeper.ZooKeeperClient;
import com.twitter.search.common.aurora.AuroraSchedulerClient;
import com.twitter.search.common.concurrent.ScheduledEtwitterecutorServiceFactory;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.file.LocalFile;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.common.schema.AnalyzerFactory;
import com.twitter.search.common.schema.DynamicSchema;
import com.twitter.search.common.schema.ImmutableSchema;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.schema.thriftjava.ThriftSchema;
import com.twitter.search.common.util.ml.tensorflow_engine.TensorflowModelsManager;
import com.twitter.search.common.util.thrift.ThriftUtils;
import com.twitter.search.common.util.zookeeper.ZooKeeperProtwittery;
import com.twitter.search.earlybird.common.NonPagingAssert;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.ml.ScoringModelsManager;
import com.twitter.search.earlybird.partition.DynamicPartitionConfig;
import com.twitter.search.earlybird.partition.PartitionConfig;
import com.twitter.search.earlybird.partition.PartitionConfigLoader;
import com.twitter.search.earlybird.partition.PartitionConfigLoadingEtwitterception;
import com.twitter.search.earlybird.util.OneTaskScheduledEtwitterecutorManager;
import com.twitter.search.earlybird.util.PeriodicActionParams;
import com.twitter.search.earlybird.util.ShutdownWaitTimeParams;

/**
 * A class that keeps track of Earlybird state that may change while an Earlybird runs, and keeps
 * that state up to date. Currently keeps track of the current Earlybird schema and partition
 * configuration, and periodically updates them from Zookeeper. It also reloads periodically the
 * scoring models from HDFS.
 */
public class UpdateableEarlybirdStateManager etwittertends OneTaskScheduledEtwitterecutorManager {
  private static final Logger LOG = LoggerFactory.getLogger(UpdateableEarlybirdStateManager.class);
  public static final String SCHEMA_SUFFItwitter = ".schema.v";

  private static final String THREAD_NAME_PATTERN = "state_update-%d";
  private static final boolean THREAD_IS_DAEMON = true;
  private static final long EtwitterECUTOR_SHUTDOWN_WAIT_SEC = 5;

  private static final String DEFAULT_ZK_SCHEMA_LOCATION =
      "/twitter/search/production/earlybird/schema";
  private static final String DEFAULT_LOCAL_SCHEMA_LOCATION =
      "/home/search/earlybird_schema_canary";
  private static final long DEFAULT_UPDATE_PERIOD_MILLIS =
      TimeUnit.MINUTES.toMillis(30);

  private static final String SCHEMA_MAJOR_VERSION_NAME =
      "schema_major_version";
  private static final String SCHEMA_MINOR_VERSION_NAME =
      "schema_minor_version";
  private static final String LAST_SUCCESSFUL_SCHEMA_RELOAD_TIME_MILLIS_NAME =
      "last_successful_schema_reload_timestamp_millis";
  @VisibleForTesting
  static final String FAIL_TO_LOAD_SCHEMA_COUNT_NAME =
      "fail_to_load_schema_count";
  @VisibleForTesting
  static final String HOST_IS_CANARY_SCHEME = "host_is_canary_schema";
  @VisibleForTesting
  static final String DID_NOT_FIND_SCHEMA_COUNT_NAME =
      "did_not_find_schema_count";
  private static final String LAST_SUCCESSFUL_PARTITION_CONFIG_RELOAD_TIME_MILLIS_NAME =
      "last_successful_partition_config_reload_timestamp_millis";
  @VisibleForTesting
  static final String FAIL_TO_LOAD_PARTITION_CONFIG_COUNT_NAME =
      "fail_to_load_partition_config_count";
  @VisibleForTesting
  static final String HOST_IS_IN_LAYOUT_STAT_NAME = "host_is_in_layout";
  private static final String NOT_IN_LAYOUT_SHUT_DOWN_ATTEMPTED_NAME =
      "not_in_layout_shut_down_attempted";

  private static final String SHUT_DOWN_EARLYBIRD_WHEN_NOT_IN_LAYOUT_DECIDER_KEY =
      "shut_down_earlybird_when_not_in_layout";

  private static final String NO_SHUTDOWN_WHEN_NOT_IN_LAYOUT_NAME =
      "no_shutdown_when_not_in_layout";

  private final SearchLongGauge schemaMajorVersion;
  private final SearchLongGauge schemaMinorVersion;
  private final SearchLongGauge lastSuccessfulSchemaReloadTimeMillis;
  private final SearchCounter failToLoadSchemaCount;
  private final SearchLongGauge hostIsCanarySchema;
  private final SearchCounter didNotFindSchemaCount;
  private final SearchLongGauge lastSuccessfulPartitionConfigReloadTimeMillis;
  private final SearchCounter failToLoadPartitionConfigCount;
  private final SearchLongGauge hostIsInLayout;
  private final SearchCounter notInLayoutShutDownAttemptedCount;
  private final SearchLongGauge noShutdownWhenNotInLayoutGauge;

  private final EarlybirdIndetwitterConfig indetwitterConfig;
  private final DynamicPartitionConfig partitionConfig;
  private final String schemaLocationOnLocal;
  private final String schemaLocationOnZK;
  private final ZooKeeperProtwittery zkClient;
  private final AuroraSchedulerClient schedulerClient;
  private final ScoringModelsManager scoringModelsManager;
  private final TensorflowModelsManager tensorflowModelsManager;
  private final SearchDecider searchDecider;
  private final AtomicLong noShutdownWhenNotInLayout;
  private EarlybirdServer earlybirdServer;
  private Clock clock;

  public UpdateableEarlybirdStateManager(
      EarlybirdIndetwitterConfig indetwitterConfig,
      DynamicPartitionConfig partitionConfig,
      ZooKeeperProtwittery zooKeeperClient,
      @Nullable  AuroraSchedulerClient schedulerClient,
      ScheduledEtwitterecutorServiceFactory etwitterecutorServiceFactory,
      ScoringModelsManager scoringModelsManager,
      TensorflowModelsManager tensorflowModelsManager,
      SearchStatsReceiver searchStatsReceiver,
      SearchDecider searchDecider,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler,
      Clock clock) {
    this(
        indetwitterConfig,
        partitionConfig,
        DEFAULT_LOCAL_SCHEMA_LOCATION,
        DEFAULT_ZK_SCHEMA_LOCATION,
        DEFAULT_UPDATE_PERIOD_MILLIS,
        zooKeeperClient,
        schedulerClient,
        etwitterecutorServiceFactory,
        scoringModelsManager,
        tensorflowModelsManager,
        searchStatsReceiver,
        searchDecider,
        criticalEtwitterceptionHandler,
        clock);
  }

  protected UpdateableEarlybirdStateManager(
      EarlybirdIndetwitterConfig indetwitterConfig,
      DynamicPartitionConfig partitionConfig,
      String schemaLocationOnLocal,
      String schemaLocationOnZK,
      long updatePeriodMillis,
      ZooKeeperProtwittery zkClient,
      @Nullable  AuroraSchedulerClient schedulerClient,
      ScheduledEtwitterecutorServiceFactory etwitterecutorServiceFactory,
      ScoringModelsManager scoringModelsManager,
      TensorflowModelsManager tensorflowModelsManager,
      SearchStatsReceiver searchStatsReceiver,
      SearchDecider searchDecider,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler,
      Clock clock) {
    super(
        etwitterecutorServiceFactory,
        THREAD_NAME_PATTERN,
        THREAD_IS_DAEMON,
        PeriodicActionParams.withFitwitteredDelay(
          updatePeriodMillis,
          TimeUnit.MILLISECONDS
        ),
        new ShutdownWaitTimeParams(
          EtwitterECUTOR_SHUTDOWN_WAIT_SEC,
          TimeUnit.SECONDS
        ),
        searchStatsReceiver,
        criticalEtwitterceptionHandler);
    this.indetwitterConfig = indetwitterConfig;
    this.partitionConfig = partitionConfig;
    this.schemaLocationOnLocal = schemaLocationOnLocal;
    this.schemaLocationOnZK = schemaLocationOnZK;
    this.zkClient = zkClient;
    this.schedulerClient = schedulerClient;
    this.scoringModelsManager = scoringModelsManager;
    this.searchDecider = searchDecider;
    this.noShutdownWhenNotInLayout = new AtomicLong(0);
    this.tensorflowModelsManager = tensorflowModelsManager;
    this.clock = clock;
    this.schemaMajorVersion = getSearchStatsReceiver().getLongGauge(
        SCHEMA_MAJOR_VERSION_NAME);
    this.schemaMinorVersion = getSearchStatsReceiver().getLongGauge(
        SCHEMA_MINOR_VERSION_NAME);
    this.lastSuccessfulSchemaReloadTimeMillis = getSearchStatsReceiver().getLongGauge(
        LAST_SUCCESSFUL_SCHEMA_RELOAD_TIME_MILLIS_NAME);
    this.failToLoadSchemaCount = getSearchStatsReceiver().getCounter(
        FAIL_TO_LOAD_SCHEMA_COUNT_NAME);
    this.hostIsCanarySchema = getSearchStatsReceiver().getLongGauge(HOST_IS_CANARY_SCHEME);
    this.didNotFindSchemaCount = getSearchStatsReceiver().getCounter(
        DID_NOT_FIND_SCHEMA_COUNT_NAME);
    this.lastSuccessfulPartitionConfigReloadTimeMillis = getSearchStatsReceiver().getLongGauge(
        LAST_SUCCESSFUL_PARTITION_CONFIG_RELOAD_TIME_MILLIS_NAME);
    this.failToLoadPartitionConfigCount = getSearchStatsReceiver().getCounter(
        FAIL_TO_LOAD_PARTITION_CONFIG_COUNT_NAME);
    this.hostIsInLayout = getSearchStatsReceiver().getLongGauge(
        HOST_IS_IN_LAYOUT_STAT_NAME);
    this.notInLayoutShutDownAttemptedCount = getSearchStatsReceiver().getCounter(
        NOT_IN_LAYOUT_SHUT_DOWN_ATTEMPTED_NAME);
    this.noShutdownWhenNotInLayoutGauge = getSearchStatsReceiver().getLongGauge(
        NO_SHUTDOWN_WHEN_NOT_IN_LAYOUT_NAME, noShutdownWhenNotInLayout);

    updateSchemaVersionStats(indetwitterConfig.getSchema());
  }

  private void updateSchemaVersionStats(Schema schema) {
    schemaMajorVersion.set(schema.getMajorVersionNumber());
    schemaMinorVersion.set(schema.getMinorVersionNumber());
    lastSuccessfulSchemaReloadTimeMillis.set(System.currentTimeMillis());
    lastSuccessfulPartitionConfigReloadTimeMillis.set(System.currentTimeMillis());
    hostIsInLayout.set(1);
  }

  private void updateSchemaVersionWithThriftSchema(ThriftSchema thriftSchema)
      throws Schema.SchemaValidationEtwitterception, DynamicSchema.SchemaUpdateEtwitterception {

      ImmutableSchema newSchema = new ImmutableSchema(
          thriftSchema, new AnalyzerFactory(), indetwitterConfig.getCluster().getNameForStats());
      indetwitterConfig.getSchema().updateSchema(newSchema);
      tensorflowModelsManager.updateFeatureSchemaIdToMlIdMap(newSchema.getSearchFeatureSchema());
      updateSchemaVersionStats(indetwitterConfig.getSchema());
      LOG.info("Schema updated. New Schema is: \n" + ThriftUtils.toTetwittertFormatSafe(thriftSchema));
  }

  protected void updateSchema(ZooKeeperProtwittery zkClientToUse) {
    // There are 3 cases:
    // 1. Try to locate local schema file to canary, it might fail either because file not etwitterist or
    // ineligible versions.
    // 2. Canary local schema failed, lookup schema file from zookeeper.
    // 3. Both local and zookeeper updates failed, we do not update schema. Either schema not etwitterists
    // in zookeeper, or this would happened after canary schema: we updated current schema but did
    // not rollback after finished.
    if (updateSchemaFromLocal()) {
      LOG.info("Host is used for schema canary");
      hostIsCanarySchema.set(1);
    } else if (updateSchemaFromZooKeeper(zkClientToUse)) {
      // Host is using schema file from zookeeper
      hostIsCanarySchema.set(0);
    } else {
      // Schema update failed. Please check schema file etwitterists on zookeeper and make sure
      // rollback after canary. Current version: {}.{}
      return;
    }
  }

  private boolean updateSchemaFromLocal() {
    ThriftSchema thriftSchema =
        loadCanaryThriftSchemaFromLocal(getCanarySchemaFileOnLocal());
    if (thriftSchema == null) {
      // It is etwitterpected to not find a local schema file. The schema file only etwitterists when the host
      // is used as canary for schema updates
      return false;
    }
    return updateSchemaFromThriftSchema(thriftSchema);
  }

  private boolean updateSchemaFromZooKeeper(ZooKeeperProtwittery zkClientToUse) {
    ThriftSchema thriftSchema = loadThriftSchemaFromZooKeeper(zkClientToUse);
    if (thriftSchema == null) {
      // It is etwitterpected to usually not find a schema file on ZooKeeper; one is only uploaded if the
      // schema changes after the package has been compiled. All the relevant error handling and
      // logging is etwitterpected to be handled by loadThriftSchemaFromZooKeeper().
      failToLoadSchemaCount.increment();
      return false;
    }
    return updateSchemaFromThriftSchema(thriftSchema);
  }

  private boolean updateSchemaFromThriftSchema(ThriftSchema thriftSchema) {
    Schema currentSchema = indetwitterConfig.getSchema();
    if (thriftSchema.getMajorVersionNumber() != currentSchema.getMajorVersionNumber()) {
      LOG.warn(
          "Major version updates are not allowed. Current major version {}, try to update to {}",
          currentSchema.getMajorVersionNumber(), thriftSchema.getMajorVersionNumber());
      return false;
    }
    if (thriftSchema.getMinorVersionNumber() > currentSchema.getMinorVersionNumber()) {
      try {
        updateSchemaVersionWithThriftSchema(thriftSchema);
      } catch (Schema.SchemaValidationEtwitterception | DynamicSchema.SchemaUpdateEtwitterception e) {
        LOG.warn("Etwitterception while updating schema: ", e);
        return false;
      }
      return true;
    } else if (thriftSchema.getMinorVersionNumber() == currentSchema.getMinorVersionNumber()) {
      LOG.info("Schema version to update is same as current one: {}.{}",
          currentSchema.getMajorVersionNumber(), currentSchema.getMinorVersionNumber());
      return true;
    } else {
      LOG.info("Found schema to update, but not eligible for dynamic update. "
              + "Current Version: {}.{};  Schema Version for updates: {}.{}",
          currentSchema.getMajorVersionNumber(),
          currentSchema.getMinorVersionNumber(),
          thriftSchema.getMajorVersionNumber(),
          thriftSchema.getMinorVersionNumber());
      return false;
    }
  }

  void updatePartitionConfig(@Nullable AuroraSchedulerClient schedulerClientToUse) {
    try {
      if (schedulerClientToUse == null) {
        NonPagingAssert.assertFailed("aurora_scheduler_client_is_null");
        throw new PartitionConfigLoadingEtwitterception("AuroraSchedulerClient can not be null.");
      }

      PartitionConfig newPartitionConfig =
          PartitionConfigLoader.getPartitionInfoForMesosConfig(schedulerClientToUse);
      partitionConfig.setCurrentPartitionConfig(newPartitionConfig);
      lastSuccessfulPartitionConfigReloadTimeMillis.set(System.currentTimeMillis());
      hostIsInLayout.set(1);
    } catch (PartitionConfigLoadingEtwitterception e) {
      // Do not change hostIsInLayout's value if we could not load the layout.
      LOG.warn("Failed to load partition config from ZooKeeper.", e);
      failToLoadPartitionConfigCount.increment();
    }
  }

  @Nullable
  private ThriftSchema loadCanaryThriftSchemaFromLocal(LocalFile schemaFile) {
    String schemaString;
    if (!schemaFile.getFile().etwitterists()) {
      return null;
    }
    try {
      schemaString = schemaFile.getCharSource().read();
    } catch (IOEtwitterception e) {
      LOG.warn("Fail to read from local schema file.");
      return null;
    }
    ThriftSchema thriftSchema = new ThriftSchema();
    try {
      ThriftUtils.fromTetwittertFormat(schemaString, thriftSchema);
      return thriftSchema;
    } catch (TEtwitterception e) {
      LOG.warn("Unable to deserialize ThriftSchema loaded locally from {}.\n{}",
          schemaFile.getName(), e);
      return null;
    }
  }

  @Nullable
  private ThriftSchema loadThriftSchemaFromZooKeeper(ZooKeeperProtwittery zkClientToUse) {
    String schemaPathOnZk = getFullSchemaPathOnZK();
    byte[] rawBytes;
    try {
      rawBytes = zkClientToUse.getData(schemaPathOnZk, false, null);
    } catch (KeeperEtwitterception.NoNodeEtwitterception e) {
      didNotFindSchemaCount.increment();
      return null;
    } catch (KeeperEtwitterception e) {
      LOG.warn("Etwitterception while loading schema from ZK at {}.\n{}", schemaPathOnZk, e);
      return null;
    } catch (InterruptedEtwitterception e) {
      Thread.currentThread().interrupt();
      LOG.warn("Interrupted while loading schema from ZK at {}.\n{}", schemaPathOnZk, e);
      return null;
    } catch (ZooKeeperClient.ZooKeeperConnectionEtwitterception e) {
      LOG.warn("Etwitterception while loading schema from ZK at {}.\n{}", schemaPathOnZk, e);
      return null;
    }
    if (rawBytes == null) {
      LOG.warn("Got null schema from ZooKeeper at {}.", schemaPathOnZk);
      return null;
    }
    String schemaString = new String(rawBytes, Charsets.UTF_8);
    ThriftSchema thriftSchema = new ThriftSchema();
    try {
      ThriftUtils.fromTetwittertFormat(schemaString, thriftSchema);
      return thriftSchema;
    } catch (TEtwitterception e) {
      LOG.warn("Unable to deserialize ThriftSchema loaded from ZK at {}.\n{}", schemaPathOnZk, e);
      return null;
    }
  }

  @VisibleForTesting
  protected String getSchemaFileName() {
    return indetwitterConfig.getCluster().name().toLowerCase()
        + UpdateableEarlybirdStateManager.SCHEMA_SUFFItwitter
        + indetwitterConfig.getSchema().getMajorVersionNumber();
  }

  @VisibleForTesting
  protected String getFullSchemaPathOnZK() {
    return String.format("%s/%s", schemaLocationOnZK, getSchemaFileName());
  }

  LocalFile getCanarySchemaFileOnLocal() {
    String canarySchemaFilePath =
        String.format("%s/%s", schemaLocationOnLocal, getSchemaFileName());
    return new LocalFile(new File(canarySchemaFilePath));
  }

  void setNoShutdownWhenNotInLayout(boolean noShutdown) {
    noShutdownWhenNotInLayout.set(noShutdown ? 1 : 0);
  }

  @Override
  protected void runOneIteration() {
    updateSchema(zkClient);
    updatePartitionConfig(schedulerClient);

    LOG.info("Reloading models.");
    scoringModelsManager.reload();
    tensorflowModelsManager.run();

    Random random = new Random();

    try {
      // We had an issue where HDFS operations were blocking, so reloading these models
      // was finishing at the same time on each instance and after that every time an instance
      // was reloading models, it was happening at the same time. This caused issues with HDFS
      // load. We now place a "guard" waiting time after each reload so that the etwitterecution time
      // on every instance is different and these calls can't easily sync to the same point in time.
      int sleepSeconds = random.netwittertInt(30 * 60);
      LOG.info("Sleeping for {} seconds", sleepSeconds);
      clock.waitFor(sleepSeconds * 1000);
    } catch (InterruptedEtwitterception etwitter) {
      LOG.info("Interrupted while sleeping");
    }
  }

  public void setEarlybirdServer(EarlybirdServer earlybirdServer) {
    this.earlybirdServer = earlybirdServer;
  }
}
