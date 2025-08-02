package com.twitter.search.earlybird_root.routers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.finagle.Service;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.futures.Futures;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.util.earlybird.EarlybirdResponseMergeUtil;
import com.twitter.search.earlybird.thrift.AdjustedRequestParams;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdResponseCode;
import com.twitter.search.earlybird.thrift.ThriftSearchQuery;
import com.twitter.search.earlybird.thrift.ThriftSearchRankingMode;
import com.twitter.search.earlybird.thrift.ThriftSearchResults;
import com.twitter.search.earlybird_root.common.ClientErrorEtwitterception;
import com.twitter.search.earlybird_root.common.EarlybirdFeatureSchemaMerger;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.earlybird_root.common.EarlybirdRequestUtil;
import com.twitter.search.earlybird_root.common.EarlybirdServiceResponse;
import com.twitter.search.earlybird_root.filters.EarlybirdTimeRangeFilter;
import com.twitter.search.earlybird_root.mergers.SuperRootResponseMerger;
import com.twitter.search.queryparser.util.QueryUtil;
import com.twitter.util.Function;
import com.twitter.util.Function0;
import com.twitter.util.Future;

/**
 * For Recency traffic SuperRoot hits realtime and/or protected realtime first and then archive
 */
public abstract class AbstractRecencyAndRelevanceRequestRouter etwittertends RequestRouter {
  public static final String FULL_ARCHIVE_AVAILABLE_FOR_GET_PROTECTED_TWEETS_ONLY_DECIDER_KEY =
      "superroot_full_archive_cluster_available_for_get_protected_tweets_only_requests";
  public static final String FULL_ARCHIVE_AVAILABLE_FOR_NOT_ENOUGH_PROTECTED_RESULTS_DECIDER_KEY =
      "superroot_full_archive_cluster_available_for_requests_without_enough_protected_results";

  private static final Logger LOG =
      LoggerFactory.getLogger(AbstractRecencyAndRelevanceRequestRouter.class);

  private final String skipProtectedClusterDeciderKey;
  private final String skipFullArchiveClusterDeciderKey;

  private final SearchCounter realtimeResponseInvalidCounter;
  private final SearchCounter realtimeResponseSearchResultsNotSetCounter;
  private final SearchCounter minSearchedStatusIdLargerThanRequestMatwitterIdCounter;
  private final SearchCounter minSearchedStatusIdLargerThanRequestUntilTimeCounter;

  private final Service<EarlybirdRequestContetwittert, EarlybirdResponse> realtime;
  private final Service<EarlybirdRequestContetwittert, EarlybirdResponse> protectedRealtime;
  private final Service<EarlybirdRequestContetwittert, EarlybirdResponse> fullArchive;
  private final SuperRootResponseMerger responseMerger;
  private final SearchDecider decider;

  AbstractRecencyAndRelevanceRequestRouter(
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> realtime,
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> protectedRealtime,
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> fullArchive,
      EarlybirdTimeRangeFilter realtimeTimeRangeFilter,
      EarlybirdTimeRangeFilter protectedTimeRangeFilter,
      EarlybirdTimeRangeFilter fullArchiveTimeRangeFilter,
      ThriftSearchRankingMode rankingMode,
      Clock clock,
      SearchDecider decider,
      EarlybirdFeatureSchemaMerger featureSchemaMerger) {
    LOG.info("Instantiating AbstractRecencyAndRelevanceRequestRouter");
    this.realtime = realtimeTimeRangeFilter.andThen(realtime);
    this.protectedRealtime = protectedTimeRangeFilter.andThen(protectedRealtime);
    this.fullArchive = fullArchiveTimeRangeFilter.andThen(fullArchive);
    this.responseMerger = new SuperRootResponseMerger(rankingMode, featureSchemaMerger, clock);
    this.decider = decider;

    String rankingModeForStats = rankingMode.name().toLowerCase();
    skipProtectedClusterDeciderKey =
        String.format("superroot_skip_protected_cluster_for_%s_requests", rankingModeForStats);
    skipFullArchiveClusterDeciderKey =
        String.format("superroot_skip_full_archive_cluster_for_%s_requests", rankingModeForStats);

    realtimeResponseInvalidCounter =
        SearchCounter.etwitterport(rankingModeForStats + "_realtime_response_invalid");
    realtimeResponseSearchResultsNotSetCounter =
        SearchCounter.etwitterport(rankingModeForStats + "_realtime_response_search_results_not_set");
    minSearchedStatusIdLargerThanRequestMatwitterIdCounter = SearchCounter.etwitterport(
        rankingModeForStats + "_min_searched_status_id_larger_than_request_matwitter_id");
    minSearchedStatusIdLargerThanRequestUntilTimeCounter = SearchCounter.etwitterport(
        rankingModeForStats + "_min_searched_status_id_larger_than_request_until_time");
  }

