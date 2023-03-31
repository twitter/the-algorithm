package com.twitter.search.earlybird_root.validators;

import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.util.Future;

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
