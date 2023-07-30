package com.X.search.earlybird_root;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;


import com.X.finagle.Service;
import com.X.finagle.mtls.authorization.server.MtlsServerSessionTrackerFilter;
import com.X.search.common.clientstats.FinagleClientStatsFilter;
import com.X.search.common.root.LoggingFilter;
import com.X.search.common.root.RequestValidationFilter;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird.thrift.EarlybirdService;
import com.X.search.earlybird.thrift.EarlybirdStatusResponse;
import com.X.search.earlybird_root.caching.FacetsCacheFilter;
import com.X.search.earlybird_root.caching.RecencyCacheFilter;
import com.X.search.earlybird_root.caching.RelevanceCacheFilter;
import com.X.search.earlybird_root.caching.RelevanceZeroResultsCacheFilter;
import com.X.search.earlybird_root.caching.StrictRecencyCacheFilter;
import com.X.search.earlybird_root.caching.TermStatsCacheFilter;
import com.X.search.earlybird_root.caching.TopTweetsCacheFilter;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;
import com.X.search.earlybird_root.filters.ClientIdTrackingFilter;
import com.X.search.earlybird_root.filters.ClientRequestTimeFilter;
import com.X.search.earlybird_root.filters.DeadlineTimeoutStatsFilter;
import com.X.search.earlybird_root.filters.DropAllProtectedOperatorFilter;
import com.X.search.earlybird_root.filters.EarlybirdFeatureSchemaAnnotateFilter;
import com.X.search.earlybird_root.filters.InitializeRequestContextFilter;
import com.X.search.earlybird_root.filters.MetadataTrackingFilter;
import com.X.search.earlybird_root.filters.NullcastTrackingFilter;
import com.X.search.earlybird_root.filters.PostCacheRequestTypeCountFilter;
import com.X.search.earlybird_root.filters.PreCacheRequestTypeCountFilter;
import com.X.search.earlybird_root.filters.QueryLangStatFilter;
import com.X.search.earlybird_root.filters.QueryOperatorStatFilter;
import com.X.search.earlybird_root.filters.RequestResultStatsFilter;
import com.X.search.earlybird_root.filters.ResponseCodeStatFilter;
import com.X.search.earlybird_root.filters.SearchPayloadSizeLocalContextFilter;
import com.X.search.earlybird_root.filters.StratoAttributionClientIdFilter;
import com.X.search.earlybird_root.filters.TopLevelExceptionHandlingFilter;
import com.X.util.Future;

@Singleton
public class RealtimeRootService implements EarlybirdService.ServiceIface {

  private final Service<EarlybirdRequest, EarlybirdResponse> allFiltersAndService;

  @Inject
  public RealtimeRootService(
      TopLevelExceptionHandlingFilter topLevelExceptionHandlingFilter,
      ResponseCodeStatFilter responseCodeStatFilter,
      LoggingFilter<EarlybirdRequest, EarlybirdResponse> loggingFilter,
      RequestValidationFilter<EarlybirdRequest, EarlybirdResponse> validationFilter,
      MtlsServerSessionTrackerFilter<EarlybirdRequest, EarlybirdResponse> mtlsFilter,
      FinagleClientStatsFilter<EarlybirdRequest, EarlybirdResponse> finagleStatsFilter,
      InitializeFilter initializeFilter,
      InitializeRequestContextFilter initializeRequestContextFilter,
      QueryLangStatFilter queryLangStatFilter,
      DropAllProtectedOperatorFilter dropAllProtectedOperatorFilter,
      QueryOperatorStatFilter queryOperatorStatFilter,
      RequestResultStatsFilter requestResultStatsFilter,
      PreCacheRequestTypeCountFilter preCacheCountFilter,
      RecencyCacheFilter recencyCacheFilter,
      RelevanceCacheFilter relevanceCacheFilter,
      RelevanceZeroResultsCacheFilter relevanceZeroResultsCacheFilter,
      StrictRecencyCacheFilter strictRecencyCacheFilter,
      FacetsCacheFilter facetsCacheFilter,
      TermStatsCacheFilter termStatsCacheFilter,
      TopTweetsCacheFilter topTweetsCacheFilter,
      PostCacheRequestTypeCountFilter postCacheCountFilter,
      ClientIdTrackingFilter clientIdTrackingFilter,
      MetadataTrackingFilter metadataTrackingFilter,
      NullcastTrackingFilter nullcastTrackingFilter,
      ClientRequestTimeFilter clientRequestTimeFilter,
      DeadlineTimeoutStatsFilter deadlineTimeoutStatsFilter,
      EarlybirdFeatureSchemaAnnotateFilter featureSchemaAnnotateFilter,
      SearchPayloadSizeLocalContextFilter searchPayloadSizeLocalContextFilter,
      @Named(ProtectedScatterGatherModule.NAMED_SCATTER_GATHER_SERVICE)
          Service<EarlybirdRequestContext, EarlybirdResponse> scatterGatherService,
      StratoAttributionClientIdFilter stratoAttributionClientIdFilter) {
    this.allFiltersAndService =
        loggingFilter
            .andThen(topLevelExceptionHandlingFilter)
            .andThen(stratoAttributionClientIdFilter)
            .andThen(clientRequestTimeFilter)
            .andThen(searchPayloadSizeLocalContextFilter)
            .andThen(responseCodeStatFilter)
            .andThen(requestResultStatsFilter)
            .andThen(validationFilter)
            .andThen(mtlsFilter)
            .andThen(finagleStatsFilter)
            .andThen(clientIdTrackingFilter)
            .andThen(metadataTrackingFilter)
            .andThen(initializeFilter)
            .andThen(initializeRequestContextFilter)
            .andThen(deadlineTimeoutStatsFilter)
            .andThen(queryLangStatFilter)
            .andThen(nullcastTrackingFilter)
            .andThen(dropAllProtectedOperatorFilter)
            .andThen(queryOperatorStatFilter)
            .andThen(preCacheCountFilter)
            .andThen(recencyCacheFilter)
            .andThen(relevanceCacheFilter)
            .andThen(relevanceZeroResultsCacheFilter)
            .andThen(strictRecencyCacheFilter)
            .andThen(facetsCacheFilter)
            .andThen(termStatsCacheFilter)
            .andThen(topTweetsCacheFilter)
            .andThen(postCacheCountFilter)
            .andThen(featureSchemaAnnotateFilter)
            .andThen(scatterGatherService);
  }

  @Override
  public Future<String> getName() {
    return Future.value("realtime root");
  }

  @Override
  public Future<EarlybirdStatusResponse> getStatus() {
    throw new UnsupportedOperationException("not supported");
  }

  @Override
  public Future<EarlybirdResponse> search(EarlybirdRequest request) {
    return allFiltersAndService.apply(request);
  }
}
