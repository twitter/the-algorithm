package com.tw ter.search.common.query;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.  ght;

/**
 * Scorer  mple ntat on that adds attr bute collect on support for an underly ng query.
 *  ant to be used  n conjunct on w h {@l nk  dent f ableQuery}.
 */
publ c class  dent f ableQueryScorer extends F lteredScorer {
  pr vate f nal F eldRankH  nfo query d;
  pr vate f nal H Attr buteCollector attrCollector;

  publ c  dent f ableQueryScorer(  ght   ght, Scorer  nner, F eldRankH  nfo query d,
                                 H Attr buteCollector attrCollector) {
    super(  ght,  nner);
    t .query d = query d;
    t .attrCollector = Precond  ons.c ckNotNull(attrCollector);
  }

  @Overr de
  publ c Doc dSet erator  erator() {
    f nal Doc dSet erator superD S  = super. erator();

    return new Doc dSet erator() {
      @Overr de
      publ c  nt doc D() {
        return superD S .doc D();
      }

      @Overr de
      publ c  nt nextDoc() throws  OExcept on {
         nt doc d = superD S .nextDoc();
         f (doc d != NO_MORE_DOCS) {
          attrCollector.collectScorerAttr but on(doc d, query d);
        }
        return doc d;
      }

      @Overr de
      publ c  nt advance( nt target) throws  OExcept on {
         nt doc d = superD S .advance(target);
         f (doc d != NO_MORE_DOCS) {
          attrCollector.collectScorerAttr but on(doc d, query d);
        }
        return doc d;
      }

      @Overr de
      publ c long cost() {
        return superD S .cost();
      }
    };
  }
}
