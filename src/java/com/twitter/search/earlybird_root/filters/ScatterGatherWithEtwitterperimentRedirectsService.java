package com.twitter.search.earlybird_root.filters;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.finagle.Service;
import com.twitter.search.common.root.ScatterGatherService;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdResponseCode;
import com.twitter.search.earlybird.thrift.EtwitterperimentCluster;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.util.Future;

public class ScatterGatherWithEtwitterperimentRedirectsService
    etwittertends Service<EarlybirdRequestContetwittert, EarlybirdResponse> {
  private final Service<EarlybirdRequestContetwittert, EarlybirdResponse>
      controlScatterGatherService;

  private final Map<EtwitterperimentCluster,
      ScatterGatherService<EarlybirdRequestContetwittert, EarlybirdResponse>>
      etwitterperimentScatterGatherServices;

  private static final Logger LOG =
      LoggerFactory.getLogger(ScatterGatherWithEtwitterperimentRedirectsService.class);

  public ScatterGatherWithEtwitterperimentRedirectsService(
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> controlScatterGatherService,
      Map<EtwitterperimentCluster,
          ScatterGatherService<EarlybirdRequestContetwittert, EarlybirdResponse>>
          etwitterperimentScatterGatherServices
  ) {
    this.controlScatterGatherService = controlScatterGatherService;
    this.etwitterperimentScatterGatherServices = etwitterperimentScatterGatherServices;
  }

  @Override
  public Future<EarlybirdResponse> apply(EarlybirdRequestContetwittert request) {
    if (request.getRequest().isSetEtwitterperimentClusterToUse()) {
      EtwitterperimentCluster cluster = request.getRequest().getEtwitterperimentClusterToUse();

      if (!etwitterperimentScatterGatherServices.containsKey(cluster)) {
        String error = String.format(
            "Received invalid etwitterperiment cluster: %s", cluster.name());

        LOG.error("{} Request: {}", error, request.getRequest());

        return Future.value(new EarlybirdResponse()
            .setResponseCode(EarlybirdResponseCode.CLIENT_ERROR)
            .setDebugString(error));
      }

      return etwitterperimentScatterGatherServices.get(cluster).apply(request);
    }

    return controlScatterGatherService.apply(request);
  }
}
