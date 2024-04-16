package com.tw ter.search.earlyb rd_root. rgers;

 mport java.ut l.ArrayL st;
 mport java.ut l.EnumMap;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l.earlyb rd.Response rgerUt ls;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;

/**
 * Accumulates Earlyb rdResponse's and determ nes w n to early term nate.
 */
publ c abstract class ResponseAccumulator {

  @V s bleForTest ng
  stat c class M nMaxSearc d dStats {
    /** How many results d d   actually c ck */
    pr vate f nal SearchCounter c ckedMaxM nSearc dStatus d;
    pr vate f nal SearchCounter unsetMaxSearc dStatus d;
    pr vate f nal SearchCounter unsetM nSearc dStatus d;
    pr vate f nal SearchCounter unsetMaxAndM nSearc dStatus d;
    pr vate f nal SearchCounter sa M nMaxSearc d dW houtResults;
    pr vate f nal SearchCounter sa M nMaxSearc d dW hOneResult;
    pr vate f nal SearchCounter sa M nMaxSearc d dW hResults;
    pr vate f nal SearchCounter fl ppedM nMaxSearc d d;

    M nMaxSearc d dStats(Earlyb rdRequestType requestType) {
      Str ng statPref x = " rge_ lper_" + requestType.getNormal zedNa ();

      c ckedMaxM nSearc dStatus d = SearchCounter.export(statPref x
          + "_max_m n_searc d_ d_c cks");
      unsetMaxSearc dStatus d = SearchCounter.export(statPref x
          + "_unset_max_searc d_status_ d");
      unsetM nSearc dStatus d = SearchCounter.export(statPref x
          + "_unset_m n_searc d_status_ d");
      unsetMaxAndM nSearc dStatus d = SearchCounter.export(statPref x
          + "_unset_max_and_m n_searc d_status_ d");
      sa M nMaxSearc d dW houtResults = SearchCounter.export(statPref x
          + "_sa _m n_max_searc d_ d_w hout_results");
      sa M nMaxSearc d dW hOneResult = SearchCounter.export(statPref x
          + "_sa _m n_max_searc d_ d_w h_one_results");
      sa M nMaxSearc d dW hResults = SearchCounter.export(statPref x
          + "_sa _m n_max_searc d_ d_w h_results");
      fl ppedM nMaxSearc d d = SearchCounter.export(statPref x
          + "_fl pped_m n_max_searc d_ d");
    }

    @V s bleForTest ng
    SearchCounter getC ckedMaxM nSearc dStatus d() {
      return c ckedMaxM nSearc dStatus d;
    }

    @V s bleForTest ng
    SearchCounter getFl ppedM nMaxSearc d d() {
      return fl ppedM nMaxSearc d d;
    }

    @V s bleForTest ng
    SearchCounter getUnsetMaxSearc dStatus d() {
      return unsetMaxSearc dStatus d;
    }

    @V s bleForTest ng
    SearchCounter getUnsetM nSearc dStatus d() {
      return unsetM nSearc dStatus d;
    }

    @V s bleForTest ng
    SearchCounter getUnsetMaxAndM nSearc dStatus d() {
      return unsetMaxAndM nSearc dStatus d;
    }

    @V s bleForTest ng
    SearchCounter getSa M nMaxSearc d dW houtResults() {
      return sa M nMaxSearc d dW houtResults;
    }

    @V s bleForTest ng
    SearchCounter getSa M nMaxSearc d dW hOneResult() {
      return sa M nMaxSearc d dW hOneResult;
    }

    @V s bleForTest ng
    SearchCounter getSa M nMaxSearc d dW hResults() {
      return sa M nMaxSearc d dW hResults;
    }
  }

  @V s bleForTest ng
  stat c f nal Map<Earlyb rdRequestType, M nMaxSearc d dStats> M N_MAX_SEARCHED_ D_STATS_MAP;
  stat c {
    EnumMap<Earlyb rdRequestType, M nMaxSearc d dStats> statsMap
        = Maps.newEnumMap(Earlyb rdRequestType.class);
    for (Earlyb rdRequestType earlyb rdRequestType : Earlyb rdRequestType.values()) {
      statsMap.put(earlyb rdRequestType, new M nMaxSearc d dStats(earlyb rdRequestType));
    }

    M N_MAX_SEARCHED_ D_STATS_MAP = Maps. mmutableEnumMap(statsMap);
  }

  //  rge has encountered at least one early term nated response.
  pr vate boolean foundEarlyTerm nat on = false;
  // Empty but successful response counter (E.g. w n a t er or part  on  s sk pped)
  pr vate  nt successfulEmptyResponseCount = 0;
  // T  l st of t  successful responses from all earlyb rd futures. T  does not  nclude empty
  // responses resulted from null requests.
  pr vate f nal L st<Earlyb rdResponse> successResponses = new ArrayL st<>();
  // T  l st of t  error responses from all earlyb rd futures.
  pr vate f nal L st<Earlyb rdResponse> errorResponses = new ArrayL st<>();
  // t  l st of max status ds seen  n each earlyb rd.
  pr vate f nal L st<Long> max ds = new ArrayL st<>();
  // t  l st of m n status ds seen  n each earlyb rd.
  pr vate f nal L st<Long> m n ds = new ArrayL st<>();

  pr vate  nt numResponses = 0;

  pr vate  nt numResultsAccumulated = 0;
  pr vate  nt numSearc dSeg nts = 0;

  /**
   * Returns a str ng that can be used for logg ng to  dent fy a s ngle response out of all t 
   * responses that are be ng  rged.
   *
   * @param response ndex t   ndex of a response's part  on or t er, depend ng on t  type of
   *                      responses be ng accumulated.
   * @param numTotalResponses t  total number of part  ons or t ers that are be ng  rged.
   */
  publ c abstract Str ng getNa ForLogg ng( nt response ndex,  nt numTotalResponses);

  /**
   * Returns a str ng that  s used to export per-Earlyb rdResponseCode stats for part  ons and t ers.
   *
   * @param response ndex t   ndex of of a response's part  on or t er.
   * @param numTotalResponses t  total number of part  ons or t ers that are be ng  rged.
   * @return a str ng that  s used to export per-Earlyb rdResponseCode stats for part  ons and t ers.
   */
  publ c abstract Str ng getNa ForEarlyb rdResponseCodeStats(
       nt response ndex,  nt numTotalResponses);

  abstract boolean shouldEarlyTerm nate rge(EarlyTerm nateT er rgePred cate  rger);

  /**
   * Add a Earlyb rdResponse
   */
  publ c vo d addResponse(Earlyb rdResponseDebug ssageBu lder response ssageBu lder,
                          Earlyb rdRequest request,
                          Earlyb rdResponse response) {
    numResponses++;
    numSearc dSeg nts += response.getNumSearc dSeg nts();

     f ( sSk ppedResponse(response)) {
      // T   s an empty response, no process ng  s requ red, just need to update stat st cs.
      successfulEmptyResponseCount++;
      handleSk ppedResponse(response.getResponseCode());
    } else  f ( sErrorResponse(response)) {
      errorResponses.add(response);
      handleErrorResponse(response);
    } else {
      handleSuccessfulResponse(response ssageBu lder, request, response);
    }
  }

  pr vate boolean  sErrorResponse(Earlyb rdResponse response) {
    return !response. sSetResponseCode()
        || response.getResponseCode() != Earlyb rdResponseCode.SUCCESS;
  }

  pr vate boolean  sSk ppedResponse(Earlyb rdResponse response) {
    return response. sSetResponseCode()
        && (response.getResponseCode() == Earlyb rdResponseCode.PART T ON_SK PPED
        || response.getResponseCode() == Earlyb rdResponseCode.T ER_SK PPED);
  }

  /**
   * Record a response correspond ng to a sk pped part  on or sk pped t er.
   */
  protected abstract vo d handleSk ppedResponse(Earlyb rdResponseCode responseCode);

  /**
   * Handle an error response
   */
  protected abstract vo d handleErrorResponse(Earlyb rdResponse response);

  /**
   * Subclasses can overr de t  to perform more successful response handl ng.
   */
  protected vo d extraSuccessfulResponseHandler(Earlyb rdResponse response) { }

 /**
  * W t r t   lper  s for  rg ng results from part  ons w h n a s ngle t er.
  */
  protected f nal boolean  s rg ngPart  onsW h nAT er() {
    return ! s rg ngAcrossT ers();
  }

  /**
   * W t r t   lper  s for  rg ng results across d fferent t ers.
   */
  protected abstract boolean  s rg ngAcrossT ers();


  /**
   * Record a successful response.
   */
  publ c f nal vo d handleSuccessfulResponse(
      Earlyb rdResponseDebug ssageBu lder response ssageBu lder,
      Earlyb rdRequest request,
      Earlyb rdResponse response) {
    successResponses.add(response);
     f (response. sSetSearchResults()) {
      Thr ftSearchResults searchResults = response.getSearchResults();
      numResultsAccumulated += searchResults.getResultsS ze();

      recordM nMaxSearc d dsAndUpdateStats(response ssageBu lder, request, response,
          searchResults);
    }
     f (response. sSetEarlyTerm nat on nfo()
        && response.getEarlyTerm nat on nfo(). sEarlyTerm nated()) {
      foundEarlyTerm nat on = true;
    }
    extraSuccessfulResponseHandler(response);
  }

  pr vate vo d recordM nMaxSearc d dsAndUpdateStats(
      Earlyb rdResponseDebug ssageBu lder response ssageBu dler,
      Earlyb rdRequest request,
      Earlyb rdResponse response,
      Thr ftSearchResults searchResults) {

    boolean  sMax dSet = searchResults. sSetMaxSearc dStatus D();
    boolean  sM n dSet = searchResults. sSetM nSearc dStatus D();

     f ( sMax dSet) {
      max ds.add(searchResults.getMaxSearc dStatus D());
    }
     f ( sM n dSet) {
      m n ds.add(searchResults.getM nSearc dStatus D());
    }

    updateM nMax dStats(response ssageBu dler, request, response, searchResults,  sMax dSet,
         sM n dSet);
  }

  pr vate vo d updateM nMax dStats(
      Earlyb rdResponseDebug ssageBu lder response ssageBu lder,
      Earlyb rdRequest request,
      Earlyb rdResponse response,
      Thr ftSearchResults searchResults,
      boolean  sMax dSet,
      boolean  sM n dSet) {
    // Now just track t  stats.
    Earlyb rdRequestType requestType = Earlyb rdRequestType.of(request);
    M nMaxSearc d dStats m nMaxSearc d dStats = M N_MAX_SEARCHED_ D_STATS_MAP.get(requestType);

    m nMaxSearc d dStats.c ckedMaxM nSearc dStatus d. ncre nt();
     f ( sMax dSet &&  sM n dSet) {
       f (searchResults.getM nSearc dStatus D() > searchResults.getMaxSearc dStatus D()) {
        //   do not expect t  case to happen  n product on.
        m nMaxSearc d dStats.fl ppedM nMaxSearc d d. ncre nt();
      } else  f (searchResults.getResultsS ze() == 0
          && searchResults.getMaxSearc dStatus D() == searchResults.getM nSearc dStatus D()) {
        m nMaxSearc d dStats.sa M nMaxSearc d dW houtResults. ncre nt();
        response ssageBu lder.debugVerbose(
            "Got no results, and sa  m n/max searc d  ds. Request: %s, Response: %s",
            request, response);
      } else  f (searchResults.getResultsS ze() == 1
          && searchResults.getMaxSearc dStatus D() == searchResults.getM nSearc dStatus D()) {
        m nMaxSearc d dStats.sa M nMaxSearc d dW hOneResult. ncre nt();
        response ssageBu lder.debugVerbose(
            "Got one results, and sa  m n/max searc d  ds. Request: %s, Response: %s",
            request, response);
      } else  f (searchResults.getMaxSearc dStatus D()
          == searchResults.getM nSearc dStatus D()) {
        m nMaxSearc d dStats.sa M nMaxSearc d dW hResults. ncre nt();
        response ssageBu lder.debugVerbose(
            "Got mult ple results, and sa  m n/max searc d  ds. Request: %s, Response: %s",
            request, response);
      }
    } else  f (! sMax dSet &&  sM n dSet) {
      //   do not expect t  case to happen  n product on.
      m nMaxSearc d dStats.unsetMaxSearc dStatus d. ncre nt();
      response ssageBu lder.debugVerbose(
          "Got unset maxSearc dStatus D. Request: %s, Response: %s", request, response);
    } else  f ( sMax dSet && ! sM n dSet) {
      //   do not expect t  case to happen  n product on.
      m nMaxSearc d dStats.unsetM nSearc dStatus d. ncre nt();
      response ssageBu lder.debugVerbose(
          "Got unset m nSearc dStatus D. Request: %s, Response: %s", request, response);
    } else {
      Precond  ons.c ckState(! sMax dSet && ! sM n dSet);
      m nMaxSearc d dStats.unsetMaxAndM nSearc dStatus d. ncre nt();
      response ssageBu lder.debugVerbose(
          "Got unset maxSearc dStatus D and m nSearc dStatus D. Request: %s, Response: %s",
          request, response);
    }
  }


  /**
   * Return part  on counts w h number of part  ons, number of successful responses, and l st of
   * responses per t er.
   */
  publ c abstract AccumulatedResponses.Part  onCounts getPart  onCounts();

  publ c f nal AccumulatedResponses getAccumulatedResults() {
    return new AccumulatedResponses(successResponses,
                                    errorResponses,
                                    max ds,
                                    m n ds,
                                    Response rgerUt ls. rgeEarlyTerm nat on nfo(successResponses),
                                     s rg ngAcrossT ers(),
                                    getPart  onCounts(),
                                    getNumSearc dSeg nts());
  }

  // Getters are only  ntended to be used by subclasses.  Ot r users should get data from
  // AccumulatedResponses

   nt getNumResponses() {
    return numResponses;
  }

   nt getNumSearc dSeg nts() {
    return numSearc dSeg nts;
  }

  L st<Earlyb rdResponse> getSuccessResponses() {
    return successResponses;
  }

   nt getNumResultsAccumulated() {
    return numResultsAccumulated;
  }

   nt getSuccessfulEmptyResponseCount() {
    return successfulEmptyResponseCount;
  }

  boolean foundError() {
    return !errorResponses. sEmpty();
  }

  boolean foundEarlyTerm nat on() {
    return foundEarlyTerm nat on;
  }
}
