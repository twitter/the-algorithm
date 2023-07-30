package com.X.search.earlybird_root;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import com.google.inject.Provides;

import com.X.finagle.Service;
import com.X.finagle.stats.StatsReceiver;
import com.X.search.common.decider.SearchDecider;
import com.X.search.common.root.PartitionConfig;
import com.X.search.common.root.PartitionLoggingSupport;
import com.X.search.common.root.RequestSuccessStats;
import com.X.search.common.root.RootClientServiceBuilder;
import com.X.search.common.schema.earlybird.EarlybirdCluster;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird.thrift.EarlybirdService;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;
import com.X.search.earlybird_root.filters.RequestContextToEarlybirdRequestFilter;

public class ProtectedScatterGatherModule extends ScatterGatherModule {
  /**
   * Provides the scatterGatherService for the protected cluster.
   */
  @Provides
  @Singleton
  @Named(NAMED_SCATTER_GATHER_SERVICE)
  @Override
  public Service<EarlybirdRequestContext, EarlybirdResponse> provideScatterGatherService(
      EarlybirdServiceScatterGatherSupport scatterGatherSupport,
      RequestSuccessStats requestSuccessStats,
      PartitionLoggingSupport<EarlybirdRequestContext> partitionLoggingSupport,
      RequestContextToEarlybirdRequestFilter requestContextToEarlybirdRequestFilter,
      PartitionAccessController partitionAccessController,
      PartitionConfig partitionConfig,
      RootClientServiceBuilder<EarlybirdService.ServiceIface> rootClientServiceBuilder,
      @Named(EarlybirdCommonModule.NAMED_EXP_CLUSTER_CLIENT)
          RootClientServiceBuilder<EarlybirdService.ServiceIface>
          expClusterRootClientServiceBuilder, // unused in protected roots
      @Named(EarlybirdCommonModule.NAMED_ALT_CLIENT) @Nullable PartitionConfig altPartitionConfig,
      @Named(EarlybirdCommonModule.NAMED_ALT_CLIENT) @Nullable
          RootClientServiceBuilder<EarlybirdService.ServiceIface> altRootClientServiceBuilder,
      StatsReceiver statsReceiver,
      EarlybirdCluster cluster,
      SearchDecider decider) {
    return buildScatterOrSplitterService(
        scatterGatherSupport,
        requestSuccessStats,
        partitionLoggingSupport,
        requestContextToEarlybirdRequestFilter,
        partitionAccessController,
        partitionConfig,
        rootClientServiceBuilder,
        altPartitionConfig,
        altRootClientServiceBuilder,
        statsReceiver,
        cluster,
        decider
    );
  }
}
