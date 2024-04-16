package com.tw ter.search.earlyb rd.querycac ;

 mport java. o. OExcept on;

 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.ut l.B Doc dSet;
 mport org.apac .lucene.ut l.B Set;
 mport org.apac .lucene.ut l.F xedB Set;
 mport org.apac .lucene.ut l.SparseF xedB Set;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.core.earlyb rd. ndex.QueryCac ResultForSeg nt;
 mport com.tw ter.search.earlyb rd.RecentT etRestr ct on;
 mport com.tw ter.search.earlyb rd.search.AbstractResultsCollector;
 mport com.tw ter.search.earlyb rd.search.SearchRequest nfo;
 mport com.tw ter.search.earlyb rd.search.SearchResults nfo;
 mport com.tw ter.search.earlyb rd.search.quer es.S nceUnt lF lter;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;

 mport stat c org.apac .lucene.search.Doc dSet erator.NO_MORE_DOCS;

 mport stat c com.tw ter.search.core.earlyb rd. ndex.T  Mapper. LLEGAL_T ME;

/**
 * Collector to update t  query cac  (one seg nt for a f lter)
 */
publ c class QueryCac ResultCollector
    extends AbstractResultsCollector<SearchRequest nfo, SearchResults nfo> {
  pr vate stat c f nal  nt UNSET = -1;

  pr vate f nal QueryCac F lter queryCac F lter;
  pr vate f nal Dec der dec der;

  pr vate B Set b Set;
  pr vate long card nal y = 0L;
  pr vate  nt start ngDoc D = UNSET;

  publ c QueryCac ResultCollector(
       mmutableSc ma nterface sc ma,
      QueryCac F lter queryCac F lter,
      Earlyb rdSearc rStats searc rStats,
      Dec der dec der,
      Clock clock,
       nt requestDebugMode) {
    super(sc ma,
        queryCac F lter.createSearchRequest nfo(),
        clock,
        searc rStats,
        requestDebugMode);
    t .queryCac F lter = queryCac F lter;
    t .dec der = dec der;
  }

  @Overr de
  publ c vo d startSeg nt() throws  OExcept on {
    // T  doc  Ds  n t  opt m zed seg nts are always  n t  0 .. (seg ntS ze - 1) range, so  
    // can use a dense b set to collect t  h s. Ho ver, unopt m zed seg nts can use any  nt
    // doc  Ds, so   have to use a sparse b set to collect t  h s  n those seg nts.
     f (currTw terReader.getSeg ntData(). sOpt m zed()) {
      sw ch (queryCac F lter.getResultSetType()) {
        case F xedB Set:
          b Set = new F xedB Set(currTw terReader.maxDoc());
          break;
        case SparseF xedB Set:
          b Set = new SparseF xedB Set(currTw terReader.maxDoc());
          break;
        default:
          throw new  llegalStateExcept on(
              "Unknown ResultSetType: " + queryCac F lter.getResultSetType().na ());
      }
    } else {
      b Set = new SparseF xedB Set(currTw terReader.maxDoc());
    }

    start ngDoc D = f ndStart ngDoc D();
    card nal y = 0;
  }

  @Overr de
  protected vo d doCollect(long t et D)  {
    b Set.set(curDoc d);
    card nal y++;
  }

  @Overr de
  protected SearchResults nfo doGetResults() {
    return new SearchResults nfo();
  }

  publ c QueryCac ResultForSeg nt getCac dResult() {
    // Note that B Set.card nal y takes l near t    n t  s ze of t  maxDoc, so   track
    // card nal y separately.
    return new QueryCac ResultForSeg nt(new B Doc dSet(b Set, card nal y),
        card nal y, start ngDoc D);
  }

  /**
   *   don't want to return results less than 15 seconds older than t  most recently  ndexed t et,
   * as t y m ght not be completely  ndexed.
   *   can't s mply use t  f rst h , as so  cac d f lters m ght not have any h s,
   * e.g. has_engage nt  n t  protected cluster.
   *   can't use a clock because streams can lag.
   */
  pr vate  nt f ndStart ngDoc D() throws  OExcept on {
     nt lastT   = currTw terReader.getSeg ntData().getT  Mapper().getLastT  ();
     f (lastT   ==  LLEGAL_T ME) {
      return NO_MORE_DOCS;
    }

     nt unt lT   = RecentT etRestr ct on.queryCac Unt lT  (dec der, lastT  );
     f (unt lT   == 0) {
      return currTw terReader.getSmallestDoc D();
    }

    return S nceUnt lF lter.getUnt lQuery(unt lT  )
        .create  ght(new  ndexSearc r(currTw terReader), ScoreMode.COMPLETE_NO_SCORES, 1.0f)
        .scorer(currTw terReader.getContext())
        . erator()
        .nextDoc();
  }
}
