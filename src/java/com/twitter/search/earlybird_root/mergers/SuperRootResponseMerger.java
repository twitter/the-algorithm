package com.tw ter.search.earlyb rd_root. rgers;

 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.cac .Cac Bu lder;
 mport com.google.common.cac .Cac Loader;
 mport com.google.common.cac .Load ngCac ;
 mport com.google.common.collect.L sts;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.common.quant y.Amount;
 mport com.tw ter.common.quant y.T  ;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.futures.Futures;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.query.thr ftjava.EarlyTerm nat on nfo;
 mport com.tw ter.search.common.relevance.ut ls.ResultComparators;
 mport com.tw ter.search.common.search.EarlyTerm nat onState;
 mport com.tw ter.search.common.ut l.F nagleUt l;
 mport com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponse rgeUt l;
 mport com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponseUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRank ngMode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftT etS ce;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdServ ceResponse;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Funct on0;
 mport com.tw ter.ut l.Future;

/** Ut l y funct ons for  rg ng recency and relevance results. */
publ c class SuperRootResponse rger {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(SuperRootResponse rger.class);
  pr vate stat c f nal Str ng ALL_STATS_PREF X = "superroot_response_ rger_";

  pr vate stat c f nal SearchCounter FULL_ARCH VE_M N_ D_GREATER_THAN_REALT ME_M N_ D =
    SearchCounter.export("full_arch ve_m n_ d_greater_than_realt  _m n_ d");

  pr vate stat c f nal Str ng ERROR_FORMAT = "%s%s_errors_from_cluster_%s_%s";

  pr vate f nal Thr ftSearchRank ngMode rank ngMode;
  pr vate f nal Earlyb rdFeatureSc ma rger featureSc ma rger;
  pr vate f nal Str ng featureStatPref x;
  pr vate f nal Clock clock;
  pr vate f nal Str ng rank ngModeStatPref x;

  pr vate f nal SearchCounter  rgedResponseSearchResultsNotSet;
  pr vate f nal SearchCounter  nval dM nStatus d;
  pr vate f nal SearchCounter  nval dMaxStatus d;
  pr vate f nal SearchCounter noM n ds;
  pr vate f nal SearchCounter noMax ds;
  pr vate f nal SearchCounter  rgedResponses;
  pr vate f nal SearchCounter  rgedResponsesW hExactDups;
  pr vate f nal Load ngCac <Pa r<Thr ftT etS ce, Thr ftT etS ce>, SearchCounter> dupsStats;

  pr vate stat c f nal Earlyb rdResponse EMPTY_RESPONSE =
      new Earlyb rdResponse(Earlyb rdResponseCode.SUCCESS, 0)
          .setSearchResults(new Thr ftSearchResults()
              .setResults(L sts.<Thr ftSearchResult>newArrayL st()));

  /**
   * Creates a new SuperRootResponse rger  nstance.
   * @param rank ngMode T  rank ng mode to use w n  rg ng results.
   * @param featureSc ma rger T   rger that can  rge feature sc ma from d fferent t ers.
   * @param clock T  clock that w ll be used to  rge results.
   */
  publ c SuperRootResponse rger(Thr ftSearchRank ngMode rank ngMode,
                                 Earlyb rdFeatureSc ma rger featureSc ma rger,
                                 Clock clock) {
    t .rank ngModeStatPref x = rank ngMode.na ().toLo rCase();

    t .rank ngMode = rank ngMode;
    t .featureSc ma rger = featureSc ma rger;
    t .clock = clock;
    t .featureStatPref x = "superroot_" + rank ngMode.na ().toLo rCase();

     rgedResponseSearchResultsNotSet = SearchCounter.export(
        ALL_STATS_PREF X + rank ngModeStatPref x + "_ rged_response_search_results_not_set");
     nval dM nStatus d =
      SearchCounter.export(ALL_STATS_PREF X + rank ngModeStatPref x + "_ nval d_m n_status_ d");
     nval dMaxStatus d =
      SearchCounter.export(ALL_STATS_PREF X + rank ngModeStatPref x + "_ nval d_max_status_ d");
    noM n ds = SearchCounter.export(ALL_STATS_PREF X + rank ngModeStatPref x + "_no_m n_ ds");
    noMax ds = SearchCounter.export(ALL_STATS_PREF X + rank ngModeStatPref x + "_no_max_ ds");
     rgedResponses = SearchCounter.export(ALL_STATS_PREF X + rank ngModeStatPref x
      + "_ rged_responses");
     rgedResponsesW hExactDups =
      SearchCounter.export(ALL_STATS_PREF X + rank ngModeStatPref x
        + "_ rged_responses_w h_exact_dups");
    dupsStats = Cac Bu lder.newBu lder()
      .bu ld(new Cac Loader<Pa r<Thr ftT etS ce, Thr ftT etS ce>, SearchCounter>() {
          @Overr de
          publ c SearchCounter load(Pa r<Thr ftT etS ce, Thr ftT etS ce> key) {
            return SearchCounter.export(
                ALL_STATS_PREF X + rank ngModeStatPref x + "_ rged_responses_w h_exact_dups_"
                + key.getF rst().na () + "_" + key.getSecond().na ());
          }
        });
  }

  pr vate vo d  ncrErrorCount(Str ng cluster, @Nullable Earlyb rdResponse response) {
    Str ng cause;
     f (response != null) {
      cause = response.getResponseCode().na ().toLo rCase();
    } else {
      cause = "null_response";
    }
    Str ng statNa  = Str ng.format(
      ERROR_FORMAT, ALL_STATS_PREF X, rank ngModeStatPref x, cluster, cause
    );

    SearchCounter.export(statNa ). ncre nt();
  }

  /**
   *  rges t  g ven response futures.
   *
   * @param earlyb rdRequestContext T  earlyb rd request.
   * @param realt  ResponseFuture T  response from t  realt   cluster.
   * @param protectedResponseFuture T  response from t  protected cluster.
   * @param fullArch veResponseFuture T  response from t  full arch ve cluster.
   * @return A future w h t   rged results.
   */
  publ c Future<Earlyb rdResponse>  rgeResponseFutures(
      f nal Earlyb rdRequestContext earlyb rdRequestContext,
      f nal Future<Earlyb rdServ ceResponse> realt  ResponseFuture,
      f nal Future<Earlyb rdServ ceResponse> protectedResponseFuture,
      f nal Future<Earlyb rdServ ceResponse> fullArch veResponseFuture) {
    Future<Earlyb rdResponse>  rgedResponseFuture = Futures.map(
        realt  ResponseFuture, protectedResponseFuture, fullArch veResponseFuture,
        new Funct on0<Earlyb rdResponse>() {
          @Overr de
          publ c Earlyb rdResponse apply() {
            //  f t  realt   response  s not val d, return an error response.
            // Also, t  realt   serv ce should always be called.
            Earlyb rdServ ceResponse realt  Response = Futures.get(realt  ResponseFuture);

             f (realt  Response.getServ ceState().serv ceWasRequested()
                && (!realt  Response.getServ ceState().serv ceWasCalled()
                    || !Earlyb rdResponse rgeUt l. sVal dResponse(
                        realt  Response.getResponse()))) {

               ncrErrorCount("realt  ", realt  Response.getResponse());
              return Earlyb rdResponse rgeUt l.transform nval dResponse(
                  realt  Response.getResponse(), "realt  ");
            }

            //  f   have a protected response and  's not val d, return an error response.
            Earlyb rdServ ceResponse protectedResponse = Futures.get(protectedResponseFuture);
             f (protectedResponse.getServ ceState().serv ceWasCalled()) {
               f (!Earlyb rdResponse rgeUt l. sVal dResponse(protectedResponse.getResponse())) {
                 ncrErrorCount("protected", protectedResponse.getResponse());

                return Earlyb rdResponse rgeUt l.transform nval dResponse(
                    protectedResponse.getResponse(), "protected");
              }
            }

            //  f   have a full arch ve response, c ck  f  's val d.
            Earlyb rdServ ceResponse fullArch veResponse = Futures.get(fullArch veResponseFuture);
            boolean arch veHasError =
              fullArch veResponse.getServ ceState().serv ceWasCalled()
              && !Earlyb rdResponse rgeUt l. sVal dResponse(fullArch veResponse.getResponse());

            //  rge t  responses.
            Earlyb rdResponse  rgedResponse =  rgeResponses(
                earlyb rdRequestContext,
                realt  Response.getResponse(),
                protectedResponse.getResponse(),
                fullArch veResponse.getResponse());

            //  f t  realt   clusters d dn't return any results, and t  full arch ve cluster
            // returned an error response, return an error  rged response.
             f (arch veHasError && !Earlyb rdResponseUt l.hasResults( rgedResponse)) {
               ncrErrorCount("full_arch ve", fullArch veResponse.getResponse());

              return Earlyb rdResponse rgeUt l.fa ledEarlyb rdResponse(
                  fullArch veResponse.getResponse().getResponseCode(),
                  "realt   clusters had no results and arch ve cluster response had error");
            }

            // Corner case: t  realt   response could have exactly numRequested results, and could
            // be exhausted (not early-term nated).  n t  case, t  request should not have been
            // sent to t  full arch ve cluster.
            //   -  f t  full arch ve cluster  s not ava lable, or was not requested, t n   don't
            //     need to change anyth ng.
            //   -  f t  full arch ve cluster  s ava lable and was requested (but wasn't h 
            //     because   found enough results  n t  realt   cluster), t n   should set t 
            //     early-term nat on flag on t   rged response, to  nd cate that   potent ally
            //     have more results for t  query  n    ndex.
             f ((fullArch veResponse.getServ ceState()
                 == Earlyb rdServ ceResponse.Serv ceState.SERV CE_NOT_CALLED)
                && !Earlyb rdResponseUt l. sEarlyTerm nated(realt  Response.getResponse())) {
              EarlyTerm nat on nfo earlyTerm nat on nfo = new EarlyTerm nat on nfo(true);
              earlyTerm nat on nfo.setEarlyTerm nat onReason(
                  EarlyTerm nat onState.TERM NATED_NUM_RESULTS_EXCEEDED.getTerm nat onReason());
               rgedResponse.setEarlyTerm nat on nfo(earlyTerm nat on nfo);
            }

            //  f  've exhausted all clusters, set t  m nSearc dStatus D to 0.
             f (!Earlyb rdResponseUt l. sEarlyTerm nated( rgedResponse)) {
               rgedResponse.getSearchResults().setM nSearc dStatus D(0);
            }

            return  rgedResponse;
          }
        });

    // Handle all  rg ng except ons.
    return handleResponseExcept on( rgedResponseFuture,
                                   "Except on thrown wh le  rg ng responses.");
  }

  /**
   *  rge t  results  n t  g ven responses.
   *
   * @param earlyb rdRequestContext T  earlyb rd request context.
   * @param realt  Response T  response from t  realt   cluster.
   * @param protectedResponse T  response from t  protected cluster.
   * @param fullArch veResponse T  response from t  full arch ve cluster.
   * @return T   rged response.
   */
  pr vate Earlyb rdResponse  rgeResponses(
      Earlyb rdRequestContext earlyb rdRequestContext,
      @Nullable Earlyb rdResponse realt  Response,
      @Nullable Earlyb rdResponse protectedResponse,
      @Nullable Earlyb rdResponse fullArch veResponse) {

    Earlyb rdRequest request = earlyb rdRequestContext.getRequest();
    Thr ftSearchQuery searchQuery = request.getSearchQuery();
     nt numResultsRequested;

     f (request. sSetNumResultsToReturnAtRoot()) {
      numResultsRequested = request.getNumResultsToReturnAtRoot();
    } else {
      numResultsRequested = searchQuery.getNumResults();
    }

    Precond  ons.c ckState(numResultsRequested > 0);

    Earlyb rdResponse  rgedResponse = EMPTY_RESPONSE.deepCopy();
     f ((realt  Response != null)
        && (realt  Response.getResponseCode() != Earlyb rdResponseCode.T ER_SK PPED)) {
       rgedResponse = realt  Response.deepCopy();
    }

     f (! rgedResponse. sSetSearchResults()) {
       rgedResponseSearchResultsNotSet. ncre nt();
       rgedResponse.setSearchResults(
          new Thr ftSearchResults(L sts.<Thr ftSearchResult>newArrayL st()));
    }

    //  f e  r t  realt   or t  full arch ve response  s early-term nated,   want t   rged
    // response to be early-term nated too. T  early-term nat on flag from t  realt   response
    // carr es over to t   rged response, because  rgedResponse  s just a deep copy of t 
    // realt   response. So   only need to c ck t  early-term nat on flag of t  full arch ve
    // response.
     f ((fullArch veResponse != null)
        && Earlyb rdResponseUt l. sEarlyTerm nated(fullArch veResponse)) {
       rgedResponse.setEarlyTerm nat on nfo(fullArch veResponse.getEarlyTerm nat on nfo());
    }

    //  f realt   has empty results and protected has so  results t n   copy t  early
    // term nat on  nformat on  f that  s present
     f (protectedResponse != null
        &&  rgedResponse.getSearchResults().getResults(). sEmpty()
        && !protectedResponse.getSearchResults().getResults(). sEmpty()
        && Earlyb rdResponseUt l. sEarlyTerm nated(protectedResponse)) {
       rgedResponse.setEarlyTerm nat on nfo(protectedResponse.getEarlyTerm nat on nfo());
    }

    //  rge t  results.
    L st<Thr ftSearchResult>  rgedResults =  rgeResults(
        numResultsRequested, realt  Response, protectedResponse, fullArch veResponse);

    // Tr m t   rged results  f necessary.
    boolean resultsTr m d = false;
     f ( rgedResults.s ze() > numResultsRequested
        && !(searchQuery. sSetRelevanceOpt ons()
             && searchQuery.getRelevanceOpt ons(). sReturnAllResults())) {
      //  f   have more results than requested, tr m t  result l st and re-adjust
      // m nSearc dStatus D.
       rgedResults =  rgedResults.subL st(0, numResultsRequested);

      // Mark early term nat on  n  rged response
       f (!Earlyb rdResponseUt l. sEarlyTerm nated( rgedResponse)) {
        EarlyTerm nat on nfo earlyTerm nat on nfo = new EarlyTerm nat on nfo(true);
        earlyTerm nat on nfo.setEarlyTerm nat onReason(
            EarlyTerm nat onState.TERM NATED_NUM_RESULTS_EXCEEDED.getTerm nat onReason());
         rgedResponse.setEarlyTerm nat on nfo(earlyTerm nat on nfo);
      }

      resultsTr m d = true;
    }

     rgedResponse.getSearchResults().setResults( rgedResults);
    featureSc ma rger. rgeFeatureSc maAcrossClusters(
        earlyb rdRequestContext,
         rgedResponse,
        featureStatPref x,
        realt  Response,
        protectedResponse,
        fullArch veResponse);

    // Set t  m nSearc dStatus D and maxSearc dStatus D f elds on t   rged response.
    setM nSearc dStatus d( rgedResponse, realt  Response, protectedResponse, fullArch veResponse,
        resultsTr m d);
    setMaxSearc dStatus d( rgedResponse, realt  Response, protectedResponse,
        fullArch veResponse);

     nt numRealt  Searc dSeg nts =
        (realt  Response != null && realt  Response. sSetNumSearc dSeg nts())
            ? realt  Response.getNumSearc dSeg nts()
            : 0;

     nt numProtectedSearc dSeg nts =
        (protectedResponse != null && protectedResponse. sSetNumSearc dSeg nts())
            ? protectedResponse.getNumSearc dSeg nts()
            : 0;

     nt numArch veSearc dSeg nts =
        (fullArch veResponse != null && fullArch veResponse. sSetNumSearc dSeg nts())
            ? fullArch veResponse.getNumSearc dSeg nts()
            : 0;

     rgedResponse.setNumSearc dSeg nts(
        numRealt  Searc dSeg nts + numProtectedSearc dSeg nts + numArch veSearc dSeg nts);

     f (earlyb rdRequestContext.getRequest().getDebugMode() > 0) {
       rgedResponse.setDebugStr ng(
           rgeClusterDebugStr ngs(realt  Response, protectedResponse, fullArch veResponse));
    }

    return  rgedResponse;
  }

  /**
   *  rges t  g ven responses.
   *
   * @param numResults t  number of results requested
   * @param realt  Response t  response from t  realt   response
   * @param protectedResponse t  response from t  protected response
   * @param fullArch veResponse t  response from t  full arch ve response
   * @return t  l st of  rged results
   */
  pr vate L st<Thr ftSearchResult>  rgeResults( nt numResults,
                                                @Nullable Earlyb rdResponse realt  Response,
                                                @Nullable Earlyb rdResponse protectedResponse,
                                                @Nullable Earlyb rdResponse fullArch veResponse) {
     rgedResponses. ncre nt();
    //   f rst  rge t  results from t  two realt   clusters, Realt   cluster and
    // Realt   Protected T ets cluster
    L st<Thr ftSearchResult>  rgedResults =  rgePubl cAndProtectedRealt  Results(
        numResults,
        realt  Response,
        protectedResponse,
        fullArch veResponse,
        clock);

    Earlyb rdResponse rgeUt l.addResultsToL st( rgedResults, fullArch veResponse,
                                                Thr ftT etS ce.FULL_ARCH VE_CLUSTER);

    L st<Thr ftSearchResult> d st nct rgedResults =
        Earlyb rdResponse rgeUt l.d st nctByStatus d( rgedResults, dupsStats);
     f ( rgedResults != d st nct rgedResults) {
       rgedResponsesW hExactDups. ncre nt();
    }

     f (rank ngMode == Thr ftSearchRank ngMode.RELEVANCE
        || rank ngMode == Thr ftSearchRank ngMode.TOPTWEETS) {
      d st nct rgedResults.sort(ResultComparators.SCORE_COMPARATOR);
    } else {
      d st nct rgedResults.sort(ResultComparators. D_COMPARATOR);
    }

    return d st nct rgedResults;
  }

  /**
   *  thod for  rg ng t ets from protected and realt   clusters
   *  - realt  , guaranteed ne r than any arch ve t ets
   *  - protected, also realt  , but w h a potent ally larger w ndow (opt onal)
   *  - arch ve, publ c, guaranteed older than any publ c realt   t ets (opt onal, used for
   *     d l m s, *not added to results*)
   *   adds t  Thr ftSearchResults from protected t ets to t  realt  Response
   *
   * Algor hm d agram: (w h ne r t ets at t  top)
   *               ------------------------------------  <--- protected maxSearc dStatus D
   *               |C:Ne st protected realt   t ets|
   *               | (does not ex st  f realt        |
   *               | max D >= protected max D)        |
   *
   *               |     ------------------------     |  <--- 60 seconds ago
   *               |D:Ne r protected realt   t ets |
   *               | (does not ex st  f realt        |
   *               | max D >= 60 seconds ago)         |
   * ----------    |     ------------------------     |  <--- publ c realt   maxSearc dStatus D
   * |A:Publ c|    |E:Automat cally val d protected   |
   * |realt  |    |realt   t ets                   |
   * ----------    |     ------------------------     |  <--- publ c realt   m nSearc dStatus D
   *               |                                  |
   * ----------    |  E  f arch ve  s present         |  <--- publ c arch ve maxSearc dStatus D
   * ----------    |  E  f arch ve  s present         |  <--- publ c arch ve maxSearc dStatus D
   * |B:Publ c|    |  F  s arch ve  s not present     |
   * |arch ve |    |                                  |
   * ----------    |     ------------------------     |  <--- publ c arch ve m nSearc dStatus D
   *               |F:Older protected realt   t ets |
   *               | (does not ex st  f protected     |
   *               | m n D >= publ c m n D)           |
   *               ------------------------------------  <--- protected m nSearc dStatus D
   * Step 1: Select t ets from groups A, and E.  f t   s enough, return t m
   * Step 2: Select t ets from groups A, E, and F.  f t   s enough, return t m
   * Step 3: Select t ets from groups A, D, E, and F and return t m
   *
   * T re are two pr mary tradeoffs, both of wh ch favor publ c t ets:
   *  (1) Benef : Wh le publ c  ndex ng latency  s < 60s, auto-updat ng never m sses publ c t ets
   *      Cost:    Absence of publ c t ets may delay protected t ets from be ng searchable for 60s
   *  (2) Benef : No fa lure or delay from t  protected cluster w ll affect realt   results
   *      Cost:     f t  protected cluster  ndexes more slowly, auto-update may m ss  s t ets
   *
   * @param fullArch veT ets - used solely for generat ng anchor po nts, not  rged  n.
   */
  @V s bleForTest ng
  stat c L st<Thr ftSearchResult>  rgePubl cAndProtectedRealt  Results(
       nt numRequested,
      Earlyb rdResponse realt  T ets,
      Earlyb rdResponse realt  ProtectedT ets,
      @Nullable Earlyb rdResponse fullArch veT ets,
      Clock clock) {
    // See wh ch results w ll actually be used
    boolean  sRealt  Usable = Earlyb rdResponseUt l.hasResults(realt  T ets);
    boolean  sArch veUsable = Earlyb rdResponseUt l.hasResults(fullArch veT ets);
    boolean  sProtectedUsable = Earlyb rdResponseUt l.hasResults(realt  ProtectedT ets);

    long m n d = Long.M N_VALUE;
    long max d = Long.MAX_VALUE;
     f ( sRealt  Usable) {
      // Determ ne t  actual upper/lo r bounds on t  t et  d
       f (realt  T ets.getSearchResults(). sSetM nSearc dStatus D()) {
        m n d = realt  T ets.getSearchResults().getM nSearc dStatus D();
      }
       f (realt  T ets.getSearchResults(). sSetMaxSearc dStatus D()) {
        max d = realt  T ets.getSearchResults().getMaxSearc dStatus D();
      }

       nt justR ght = realt  T ets.getSearchResults().getResultsS ze();
       f ( sArch veUsable) {
        justR ght += fullArch veT ets.getSearchResults().getResultsS ze();
         f (fullArch veT ets.getSearchResults(). sSetM nSearc dStatus D()) {
          long fullArch veM n d = fullArch veT ets.getSearchResults().getM nSearc dStatus D();
           f (fullArch veM n d <= m n d) {
            m n d = fullArch veM n d;
          } else {
            FULL_ARCH VE_M N_ D_GREATER_THAN_REALT ME_M N_ D. ncre nt();
          }
        }
      }
       f ( sProtectedUsable) {
        for (Thr ftSearchResult result : realt  ProtectedT ets.getSearchResults().getResults()) {
           f (result.get d() >= m n d && result.get d() <= max d) {
            justR ght++;
          }
        }
      }
       f (justR ght < numRequested) {
        // S nce t   s only used as an upper bound, old (pre-2010)  ds are st ll handled correctly
        max d = Math.max(
            max d,
            Snowflake dParser.generateVal dStatus d(
                clock.nowM ll s() - Amount.of(60, T  .SECONDS).as(T  .M LL SECONDS), 0));
      }
    }

    L st<Thr ftSearchResult>  rgedSearchResults = L sts.newArrayL stW hCapac y(numRequested * 2);

    // Add val d t ets  n order of pr or y: protected, t n realt  
    // Only add results that are w h n range (that c ck only matters for protected)
     f ( sProtectedUsable) {
      Earlyb rdResponse rgeUt l.markW hT etS ce(
          realt  ProtectedT ets.getSearchResults().getResults(),
          Thr ftT etS ce.REALT ME_PROTECTED_CLUSTER);
      for (Thr ftSearchResult result : realt  ProtectedT ets.getSearchResults().getResults()) {
         f (result.get d() <= max d && result.get d() >= m n d) {
           rgedSearchResults.add(result);
        }
      }
    }

     f ( sRealt  Usable) {
      Earlyb rdResponse rgeUt l.addResultsToL st(
           rgedSearchResults, realt  T ets, Thr ftT etS ce.REALT ME_CLUSTER);
    }

    // Set t  m nSearc dStatus D and maxSearc dStatus D on t  protected response to t 
    // m n d and max d that  re used to tr m t  protected results.
    // T   s needed  n order to correctly set t se  Ds on t   rged response.
    Thr ftSearchResults protectedResults =
      Earlyb rdResponseUt l.getResults(realt  ProtectedT ets);
     f ((protectedResults != null)
        && protectedResults. sSetM nSearc dStatus D()
        && (protectedResults.getM nSearc dStatus D() < m n d)) {
      protectedResults.setM nSearc dStatus D(m n d);
    }
     f ((protectedResults != null)
        && protectedResults. sSetMaxSearc dStatus D()
        && (protectedResults.getMaxSearc dStatus D() > max d)) {
      realt  ProtectedT ets.getSearchResults().setMaxSearc dStatus D(max d);
    }

    return  rgedSearchResults;
  }

  /**
   *  rges t  debug str ngs of t  g ven cluster responses.
   *
   * @param realt  Response T  response from t  realt   cluster.
   * @param protectedResponse T  response from t  protected cluster.
   * @param fullArch veResponse T  response from t  full arch ve cluster.
   * @return T   rged debug str ng.
   */
  publ c stat c Str ng  rgeClusterDebugStr ngs(@Nullable Earlyb rdResponse realt  Response,
                                                @Nullable Earlyb rdResponse protectedResponse,
                                                @Nullable Earlyb rdResponse fullArch veResponse) {
    Str ngBu lder sb = new Str ngBu lder();
     f ((realt  Response != null) && realt  Response. sSetDebugStr ng()) {
      sb.append("Realt   response: ").append(realt  Response.getDebugStr ng());
    }
     f ((protectedResponse != null) && protectedResponse. sSetDebugStr ng()) {
       f (sb.length() > 0) {
        sb.append("\n");
      }
      sb.append("Protected response: ").append(protectedResponse.getDebugStr ng());
    }
     f ((fullArch veResponse != null) && fullArch veResponse. sSetDebugStr ng()) {
       f (sb.length() > 0) {
        sb.append("\n");
      }
      sb.append("Full arch ve response: ").append(fullArch veResponse.getDebugStr ng());
    }

     f (sb.length() == 0) {
      return null;
    }
    return sb.toStr ng();
  }

  /**
   * Sets t  m nSearc dStatus D f eld on t   rged response.
   *
   * @param  rgedResponse T   rged response.
   * @param fullArch veResponse T  full arch ve response.
   * @param resultsTr m d W t r t   rged response results  re tr m d.
   */
  pr vate vo d setM nSearc dStatus d(Earlyb rdResponse  rgedResponse,
      Earlyb rdResponse realt  Response,
      Earlyb rdResponse protectedResponse,
      Earlyb rdResponse fullArch veResponse,
      boolean resultsTr m d) {
    Precond  ons.c ckNotNull( rgedResponse.getSearchResults());
     f (resultsTr m d) {
      //   got more results that   asked for and   tr m d t m.
      // Set m nSearc dStatus D to t   D of t  oldest result.
      Thr ftSearchResults searchResults =  rgedResponse.getSearchResults();
       f (searchResults.getResultsS ze() > 0) {
        L st<Thr ftSearchResult> results = searchResults.getResults();
        long lastResult d = results.get(results.s ze() - 1).get d();
        searchResults.setM nSearc dStatus D(lastResult d);
      }
      return;
    }

    //   d d not get more results that   asked for. Get t  m n of t  m nSearc dStatus Ds of
    // t   rged responses.
    L st<Long> m n Ds = L sts.newArrayL st();
     f (fullArch veResponse != null
        && fullArch veResponse. sSetSearchResults()
        && fullArch veResponse.getSearchResults(). sSetM nSearc dStatus D()) {
      m n Ds.add(fullArch veResponse.getSearchResults().getM nSearc dStatus D());
       f ( rgedResponse.getSearchResults(). sSetM nSearc dStatus D()
          &&  rgedResponse.getSearchResults().getM nSearc dStatus D()
          < fullArch veResponse.getSearchResults().getM nSearc dStatus D()) {
         nval dM nStatus d. ncre nt();
      }
    }

     f (protectedResponse != null
        && !Earlyb rdResponseUt l.hasResults(realt  Response)
        && Earlyb rdResponseUt l.hasResults(protectedResponse)
        && protectedResponse.getSearchResults(). sSetM nSearc dStatus D()) {
      m n Ds.add(protectedResponse.getSearchResults().getM nSearc dStatus D());
    }

     f ( rgedResponse.getSearchResults(). sSetM nSearc dStatus D()) {
      m n Ds.add( rgedResponse.getSearchResults().getM nSearc dStatus D());
    }

     f (!m n Ds. sEmpty()) {
       rgedResponse.getSearchResults().setM nSearc dStatus D(Collect ons.m n(m n Ds));
    } else {
      noM n ds. ncre nt();
    }
  }

  /**
   * Sets t  maxSearc dStatus D f eld on t   rged response.
   *
   * @param  rgedResponse T   rged response.
   * @param fullArch veResponse T  full arch ve response.
   */
  pr vate vo d setMaxSearc dStatus d(Earlyb rdResponse  rgedResponse,
      Earlyb rdResponse realt  Response,
      Earlyb rdResponse protectedResponse,
      Earlyb rdResponse fullArch veResponse) {

    Precond  ons.c ckNotNull( rgedResponse.getSearchResults());
    L st<Long> max Ds = L sts.newArrayL st();
     f (fullArch veResponse != null
        && fullArch veResponse. sSetSearchResults()
        && fullArch veResponse.getSearchResults(). sSetMaxSearc dStatus D()) {
      max Ds.add(fullArch veResponse.getSearchResults().getMaxSearc dStatus D());
       f ( rgedResponse.getSearchResults(). sSetMaxSearc dStatus D()
          && fullArch veResponse.getSearchResults().getMaxSearc dStatus D()
          >  rgedResponse.getSearchResults().getMaxSearc dStatus D()) {
         nval dMaxStatus d. ncre nt();
      }
    }

     f (protectedResponse != null
        && !Earlyb rdResponseUt l.hasResults(realt  Response)
        && Earlyb rdResponseUt l.hasResults(protectedResponse)
        && protectedResponse.getSearchResults(). sSetMaxSearc dStatus D()) {

      max Ds.add(protectedResponse.getSearchResults().getMaxSearc dStatus D());
    }

     f ( rgedResponse.getSearchResults(). sSetMaxSearc dStatus D()) {
      max Ds.add( rgedResponse.getSearchResults().getMaxSearc dStatus D());
    }

    Thr ftSearchResults searchResults =  rgedResponse.getSearchResults();
     f (searchResults.getResultsS ze() > 0) {
      L st<Thr ftSearchResult> results = searchResults.getResults();
      max Ds.add(results.get(0).get d());
    }

     f (!max Ds. sEmpty()) {
       rgedResponse.getSearchResults().setMaxSearc dStatus D(Collect ons.max(max Ds));
    } else {
      noMax ds. ncre nt();
    }
  }

  /**
   * Handles except ons thrown wh le  rg ng responses. T  out except ons are converted to
   * SERVER_T MEOUT_ERROR responses. All ot r except ons are converted to PERS STENT_ERROR
   * responses.
   */
  pr vate Future<Earlyb rdResponse> handleResponseExcept on(
      Future<Earlyb rdResponse> responseFuture, f nal Str ng debugMsg) {
    return responseFuture.handle(
        new Funct on<Throwable, Earlyb rdResponse>() {
          @Overr de
          publ c Earlyb rdResponse apply(Throwable t) {
            Earlyb rdResponseCode responseCode = Earlyb rdResponseCode.PERS STENT_ERROR;
             f (F nagleUt l. sT  outExcept on(t)) {
              responseCode = Earlyb rdResponseCode.SERVER_T MEOUT_ERROR;
            }
            Earlyb rdResponse response = new Earlyb rdResponse(responseCode, 0);
            response.setDebugStr ng(debugMsg + "\n" + t);
            return response;
          }
        });
  }
}
