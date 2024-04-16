package com.tw ter.search.earlyb rd.search.relevance;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene.search.Query;

 mport com.tw ter.search.common.search.Term nat onTracker;
 mport com.tw ter.search.earlyb rd.Qual yFactor;
 mport com.tw ter.search.earlyb rd.search.SearchRequest nfo;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRelevanceOpt ons;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadataOpt ons;

publ c class RelevanceSearchRequest nfo extends SearchRequest nfo {
  pr vate f nal Thr ftSearchRelevanceOpt ons relevanceOpt ons;

  publ c RelevanceSearchRequest nfo(
      Thr ftSearchQuery searchQuery, Query query,
      Term nat onTracker term nat onTracker, Qual yFactor qual yFactor) {
    super(addResult tadataOpt ons fUnset(searchQuery), query, term nat onTracker, qual yFactor);
    t .relevanceOpt ons = searchQuery.getRelevanceOpt ons();
  }

  pr vate stat c Thr ftSearchQuery addResult tadataOpt ons fUnset(Thr ftSearchQuery searchQuery) {
     f (!searchQuery. sSetResult tadataOpt ons()) {
      searchQuery.setResult tadataOpt ons(new Thr ftSearchResult tadataOpt ons());
    }
    return searchQuery;
  }

  @Overr de
  protected  nt calculateMaxH sToProcess(Thr ftSearchQuery thr ftSearchQuery) {
    Thr ftSearchRelevanceOpt ons searchRelevanceOpt ons = thr ftSearchQuery.getRelevanceOpt ons();

    // Don't use t  value from t  Thr ftSearchQuery object  f one  s prov ded  n t 
    // relevance opt ons
     nt requestedMaxH sToProcess = searchRelevanceOpt ons. sSetMaxH sToProcess()
        ? searchRelevanceOpt ons.getMaxH sToProcess()
        : super.calculateMaxH sToProcess(thr ftSearchQuery);

    return qual yFactorMaxH sToProcess(getNumResultsRequested(), requestedMaxH sToProcess);
  }

  publ c Thr ftSearchRelevanceOpt ons getRelevanceOpt ons() {
    return t .relevanceOpt ons;
  }

  /**
   * Reduces maxH sToProcess based on qual y factor. Never reduces   beyond
   * numResults.
   * @param numResults
   * @param maxH sToProcess
   * @return Reduced maxH sToProcess.
   */
  publ c  nt qual yFactorMaxH sToProcess( nt numResults,  nt maxH sToProcess) {
    Precond  ons.c ckNotNull(qual yFactor);

    // Do not qual y factor  f t re  s no lo r bound on maxH sToProcess.
     f (numResults > maxH sToProcess) {
      return maxH sToProcess;
    }

    double currentQual yFactor = qual yFactor.get();
    return Math.max(numResults, ( nt) (currentQual yFactor * maxH sToProcess));
  }
}
