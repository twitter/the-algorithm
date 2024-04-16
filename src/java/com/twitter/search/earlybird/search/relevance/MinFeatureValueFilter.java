package com.tw ter.search.earlyb rd.search.relevance;

 mport java. o. OExcept on;
 mport java.ut l.Objects;

 mport com.google.common.annotat ons.V s bleForTest ng;

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

 mport com.tw ter.search.common.encod ng.features.ByteNormal zer;
 mport com.tw ter.search.common.encod ng.features.ClampByteNormal zer;
 mport com.tw ter.search.common.encod ng.features.S ngleBytePos  veFloatNormal zer;
 mport com.tw ter.search.common.query.DefaultF lter  ght;
 mport com.tw ter.search.common.query.F lteredQuery;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.RangeF lterD S ;

publ c f nal class M nFeatureValueF lter extends Query  mple nts F lteredQuery.Doc dF lterFactory {
  pr vate f nal Str ng featureNa ;
  pr vate f nal ByteNormal zer normal zer;
  pr vate f nal double m nValue;

  /**
   * Creates a query that f lters out all h s that have a value smaller than t  g ven threshold
   * for t  g ven feature.
   *
   * @param featureNa  T  feature.
   * @param m nValue T  threshold for t  feature values.
   * @return A query that f lters out all h s that have a value smaller than t  g ven threshold
   *         for t  g ven feature.
   */
  publ c stat c Query getM nFeatureValueF lter(Str ng featureNa , double m nValue) {
    return new BooleanQuery.Bu lder()
        .add(new M nFeatureValueF lter(featureNa , m nValue), BooleanClause.Occur.F LTER)
        .bu ld();
  }

  publ c stat c F lteredQuery.Doc dF lterFactory getDoc dF lterFactory(
      Str ng featureNa , double m nValue) {
    return new M nFeatureValueF lter(featureNa , m nValue);
  }

  /**
   * Returns t  normal zer that should be used to normal ze t  values for t  g ven feature.
   *
   * @param featureNa  T  feature.
   * @return T  normal zer that should be used to normal ze t  values for t  g ven feature.
   */
  @V s bleForTest ng
  publ c stat c ByteNormal zer getM nFeatureValueNormal zer(Str ng featureNa ) {
     f (featureNa .equals(Earlyb rdF eldConstant.USER_REPUTAT ON.getF eldNa ())) {
      return new ClampByteNormal zer(0, 100);
    }

     f (featureNa .equals(Earlyb rdF eldConstant.FAVOR TE_COUNT.getF eldNa ())
        || featureNa .equals(Earlyb rdF eldConstant.PARUS_SCORE.getF eldNa ())
        || featureNa .equals(Earlyb rdF eldConstant.REPLY_COUNT.getF eldNa ())
        || featureNa .equals(Earlyb rdF eldConstant.RETWEET_COUNT.getF eldNa ())) {
      return new S ngleBytePos  veFloatNormal zer();
    }

    throw new  llegalArgu ntExcept on("Unknown normal zat on  thod for f eld " + featureNa );
  }

  @Overr de
  publ c  nt hashCode() {
    // Probably doesn't make sense to  nclude t  sc maSnapshot and normal zer  re.
    return ( nt) ((featureNa  == null ? 0 : featureNa .hashCode() * 7) + m nValue);
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof M nFeatureValueF lter)) {
      return false;
    }

    // Probably doesn't make sense to  nclude t  sc maSnapshot and normal zer  re.
    M nFeatureValueF lter f lter = M nFeatureValueF lter.class.cast(obj);
    return Objects.equals(featureNa , f lter.featureNa ) && (m nValue == f lter.m nValue);
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return Str ng.format("M nFeatureValueF lter(%s, %f)", featureNa , m nValue);
  }

  pr vate M nFeatureValueF lter(Str ng featureNa , double m nValue) {
    t .featureNa  = featureNa ;
    t .normal zer = getM nFeatureValueNormal zer(featureNa );
    t .m nValue = normal zer.normal ze(m nValue);
  }

  @Overr de
  publ c F lteredQuery.Doc dF lter getDoc dF lter(LeafReaderContext context) throws  OExcept on {
    f nal Nu r cDocValues featureDocValues = context.reader().getNu r cDocValues(featureNa );
    return (doc d) -> featureDocValues.advanceExact(doc d)
        && ((byte) featureDocValues.longValue() >= m nValue);
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
    return new DefaultF lter  ght(t ) {
      @Overr de
      protected Doc dSet erator getDoc dSet erator(LeafReaderContext context) throws  OExcept on {
        return new M nFeatureValueDoc dSet erator(
            context.reader(), featureNa , m nValue);
      }
    };
  }

  pr vate stat c f nal class M nFeatureValueDoc dSet erator extends RangeF lterD S  {
    pr vate f nal Nu r cDocValues featureDocValues;
    pr vate f nal double m nValue;

    M nFeatureValueDoc dSet erator(LeafReader  ndexReader,
                                    Str ng featureNa ,
                                    double m nValue) throws  OExcept on {
      super( ndexReader);
      t .featureDocValues =  ndexReader.getNu r cDocValues(featureNa );
      t .m nValue = m nValue;
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
      return featureDocValues.advanceExact(doc D())
          && ((byte) featureDocValues.longValue() >= m nValue);
    }
  }

  publ c double getM nValue() {
    return m nValue;
  }

  publ c ByteNormal zer getNormal zer() {
    return normal zer;
  }
}
