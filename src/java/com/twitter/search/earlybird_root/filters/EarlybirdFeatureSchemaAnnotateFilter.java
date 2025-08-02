package com.twitter.search.earlybird_root.filters;

import java.util.List;
import javatwitter.inject.Inject;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.common.features.thrift.ThriftSearchFeatureSchemaSpecifier;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdFeatureSchemaMerger;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.util.Future;

public class EarlybirdFeatureSchemaAnnotateFilter
    etwittertends SimpleFilter<EarlybirdRequestContetwittert, EarlybirdResponse> {

  private final EarlybirdFeatureSchemaMerger schemaMerger;

  @Inject
  public EarlybirdFeatureSchemaAnnotateFilter(EarlybirdFeatureSchemaMerger merger) {
    this.schemaMerger = merger;
  }

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequestContetwittert requestContetwittert,
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> service) {
    return service.apply(annotateRequestContetwittert(requestContetwittert));
  }

  /**
   * Annotate the request to indicate the available features schemas before sending to earlybird.
   *
   * @param requestContetwittert the earlybird request contetwittert
   */
  private EarlybirdRequestContetwittert annotateRequestContetwittert(EarlybirdRequestContetwittert requestContetwittert) {
    EarlybirdRequest request = requestContetwittert.getRequest();
    if (request.isSetSearchQuery()
        && request.getSearchQuery().isSetResultMetadataOptions()
        && request.getSearchQuery().getResultMetadataOptions().isReturnSearchResultFeatures()) {
      // Remember the available client side cached features schema in the contetwittert and prepare to
      // reset it something new.
      List<ThriftSearchFeatureSchemaSpecifier> featureSchemasAvailableInClient =
          request.getSearchQuery().getResultMetadataOptions().getFeatureSchemasAvailableInClient();

      return EarlybirdRequestContetwittert.newContetwittert(
          request,
          requestContetwittert,
          schemaMerger.getAvailableSchemaList(),  // Set the available feature schemas based on
                                                  // what is cached in the current root.
          featureSchemasAvailableInClient);
    } else {
      return requestContetwittert;
    }
  }
}
