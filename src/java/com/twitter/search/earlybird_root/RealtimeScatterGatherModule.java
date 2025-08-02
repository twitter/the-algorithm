package com.twitter.search.earlybird_root;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
import com.twitter.search.common.root.ScatterGatherService;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdService;
import com.twitter.search.earlybird.thrift.EtwitterperimentCluster;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.earlybird_root.filters.RequestContetwittertToEarlybirdRequestFilter;
import com.twitter.search.earlybird_root.filters.ScatterGatherWithEtwitterperimentRedirectsService;

public class RealtimeScatterGatherModule etwittertends ScatterGatherModule {
  private static final Logger LOG =
      LoggerFactory.getLogger(RealtimeScatterGatherModule.class);

  /**
   * Provides a scatter gather service for the realtime cluster that redirects to etwitterperimental
   * clusters when the etwitterperiment cluster parameter is set on the EarlybirdRequest.
   *
   * Note: if an alternate client is specified via altPartitionConfig or
   * altRootClientServiceBuilder, it will be built and used for the "control" cluster, but the
   * etwitterperiment cluster takes precedence (if the etwitterperiment cluster is set on the request, the
   * alternate client will never be used.
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


    Service<EarlybirdRequestContetwittert, EarlybirdResponse> controlService =
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

    Map<EtwitterperimentCluster, ScatterGatherService<EarlybirdRequestContetwittert, EarlybirdResponse>>
        etwitterperimentScatterGatherServices = new HashMap<>();

    LOG.info("Using ScatterGatherWithEtwitterperimentRedirectsService");
    LOG.info("Control Partition Path: {}", partitionConfig.getPartitionPath());

    Arrays.stream(EtwitterperimentCluster.values())
        .filter(v -> v.name().toLowerCase().startsWith("etwitterp"))
        .forEach(etwitterperimentCluster -> {
          String etwitterpPartitionPath = partitionConfig.getPartitionPath()
              + "-" + etwitterperimentCluster.name().toLowerCase();

          LOG.info("Etwitterperiment Partition Path: {}", etwitterpPartitionPath);

          etwitterperimentScatterGatherServices.put(etwitterperimentCluster,
              createScatterGatherService(
                  "",
                  scatterGatherSupport,
                  requestSuccessStats,
                  partitionLoggingSupport,
                  requestContetwittertToEarlybirdRequestFilter,
                  partitionAccessController,
                  partitionConfig.getNumPartitions(),
                  etwitterpPartitionPath,
                  etwitterpClusterRootClientServiceBuilder,
                  statsReceiver,
                  cluster,
                  decider,
                  etwitterperimentCluster.name().toLowerCase()));
        });

    return new ScatterGatherWithEtwitterperimentRedirectsService(
        controlService,
        etwitterperimentScatterGatherServices);
  }
}
