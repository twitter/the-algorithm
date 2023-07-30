package com.X.search.earlybird_root;

import java.util.List;

import javax.inject.Inject;

import com.X.finagle.Filter;
import com.X.finagle.Service;
import com.X.search.common.schema.earlybird.EarlybirdCluster;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird_root.common.EarlybirdFeatureSchemaMerger;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;
import com.X.search.earlybird_root.mergers.EarlybirdResponseMerger;
import com.X.search.earlybird_root.mergers.TierResponseAccumulator;
import com.X.util.Function;
import com.X.util.Future;

/**
 * Filter used to merge results from multiple tiers
 */
public class MultiTierResultsMergeFilter extends
    Filter<EarlybirdRequestContext, EarlybirdResponse,
        EarlybirdRequestContext, List<Future<EarlybirdResponse>>> {

  private final EarlybirdFeatureSchemaMerger featureSchemaMerger;

  @Inject
  public MultiTierResultsMergeFilter(EarlybirdFeatureSchemaMerger featureSchemaMerger) {
    this.featureSchemaMerger = featureSchemaMerger;
  }

  @Override
  public Future<EarlybirdResponse> apply(
      final EarlybirdRequestContext request,
      Service<EarlybirdRequestContext, List<Future<EarlybirdResponse>>> service) {
    return service.apply(request).flatMap(Function.func(responses -> merge(request, responses)));
  }

  private Future<EarlybirdResponse> merge(
      EarlybirdRequestContext requestContext,
      List<Future<EarlybirdResponse>> responses) {

    // For multi-tier response merging, the number of partitions do not have meaning because
    // the response is not uniformly partitioned anymore.  We pass Integer.MAX_VALUE for stats
    // counting purpose.
    EarlybirdResponseMerger merger = EarlybirdResponseMerger.getResponseMerger(
        requestContext,
        responses,
        new TierResponseAccumulator(),
        EarlybirdCluster.FULL_ARCHIVE,
        featureSchemaMerger,
        Integer.MAX_VALUE);
    return merger.merge();
  }
}
