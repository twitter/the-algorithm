package com.tw ter.search.earlyb rd_root.collectors;

 mport com.tw ter.search.common.relevance.ut ls.ResultComparators;
 mport com.tw ter.search.common.ut l.earlyb rd.Thr ftSearchResultsRelevanceStatsUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultsRelevanceStats;

/**
 * Relevance rgeCollector class extends (@l nk Recency rgeCollector} to do k-way  rge of
 * earlyb rd responses, but sorted by relevance score.
 *
 * Note that t   s a superset of funct onal y found  n
 * {@l nk com.tw ter.search.blender.serv ces.earlyb rd.relevance.RelevanceCollector}
 *  f   make changes  re, evaluate  f t y should be made  n RelevanceCollector as  ll.
 */
publ c class Relevance rgeCollector extends Recency rgeCollector {

  publ c Relevance rgeCollector( nt numResponses) {
    super(numResponses, ResultComparators.SCORE_COMPARATOR);
  }

  @Overr de
  protected vo d collectStats(Earlyb rdResponse response) {
    super.collectStats(response);

     f (!response.getSearchResults(). sSetRelevanceStats()) {
      return;
    }

     f (!f nalResults. sSetRelevanceStats()) {
      f nalResults.setRelevanceStats(new Thr ftSearchResultsRelevanceStats());
    }

    Thr ftSearchResultsRelevanceStats base = f nalResults.getRelevanceStats();
    Thr ftSearchResultsRelevanceStats delta = response.getSearchResults().getRelevanceStats();

    Thr ftSearchResultsRelevanceStatsUt l.addRelevanceStats(base, delta);
  }
}
