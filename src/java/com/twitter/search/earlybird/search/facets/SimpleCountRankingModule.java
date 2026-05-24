package com.twitter.search.earlybird.search.facets;

import java.util.Iterator;

import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.core.earlybird.facets.FacetCountState;
import com.twitter.search.core.earlybird.facets.FacetCountState.FacetFieldResults;
import com.twitter.search.earlybird.search.EarlybirdLuceneSearcher;
import com.twitter.search.earlybird.thrift.ThriftFacetFieldResults;

public class SimpleCountRankingModule extends FacetRankingModule {

  @Override
  public void prepareResults(
      EarlybirdLuceneSearcher.FacetSearchResults hits,
      FacetCountState<ThriftFacetFieldResults> facetCountState) {
    Iterator<FacetFieldResults<ThriftFacetFieldResults>> fieldResultsIterator =
            facetCountState.getFacetFieldResultsIterator();
    while (fieldResultsIterator.hasNext()) {
      FacetFieldResults<ThriftFacetFieldResults> state = fieldResultsIterator.next();
      if (!state.isFinished()) {
        Schema.FieldInfo facetField =
                facetCountState.getSchema().getFacetFieldByFacetName(state.facetName);
        state.results = hits.getFacetResults(
                facetField.getFieldType().getFacetName(), state.numResultsRequested);
        if (state.results != null) {
          state.numResultsFound = state.results.getTopFacetsSize();
        }
      }
    }
  }
}
