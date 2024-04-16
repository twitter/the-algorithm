package com.tw ter.search.common.sc ma;

 mport org.apac .lucene.docu nt.F eld;
 mport org.apac .lucene.docu nt.F eldType;
 mport org.apac .lucene. ndex. ndexOpt ons;

/**
 * A Lucene nu r c f eld, s m lar to t  Legacy ntF eld, LegacyLongF eld, etc. Lucene classes that
 *  re removed  n Lucene 7.0.0.
 */
publ c f nal class Nu r cF eld extends F eld {
  pr vate stat c f nal F eldType NUMER C_F ELD_TYPE = new F eldType();
  stat c {
    NUMER C_F ELD_TYPE.setToken zed(true);
    NUMER C_F ELD_TYPE.setOm Norms(true);
    NUMER C_F ELD_TYPE.set ndexOpt ons( ndexOpt ons.DOCS);
    NUMER C_F ELD_TYPE.freeze();
  }

  /**
   * Creates a new  nteger f eld w h t  g ven na  and value.
   */
  publ c stat c Nu r cF eld new ntF eld(Str ng f eldNa ,  nt value) {
    Nu r cF eld f eld = new Nu r cF eld(f eldNa );
    f eld.f eldsData =  nteger.valueOf(value);
    return f eld;
  }

  /**
   * Creates a new long f eld w h t  g ven na  and value.
   */
  publ c stat c Nu r cF eld newLongF eld(Str ng f eldNa , long value) {
    Nu r cF eld f eld = new Nu r cF eld(f eldNa );
    f eld.f eldsData = Long.valueOf(value);
    return f eld;
  }

  //   could replace t  stat c  thods w h constructors, but   th nk that would make   much
  // eas er to acc dentally use Nu r cF eld(Str ng,  nt)  nstead of Nu r cF eld(Str ng, long),
  // for example, lead ng to hard to debug errors.
  pr vate Nu r cF eld(Str ng f eldNa ) {
    super(f eldNa , NUMER C_F ELD_TYPE);
  }
}