  private void checkRequestPreconditions(EarlybirdRequest request) {
    // CollectorParams should be set in EarlybirdRequestUtil.checkAndSetCollectorParams().
    Preconditions.checkNotNull(request.getSearchQuery().getCollectorParams());

    // return a Client error if the num results are less than 0
    if (request.getSearchQuery().getNumResults() < 0) {
      throw new ClientErrorEtwitterception("The request.searchQuery.numResults field can't be negative");
    }

    if (request.getSearchQuery().getCollectorParams().getNumResultsToReturn() < 0) {
      throw new ClientErrorEtwitterception("The request.searchQuery.collectorParams.numResultsToReturn "
          + "field can't be negative");
    }
  }

  /**
   * Hit realtime and/or protected realtime first, if not enough results, then hit archive,
   * merge the results.
   */
  @Override
  public Future<EarlybirdResponse> route(final EarlybirdRequestContetwittert requestContetwittert) {
    EarlybirdRequest request = requestContetwittert.getRequest();

    this.checkRequestPreconditions(request);

    ArrayList<RequestResponse> savedRequestResponses = new ArrayList<>();

    // If clients do not define numResults to return or the numResults requested are 0
    // return an empty EarlyBirdResponse without hitting any service.
    if (request.getSearchQuery().getNumResults() == 0
        || request.getSearchQuery().getCollectorParams().getNumResultsToReturn() == 0) {
      return Future.value(successNoResultsResponse());
    }

    // Realtime earlybird response is already required. Even if the service is not called
    // the result passed to the mergers should be a valid one.
    EarlybirdServiceResponse.ServiceState realtimeServiceState =
        getRealtimeServiceState(requestContetwittert);
    final Future<EarlybirdServiceResponse> realtimeResponseFuture =
        realtimeServiceState.serviceWasCalled()
            ? getRealtimeResponse(savedRequestResponses, requestContetwittert)
            : Future.value(EarlybirdServiceResponse.serviceNotCalled(realtimeServiceState));

    // If no flock response (followedUserIds) is set, request wont be sent to protected.
    EarlybirdServiceResponse.ServiceState protectedServiceState =
        getProtectedServiceState(requestContetwittert);
    final Future<EarlybirdServiceResponse> protectedResponseFuture =
        protectedServiceState.serviceWasCalled()
            ? getProtectedResponse(savedRequestResponses, requestContetwittert)
            : Future.value(EarlybirdServiceResponse.serviceNotCalled(protectedServiceState));

    final Future<EarlybirdServiceResponse> archiveResponseFuture =
        Futures.flatMap(realtimeResponseFuture, protectedResponseFuture,
            new Function0<Future<EarlybirdServiceResponse>>() {
              @Override
              public Future<EarlybirdServiceResponse> apply() {
                EarlybirdServiceResponse realtimeResponse = Futures.get(realtimeResponseFuture);
                EarlybirdServiceResponse protectedResponse = Futures.get(protectedResponseFuture);
                EarlybirdServiceResponse.ServiceState fullArchiveServiceState =
                    getFullArchiveServiceState(requestContetwittert, realtimeResponse, protectedResponse);
                return fullArchiveServiceState.serviceWasCalled()
                    ? getFullArchiveResponse(savedRequestResponses, requestContetwittert,
                    realtimeResponse.getResponse(), protectedResponse.getResponse())
                    : Future.value(
                        EarlybirdServiceResponse.serviceNotCalled(fullArchiveServiceState));
              }
            }
        );

    Future<EarlybirdResponse> mergedResponse = responseMerger.mergeResponseFutures(
        requestContetwittert, realtimeResponseFuture, protectedResponseFuture, archiveResponseFuture);
    mergedResponse = mergedResponse
        .map(RequestRouterUtil.checkMinSearchedStatusId(
                 requestContetwittert,
                 "matwitter_id",
                 EarlybirdRequestUtil.getRequestMatwitterId(requestContetwittert.getParsedQuery()),
                 realtimeResponseFuture,
                 protectedResponseFuture,
                 archiveResponseFuture,
                 minSearchedStatusIdLargerThanRequestMatwitterIdCounter))
        .map(RequestRouterUtil.checkMinSearchedStatusId(
                 requestContetwittert,
                 "until_time",
                 EarlybirdRequestUtil.getRequestMatwitterIdFromUntilTime(requestContetwittert.getParsedQuery()),
                 realtimeResponseFuture,
                 protectedResponseFuture,
                 archiveResponseFuture,
                 minSearchedStatusIdLargerThanRequestUntilTimeCounter));

    return this.maybeAttachSentRequestsToDebugInfo(
        savedRequestResponses,
        requestContetwittert,
        mergedResponse
    );
  }

