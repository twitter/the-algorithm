package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;
 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;

 mport stat c com.tw ter.search.core.earlyb rd. ndex. nverted.Sk pL stConta ner.HasPayloads;
 mport stat c com.tw ter.search.core.earlyb rd. ndex. nverted.Sk pL stConta ner.HasPos  ons;
 mport stat c com.tw ter.search.core.earlyb rd. ndex. nverted.Sk pL stConta ner. NVAL D_POS T ON;
 mport stat c com.tw ter.search.core.earlyb rd. ndex. nverted.TermsArray. NVAL D;

/**
 * A sk p l st  mple ntat on of real t   post ng l st. Supports out of order updates.
 */
publ c class Sk pL stPost ngL st  mple nts Flushable {
  /** Underly ng sk p l st. */
  pr vate f nal Sk pL stConta ner<Key> sk pL stConta ner;

  /** Key used w n  nsert ng  nto t  sk p l st. */
  pr vate f nal Key key = new Key();

  publ c Sk pL stPost ngL st(
      HasPos  ons hasPos  ons,
      HasPayloads hasPayloads,
      Str ng f eld) {
    t .sk pL stConta ner = new Sk pL stConta ner<>(
        new Doc DComparator(),
        hasPos  ons,
        hasPayloads,
        f eld);
  }

  /** Used by {@l nk Sk pL stPost ngL st.FlushHandler} */
  pr vate Sk pL stPost ngL st(Sk pL stConta ner<Key> sk pL stConta ner) {
    t .sk pL stConta ner = sk pL stConta ner;
  }

  /**
   * Appends a post ng to t  post ng l st for a term.
   */
  publ c vo d appendPost ng(
       nt term D,
      TermsArray termsArray,
       nt doc D,
       nt pos  on,
      @Nullable BytesRef payload) {
    termsArray.getLargestPost ngs()[term D] = Math.max(
        termsArray.getLargestPost ngs()[term D],
        doc D);

    // Append to an ex st ng sk p l st.
    // Not ce,  ader to r  ndex  s stored at t  last post ngs po nter spot.
     nt post ngsPo nter = termsArray.getPost ngsPo nter(term D);
     f (post ngsPo nter ==  NVAL D) {
      // Create a new sk p l st and add t  f rst post ng.
      post ngsPo nter = sk pL stConta ner.newSk pL st();
    }

    boolean havePost ngForT Doc =  nsertPost ng(doc D, pos  on, payload, post ngsPo nter);

    //  f t   s a new docu nt  D,   need to update t  docu nt frequency for t  term
     f (!havePost ngForT Doc) {
      termsArray.getDocu ntFrequency()[term D]++;
    }

    termsArray.updatePost ngsPo nter(term D, post ngsPo nter);
  }

  /**
   * Deletes t  g ven doc  D from t  post ng l st for t  term.
   */
  publ c vo d deletePost ng( nt term D, TermsArray post ngsArray,  nt doc D) {
     nt docFreq = post ngsArray.getDocu ntFrequency()[term D];
     f (docFreq == 0) {
      return;
    }

     nt post ngsPo nter = post ngsArray.getPost ngsPo nter(term D);
    // sk pL stConta ner  s not empty, try to delete doc d from  .
     nt smallestDoc = deletePost ng(doc D, post ngsPo nter);
     f (smallestDoc == Sk pL stConta ner. N T AL_VALUE) {
      // Key does not ex st.
      return;
    }

    post ngsArray.getDocu ntFrequency()[term D]--;
  }

  /**
   *  nsert post ng  nto an ex st ng sk p l st.
   *
   * @param doc D doc D of t  t  post ng.
   * @param sk pL st ad  ader to r  ndex of t  sk p l st
   *                          n wh ch t  post ng w ll be  nserted.
   * @return w t r   have already  nserted t  docu nt  D  nto t  term l st.
   */
  pr vate boolean  nsertPost ng( nt doc D,  nt pos  on, BytesRef termPayload,  nt sk pL st ad) {
     nt[] payload = PayloadUt l.encodePayload(termPayload);
    return sk pL stConta ner. nsert(key.w hDocAndPos  on(doc D, pos  on), doc D, pos  on,
        payload, sk pL st ad);
  }

  pr vate  nt deletePost ng( nt doc D,  nt sk pL st ad) {
    return sk pL stConta ner.delete(key.w hDocAndPos  on(doc D,  NVAL D_POS T ON), sk pL st ad);
  }

  /** Return a term docs enu rator w h pos  on flag on. */
  publ c Post ngsEnum post ngs(
       nt post ngPo nter,
       nt docFreq,
       nt maxPubl s dPo nter) {
    return new Sk pL stPost ngsEnum(
        post ngPo nter, docFreq, maxPubl s dPo nter, sk pL stConta ner);
  }

  /**
   * Get t  number of docu nts (AKA docu nt frequency or DF) for t  g ven term.
   */
  publ c  nt getDF( nt term D, TermsArray post ngsArray) {
     nt[] docu ntFrequency = post ngsArray.getDocu ntFrequency();
    Precond  ons.c ckArgu nt(term D < docu ntFrequency.length);

    return docu ntFrequency[term D];
  }

  publ c  nt getDoc DFromPost ng( nt post ng) {
    // Post ng  s s mply t  whole doc  D.
    return post ng;
  }

  publ c  nt getMaxPubl s dPo nter() {
    return sk pL stConta ner.getPoolS ze();
  }


  @SuppressWarn ngs("unc cked")
  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c class FlushHandler extends Flushable.Handler<Sk pL stPost ngL st> {
    pr vate stat c f nal Str ng SK P_L ST_PROP_NAME = "sk pL st";

    publ c FlushHandler(Sk pL stPost ngL st objectToFlush) {
      super(objectToFlush);
    }

    publ c FlushHandler() {
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      Sk pL stPost ngL st objectToFlush = getObjectToFlush();

      objectToFlush.sk pL stConta ner.getFlushHandler()
          .flush(flush nfo.newSubPropert es(SK P_L ST_PROP_NAME), out);
    }

    @Overr de
    protected Sk pL stPost ngL st doLoad(
        Flush nfo flush nfo, DataDeser al zer  n) throws  OExcept on {
      Sk pL stComparator<Key> comparator = new Doc DComparator();
      Sk pL stConta ner.FlushHandler<Key> flushHandler =
          new Sk pL stConta ner.FlushHandler<>(comparator);
      Sk pL stConta ner<Key> sk pL st =
          flushHandler.load(flush nfo.getSubPropert es(SK P_L ST_PROP_NAME),  n);
      return new Sk pL stPost ngL st(sk pL st);
    }
  }

  /**
   * Key used to  n {@l nk Sk pL stConta ner} by {@l nk Sk pL stPost ngL st}.
   */
  publ c stat c class Key {
    pr vate  nt doc D;
    pr vate  nt pos  on;

    publ c  nt getDoc D() {
      return doc D;
    }

    publ c  nt getPos  on() {
      return pos  on;
    }

    publ c Key w hDocAndPos  on( nt w hDoc D,  nt w hPos  on) {
      t .doc D = w hDoc D;
      t .pos  on = w hPos  on;
      return t ;
    }
  }

  /**
   * Comparator for doc D and pos  on.
   */
  publ c stat c class Doc DComparator  mple nts Sk pL stComparator<Key> {
    pr vate stat c f nal  nt SENT NEL_VALUE = Doc dSet erator.NO_MORE_DOCS;

    @Overr de
    publ c  nt compareKeyW hValue(Key key,  nt targetDoc D,  nt targetPos  on) {
      // No key could represent sent nel value and sent nel value  s t  largest.
       nt docCompare = key.getDoc D() - targetDoc D;
       f (docCompare == 0 && targetPos  on !=  NVAL D_POS T ON) {
        return key.getPos  on() - targetPos  on;
      } else {
        return docCompare;
      }
    }

    @Overr de
    publ c  nt compareValues( nt doc D1,  nt doc D2) {
      // Sent nel value  s t  largest.
      return doc D1 - doc D2;
    }

    @Overr de
    publ c  nt getSent nelValue() {
      return SENT NEL_VALUE;
    }
  }
}
