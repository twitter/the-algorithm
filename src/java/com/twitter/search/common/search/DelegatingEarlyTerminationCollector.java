package com.tw ter.search.common.search;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport javax.annotat on.Nullable;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene.search.Collector;
 mport org.apac .lucene.search.LeafCollector;
 mport org.apac .lucene.search.Scorable;
 mport org.apac .lucene.search.ScoreMode;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.query.thr ftjava.CollectorParams;

/**
 * A {@l nk com.tw ter.search.common.search.Tw terEarlyTerm nat onCollector}
 * that delegates actual h  collect on to a sub collector.
 */
publ c f nal class Delegat ngEarlyTerm nat onCollector
    extends Tw terEarlyTerm nat onCollector {
  pr vate f nal Collector subCollector;
  pr vate LeafCollector subLeafCollector;

  /** Creates a new Delegat ngEarlyTerm nat onCollector  nstance. */
  publ c Delegat ngEarlyTerm nat onCollector(Collector subCollector,
                                             CollectorParams collectorParams,
                                             Term nat onTracker term nat onTracker,
                                             @Nullable QueryCostProv der queryCostProv der,
                                              nt numDocsBet enT  outC cks,
                                             Clock clock) {
    super(
        collectorParams,
        term nat onTracker,
        queryCostProv der,
        numDocsBet enT  outC cks,
        clock);
    t .subCollector = subCollector;
  }

  @Overr de
  publ c vo d setScorer(Scorable scorer) throws  OExcept on {
    super.setScorer(scorer);
    subLeafCollector.setScorer(scorer);
  }

  @Overr de
  protected vo d doCollect() throws  OExcept on {
    subLeafCollector.collect(curDoc d);
  }

  @Overr de
  protected vo d doF n shSeg nt( nt lastSearc dDoc D) throws  OExcept on {
     f (subCollector  nstanceof Tw terCollector) {
      ((Tw terCollector) subCollector).f n shSeg nt(lastSearc dDoc D);
    }
  }

  @Overr de
  publ c vo d setNextReader(LeafReaderContext context) throws  OExcept on {
    super.setNextReader(context);
    subLeafCollector = subCollector.getLeafCollector(context);
  }

  @Overr de
  publ c ScoreMode scoreMode() {
    return subCollector.scoreMode();
  }

  @Overr de
  publ c L st<Str ng> getDebug nfo() {
    return null;
  }
}
