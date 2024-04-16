package com.tw ter.search.earlyb rd.search.relevance.collectors;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport com.google.common.collect.L sts;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.relevance.features.T et ntegerSh ngleS gnature;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceH ;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceSearchRequest nfo;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceSearchResults;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.Scor ngFunct on;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;

/**
 * RelevanceAllCollector  s a results collector that collects all results sorted by score,
 *  nclud ng s gnature-dupl cates and results sk pped by t  scor ng funct on.
 */
publ c class RelevanceAllCollector extends AbstractRelevanceCollector {
  // All results.
  protected f nal L st<RelevanceH > results;

  publ c RelevanceAllCollector(
       mmutableSc ma nterface sc ma,
      RelevanceSearchRequest nfo searchRequest nfo,
      Scor ngFunct on scor ngFunct on,
      Earlyb rdSearc rStats searc rStats,
      Earlyb rdCluster cluster,
      UserTable userTable,
      Clock clock,
       nt requestDebugMode) {
    super(sc ma, searchRequest nfo, scor ngFunct on, searc rStats, cluster, userTable, clock,
        requestDebugMode);
    t .results = L sts.newArrayL st();
  }

  @Overr de
  protected vo d doCollectW hScore(long t et D, float score) throws  OExcept on {
    Thr ftSearchResult tadata  tadata = collect tadata();
    scor ngFunct on.populateResult tadataBasedOnScor ngData(
        searchRequest nfo.getSearchQuery().getResult tadataOpt ons(),
         tadata,
        scor ngFunct on.getScor ngDataForCurrentDocu nt());
    results.add(new RelevanceH (
        currT  Sl ce D,
        t et D,
        T et ntegerSh ngleS gnature.deser al ze( tadata.getS gnature()),
         tadata));
  }

  @Overr de
  protected RelevanceSearchResults doGetRelevanceResults() {
    f nal  nt numResults = results.s ze();
    RelevanceSearchResults searchResults = new RelevanceSearchResults(numResults);

    //  nsert h s  n decreas ng order by score.
    results.sort(RelevanceH .COMPARATOR_BY_SCORE);
    for ( nt   = 0;   < numResults;  ++) {
      searchResults.setH (results.get( ),  );
    }
    searchResults.setRelevanceStats(getRelevanceStats());
    searchResults.setNumH s(numResults);
    return searchResults;
  }
}
