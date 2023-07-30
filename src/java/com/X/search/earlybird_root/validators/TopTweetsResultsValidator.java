package com.X.search.earlybird_root.validators;

import com.X.search.common.schema.earlybird.EarlybirdCluster;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.util.Future;

public class TopTweetsResultsValidator implements ServiceResponseValidator<EarlybirdResponse> {
  private final EarlybirdCluster cluster;

  public TopTweetsResultsValidator(EarlybirdCluster cluster) {
    this.cluster = cluster;
  }

  @Override
  public Future<EarlybirdResponse> validate(EarlybirdResponse response) {
    if (!response.isSetSearchResults() || !response.getSearchResults().isSetResults()) {
      return Future.exception(
          new IllegalStateException(cluster + " didn't set search results."));
    }
    return Future.value(response);
  }
}
