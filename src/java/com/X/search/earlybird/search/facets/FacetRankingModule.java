package com.X.search.earlybird.search.facets;

import java.util.ArrayList;
import java.util.List;

import com.X.search.core.earlybird.facets.FacetCountState;
import com.X.search.earlybird.search.EarlybirdLuceneSearcher;
import com.X.search.earlybird.thrift.ThriftFacetFieldResults;

public abstract class FacetRankingModule {
  public static final List<FacetRankingModule> REGISTERED_RANKING_MODULES =
      new ArrayList<>();

  static {
    REGISTERED_RANKING_MODULES.add(new SimpleCountRankingModule());
  }

  /**
   * Prepares the {@link com.X.search.earlybird.thrift.ThriftFacetFieldResults}
   * in {@link FacetCountState} before they're returned. This extension point therefore allows
   * post-processing the facet results, e.g. for re-ranking or sorting purposes.
   */
  public abstract void prepareResults(
      EarlybirdLuceneSearcher.FacetSearchResults hits,
      FacetCountState<ThriftFacetFieldResults> facetCountState);
}
