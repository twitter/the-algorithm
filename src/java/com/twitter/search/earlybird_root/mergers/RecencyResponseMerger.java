package com.tw ter.search.earlyb rd_root. rgers;

 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.query.thr ftjava.EarlyTerm nat on nfo;
 mport com.tw ter.search.common.relevance.ut ls.ResultComparators;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.collectors.Recency rgeCollector;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;

 mport stat c com.tw ter.search.earlyb rd_root. rgers.RecencyResponse rger
    .EarlyTerm nat onTr mm ngStats.Type.ALREADY_EARLY_TERM NATED;
 mport stat c com.tw ter.search.earlyb rd_root. rgers.RecencyResponse rger
    .EarlyTerm nat onTr mm ngStats.Type.F LTERED;
 mport stat c com.tw ter.search.earlyb rd_root. rgers.RecencyResponse rger
    .EarlyTerm nat onTr mm ngStats.Type.F LTERED_AND_TRUNCATED;
 mport stat c com.tw ter.search.earlyb rd_root. rgers.RecencyResponse rger
    .EarlyTerm nat onTr mm ngStats.Type.NOT_EARLY_TERM NATED;
 mport stat c com.tw ter.search.earlyb rd_root. rgers.RecencyResponse rger
    .EarlyTerm nat onTr mm ngStats.Type.TERM NATED_GOT_EXACT_NUM_RESULTS;
 mport stat c com.tw ter.search.earlyb rd_root. rgers.RecencyResponse rger
    .EarlyTerm nat onTr mm ngStats.Type.TRUNCATED;

/**
 *  rger class to  rge recency search Earlyb rdResponse objects.
 */
