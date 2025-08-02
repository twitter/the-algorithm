package com.twitter.search.earlybird_root;

import javatwitter.annotation.Nullable;
import javatwitter.inject.Named;
import javatwitter.inject.Singleton;

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
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.earlybird_root.filters.RequestContetwittertToEarlybirdRequestFilter;

public class RealtimeCgScatterGatherModule etwittertends ScatterGatherModule {
  private static final Logger LOG =
      LoggerFactory.getLogger(RealtimeCgScatterGatherModule.class);

  /**
   * Provides a scatter gather service for the realtime_cg cluster.
   */
  @Provides
  @Singleton
  @Named(NAMED_SCATTER_GATHER_SERVICE)
  @Override
  public Service<EarlybirdRequestContetwittert, EarlybirdResponse> provideScatterGatherService(
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
      SearchDecider decider) {


    return
        buildScatterOrSplitterService(
            scatterGatherSupport,
            requestSuccessStats,
            partitionLoggingSupport,
            requestContetwittertToEarlybirdRequestFilter,
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
