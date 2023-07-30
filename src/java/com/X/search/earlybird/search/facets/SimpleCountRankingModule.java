package com.X.search.earlybird.search.facets;

import java.util.Iterator;

import com.X.search.common.schema.base.Schema;
import com.X.search.core.earlybird.facets.FacetCountState;
import com.X.search.core.earlybird.facets.FacetCountState.FacetFieldResults;
import com.X.search.earlybird.search.EarlybirdLuceneSearcher;
import com.X.search.earlybird.thrift.ThriftFacetFieldResults;

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
