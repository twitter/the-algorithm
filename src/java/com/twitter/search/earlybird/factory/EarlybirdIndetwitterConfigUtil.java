package com.twitter.search.earlybird.factory;

import com.twitter.decider.Decider;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.EarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.RealtimeEarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.archive.ArchiveOnDiskEarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.archive.ArchiveSearchPartitionManager;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.partition.SearchIndetwitteringMetricSet;

public final class EarlybirdIndetwitterConfigUtil {
  private EarlybirdIndetwitterConfigUtil() {
  }

  /**
   * Creates the indetwitter config for this earlybird.
   */
  public static EarlybirdIndetwitterConfig createEarlybirdIndetwitterConfig(
      Decider decider, SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    if (isArchiveSearch()) {
      return new ArchiveOnDiskEarlybirdIndetwitterConfig(decider, searchIndetwitteringMetricSet,
          criticalEtwitterceptionHandler);
    } else if (isProtectedSearch()) {
      return new RealtimeEarlybirdIndetwitterConfig(
          EarlybirdCluster.PROTECTED, decider, searchIndetwitteringMetricSet, criticalEtwitterceptionHandler);
    } else if (isRealtimeCG()) {
      return new RealtimeEarlybirdIndetwitterConfig(
          EarlybirdCluster.REALTIME_CG, decider, searchIndetwitteringMetricSet, criticalEtwitterceptionHandler);
    } else {
      return new RealtimeEarlybirdIndetwitterConfig(
          EarlybirdCluster.REALTIME, decider, searchIndetwitteringMetricSet, criticalEtwitterceptionHandler);
    }
  }

  public static boolean isArchiveSearch() {
    // Re-reading config on each call so that tests can reliably overwrite this
    return EarlybirdConfig.getString("partition_manager", "realtime")
        .equals(ArchiveSearchPartitionManager.CONFIG_NAME);
  }

  private static boolean isProtectedSearch() {
    // Re-reading config on each call so that tests can reliably overwrite this
    return EarlybirdConfig.getBool("protected_indetwitter", false);
  }

  private static boolean isRealtimeCG() {
    // Re-reading config on each call so that tests can reliably overwrite this
    return EarlybirdConfig.getBool("realtime_cg_indetwitter", false);
  }
}
