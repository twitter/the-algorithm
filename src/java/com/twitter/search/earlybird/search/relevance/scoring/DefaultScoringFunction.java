package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport org.apac .lucene.search.Explanat on;

 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultsRelevanceStats;

/*
 * A sample scorer, doesn't really do anyth ng, returns t  sa  score for every docu nt.
 */
publ c class DefaultScor ngFunct on extends Scor ngFunct on {
  pr vate float score;

  publ c DefaultScor ngFunct on( mmutableSc ma nterface sc ma) {
    super(sc ma);
  }

  @Overr de
  protected float score(float luceneQueryScore) {
    score = luceneQueryScore;
    return luceneQueryScore;
  }

  @Overr de
  protected Explanat on doExpla n(float luceneScore) {
    // just an example - t  scor ng funct on w ll go away soon
    return Explanat on.match(luceneScore, "luceneScore=" + luceneScore);
  }

  @Overr de
  publ c vo d updateRelevanceStats(Thr ftSearchResultsRelevanceStats relevanceStats) {
    relevanceStats.setNumScored(relevanceStats.getNumScored() + 1);
     f (score == Scor ngFunct on.SK P_H T) {
      relevanceStats.setNumSk pped(relevanceStats.getNumSk pped() + 1);
    }
  }
}
