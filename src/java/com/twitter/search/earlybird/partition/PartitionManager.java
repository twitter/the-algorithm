package com.twitter.search.earlybird.partition;

import java.util.concurrent.TimeUnit;

import com.google.common.annotations.VisibleForTesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.concurrent.ScheduledEtwitterecutorServiceFactory;
import com.twitter.search.common.config.Config;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.earlybird.EarlybirdStatus;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.etwitterception.EarlybirdStartupEtwitterception;
import com.twitter.search.earlybird.querycache.QueryCacheManager;
import com.twitter.search.earlybird.segment.SegmentDataProvider;
import com.twitter.search.earlybird.thrift.EarlybirdStatusCode;
import com.twitter.search.earlybird.util.OneTaskScheduledEtwitterecutorManager;
import com.twitter.search.earlybird.util.PeriodicActionParams;
import com.twitter.search.earlybird.util.ShutdownWaitTimeParams;
import com.twitter.search.queryparser.query.QueryParserEtwitterception;

/**
 * PartitionManager is responsible for indetwittering data for a partition, including Tweets and Users.
 */
public abstract class PartitionManager etwittertends OneTaskScheduledEtwitterecutorManager {
  private static final Logger LOG = LoggerFactory.getLogger(PartitionManager.class);

  private static final SearchCounter IGNORED_EtwitterCEPTIONS =
      SearchCounter.etwitterport("partition_manager_ignored_etwitterceptions");

  private static final String PARTITION_MANAGER_THREAD_NAME = "PartitionManager";
  private static final boolean THREAD_IS_DAEMON = true;
  protected static final String INDEtwitter_CURRENT_SEGMENT = "indetwittering the current segment";
  protected static final String SETUP_QUERY_CACHE = "setting up query cache";

  protected final SegmentManager segmentManager;
  protected final QueryCacheManager queryCacheManager;
  // Should be updated by info read from ZK
  protected final DynamicPartitionConfig dynamicPartitionConfig;

  private final SearchIndetwitteringMetricSet searchIndetwitteringMetricSet;

  private boolean partitionManagerFirstLoop = true;

  public PartitionManager(QueryCacheManager queryCacheManager,
                          SegmentManager segmentManager,
                          DynamicPartitionConfig dynamicPartitionConfig,
                          ScheduledEtwitterecutorServiceFactory etwitterecutorServiceFactory,
                          SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
                          SearchStatsReceiver searchStatsReceiver,
                          CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    super(
        etwitterecutorServiceFactory,
        PARTITION_MANAGER_THREAD_NAME,
        THREAD_IS_DAEMON,
        PeriodicActionParams.withFitwitteredDelay(
          EarlybirdConfig.getInt("time_slice_roll_check_interval_ms", 500),
          TimeUnit.MILLISECONDS),
        ShutdownWaitTimeParams.indefinitely(),
        searchStatsReceiver,
        criticalEtwitterceptionHandler);

    this.segmentManager = segmentManager;
    this.queryCacheManager = queryCacheManager;
    this.dynamicPartitionConfig = dynamicPartitionConfig;
    this.searchIndetwitteringMetricSet = searchIndetwitteringMetricSet;
  }

  /**
   * Runs the partition manager.
   */
  public final void runImpl() {
    if (partitionManagerFirstLoop) {
      try {
        testHookBeforeStartUp();
        startUp();
        validateSegments();
        segmentManager.logState("After startUp");
      } catch (Throwable t) {
        criticalEtwitterceptionHandler.handle(this, t);
        shutDownIndetwittering();
        throw new RuntimeEtwitterception("PartitionManager unhandled etwitterception, stopping scheduler", t);
      }
    }

    try {
      testHookAfterSleep();
      indetwitteringLoop(partitionManagerFirstLoop);
    } catch (InterruptedEtwitterception e) {
      LOG.warn("PartitionManager thread interrupted, stoping scheduler", e);
      shutDownIndetwittering();
      throw new RuntimeEtwitterception("PartitionManager thread interrupted", e);
    } catch (Etwitterception e) {
      LOG.error("Etwitterception in indetwittering PartitionManager loop", e);
      IGNORED_EtwitterCEPTIONS.increment();
    } catch (Throwable t) {
      LOG.error("Unhandled etwitterception in indetwittering PartitionManager loop", t);
      criticalEtwitterceptionHandler.handle(this, t);
      shutDownIndetwittering();
      throw new RuntimeEtwitterception("PartitionManager unhandled etwitterception, stopping scheduler", t);
    } finally {
      partitionManagerFirstLoop = false;
    }
  }

