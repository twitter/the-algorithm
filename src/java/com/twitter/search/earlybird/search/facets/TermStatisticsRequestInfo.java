package com.tw ter.search.earlyb rd.search.facets;

 mport java.ut l.L nkedL st;
 mport java.ut l.L st;
 mport java.ut l.Set;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableSet;

 mport org.apac .lucene.search.Query;

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.search.Term nat onTracker;
 mport com.tw ter.search.common.ut l.text.Normal zer lper;
 mport com.tw ter.search.common.ut l.url.URLUt ls;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.search.SearchRequest nfo;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ft togramSett ngs;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermStat st csRequest;

publ c class TermStat st csRequest nfo extends SearchRequest nfo {
  pr vate stat c f nal Set<Str ng> FACET_URL_F ELDS_TO_NORMAL ZE = new  mmutableSet.Bu lder()
      .add(Earlyb rdF eldConstant. MAGES_FACET)
      .add(Earlyb rdF eldConstant.V DEOS_FACET)
      .add(Earlyb rdF eldConstant.NEWS_FACET)
      .bu ld();

  protected f nal L st<Thr ftTermRequest> termRequests;
  protected f nal Thr ft togramSett ngs  togramSett ngs;

  /**
   * Creates a new TermStat st csRequest nfo  nstance us ng t  prov ded query.
   */
  publ c TermStat st csRequest nfo(Thr ftSearchQuery searchQuery,
                                   Query luceneQuery,
                                   Thr ftTermStat st csRequest termStatsRequest,
                                   Term nat onTracker term nat onTracker) {
    super(searchQuery, luceneQuery, term nat onTracker);
    t .termRequests = termStatsRequest. sSetTermRequests()
                        ? termStatsRequest.getTermRequests() : new L nkedL st<>();
    t . togramSett ngs = termStatsRequest.get togramSett ngs();
     f (termStatsRequest. s ncludeGlobalCounts()) {
      // Add an empty request to  nd cate   need a global count across all f elds.
      termRequests.add(new Thr ftTermRequest().setF eldNa ("").setTerm(""));
    }

    //   only normal ze TEXT terms and urls. All ot r terms, e.g. top cs (na d ent  es) are
    // not normal zed.  re t  assumpt on  s that t  caller passes t  exact terms back that
    // t  facet AP  returned
    for (Thr ftTermRequest termReq : termRequests) {
       f (termReq.getTerm(). sEmpty()) {
        cont nue;  // t  spec al catch-all term.
      }

       f (!termReq. sSetF eldNa ()
          || termReq.getF eldNa ().equals(Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa ())) {
        // normal ze t  TEXT term as  's normal zed dur ng  ngest on
        termReq.setTerm(Normal zer lper.normal zeW hUnknownLocale(
                            termReq.getTerm(), Earlyb rdConf g.getPengu nVers on()));
      } else  f (FACET_URL_F ELDS_TO_NORMAL ZE.conta ns(termReq.getF eldNa ())) {
        // remove t  tra l ng slash from t  URL path. T  operat on  s  dempotent,
        // so e  r a sp derduck URL or a facet URL can be used  re. T  latter would just
        // be normal zed tw ce, wh ch  s f ne.
        termReq.setTerm(URLUt ls.normal zePath(termReq.getTerm()));
      }
    }
  }

  @Overr de
  protected  nt calculateMaxH sToProcess(Thr ftSearchQuery searchQuery) {
    Precond  ons.c ckNotNull(searchQuery.getCollectorParams());
     f (!searchQuery.getCollectorParams(). sSetTerm nat onParams()
        || !searchQuery.getCollectorParams().getTerm nat onParams(). sSetMaxH sToProcess()) {
      // Overr de t  default value to all h s.
      return  nteger.MAX_VALUE;
    } else {
      return super.calculateMaxH sToProcess(searchQuery);
    }
  }

  publ c f nal L st<Thr ftTermRequest> getTermRequests() {
    return t .termRequests;
  }

  publ c f nal Thr ft togramSett ngs get togramSett ngs() {
    return t . togramSett ngs;
  }

  publ c f nal boolean  sReturn togram() {
    return t . togramSett ngs != null;
  }
}
