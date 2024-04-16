package com.tw ter.search.common.search.term nat on;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.  ght;

 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.query.F lteredScorer;
 mport com.tw ter.search.common.search.Doc dTracker;

/**
 * Scorer  mple ntat on that adds term nat on support for an underly ng query.
 *  ant to be used  n conjunct on w h {@l nk Term nat onQuery}.
 */
publ c class Term nat onQueryScorer extends F lteredScorer  mple nts Doc dTracker {
  pr vate f nal QueryT  out t  out;
  pr vate  nt lastSearc dDoc d = -1;

  Term nat onQueryScorer(  ght   ght, Scorer  nner, QueryT  out t  out) {
    super(  ght,  nner);
    t .t  out = Precond  ons.c ckNotNull(t  out);
    t .t  out.reg sterDoc dTracker(t );
    SearchRateCounter.export(
        t  out.getCl ent d() + "_num_term nat on_query_scorers_created"). ncre nt();
  }

  @Overr de
  publ c Doc dSet erator  erator() {
    f nal Doc dSet erator superD S  = super. erator();
    return new Doc dSet erator() {
      // lastSearc dDoc d  s t   D of t  last docu nt that was traversed  n t  post ng l st.
      // doc d  s t  current doc  D  n t   erator.  n most cases, lastSearc dDoc d and doc d
      // w ll be equal. T y w ll be d fferent only  f t  query needed to be term nated based on
      // t  t  out.  n that case, doc d w ll be set to NO_MORE_DOCS, but lastSearc dDoc d w ll
      // st ll be set to t  last docu nt that was actually traversed.
      pr vate  nt doc d = -1;

      @Overr de
      publ c  nt doc D() {
        return doc d;
      }

      @Overr de
      publ c  nt nextDoc() throws  OExcept on {
         f (doc d == NO_MORE_DOCS) {
          return NO_MORE_DOCS;
        }

         f (t  out.shouldEx ()) {
          doc d = NO_MORE_DOCS;
        } else {
          doc d = superD S .nextDoc();
          lastSearc dDoc d = doc d;
        }
        return doc d;
      }

      @Overr de
      publ c  nt advance( nt target) throws  OExcept on {
         f (doc d == NO_MORE_DOCS) {
          return NO_MORE_DOCS;
        }

         f (target == NO_MORE_DOCS) {
          doc d = NO_MORE_DOCS;
          lastSearc dDoc d = doc d;
        } else  f (t  out.shouldEx ()) {
          doc d = NO_MORE_DOCS;
        } else {
          doc d = superD S .advance(target);
          lastSearc dDoc d = doc d;
        }
        return doc d;
      }

      @Overr de
      publ c long cost() {
        return superD S .cost();
      }
    };
  }

  @Overr de
  publ c  nt getCurrentDoc d() {
    return lastSearc dDoc d;
  }
}
