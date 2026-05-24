package com.twitter.search.core.earlybird.facets;

import java.io.IOException;
import java.util.List;

import com.google.common.base.Preconditions;

import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.sortedset.SortedSetDocValuesFacetCounts;
import org.apache.lucene.facet.sortedset.SortedSetDocValuesReaderState;

import com.twitter.search.common.facets.CountFacetSearchParam;
import com.twitter.search.common.facets.FacetSearchParam;
import com.twitter.search.common.facets.FacetsFactory;

/**
 * Factory for SortedSetDocValuesFacetCounts
 */
public class SortedSetDocValuesFacetsFactory implements FacetsFactory {
  private final SortedSetDocValuesReaderState state;

  public SortedSetDocValuesFacetsFactory(SortedSetDocValuesReaderState state) {
    this.state = state;
  }

  @Override
  public Facets create(
      List<FacetSearchParam> facetSearchParams,
      FacetsCollector facetsCollector) throws IOException {

    Preconditions.checkNotNull(facetsCollector);

    return new SortedSetDocValuesFacetCounts(state, facetsCollector);
  }

  @Override
  public boolean accept(FacetSearchParam facetSearchParam) {
    return facetSearchParam instanceof CountFacetSearchParam
        && (facetSearchParam.getFacetFieldRequest().getPath() == null
            || facetSearchParam.getFacetFieldRequest().getPath().isEmpty())
        && SortedSetDocValuesReaderStateHelper.isDimSupported(
            state, facetSearchParam.getFacetFieldRequest().getField());
  }
}