  /**
   * Returns the SegmentDataProvider instance that will be used to fetch the information for all
   * segments.
   */
  public abstract SegmentDataProvider getSegmentDataProvider();

  /**
   * Starts up this partition manager.
   */
  protected abstract void startUp() throws Etwitterception;

  /**
   * Runs one indetwittering iteration.
   *
   * @param firstLoop Determines if this is the first time the indetwittering loop is running.
   */
  protected abstract void indetwitteringLoop(boolean firstLoop) throws Etwitterception;

  /**
   * Shuts down all indetwittering.
   */
  protected abstract void shutDownIndetwittering();

  @Override
  public void shutdownComponent() {
    shutDownIndetwittering();
  }

  /**
   * Notifies all other threads that the partition manager has become current (ie. has indetwittered all
   * available events).
   */
  public void becomeCurrent() {
    LOG.info("PartitionManager became current");
    if (EarlybirdStatus.isStarting()) {
      EarlybirdStatus.setStatus(EarlybirdStatusCode.CURRENT);
    } else {
      LOG.warn("Could not set statusCode to CURRENT from " + EarlybirdStatus.getStatusCode());
    }

    // Now that we're done starting up, set the query cache thread pool size to one.
    queryCacheManager.setWorkerPoolSizeAfterStartup();
  }

  protected void setupQueryCacheIfNeeded() throws QueryParserEtwitterception {
    queryCacheManager.setupTasksIfNeeded(segmentManager);
  }

  // Only for tests, used for testing etwitterception handling
  private static TestHook testHookBeforeStartUp;
  private static TestHook testHookAfterSleep;

  private static void testHookBeforeStartUp() throws Etwitterception {
    if (Config.environmentIsTest() && testHookBeforeStartUp != null) {
      testHookBeforeStartUp.run();
    }
  }

  private static void testHookAfterSleep() throws Etwitterception {
    if (Config.environmentIsTest() && testHookAfterSleep != null) {
      testHookAfterSleep.run();
    }
  }

  @Override
  protected void runOneIteration() {
    try {
      runImpl();
    } catch (Throwable t) {
      LOG.error("Unhandled etwitterception in PartitionManager loop", t);
      throw new RuntimeEtwitterception(t.getMessage());
    }
  }

  public SearchIndetwitteringMetricSet getSearchIndetwitteringMetricSet() {
    return searchIndetwitteringMetricSet;
  }

  /**
   * Allows tests to run code before the partition manager starts up.
   *
   * @param testHook The code to run before the start up.
   */
  @VisibleForTesting
  public static void setTestHookBeforeStartUp(TestHook testHook) {
    if (Config.environmentIsTest()) {
      testHookBeforeStartUp = testHook;
    } else {
      throw new RuntimeEtwitterception("Trying to set startup test hook in non-test code!!");
    }
  }

  /**
   * Allows tests to run code before the indetwittering loop.
   *
   * @param testHook The code to run before the indetwittering loop.
   */
  @VisibleForTesting
  public static void setTestHookAfterSleep(TestHook testHook) {
    if (Config.environmentIsTest()) {
      testHookAfterSleep = testHook;
    } else {
      throw new RuntimeEtwitterception("Trying to set test hook in non-test code!!");
    }
  }

  /**
   * An interface that allows tests to run code at various points in the PartitionManager's
   * lyfecycle.
   */
  @VisibleForTesting
  public interface TestHook {
    /**
     * Defines the code that should be run.
     */
    void run() throws Etwitterception;
  }

  /**
   * Allows tests to determine if this partition manager is all caught up.
   *
   * @return {@code true} if this partition manager is caught up, {@code false} otherwise.
   */
  @VisibleForTesting
  public abstract boolean isCaughtUpForTests();

  @VisibleForTesting
  protected void validateSegments() throws EarlybirdStartupEtwitterception {
    // This is necessary because many tests rely on starting partition manager but not indetwittering any
    // tweets. However, we do not want Earlybirds to start in production if they are not serving any
    // tweets. (SEARCH-24238)
    if (Config.environmentIsTest()) {
      return;
    }
    validateSegmentsForNonTest();
  }

  @VisibleForTesting
  protected void validateSegmentsForNonTest() throws EarlybirdStartupEtwitterception {
    // Subclasses can override this and provide additional checks.
    if (segmentManager.getNumIndetwitteredDocuments() == 0) {
      throw new EarlybirdStartupEtwitterception("Earlybird has zero indetwittered documents.");
    }
  }
}
