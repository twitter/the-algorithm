package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport javax.annotat on.Nullable;

 mport org.apac .lucene.ut l.packed.Packed nts;

/**
 * A Post ngsEnum for  erat ng over LowDFPacked ntsPost ngL sts.
 *
 * Can be used w h pos  ons and w hout pos  ons.
 */
publ c class LowDFPacked ntsPost ngsEnum extends Earlyb rdOpt m zedPost ngsEnum {
  pr vate stat c f nal  nt SK P_ NTERVAL = 128;

  pr vate f nal Packed nts.Reader packedDoc ds;
  @Nullable
  pr vate f nal Packed nts.Reader packedPos  ons;
  pr vate f nal  nt lastPost ngPo nter;
  pr vate f nal  nt largestDoc D;
  pr vate  nt currentPos  onPo nter;

  /** Po nter to t  next post ng that w ll be loaded. */
  pr vate  nt nextPost ngPo nter;

  /**
   * Creates a new Post ngsEnum for all post ngs  n a g ven term.
   */
  publ c LowDFPacked ntsPost ngsEnum(
      Packed nts.Reader packedDoc ds,
      @Nullable
      Packed nts.Reader packedPos  ons,
       nt post ngL stPo nter,
       nt numPost ngs) {
    super(post ngL stPo nter, numPost ngs);

    t .packedDoc ds = packedDoc ds;
    t .packedPos  ons = packedPos  ons;
    t .nextPost ngPo nter = post ngL stPo nter;

    t .lastPost ngPo nter = post ngL stPo nter + numPost ngs - 1;
    t .largestDoc D = ( nt) packedDoc ds.get(lastPost ngPo nter);

    loadNextPost ng();

    // Treat each term as a s ngle block load.
    queryCostTracker.track(QueryCostTracker.CostType.LOAD_OPT M ZED_POST NG_BLOCK);
  }

  @Overr de
  protected vo d loadNextPost ng() {
     f (nextPost ngPo nter <= lastPost ngPo nter) {
      nextDoc D = ( nt) packedDoc ds.get(nextPost ngPo nter);
      nextFreq = 1;
    } else {
      // all post ngs fully processed
      nextDoc D = NO_MORE_DOCS;
      nextFreq = 0;
    }
    nextPost ngPo nter++;
  }

  @Overr de
  protected vo d startCurrentDoc() {
     f (packedPos  ons != null) {
      /**
       * Re mber w re    re at t  beg nn ng of t  doc, so that   can  erate over t 
       * pos  ons for t  doc  f needed.
       * Adjust by `- 1 - getCurrentFreq()` because   already advanced beyond t  last post ng  n
       * t  prev ous loadNextPost ng() calls.
       * @see #nextDocNoDel()
       */
      currentPos  onPo nter = nextPost ngPo nter - 1 - getCurrentFreq();
    }
  }

  @Overr de
  protected vo d sk pTo( nt target) {
    assert target != NO_MORE_DOCS : "Should be handled  n parent class advance  thod";

    // now   know t re must be a doc  n t  block that   can return
     nt sk p ndex = nextPost ngPo nter + SK P_ NTERVAL;
    wh le (sk p ndex <= lastPost ngPo nter && target > packedDoc ds.get(sk p ndex)) {
      nextPost ngPo nter = sk p ndex;
      sk p ndex += SK P_ NTERVAL;
    }
  }

  @Overr de
  publ c  nt nextPos  on() throws  OExcept on {
     f (packedPos  ons == null) {
      return -1;
    } else  f (currentPos  onPo nter < packedPos  ons.s ze()) {
      return ( nt) packedPos  ons.get(currentPos  onPo nter++);
    } else {
      return -1;
    }
  }

  @Overr de
  publ c  nt getLargestDoc D() throws  OExcept on {
    return largestDoc D;
  }

  @Overr de
  publ c long cost() {
    // cost would be -1  f t  enum  s exhausted.
    f nal  nt cost = lastPost ngPo nter - nextPost ngPo nter + 1;
    return cost < 0 ? 0 : cost;
  }
}
