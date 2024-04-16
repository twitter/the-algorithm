package com.tw ter.search.earlyb rd_root. rgers;

 mport java.ut l.Collect ons;
 mport java.ut l.HashSet;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport scala.runt  .BoxedUn ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Opt onal;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;
 mport com.google.common.collect.Sets;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.ut l.F nagleUt l;
 mport com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponse rgeUt l;
 mport com.tw ter.search.common.ut l.earlyb rd.ResultsUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdDebug nfo;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.collectors.Mult way rgeCollector;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestUt l;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

/**
 * Base Earlyb rdResponse rger conta n ng bas c log c to  rge Earlyb rdResponse objects
 */
publ c abstract class Earlyb rdResponse rger  mple nts EarlyTerm nateT er rgePred cate {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdResponse rger.class);
  pr vate stat c f nal Logger M N_SEARCHED_STATUS_ D_LOGGER =
      LoggerFactory.getLogger("M nSearc dStatus dLogger");

  pr vate stat c f nal SearchCounter NO_SEARCH_RESULT_COUNTER =
      SearchCounter.export("no_search_result_count");
  pr vate stat c f nal SearchCounter NO_RESPONSES_TO_MERGE =
      SearchCounter.export("no_responses_to_ rge");
  pr vate stat c f nal SearchCounter EARLYB RD_RESPONSE_NO_MORE_RESULTS =
      SearchCounter.export(" rger_earlyb rd_response_no_more_results");
  pr vate stat c f nal Str ng PART T ON_OR_T ER_COUNTER_NAME_FORMAT =
      " rger_wa ed_for_response_from_%s_counter";
  pr vate stat c f nal Str ng PART T ON_OR_T ER_ERROR_COUNTER_NAME_FORMAT =
      " rger_num_error_responses_from_%s";
  pr vate stat c f nal Str ng PART T ON_OR_T ER_RESPONSE_CODE_COUNTER_NAME_FORMAT =
      " rger_earlyb rd_response_code_from_%s_%s";

  protected f nal Earlyb rdResponseDebug ssageBu lder response ssageBu lder;
  protected f nal Earlyb rdRequestContext requestContext;
  protected f nal  mmutableL st<Future<Earlyb rdResponse>> responses;
  protected AccumulatedResponses accumulatedResponses;


  @V s bleForTest ng
  stat c f nal Map<Earlyb rdRequestType, SearchCounter> MERGER_CREATED_STATS =
      perRequestTypeCounter mmutableMap("earlyb rd_response_ rger_%s_created_count");

  @V s bleForTest ng
  stat c f nal Map<Earlyb rdRequestType, SearchCounter>
    M N_SEARCHED_STATUS_ D_LARGER_THAN_REQUEST_MAX_ D = perRequestTypeCounter mmutableMap(
        " rger_%s_m n_searc d_status_ d_larger_than_request_max_ d");

  @V s bleForTest ng
  stat c f nal Map<Earlyb rdRequestType, SearchCounter>
    M N_SEARCHED_STATUS_ D_LARGER_THAN_REQUEST_UNT L_T ME = perRequestTypeCounter mmutableMap(
        " rger_%s_m n_searc d_status_ d_larger_than_request_unt l_t  ");

  pr vate stat c Map<Earlyb rdRequestType, SearchCounter> perRequestTypeCounter mmutableMap(
      Str ng statPattern) {
    Map<Earlyb rdRequestType, SearchCounter> statsMap = Maps.newEnumMap(Earlyb rdRequestType.class);
    for (Earlyb rdRequestType earlyb rdRequestType : Earlyb rdRequestType.values()) {
      Str ng statNa  = Str ng.format(statPattern, earlyb rdRequestType.getNormal zedNa ());
      statsMap.put(earlyb rdRequestType, SearchCounter.export(statNa ));
    }

    return Maps. mmutableEnumMap(statsMap);
  }

  publ c stat c f nal com.google.common.base.Funct on<Earlyb rdResponse, Map<Long,  nteger>>
    H T_COUNT_GETTER =
      response -> response.getSearchResults() == null
        ? null
        : response.getSearchResults().getH Counts();

  pr vate f nal Cha n rger cha n rger;

  pr vate class Cha n rger {
    pr vate f nal Earlyb rdRequestContext requestContext;
    pr vate f nal ResponseAccumulator responseAccumulator;
    pr vate f nal L st<Future<Earlyb rdResponse>> responses;
    pr vate f nal Earlyb rdResponseDebug ssageBu lder response ssageBu lder;
    pr vate  nt currentFuture ndex = -1;

    publ c Cha n rger(Earlyb rdRequestContext requestContext,
                       ResponseAccumulator responseAccumulator,
                       L st<Future<Earlyb rdResponse>> responses,
                       Earlyb rdResponseDebug ssageBu lder response ssageBu lder) {
      t .requestContext = requestContext;
      t .responseAccumulator = responseAccumulator;
      t .responses = responses;
      t .response ssageBu lder = response ssageBu lder;
    }

    publ c Future<Earlyb rdResponse>  rge() {
      // 'responseFutures' should always be sorted.
      // W n returned by Earlyb rdScatterGat r serv ce, t  responses are sorted by part  on  D.
      // W n returned by Earlyb rdCha nedScatterGat rServ ce,
      // responses are sorted descend ng by t er start date. See:
      // com.tw ter.search.earlyb rd_root.Earlyb rdCha nedScatterGat rServ ce.T ER_COMPARATOR.
      //
      // W n  rg ng responses from part  ons,   want to wa  for responses from all part  ons,
      // so t  order  n wh ch   wa  for those results does not matter. W n  rg ng responses
      // from t ers,   want to wa  for t  response from t  latest.  f   don't need any more
      // responses to compute t  f nal response, t n   don't need to wa  for t  responses from
      // ot r t ers.  f   cannot term nate early, t n   want to wa  for t  responses from t 
      // second t er, and so on.
      //
      //   do not need to have any expl c  synchron zat on, because:
      //   1. T  callbacks for future_  are set by t  flatMap() callback on future_{ -1} (w n
      //      recurs vely call ng  rge()  ns de t  flatMap()).
      //   2. Before sett ng t  callbacks on future_ , future_{ -1}.flatMap() adds t  response
      //      results to  rge lper.
      //   3. W n t  callbacks on future_  are set, t   mory barr er bet en
      //      thread_runn ng_future_{ -1} and thread_runn ng_future_   s crossed. T  guarantees
      //      that thread_runn ng_future_  w ll see t  updates to  rge lper before   sees t 
      //      callbacks. (Or thread_runn ng_future_{ -1} == thread_runn ng_future_ ,  n wh ch case
      //      synchron zat on  s not an  ssue, and correctness  s guarateed by t  order  n wh ch
      //      th ngs w ll run.)
      //   4. T  sa  reason ng appl es to currentFuture ndex.

      ++currentFuture ndex;
       f (currentFuture ndex >= responses.s ze()) {
        return Future.value(getT  d rgedResponse(responseAccumulator.getAccumulatedResults()));
      }

      f nal Str ng part  onT erNa  =
          responseAccumulator.getNa ForLogg ng(currentFuture ndex, responses.s ze());
      f nal Str ng na ForEarlyb rdResponseCodeStats =
          responseAccumulator.getNa ForEarlyb rdResponseCodeStats(
              currentFuture ndex, responses.s ze());

      //  f a t er  n t  cha n throws an except on, convert   to a null response, and let t 
      //  rge lper handle   appropr ately.
      return responses.get(currentFuture ndex)
        .handle(Funct on.func(t -> {
           f (F nagleUt l. sCancelExcept on(t)) {
            return new Earlyb rdResponse()
                .setResponseCode(Earlyb rdResponseCode.CL ENT_CANCEL_ERROR);
          } else  f (F nagleUt l. sT  outExcept on(t)) {
            return new Earlyb rdResponse()
                .setResponseCode(Earlyb rdResponseCode.SERVER_T MEOUT_ERROR);
          } else {
            SearchCounter.export(
                Str ng.format(PART T ON_OR_T ER_ERROR_COUNTER_NAME_FORMAT, part  onT erNa ))
                . ncre nt();
             f (response ssageBu lder. sDebugMode()) {
              response ssageBu lder.debugAndLogWarn ng(
                  Str ng.format("[%s] fa led, except on [%s]",
                      part  onT erNa , t.toStr ng()));
            }
            LOG.warn("except on response from: " + part  onT erNa , t);
            return new Earlyb rdResponse()
                .setResponseCode(Earlyb rdResponseCode.TRANS ENT_ERROR);
          }
        }))
        .flatMap(Funct on.func(response -> {
          Precond  ons.c ckNotNull(response);

          SearchCounter.export(
              Str ng.format(PART T ON_OR_T ER_RESPONSE_CODE_COUNTER_NAME_FORMAT,
                            na ForEarlyb rdResponseCodeStats,
                            response.getResponseCode().na ().toLo rCase()))
              . ncre nt();

           f ((response.getResponseCode() != Earlyb rdResponseCode.PART T ON_SK PPED)
              && (response.getResponseCode() != Earlyb rdResponseCode.T ER_SK PPED)) {
            SearchCounter.export(
                Str ng.format(PART T ON_OR_T ER_COUNTER_NAME_FORMAT, part  onT erNa ))
              . ncre nt();
          }

           f (response.getResponseCode() == Earlyb rdResponseCode.CL ENT_CANCEL_ERROR) {
            // t  request has been cancelled, no need to proceed
            return Future.value(response);
          }

          rewr eResponseCode fSearchResultsM ss ng(requestContext, part  onT erNa , response);
          response ssageBu lder.logResponseDebug nfo(
              requestContext.getRequest(),
              part  onT erNa ,
              response);
          responseAccumulator.addResponse(
              response ssageBu lder,
              requestContext.getRequest(),
              response);

           f (responseAccumulator.shouldEarlyTerm nate rge(Earlyb rdResponse rger.t )) {
            return Future.value(getT  d rgedResponse(
                responseAccumulator.getAccumulatedResults()));
          }
          return  rge();
        }));
    }
  }

  pr vate vo d rewr eResponseCode fSearchResultsM ss ng(
      Earlyb rdRequestContext earlyb rdRequestContext,
      Str ng part  onT erNa ,
      Earlyb rdResponse response) {
    //   always requ re searchResults to be set, even for term stats and facet requests.
    // T   s because searchResults conta ns  mportant  nfo such as pag nat on cursors
    // l ke m nSearchStatus d and m nSearc dT  S nceEpoch.
    //   expect all successful responses to have searchResults set.
     f (response. sSetResponseCode()
        && response.getResponseCode() == Earlyb rdResponseCode.SUCCESS
        && response.getSearchResults() == null) {
      NO_SEARCH_RESULT_COUNTER. ncre nt();
      LOG.warn("Rece ved Earlyb rd response w h null searchResults from [{}]"
               + " Earlyb rdRequest [{}] Earlyb rdResponse [{}] ",
               part  onT erNa , earlyb rdRequestContext.getRequest(), response);
      response.setResponseCode(Earlyb rdResponseCode.TRANS ENT_ERROR);
    }
  }

  /**
   * Construct a Earlyb rdResponse rger to  rge responses from mult ple part  ons or t ers
   * based on mode.
   */
  Earlyb rdResponse rger(Earlyb rdRequestContext requestContext,
                          L st<Future<Earlyb rdResponse>> responses,
                          ResponseAccumulator responseAccumulator) {
    t .requestContext = requestContext;
    t .responses =  mmutableL st.copyOf(responses);
    t .response ssageBu lder =
        new Earlyb rdResponseDebug ssageBu lder(requestContext.getRequest());
    t .cha n rger = new Cha n rger(requestContext, responseAccumulator, responses,
        response ssageBu lder);
  }

  /**
   * Get a response  rger to  rge t  g ven responses.
   */
  publ c stat c Earlyb rdResponse rger getResponse rger(
      Earlyb rdRequestContext requestContext,
      L st<Future<Earlyb rdResponse>> responses,
      ResponseAccumulator  lper,
      Earlyb rdCluster cluster,
      Earlyb rdFeatureSc ma rger featureSc ma rger,
       nt numPart  ons) {
    Earlyb rdRequestType type = requestContext.getEarlyb rdRequestType();
    MERGER_CREATED_STATS.get(type). ncre nt();
    sw ch (type) {
      case FACETS:
        return new FacetResponse rger(requestContext, responses,  lper);
      case TERM_STATS:
        return new TermStat st csResponse rger(requestContext, responses,  lper);
      case RECENCY:
        return new RecencyResponse rger(requestContext, responses,  lper, featureSc ma rger);
      case STR CT_RECENCY:
        return new Str ctRecencyResponse rger(
            requestContext, responses,  lper, featureSc ma rger, cluster);
      case RELEVANCE:
        return new RelevanceResponse rger(
            requestContext, responses,  lper, featureSc ma rger, numPart  ons);
      case TOP_TWEETS:
        return new TopT etsResponse rger(requestContext, responses,  lper);
      default:
        throw new Runt  Except on("Earlyb rdRequestType " + type + " s not supported by  rge");
    }
  }

  /**
   * T   thod can perform two types of  rges:
   *   1.  rge responses w h n a t er from d fferent part  ons.
   *   2.  rge responses from mult ple t ers.
   */
  publ c f nal Future<Earlyb rdResponse>  rge() {
    return cha n rger. rge()
        .onSuccess(c ckM nSearc dStatus dFunct on(
                 "max_ d",
                 Earlyb rdRequestUt l.getRequestMax d(requestContext.getParsedQuery()),
                 M N_SEARCHED_STATUS_ D_LARGER_THAN_REQUEST_MAX_ D.get(
                     requestContext.getEarlyb rdRequestType())))
        .onSuccess(c ckM nSearc dStatus dFunct on(
                 "unt l_t  ",
                 Earlyb rdRequestUt l.getRequestMax dFromUnt lT  (requestContext.getParsedQuery()),
                 M N_SEARCHED_STATUS_ D_LARGER_THAN_REQUEST_UNT L_T ME.get(
                     requestContext.getEarlyb rdRequestType())));
  }

  /**
   * Returns t  funct on that c cks  f t  m nSearc dStatus D on t   rged response  s h g r
   * than t  max  D  n t  request.
   */
  pr vate Funct on<Earlyb rdResponse, BoxedUn > c ckM nSearc dStatus dFunct on(
      f nal Str ng operator, f nal Opt onal<Long> requestMax d, f nal SearchCounter stat) {
    return Funct on.cons( rgedResponse -> {
       f (requestMax d. sPresent()
          && requestMax d.get() != Long.MAX_VALUE
          && ( rgedResponse.getResponseCode() == Earlyb rdResponseCode.SUCCESS)
          &&  rgedResponse. sSetSearchResults()
          &&  rgedResponse.getSearchResults(). sSetM nSearc dStatus D()) {
        long m nSearc dStatus d =  rgedResponse.getSearchResults().getM nSearc dStatus D();
        //   so t  s set m nSearc dStatus d = max_ d + 1 w n a request t  s out even
        // before any search happens.
        // C ck SEARCH-10134 for more deta ls.
         f (m nSearc dStatus d > requestMax d.get() + 1) {
          stat. ncre nt();
          Str ng log ssage = "Response has a m nSearc dStatus D ({}) larger than request "
              + operator + " ({})."
              + "\nrequest type: {}"
              + "\nrequest: {}"
              + "\n rged response: {}"
              + "\nSuccessful accumulated responses:";
          L st<Object> log ssageParams = L sts.newArrayL st();
          log ssageParams.add(m nSearc dStatus d);
          log ssageParams.add(requestMax d.get());
          log ssageParams.add(requestContext.getEarlyb rdRequestType());
          log ssageParams.add(requestContext.getRequest());
          log ssageParams.add( rgedResponse);
          for (Earlyb rdResponse response : accumulatedResponses.getSuccessResponses()) {
            log ssage += "\naccumulated response: {}";
            log ssageParams.add(response);
          }
          M N_SEARCHED_STATUS_ D_LOGGER.warn(log ssage, log ssageParams.toArray());
        }
      }
    });
  }

  pr vate Earlyb rdResponse getT  d rgedResponse(AccumulatedResponses accResponses) {
    long start = System.nanoT  ();
    try {
      return get rgedResponse(accResponses);
    } f nally {
      long totalT   = System.nanoT  () - start;
      get rgedResponseT  r().t  r ncre nt(totalT  );
    }
  }

  pr vate Earlyb rdResponse  n  al ze rgedSuccessResponseFromAccumulatedResponses() {
    Earlyb rdResponse  rgedResponse = new Earlyb rdResponse();

    AccumulatedResponses.Part  onCounts part  onCounts =
        accumulatedResponses.getPart  onCounts();

     rgedResponse.setNumPart  ons(part  onCounts.getNumPart  ons())
        .setNumSuccessfulPart  ons(part  onCounts.getNumSuccessfulPart  ons())
        .setPerT erResponse(part  onCounts.getPerT erResponse())
        .setNumSearc dSeg nts(accumulatedResponses.getNumSearc dSeg nts());

     rgedResponse.setEarlyTerm nat on nfo(accumulatedResponses.get rgedEarlyTerm nat on nfo());
     rgedResponse.setResponseCode(Earlyb rdResponseCode.SUCCESS);

    return  rgedResponse;
  }

  pr vate Earlyb rdResponse get rgedResponse(AccumulatedResponses accResponses) {
    accumulatedResponses = accResponses;
    Earlyb rdResponse  rgedResponse;

     f (accumulatedResponses.getSuccessResponses(). sEmpty()
        && !accumulatedResponses.foundError()) {
      // No successful or error responses. T   ans that all t ers / part  ons are  ntent onally
      // sk pped. Return a blank successful response.
      NO_RESPONSES_TO_MERGE. ncre nt();
       rgedResponse = new Earlyb rdResponse()
          .setResponseCode(Earlyb rdResponseCode.SUCCESS)
          .setSearchResults(new Thr ftSearchResults())
          .setDebugStr ng("No responses to  rge, probably because all t ers/part  ons "
              + " re sk pped.");
    } else  f (accumulatedResponses. s rg ngAcrossT ers()) {
       rgedResponse = get rgedResponseAcrossT ers();
    } else {
       rgedResponse = get rgedResponseAcrossPart  ons();
    }

    save rgedDebugStr ng( rgedResponse);
    return  rgedResponse;
  }

  pr vate Earlyb rdResponse get rgedResponseAcrossT ers() {
    Precond  ons.c ckState(
        !accumulatedResponses.getSuccessResponses(). sEmpty()
            || accumulatedResponses.foundError());

    // W n  rg ng across t ers,  f   have one fa led t er,   should fa l t  whole
    // response. Note that due to early term nat on,  f a t er that  s old fa ls
    // but t  ne r t ers return enough results, t  fa led t er won't show up
    //  re  n accumulatedResponses -- t  only t ers that show up  re
    // w ll be successful.
     f (accumulatedResponses.foundError()) {
      // T  T erResponseAccumulator early term nates on t  f rst error, so   should
      // never get more than one error. T   ans that t  get rgedErrorResponse w ll
      // return an error response w h t  error code of that one error, and w ll never
      // have to dec de wh ch error response to return  f t  error responses are all
      // d fferent.

      // Perhaps   should just return accumulatedResponses.getErrorResponses().get(0);
      Precond  ons.c ckState(accumulatedResponses.getErrorResponses().s ze() == 1);
      return accumulatedResponses.get rgedErrorResponse();
    } else {
      Earlyb rdResponse  rgedResponse =  n  al ze rgedSuccessResponseFromAccumulatedResponses();
      return  nternal rge( rgedResponse);
    }
  }

  pr vate Earlyb rdResponse get rgedResponseAcrossPart  ons() {
    Precond  ons.c ckState(
        !accumulatedResponses.getSuccessResponses(). sEmpty()
            || accumulatedResponses.foundError());

    Earlyb rdResponse  rgedResponse;

    // Unl ke t er  rg ng, one fa led response doesn't  an t   rged response should
    // fa l.  f   have successful responses   can c ck t  success rat o and  f  s
    // good   can st ll return a successful  rge.
     f (!accumulatedResponses.getSuccessResponses(). sEmpty()) {
      //   have at least one successful response, but st ll need to c ck t  success rat o.
      //  rgedResponse  s a SUCCESS response after t  call, but   w ll
      // set   to fa lure below  f necessary.
       rgedResponse =  n  al ze rgedSuccessResponseFromAccumulatedResponses();

       nt numSuccessResponses =  rgedResponse.getNumSuccessfulPart  ons();
       nt numPart  ons =  rgedResponse.getNumPart  ons();
      double successThreshold = getSuccessResponseThreshold();
       f (c ckSuccessPart  onRat o(numSuccessResponses, numPart  ons, successThreshold)) {
        // Success! Proceed w h  rg ng.
         rgedResponse.setResponseCode(Earlyb rdResponseCode.SUCCESS);
         rgedResponse =  nternal rge( rgedResponse);
      } else {
        response ssageBu lder.logBelowSuccessThreshold(
            requestContext.getRequest().getSearchQuery(), numSuccessResponses, numPart  ons,
            successThreshold);
         rgedResponse.setResponseCode(Earlyb rdResponseCode.TOO_MANY_PART T ONS_FA LED_ERROR);
      }
    } else {
       rgedResponse = accumulatedResponses.get rgedErrorResponse();
    }

    return  rgedResponse;
  }

  /**
   * Der ve class should  mple nt t  log c to  rge t  spec f c type of results (recency,
   * relevance, Top T ets, etc..)
   */
  protected abstract Earlyb rdResponse  nternal rge(Earlyb rdResponse response);

  protected abstract SearchT  rStats get rgedResponseT  r();

  /**
   * Do   have enough results so far that   can early term nate and not cont nue onto next t er?
   */
  publ c boolean shouldEarlyTerm nateT er rge( nt totalResultsFromSuccessfulShards,
                                                  boolean foundEarlyTerm nat on) {
    //   are tak ng t  most conservat ve t er response  rg ng.
    // T   s t  most conservat ve  rge log c --- as long as   have so  results,   should
    // not return anyth ng from t  next t er. T  may cause not  deal exper ence w re a
    // page  s not full, but t  use can st ll scroll furt r.

    return foundEarlyTerm nat on || totalResultsFromSuccessfulShards >= 1;
  }

  pr vate vo d save rgedDebugStr ng(Earlyb rdResponse  rgedResponse) {
     f (response ssageBu lder. sDebugMode()) {
      Str ng  ssage = response ssageBu lder.debugStr ng();
       rgedResponse.setDebugStr ng( ssage);
       f (!accumulatedResponses.getSuccessResponses(). sEmpty()
          && accumulatedResponses.getSuccessResponses().get(0). sSetDebug nfo()) {

        Earlyb rdDebug nfo debug nfo =
            accumulatedResponses.getSuccessResponses().get(0).getDebug nfo();
         rgedResponse.setDebug nfo(debug nfo);
      }
    }
  }

  pr vate double getSuccessResponseThreshold() {
    Earlyb rdRequest request = requestContext.getRequest();
     f (request. sSetSuccessfulResponseThreshold()) {
      double successfulResponseThreshold = request.getSuccessfulResponseThreshold();
      Precond  ons.c ckArgu nt(successfulResponseThreshold > 0,
          " nval d successfulResponseThreshold %s", successfulResponseThreshold);
      Precond  ons.c ckArgu nt(successfulResponseThreshold <= 1.0,
          " nval d successfulResponseThreshold %s", successfulResponseThreshold);
      return successfulResponseThreshold;
    } else {
      return getDefaultSuccessResponseThreshold();
    }
  }

  protected abstract double getDefaultSuccessResponseThreshold();

  pr vate stat c boolean c ckSuccessPart  onRat o(
       nt numSuccessResponses,
       nt numPart  ons,
      double goodResponseThreshold) {
    Precond  ons.c ckArgu nt(goodResponseThreshold > 0.0,
        " nval d goodResponseThreshold %s", goodResponseThreshold);
    return numSuccessResponses >= (numPart  ons * goodResponseThreshold);
  }

  /**
   *  rge h  counts from all results.
   */
  protected Map<Long,  nteger> aggregateH CountMap() {
    Map<Long,  nteger> h Counts = ResultsUt l
        .aggregateCountMap(accumulatedResponses.getSuccessResponses(), H T_COUNT_GETTER);
     f (h Counts.s ze() > 0) {
       f (response ssageBu lder. sDebugMode()) {
        response ssageBu lder.append("H  counts:\n");
        for (Map.Entry<Long,  nteger> entry : h Counts.entrySet()) {
          response ssageBu lder.append(Str ng.format("  %10s seconds: %d h s\n",
              entry.getKey() / 1000, entry.getValue()));
        }
      }
      return h Counts;
    }
    return null;
  }

  /**
   * Returns t  number of results to keep as part of  rge-collect on.
   */
  protected f nal  nt computeNumResultsToKeep() {
    return Earlyb rdResponse rgeUt l.computeNumResultsToKeep(requestContext.getRequest());
  }

  /**
   * Remove exact dupl cates (sa   d) from t  result set.
   */
  protected stat c vo d tr mExactDups(Thr ftSearchResults searchResults, Tr mStats tr mStats) {
     nt numResults = searchResults.getResultsS ze();
    L st<Thr ftSearchResult> oldResults = searchResults.getResults();
    L st<Thr ftSearchResult> newResults = L sts.newArrayL stW hCapac y(numResults);
    HashSet<Long> resultSet = Sets.newHashSetW hExpectedS ze(numResults);

    for (Thr ftSearchResult result : oldResults) {
       f (resultSet.conta ns(result.get d())) {
        tr mStats. ncreaseRemovedDupsCount();
        cont nue;
      }

      newResults.add(result);
      resultSet.add(result.get d());
    }

    searchResults.setResults(newResults);
  }

  protected f nal  nt addResponsesToCollector(Mult way rgeCollector collector) {
     nt totalResultS ze = 0;
    for (Earlyb rdResponse response : accumulatedResponses.getSuccessResponses()) {
       f (response. sSetSearchResults()) {
        totalResultS ze += response.getSearchResults().getResultsS ze();
      }
      collector.addResponse(response);
    }
    return totalResultS ze;
  }

  /**
   * G ven a sorted searchResults (for recency, sorted by  D; for relevance, sorted by score),
   * returns t  f rst 'computeNumResultsToKeep()' number of results.
   *
   * @param searchResults t  searchResults to be truncated.
   */
  protected f nal vo d truncateResults(Thr ftSearchResults searchResults, Tr mStats tr mStats) {
     nt numResultsRequested = computeNumResultsToKeep();

     nt to = numResultsRequested ==  nteger.MAX_VALUE ? searchResults.getResultsS ze()
        : Math.m n(numResultsRequested, searchResults.getResultsS ze());
     f (searchResults.getResultsS ze() > to) {
      tr mStats.setResultsTruncatedFromTa lCount(searchResults.getResultsS ze() - to);

       f (to > 0) {
        searchResults.setResults(searchResults.getResults().subL st(0, to));
      } else {
        // No more results for t  next page
        EARLYB RD_RESPONSE_NO_MORE_RESULTS. ncre nt();
        searchResults.setResults(Collect ons.<Thr ftSearchResult>emptyL st());
      }
    }
  }

  Earlyb rdRequest getEarlyb rdRequest() {
    return requestContext.getRequest();
  }
}
