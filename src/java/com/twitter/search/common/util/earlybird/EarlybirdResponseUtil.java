package com.tw ter.search.common.ut l.earlyb rd;

 mport java.ut l.ArrayL st;
 mport java.ut l.Collect ons;
 mport java.ut l.HashSet;
 mport java.ut l.L st;
 mport java.ut l.Set;
 mport java.ut l.stream.Collectors;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.adapt ve.adapt ve_results.thr ftjava.T etS ce;
 mport com.tw ter.search.common.logg ng.ObjectKey;
 mport com.tw ter.search.common.runt  .DebugManager;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftT etS ce;

/** Ut l y  thods that work on Earlyb rdResponses. */
publ c f nal class Earlyb rdResponseUt l {
  pr vate Earlyb rdResponseUt l() {
  }

  /**
   * Returns t  results  n t  g ven Earlyb rdResponse.
   *
   * @param response T  Earlyb rdResponse.
   * @return T  results  n t  g ven Earlyb rdResponse, or {@code null}  f t  response  s
   *         {@code null} or t  results are not set.
   */
  publ c stat c Thr ftSearchResults getResults(Earlyb rdResponse response) {
     f ((response == null) || !response. sSetSearchResults()) {
      return null;
    }

    return response.getSearchResults();
  }

  /**
   * Determ nes  f t  g ven Earlyb rdResponse has results.
   *
   * @param response T  Earlyb rdResponse.
   * @return {@code true}  f t  g ven Earlyb rdResponse has results; {@code false} ot rw se.
   */
  publ c stat c boolean hasResults(Earlyb rdResponse response) {
    Thr ftSearchResults results = getResults(response);
    return (results != null) && results. sSetResults() && !results.getResults(). sEmpty();
  }

  /**
   * Returns t  number of results  n t  g ven Earlyb rdResponse.
   *
   * @param response T  Earlyb rdResponse.
   * @return T  number of results  n t  g ven Earlyb rdResponse.
   */
  publ c stat c  nt getNumResults(Earlyb rdResponse response) {
    return hasResults(response) ? response.getSearchResults().getResultsS ze() : 0;
  }

  /**
   * Determ nes t  response  s early-term nated.
   *
   * @param response T  Earlyb rdResponse.
   * @return {@code true}  f t  response  s early-term nated; {@code false} ot rw se.
   */
  publ c stat c boolean  sEarlyTerm nated(Earlyb rdResponse response) {
    Precond  ons.c ckNotNull(response);
    return response. sSetEarlyTerm nat on nfo()
        && response.getEarlyTerm nat on nfo(). sEarlyTerm nated();
  }

  /**
   * Returns  f t  response should be cons dered fa led for purposes of stats and logg ng.
   */
  publ c stat c boolean responseCons deredFa led(Earlyb rdResponseCode code) {
    return code != Earlyb rdResponseCode.SUCCESS
        && code != Earlyb rdResponseCode.REQUEST_BLOCKED_ERROR
        && code != Earlyb rdResponseCode.T ER_SK PPED;
  }

  /**
   * Extract results from Earlyb rd response.
   */
  publ c stat c L st<Thr ftSearchResult> extractResultsFromEarlyb rdResponse(
      Earlyb rdResponse response) {
    return hasResults(response)
        ? response.getSearchResults().getResults() : Collect ons.emptyL st();
  }

  /**
   * Log t  Earlyb rd response as a cand date s ce.
   */
  publ c stat c Earlyb rdResponse debugLogAsCand dateS ce(
      Earlyb rdResponse response, T etS ce t etS ce) {
    L st<Thr ftSearchResult> results = extractResultsFromEarlyb rdResponse(response);
    debugLogAsCand dateS ce lper(results, t etS ce);
    return response;
  }

  /**
   * Log a l st of Thr ftSearchResult as a cand date s ce.
   */
  publ c stat c L st<Thr ftSearchResult> debugLogAsCand dateS ce(
      L st<Thr ftSearchResult> results, T etS ce t etS ce) {
    debugLogAsCand dateS ce lper(results, t etS ce);
    return results;
  }

  pr vate stat c vo d debugLogAsCand dateS ce lper(
      L st<Thr ftSearchResult> results, T etS ce t etS ce) {
    // debug  ssage for Earlyb rd relevance cand date s ce
    L st<Str ng> str ds = results
        .stream()
        .map(Thr ftSearchResult::get d)
        .map(Object::toStr ng)
        .collect(Collectors.toL st());
    ObjectKey debugMsgKey = ObjectKey.createT etCand dateS ceKey(
        t etS ce.na ());
    DebugManager.perObjectBas c(
        debugMsgKey,
        Str ng.format("[%s][%s] results: %s", debugMsgKey.getType(), debugMsgKey.get d(), str ds));
  }

  /**
   * Extract t  real t   response from an ex st ng response
   */
  publ c stat c Earlyb rdResponse extractRealt  Response(Earlyb rdResponse response) {
    Earlyb rdResponse realt  Response = response.deepCopy();
     f (Earlyb rdResponseUt l.hasResults(response)) {
      L st<Thr ftSearchResult> realt  Results = realt  Response.getSearchResults().getResults();
      realt  Results.clear();
      for (Thr ftSearchResult result : response.getSearchResults().getResults()) {
         f (result.getT etS ce() == Thr ftT etS ce.REALT ME_CLUSTER) {
          realt  Results.add(result);
        }
      }
    }

    return realt  Response;
  }

  /**
   * Returns an Earlyb rdResponse that should be returned by roots w n a t er was sk pped.
   *
   * @param m n d T  m nSearc dStatus D to be set on t  response.
   * @param max d T  maxSearc dStatus D to be set on t  response.
   * @param debugMsg T  debug  ssage to be set on t  response.
   * @return A response that should be returned by roots w n a t er was sk pped.
   */
  publ c stat c Earlyb rdResponse t erSk ppedRootResponse(long m n d, long max d, Str ng debugMsg) {
    return new Earlyb rdResponse(Earlyb rdResponseCode.SUCCESS, 0)
      .setSearchResults(new Thr ftSearchResults()
                        .setResults(new ArrayL st<>())
                        .setM nSearc dStatus D(m n d)
                        .setMaxSearc dStatus D(max d))
      .setDebugStr ng(debugMsg);
  }

  /**
   * Determ nes  f t  g ven response  s a success response.
   *
   * A response  s cons dered successful  f  's not null and has e  r a SUCCESS, T ER_SK PPED or
   * REQUEST_BLOCKED_ERROR response code.
   *
   * @param response T  response to c ck.
   * @return W t r t  g ven response  s successful or not.
   */
  publ c stat c boolean  sSuccessfulResponse(Earlyb rdResponse response) {
    return response != null
      && (response.getResponseCode() == Earlyb rdResponseCode.SUCCESS
          || response.getResponseCode() == Earlyb rdResponseCode.T ER_SK PPED
          || response.getResponseCode() == Earlyb rdResponseCode.REQUEST_BLOCKED_ERROR);
  }

  /**
   * F nds all unexpected nullcast statuses w h n t  g ven result. A nullcast status  s
   * unexpected  ff:
   *   1. t  t et  s a nullcast t et.
   *   2. t  t et  s NOT expl c ly requested w h {@l nk Thr ftSearchQuery#searchStatus ds}
   */
  publ c stat c Set<Long> f ndUnexpectedNullcastStatus ds(
      Thr ftSearchResults thr ftSearchResults, Earlyb rdRequest request) {
    Set<Long> status ds = new HashSet<>();
    for (Thr ftSearchResult result : thr ftSearchResults.getResults()) {
       f (result sNullcast(result) && ! sSearchStatus d(request, result.get d())) {
        status ds.add(result.get d());
      }
    }
    return status ds;
  }

  pr vate stat c boolean  sSearchStatus d(Earlyb rdRequest request, long  d) {
    return request.getSearchQuery(). sSetSearchStatus ds()
        && request.getSearchQuery().getSearchStatus ds().conta ns( d);
  }

  pr vate stat c boolean result sNullcast(Thr ftSearchResult result) {
    return result. sSet tadata() && result.get tadata(). s sNullcast();
  }
}
