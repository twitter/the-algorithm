package com.twitter.search.earlybird_root;

import java.util.List;

import javatwitter.inject.Inject;

import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.finagle.Service;
import com.twitter.search.common.root.PartitionLoggingSupport;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.util.Future;

/**
 * A chain of scatter gather services.
 * Regular roots use ScatterGatherService directly. This class is only used by multi-tier roots.
 */
public class EarlybirdChainedScatterGatherService etwittertends
    Service<EarlybirdRequestContetwittert, List<Future<EarlybirdResponse>>> {

  private static final Logger LOG =
    LoggerFactory.getLogger(EarlybirdChainedScatterGatherService.class);

  private final List<Service<EarlybirdRequestContetwittert, EarlybirdResponse>> serviceChain;

  /**
   * Construct a ScatterGatherServiceChain, by loading configurations from earlybird-tiers.yml.
   */
  @Inject
  public EarlybirdChainedScatterGatherService(
      EarlybirdServiceChainBuilder serviceChainBuilder,
      EarlybirdServiceScatterGatherSupport scatterGatherSupport,
      PartitionLoggingSupport<EarlybirdRequestContetwittert> partitionLoggingSupport) {

    serviceChain =
        serviceChainBuilder.buildServiceChain(scatterGatherSupport, partitionLoggingSupport);

    if (serviceChain.isEmpty()) {
      LOG.error("At least one tier has to be enabled.");
      throw new RuntimeEtwitterception("Root does not work with all tiers disabled.");
    }
  }

  @Override
  public Future<List<Future<EarlybirdResponse>>> apply(EarlybirdRequestContetwittert requestContetwittert) {
    // Hit all tiers in parallel.
    List<Future<EarlybirdResponse>> resultList =
        Lists.newArrayListWithCapacity(serviceChain.size());
    for (final Service<EarlybirdRequestContetwittert, EarlybirdResponse> service : serviceChain) {
      resultList.add(service.apply(requestContetwittert));
    }
    return Future.value(resultList);
  }
}
