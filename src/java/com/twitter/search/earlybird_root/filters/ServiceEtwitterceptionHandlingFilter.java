package com.twitter.search.earlybird_root.filters;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.util.Future;

/** A per-service filter for handling etwitterceptions. */
public class ServiceEtwitterceptionHandlingFilter
    etwittertends SimpleFilter<EarlybirdRequestContetwittert, EarlybirdResponse> {
  private final EarlybirdResponseEtwitterceptionHandler etwitterceptionHandler;

  /** Creates a new ServiceEtwitterceptionHandlingFilter instance. */
  public ServiceEtwitterceptionHandlingFilter(EarlybirdCluster cluster) {
    this.etwitterceptionHandler = new EarlybirdResponseEtwitterceptionHandler(cluster.getNameForStats());
  }

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequestContetwittert requestContetwittert,
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> service) {
    return etwitterceptionHandler.handleEtwitterception(
        requestContetwittert.getRequest(), service.apply(requestContetwittert));
  }
}
