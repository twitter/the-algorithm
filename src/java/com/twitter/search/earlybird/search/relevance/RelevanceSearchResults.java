package com.tw ter.search.earlyb rd.search.relevance;

 mport com.tw ter.search.earlyb rd.search.H ;
 mport com.tw ter.search.earlyb rd.search.S mpleSearchResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultsRelevanceStats;

publ c class RelevanceSearchResults extends S mpleSearchResults {
  publ c f nal Thr ftSearchResult tadata[] result tadata;
  pr vate Thr ftSearchResultsRelevanceStats relevanceStats = null;
  pr vate long scor ngT  Nanos = 0;

  publ c RelevanceSearchResults( nt s ze) {
    super(s ze);
    t .result tadata = new Thr ftSearchResult tadata[s ze];
  }

  publ c vo d setH (H  h ,  nt h  ndex) {
    h s[h  ndex] = h ;
    result tadata[h  ndex] = h .get tadata();
  }

  publ c vo d setRelevanceStats(Thr ftSearchResultsRelevanceStats relevanceStats) {
    t .relevanceStats = relevanceStats;
  }
  publ c Thr ftSearchResultsRelevanceStats getRelevanceStats() {
    return relevanceStats;
  }

  publ c vo d setScor ngT  Nanos(long scor ngT  Nanos) {
    t .scor ngT  Nanos = scor ngT  Nanos;
  }

  publ c long getScor ngT  Nanos() {
    return scor ngT  Nanos;
  }
}
