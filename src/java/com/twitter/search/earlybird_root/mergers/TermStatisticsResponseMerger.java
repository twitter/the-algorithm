package com.tw ter.search.earlyb rd_root. rgers;

 mport java.ut l.Collect on;
 mport java.ut l.L st;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.collect.Collect ons2;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.ut l.earlyb rd.FacetsResultsUt ls;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermStat st csRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermStat st csResults;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;

/**
 *  rger class to  rge termstats Earlyb rdResponse objects
 */
publ c class TermStat st csResponse rger extends Earlyb rdResponse rger {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(TermStat st csResponse rger.class);

  pr vate stat c f nal SearchT  rStats T MER =
      SearchT  rStats.export(" rge_term_stats", T  Un .NANOSECONDS, false, true);

  pr vate stat c f nal double SUCCESSFUL_RESPONSE_THRESHOLD = 0.9;

  publ c TermStat st csResponse rger(Earlyb rdRequestContext requestContext,
                                      L st<Future<Earlyb rdResponse>> responses,
                                      ResponseAccumulator mode) {
    super(requestContext, responses, mode);
  }

  @Overr de
  protected SearchT  rStats get rgedResponseT  r() {
    return T MER;
  }

  @Overr de
  protected double getDefaultSuccessResponseThreshold() {
    return SUCCESSFUL_RESPONSE_THRESHOLD;
  }

  @Overr de
  protected Earlyb rdResponse  nternal rge(Earlyb rdResponse termStatsResponse) {
    Thr ftTermStat st csRequest termStat st csRequest =
        requestContext.getRequest().getTermStat st csRequest();

    Collect on<Earlyb rdResponse> termStatsResults =
        Collect ons2.f lter(accumulatedResponses.getSuccessResponses(),
            earlyb rdResponse -> earlyb rdResponse. sSetTermStat st csResults());

    Thr ftTermStat st csResults results =
        new Thr ftTermResults rger(
            termStatsResults,
            termStat st csRequest.get togramSett ngs())
        . rge();

     f (results.getTermResults(). sEmpty()) {
      f nal Str ng l ne = "No results returned from any backend for term stat st cs request: {}";

      //  f t  termstats request was not empty and   got empty results. log   as a warn ng
      // ot rw se log  s as a debug.
       f (termStat st csRequest.getTermRequestsS ze() > 0) {
        LOG.warn(l ne, termStat st csRequest);
      } else {
        LOG.debug(l ne, termStat st csRequest);
      }
    }

    termStatsResponse.setTermStat st csResults(results);
    termStatsResponse.setSearchResults(Thr ftTermResults rger. rgeSearchStats(termStatsResults));

    FacetsResultsUt ls.f xNat vePhotoUrl(results.getTermResults().values());

    LOG.debug("TermStats call completed successfully: {}", termStatsResponse);

    return termStatsResponse;
  }

  @Overr de
  publ c boolean shouldEarlyTerm nateT er rge( nt totalResultsFromSuccessfulShards,
                                                  boolean foundEarlyTerm nat on) {
    // To get accurate term stats, must never early term nate
    return false;
  }
}