  private EarlybirdResponse successNoResultsResponse() {
    return new EarlybirdResponse(EarlybirdResponseCode.SUCCESS, 0)
        .setSearchResults(new ThriftSearchResults().setResults(Collections.emptyList()));
  }

  protected abstract boolean shouldSendRequestToFullArchiveCluster(
      EarlybirdRequest request, EarlybirdResponse realtimeResponse);

  /** Determines if the protected service is available and if a request should be sent to it. */
  private EarlybirdServiceResponse.ServiceState getProtectedServiceState(
      EarlybirdRequestContetwittert requestContetwittert) {
    if (!requestContetwittert.getRequest().isSetFollowedUserIds()
        || requestContetwittert.getRequest().getFollowedUserIds().isEmpty()) {
      return EarlybirdServiceResponse.ServiceState.SERVICE_NOT_REQUESTED;
    }

    if (decider.isAvailable(skipProtectedClusterDeciderKey)) {
      return EarlybirdServiceResponse.ServiceState.SERVICE_NOT_AVAILABLE;
    }

    return EarlybirdServiceResponse.ServiceState.SERVICE_CALLED;
  }

  /** Determines if the realtime service is available and if a request should be sent to it. */
  private EarlybirdServiceResponse.ServiceState getRealtimeServiceState(
      EarlybirdRequestContetwittert requestContetwittert) {
    EarlybirdRequest request = requestContetwittert.getRequest();

    // SERVICE_NOT_REQUESTED should always be returned before other states as
    // SuperRootResponseMerger has special logic for this case.
    if (request.isSetGetProtectedTweetsOnly() && request.isGetProtectedTweetsOnly()) {
      return EarlybirdServiceResponse.ServiceState.SERVICE_NOT_REQUESTED;
    }

    return EarlybirdServiceResponse.ServiceState.SERVICE_CALLED;
  }

  /** Determines if the full archive service is available and if a request should be sent to it. */
  private EarlybirdServiceResponse.ServiceState getFullArchiveServiceState(
      EarlybirdRequestContetwittert requestContetwittert,
      EarlybirdServiceResponse publicServiceResponse,
      EarlybirdServiceResponse protectedServiceResponse) {

    // SERVICE_NOT_REQUESTED should be always be returned before other states as
    // SuperRootResponseMerger has special logic for this case.
    if (!requestContetwittert.getRequest().isSetGetOlderResults()
        || !requestContetwittert.getRequest().isGetOlderResults()) {
      return EarlybirdServiceResponse.ServiceState.SERVICE_NOT_REQUESTED;
    }

    // allow requesting full archive service when decider is enabled
    if (!decider.isAvailable(FULL_ARCHIVE_AVAILABLE_FOR_GET_PROTECTED_TWEETS_ONLY_DECIDER_KEY)
        && requestContetwittert.getRequest().isSetGetProtectedTweetsOnly()
        && requestContetwittert.getRequest().isGetProtectedTweetsOnly()) {
      return EarlybirdServiceResponse.ServiceState.SERVICE_NOT_REQUESTED;
    }

    if (decider.isAvailable(skipFullArchiveClusterDeciderKey)) {
      return EarlybirdServiceResponse.ServiceState.SERVICE_NOT_AVAILABLE;
    }

    boolean serviceWasCalledForPublic =
        getFullArchiveServiceState(requestContetwittert, publicServiceResponse).serviceWasCalled();
    boolean serviceWasCalledForProtected =
        decider.isAvailable(FULL_ARCHIVE_AVAILABLE_FOR_NOT_ENOUGH_PROTECTED_RESULTS_DECIDER_KEY)
        && getFullArchiveServiceState(requestContetwittert, protectedServiceResponse).serviceWasCalled();
    if (!serviceWasCalledForPublic && !serviceWasCalledForProtected) {
      return EarlybirdServiceResponse.ServiceState.SERVICE_NOT_CALLED;
    }

    return EarlybirdServiceResponse.ServiceState.SERVICE_CALLED;
  }

