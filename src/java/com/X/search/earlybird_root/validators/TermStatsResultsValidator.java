package com.X.search.earlybird_root.validators;

import com.X.search.common.schema.earlybird.EarlybirdCluster;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.util.Future;

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
