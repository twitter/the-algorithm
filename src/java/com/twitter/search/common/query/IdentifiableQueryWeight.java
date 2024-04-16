package com.tw ter.search.common.query;

 mport java. o. OExcept on;
 mport java.ut l.Set;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.Explanat on;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.  ght;

/**
 *   ght  mple ntat on that adds attr bute collect on support for an underly ng query.
 *  ant to be used  n conjunct on w h {@l nk  dent f ableQuery}.
 */
publ c class  dent f ableQuery  ght extends   ght {
  pr vate f nal   ght  nner;
  pr vate f nal F eldRankH  nfo query d;
  pr vate f nal H Attr buteCollector attrCollector;

  /** Creates a new  dent f ableQuery  ght  nstance. */
  publ c  dent f ableQuery  ght( dent f ableQuery query,   ght  nner, F eldRankH  nfo query d,
                                 H Attr buteCollector attrCollector) {
    super(query);
    t . nner =  nner;
    t .query d = query d;
    t .attrCollector = Precond  ons.c ckNotNull(attrCollector);
  }

  @Overr de
  publ c Explanat on expla n(LeafReaderContext context,  nt doc)
      throws  OExcept on {
    return  nner.expla n(context, doc);
  }

  @Overr de
  publ c Scorer scorer(LeafReaderContext context) throws  OExcept on {
    attrCollector.clearH Attr but ons(context, query d);
    Scorer  nnerScorer =  nner.scorer(context);
     f ( nnerScorer != null) {
      return new  dent f ableQueryScorer(t ,  nnerScorer, query d, attrCollector);
    } else {
      return null;
    }
  }

  @Overr de
  publ c vo d extractTerms(Set<Term> terms) {
     nner.extractTerms(terms);
  }

  @Overr de
  publ c boolean  sCac able(LeafReaderContext ctx) {
    return  nner. sCac able(ctx);
  }
}
