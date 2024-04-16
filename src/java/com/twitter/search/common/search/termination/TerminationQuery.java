package com.tw ter.search.common.search.term nat on;

 mport java. o. OExcept on;
 mport java.ut l.Arrays;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex. ndexReader;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

/**
 * Query  mple ntat on that can t  out and return non-exhaust ve results.
 */
publ c class Term nat onQuery extends Query {
  pr vate f nal Query  nner;
  pr vate f nal QueryT  out t  out;

  publ c Term nat onQuery(Query  nner, QueryT  out t  out) {
    t . nner = Precond  ons.c ckNotNull( nner);
    t .t  out = Precond  ons.c ckNotNull(t  out);
  }

  @Overr de
  publ c   ght create  ght(
       ndexSearc r searc r, ScoreMode scoreMode, float boost) throws  OExcept on {
      ght  nner  ght =  nner.create  ght(searc r, scoreMode, boost);
    return new Term nat onQuery  ght(t ,  nner  ght, t  out);
  }

  @Overr de
  publ c Query rewr e( ndexReader reader) throws  OExcept on {
    Query rewr ten =  nner.rewr e(reader);
     f (rewr ten !=  nner) {
      return new Term nat onQuery(rewr ten, t  out);
    }
    return t ;
  }

  publ c QueryT  out getT  out() {
    return t  out;
  }

  @Overr de
  publ c  nt hashCode() {
    return Arrays.hashCode(new Object[] { nner, t  out});
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof Term nat onQuery)) {
      return false;
    }

    Term nat onQuery term nat onQuery = Term nat onQuery.class.cast(obj);
    return Arrays.equals(new Object[] { nner, t  out},
                         new Object[] {term nat onQuery. nner, term nat onQuery.t  out});
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return  nner.toStr ng(f eld);
  }
}
