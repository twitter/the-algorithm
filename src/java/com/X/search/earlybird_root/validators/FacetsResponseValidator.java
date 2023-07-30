package com.X.search.earlybird_root.validators;

import com.X.search.common.schema.earlybird.EarlybirdCluster;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.util.Future;

public class FacetsResponseValidator implements ServiceResponseValidator<EarlybirdResponse> {

  private final EarlybirdCluster cluster;

  /**
   * Validator for facets responses
   */
  public FacetsResponseValidator(EarlybirdCluster cluster) {
    this.cluster = cluster;
  }

  @Override
  public Future<EarlybirdResponse> validate(EarlybirdResponse response) {
    if (!response.isSetSearchResults() || !response.getSearchResults().isSetResults()) {
      return Future.exception(
          new IllegalStateException(cluster + " didn't set search results."));
    }

    if (!response.isSetFacetResults()) {
      return Future.exception(
          new IllegalStateException(
              cluster + " facets response does not have the facetResults field set."));
    }

    if (response.getFacetResults().getFacetFields().isEmpty()) {
      return Future.exception(
          new IllegalStateException(
              cluster + " facets response does not have any facet fields set."));
    }

    return Future.value(response);
  }
}
