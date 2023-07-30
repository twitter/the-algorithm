package com.X.search.earlybird_root;

import javax.inject.Inject;
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
import com.X.search.earlybird_root.filters.ClientIdArchiveAccessFilter;
import com.X.search.earlybird_root.filters.ClientIdQuotaFilter;
import com.X.search.earlybird_root.filters.ClientIdTrackingFilter;
import com.X.search.earlybird_root.filters.ClientRequestTimeFilter;
import com.X.search.earlybird_root.filters.DeadlineTimeoutStatsFilter;
import com.X.search.earlybird_root.filters.DisableClientByTierFilter;
import com.X.search.earlybird_root.filters.EarlybirdFeatureSchemaAnnotateFilter;
import com.X.search.earlybird_root.filters.InitializeRequestContextFilter;
import com.X.search.earlybird_root.filters.MetadataTrackingFilter;
import com.X.search.earlybird_root.filters.NamedMultiTermDisjunctionStatsFilter;
import com.X.search.earlybird_root.filters.NullcastTrackingFilter;
import com.X.search.earlybird_root.filters.PreCacheRequestTypeCountFilter;
import com.X.search.earlybird_root.filters.QueryLangStatFilter;
import com.X.search.earlybird_root.filters.QueryOperatorStatFilter;
import com.X.search.earlybird_root.filters.QueryTokenizerFilter;
import com.X.search.earlybird_root.filters.RequestResultStatsFilter;
import com.X.search.earlybird_root.filters.RequestSuccessStatsFilter;
import com.X.search.earlybird_root.filters.ResponseCodeStatFilter;
import com.X.search.earlybird_root.filters.SearchPayloadSizeLocalContextFilter;
import com.X.search.earlybird_root.filters.RejectRequestsByQuerySourceFilter;
import com.X.search.earlybird_root.filters.StratoAttributionClientIdFilter;
import com.X.search.earlybird_root.filters.TopLevelExceptionHandlingFilter;
import com.X.search.earlybird_root.filters.VeryRecentTweetsFilter;
import com.X.util.Future;

@Singleton
class SuperRootService implements EarlybirdService.ServiceIface {
  private final Service<EarlybirdRequest, EarlybirdResponse> fullSearchMethod;

  @Inject
  public SuperRootService(
      TopLevelExceptionHandlingFilter topLevelExceptionHandlingFilter,
      ResponseCodeStatFilter responseCodeStatFilter,
      LoggingFilter<EarlybirdRequest, EarlybirdResponse> loggingFilter,
      NamedMultiTermDisjunctionStatsFilter namedMultiTermDisjunctionStatsFilter,
      RequestValidationFilter<EarlybirdRequest, EarlybirdResponse> validationFilter,
      MtlsServerSessionTrackerFilter<EarlybirdRequest, EarlybirdResponse> mtlsFilter,
      FinagleClientStatsFilter<EarlybirdRequest, EarlybirdResponse> finagleStatsFilter,
      InitializeFilter initializeFilter,
      InitializeRequestContextFilter initializeRequestContextFilter,
      QueryLangStatFilter queryLangStatFilter,
      QueryOperatorStatFilter queryOperatorStatFilter,
      RequestResultStatsFilter requestResultStatsFilter,
      PreCacheRequestTypeCountFilter preCacheRequestTypeCountFilter,
      ClientIdArchiveAccessFilter clientIdArchiveAccessFilter,
      DisableClientByTierFilter disableClientByTierFilter,
      ClientIdTrackingFilter clientIdTrackingFilter,
      ClientIdQuotaFilter quotaFilter,
      RejectRequestsByQuerySourceFilter rejectRequestsByQuerySourceFilter,
      MetadataTrackingFilter metadataTrackingFilter,
      VeryRecentTweetsFilter veryRecentTweetsFilter,
      RequestSuccessStatsFilter requestSuccessStatsFilter,
      NullcastTrackingFilter nullcastTrackingFilter,
      QueryTokenizerFilter queryTokenizerFilter,
      ClientRequestTimeFilter clientRequestTimeFilter,
      DeadlineTimeoutStatsFilter deadlineTimeoutStatsFilter,
      SuperRootRequestTypeRouter superRootSearchService,
      EarlybirdFeatureSchemaAnnotateFilter featureSchemaAnnotateFilter,
      SearchPayloadSizeLocalContextFilter searchPayloadSizeLocalContextFilter,
      StratoAttributionClientIdFilter stratoAttributionClientIdFilter) {
    this.fullSearchMethod =
        loggingFilter
            .andThen(topLevelExceptionHandlingFilter)
            .andThen(stratoAttributionClientIdFilter)
            .andThen(clientRequestTimeFilter)
            .andThen(searchPayloadSizeLocalContextFilter)
            .andThen(requestSuccessStatsFilter)
            .andThen(requestResultStatsFilter)
            .andThen(responseCodeStatFilter)
            .andThen(validationFilter)
            .andThen(mtlsFilter)
            .andThen(finagleStatsFilter)
            .andThen(disableClientByTierFilter)
            .andThen(clientIdTrackingFilter)
            .andThen(quotaFilter)
            .andThen(clientIdArchiveAccessFilter)
            .andThen(rejectRequestsByQuerySourceFilter)
            .andThen(namedMultiTermDisjunctionStatsFilter)
            .andThen(metadataTrackingFilter)
            .andThen(veryRecentTweetsFilter)
            .andThen(initializeFilter)
            .andThen(initializeRequestContextFilter)
            .andThen(deadlineTimeoutStatsFilter)
            .andThen(queryLangStatFilter)
            .andThen(nullcastTrackingFilter)
            .andThen(queryOperatorStatFilter)
            .andThen(preCacheRequestTypeCountFilter)
            .andThen(queryTokenizerFilter)
            .andThen(featureSchemaAnnotateFilter)
            .andThen(superRootSearchService);
  }

  @Override
  public Future<String> getName() {
    return Future.value("superroot");
  }

  @Override
  public Future<EarlybirdStatusResponse> getStatus() {
    throw new UnsupportedOperationException("not supported");
  }

  @Override
  public Future<EarlybirdResponse> search(EarlybirdRequest request) {
    return fullSearchMethod.apply(request);
  }
}
