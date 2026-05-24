package com.twitter.search.earlybird_root.validators;

import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.util.Future;

public class TermStatsResultsValidator implements ServiceResponseValidator<EarlybirdResponse> {
  private final EarlybirdCluster cluster;

  public TermStatsResultsValidator(EarlybirdCluster cluster) {
    this.cluster = cluster;
  }

  @Override
  public Future<EarlybirdResponse> validate(EarlybirdResponse response) {
    if (!response.isSetTermStatisticsResults()
        || !response.getTermStatisticsResults().isSetTermResults()) {
      return Future.exception(
          new IllegalStateException(cluster + " returned null term statistics results."));
    }
    return Future.value(response);
  }
}
