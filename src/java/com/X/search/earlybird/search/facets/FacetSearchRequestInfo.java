package com.X.search.earlybird.search.facets;

import org.apache.lucene.search.Query;

import com.X.search.common.ranking.thriftjava.ThriftFacetRankingOptions;
import com.X.search.common.search.TerminationTracker;
import com.X.search.core.earlybird.facets.FacetCountState;
import com.X.search.earlybird.search.SearchRequestInfo;
import com.X.search.earlybird.thrift.ThriftSearchQuery;

public class FacetSearchRequestInfo extends SearchRequestInfo {
  protected final FacetCountState facetCountState;
  protected final ThriftFacetRankingOptions rankingOptions;

  public FacetSearchRequestInfo(ThriftSearchQuery searchQuery,
                                ThriftFacetRankingOptions rankingOptions,
                                Query query,
                                FacetCountState facetCountState,
                                TerminationTracker terminationTracker) {
    super(searchQuery, query, terminationTracker);
    this.facetCountState = facetCountState;
    this.rankingOptions = rankingOptions;
  }

  public final FacetCountState getFacetCountState() {
    return this.facetCountState;
  }
}
