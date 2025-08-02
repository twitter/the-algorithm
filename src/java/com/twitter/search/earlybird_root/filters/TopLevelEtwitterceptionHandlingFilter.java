package com.twitter.search.earlybird_root.filters;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.util.Future;

/** A top level filter for handling etwitterceptions. */
public class TopLevelEtwitterceptionHandlingFilter
    etwittertends SimpleFilter<EarlybirdRequest, EarlybirdResponse> {
  private final EarlybirdResponseEtwitterceptionHandler etwitterceptionHandler;

  /** Creates a new TopLevelEtwitterceptionHandlingFilter instance. */
  public TopLevelEtwitterceptionHandlingFilter() {
    this.etwitterceptionHandler = new EarlybirdResponseEtwitterceptionHandler("top_level");
  }

  @Override
  public Future<EarlybirdResponse> apply(EarlybirdRequest request,
                                         Service<EarlybirdRequest, EarlybirdResponse> service) {
    return etwitterceptionHandler.handleEtwitterception(request, service.apply(request));
  }
}
