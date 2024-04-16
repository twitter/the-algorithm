package com.tw ter.search.earlyb rd.search.relevance.collectors;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.common_ nternal.collect ons.RandomAccessPr or yQueue;
 mport com.tw ter.search.common.relevance.features.T et ntegerSh ngleS gnature;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.search.EarlyTerm nat onState;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceH ;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceSearchRequest nfo;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceSearchResults;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.Scor ngFunct on;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultsRelevanceStats;

/**
 * RelevanceTopCollector  s a results collector that collects t  top numResults by
 * score, f lter ng out dupl cates.
 */
publ c class RelevanceTopCollector extends AbstractRelevanceCollector {
  // Search results are collected  n a m n- ap.
  protected f nal RandomAccessPr or yQueue<RelevanceH , T et ntegerSh ngleS gnature> m nQueue;

  // Number of h s actually added to t  m n queue after dupe f lter ng and sk pp ng.
  // Less than or equal to numH sProcessed.
  protected  nt numH sCollected;

  // T  'top' of t  m n  ap, or, t  lo st scored docu nt  n t   ap.
  pr vate RelevanceH  pqTop;
  pr vate float lo stScore = Scor ngFunct on.SK P_H T;

  pr vate f nal boolean  sF lterDupes;

  publ c RelevanceTopCollector(
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
    t .m nQueue = new RandomAccessPr or yQueue<RelevanceH , T et ntegerSh ngleS gnature>(
        searchRequest nfo.getNumResultsRequested(), RelevanceH .PQ_COMPARATOR_BY_SCORE) {
      @Overr de
      protected RelevanceH  getSent nelObject() {
        return new RelevanceH (); // default relevance constructor would create a h  w h t 
                                   // lo st score poss ble.
      }
    };
    t .pqTop = m nQueue.top();
    t . sF lterDupes = getSearchRequest nfo().getRelevanceOpt ons(). sF lterDups();
  }

  protected vo d collectW hScore nternal(
      long t et D,
      long t  Sl ce D,
      float score,
      Thr ftSearchResult tadata  tadata) {
    // T  collector cannot handle t se scores:
    assert !Float. sNaN(score);

     f (score <= lo stScore) {
      // S nce docs are returned  n-order ( .e.,  ncreas ng doc  d), a docu nt
      // w h equal score to pqTop.score cannot compete s nce H Queue favors
      // docu nts w h lo r doc  ds. T refore reject those docs too.
      //  MPORTANT: docs sk pped by t  scor ng funct on w ll have scores set
      // to Scor ngFunct on.SK P_H T,  an ng t y w ll not be collected.
      return;
    }

    boolean dupFound = false;
    Precond  ons.c ckState( tadata. sSetS gnature(),
        "T  s gnature should be set at  tadata collect on t  , but    s null. "
            + "T et  d = %s,  tadata = %s",
        t et D,
         tadata);
     nt s gnature nt =  tadata.getS gnature();
    f nal T et ntegerSh ngleS gnature s gnature =
        T et ntegerSh ngleS gnature.deser al ze(s gnature nt);

     f ( sF lterDupes) {
      // update dupl cate  f any
       f (s gnature nt != T et ntegerSh ngleS gnature.DEFAULT_NO_S GNATURE) {
        dupFound = m nQueue. ncre ntEle nt(
            s gnature,
            ele nt -> {
               f (score > ele nt.getScore()) {
                ele nt.update(t  Sl ce D, t et D, s gnature,  tadata);
              }
            }
        );
      }
    }

     f (!dupFound) {
      numH sCollected++;

      //  f   d dn't f nd a dupl cate ele nt to update t n   add   now as a new ele nt to t 
      // pq
      pqTop = m nQueue.updateTop(top -> top.update(t  Sl ce D, t et D, s gnature,  tadata));

      lo stScore = pqTop.getScore();
    }
  }

  @Overr de
  protected vo d doCollectW hScore(f nal long t et D, f nal float score) throws  OExcept on {
    Thr ftSearchResult tadata  tadata = collect tadata();
    scor ngFunct on.populateResult tadataBasedOnScor ngData(
        searchRequest nfo.getSearchQuery().getResult tadataOpt ons(),
         tadata,
        scor ngFunct on.getScor ngDataForCurrentDocu nt());
    collectW hScore nternal(t et D, currT  Sl ce D, score,  tadata);
  }

  @Overr de
  publ c EarlyTerm nat onState  nnerShouldCollectMore() {
    // Note that numH sCollected  re m ght be less than num results collected  n t 
    // Tw terEarlyTerm nat onCollector,  f   h  dups or t re are very low scores.
     f (numH sCollected >= getMaxH sToProcess()) {
      return setEarlyTerm nat onState(EarlyTerm nat onState.TERM NATED_MAX_H TS_EXCEEDED);
    }
    return EarlyTerm nat onState.COLLECT NG;
  }

  @Overr de
  protected RelevanceSearchResults doGetRelevanceResults() throws  OExcept on {
    return getRelevanceResults nternal();
  }

  protected RelevanceSearchResults getRelevanceResults nternal() {
    return resultsFromQueue(m nQueue, getSearchRequest nfo().getNumResultsRequested(),
                            getRelevanceStats());
  }

  pr vate stat c RelevanceSearchResults resultsFromQueue(
      RandomAccessPr or yQueue<RelevanceH , T et ntegerSh ngleS gnature> pq,
       nt des redNumResults,
      Thr ftSearchResultsRelevanceStats relevanceStats) {
    // tr m f rst  n case   d dn't f ll up t  queue to not get any sent nel values  re
     nt numResults = pq.tr m();
     f (numResults > des redNumResults) {
      for ( nt   = 0;   < numResults - des redNumResults;  ++) {
        pq.pop();
      }
      numResults = des redNumResults;
    }
    RelevanceSearchResults results = new RelevanceSearchResults(numResults);
    //  nsert h s  n decreas ng order by score
    for ( nt   = numResults - 1;   >= 0;  --) {
      RelevanceH  h  = pq.pop();
      results.setH (h ,  );
    }
    results.setRelevanceStats(relevanceStats);
    results.setNumH s(numResults);
    return results;
  }
}
