package com.twitter.search.earlybird.search.facets;

import org.apache.lucene.search.Query;

import com.twitter.search.common.ranking.thriftjava.ThriftFacetRankingOptions;
import com.twitter.search.common.search.TerminationTracker;
import com.twitter.search.core.earlybird.facets.FacetCountState;
import com.twitter.search.earlybird.search.SearchRequestInfo;
import com.twitter.search.earlybird.thrift.ThriftSearchQuery;

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
