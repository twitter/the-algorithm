package com.tw ter.search.earlyb rd.search;

 mport java.ut l.L st;
 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene.search.Query;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.query.H Attr bute lper;
 mport com.tw ter.search.common.search.Term nat onTracker;
 mport com.tw ter.search.earlyb rd.Qual yFactor;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.queryparser.ut l. dT  Ranges;

publ c class SearchRequest nfo {
  pr vate f nal Thr ftSearchQuery searchQuery;
  pr vate f nal Query luceneQuery;
  pr vate f nal boolean collectConversat on d;
  pr vate f nal boolean collectResultLocat on;
  pr vate f nal boolean get nReplyToStatus d;
  pr vate f nal boolean getReferenceAuthor d;
  pr vate f nal boolean getFromUser d;
  pr vate f nal boolean collectExclus veConversat onAuthor d;

  pr vate f nal  nt numResultsRequested;
  pr vate f nal  nt maxH sToProcess;
  pr vate f nal L st<Str ng> facetF eldNa s;
  pr vate long t  stamp;

  pr vate f nal Term nat onTracker term nat onTracker;

  protected f nal Qual yFactor qual yFactor;

  // Set  f   want to collect per-f eld h  attr butes for t  request.
  @Nullable
  pr vate H Attr bute lper h Attr bute lper;

  pr vate  dT  Ranges  dT  Ranges;

  pr vate stat c f nal  nt DEFAULT_MAX_H TS = 1000;

  pr vate stat c f nal SearchCounter RESET_MAX_H TS_TO_PROCESS_COUNTER =
      SearchCounter.export("search_request_ nfo_reset_max_h s_to_process");

  publ c SearchRequest nfo(
      Thr ftSearchQuery searchQuery,
      Query luceneQuery,
      Term nat onTracker term nat onTracker) {
    t (searchQuery, luceneQuery, term nat onTracker, null);
  }

  publ c SearchRequest nfo(
      Thr ftSearchQuery searchQuery,
      Query luceneQuery,
      Term nat onTracker term nat onTracker,
      Qual yFactor qual yFactor) {
    Precond  ons.c ckNotNull(searchQuery.getCollectorParams());
    Precond  ons.c ckNotNull(term nat onTracker);

    t .searchQuery = searchQuery;
    t .luceneQuery = luceneQuery;
    t .collectConversat on d = searchQuery. sCollectConversat on d();
     f (searchQuery. sSetResult tadataOpt ons()) {
      t .collectResultLocat on = searchQuery.getResult tadataOpt ons(). sGetResultLocat on();
      t .get nReplyToStatus d = searchQuery.getResult tadataOpt ons(). sGet nReplyToStatus d();
      t .getReferenceAuthor d =
          searchQuery.getResult tadataOpt ons(). sGetReferencedT etAuthor d();
      t .getFromUser d = searchQuery.getResult tadataOpt ons(). sGetFromUser d();
      t .collectExclus veConversat onAuthor d =
          searchQuery.getResult tadataOpt ons(). sGetExclus veConversat onAuthor d();
    } else {
      t .collectResultLocat on = false;
      t .get nReplyToStatus d = false;
      t .getReferenceAuthor d = false;
      t .getFromUser d = false;
      t .collectExclus veConversat onAuthor d = false;
    }

    t .qual yFactor = qual yFactor;

    t .numResultsRequested = searchQuery.getCollectorParams().getNumResultsToReturn();
    t .maxH sToProcess = calculateMaxH sToProcess(searchQuery);
    t .term nat onTracker = term nat onTracker;
    t .facetF eldNa s = searchQuery.getFacetF eldNa s();
  }

  /**
   * Gets t  value to be used as max h s to process for t  query. T  base class gets   from
   * t  searchQuery d rectly, and uses a default  f that's not set.
   *
   * Subclasses can overr de t  to compute a d fferent value for max h s to process.
   */
  protected  nt calculateMaxH sToProcess(Thr ftSearchQuery thr ftSearchQuery) {
     nt maxH s = thr ftSearchQuery.getCollectorParams(). sSetTerm nat onParams()
        ? thr ftSearchQuery.getCollectorParams().getTerm nat onParams().getMaxH sToProcess() : 0;

     f (maxH s <= 0) {
      maxH s = DEFAULT_MAX_H TS;
      RESET_MAX_H TS_TO_PROCESS_COUNTER. ncre nt();
    }
    return maxH s;
  }

  publ c f nal Thr ftSearchQuery getSearchQuery() {
    return t .searchQuery;
  }

  publ c Query getLuceneQuery() {
    return luceneQuery;
  }

  publ c f nal  nt getNumResultsRequested() {
    return numResultsRequested;
  }

  publ c f nal  nt getMaxH sToProcess() {
    return maxH sToProcess;
  }

  publ c boolean  sCollectConversat on d() {
    return collectConversat on d;
  }

  publ c boolean  sCollectResultLocat on() {
    return collectResultLocat on;
  }

  publ c boolean  sGet nReplyToStatus d() {
    return get nReplyToStatus d;
  }

  publ c boolean  sGetReferenceAuthor d() {
    return getReferenceAuthor d;
  }

  publ c boolean  sCollectExclus veConversat onAuthor d() {
    return collectExclus veConversat onAuthor d;
  }

  publ c f nal  dT  Ranges get dT  Ranges() {
    return  dT  Ranges;
  }

  publ c SearchRequest nfo set dT  Ranges( dT  Ranges new dT  Ranges) {
    t . dT  Ranges = new dT  Ranges;
    return t ;
  }

  publ c SearchRequest nfo setT  stamp(long newT  stamp) {
    t .t  stamp = newT  stamp;
    return t ;
  }

  publ c long getT  stamp() {
    return t  stamp;
  }

  publ c Term nat onTracker getTerm nat onTracker() {
    return t .term nat onTracker;
  }

  @Nullable
  publ c H Attr bute lper getH Attr bute lper() {
    return h Attr bute lper;
  }

  publ c vo d setH Attr bute lper(@Nullable H Attr bute lper h Attr bute lper) {
    t .h Attr bute lper = h Attr bute lper;
  }

  publ c L st<Str ng> getFacetF eldNa s() {
    return facetF eldNa s;
  }

  publ c boolean  sGetFromUser d() {
    return getFromUser d;
  }
}
