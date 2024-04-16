package com.tw ter.search.earlyb rd_root. rgers;

 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;

/**
 * A RecencyResponse rger that pr or  zes not los ng results dur ng pag nat on.
 * As of now, t   rger  s used by Gn p to make sure that scroll ng returns all results.
 *
 * T  log c used for  rg ng part  ons  s a b  tr cky, because on one hand,   want to make sure
 * that   do m ss results on t  next pag nat on request; on t  ot r hand,   want to return as
 * many results as   can, and   want to set t  m nSearc dStatus D of t   rged response as low
 * as   can,  n order to m n m ze t  number of pag nat on requests.
 *
 * T   rg ng log c  s:
 *
 * Realt   cluster:
 *  1.  rge results from all part  ons
 *  2.  f at least one part  on response  s early-term nated, set earlyTerm nated = true
 *     on t   rged response
 *  3. set tr mm ngM n d = max(m nSearc dStatus Ds of all part  on responses)
 *  4. tr m all results to tr mm ngM n d
 *  5. set m nSearc dStatus D on t   rged response to tr mm ngM n d
 *  6.  f   have more than numRequested results:
 *     - keep only t  ne st numRequested results
 *     - set m nSearc dStatus D of t   rged response to t  lo st t et  D  n t  response
 *  7.  f at least one part  on response  s not early-term nated, set
 *     t erBottom d = max(m nSearc dStatus Ds of all non-early-term nated responses)
 *     (ot rw se, set t erBottom d to so  undef ned value: -1, Long.MAX_VALUE, etc.)
 *  8.  f m nSearc dStatus D of t   rged response  s t  sa  as t erBottom d,
 *     clear t  early-term nat on flag on t   rged response
 *
 * T  log c  n steps 7 and 8 can be a l tle tr cky to understand. T y bas cally say: w n  've
 * exhausted t  "least deep" part  on  n t  realt   cluster,  's t   to move to t  full
 * arch ve cluster ( f   keep go ng past t  "least deep" part  on,   m ght m ss results).
 *
 * Full arch ve cluster:
 *  1.  rge results from all part  ons
 *  2.  f at least one part  on response  s early-term nated, set earlyTerm nated = true
 *     on t   rged response
 *  3. set tr mm ngM n d to:
 *     - max(m nSearc dStatus Ds of early-term nated responses),  f at least one part  on response
 *        s early-term nated
 *     - m n(m nSearc dStatus Ds of all responses),  f all part  on responses are not
 *       early-term nated
 *  4. tr m all results to tr mm ngM n d
 *  5. set m nSearc dStatus D of t   rged response to tr mm ngM n d
 *  6.  f   have more than numRequested results:
 *     - keep only t  ne st numRequested results
 *     - set m nSearc dStatus D of t   rged response to t  lo st t et  D  n t  response
 *
 * T  log c  n step 3 can be a l tle tr cky to understand. On one hand,  f   always set
 * tr mm ngM n d to t  h g st m nSearc dStatus D, t n so  t ets at t  very bottom of so 
 * part  ons w ll never be returned. Cons der t  case:
 *
 *  part  on 1 has t ets 10, 8, 6
 *  part  on 2 has t ets 9, 7, 5
 *
 *  n t  case,   would always tr m all results to m n d = 6, and t et 5 would never be returned.
 *
 * On t  ot r hand,  f   always set tr mm ngM n d to t  lo st m nSearc dStatus D, t n  
 * m ght m ss t ets from part  ons that early-term nated. Cons der t  case:
 *
 * part  on 1 has t ets 10, 5, 3, 1 that match   query
 * part  on 2 has t ets 9, 8, 7, 6, 2 that match   query
 *
 *  f   ask for 3 results, than part  on 1 w ll return t ets 10, 5, 3, and part  on 2 w ll
 * return t ets 9, 8, 7.  f   set tr mm ngM n d = m n(m nSearc dStatus Ds), t n t  next
 * pag nat on request w ll have [max_ d = 2], and   w ll m ss t et 6.
 *
 * So t   ntu  on  re  s that  f   have an early-term nated response,   cannot set
 * tr mm ngM n d to so th ng lo r than t  m nSearc dStatus D returned by that part  on
 * (ot rw se   m ght m ss results from that part  on). Ho ver,  f  've exhausted all
 * part  ons, t n  's OK to not tr m any result, because t ers do not  ntersect, so   w ll not
 * m ss any result from t  next t er once   get t re.
 */
