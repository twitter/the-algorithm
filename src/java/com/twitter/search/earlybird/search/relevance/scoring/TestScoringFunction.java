package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport org.apac .lucene.search.Explanat on;

 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadataOpt ons;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultType;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultsRelevanceStats;

/**
 * A dum  scor ng funct on for test, t  score  s always t et d/10000.0
 * S nce score_f lter: operator requ res all score to be bet en [0, 1],  f   want to use t 
 * w h  , don't use any t et  d larger than 10000  n y  test.
 */
publ c class TestScor ngFunct on extends Scor ngFunct on {
  pr vate Thr ftSearchResult tadata  tadata = null;
  pr vate float score;

  publ c TestScor ngFunct on( mmutableSc ma nterface sc ma) {
    super(sc ma);
  }

  @Overr de
  protected float score(float luceneQueryScore) {
    long t et d = t et DMapper.getT et D(getCurrentDoc D());
    t .score = (float) (t et d / 10000.0);
    System.out.pr ntln(Str ng.format("score for t et %10d  s %6.3f", t et d, score));
    return t .score;
  }

  @Overr de
  protected Explanat on doExpla n(float luceneScore) {
    return null;
  }

  @Overr de
  publ c Thr ftSearchResult tadata getResult tadata(Thr ftSearchResult tadataOpt ons opt ons) {
     f ( tadata == null) {
       tadata = new Thr ftSearchResult tadata()
          .setResultType(Thr ftSearchResultType.RELEVANCE)
          .setPengu nVers on(Earlyb rdConf g.getPengu nVers onByte());
       tadata.setScore(score);
    }
    return  tadata;
  }

  @Overr de
  publ c vo d updateRelevanceStats(Thr ftSearchResultsRelevanceStats relevanceStats) {
  }
}
