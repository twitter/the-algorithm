package com.tw ter.search.common.search.term nat on;

 mport java. o. OExcept on;
 mport java.ut l.Set;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.Explanat on;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.  ght;

/**
 *   ght  mple ntat on that adds term nat on support for an underly ng query.
 *  ant to be used  n conjunct on w h {@l nk Term nat onQuery}.
 */
publ c class Term nat onQuery  ght extends   ght {
  pr vate f nal   ght  nner;
  pr vate f nal QueryT  out t  out;

  Term nat onQuery  ght(Term nat onQuery query,   ght  nner, QueryT  out t  out) {
    super(query);
    t . nner =  nner;
    t .t  out = Precond  ons.c ckNotNull(t  out);
  }

  @Overr de
  publ c Explanat on expla n(LeafReaderContext context,  nt doc)
      throws  OExcept on {
    return  nner.expla n(context, doc);
  }

  @Overr de
  publ c Scorer scorer(LeafReaderContext context) throws  OExcept on {
    Scorer  nnerScorer =  nner.scorer(context);
     f ( nnerScorer != null) {
      return new Term nat onQueryScorer(t ,  nnerScorer, t  out);
    }

    return null;
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
