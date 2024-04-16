package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;

 mport com.google.common.annotat ons.V s bleForTest ng;

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
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.AllDocs erator;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.RangeF lterD S ;
 mport com.tw ter.search.earlyb rd. ndex.T et DMapper;

/**
 * F lters t et  ds accord ng to s nce_ d and max_ d para ter.
 *
 * Note that s nce_ d  s exclus ve and max_ d  s  nclus ve.
 */
publ c f nal class S nceMax DF lter extends Query {
  publ c stat c f nal long NO_F LTER = -1;

  pr vate f nal long s nce dExclus ve;
  pr vate f nal long max d nclus ve;

  publ c stat c Query getS nceMax DQuery(long s nce dExclus ve, long max d nclus ve) {
    return new BooleanQuery.Bu lder()
        .add(new S nceMax DF lter(s nce dExclus ve, max d nclus ve), BooleanClause.Occur.F LTER)
        .bu ld();
  }

  publ c stat c Query getS nce DQuery(long s nce dExclus ve) {
    return new BooleanQuery.Bu lder()
        .add(new S nceMax DF lter(s nce dExclus ve, NO_F LTER), BooleanClause.Occur.F LTER)
        .bu ld();
  }

  publ c stat c Query getMax DQuery(long max d nclus ve) {
    return new BooleanQuery.Bu lder()
        .add(new S nceMax DF lter(NO_F LTER, max d nclus ve), BooleanClause.Occur.F LTER)
        .bu ld();
  }

  pr vate S nceMax DF lter(long s nce dExclus ve, long max d nclus ve) {
    t .s nce dExclus ve = s nce dExclus ve;
    t .max d nclus ve = max d nclus ve;
  }

  @Overr de
  publ c  nt hashCode() {
    return ( nt) (s nce dExclus ve * 13 + max d nclus ve);
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof S nceMax DF lter)) {
      return false;
    }

    S nceMax DF lter f lter = S nceMax DF lter.class.cast(obj);
    return (s nce dExclus ve == f lter.s nce dExclus ve)
        && (max d nclus ve == f lter.max d nclus ve);
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
     f (s nce dExclus ve != NO_F LTER && max d nclus ve != NO_F LTER) {
      return "S nce dF lter:" + s nce dExclus ve + ",Max dF lter:" + max d nclus ve;
    } else  f (max d nclus ve != NO_F LTER) {
      return "Max dF lter:" + max d nclus ve;
    } else {
      return "S nce dF lter:" + s nce dExclus ve;
    }
  }

  /**
   * Determ nes  f t  seg nt  s at least part ally covered by t  g ven t et  D range.
   */
  publ c stat c boolean s nceMax Ds nRange(
      T et DMapper t et dMapper, long s nce dExclus ve, long max d nclus ve) {
    // C ck for s nce  d out of range. Note that s nce t   D  s exclus ve,
    // equal y  s out of range too.
     f (s nce dExclus ve != NO_F LTER && s nce dExclus ve >= t et dMapper.getMaxT et D()) {
      return false;
    }

    // C ck for max  d  n range.
    return max d nclus ve == NO_F LTER || max d nclus ve >= t et dMapper.getM nT et D();
  }

  // Returns true  f t  seg nt  s completely covered by t se  d f lters.
  pr vate stat c boolean s nceMax dsCoverRange(
      T et DMapper t et dMapper, long s nce dExclus ve, long max d nclus ve) {
    // C ck for s nce_ d spec f ed AND s nce_ d ne r than than f rst t et.
     f (s nce dExclus ve != NO_F LTER && s nce dExclus ve >= t et dMapper.getM nT et D()) {
      return false;
    }

    // C ck for max  d  n range.
    return max d nclus ve == NO_F LTER || max d nclus ve > t et dMapper.getMaxT et D();
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost)
      throws  OExcept on {
    return new DefaultF lter  ght(t ) {
      @Overr de
      protected Doc dSet erator getDoc dSet erator(LeafReaderContext context) throws  OExcept on {
        LeafReader reader = context.reader();
         f (!(reader  nstanceof Earlyb rd ndexSeg ntAtom cReader)) {
          return new AllDocs erator(reader);
        }

        Earlyb rd ndexSeg ntAtom cReader tw ter n mory ndexReader =
            (Earlyb rd ndexSeg ntAtom cReader) reader;
        T et DMapper t et dMapper =
            (T et DMapper) tw ter n mory ndexReader.getSeg ntData().getDoc DToT et DMapper();

        //  mportant to return a null Doc dSet erator  re, so t  Scorer w ll sk p search ng
        // t  seg nt completely.
         f (!s nceMax Ds nRange(t et dMapper, s nce dExclus ve, max d nclus ve)) {
          return null;
        }

        // Opt m zat on: just return a match-all  erator w n t  whole seg nt  s  n range.
        // T  avo ds hav ng to do so many status  d lookups.
         f (s nceMax dsCoverRange(t et dMapper, s nce dExclus ve, max d nclus ve)) {
          return new AllDocs erator(reader);
        }

        return new S nceMax DDoc dSet erator(
            tw ter n mory ndexReader, s nce dExclus ve, max d nclus ve);
      }
    };
  }

  @V s bleForTest ng
  stat c class S nceMax DDoc dSet erator extends RangeF lterD S  {
    pr vate f nal Doc DToT et DMapper doc dToT et dMapper;
    pr vate f nal long s nce dExclus ve;
    pr vate f nal long max d nclus ve;

    publ c S nceMax DDoc dSet erator(Earlyb rd ndexSeg ntAtom cReader reader,
                                      long s nce dExclus ve,
                                      long max d nclus ve) throws  OExcept on {
      super(reader,
            f ndMax dDoc D(reader, max d nclus ve),
            f ndS nce dDoc D(reader, s nce dExclus ve));
      t .doc dToT et dMapper = reader.getSeg ntData().getDoc DToT et DMapper();
      t .s nce dExclus ve = s nce dExclus ve;  // s nceStatus d == NO_F LTER  s OK,  's exclus ve
      t .max d nclus ve = max d nclus ve != NO_F LTER ? max d nclus ve : Long.MAX_VALUE;
    }

    /**
     * T   s a necessary c ck w n   have out of order t ets  n t  arch ve.
     * W n t ets are out of order, t  guarantees that no false pos  ve results are returned.
     *  .e.   can st ll m ss so  t ets  n t  spec f ed range, but   never  ncorrectly return
     * anyth ng that's not  n t  range.
     */
    @Overr de
    protected boolean shouldReturnDoc() {
      f nal long status D = doc dToT et dMapper.getT et D(doc D());
      return status D > s nce dExclus ve && status D <= max d nclus ve;
    }

    pr vate stat c  nt f ndS nce dDoc D(
        Earlyb rd ndexSeg ntAtom cReader reader, long s nce dExclus ve) throws  OExcept on {
      T et DMapper t et dMapper =
          (T et DMapper) reader.getSeg ntData().getDoc DToT et DMapper();
       f (s nce dExclus ve != S nceMax DF lter.NO_F LTER) {
        //   use t  as an upper bound on t  search, so   want to f nd t  h g st poss ble
        // doc  D for t  t et  D.
        boolean f ndMaxDoc D = true;
        return t et dMapper.f ndDoc dBound(
            s nce dExclus ve,
            f ndMaxDoc D,
            reader.getSmallestDoc D(),
            reader.maxDoc() - 1);
      } else {
        return Doc DToT et DMapper. D_NOT_FOUND;
      }
    }

    pr vate stat c  nt f ndMax dDoc D(
        Earlyb rd ndexSeg ntAtom cReader reader, long max d nclus ve) throws  OExcept on {
      T et DMapper t et dMapper =
          (T et DMapper) reader.getSeg ntData().getDoc DToT et DMapper();
       f (max d nclus ve != S nceMax DF lter.NO_F LTER) {
        //   use t  as a lo r bound on t  search, so   want to f nd t  lo st poss ble
        // doc  D for t  t et  D.
        boolean f ndMaxDoc D = false;
        return t et dMapper.f ndDoc dBound(
            max d nclus ve,
            f ndMaxDoc D,
            reader.getSmallestDoc D(),
            reader.maxDoc() - 1);
      } else {
        return Doc DToT et DMapper. D_NOT_FOUND;
      }
    }
  }
}