  private EarlybirdServiceResponse.ServiceState getFullArchiveServiceState(
      EarlybirdRequestContetwittert requestContetwittert,
      EarlybirdServiceResponse realtimeServiceResponse) {
    EarlybirdResponse realtimeResponse = realtimeServiceResponse.getResponse();

    if (!EarlybirdResponseMergeUtil.isValidResponse(realtimeResponse)) {
      realtimeResponseInvalidCounter.increment();
      return EarlybirdServiceResponse.ServiceState.SERVICE_NOT_CALLED;
    }

    if (!realtimeResponse.isSetSearchResults()) {
      realtimeResponseSearchResultsNotSetCounter.increment();
      return EarlybirdServiceResponse.ServiceState.SERVICE_NOT_CALLED;
    }

    if (!shouldSendRequestToFullArchiveCluster(requestContetwittert.getRequest(), realtimeResponse)) {
      return EarlybirdServiceResponse.ServiceState.SERVICE_NOT_CALLED;
    }

    return EarlybirdServiceResponse.ServiceState.SERVICE_CALLED;
  }

  /**
   * Modify the original request contetwittert based on the followedUserId field and then send the
   * request to the protected cluster.
   */
  private Future<EarlybirdServiceResponse> getProtectedResponse(
      ArrayList<RequestResponse> savedRequestResponses,
      final EarlybirdRequestContetwittert requestContetwittert) {
    EarlybirdRequestContetwittert protectedRequestContetwittert =
        EarlybirdRequestContetwittert.newContetwittertWithRestrictFromUserIdFilter64(requestContetwittert);
    Preconditions.checkArgument(
        protectedRequestContetwittert.getRequest().getSearchQuery().isSetFromUserIDFilter64());

    // SERVICE_NOT_REQUESTED should be always be returned before other states as
    // SuperRootResponseMerger has special logic for this case.
    if (protectedRequestContetwittert.getRequest().getSearchQuery().getFromUserIDFilter64().isEmpty()) {
      return Future.value(EarlybirdServiceResponse.serviceNotCalled(
          EarlybirdServiceResponse.ServiceState.SERVICE_NOT_REQUESTED));
    }

    if (requestContetwittert.getRequest().isSetAdjustedProtectedRequestParams()) {
      adjustRequestParams(protectedRequestContetwittert.getRequest(),
                          requestContetwittert.getRequest().getAdjustedProtectedRequestParams());
    }

    LOG.debug("Request sent to the protected cluster: {}", protectedRequestContetwittert.getRequest());
    return toEarlybirdServiceResponseFuture(
        savedRequestResponses,
        protectedRequestContetwittert,
        "protected",
        this.protectedRealtime
    );
  }

  private Future<EarlybirdServiceResponse> getRealtimeResponse(
      ArrayList<RequestResponse> savedRequestResponses,
      EarlybirdRequestContetwittert requestContetwittert) {
    return toEarlybirdServiceResponseFuture(
        savedRequestResponses,
        requestContetwittert,
        "realtime",
        this.realtime);
  }

