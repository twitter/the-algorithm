package com.tw ter.search.earlyb rd_root. rgers;

 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.TreeMap;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.stream.Collectors;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Funct on;
 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponseUt l;
 mport com.tw ter.search.common.ut l.earlyb rd.ResultsUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRank ngMode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.collectors.Relevance rgeCollector;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;

/**
 *  rger class to  rge relevance search Earlyb rdResponse objects
 */
publ c class RelevanceResponse rger extends Earlyb rdResponse rger {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(RelevanceResponse rger.class);

  pr vate stat c f nal SearchT  rStats T MER =
      SearchT  rStats.export(" rge_relevance", T  Un .NANOSECONDS, false, true);

  pr vate stat c f nal SearchCounter RELVEANCE_T ER_MERGE_EARLY_TERM NATED_W TH_NOT_ENOUGH_RESULTS =
      SearchCounter.export(" rger_relevance_t er_ rge_early_term nated_w h_not_enough_results");

  pr vate stat c f nal Str ng PART T ON_NUM_RESULTS_COUNTER_SK P_STATS =
      " rger_relevance_post_tr m d_results_sk p_stat_t er_%s_part  on_%d";

  @V s bleForTest ng
  publ c stat c f nal Str ng PART T ON_NUM_RESULTS_COUNTER_NAME_FORMAT =
      " rger_relevance_post_tr m d_results_from_t er_%s_part  on_%d";

  protected stat c f nal Funct on<Earlyb rdResponse, Map<Thr ftLanguage,  nteger>> LANG_MAP_GETTER =
      response -> response.getSearchResults() == null
          ? null
          : response.getSearchResults().getLanguage togram();

  pr vate stat c f nal double SUCCESSFUL_RESPONSE_THRESHOLD = 0.8;

  pr vate f nal Earlyb rdFeatureSc ma rger featureSc ma rger;

  // T  number of part  ons are not  an ngful w n    s  nvoked through mult -t er  rg ng.
  pr vate f nal  nt numPart  ons;

  publ c RelevanceResponse rger(Earlyb rdRequestContext requestContext,
                                 L st<Future<Earlyb rdResponse>> responses,
                                 ResponseAccumulator mode,
                                 Earlyb rdFeatureSc ma rger featureSc ma rger,
                                  nt numPart  ons) {
    super(requestContext, responses, mode);
    t .featureSc ma rger = Precond  ons.c ckNotNull(featureSc ma rger);
    t .numPart  ons = numPart  ons;
  }

  @Overr de
  protected double getDefaultSuccessResponseThreshold() {
    return SUCCESSFUL_RESPONSE_THRESHOLD;
  }

  @Overr de
  protected SearchT  rStats get rgedResponseT  r() {
    return T MER;
  }

  @Overr de
  protected Earlyb rdResponse  nternal rge(Earlyb rdResponse  rgedResponse) {
    f nal Thr ftSearchQuery searchQuery = requestContext.getRequest().getSearchQuery();
    long max d = f ndMaxFullySearc dStatus D();
    long m n d = f ndM nFullySearc dStatus D();

    Precond  ons.c ckNotNull(searchQuery);
    Precond  ons.c ckState(searchQuery. sSetRank ngMode());
    Precond  ons.c ckState(searchQuery.getRank ngMode() == Thr ftSearchRank ngMode.RELEVANCE);

    // F rst get t  results  n score order (t  default comparator for t   rge collector).
    Relevance rgeCollector collector = new Relevance rgeCollector(responses.s ze());
     nt totalResultS ze = addResponsesToCollector(collector);
    Thr ftSearchResults searchResults = collector.getAllSearchResults();

    Tr mStats tr mStats = tr mResults(searchResults);
    featureSc ma rger.collectAndSetFeatureSc ma nResponse(
        searchResults,
        requestContext,
        " rger_relevance_t er",
        accumulatedResponses.getSuccessResponses());

     rgedResponse.setSearchResults(searchResults);

    searchResults =  rgedResponse.getSearchResults();
    searchResults
        .setH Counts(aggregateH CountMap())
        .setLanguage togram(aggregateLanguage tograms());

     f (!accumulatedResponses.getMax ds(). sEmpty()) {
      searchResults.setMaxSearc dStatus D(max d);
    }

     f (!accumulatedResponses.getM n ds(). sEmpty()) {
      searchResults.setM nSearc dStatus D(m n d);
    }

    LOG.debug("H s: {} Removed dupl cates: {}", totalResultS ze, tr mStats.getRemovedDupsCount());
    LOG.debug("Hash Part  on'ed Earlyb rd call completed successfully: {}",  rgedResponse);

    publ shNumResultsFromPart  onStat st cs( rgedResponse);

    return  rgedResponse;
  }

  /**
   *  f any of t  part  ons has an early term nat on, t  t er  rge must also early term nate.
   *
   *  f a part  on early term nated (  haven't fully searc d that part  on), and    nstead
   * moved onto t  next t er, t re w ll be a gap of unsearc d results.
   *
   *  f   early term nat on cond  on was only  f   had enough results,   could get bad qual y
   * results by only look ng at 20 h s w n ask ng for 20 results.
   */
  @Overr de
  publ c boolean shouldEarlyTerm nateT er rge( nt totalResultsFromSuccessfulShards,
                                               boolean foundEarlyTerm nat on) {

    // Don't use computeNumResultsToKeep because  f returnAllResults  s true,   w ll be
    //  nteger.MAX_VALUE and   w ll always log a stat that   d dn't get enough results
     nt resultsRequested;
    Earlyb rdRequest request = requestContext.getRequest();
     f (request. sSetNumResultsToReturnAtRoot()) {
      resultsRequested = request.getNumResultsToReturnAtRoot();
    } else {
      resultsRequested = request.getSearchQuery().getCollectorParams().getNumResultsToReturn();
    }
     f (foundEarlyTerm nat on && totalResultsFromSuccessfulShards < resultsRequested) {
      RELVEANCE_T ER_MERGE_EARLY_TERM NATED_W TH_NOT_ENOUGH_RESULTS. ncre nt();
    }

    return foundEarlyTerm nat on;
  }

  /**
   *  rge language  tograms from all quer es.
   *
   * @return  rge per-language count map.
   */
  pr vate Map<Thr ftLanguage,  nteger> aggregateLanguage tograms() {
    Map<Thr ftLanguage,  nteger> totalLangCounts = new TreeMap<>(
        ResultsUt l.aggregateCountMap(
            accumulatedResponses.getSuccessResponses(), LANG_MAP_GETTER));
     f (totalLangCounts.s ze() > 0) {
       f (response ssageBu lder. sDebugMode()) {
        response ssageBu lder.append("Language D strbut on:\n");
         nt count = 0;
        for (Map.Entry<Thr ftLanguage,  nteger> entry : totalLangCounts.entrySet()) {
          response ssageBu lder.append(
              Str ng.format(" %10s:%6d", entry.getKey(), entry.getValue()));
           f (++count % 5 == 0) {
            response ssageBu lder.append("\n");
          }
        }
        response ssageBu lder.append("\n");
      }
    }
    return totalLangCounts;
  }

  /**
   * F nd t  m n status  d that has been searc d. S nce no results are tr m d for Relevance mode,
   *   should be t  smallest among t  m n  Ds.
   */
  pr vate long f ndM nFullySearc dStatus D() {
    // T  m n  D should be t  smallest among t  m n  Ds
    return accumulatedResponses.getM n ds(). sEmpty() ? 0
        : Collect ons.m n(accumulatedResponses.getM n ds());
  }

  /**
   * F nd t  max status  d that has been searc d. S nce no results are tr m d for Relevance mode,
   *   should be t  largest among t  max  Ds.
   */
  pr vate long f ndMaxFullySearc dStatus D() {
    // T  max  D should be t  largest among t  max  Ds
    return accumulatedResponses.getMax ds(). sEmpty() ? 0
        : Collect ons.max(accumulatedResponses.getMax ds());
  }

  /**
   * Return all t  searchResults except dupl cates.
   *
   * @param searchResults Thr ftSearchResults that hold t  to be tr m d L st<Thr ftSearchResult>
   * @return Tr mStats conta n ng stat st cs about how many results be ng removed
   */
  pr vate Tr mStats tr mResults(Thr ftSearchResults searchResults) {
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

    truncateResults(searchResults, tr mStats);

    return tr mStats;
  }

  pr vate vo d publ shNumResultsFromPart  onStat st cs(Earlyb rdResponse  rgedResponse) {

    // Keep track of all of t  results that  re kept after  rg ng
    Set<Long>  rgedResults =
        Earlyb rdResponseUt l.getResults( rgedResponse).getResults()
            .stream()
            .map(result -> result.get d())
            .collect(Collectors.toSet());

    // For each successful response (pre  rge), count how many of  s results  re kept post  rge.
    //  ncre nt t  appropr ate stat.
    for (Earlyb rdResponse response : accumulatedResponses.getSuccessResponses()) {
       f (!response. sSetEarlyb rdServerStats()) {
        cont nue;
      }
       nt numResultsKept = 0;
      for (Thr ftSearchResult result
          : Earlyb rdResponseUt l.getResults(response).getResults()) {
         f ( rgedResults.conta ns(result.get d())) {
          ++numResultsKept;
        }
      }

      //   only update part  on stats w n t  part  on  D looks sane.
      Str ng t erNa  = response.getEarlyb rdServerStats().getT erNa ();
       nt part  on = response.getEarlyb rdServerStats().getPart  on();
       f (part  on >= 0 && part  on < numPart  ons) {
        SearchCounter.export(Str ng.format(PART T ON_NUM_RESULTS_COUNTER_NAME_FORMAT,
            t erNa ,
            part  on))
            .add(numResultsKept);
      } else {
        SearchCounter.export(Str ng.format(PART T ON_NUM_RESULTS_COUNTER_SK P_STATS,
            t erNa ,
            part  on)). ncre nt();
      }
    }
  }
}
