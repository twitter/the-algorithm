package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene.search.BooleanClause;
 mport org.apac .lucene.search.BooleanQuery;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

 mport com.tw ter.search.common.query.DefaultF lter  ght;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.T  Mapper;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.AllDocs erator;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.RangeF lterD S ;

// F lters t ets accord ng to s nce t   and unt l t   ( n seconds).
// Note that s nce t    s  nclus ve, and unt l t    s exclus ve.
publ c f nal class S nceUnt lF lter extends Query {
  publ c stat c f nal  nt NO_F LTER = -1;

  // T se are both  n seconds s nce t  epoch.
  pr vate f nal  nt m nT   nclus ve;
  pr vate f nal  nt maxT  Exclus ve;

  publ c stat c Query getS nceQuery( nt s nceT  Seconds) {
    return new BooleanQuery.Bu lder()
        .add(new S nceUnt lF lter(s nceT  Seconds, NO_F LTER), BooleanClause.Occur.F LTER)
        .bu ld();
  }

  publ c stat c Query getUnt lQuery( nt unt lT  Seconds) {
    return new BooleanQuery.Bu lder()
        .add(new S nceUnt lF lter(NO_F LTER, unt lT  Seconds), BooleanClause.Occur.F LTER)
        .bu ld();
  }

  publ c stat c Query getS nceUnt lQuery( nt s nceT  Seconds,  nt unt lT  Seconds) {
    return new BooleanQuery.Bu lder()
        .add(new S nceUnt lF lter(s nceT  Seconds, unt lT  Seconds), BooleanClause.Occur.F LTER)
        .bu ld();
  }

  pr vate S nceUnt lF lter( nt s nceT  ,  nt unt lT  ) {
    t .m nT   nclus ve = s nceT   != NO_F LTER ? s nceT   : 0;
    t .maxT  Exclus ve = unt lT   != NO_F LTER ? unt lT   :  nteger.MAX_VALUE;
  }

  @Overr de
  publ c  nt hashCode() {
    return ( nt) (m nT   nclus ve * 17 + maxT  Exclus ve);
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof S nceUnt lF lter)) {
      return false;
    }

    S nceUnt lF lter f lter = S nceUnt lF lter.class.cast(obj);
    return (m nT   nclus ve == f lter.m nT   nclus ve)
        && (maxT  Exclus ve == f lter.maxT  Exclus ve);
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
     f (m nT   nclus ve > 0 && maxT  Exclus ve !=  nteger.MAX_VALUE) {
      return "S nceF lter:" + t .m nT   nclus ve + ",Unt lF lter:" + maxT  Exclus ve;
    } else  f (m nT   nclus ve > 0) {
      return "S nceF lter:" + t .m nT   nclus ve;
    } else {
      return "Unt lF lter:" + t .maxT  Exclus ve;
    }
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost)
      throws  OExcept on {
    return new DefaultF lter  ght(t ) {
      @Overr de
      protected Doc dSet erator getDoc dSet erator(LeafReaderContext context) throws  OExcept on {
        LeafReader  ndexReader = context.reader();
         f (!( ndexReader  nstanceof Earlyb rd ndexSeg ntAtom cReader)) {
          return new AllDocs erator( ndexReader);
        }

        Earlyb rd ndexSeg ntAtom cReader reader = (Earlyb rd ndexSeg ntAtom cReader)  ndexReader;
        T  Mapper t  Mapper = reader.getSeg ntData().getT  Mapper();
         nt smallestDoc D = t  Mapper.f ndF rstDoc d(maxT  Exclus ve, reader.getSmallestDoc D());
         nt largestDoc = t  Mapper.f ndF rstDoc d(m nT   nclus ve, reader.getSmallestDoc D());
         nt smallestDoc = smallestDoc D > 0 ? smallestDoc D - 1 : 0;
        return new S nceUnt lDoc dSet erator(
            reader,
            t  Mapper,
            smallestDoc,
            largestDoc,
            m nT   nclus ve,
            maxT  Exclus ve);
      }
    };
  }

  // Returns true  f t  T  Mapper  s at least part ally covered by t se t   f lters.
  publ c stat c boolean s nceUnt lT  s nRange(
      T  Mapper t  Mapper,  nt s nceT  ,  nt unt lT  ) {
    return (s nceT   == NO_F LTER || s nceT   <= t  Mapper.getLastT  ())
        && (unt lT   == NO_F LTER || unt lT   >= t  Mapper.getF rstT  ());
  }

  pr vate stat c f nal class S nceUnt lDoc dSet erator extends RangeF lterD S  {
    pr vate f nal T  Mapper t  Mapper;
    pr vate f nal  nt m nT   nclus ve;
    pr vate f nal  nt maxT  Exclus ve;

    publ c S nceUnt lDoc dSet erator(Earlyb rd ndexSeg ntAtom cReader reader,
                                      T  Mapper t  Mapper,
                                       nt smallestDoc D,
                                       nt largestDoc D,
                                       nt m nT   nclus ve,
                                       nt maxExclus ve) throws  OExcept on {
      super(reader, smallestDoc D, largestDoc D);
      t .t  Mapper = t  Mapper;
      t .m nT   nclus ve = m nT   nclus ve;
      t .maxT  Exclus ve = maxExclus ve;
    }

    @Overr de
    protected boolean shouldReturnDoc() {
      f nal  nt docT   = t  Mapper.getT  (doc D());
      return docT   >= m nT   nclus ve && docT   < maxT  Exclus ve;
    }
  }
}
