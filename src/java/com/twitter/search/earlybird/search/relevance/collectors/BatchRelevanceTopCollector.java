package com.tw ter.search.earlyb rd.search.relevance.collectors;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport java.ut l.concurrent.T  Un ;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchResultFeatures;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.search.EarlyTerm nat onState;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.search.relevance.L nearScor ngData;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceSearchRequest nfo;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceSearchResults;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.BatchH ;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.Scor ngFunct on;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRelevanceOpt ons;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultExtra tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;

/**
 * BatchRelevanceTopCollector  s s m lar to t  `RelevanceTopCollector`  n what   outputs:
 * Collects t  top numResults by score, f lter ng out dupl cates
 * and results w h scores equal to Flat.M N_VALUE.
 * T  way that   ach eves that  s d fferent though:   w ll score docu nts through t  batch score
 * funct on  nstead of scor ng docu nts one by one.
 */
publ c class BatchRelevanceTopCollector extends RelevanceTopCollector {
  protected f nal L st<BatchH > h s;

  publ c BatchRelevanceTopCollector(
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
    t .h s = new ArrayL st<>(( nt) getMaxH sToProcess());
  }

  @Overr de
  protected vo d doCollectW hScore(long t et D, float score) throws  OExcept on {
    Pa r<L nearScor ngData, Thr ftSearchResultFeatures> pa r =
        scor ngFunct on.collectFeatures(score);
    Thr ftSearchResult tadata  tadata = collect tadata();
    h s.add(new BatchH (pa r.getF rst(),
        pa r.getSecond(),
         tadata,
        t et D,
        currT  Sl ce D));
  }

  @Overr de
  publ c EarlyTerm nat onState  nnerShouldCollectMore() {
     f (h s.s ze() >= getMaxH sToProcess()) {
      return setEarlyTerm nat onState(EarlyTerm nat onState.TERM NATED_MAX_H TS_EXCEEDED);
    }
    return EarlyTerm nat onState.COLLECT NG;
  }

  @Overr de
  protected RelevanceSearchResults doGetRelevanceResults() throws  OExcept on {
    f nal long scor ngStartNanos = getClock().nowNanos();
    float[] scores = scor ngFunct on.batchScore(h s);
    f nal long scor ngEndNanos = getClock().nowNanos();
    addToOverallScor ngT  Nanos(scor ngStartNanos, scor ngEndNanos);
    exportBatchScor ngT  (scor ngEndNanos - scor ngStartNanos);

    for ( nt   = 0;   < h s.s ze();  ++) {
      BatchH  h  = h s.get( );
      Thr ftSearchResult tadata  tadata = h .get tadata();

       f (! tadata. sSetExtra tadata()) {
         tadata.setExtra tadata(new Thr ftSearchResultExtra tadata());
      }
       tadata.getExtra tadata().setFeatures(h .getFeatures());


      // Populate t  Thr ftSearchResult tadata post batch scor ng w h  nformat on from t 
      // L nearScor ngData, wh ch now  ncludes a score.
      scor ngFunct on.populateResult tadataBasedOnScor ngData(
          searchRequest nfo.getSearchQuery().getResult tadataOpt ons(),
           tadata,
          h .getScor ngData());

      collectW hScore nternal(
          h .getT et D(),
          h .getT  Sl ce D(),
          scores[ ],
           tadata
      );
    }
    return getRelevanceResults nternal();
  }

  pr vate vo d exportBatchScor ngT  (long scor ngT  Nanos) {
    Thr ftSearchRelevanceOpt ons relevanceOpt ons = searchRequest nfo.getRelevanceOpt ons();
     f (relevanceOpt ons. sSetRank ngParams()
        && relevanceOpt ons.getRank ngParams(). sSetSelectedTensorflowModel()) {
      Str ng model = relevanceOpt ons.getRank ngParams().getSelectedTensorflowModel();
      SearchT  rStats batchScor ngPerModelT  r = SearchT  rStats.export(
          Str ng.format("batch_scor ng_t  _for_model_%s", model),
          T  Un .NANOSECONDS,
          false,
          true);
      batchScor ngPerModelT  r.t  r ncre nt(scor ngT  Nanos);
    }
  }
}