publ c class Str ctRecencyResponse rger extends RecencyResponse rger {
  pr vate stat c f nal SearchT  rStats STR CT_RECENCY_T MER_AVG =
      SearchT  rStats.export(" rge_recency_str ct", T  Un .NANOSECONDS, false, true);

  @V s bleForTest ng
  stat c f nal EarlyTerm nat onTr mm ngStats PART T ON_MERG NG_EARLY_TERM NAT ON_TR MM NG_STATS =
      new EarlyTerm nat onTr mm ngStats("str ct_recency_part  on_ rg ng");

  @V s bleForTest ng
  stat c f nal EarlyTerm nat onTr mm ngStats T ER_MERG NG_EARLY_TERM NAT ON_TR MM NG_STATS =
      new EarlyTerm nat onTr mm ngStats("str ct_recency_t er_ rg ng");

  pr vate f nal Earlyb rdCluster cluster;

  publ c Str ctRecencyResponse rger(Earlyb rdRequestContext requestContext,
                                     L st<Future<Earlyb rdResponse>> responses,
                                     ResponseAccumulator mode,
                                     Earlyb rdFeatureSc ma rger featureSc ma rger,
                                     Earlyb rdCluster cluster) {
    super(requestContext, responses, mode, featureSc ma rger);
    t .cluster = cluster;
  }

  @Overr de
  protected SearchT  rStats get rgedResponseT  r() {
    return STR CT_RECENCY_T MER_AVG;
  }

  /**
   * Unl ke {@l nk com.tw ter.search.earlyb rd_root. rgers.RecencyResponse rger}, t   thod
   * takes a much s mpler approach by just tak ng t  max of t  maxSearc dStatus ds.
   *
   * Also, w n no maxSearc dStatus d  s ava lable at all, Long.M N_VALUE  s used  nstead of
   * Long.MAX_VALUE. T  ensures that   don't return any result  n t se cases.
   */
  @Overr de
  protected long f ndMaxFullySearc dStatus D() {
    return accumulatedResponses.getMax ds(). sEmpty()
        ? Long.M N_VALUE : Collect ons.max(accumulatedResponses.getMax ds());
  }

  /**
   * T   thod  s subtly d fferent from t  base class vers on: w n no m nSearc dStatus d  s
   * ava lable at all, Long.MAX_VALUE  s used  nstead of Long.M N_VALUE. T  ensures that  
   * don't return any result  n t se cases.
   */
  @Overr de
  protected long f ndM nFullySearc dStatus D() {
    L st<Long> m n ds = accumulatedResponses.getM n ds();
     f (m n ds. sEmpty()) {
      return Long.MAX_VALUE;
    }

     f (accumulatedResponses. s rg ngPart  onsW h nAT er()) {
      return getTr mm ngM n d();
    }

    // W n  rg ng t ers, t  m n  D should be t  smallest among t  m n  Ds.
    return Collect ons.m n(m n ds);
  }

  @Overr de
  protected Tr mStats tr mResults(
      Thr ftSearchResults searchResults, long  rgedM n, long  rgedMax) {
     f (!searchResults. sSetResults() || searchResults.getResultsS ze() == 0) {
      // no results, no tr mm ng needed
      return Tr mStats.EMPTY_STATS;
    }

    Tr mStats tr mStats = new Tr mStats();
    tr mExactDups(searchResults, tr mStats);
    f lterResultsBy rgedM nMax ds(searchResults,  rgedMax,  rgedM n, tr mStats);
     nt numResults = computeNumResultsToKeep();
     f (searchResults.getResultsS ze() > numResults) {
      tr mStats.setResultsTruncatedFromTa lCount(searchResults.getResultsS ze() - numResults);
      searchResults.setResults(searchResults.getResults().subL st(0, numResults));
    }

    return tr mStats;
  }

  /**
   * T   thod  s d fferent from t  base class vers on because w n m nResult d  s b gger
   * than current rgedM n,   always take m nResult d.
   *  f   don't do t ,   would lose results.
   *
   *  llustrat on w h an example. Assum ng   are outs de of t  lag threshold.
   * Num results requested: 3
   * Response 1:  m n: 100   max: 900   results:  400, 500, 600
   * Response 2:  m n: 300   max: 700   results:  350, 450, 550
   *
   *  rged results: 600, 550, 500
   *  rged max: 900
   *  rged m n:   could take 300 (m n d), or take 500 (m nResult d).
   *
   *  f   take m n d, and use 300 as t  pag nat on cursor,  'd lose results
   * 350 and 450 w n   pag nate. So   have to take m nResult d  re.
   */
  @Overr de
  protected vo d set rgedM nSearc dStatus d(
      Thr ftSearchResults searchResults,
      long current rgedM n,
      boolean results reTr m d) {
     f (accumulatedResponses.getM n ds(). sEmpty()) {
      return;
    }

    long m n d = current rgedM n;
     f (results reTr m d
        && (searchResults != null)
        && searchResults. sSetResults()
        && (searchResults.getResultsS ze() > 0)) {
      L st<Thr ftSearchResult> results = searchResults.getResults();
      m n d = results.get(results.s ze() - 1).get d();
    }

    searchResults.setM nSearc dStatus D(m n d);
  }

  @Overr de
  protected boolean clearEarlyTerm nat on fReach ngT erBottom(Earlyb rdResponse  rgedResponse) {
     f (Earlyb rdCluster. sArch ve(cluster)) {
      //   don't need to worry about t  t er bottom w n  rg ng part  on responses  n t  full
      // arch ve cluster:  f all part  ons  re exhausted and   d dn't tr m t  results, t n
      // t  early-term nated flag on t   rged response w ll be false.  f at least one part  on
      //  s early-term nated, or   tr m d so  results, t n t  ealry-term nated flag on t 
      //  rged response w ll be true, and   should cont nue gett ng results from t  t er before
      //   move to t  next one.
      return false;
    }

    Thr ftSearchResults searchResults =  rgedResponse.getSearchResults();
     f (searchResults.getM nSearc dStatus D() == getT erBottom d()) {
       rgedResponse.getEarlyTerm nat on nfo().setEarlyTerm nated(false);
       rgedResponse.getEarlyTerm nat on nfo().unset rgedEarlyTerm nat onReasons();
      response ssageBu lder.debugVerbose(
          "Set earlyterm nat on to false because m nSearc dStatus d  s t er bottom");
      return true;
    }
    return false;
  }

  @Overr de
  protected boolean shouldEarlyTerm nateW nEnoughTr m dResults() {
    return false;
  }

  @Overr de
  protected f nal EarlyTerm nat onTr mm ngStats getEarlyTerm nat onTr mm ngStatsForPart  ons() {
    return PART T ON_MERG NG_EARLY_TERM NAT ON_TR MM NG_STATS;
  }

  @Overr de
  protected f nal EarlyTerm nat onTr mm ngStats getEarlyTerm nat onTr mm ngStatsForT ers() {
    return T ER_MERG NG_EARLY_TERM NAT ON_TR MM NG_STATS;
  }

  /** Determ nes t  bottom of t  realt   cluster, based on t  part  on responses. */
  pr vate long getT erBottom d() {
    Precond  ons.c ckState(!Earlyb rdCluster. sArch ve(cluster));

    long t erBottom d = -1;
    for (Earlyb rdResponse response : accumulatedResponses.getSuccessResponses()) {
       f (! sEarlyTerm nated(response)
          && response. sSetSearchResults()
          && response.getSearchResults(). sSetM nSearc dStatus D()
          && (response.getSearchResults().getM nSearc dStatus D() > t erBottom d)) {
        t erBottom d = response.getSearchResults().getM nSearc dStatus D();
      }
    }

    return t erBottom d;
  }

  /** Determ nes t  m n d to wh ch all results should be tr m d. */
  pr vate long getTr mm ngM n d() {
    L st<Long> m n ds = accumulatedResponses.getM n ds();
    Precond  ons.c ckArgu nt(!m n ds. sEmpty());

     f (!Earlyb rdCluster. sArch ve(cluster)) {
      return Collect ons.max(m n ds);
    }

    long maxOfEarlyTerm natedM ns = -1;
    long m nOfAllM ns = Long.MAX_VALUE;
    for (Earlyb rdResponse response : accumulatedResponses.getSuccessResponses()) {
       f (response. sSetSearchResults()
          && response.getSearchResults(). sSetM nSearc dStatus D()) {
        long m n d = response.getSearchResults().getM nSearc dStatus D();
        m nOfAllM ns = Math.m n(m nOfAllM ns, m n d);
         f ( sEarlyTerm nated(response)) {
          maxOfEarlyTerm natedM ns = Math.max(maxOfEarlyTerm natedM ns, m n d);
        }
      }
    }
     f (maxOfEarlyTerm natedM ns >= 0) {
      return maxOfEarlyTerm natedM ns;
    } else {
      return m nOfAllM ns;
    }
  }

  /** Determ nes  f t  g ven earlyb rd response  s early term nated. */
  pr vate boolean  sEarlyTerm nated(Earlyb rdResponse response) {
    return response. sSetEarlyTerm nat on nfo()
      && response.getEarlyTerm nat on nfo(). sEarlyTerm nated();
  }
}
