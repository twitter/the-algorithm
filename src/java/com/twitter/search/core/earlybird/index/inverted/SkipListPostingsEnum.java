package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rdRealt   ndexSeg ntData;

 mport stat c com.tw ter.search.core.earlyb rd. ndex. nverted.Sk pL stConta ner. NVAL D_POS T ON;

/**
 * TermDocs enu rator used by {@l nk Sk pL stPost ngL st}.
 */
publ c class Sk pL stPost ngsEnum extends Post ngsEnum {
  /**  n  al ze cur doc  D and frequency. */
  pr vate  nt curDoc = TermsArray. NVAL D;
  pr vate  nt curFreq = 0;

  pr vate f nal  nt post ngPo nter;

  pr vate f nal  nt cost;

  /**
   * maxPubl s dPo nter ex sts to prevent us from return ng docu nts that are part ally  ndexed.
   * T se po nters are safe to follow, but t  docu nts should not be returned. See
   * {@l nk Earlyb rdRealt   ndexSeg ntData#getSyncData()} ()}.
   */
  pr vate f nal  nt maxPubl s dPo nter;

  /** Sk p l st  nfo and search key */
  pr vate f nal Sk pL stConta ner<Sk pL stPost ngL st.Key> sk pl st;
  pr vate f nal Sk pL stPost ngL st.Key key = new Sk pL stPost ngL st.Key();

  /**
   * Po nter/post ng/doc D of next post ng  n t  sk p l st.
   *  Not ce t  next  re  s relat ve to last post ng w h curDoc  D.
   */
  pr vate  nt nextPost ngPo nter;
  pr vate  nt nextPost ngDoc D;

  /**
   *   save t  pos  onPo nter because   must walk t  post ng l st to obta n term frequency
   * before   can start  erat ng through docu nt pos  ons. To do that walk,    ncre nt
   * post ngsPo nter unt l   po nts to t  f rst post ng for t  next doc, so post ngsPo nter  s no
   * longer what   want to use as t  start of t  pos  on l st. T  pos  on po nter starts out
   * po nt ng to t  f rst post ng w h that doc  D value. T re can be dupl cate doc  D values w h
   * d fferent pos  ons. To f nd subsequent pos  ons,   s mply walk t  post ng l st us ng t 
   * po nter.
   */
  pr vate  nt pos  onPo nter = -1;

  /**
   * T  payloadPo nter should only be called after call ng nextPos  on, as   po nts to a payload
   * for each pos  on.    s not updated unless nextPos  on  s called.
   */
  pr vate  nt payloadPo nter = -1;

  /** Search f nger used  n advance  thod. */
  pr vate f nal Sk pL stSearchF nger advanceSearchF nger;

  /**
   * A new {@l nk Post ngsEnum} for a real-t   sk p l st-based post ng l st.
   */
  publ c Sk pL stPost ngsEnum(
       nt post ngPo nter,
       nt docFreq,
       nt maxPubl s dPo nter,
      Sk pL stConta ner<Sk pL stPost ngL st.Key> sk pl st) {
    t .post ngPo nter = post ngPo nter;
    t .sk pl st = sk pl st;
    t .advanceSearchF nger = t .sk pl st.bu ldSearchF nger();
    t .maxPubl s dPo nter = maxPubl s dPo nter;
    t .nextPost ngPo nter = post ngPo nter;

    // WARN NG:
    // docFreq  s approx mate and may not be t  true docu nt frequency of t  post ng l st.
    t .cost = docFreq;

     f (post ngPo nter != -1) {
      // Because t  post ng po nter  s not negat ve 1,   know  's val d.
      readNextPost ng();
    }

    advanceSearchF nger.reset();
  }

  @Overr de
  publ c f nal  nt nextDoc() {
    // Not ce  f sk p l st  s exhausted nextPost ngPo nter w ll po nt back to post ngPo nter s nce
    // sk p l st  s c rcle l nked.
     f (nextPost ngPo nter == post ngPo nter) {
      // Sk p l st  s exhausted.
      curDoc = NO_MORE_DOCS;
      curFreq = 0;
    } else {
      // Sk p l st  s not exhausted.
      curDoc = nextPost ngDoc D;
      curFreq = 1;
      pos  onPo nter = nextPost ngPo nter;

      // Keep read ng all t  post ng w h t  sa  doc  D.
      // Not ce:
      //   - post ng w h t  sa  doc  D w ll be stored consecut vely
      //     s nce t  sk p l st  s sorted.
      //   -  f sk p l st  s exhausted, nextPost ngPo nter w ll beco  post ngPo nter
      //     s nce sk p l st  s c rcle l nked.
      readNextPost ng();
      wh le (nextPost ngPo nter != post ngPo nter && nextPost ngDoc D == curDoc) {
        curFreq++;
        readNextPost ng();
      }
    }

    // Returned updated curDoc.
    return curDoc;
  }

  /**
   * Moves t  enu rator forward by one ele nt, t n reads t   nformat on at that pos  on.
   * */
  pr vate vo d readNextPost ng() {
    // Move search f nger forward at lo st level.
    advanceSearchF nger.setPo nter(0, nextPost ngPo nter);

    // Read next post ng po nter.
    nextPost ngPo nter = sk pl st.getNextPo nter(nextPost ngPo nter);

    // Read t  new post ng pos  oned under nextPost ngPo nter  nto t  nextPost ngDoc D.
    readNextPost ng nfo();
  }

  pr vate boolean  sPo nterPubl s d( nt po nter) {
    return po nter <= maxPubl s dPo nter;
  }

  /** Read next post ng and doc  d encoded  n next post ng. */
  pr vate vo d readNextPost ng nfo() {
    //   need to sk p over every po nter that has not been publ s d to t  Enum, ot rw se t 
    // searc r w ll see unpubl s d docu nts.   also end term nat on  f   reach
    // nextPost ngPo nter == post ngPo nter, because that  ans   have reac d t  end of t 
    // sk pl st.
    wh le (! sPo nterPubl s d(nextPost ngPo nter) && nextPost ngPo nter != post ngPo nter) {
      // Move search f nger forward at lo st level.
      advanceSearchF nger.setPo nter(0, nextPost ngPo nter);

      // Read next post ng po nter.
      nextPost ngPo nter = sk pl st.getNextPo nter(nextPost ngPo nter);
    }

    // Not ce  f sk p l st  s exhausted, nextPost ngPo nter w ll be post ngPo nter
    // s nce sk p l st  s c rcle l nked.
     f (nextPost ngPo nter != post ngPo nter) {
      nextPost ngDoc D = sk pl st.getValue(nextPost ngPo nter);
    } else {
      nextPost ngDoc D = NO_MORE_DOCS;
    }
  }

  /**
   * Jump to t  target, t n use {@l nk #nextDoc()} to collect nextDoc  nfo.
   * Not ce target m ght be smaller than curDoc or smallestDoc D.
   */
  @Overr de
  publ c f nal  nt advance( nt target) {
     f (target == NO_MORE_DOCS) {
      // Exhaust t  post ng l st, so that future calls to doc D() always return NO_MORE_DOCS.
      nextPost ngPo nter = post ngPo nter;
    }

     f (nextPost ngPo nter == post ngPo nter) {
      // Call nextDoc to ensure that all values are updated and   don't have to dupl cate that
      //  re.
      return nextDoc();
    }

    // Jump to target  f target  s b gger.
     f (target >= curDoc && target >= nextPost ngDoc D) {
      jumpToTarget(target);
    }

    // Retr eve next doc.
    return nextDoc();
  }

  /**
   * Set t  next post ng po nter (and  nfo) to t  f rst post ng
   * w h doc  D equal to or larger than t  target.
   *
   * Not ce t   thod does not set curDoc or curFreq.
   */
  pr vate vo d jumpToTarget( nt target) {
    // Do a ce l search.
    nextPost ngPo nter = sk pl st.searchCe l(
        key.w hDocAndPos  on(target,  NVAL D_POS T ON), post ngPo nter, advanceSearchF nger);

    // Read next post ng  nformat on.
    readNextPost ng nfo();
  }

  @Overr de
  publ c  nt nextPos  on() {
    //  f doc  D  s equal to no more docs than   are past t  end of t  post ng l st.  f doc  D
    //  s  nval d, t n   have not called nextDoc yet, and   should not return a real pos  on.
    //  f t  pos  on po nter  s past t  current doc  D, t n   should not return a pos  on
    // unt l nextDoc  s called aga n (  don't want to return pos  ons for a d fferent doc).
     f (doc D() == NO_MORE_DOCS
        || doc D() == TermsArray. NVAL D
        || sk pl st.getValue(pos  onPo nter) != doc D()) {
      return  NVAL D_POS T ON;
    }
    payloadPo nter = pos  onPo nter;
     nt pos  on = sk pl st.getPos  on(pos  onPo nter);
    do {
      pos  onPo nter = sk pl st.getNextPo nter(pos  onPo nter);
    } wh le (! sPo nterPubl s d(pos  onPo nter) && pos  onPo nter != post ngPo nter);
    return pos  on;
  }

  @Overr de
  publ c BytesRef getPayload() {
     f (sk pl st.getHasPayloads() == Sk pL stConta ner.HasPayloads.NO) {
      return null;
    }

     nt po nter = sk pl st.getPayloadPo nter(t .payloadPo nter);
    Precond  ons.c ckState(po nter > 0);
    return PayloadUt l.decodePayload(sk pl st.getBlockPool(), po nter);
  }

  @Overr de
  publ c  nt startOffset() {
    return -1;
  }

  @Overr de
  publ c  nt endOffset() {
    return -1;
  }

  @Overr de
  publ c f nal  nt doc D() {
    return curDoc;
  }

  @Overr de
  publ c f nal  nt freq() {
    return curFreq;
  }

  @Overr de
  publ c long cost() {
    return cost;
  }
}
