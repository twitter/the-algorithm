package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport org.apac .lucene.ut l.BytesRef;

/**
 * Extend {@l nk Earlyb rdPost ngsEnum} to add more funct onal  es for docs (and pos  ons)
 * enu rator of {@l nk Opt m zedPost ngL sts}.
 */
publ c abstract class Earlyb rdOpt m zedPost ngsEnum extends Earlyb rdPost ngsEnum {
  /** Current doc and  s frequency. */
  pr vate  nt currentDoc D = -1;
  pr vate  nt currentFreq = 0;

  /**
   * Next doc and  s frequency.
   * T se values should be set at {@l nk #loadNextPost ng()}.
   */
  protected  nt nextDoc D;
  protected  nt nextFreq;

  /** Po nter to t  enu rated post ng l st. */
  protected f nal  nt post ngL stPo nter;

  /** Total number of post ngs  n t  enu rated post ng l st. */
  protected f nal  nt numPost ngsTotal;

  /** Query cost tracker. */
  protected f nal QueryCostTracker queryCostTracker;

  /**
   * Sole constructor.
   *
   * @param post ngL stPo nter po nter to t  post ng l st for wh ch t  enu rator  s created
   * @param numPost ngs number of post ngs  n t  post ng l st for wh ch t  enu rator  s created
   */
  publ c Earlyb rdOpt m zedPost ngsEnum( nt post ngL stPo nter,  nt numPost ngs) {
    t .post ngL stPo nter = post ngL stPo nter;
    t .numPost ngsTotal = numPost ngs;

    // Get t  thread local query cost tracker.
    t .queryCostTracker = QueryCostTracker.getTracker();
  }

  /**
   * Set {@l nk #currentDoc D} and {@l nk #currentFreq} and load next post ng.
   * T   thod w ll de-dup  f dupl cate doc  Ds are stored.
   *
   * @return {@l nk #currentDoc D}
   * @see {@l nk #nextDoc()}
   */
  @Overr de
  protected f nal  nt nextDocNoDel() throws  OExcept on {
    currentDoc D = nextDoc D;

    // Return  m d ately  f exhausted.
     f (currentDoc D == NO_MORE_DOCS) {
      return NO_MORE_DOCS;
    }

    currentFreq = nextFreq;
    loadNextPost ng();

    //  n case dupl cate doc  D  s stored.
    wh le (currentDoc D == nextDoc D) {
      currentFreq += nextFreq;
      loadNextPost ng();
    }

    startCurrentDoc();
    return currentDoc D;
  }

  /**
   * Called w n {@l nk #nextDocNoDel()} advances to a new doc D.
   * Subclasses can do extra account ng as needed.
   */
  protected vo d startCurrentDoc() {
    // No-op  n t  class.
  }

  /**
   * Loads t  next post ng, sett ng t  nextDoc D and nextFreq.
   *
   * @see #nextDocNoDel()
   */
  protected abstract vo d loadNextPost ng();

  /**
   * Subclass should  mple nt {@l nk #sk pTo( nt)}.
   *
   * @see org.apac .lucene.search.Doc dSet erator#advance( nt)
   */
  @Overr de
  publ c f nal  nt advance( nt target) throws  OExcept on {
    // Sk pp ng to NO_MORE_DOCS or beyond largest doc  D.
     f (target == NO_MORE_DOCS || target > getLargestDoc D()) {
      currentDoc D = nextDoc D = NO_MORE_DOCS;
      currentFreq = nextFreq = 0;
      return NO_MORE_DOCS;
    }

    // Sk p as close as poss ble.
    sk pTo(target);

    // Call ng nextDoc to reach t  target, or go beyond    f target does not ex st.
     nt doc;
    do {
      doc = nextDoc();
    } wh le (doc < target);

    return doc;
  }

  /**
   * Used  n {@l nk #advance( nt)}.
   * T   thod should sk p to t  g ven target as close as poss ble, but NOT reach t  target.
   *
   * @see #advance( nt)
   */
  protected abstract vo d sk pTo( nt target);

  /**
   * Return loaded {@l nk #currentFreq}.
   *
   * @see org.apac .lucene. ndex.Post ngsEnum#freq()
   * @see #nextDocNoDel()
   */
  @Overr de
  publ c f nal  nt freq() throws  OExcept on {
    return currentFreq;
  }

  /**
   * Return loaded {@l nk #currentDoc D}.
   *
   * @see org.apac .lucene. ndex.Post ngsEnum#doc D() ()
   * @see #nextDocNoDel()
   */
  @Overr de
  publ c f nal  nt doc D() {
    return currentDoc D;
  }

  /*********************************************
   * Not Supported  nformat on                 *
   * @see org.apac .lucene. ndex.Post ngsEnum *
   *********************************************/

  @Overr de
  publ c  nt nextPos  on() throws  OExcept on {
    return -1;
  }

  @Overr de
  publ c  nt startOffset() throws  OExcept on {
    return -1;
  }

  @Overr de
  publ c  nt endOffset() throws  OExcept on {
    return -1;
  }

  @Overr de
  publ c BytesRef getPayload() throws  OExcept on {
    return null;
  }

  /*********************************
   *  lper  thods for subclasses *
   *********************************/

  protected  nt getCurrentFreq() {
    return currentFreq;
  }
}
