package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;
 mport java.ut l.Set;

 mport com.google.common.base.Precond  ons;

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
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.RangeF lterD S ;

publ c f nal class FeatureValue nAcceptL stOrUnsetF lter extends Query {

  pr vate f nal Str ng featureNa ;
  pr vate f nal Set<Long>  dsAcceptL st;

  /**
   * Creates a query that f lters for h s that have t  g ven feature unset, or that have t 
   * g ven feature set to a value  n t  g ven l st of  Ds.
   *
   * @param featureNa  T  feature.
   * @param  ds A l st of  d values t  f lter w ll accept for t  g ven feature.
   * @return A query that f lters out all h s that have t  g ven feature set.
   */
  publ c stat c Query getFeatureValue nAcceptL stOrUnsetF lter(Str ng featureNa , Set<Long>  ds) {
    return new BooleanQuery.Bu lder()
        .add(new FeatureValue nAcceptL stOrUnsetF lter(featureNa ,  ds),
            BooleanClause.Occur.F LTER)
        .bu ld();
  }

  @Overr de
  publ c Str ng toStr ng(Str ng s) {
    return Str ng.format("FeatureValue nAcceptL stOrUnsetF lter(%s, AcceptL st = (%s))",
        featureNa ,
         dsAcceptL st);
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof FeatureValue nAcceptL stOrUnsetF lter)) {
      return false;
    }

    FeatureValue nAcceptL stOrUnsetF lter f lter =
        FeatureValue nAcceptL stOrUnsetF lter.class.cast(obj);
    return featureNa .equals(f lter.featureNa ) &&  dsAcceptL st.equals(f lter. dsAcceptL st);
  }

  @Overr de
  publ c  nt hashCode() {
    return featureNa .hashCode() * 7 +  dsAcceptL st.hashCode();
  }

  pr vate FeatureValue nAcceptL stOrUnsetF lter(Str ng featureNa , Set<Long>  ds) {
    t .featureNa  = Precond  ons.c ckNotNull(featureNa );
    t . dsAcceptL st = Precond  ons.c ckNotNull( ds);
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
    return new DefaultF lter  ght(t ) {
      @Overr de
      protected Doc dSet erator getDoc dSet erator(LeafReaderContext context) throws  OExcept on {
        return new FeatureValue nAcceptL stOrUnsetDoc dSet erator(
            context.reader(), featureNa ,  dsAcceptL st);
      }
    };
  }

  pr vate stat c f nal class FeatureValue nAcceptL stOrUnsetDoc dSet erator
      extends RangeF lterD S  {
    pr vate f nal Nu r cDocValues featureDocValues;
    pr vate f nal Set<Long>  dsAcceptL st;

    FeatureValue nAcceptL stOrUnsetDoc dSet erator(
        LeafReader  ndexReader, Str ng featureNa , Set<Long>  ds) throws  OExcept on {
      super( ndexReader);
      t .featureDocValues =  ndexReader.getNu r cDocValues(featureNa );
      t . dsAcceptL st =  ds;
    }

    @Overr de
    publ c boolean shouldReturnDoc() throws  OExcept on {
      //  f featureDocValues  s null, that  ans t re  re no docu nts  ndexed w h t  g ven
      // f eld  n t  current seg nt.
      //
      // T  advanceExact()  thod returns false  f   cannot f nd t  g ven doc d  n t 
      // Nu r cDocValues  nstance. So  f advanceExact() returns false t n   know t  feature  s
      // unset.
      // Ho ver, for realt   Earlyb rds   have a custom  mple ntat on of Nu r cDocValues,
      // ColumnStr deF eldDocValues, wh ch w ll conta n an entry for every  ndexed doc d and use a
      // value of 0 to  nd cate that a feature  s unset.
      //
      // So to c ck  f a feature  s unset for a g ven doc d,   f rst need to c ck  f   can f nd
      // t  doc d, and t n   add  onally need to c ck  f t  feature value  s 0.
      return featureDocValues == null
          || !featureDocValues.advanceExact(doc D())
          || featureDocValues.longValue() == 0
          ||  dsAcceptL st.conta ns(featureDocValues.longValue());
    }
  }
}