publ c class RecencyResponse rger extends Earlyb rdResponse rger {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(RecencyResponse rger.class);

  pr vate stat c f nal SearchT  rStats RECENCY_T MER =
      SearchT  rStats.export(" rge_recency", T  Un .NANOSECONDS, false, true);

  @V s bleForTest ng
  stat c f nal Str ng TERM NATED_COLLECTED_ENOUGH_RESULTS =
      "term nated_collected_enough_results";

  // Allo d repl cat on lag relat ve to all repl cas.  Repl cat on lag exceed ng
  // t  amount may result  n so  t ets from t  repl ca not returned  n search.
  pr vate stat c f nal long ALLOWED_REPL CAT ON_LAG_MS = 10000;

  pr vate stat c f nal double SUCCESSFUL_RESPONSE_THRESHOLD = 0.9;

  @V s bleForTest ng
  stat c f nal SearchCounter RECENCY_ZERO_RESULT_COUNT_AFTER_F LTER NG_MAX_M N_ DS =
      SearchCounter.export(" rger_recency_zero_result_count_after_f lter ng_max_m n_ ds");

  @V s bleForTest ng
  stat c f nal SearchCounter RECENCY_TR MMED_TOO_MANY_RESULTS_COUNT =
      SearchCounter.export(" rger_recency_tr m d_too_many_results_count");

  pr vate stat c f nal SearchCounter RECENCY_T ER_MERGE_EARLY_TERM NATED_W TH_NOT_ENOUGH_RESULTS =
      SearchCounter.export(" rger_recency_t er_ rge_early_term nated_w h_not_enough_results");

  pr vate stat c f nal SearchCounter RECENCY_CLEARED_EARLY_TERM NAT ON_COUNT =
      SearchCounter.export(" rger_recency_cleared_early_term nat on_count");

  /**
   * Results  re truncated because  rged results exceeded t  requested numResults.
   */
  @V s bleForTest ng
  stat c f nal Str ng MERG NG_EARLY_TERM NAT ON_REASON_TRUNCATED =
      "root_ rg ng_truncated_results";

  /**
   * Results that  re  re f ltered smaller than  rged m nSearc dStatus d  re f ltered out.
   */
  @V s bleForTest ng
  stat c f nal Str ng MERG NG_EARLY_TERM NAT ON_REASON_F LTERED =
      "root_ rg ng_f ltered_results";

  @V s bleForTest ng
  stat c f nal EarlyTerm nat onTr mm ngStats PART T ON_MERG NG_EARLY_TERM NAT ON_TR MM NG_STATS =
      new EarlyTerm nat onTr mm ngStats("recency_part  on_ rg ng");

  @V s bleForTest ng
  stat c f nal EarlyTerm nat onTr mm ngStats T ER_MERG NG_EARLY_TERM NAT ON_TR MM NG_STATS =
      new EarlyTerm nat onTr mm ngStats("recency_t er_ rg ng");

  @V s bleForTest ng
  stat c class EarlyTerm nat onTr mm ngStats {

    enum Type {
      /**
       * T  whole result was not term nated at all.
       */
      NOT_EARLY_TERM NATED,
      /**
       * Was term nated before   d d any tr mm ng.
       */
      ALREADY_EARLY_TERM NATED,
      /**
       * Was not term nated w n  rged, but results  re f ltered due to m n/max ranges.
       */
      F LTERED,
      /**
       * Was not term nated w n  rged, but results  re truncated.
       */
      TRUNCATED,
      /**
       * Was not term nated w n  rged, but results  re f ltered due to m n/max ranges and
       * truncated.
       */
      F LTERED_AND_TRUNCATED,
      /**
       * W n t  search asks for X result, and   get exactly X results back, w hout tr mm ng
       * or truncat ng on t  ta l s de (m n_ d s de),   st ll mark t  search as early term nated.
       * T   s because later t ers poss bly has more results.
       */
      TERM NATED_GOT_EXACT_NUM_RESULTS,
    }

    /**
     * A counter track ng  rged responses for each {@l nk EarlyTerm nat onTr mm ngStats.Type}
     * def ne above.
     */
    pr vate f nal  mmutableMap<Type, SearchCounter> searchCounterMap;

    EarlyTerm nat onTr mm ngStats(Str ng pref x) {
      Map<Type, SearchCounter> tempMap = Maps.newEnumMap(Type.class);

      tempMap.put(NOT_EARLY_TERM NATED,
          SearchCounter.export(pref x + "_not_early_term nated_after_ rg ng"));
      tempMap.put(ALREADY_EARLY_TERM NATED,
          SearchCounter.export(pref x + "_early_term nated_before_ rge_tr mm ng"));
      tempMap.put(TRUNCATED,
          SearchCounter.export(pref x + "_early_term nated_after_ rg ng_truncated"));
      tempMap.put(F LTERED,
          SearchCounter.export(pref x + "_early_term nated_after_ rg ng_f ltered"));
      tempMap.put(F LTERED_AND_TRUNCATED,
          SearchCounter.export(pref x + "_early_term nated_after_ rg ng_f ltered_and_truncated"));
      tempMap.put(TERM NATED_GOT_EXACT_NUM_RESULTS,
          SearchCounter.export(pref x + "_early_term nated_after_ rg ng_got_exact_num_results"));

      searchCounterMap = Maps. mmutableEnumMap(tempMap);
    }

    publ c SearchCounter getCounterFor(Type type) {
      return searchCounterMap.get(type);
    }
  }

  pr vate f nal Earlyb rdFeatureSc ma rger featureSc ma rger;

  publ c RecencyResponse rger(Earlyb rdRequestContext requestContext,
                               L st<Future<Earlyb rdResponse>> responses,
                               ResponseAccumulator mode,
                               Earlyb rdFeatureSc ma rger featureSc ma rger) {
    super(requestContext, responses, mode);
    t .featureSc ma rger = featureSc ma rger;
  }

  @Overr de
  protected double getDefaultSuccessResponseThreshold() {
    return SUCCESSFUL_RESPONSE_THRESHOLD;
  }

  @Overr de
  protected SearchT  rStats get rgedResponseT  r() {
    return RECENCY_T MER;
  }

  @Overr de
  protected Earlyb rdResponse  nternal rge(Earlyb rdResponse  rgedResponse) {
    // T   rged maxSearc dStatus d and m nSearc dStatus d
    long max d = f ndMaxFullySearc dStatus D();
    long m n d = f ndM nFullySearc dStatus D();

    Recency rgeCollector collector = new Recency rgeCollector(responses.s ze());
     nt totalResultS ze = addResponsesToCollector(collector);
    Thr ftSearchResults searchResults = collector.getAllSearchResults();

    Tr mStats tr mStats = tr mResults(searchResults, m n d, max d);
    set rgedMaxSearc dStatus d(searchResults, max d);
    set rgedM nSearc dStatus d(
        searchResults, m n d, tr mStats.getResultsTruncatedFromTa lCount() > 0);

     rgedResponse.setSearchResults(searchResults);

    // Overr de so  components of t  response as appropr ate to real-t  .
    searchResults.setH Counts(aggregateH CountMap());
     f (accumulatedResponses. s rg ngPart  onsW h nAT er()
        && clearEarlyTerm nat on fReach ngT erBottom( rgedResponse)) {
      RECENCY_CLEARED_EARLY_TERM NAT ON_COUNT. ncre nt();
    } else {
      setEarlyTerm nat onForTr m dResults( rgedResponse, tr mStats);
    }

    response ssageBu lder.debugVerbose("H s: %s %s", totalResultS ze, tr mStats);
    response ssageBu lder.debugVerbose(
        "Hash Part  oned Earlyb rd call completed successfully: %s",  rgedResponse);

    featureSc ma rger.collectAndSetFeatureSc ma nResponse(
        searchResults,
        requestContext,
        " rger_recency_t er",
        accumulatedResponses.getSuccessResponses());

    return  rgedResponse;
  }

  /**
   * W n   reac d t er bottom, pag nat on can stop work ng even though   haven't got
   * all results. e.g.
   * Results from part  on 1:  [101 91 81], m nSearc dStatus d  s 81
   * Results from Part  on 2:  [102 92],  m nSearc dStatus d  s 92, not early term nated.
   *
   * After  rge,   get [102, 101, 92], w h m nResult d == 92. S nce results from
   * part  on 2  s not early term nated, 92  s t  t er bottom  re. S nce results are
   * f ltered, early term nat on for  rged result  s set to true, so blender w ll call aga n,
   * w h maxDoc d == 91. T  t     get result:
   * Results from part  on 1: [91 81], m nSearc dStatus d  s 81
   * Results from part  on 2: [], m nSearc dStatus d  s st ll 92
   * After  rge   get [] and m nSearc dStatus d  s st ll 92. No progress can be made on
   * pag nat on and cl ents get stuck.
   *
   * So  n t  case,   clear t  early term nat on flag to tell blender t re  s no more
   * result  n t  t er. T ets below t er bottom w ll be m ssed, but that also happens
   * w hout t  step, as t  next pag nat on call w ll return empty results anyway.
   * So even  f t re  s NOT overlap bet en t ers, t   s st ll better.
   *
   * Return true  f early term nat on  s cleared due to t , ot rw se return false.
   * To be safe,   do noth ng  re to keep ex st ng behav or and only overr de    n
   * Str ctRecencyResponse rger.
   */
  protected boolean clearEarlyTerm nat on fReach ngT erBottom(Earlyb rdResponse  rgedResponse) {
    return false;
  }

  /**
   * Determ nes  f t   rged response should be early-term nated w n   has exactly as many
   * tr m d results as requested, as  s not early-term nated because of ot r reasons.
   */
  protected boolean shouldEarlyTerm nateW nEnoughTr m dResults() {
    return true;
  }

  /**
   *  f t  end results  re tr m d  n any way, reflect that  n t  response as a query that was
   * early term nated. A response can be e  r (1) truncated because    rged more results than
   * what was asked for w h numResults, or (2)   f ltered results that  re smaller than t 
   *  rged m nSearc dStatus d.
   *
   * @param  rgedResponse t   rged response.
   * @param tr mStats tr m stats for t   rge.
   */
  pr vate vo d setEarlyTerm nat onForTr m dResults(
      Earlyb rdResponse  rgedResponse,
      Tr mStats tr mStats) {

    response ssageBu lder.debugVerbose("C ck ng for  rge tr mm ng, tr mStats %s", tr mStats);

    EarlyTerm nat onTr mm ngStats stats = getEarlyTerm nat onTr mm ngStats();

    EarlyTerm nat on nfo earlyTerm nat on nfo =  rgedResponse.getEarlyTerm nat on nfo();
    Precond  ons.c ckNotNull(earlyTerm nat on nfo);

     f (!earlyTerm nat on nfo. sEarlyTerm nated()) {
       f (tr mStats.getM n dF lterCount() > 0 || tr mStats.getResultsTruncatedFromTa lCount() > 0) {
        response ssageBu lder.debugVerbose("Sett ng early term nat on, tr mStats: %s, results: %s",
            tr mStats,  rgedResponse);

        earlyTerm nat on nfo.setEarlyTerm nated(true);
        addEarlyTerm nat onReasons(earlyTerm nat on nfo, tr mStats);

         f (tr mStats.getM n dF lterCount() > 0
            && tr mStats.getResultsTruncatedFromTa lCount() > 0) {
          stats.getCounterFor(F LTERED_AND_TRUNCATED). ncre nt();
        } else  f (tr mStats.getM n dF lterCount() > 0) {
          stats.getCounterFor(F LTERED). ncre nt();
        } else  f (tr mStats.getResultsTruncatedFromTa lCount() > 0) {
          stats.getCounterFor(TRUNCATED). ncre nt();
        } else {
          Precond  ons.c ckState(false, " nval d Tr mStats: %s", tr mStats);
        }
      } else  f ((computeNumResultsToKeep() ==  rgedResponse.getSearchResults().getResultsS ze())
                 && shouldEarlyTerm nateW nEnoughTr m dResults()) {
        earlyTerm nat on nfo.setEarlyTerm nated(true);
        earlyTerm nat on nfo.addTo rgedEarlyTerm nat onReasons(
            TERM NATED_COLLECTED_ENOUGH_RESULTS);
        stats.getCounterFor(TERM NATED_GOT_EXACT_NUM_RESULTS). ncre nt();
      } else {
        stats.getCounterFor(NOT_EARLY_TERM NATED). ncre nt();
      }
    } else {
      stats.getCounterFor(ALREADY_EARLY_TERM NATED). ncre nt();
      // Even  f t  results  re already marked as early term nated,   can add add  onal
      // reasons for debugg ng ( f t   rged results  re f ltered or truncated).
      addEarlyTerm nat onReasons(earlyTerm nat on nfo, tr mStats);
    }
  }

  pr vate vo d addEarlyTerm nat onReasons(
      EarlyTerm nat on nfo earlyTerm nat on nfo,
      Tr mStats tr mStats) {

     f (tr mStats.getM n dF lterCount() > 0) {
      earlyTerm nat on nfo.addTo rgedEarlyTerm nat onReasons(
          MERG NG_EARLY_TERM NAT ON_REASON_F LTERED);
    }

     f (tr mStats.getResultsTruncatedFromTa lCount() > 0) {
      earlyTerm nat on nfo.addTo rgedEarlyTerm nat onReasons(
          MERG NG_EARLY_TERM NAT ON_REASON_TRUNCATED);
    }
  }

  pr vate EarlyTerm nat onTr mm ngStats getEarlyTerm nat onTr mm ngStats() {
     f (accumulatedResponses. s rg ngPart  onsW h nAT er()) {
      return getEarlyTerm nat onTr mm ngStatsForPart  ons();
    } else {
      return getEarlyTerm nat onTr mm ngStatsForT ers();
    }
  }

  protected EarlyTerm nat onTr mm ngStats getEarlyTerm nat onTr mm ngStatsForPart  ons() {
    return PART T ON_MERG NG_EARLY_TERM NAT ON_TR MM NG_STATS;
  }

  protected EarlyTerm nat onTr mm ngStats getEarlyTerm nat onTr mm ngStatsForT ers() {
    return T ER_MERG NG_EARLY_TERM NAT ON_TR MM NG_STATS;
  }

  /**
   *  f   get enough results, no need to go on.
   *  f one of t  part  ons early term nated,   can't go on or else t re could be a gap.
   */
  @Overr de
  publ c boolean shouldEarlyTerm nateT er rge( nt totalResultsFromSuccessfulShards,
                                                  boolean foundEarlyTerm nat on) {


     nt resultsRequested = computeNumResultsToKeep();

    boolean shouldEarlyTerm nate = foundEarlyTerm nat on
        || totalResultsFromSuccessfulShards >= resultsRequested;

     f (shouldEarlyTerm nate && totalResultsFromSuccessfulShards < resultsRequested) {
      RECENCY_T ER_MERGE_EARLY_TERM NATED_W TH_NOT_ENOUGH_RESULTS. ncre nt();
    }

    return shouldEarlyTerm nate;
  }

  /**
   * F nd t  m n status  d that has been _completely_ searc d across all part  ons. T 
   * largest m n status  d across all part  ons.
   *
   * @return t  m n searc d status  d found
   */
  protected long f ndM nFullySearc dStatus D() {
    L st<Long> m n ds = accumulatedResponses.getM n ds();
     f (m n ds. sEmpty()) {
      return Long.M N_VALUE;
    }

     f (accumulatedResponses. s rg ngPart  onsW h nAT er()) {
      // W n  rg ng part  ons, t  m n  D should be t  largest among t  m n  Ds.
      return Collect ons.max(accumulatedResponses.getM n ds());
    } else {
      // W n  rg ng t ers, t  m n  D should be t  smallest among t  m n  Ds.
      return Collect ons.m n(accumulatedResponses.getM n ds());
    }
  }

  /**
   * F nd t  max status  d that has been _completely_ searc d across all part  ons. T 
   * smallest max status  d across all part  ons.
   *
   * T   s w re   reconc le repl cat on lag by select ng t  oldest max d from t 
   * part  ons searc d.
   *
   * @return t  max searc d status  d found
   */
   protected long f ndMaxFullySearc dStatus D() {
    L st<Long> max Ds = accumulatedResponses.getMax ds();
     f (max Ds. sEmpty()) {
      return Long.MAX_VALUE;
    }
    Collect ons.sort(max Ds);

    f nal long ne st = max Ds.get(max Ds.s ze() - 1);
    f nal long ne stT  stamp = Snowflake dParser.getT  stampFromT et d(ne st);

    for ( nt   = 0;   < max Ds.s ze();  ++) {
      long oldest = max Ds.get( );
      long oldestT  stamp = Snowflake dParser.getT  stampFromT et d(oldest);
      long deltaMs = ne stT  stamp - oldestT  stamp;

       f (  == 0) {
        LOG.debug("Max delta  s {}", deltaMs);
      }

       f (deltaMs < ALLOWED_REPL CAT ON_LAG_MS) {
         f (  != 0) {
          LOG.debug("{} part  on repl cas lagg ng more than {} ms",  , ALLOWED_REPL CAT ON_LAG_MS);
        }
        return oldest;
      }
    }

    // Can't get  re - by t  po nt oldest == ne st, and delta  s 0.
    return ne st;
  }

  /**
   * Tr m t  Thr ftSearchResults  f   have enough results, to return t  f rst
   * 'computeNumResultsToKeep()' number of results.
   *
   *  f   don't have enough results after tr mm ng, t  funct on w ll f rst try to back f ll
   * older results, t n ne r results
   *
   * @param searchResults Thr ftSearchResults that hold t  to be tr m d L st<Thr ftSearchResult>
   * @return Tr mStats conta n ng stat st cs about how many results be ng removed
   */
  protected Tr mStats tr mResults(
      Thr ftSearchResults searchResults,
      long  rgedM n,
      long  rgedMax) {
     f (!searchResults. sSetResults() || searchResults.getResultsS ze() == 0) {
      // no results, no tr mm ng needed
      return Tr mStats.EMPTY_STATS;
    }

     f (requestContext.getRequest().getSearchQuery(). sSetSearchStatus ds()) {
      // Not a normal search, no tr mm ng needed
      return Tr mStats.EMPTY_STATS;
    }

    Tr mStats tr mStats = new Tr mStats();
    tr mExactDups(searchResults, tr mStats);

     nt numResultsRequested = computeNumResultsToKeep();
     f (shouldSk pTr mm ngW nNotEnoughResults(searchResults, numResultsRequested)) {
      //////////////////////////////////////////////////////////
      //   don't have enough results, let's not do tr mm ng
      //////////////////////////////////////////////////////////
      return tr mStats;
    }

     f (accumulatedResponses. s rg ngPart  onsW h nAT er()) {
      tr mResultsBasedSearc dRange(
          searchResults, tr mStats, numResultsRequested,  rgedM n,  rgedMax);
    }

    // Respect "computeNumResultsToKeep()"  re, only keep "computeNumResultsToKeep()" results.
    truncateResults(searchResults, tr mStats);

    return tr mStats;
  }

  /**
   * W n t re's not enough results,   don't remove results based on t  searc d range.
   * T  has a tradeoff:  w h t ,   don't reduce   recall w n   already don't have enough
   * results. Ho ver, w h t ,   can lose results wh le pag nat ng because   return results
   * outs de of t  val d searc d range.
   */
  protected boolean shouldSk pTr mm ngW nNotEnoughResults(
      Thr ftSearchResults searchResults,  nt numResultsRequested) {
    return searchResults.getResultsS ze() <= numResultsRequested;
  }


  /**
   * Tr m results based on search range. T  search range [x, y]  s determ ned by:
   *   x  s t  max mun of t  m n mun search  Ds;
   *   y  s t  m n mun of t  max mum search  Ds.
   *
   *  ds out s de of t  range are removed.
   *  f   do not get enough results after t  removal,   add  Ds back unt l   get enough results.
   *   f rst add  Ds back from t  older s de back.  f t re's st ll not enough results,
   *   start add ng  Ds from t  ne r s de back.
   */
  pr vate vo d tr mResultsBasedSearc dRange(Thr ftSearchResults searchResults,
                                             Tr mStats tr mStats,
                                              nt numResultsRequested,
                                             long  rgedM n,
                                             long  rgedMax) {
    ///////////////////////////////////////////////////////////////////
    //   have more results than requested, let's do so  tr mm ng
    ///////////////////////////////////////////////////////////////////

    // Save t  or g nal results before tr mm ng
    L st<Thr ftSearchResult> or g nalResults = searchResults.getResults();

    f lterResultsBy rgedM nMax ds(searchResults,  rgedMax,  rgedM n, tr mStats);

    // T  does happen.    s hard to say what   should do  re so   just return t  or g nal
    // result  re.
     f (searchResults.getResultsS ze() == 0) {
      RECENCY_ZERO_RESULT_COUNT_AFTER_F LTER NG_MAX_M N_ DS. ncre nt();
      searchResults.setResults(or g nalResults);

      // Clean up m n/m x f ltered count, s nce  're br ng ng back whatever   just f ltered.
      tr mStats.clearMax dF lterCount();
      tr mStats.clearM n dF lterCount();

       f (LOG. sDebugEnabled() || response ssageBu lder. sDebugMode()) {
        Str ng errMsg = "No tr mm ng  s done as f ltered results  s empty. "
            + "max d=" +  rgedMax + ",m n d=" +  rgedM n;
        LOG.debug(errMsg);
        response ssageBu lder.append(errMsg + "\n");
      }
    } else {
      // oops!  're tr mm ng too many results. Let's put so  back
       f (searchResults.getResultsS ze() < numResultsRequested) {
        RECENCY_TR MMED_TOO_MANY_RESULTS_COUNT. ncre nt();

        L st<Thr ftSearchResult> tr m dResults = searchResults.getResults();
        long f rstTr m dResult d = tr m dResults.get(0).get d();
        long lastTr m dResult d = tr m dResults.get(tr m dResults.s ze() - 1).get d();

        // F rst, try to back f ll w h older results
         nt   = 0;
        for (;   < or g nalResults.s ze(); ++ ) {
          Thr ftSearchResult result = or g nalResults.get( );
           f (result.get d() < lastTr m dResult d) {
            tr m dResults.add(result);
            tr mStats.decreaseM n dF lterCount();
             f (tr m dResults.s ze() >= numResultsRequested) {
              break;
            }
          }
        }

        // st ll not enough results? back f ll w h ne r results
        // f nd t  oldest of t  ne r results
         f (tr m dResults.s ze() < numResultsRequested) {
          // st ll not enough results? back f ll w h ne r results
          // f nd t  oldest of t  ne r results
          for (  = or g nalResults.s ze() - 1;   >= 0; -- ) {
            Thr ftSearchResult result = or g nalResults.get( );
             f (result.get d() > f rstTr m dResult d) {
              tr m dResults.add(result);
              tr mStats.decreaseMax dF lterCount();
               f (tr m dResults.s ze() >= numResultsRequested) {
                break;
              }
            }
          }

          // ne r results  re added to t  back of t  l st, re-sort
          Collect ons.sort(tr m dResults, ResultComparators. D_COMPARATOR);
        }
      }
    }
  }

  protected vo d set rgedM nSearc dStatus d(
      Thr ftSearchResults searchResults,
      long current rgedM n,
      boolean results reTr m d) {
     f (accumulatedResponses.getM n ds(). sEmpty()) {
      return;
    }

    long  rged;
     f (searchResults == null
        || !searchResults. sSetResults()
        || searchResults.getResultsS ze() == 0) {
       rged = current rgedM n;
    } else {
      L st<Thr ftSearchResult> results = searchResults.getResults();
      long f rstResult d = results.get(0).get d();
      long lastResult d = results.get(results.s ze() - 1).get d();
       rged = Math.m n(f rstResult d, lastResult d);
       f (!results reTr m d) {
        //  f t  results  re tr m d,   want to set m nSearc dStatus D to t  smallest
        // t et  D  n t  response. Ot rw se,   want to take t  m n bet en that, and
        // t  current m nSearc dStatus D.
         rged = Math.m n( rged, current rgedM n);
      }
    }

    searchResults.setM nSearc dStatus D( rged);
  }

  pr vate vo d set rgedMaxSearc dStatus d(
      Thr ftSearchResults searchResults,
      long current rgedMax) {
     f (accumulatedResponses.getMax ds(). sEmpty()) {
      return;
    }

    long  rged;
     f (searchResults == null
        || !searchResults. sSetResults()
        || searchResults.getResultsS ze() == 0) {
       rged = current rgedMax;
    } else {
      L st<Thr ftSearchResult> results = searchResults.getResults();
      long f rstResult d = results.get(0).get d();
      long lastResult d = results.get(results.s ze() - 1).get d();
      long maxResult d = Math.max(f rstResult d, lastResult d);
       rged = Math.max(maxResult d, current rgedMax);
    }

    searchResults.setMaxSearc dStatus D( rged);
  }

  protected stat c vo d f lterResultsBy rgedM nMax ds(
      Thr ftSearchResults results, long maxStatus d, long m nStatus d, Tr mStats tr mStats) {
    L st<Thr ftSearchResult> tr  dResults =
        L sts.newArrayL stW hCapac y(results.getResultsS ze());

    for (Thr ftSearchResult result : results.getResults()) {
      long status d = result.get d();

       f (status d > maxStatus d) {
        tr mStats. ncreaseMax dF lterCount();
      } else  f (status d < m nStatus d) {
        tr mStats. ncreaseM n dF lterCount();
      } else {
        tr  dResults.add(result);
      }
    }

    results.setResults(tr  dResults);
  }
}
