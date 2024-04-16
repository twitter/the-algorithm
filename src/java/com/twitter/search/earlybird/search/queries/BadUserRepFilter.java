package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Nu r cDocValues;
 mport org.apac .lucene.search.BooleanClause;
 mport org.apac .lucene.search.BooleanQuery;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

 mport com.tw ter.search.common.query.DefaultF lter  ght;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.AllDocs erator;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.RangeF lterD S ;

publ c f nal class BadUserRepF lter extends Query {
  /**
   * Creates a query that f lters out results com ng from users w h bad reputat on.
   *
   * @param m nT epCred T  lo st acceptable user reputat on.
   * @return A query that f lters out results from bad reputat on users.
   */
  publ c stat c Query getBadUserRepF lter( nt m nT epCred) {
     f (m nT epCred <= 0) {
      return null;
    }

    return new BooleanQuery.Bu lder()
        .add(new BadUserRepF lter(m nT epCred), BooleanClause.Occur.F LTER)
        .bu ld();
  }

  pr vate f nal  nt m nT epCred;

  pr vate BadUserRepF lter( nt m nT epCred) {
    t .m nT epCred = m nT epCred;
  }

  @Overr de
  publ c  nt hashCode() {
    return m nT epCred;
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof BadUserRepF lter)) {
      return false;
    }

    return m nT epCred == BadUserRepF lter.class.cast(obj).m nT epCred;
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return "BadUserRepF lter:" + m nT epCred;
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
    return new DefaultF lter  ght(t ) {
      @Overr de
      protected Doc dSet erator getDoc dSet erator(LeafReaderContext context) throws  OExcept on {
        LeafReader reader = context.reader();
         f (!(reader  nstanceof Earlyb rd ndexSeg ntAtom cReader)) {
          return new AllDocs erator(reader);
        }

        return new BadUserExcludeDoc dSet erator(
            (Earlyb rd ndexSeg ntAtom cReader) context.reader(), m nT epCred);
      }
    };
  }

  pr vate stat c f nal class BadUserExcludeDoc dSet erator extends RangeF lterD S  {
    pr vate f nal Nu r cDocValues userReputat onDocValues;
    pr vate f nal  nt m nT epCred;

    BadUserExcludeDoc dSet erator(Earlyb rd ndexSeg ntAtom cReader  ndexReader,
                                    nt m nT epCred) throws  OExcept on {
      super( ndexReader);
      t .userReputat onDocValues =
           ndexReader.getNu r cDocValues(Earlyb rdF eldConstant.USER_REPUTAT ON.getF eldNa ());
      t .m nT epCred = m nT epCred;
    }

    @Overr de
    publ c boolean shouldReturnDoc() throws  OExcept on {
      //   need t  expl c  cast ng to byte, because of how   encode and decode features  n  
      // encoded_t et_features f eld.  f a feature  s an  nt (uses all 32 b s of t   nt), t n
      // encod ng t  feature and t n decod ng   preserves  s or g nal value. Ho ver,  f t 
      // feature does not use t  ent re  nt (and espec ally  f   uses b s so w re  n t  m ddle
      // of t   nt), t n t  feature value  s assu d to be uns gned w n   goes through t 
      // process of encod ng and decod ng. So a user rep of
      // RelevanceS gnalConstants.UNSET_REPUTAT ON_SENT NEL (-128) w ll be correctly encoded as t 
      // b nary value 10000000, but w ll be treated as an uns gned value w n decoded, and t refore
      // t  decoded value w ll be 128.
      //
      //  n retrospect, t  seems l ke a really poor des gn dec s on.   seems l ke   would be
      // better  f all feature values  re cons dered to be s gned, even  f most features can never
      // have negat ve values. Unfortunately, mak ng t  change  s not easy, because so  features
      // store normal zed values, so   would also need to change t  range of allo d values
      // produced by those normal zers, as  ll as all code that depends on those values.
      //
      // So for now, just cast t  value to a byte, to get t  proper negat ve value.
      return userReputat onDocValues.advanceExact(doc D())
          && ((byte) userReputat onDocValues.longValue() >= m nT epCred);
    }
  }
}
