package com.tw ter.search.core.earlyb rd. ndex;

 mport org.apac .lucene.docu nt.F eld;
 mport org.apac .lucene. ndex.DocValuesType;

 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;

publ c class Earlyb rd ndexableF eld extends F eld {

  /**
   * Creates a new  ndexable f eld w h t  g ven na , value and {@l nk Earlyb rdF eldType}.
   */
  publ c Earlyb rd ndexableF eld(Str ng na , Object value, Earlyb rdF eldType f eldType) {
    super(na , f eldType);
     f (f eldType.docValuesType() == DocValuesType.NUMER C) {
       f (value  nstanceof Number) {
        super.f eldsData = ((Number) value).longValue();
      } else {
        throw new  llegalArgu ntExcept on("value not a number: " + value.getClass());
      }
    }
  }

}
