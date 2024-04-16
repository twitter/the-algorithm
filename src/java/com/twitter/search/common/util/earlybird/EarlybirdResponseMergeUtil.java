package com.tw ter.search.common.ut l.earlyb rd;

 mport java.ut l.HashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.concurrent.Execut onExcept on;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.cac .Load ngCac ;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.L sts;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRank ngMode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftT etS ce;

/**
 * Ut l y  thods to  rge Earlyb rdResponses.
 */
publ c f nal class Earlyb rdResponse rgeUt l {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdResponse rgeUt l.class);

  pr vate stat c f nal Str ng  NVAL D_RESPONSE_STATS_PREF X = " nval d_response_stats_";

  // Stats for  nval d earlyb rd response
  pr vate stat c f nal  mmutableMap<Earlyb rdResponseCode, SearchCounter> ERROR_EXCEPT ONS;

  publ c stat c f nal SearchCounter NULL_RESPONSE_COUNTER =
      SearchCounter.export( NVAL D_RESPONSE_STATS_PREF X + "null_response");
  publ c stat c f nal SearchCounter SEARCH_RESULTS_NOT_SET_COUNTER =
      SearchCounter.export( NVAL D_RESPONSE_STATS_PREF X + "search_results_not_set");
  publ c stat c f nal SearchCounter SEARCH_RESULTS_W TH_RESULTS_NOT_SET_COUNTER =
      SearchCounter.export( NVAL D_RESPONSE_STATS_PREF X + "search_results_w h_results_not_set");
  publ c stat c f nal SearchCounter MAX_SEARCHED_STATUS_ D_NOT_SET_COUNTER =
      SearchCounter.export( NVAL D_RESPONSE_STATS_PREF X + "max_searc d_status_ d_not_set");
  publ c stat c f nal SearchCounter M N_SEARCHED_STATUS_ D_NOT_SET_COUNTER =
      SearchCounter.export( NVAL D_RESPONSE_STATS_PREF X + "m n_searc d_status_ d_not_set");

  stat c {
     mmutableMap.Bu lder<Earlyb rdResponseCode, SearchCounter> bu lder =  mmutableMap.bu lder();

    for (Earlyb rdResponseCode responseCode : Earlyb rdResponseCode.values()) {
       f (responseCode != Earlyb rdResponseCode.SUCCESS) {
        bu lder.put(responseCode, SearchCounter.export(
             NVAL D_RESPONSE_STATS_PREF X + responseCode.na ().toLo rCase()));
      }
    }

    ERROR_EXCEPT ONS = bu lder.bu ld();
  }

  pr vate Earlyb rdResponse rgeUt l() {
  }

  /**
   * Tags t  results  n t  g ven Earlyb rdResponse w h t  g ven Thr ftT etS ce and adds t m
   * to t  g ven l st of results.
   *
   * @param results T  l st of results to wh ch t  new results w ll be added.
   * @param earlyb rdResponse T  Earlyb rdResponse whose results w ll be added to {@code results}.
   * @param t etS ce T  Thr ftT etS ce that w ll be used to mark all results  n
   *                    {@code earlyb rdResponse}.
   * @return {@code false}  f {@code earlyb rdResponse}  s {@code null} or doesn't have any results;
   *         {@code true}, ot rw se.
   */
  publ c stat c boolean addResultsToL st(L st<Thr ftSearchResult> results,
                                         Earlyb rdResponse earlyb rdResponse,
                                         Thr ftT etS ce t etS ce) {
    return Earlyb rdResponseUt l.hasResults(earlyb rdResponse)
      && addResultsToL st(results,
                          earlyb rdResponse.getSearchResults().getResults(),
                          t etS ce);
  }

  /**
   * Tags t  results  n t  g ven l st w h t  g ven Thr ftT etS ce and adds t m to t  g ven
   * l st of results.
   *
   * @param results T  l st of results to wh ch t  new results w ll be added.
   * @param resultsToAdd T  l st of results to add.
   * @param t etS ce T  Thr ftT etS ce that w ll be used to mark all results  n
   *                    {@code resultsToAdd}.
   * @return {@code false}  f {@code results}  s {@code null} or  f {@code resultsToAdd}  s
   *         {@code null} or doesn't have any results; {@code true}, ot rw se.
   */
  publ c stat c boolean addResultsToL st(L st<Thr ftSearchResult> results,
                                         L st<Thr ftSearchResult> resultsToAdd,
                                         Thr ftT etS ce t etS ce) {
    Precond  ons.c ckNotNull(results);
     f ((resultsToAdd == null) || resultsToAdd. sEmpty()) {
      return false;
    }

    markW hT etS ce(resultsToAdd, t etS ce);

    results.addAll(resultsToAdd);
    return true;
  }

  /**
   * D st nct t   nput Thr ftSearchResult by  s status  d.  f t re are dupl cates, t  f rst
   *  nstance of t  dupl cates  s returned  n t  d st nct result.  f t  d st nct result  s t 
   * sa  as t   nput result, t   n  al  nput result  s returned; ot rw se, t  d st nct result
   *  s returned.
   *
   * @param results t   nput result
   * @param dupsStats stats counter track dupl cates s ce
   * @return t   nput result  f t re  s no dupl cate; ot rw se, return t  d st nct result
   */
  publ c stat c L st<Thr ftSearchResult> d st nctByStatus d(
      L st<Thr ftSearchResult> results,
      Load ngCac <Pa r<Thr ftT etS ce, Thr ftT etS ce>, SearchCounter> dupsStats) {
    Map<Long, Thr ftT etS ce> seenStatus dToS ceMap = new HashMap<>();
    L st<Thr ftSearchResult> d st nctResults = L sts.newArrayL stW hCapac y(results.s ze());
    for (Thr ftSearchResult result : results)  {
       f (seenStatus dToS ceMap.conta nsKey(result.get d())) {
        Thr ftT etS ce s ce1 = seenStatus dToS ceMap.get(result.get d());
        Thr ftT etS ce s ce2 = result.getT etS ce();
         f (s ce1 != null && s ce2 != null) {
          try {
            dupsStats.get(Pa r.of(s ce1, s ce2)). ncre nt();
          } catch (Execut onExcept on e) {
            LOG.warn("Could not  ncre nt stat for dupl cate results from clusters " + s ce1
                + " and " + s ce2, e);
          }
        }
      } else {
        d st nctResults.add(result);
        seenStatus dToS ceMap.put(result.get d(), result.getT etS ce());
      }
    }
    return results.s ze() == d st nctResults.s ze() ? results : d st nctResults;
  }

  /**
   * Tags t  g ven results w h t  g ven Thr ftT etS ce.
   *
   * @param results T  results to be tagged.
   * @param t etS ce T  Thr ftT etS ce to be used to tag t  g ven results.
   */
  publ c stat c vo d markW hT etS ce(L st<Thr ftSearchResult> results,
                                         Thr ftT etS ce t etS ce) {
     f (results != null) {
      for (Thr ftSearchResult result : results) {
        result.setT etS ce(t etS ce);
      }
    }
  }

  /**
   * C ck  f an Earlyb rd response  s val d
   */
  publ c stat c boolean  sVal dResponse(f nal Earlyb rdResponse response) {
     f (response == null) {
      NULL_RESPONSE_COUNTER. ncre nt();
      return false;
    }

     f (!Earlyb rdResponseUt l. sSuccessfulResponse(response)) {
      return false;
    }

     f (!response. sSetSearchResults()) {
      SEARCH_RESULTS_NOT_SET_COUNTER. ncre nt();
      return true;
    }

     f (!response.getSearchResults(). sSetResults()) {
      SEARCH_RESULTS_W TH_RESULTS_NOT_SET_COUNTER. ncre nt();
    }

    //  n earlyb rd, w n earlyb rd term nated, e.g., t   out, complex quer es -   don't set t 
    // m n/max  searc d status  d.
    boolean  sEarlyTerm nated = response. sSetEarlyTerm nat on nfo()
        && response.getEarlyTerm nat on nfo(). sEarlyTerm nated();

     f (! sEarlyTerm nated && !response.getSearchResults(). sSetM nSearc dStatus D()) {
      M N_SEARCHED_STATUS_ D_NOT_SET_COUNTER. ncre nt();
    }

     f (! sEarlyTerm nated && !response.getSearchResults(). sSetMaxSearc dStatus D()) {
      MAX_SEARCHED_STATUS_ D_NOT_SET_COUNTER. ncre nt();
    }

    return true;
  }

  /**
   * For  nval d successful Earlyb rd Response, return a fa led response w h debug msg.
   */
  publ c stat c Earlyb rdResponse transform nval dResponse(f nal Earlyb rdResponse response,
                                                           f nal Str ng debugMsg) {
     f (response == null) {
      return fa ledEarlyb rdResponse(Earlyb rdResponseCode.PERS STENT_ERROR,
          debugMsg + ", msg: null response from downstream");
    }
    Precond  ons.c ckState(response.getResponseCode() != Earlyb rdResponseCode.SUCCESS);

    Earlyb rdResponseCode newResponseCode;
    Earlyb rdResponseCode responseCode = response.getResponseCode();
    sw ch (responseCode) {
      case T ER_SK PPED:
        ERROR_EXCEPT ONS.get(responseCode). ncre nt();
        return response;
      case REQUEST_BLOCKED_ERROR:
      case CL ENT_ERROR:
      case SERVER_T MEOUT_ERROR:
      case QUOTA_EXCEEDED_ERROR:
      case CL ENT_CANCEL_ERROR:
      case TOO_MANY_PART T ONS_FA LED_ERROR:
        ERROR_EXCEPT ONS.get(responseCode). ncre nt();
        newResponseCode = responseCode;
        break;
      default:
        ERROR_EXCEPT ONS.get(responseCode). ncre nt();
        newResponseCode = Earlyb rdResponseCode.PERS STENT_ERROR;
    }

    Str ng newDebugMsg = debugMsg + ", downstream response code: " + responseCode
      + (response. sSetDebugStr ng() ? ", downstream msg: " + response.getDebugStr ng() : "");


    return fa ledEarlyb rdResponse(newResponseCode, newDebugMsg);
  }

  /**
   * Create a new Earlyb rdResponse w h debug msg
   */
  publ c stat c Earlyb rdResponse fa ledEarlyb rdResponse(f nal Earlyb rdResponseCode responseCode,
                                                          f nal Str ng debugMsg) {
    Earlyb rdResponse fa ledResponse = new Earlyb rdResponse();
    fa ledResponse.setResponseCode(responseCode);
    fa ledResponse.setDebugStr ng(debugMsg);
    return fa ledResponse;
  }

  /**
   * Returns t  number of results to keep as part of  rge-collect on. Recency mode should  gnore
   * relevance opt ons.  n part cular, t  flag returnAllResults  ns de relevance opt ons.
   */
  publ c stat c  nt computeNumResultsToKeep(Earlyb rdRequest request) {
    Thr ftSearchQuery searchQuery = request.getSearchQuery();

     f (searchQuery.getRank ngMode() != Thr ftSearchRank ngMode.RECENCY
        && searchQuery. sSetRelevanceOpt ons()
        && searchQuery.getRelevanceOpt ons(). sReturnAllResults()) {
      return  nteger.MAX_VALUE;
    }

     f (request. sSetNumResultsToReturnAtRoot()) {
      return request.getNumResultsToReturnAtRoot();
    }

     f (searchQuery. sSetCollectorParams()) {
      return searchQuery.getCollectorParams().getNumResultsToReturn();
    }

    return searchQuery.getNumResults();
  }
}
