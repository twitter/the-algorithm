package com.tw ter.search.earlyb rd.search.relevance.collectors;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd.facets.Language togram;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.search.AbstractResultsCollector;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceSearchRequest nfo;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceSearchResults;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.Scor ngFunct on;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadataOpt ons;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultsRelevanceStats;

/**
 * AbstractRelevanceCollector  s a results collector that collects RelevanceH  results
 * wh ch  nclude more deta led  nformat on than a normal H .
 */
publ c abstract class AbstractRelevanceCollector
    extends AbstractResultsCollector<RelevanceSearchRequest nfo, RelevanceSearchResults> {
  protected f nal Scor ngFunct on scor ngFunct on;
  pr vate f nal Thr ftSearchResultsRelevanceStats relevanceStats;
  pr vate f nal Earlyb rdCluster cluster;
  pr vate f nal UserTable userTable;

  // Per-language result counts.
  pr vate f nal Language togram language togram = new Language togram();

  // Accumulated t   spend on relevance scor ng across all collected h s,  nclud ng batch scor ng.
  pr vate long scor ngT  Nanos = 0;

  publ c AbstractRelevanceCollector(
       mmutableSc ma nterface sc ma,
      RelevanceSearchRequest nfo searchRequest nfo,
      Scor ngFunct on scor ngFunct on,
      Earlyb rdSearc rStats searc rStats,
      Earlyb rdCluster cluster,
      UserTable userTable,
      Clock clock,
       nt requestDebugMode) {
    super(sc ma, searchRequest nfo, clock, searc rStats, requestDebugMode);
    t .scor ngFunct on = scor ngFunct on;
    t .relevanceStats = new Thr ftSearchResultsRelevanceStats();
    t .cluster = cluster;
    t .userTable = userTable;
  }

  /**
   * Subclasses must  mple nt t   thod to actually collect a scored relevance h .
   */
  protected abstract vo d doCollectW hScore(long t et D, float score) throws  OExcept on;

  @Overr de
  publ c f nal vo d startSeg nt() throws  OExcept on {
    scor ngFunct on.setNextReader(currTw terReader);

    Thr ftSearchResult tadataOpt ons opt ons =
        searchRequest nfo.getSearchQuery().getResult tadataOpt ons();
    featuresRequested = opt ons != null && opt ons. sReturnSearchResultFeatures();
  }

  @Overr de
  protected f nal vo d doCollect(long t et D) throws  OExcept on {
    f nal long scor ngStartNanos = getClock().nowNanos();
    float luceneSore = scorer.score();
    f nal float score = scor ngFunct on.score(curDoc d, luceneSore);
    f nal long scor ngEndNanos = getClock().nowNanos();
    addToOverallScor ngT  Nanos(scor ngStartNanos, scor ngEndNanos);

    scor ngFunct on.updateRelevanceStats(relevanceStats);

    updateH Counts(t et D);

    doCollectW hScore(t et D, score);
  }

  protected f nal vo d addToOverallScor ngT  Nanos(long scor ngStartNanos, long scor ngEndNanos) {
    scor ngT  Nanos += scor ngEndNanos - scor ngStartNanos;
  }

  protected f nal Thr ftSearchResult tadata collect tadata() throws  OExcept on {
    Thr ftSearchResult tadataOpt ons opt ons =
        searchRequest nfo.getSearchQuery().getResult tadataOpt ons();
    Precond  ons.c ckNotNull(opt ons);
    Thr ftSearchResult tadata  tadata =
        Precond  ons.c ckNotNull(scor ngFunct on.getResult tadata(opt ons));
     f ( tadata. sSetLanguage()) {
      language togram. ncre nt( tadata.getLanguage().getValue());
    }

    // So  add  onal  tadata wh ch  s not prov ded by t  scor ng funct on, but
    // by access ng t  reader d rectly.
     f (currTw terReader != null) {
      f llResultGeoLocat on( tadata);
       f (searchRequest nfo. sCollectConversat on d()) {
        long conversat on d =
            docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.CONVERSAT ON_ D_CSF);
         f (conversat on d != 0) {
          ensureExtra tadata sSet( tadata);
           tadata.getExtra tadata().setConversat on d(conversat on d);
        }
      }
    }

    // C ck and collect h  attr but on data,  f  's ava lable.
    f llH Attr but on tadata( tadata);

    long fromUser d = docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.FROM_USER_ D_CSF);
     f (searchRequest nfo. sGetFromUser d()) {
       tadata.setFromUser d(fromUser d);
    }

    collectExclus veConversat onAuthor d( tadata);
    collectFacets( tadata);
    collectFeatures( tadata);
    collect sProtected( tadata, cluster, userTable);

    return  tadata;
  }

  protected f nal Thr ftSearchResultsRelevanceStats getRelevanceStats() {
    return relevanceStats;
  }

  publ c f nal Language togram getLanguage togram() {
    return language togram;
  }

  @Overr de
  protected f nal RelevanceSearchResults doGetResults() throws  OExcept on {
    f nal RelevanceSearchResults results = doGetRelevanceResults();
    results.setScor ngT  Nanos(scor ngT  Nanos);
    return results;
  }

  /**
   * For subclasses to process and aggregate collected h s.
   */
  protected abstract RelevanceSearchResults doGetRelevanceResults() throws  OExcept on;
}
