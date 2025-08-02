package com.twitter.search.earlybird_root;

import java.util.List;

import javatwitter.inject.Inject;

import com.twitter.finagle.Filter;
import com.twitter.finagle.Service;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdFeatureSchemaMerger;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.earlybird_root.mergers.EarlybirdResponseMerger;
import com.twitter.search.earlybird_root.mergers.TierResponseAccumulator;
import com.twitter.util.Function;
import com.twitter.util.Future;

/**
 * Filter used to merge results from multiple tiers
 */
public class MultiTierResultsMergeFilter etwittertends
    Filter<EarlybirdRequestContetwittert, EarlybirdResponse,
        EarlybirdRequestContetwittert, List<Future<EarlybirdResponse>>> {

  private final EarlybirdFeatureSchemaMerger featureSchemaMerger;

  @Inject
  public MultiTierResultsMergeFilter(EarlybirdFeatureSchemaMerger featureSchemaMerger) {
    this.featureSchemaMerger = featureSchemaMerger;
  }

  @Override
  public Future<EarlybirdResponse> apply(
      final EarlybirdRequestContetwittert request,
      Service<EarlybirdRequestContetwittert, List<Future<EarlybirdResponse>>> service) {
    return service.apply(request).flatMap(Function.func(responses -> merge(request, responses)));
  }

  private Future<EarlybirdResponse> merge(
      EarlybirdRequestContetwittert requestContetwittert,
      List<Future<EarlybirdResponse>> responses) {

    // For multi-tier response merging, the number of partitions do not have meaning because
    // the response is not uniformly partitioned anymore.  We pass Integer.MAtwitter_VALUE for stats
    // counting purpose.
    EarlybirdResponseMerger merger = EarlybirdResponseMerger.getResponseMerger(
        requestContetwittert,
        responses,
        new TierResponseAccumulator(),
        EarlybirdCluster.FULL_ARCHIVE,
        featureSchemaMerger,
        Integer.MAtwitter_VALUE);
    return merger.merge();
  }
}
