package com.twitter.search.earlybird_root;

import java.util.ArrayList;
import java.util.List;

import javatwitter.annotation.Nullable;
import javatwitter.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.finagle.Service;
import com.twitter.finagle.stats.StatsReceiver;
import com.twitter.inject.TwitterModule;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.root.PartitionConfig;
import com.twitter.search.common.root.PartitionLoggingSupport;
import com.twitter.search.common.root.RequestSuccessStats;
import com.twitter.search.common.root.RootClientServiceBuilder;
import com.twitter.search.common.root.ScatterGatherService;
import com.twitter.search.common.root.SplitterService;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.config.TierConfig;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdService;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.earlybird_root.filters.RequestContetwittertToEarlybirdRequestFilter;

public abstract class ScatterGatherModule etwittertends TwitterModule {
  private static final Logger LOG = LoggerFactory.getLogger(ScatterGatherModule.class);

  private static final String SEARCH_METHOD_NAME = "search";
  protected static final String ALT_TRAFFIC_PERCENTAGE_DECIDER_KEY_PREFItwitter =
      "alt_client_traffic_percentage_";
  static final String NAMED_SCATTER_GATHER_SERVICE = "scatter_gather_service";

  /**
   * Provides the scatterGatherService for single tier Earlybird clusters (Protected and Realtime).
   */
  public abstract Service<EarlybirdRequestContetwittert, EarlybirdResponse> provideScatterGatherService(
      EarlybirdServiceScatterGatherSupport scatterGatherSupport,
      RequestSuccessStats requestSuccessStats,
      PartitionLoggingSupport<EarlybirdRequestContetwittert> partitionLoggingSupport,
      RequestContetwittertToEarlybirdRequestFilter requestContetwittertToEarlybirdRequestFilter,
      PartitionAccessController partitionAccessController,
      PartitionConfig partitionConfig,
      RootClientServiceBuilder<EarlybirdService.ServiceIface> rootClientServiceBuilder,
      @Named(EarlybirdCommonModule.NAMED_EtwitterP_CLUSTER_CLIENT)
          RootClientServiceBuilder<EarlybirdService.ServiceIface>
          etwitterpClusterRootClientServiceBuilder,
      @Named(EarlybirdCommonModule.NAMED_ALT_CLIENT) @Nullable PartitionConfig altPartitionConfig,
      @Named(EarlybirdCommonModule.NAMED_ALT_CLIENT) @Nullable
          RootClientServiceBuilder<EarlybirdService.ServiceIface> altRootClientServiceBuilder,
      StatsReceiver statsReceiver,
      EarlybirdCluster cluster,
      SearchDecider decider);

  protected final Service<EarlybirdRequestContetwittert, EarlybirdResponse> buildScatterOrSplitterService(
      EarlybirdServiceScatterGatherSupport scatterGatherSupport,
      RequestSuccessStats requestSuccessStats,
      PartitionLoggingSupport<EarlybirdRequestContetwittert> partitionLoggingSupport,
      RequestContetwittertToEarlybirdRequestFilter requestContetwittertToEarlybirdRequestFilter,
      PartitionAccessController partitionAccessController,
      PartitionConfig partitionConfig,
      RootClientServiceBuilder<EarlybirdService.ServiceIface> rootClientServiceBuilder,
      @Named(EarlybirdCommonModule.NAMED_ALT_CLIENT) @Nullable PartitionConfig altPartitionConfig,
      @Named(EarlybirdCommonModule.NAMED_ALT_CLIENT) @Nullable
          RootClientServiceBuilder<EarlybirdService.ServiceIface> altRootClientServiceBuilder,
      StatsReceiver statsReceiver,
      EarlybirdCluster cluster,
      SearchDecider decider
  ) {
    ScatterGatherService<EarlybirdRequestContetwittert, EarlybirdResponse> scatterGatherService =
        createScatterGatherService(
            "",
            scatterGatherSupport,
            requestSuccessStats,
            partitionLoggingSupport,
            requestContetwittertToEarlybirdRequestFilter,
            partitionAccessController,
            partitionConfig.getNumPartitions(),
            partitionConfig.getPartitionPath(),
            rootClientServiceBuilder,
            statsReceiver,
            cluster,
            decider,
            TierConfig.DEFAULT_TIER_NAME);

    if (altPartitionConfig == null || altRootClientServiceBuilder == null) {
      LOG.info("altPartitionConfig or altRootClientServiceBuilder is not available, "
          + "not using SplitterService");
      return scatterGatherService;
    }

    LOG.info("alt client config available, using SplitterService");

    ScatterGatherService<EarlybirdRequestContetwittert, EarlybirdResponse> altScatterGatherService =
        createScatterGatherService(
            "_alt",
            scatterGatherSupport,
            requestSuccessStats,
            partitionLoggingSupport,
            requestContetwittertToEarlybirdRequestFilter,
            partitionAccessController,
            altPartitionConfig.getNumPartitions(),
            altPartitionConfig.getPartitionPath(),
            altRootClientServiceBuilder,
            statsReceiver,
            cluster,
            decider,
            TierConfig.DEFAULT_TIER_NAME);

    return new SplitterService<>(
        scatterGatherService,
        altScatterGatherService,
        decider,
        ALT_TRAFFIC_PERCENTAGE_DECIDER_KEY_PREFItwitter + cluster.getNameForStats());
  }

  protected ScatterGatherService<EarlybirdRequestContetwittert, EarlybirdResponse>
      createScatterGatherService(
          String nameSuffitwitter,
          EarlybirdServiceScatterGatherSupport scatterGatherSupport,
          RequestSuccessStats requestSuccessStats,
          PartitionLoggingSupport<EarlybirdRequestContetwittert> partitionLoggingSupport,
          RequestContetwittertToEarlybirdRequestFilter requestContetwittertToEarlybirdRequestFilter,
          PartitionAccessController partitionAccessController,
          int numPartitions,
          String partitionPath,
          RootClientServiceBuilder<EarlybirdService.ServiceIface> rootClientServiceBuilder,
          StatsReceiver statsReceiver,
          EarlybirdCluster cluster,
          SearchDecider decider,
          String clientName) {
    rootClientServiceBuilder.initializeWithPathSuffitwitter(clientName + nameSuffitwitter,
        numPartitions,
        partitionPath);

    ClientBackupFilter backupFilter =
        new ClientBackupFilter(
            "root_" + cluster.getNameForStats(),
            "earlybird" + nameSuffitwitter,
            statsReceiver,
            decider);

    ClientLatencyFilter clientLatencyFilter = new ClientLatencyFilter("all" + nameSuffitwitter);

    List<Service<EarlybirdRequestContetwittert, EarlybirdResponse>> services = new ArrayList<>();
    for (Service<EarlybirdRequest, EarlybirdResponse> service
        : rootClientServiceBuilder
        .<EarlybirdRequest, EarlybirdResponse>safeBuildServiceList(SEARCH_METHOD_NAME)) {
      services.add(requestContetwittertToEarlybirdRequestFilter
          .andThen(backupFilter)
          .andThen(clientLatencyFilter)
          .andThen(service));
    }
    services = SkipPartitionFilter.wrapServices(TierConfig.DEFAULT_TIER_NAME, services,
        partitionAccessController);

    return new ScatterGatherService<>(
        scatterGatherSupport,
        services,
        requestSuccessStats,
        partitionLoggingSupport);
  }
}
