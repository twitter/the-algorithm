package com.tw ter.search.common.query;

 mport java. o. OExcept on;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex. ndexReader;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

/**
 * Query  mple ntat on adds attr bute collect on support for an underly ng query.
 */
publ c class  dent f ableQuery extends Query {
  protected f nal Query  nner;
  pr vate f nal F eldRankH  nfo query d;
  pr vate f nal H Attr buteCollector attrCollector;

  publ c  dent f ableQuery(Query  nner, F eldRankH  nfo query d,
                           H Attr buteCollector attrCollector) {
    t . nner = Precond  ons.c ckNotNull( nner);
    t .query d = query d;
    t .attrCollector = Precond  ons.c ckNotNull(attrCollector);
  }

  @Overr de
  publ c   ght create  ght(
       ndexSearc r searc r, ScoreMode scoreMode, float boost) throws  OExcept on {
      ght  nner  ght =  nner.create  ght(searc r, scoreMode, boost);
    return new  dent f ableQuery  ght(t ,  nner  ght, query d, attrCollector);
  }

  @Overr de
  publ c Query rewr e( ndexReader reader) throws  OExcept on {
    Query rewr ten =  nner.rewr e(reader);
     f (rewr ten !=  nner) {
      return new  dent f ableQuery(rewr ten, query d, attrCollector);
    }
    return t ;
  }

  @Overr de
  publ c  nt hashCode() {
    return  nner.hashCode() * 13 + (query d == null ? 0 : query d.hashCode());
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof  dent f ableQuery)) {
      return false;
    }

     dent f ableQuery  dent f ableQuery =  dent f ableQuery.class.cast(obj);
    return  nner.equals( dent f ableQuery. nner)
        && (query d == null
            ?  dent f ableQuery.query d == null
            : query d.equals( dent f ableQuery.query d));
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return  nner.toStr ng(f eld);
  }

  @V s bleForTest ng
  publ c Query getQueryForTest() {
    return  nner;
  }

  @V s bleForTest ng
  publ c F eldRankH  nfo getQuery dForTest() {
    return query d;
  }
}
