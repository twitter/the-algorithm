package com.twitter.search.earlybird_root;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import com.google.inject.Provides;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.finagle.Service;
import com.twitter.finagle.stats.StatsReceiver;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.root.PartitionConfig;
import com.twitter.search.common.root.PartitionLoggingSupport;
import com.twitter.search.common.root.RequestSuccessStats;
import com.twitter.search.common.root.RootClientServiceBuilder;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdService;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;
import com.twitter.search.earlybird_root.filters.RequestContextToEarlybirdRequestFilter;

public class RealtimeCgScatterGatherModule extends ScatterGatherModule {
  private static final Logger LOG =
      LoggerFactory.getLogger(RealtimeCgScatterGatherModule.class);

  /**
   * Provides a scatter gather service for the realtime_cg cluster.
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
          expClusterRootClientServiceBuilder,
      @Named(EarlybirdCommonModule.NAMED_ALT_CLIENT) @Nullable PartitionConfig altPartitionConfig,
      @Named(EarlybirdCommonModule.NAMED_ALT_CLIENT) @Nullable
          RootClientServiceBuilder<EarlybirdService.ServiceIface> altRootClientServiceBuilder,
      StatsReceiver statsReceiver,
      EarlybirdCluster cluster,
      SearchDecider decider) {


    return
        buildScatterOrSplitterService(
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
