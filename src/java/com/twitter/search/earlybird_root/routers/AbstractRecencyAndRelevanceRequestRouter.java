package com.tw ter.search.earlyb rd_root.routers;

 mport java.ut l.ArrayL st;
 mport java.ut l.Collect ons;
 mport java.ut l.L st;

 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.futures.Futures;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponse rgeUt l;
 mport com.tw ter.search.earlyb rd.thr ft.AdjustedRequestParams;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRank ngMode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.common.Cl entErrorExcept on;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestUt l;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdServ ceResponse;
 mport com.tw ter.search.earlyb rd_root.f lters.Earlyb rdT  RangeF lter;
 mport com.tw ter.search.earlyb rd_root. rgers.SuperRootResponse rger;
 mport com.tw ter.search.queryparser.ut l.QueryUt l;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Funct on0;
 mport com.tw ter.ut l.Future;

/**
 * For Recency traff c SuperRoot h s realt   and/or protected realt   f rst and t n arch ve
 */
publ c abstract class AbstractRecencyAndRelevanceRequestRouter extends RequestRouter {
  publ c stat c f nal Str ng FULL_ARCH VE_AVA LABLE_FOR_GET_PROTECTED_TWEETS_ONLY_DEC DER_KEY =
      "superroot_full_arch ve_cluster_ava lable_for_get_protected_t ets_only_requests";
  publ c stat c f nal Str ng FULL_ARCH VE_AVA LABLE_FOR_NOT_ENOUGH_PROTECTED_RESULTS_DEC DER_KEY =
      "superroot_full_arch ve_cluster_ava lable_for_requests_w hout_enough_protected_results";

  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(AbstractRecencyAndRelevanceRequestRouter.class);

  pr vate f nal Str ng sk pProtectedClusterDec derKey;
  pr vate f nal Str ng sk pFullArch veClusterDec derKey;

  pr vate f nal SearchCounter realt  Response nval dCounter;
  pr vate f nal SearchCounter realt  ResponseSearchResultsNotSetCounter;
  pr vate f nal SearchCounter m nSearc dStatus dLargerThanRequestMax dCounter;
  pr vate f nal SearchCounter m nSearc dStatus dLargerThanRequestUnt lT  Counter;

  pr vate f nal Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> realt  ;
  pr vate f nal Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> protectedRealt  ;
  pr vate f nal Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> fullArch ve;
  pr vate f nal SuperRootResponse rger response rger;
  pr vate f nal SearchDec der dec der;

  AbstractRecencyAndRelevanceRequestRouter(
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> realt  ,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> protectedRealt  ,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> fullArch ve,
      Earlyb rdT  RangeF lter realt  T  RangeF lter,
      Earlyb rdT  RangeF lter protectedT  RangeF lter,
      Earlyb rdT  RangeF lter fullArch veT  RangeF lter,
      Thr ftSearchRank ngMode rank ngMode,
      Clock clock,
      SearchDec der dec der,
      Earlyb rdFeatureSc ma rger featureSc ma rger) {
    LOG. nfo(" nstant at ng AbstractRecencyAndRelevanceRequestRouter");
    t .realt   = realt  T  RangeF lter.andT n(realt  );
    t .protectedRealt   = protectedT  RangeF lter.andT n(protectedRealt  );
    t .fullArch ve = fullArch veT  RangeF lter.andT n(fullArch ve);
    t .response rger = new SuperRootResponse rger(rank ngMode, featureSc ma rger, clock);
    t .dec der = dec der;

    Str ng rank ngModeForStats = rank ngMode.na ().toLo rCase();
    sk pProtectedClusterDec derKey =
        Str ng.format("superroot_sk p_protected_cluster_for_%s_requests", rank ngModeForStats);
    sk pFullArch veClusterDec derKey =
        Str ng.format("superroot_sk p_full_arch ve_cluster_for_%s_requests", rank ngModeForStats);

    realt  Response nval dCounter =
        SearchCounter.export(rank ngModeForStats + "_realt  _response_ nval d");
    realt  ResponseSearchResultsNotSetCounter =
        SearchCounter.export(rank ngModeForStats + "_realt  _response_search_results_not_set");
    m nSearc dStatus dLargerThanRequestMax dCounter = SearchCounter.export(
        rank ngModeForStats + "_m n_searc d_status_ d_larger_than_request_max_ d");
    m nSearc dStatus dLargerThanRequestUnt lT  Counter = SearchCounter.export(
        rank ngModeForStats + "_m n_searc d_status_ d_larger_than_request_unt l_t  ");
  }

  pr vate vo d c ckRequestPrecond  ons(Earlyb rdRequest request) {
    // CollectorParams should be set  n Earlyb rdRequestUt l.c ckAndSetCollectorParams().
    Precond  ons.c ckNotNull(request.getSearchQuery().getCollectorParams());

    // return a Cl ent error  f t  num results are less than 0
     f (request.getSearchQuery().getNumResults() < 0) {
      throw new Cl entErrorExcept on("T  request.searchQuery.numResults f eld can't be negat ve");
    }

     f (request.getSearchQuery().getCollectorParams().getNumResultsToReturn() < 0) {
      throw new Cl entErrorExcept on("T  request.searchQuery.collectorParams.numResultsToReturn "
          + "f eld can't be negat ve");
    }
  }

  /**
   * H  realt   and/or protected realt   f rst,  f not enough results, t n h  arch ve,
   *  rge t  results.
   */
  @Overr de
  publ c Future<Earlyb rdResponse> route(f nal Earlyb rdRequestContext requestContext) {
    Earlyb rdRequest request = requestContext.getRequest();

    t .c ckRequestPrecond  ons(request);

    ArrayL st<RequestResponse> savedRequestResponses = new ArrayL st<>();

    //  f cl ents do not def ne numResults to return or t  numResults requested are 0
    // return an empty EarlyB rdResponse w hout h t ng any serv ce.
     f (request.getSearchQuery().getNumResults() == 0
        || request.getSearchQuery().getCollectorParams().getNumResultsToReturn() == 0) {
      return Future.value(successNoResultsResponse());
    }

    // Realt   earlyb rd response  s already requ red. Even  f t  serv ce  s not called
    // t  result passed to t   rgers should be a val d one.
    Earlyb rdServ ceResponse.Serv ceState realt  Serv ceState =
        getRealt  Serv ceState(requestContext);
    f nal Future<Earlyb rdServ ceResponse> realt  ResponseFuture =
        realt  Serv ceState.serv ceWasCalled()
            ? getRealt  Response(savedRequestResponses, requestContext)
            : Future.value(Earlyb rdServ ceResponse.serv ceNotCalled(realt  Serv ceState));

    //  f no flock response (follo dUser ds)  s set, request wont be sent to protected.
    Earlyb rdServ ceResponse.Serv ceState protectedServ ceState =
        getProtectedServ ceState(requestContext);
    f nal Future<Earlyb rdServ ceResponse> protectedResponseFuture =
        protectedServ ceState.serv ceWasCalled()
            ? getProtectedResponse(savedRequestResponses, requestContext)
            : Future.value(Earlyb rdServ ceResponse.serv ceNotCalled(protectedServ ceState));

    f nal Future<Earlyb rdServ ceResponse> arch veResponseFuture =
        Futures.flatMap(realt  ResponseFuture, protectedResponseFuture,
            new Funct on0<Future<Earlyb rdServ ceResponse>>() {
              @Overr de
              publ c Future<Earlyb rdServ ceResponse> apply() {
                Earlyb rdServ ceResponse realt  Response = Futures.get(realt  ResponseFuture);
                Earlyb rdServ ceResponse protectedResponse = Futures.get(protectedResponseFuture);
                Earlyb rdServ ceResponse.Serv ceState fullArch veServ ceState =
                    getFullArch veServ ceState(requestContext, realt  Response, protectedResponse);
                return fullArch veServ ceState.serv ceWasCalled()
                    ? getFullArch veResponse(savedRequestResponses, requestContext,
                    realt  Response.getResponse(), protectedResponse.getResponse())
                    : Future.value(
                        Earlyb rdServ ceResponse.serv ceNotCalled(fullArch veServ ceState));
              }
            }
        );

    Future<Earlyb rdResponse>  rgedResponse = response rger. rgeResponseFutures(
        requestContext, realt  ResponseFuture, protectedResponseFuture, arch veResponseFuture);
     rgedResponse =  rgedResponse
        .map(RequestRouterUt l.c ckM nSearc dStatus d(
                 requestContext,
                 "max_ d",
                 Earlyb rdRequestUt l.getRequestMax d(requestContext.getParsedQuery()),
                 realt  ResponseFuture,
                 protectedResponseFuture,
                 arch veResponseFuture,
                 m nSearc dStatus dLargerThanRequestMax dCounter))
        .map(RequestRouterUt l.c ckM nSearc dStatus d(
                 requestContext,
                 "unt l_t  ",
                 Earlyb rdRequestUt l.getRequestMax dFromUnt lT  (requestContext.getParsedQuery()),
                 realt  ResponseFuture,
                 protectedResponseFuture,
                 arch veResponseFuture,
                 m nSearc dStatus dLargerThanRequestUnt lT  Counter));

    return t .maybeAttachSentRequestsToDebug nfo(
        savedRequestResponses,
        requestContext,
         rgedResponse
    );
  }

  pr vate Earlyb rdResponse successNoResultsResponse() {
    return new Earlyb rdResponse(Earlyb rdResponseCode.SUCCESS, 0)
        .setSearchResults(new Thr ftSearchResults().setResults(Collect ons.emptyL st()));
  }

  protected abstract boolean shouldSendRequestToFullArch veCluster(
      Earlyb rdRequest request, Earlyb rdResponse realt  Response);

  /** Determ nes  f t  protected serv ce  s ava lable and  f a request should be sent to  . */
  pr vate Earlyb rdServ ceResponse.Serv ceState getProtectedServ ceState(
      Earlyb rdRequestContext requestContext) {
     f (!requestContext.getRequest(). sSetFollo dUser ds()
        || requestContext.getRequest().getFollo dUser ds(). sEmpty()) {
      return Earlyb rdServ ceResponse.Serv ceState.SERV CE_NOT_REQUESTED;
    }

     f (dec der. sAva lable(sk pProtectedClusterDec derKey)) {
      return Earlyb rdServ ceResponse.Serv ceState.SERV CE_NOT_AVA LABLE;
    }

    return Earlyb rdServ ceResponse.Serv ceState.SERV CE_CALLED;
  }

  /** Determ nes  f t  realt   serv ce  s ava lable and  f a request should be sent to  . */
  pr vate Earlyb rdServ ceResponse.Serv ceState getRealt  Serv ceState(
      Earlyb rdRequestContext requestContext) {
    Earlyb rdRequest request = requestContext.getRequest();

    // SERV CE_NOT_REQUESTED should always be returned before ot r states as
    // SuperRootResponse rger has spec al log c for t  case.
     f (request. sSetGetProtectedT etsOnly() && request. sGetProtectedT etsOnly()) {
      return Earlyb rdServ ceResponse.Serv ceState.SERV CE_NOT_REQUESTED;
    }

    return Earlyb rdServ ceResponse.Serv ceState.SERV CE_CALLED;
  }

  /** Determ nes  f t  full arch ve serv ce  s ava lable and  f a request should be sent to  . */
  pr vate Earlyb rdServ ceResponse.Serv ceState getFullArch veServ ceState(
      Earlyb rdRequestContext requestContext,
      Earlyb rdServ ceResponse publ cServ ceResponse,
      Earlyb rdServ ceResponse protectedServ ceResponse) {

    // SERV CE_NOT_REQUESTED should be always be returned before ot r states as
    // SuperRootResponse rger has spec al log c for t  case.
     f (!requestContext.getRequest(). sSetGetOlderResults()
        || !requestContext.getRequest(). sGetOlderResults()) {
      return Earlyb rdServ ceResponse.Serv ceState.SERV CE_NOT_REQUESTED;
    }

    // allow request ng full arch ve serv ce w n dec der  s enabled
     f (!dec der. sAva lable(FULL_ARCH VE_AVA LABLE_FOR_GET_PROTECTED_TWEETS_ONLY_DEC DER_KEY)
        && requestContext.getRequest(). sSetGetProtectedT etsOnly()
        && requestContext.getRequest(). sGetProtectedT etsOnly()) {
      return Earlyb rdServ ceResponse.Serv ceState.SERV CE_NOT_REQUESTED;
    }

     f (dec der. sAva lable(sk pFullArch veClusterDec derKey)) {
      return Earlyb rdServ ceResponse.Serv ceState.SERV CE_NOT_AVA LABLE;
    }

    boolean serv ceWasCalledForPubl c =
        getFullArch veServ ceState(requestContext, publ cServ ceResponse).serv ceWasCalled();
    boolean serv ceWasCalledForProtected =
        dec der. sAva lable(FULL_ARCH VE_AVA LABLE_FOR_NOT_ENOUGH_PROTECTED_RESULTS_DEC DER_KEY)
        && getFullArch veServ ceState(requestContext, protectedServ ceResponse).serv ceWasCalled();
     f (!serv ceWasCalledForPubl c && !serv ceWasCalledForProtected) {
      return Earlyb rdServ ceResponse.Serv ceState.SERV CE_NOT_CALLED;
    }

    return Earlyb rdServ ceResponse.Serv ceState.SERV CE_CALLED;
  }

  pr vate Earlyb rdServ ceResponse.Serv ceState getFullArch veServ ceState(
      Earlyb rdRequestContext requestContext,
      Earlyb rdServ ceResponse realt  Serv ceResponse) {
    Earlyb rdResponse realt  Response = realt  Serv ceResponse.getResponse();

     f (!Earlyb rdResponse rgeUt l. sVal dResponse(realt  Response)) {
      realt  Response nval dCounter. ncre nt();
      return Earlyb rdServ ceResponse.Serv ceState.SERV CE_NOT_CALLED;
    }

     f (!realt  Response. sSetSearchResults()) {
      realt  ResponseSearchResultsNotSetCounter. ncre nt();
      return Earlyb rdServ ceResponse.Serv ceState.SERV CE_NOT_CALLED;
    }

     f (!shouldSendRequestToFullArch veCluster(requestContext.getRequest(), realt  Response)) {
      return Earlyb rdServ ceResponse.Serv ceState.SERV CE_NOT_CALLED;
    }

    return Earlyb rdServ ceResponse.Serv ceState.SERV CE_CALLED;
  }

  /**
   * Mod fy t  or g nal request context based on t  follo dUser d f eld and t n send t 
   * request to t  protected cluster.
   */
  pr vate Future<Earlyb rdServ ceResponse> getProtectedResponse(
      ArrayL st<RequestResponse> savedRequestResponses,
      f nal Earlyb rdRequestContext requestContext) {
    Earlyb rdRequestContext protectedRequestContext =
        Earlyb rdRequestContext.newContextW hRestr ctFromUser dF lter64(requestContext);
    Precond  ons.c ckArgu nt(
        protectedRequestContext.getRequest().getSearchQuery(). sSetFromUser DF lter64());

    // SERV CE_NOT_REQUESTED should be always be returned before ot r states as
    // SuperRootResponse rger has spec al log c for t  case.
     f (protectedRequestContext.getRequest().getSearchQuery().getFromUser DF lter64(). sEmpty()) {
      return Future.value(Earlyb rdServ ceResponse.serv ceNotCalled(
          Earlyb rdServ ceResponse.Serv ceState.SERV CE_NOT_REQUESTED));
    }

     f (requestContext.getRequest(). sSetAdjustedProtectedRequestParams()) {
      adjustRequestParams(protectedRequestContext.getRequest(),
                          requestContext.getRequest().getAdjustedProtectedRequestParams());
    }

    LOG.debug("Request sent to t  protected cluster: {}", protectedRequestContext.getRequest());
    return toEarlyb rdServ ceResponseFuture(
        savedRequestResponses,
        protectedRequestContext,
        "protected",
        t .protectedRealt  
    );
  }

  pr vate Future<Earlyb rdServ ceResponse> getRealt  Response(
      ArrayL st<RequestResponse> savedRequestResponses,
      Earlyb rdRequestContext requestContext) {
    return toEarlyb rdServ ceResponseFuture(
        savedRequestResponses,
        requestContext,
        "realt  ",
        t .realt  );
  }

  /**
   * Mod fy ng t  ex st ng max  d f lter of t  request or append ng a new
   * max  d f lter and t n send t  request to t  full arch ve cluster.
   */
  pr vate Future<Earlyb rdServ ceResponse> getFullArch veResponse(
      ArrayL st<RequestResponse> savedRequestResponses,
      Earlyb rdRequestContext requestContext,
      Earlyb rdResponse realt  Response,
      Earlyb rdResponse protectedResponse) {
    long realt  M n d = getM nSearc d d(realt  Response);
    long protectedM n d = getM nSearc d d(protectedResponse);
    //  f both realt   and protected m n searc d  ds are ava lable, t  larger(ne r) one  s used
    // to make sure no t ets are left out. Ho ver, t   ans   m ght  ntroduce dupl cates for
    // t  ot r response. T  response  rger w ll dedup t  response. T  log c  s enabled
    // w n full arch ve cluster  s ava lable for requests w hout enough protected results.
    long m n d =
        dec der. sAva lable(FULL_ARCH VE_AVA LABLE_FOR_NOT_ENOUGH_PROTECTED_RESULTS_DEC DER_KEY)
            ? Math.max(realt  M n d, protectedM n d) : realt  M n d;

     f (m n d <= 0) {
      //  f t  realt   response doesn't have a m nSearc dStatus D set, get all results from
      // t  full arch ve cluster.
      m n d = Long.MAX_VALUE;
    }

    // T  [max_ d] operator  s  nclus ve  n earlyb rds. T   ans that a query w h [max_ d X]
    // w ll return t et X,  f X matc s t  rest of t  query. So   should add a [max_ d (X - 1)]
    // operator to t  full arch ve query ( nstead of [max_ d X]). Ot rw se,   could end up w h
    // dupl cates. For example:
    //
    //  realt   response: results = [ 100, 90, 80 ], m nSearc dStatus D = 80
    //  full arch ve request: [max_ d 80]
    //  full arch ve response: results = [ 80, 70, 60 ]
    //
    //  n t  case, t et 80 would be returned from both t  realt   and full arch ve clusters.
    Earlyb rdRequestContext arch veRequestContext =
        Earlyb rdRequestContext.copyRequestContext(
            requestContext,
            QueryUt l.addOrReplaceMax dF lter(
                requestContext.getParsedQuery(),
                m n d - 1));

     f (requestContext.getRequest(). sSetAdjustedFullArch veRequestParams()) {
      adjustRequestParams(arch veRequestContext.getRequest(),
                          requestContext.getRequest().getAdjustedFullArch veRequestParams());
    }

    LOG.debug("Request sent to t  full arch ve cluster: {},", arch veRequestContext.getRequest());
    return toEarlyb rdServ ceResponseFuture(
        savedRequestResponses,
        arch veRequestContext,
        "arch ve",
        t .fullArch ve
    );
  }

  pr vate long getM nSearc d d(Earlyb rdResponse response) {
    return response != null && response. sSetSearchResults()
        ? response.getSearchResults().getM nSearc dStatus D() : 0;
  }

  pr vate vo d adjustRequestParams(Earlyb rdRequest request,
                                   AdjustedRequestParams adjustedRequestParams) {
    Thr ftSearchQuery searchQuery = request.getSearchQuery();

     f (adjustedRequestParams. sSetNumResults()) {
      searchQuery.setNumResults(adjustedRequestParams.getNumResults());
       f (searchQuery. sSetCollectorParams()) {
        searchQuery.getCollectorParams().setNumResultsToReturn(
            adjustedRequestParams.getNumResults());
      }
    }

     f (adjustedRequestParams. sSetMaxH sToProcess()) {
      searchQuery.setMaxH sToProcess(adjustedRequestParams.getMaxH sToProcess());
       f (searchQuery. sSetRelevanceOpt ons()) {
        searchQuery.getRelevanceOpt ons().setMaxH sToProcess(
            adjustedRequestParams.getMaxH sToProcess());
      }
       f (searchQuery. sSetCollectorParams()
          && searchQuery.getCollectorParams(). sSetTerm nat onParams()) {
        searchQuery.getCollectorParams().getTerm nat onParams().setMaxH sToProcess(
            adjustedRequestParams.getMaxH sToProcess());
      }
    }

     f (adjustedRequestParams. sSetReturnAllResults()) {
       f (searchQuery. sSetRelevanceOpt ons()) {
        searchQuery.getRelevanceOpt ons().setReturnAllResults(
            adjustedRequestParams. sReturnAllResults());
      }
    }
  }

  pr vate Future<Earlyb rdServ ceResponse> toEarlyb rdServ ceResponseFuture(
      L st<RequestResponse> savedRequestResponses,
      Earlyb rdRequestContext requestContext,
      Str ng sentTo,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    Future<Earlyb rdResponse> responseFuture = serv ce.apply(requestContext);
    t .saveRequestResponse(
        savedRequestResponses, sentTo, requestContext, responseFuture
    );

    return responseFuture.map(new Funct on<Earlyb rdResponse, Earlyb rdServ ceResponse>() {
      @Overr de
      publ c Earlyb rdServ ceResponse apply(Earlyb rdResponse response) {
        return Earlyb rdServ ceResponse.serv ceCalled(response);
      }
    });
  }
}
