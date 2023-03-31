package com.twitter.search.earlybird_root;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.twitter.finagle.Service;
import com.twitter.finagle.mtls.authorization.server.MtlsServerSessionTrackerFilter;
import com.twitter.search.common.clientstats.FinagleClientStatsFilter;
import com.twitter.search.common.root.LoggingFilter;
import com.twitter.search.common.root.RequestValidationFilter;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdService;
import com.twitter.search.earlybird.thrift.EarlybirdStatusResponse;
import com.twitter.search.earlybird_root.filters.ClientIdArchiveAccessFilter;
import com.twitter.search.earlybird_root.filters.ClientIdQuotaFilter;
import com.twitter.search.earlybird_root.filters.ClientIdTrackingFilter;
import com.twitter.search.earlybird_root.filters.ClientRequestTimeFilter;
import com.twitter.search.earlybird_root.filters.DeadlineTimeoutStatsFilter;
import com.twitter.search.earlybird_root.filters.DisableClientByTierFilter;
import com.twitter.search.earlybird_root.filters.EarlybirdFeatureSchemaAnnotateFilter;
import com.twitter.search.earlybird_root.filters.InitializeRequestContextFilter;
import com.twitter.search.earlybird_root.filters.MetadataTrackingFilter;
import com.twitter.search.earlybird_root.filters.NamedMultiTermDisjunctionStatsFilter;
import com.twitter.search.earlybird_root.filters.NullcastTrackingFilter;
import com.twitter.search.earlybird_root.filters.PreCacheRequestTypeCountFilter;
import com.twitter.search.earlybird_root.filters.QueryLangStatFilter;
import com.twitter.search.earlybird_root.filters.QueryOperatorStatFilter;
import com.twitter.search.earlybird_root.filters.QueryTokenizerFilter;
import com.twitter.search.earlybird_root.filters.RequestResultStatsFilter;
import com.twitter.search.earlybird_root.filters.RequestSuccessStatsFilter;
import com.twitter.search.earlybird_root.filters.ResponseCodeStatFilter;
import com.twitter.search.earlybird_root.filters.SearchPayloadSizeLocalContextFilter;
import com.twitter.search.earlybird_root.filters.RejectRequestsByQuerySourceFilter;
import com.twitter.search.earlybird_root.filters.StratoAttributionClientIdFilter;
import com.twitter.search.earlybird_root.filters.TopLevelExceptionHandlingFilter;
import com.twitter.search.earlybird_root.filters.VeryRecentTweetsFilter;
import com.twitter.util.Future;

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