  /**
   * Modifying the etwitteristing matwitter id filter of the request or appending a new
   * matwitter id filter and then send the request to the full archive cluster.
   */
  private Future<EarlybirdServiceResponse> getFullArchiveResponse(
      ArrayList<RequestResponse> savedRequestResponses,
      EarlybirdRequestContetwittert requestContetwittert,
      EarlybirdResponse realtimeResponse,
      EarlybirdResponse protectedResponse) {
    long realtimeMinId = getMinSearchedId(realtimeResponse);
    long protectedMinId = getMinSearchedId(protectedResponse);
    // if both realtime and protected min searched ids are available, the larger(newer) one is used
    // to make sure no tweets are left out. However, this means it might introduce duplicates for
    // the other response. The response merger will dedup the response. This logic is enabled
    // when full archive cluster is available for requests without enough protected results.
    long minId =
        decider.isAvailable(FULL_ARCHIVE_AVAILABLE_FOR_NOT_ENOUGH_PROTECTED_RESULTS_DECIDER_KEY)
            ? Math.matwitter(realtimeMinId, protectedMinId) : realtimeMinId;

    if (minId <= 0) {
      // If the realtime response doesn't have a minSearchedStatusID set, get all results from
      // the full archive cluster.
      minId = Long.MAtwitter_VALUE;
    }

    // The [matwitter_id] operator is inclusive in earlybirds. This means that a query with [matwitter_id twitter]
    // will return tweet twitter, if twitter matches the rest of the query. So we should add a [matwitter_id (twitter - 1)]
    // operator to the full archive query (instead of [matwitter_id twitter]). Otherwise, we could end up with
    // duplicates. For etwitterample:
    //
    //  realtime response: results = [ 100, 90, 80 ], minSearchedStatusID = 80
    //  full archive request: [matwitter_id 80]
    //  full archive response: results = [ 80, 70, 60 ]
    //
    // In this case, tweet 80 would be returned from both the realtime and full archive clusters.
    EarlybirdRequestContetwittert archiveRequestContetwittert =
        EarlybirdRequestContetwittert.copyRequestContetwittert(
            requestContetwittert,
            QueryUtil.addOrReplaceMatwitterIdFilter(
                requestContetwittert.getParsedQuery(),
                minId - 1));

    if (requestContetwittert.getRequest().isSetAdjustedFullArchiveRequestParams()) {
      adjustRequestParams(archiveRequestContetwittert.getRequest(),
                          requestContetwittert.getRequest().getAdjustedFullArchiveRequestParams());
    }

    LOG.debug("Request sent to the full archive cluster: {},", archiveRequestContetwittert.getRequest());
    return toEarlybirdServiceResponseFuture(
        savedRequestResponses,
        archiveRequestContetwittert,
        "archive",
        this.fullArchive
    );
  }

  private long getMinSearchedId(EarlybirdResponse response) {
    return response != null && response.isSetSearchResults()
        ? response.getSearchResults().getMinSearchedStatusID() : 0;
  }

  private void adjustRequestParams(EarlybirdRequest request,
                                   AdjustedRequestParams adjustedRequestParams) {
    ThriftSearchQuery searchQuery = request.getSearchQuery();

    if (adjustedRequestParams.isSetNumResults()) {
      searchQuery.setNumResults(adjustedRequestParams.getNumResults());
      if (searchQuery.isSetCollectorParams()) {
        searchQuery.getCollectorParams().setNumResultsToReturn(
            adjustedRequestParams.getNumResults());
      }
    }

    if (adjustedRequestParams.isSetMatwitterHitsToProcess()) {
      searchQuery.setMatwitterHitsToProcess(adjustedRequestParams.getMatwitterHitsToProcess());
      if (searchQuery.isSetRelevanceOptions()) {
        searchQuery.getRelevanceOptions().setMatwitterHitsToProcess(
            adjustedRequestParams.getMatwitterHitsToProcess());
      }
      if (searchQuery.isSetCollectorParams()
          && searchQuery.getCollectorParams().isSetTerminationParams()) {
        searchQuery.getCollectorParams().getTerminationParams().setMatwitterHitsToProcess(
            adjustedRequestParams.getMatwitterHitsToProcess());
      }
    }

    if (adjustedRequestParams.isSetReturnAllResults()) {
      if (searchQuery.isSetRelevanceOptions()) {
        searchQuery.getRelevanceOptions().setReturnAllResults(
            adjustedRequestParams.isReturnAllResults());
      }
    }
  }

  private Future<EarlybirdServiceResponse> toEarlybirdServiceResponseFuture(
      List<RequestResponse> savedRequestResponses,
      EarlybirdRequestContetwittert requestContetwittert,
      String sentTo,
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> service) {
    Future<EarlybirdResponse> responseFuture = service.apply(requestContetwittert);
    this.saveRequestResponse(
        savedRequestResponses, sentTo, requestContetwittert, responseFuture
    );

    return responseFuture.map(new Function<EarlybirdResponse, EarlybirdServiceResponse>() {
      @Override
      public EarlybirdServiceResponse apply(EarlybirdResponse response) {
        return EarlybirdServiceResponse.serviceCalled(response);
      }
    });
  }
}
