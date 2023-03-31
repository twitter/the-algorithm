package com.twitter.search.earlybird_root;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;

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
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;
import com.twitter.search.earlybird_root.filters.RequestContextToEarlybirdRequestFilter;

public abstract class ScatterGatherModule extends TwitterModule {
  private static final Logger LOG = LoggerFactory.getLogger(ScatterGatherModule.class);

  private static final String SEARCH_METHOD_NAME = "search";
  protected static final String ALT_TRAFFIC_PERCENTAGE_DECIDER_KEY_PREFIX =
      "alt_client_traffic_percentage_";
  static final String NAMED_SCATTER_GATHER_SERVICE = "scatter_gather_service";

  /**
   * Provides the scatterGatherService for single tier Earlybird clusters (Protected and Realtime).
   */
  public abstract Service<EarlybirdRequestContext, EarlybirdResponse> provideScatterGatherService(
      EarlybirdServiceScatterGatherSupport scatterGatherSupport,
      RequestSuccessStats requestSuccessStats,
      PartitionLoggingSupport<EarlybirdRequestContext> partitionLoggingSupport,
      RequestContextToEarlybirdRequestFilter requestContextToEarlybirdRequestFilter,
      PartitionAccessController partitionAccessController,
      PartitionConfig partitionConfig,
      RootClientServiceBuilder<EarlybirdService.ServiceIface> rootClientServiceBuilder,
      @Named(EarlybirdCommonModule.NAMED_EXP_CLUSTER_CLIENT)
          RootClientServiceBuilder<EarlybirdService.ServiceIface>
          expClusterRootClientServiceBuilder,
      @Named(EarlybirdCommonModule.NAMED_ALT_CLIENT) @Nullable PartitionConfig altPartitionConfig,
      @Named(EarlybirdCommonModule.NAMED_ALT_CLIENT) @Nullable
          RootClientServiceBuilder<EarlybirdService.ServiceIface> altRootClientServiceBuilder,
      StatsReceiver statsReceiver,
      EarlybirdCluster cluster,
      SearchDecider decider);

  protected final Service<EarlybirdRequestContext, EarlybirdResponse> buildScatterOrSplitterService(
      EarlybirdServiceScatterGatherSupport scatterGatherSupport,
      RequestSuccessStats requestSuccessStats,
      PartitionLoggingSupport<EarlybirdRequestContext> partitionLoggingSupport,
      RequestContextToEarlybirdRequestFilter requestContextToEarlybirdRequestFilter,
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
    ScatterGatherService<EarlybirdRequestContext, EarlybirdResponse> scatterGatherService =
        createScatterGatherService(
            "",
            scatterGatherSupport,
            requestSuccessStats,
            partitionLoggingSupport,
            requestContextToEarlybirdRequestFilter,
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

    ScatterGatherService<EarlybirdRequestContext, EarlybirdResponse> altScatterGatherService =
        createScatterGatherService(
            "_alt",
            scatterGatherSupport,
            requestSuccessStats,
            partitionLoggingSupport,
            requestContextToEarlybirdRequestFilter,
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
        ALT_TRAFFIC_PERCENTAGE_DECIDER_KEY_PREFIX + cluster.getNameForStats());
  }

  protected ScatterGatherService<EarlybirdRequestContext, EarlybirdResponse>
      createScatterGatherService(
          String nameSuffix,
          EarlybirdServiceScatterGatherSupport scatterGatherSupport,
          RequestSuccessStats requestSuccessStats,
          PartitionLoggingSupport<EarlybirdRequestContext> partitionLoggingSupport,
          RequestContextToEarlybirdRequestFilter requestContextToEarlybirdRequestFilter,
          PartitionAccessController partitionAccessController,
          int numPartitions,
          String partitionPath,
          RootClientServiceBuilder<EarlybirdService.ServiceIface> rootClientServiceBuilder,
          StatsReceiver statsReceiver,
          EarlybirdCluster cluster,
          SearchDecider decider,
          String clientName) {
    rootClientServiceBuilder.initializeWithPathSuffix(clientName + nameSuffix,
        numPartitions,
        partitionPath);

    ClientBackupFilter backupFilter =
        new ClientBackupFilter(
            "root_" + cluster.getNameForStats(),
            "earlybird" + nameSuffix,
            statsReceiver,
            decider);

    ClientLatencyFilter clientLatencyFilter = new ClientLatencyFilter("all" + nameSuffix);

    List<Service<EarlybirdRequestContext, EarlybirdResponse>> services = new ArrayList<>();
    for (Service<EarlybirdRequest, EarlybirdResponse> service
        : rootClientServiceBuilder
        .<EarlybirdRequest, EarlybirdResponse>safeBuildServiceList(SEARCH_METHOD_NAME)) {
      services.add(requestContextToEarlybirdRequestFilter
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
