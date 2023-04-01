package com.twitter.search.earlybird.partition;

import java.io.IOException;

import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.aurora.AuroraInstanceKey;
import com.twitter.search.common.aurora.AuroraSchedulerClient;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.factory.PartitionConfigUtil;

public final class PartitionConfigLoader {
  private static final Logger LOG = LoggerFactory.getLogger(PartitionConfigLoader.class);

  private PartitionConfigLoader() {
    // this never gets called
  }

  /**
   * Load partition information from the command line arguments and Aurora scheduler.
   *
   * @return The new PartitionConfig object for this host
   */
  public static PartitionConfig getPartitionInfoForMesosConfig(
      AuroraSchedulerClient schedulerClient) throws PartitionConfigLoadingException {
    AuroraInstanceKey instanceKey =
        Preconditions.checkNotNull(EarlybirdConfig.getAuroraInstanceKey());
    int numTasks;

    try {
      numTasks = schedulerClient.getActiveTasks(
          instanceKey.getRole(), instanceKey.getEnv(), instanceKey.getJobName()).size();
      LOG.info("Found {} active tasks", numTasks);
    } catch (IOException e) {
      // This can happen when Aurora Scheduler is holding a conclave to elect a new reader.
      LOG.warn("Failed to get tasks from Aurora scheduler.", e);
      throw new PartitionConfigLoadingException("Failed to get tasks from Aurora scheduler.");
    }

    return PartitionConfigUtil.initPartitionConfigForAurora(numTasks);
  }
}
