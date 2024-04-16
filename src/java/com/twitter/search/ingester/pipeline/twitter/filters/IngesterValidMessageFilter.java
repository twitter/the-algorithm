package com.tw ter.search. ngester.p pel ne.tw ter.f lters;

 mport java.ut l.EnumSet;
 mport java.ut l.Set;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssageUt l;

publ c class  ngesterVal d ssageF lter {
  publ c stat c f nal Str ng KEEP_NULLCAST_DEC DER_KEY =
      " ngester_all_keep_nullcasts";
  publ c stat c f nal Str ng STR P_SUPPLEMENTARY_EMOJ S_DEC DER_KEY_PREF X =
      "val d_ ssage_f lter_str p_supple ntary_emoj s_";

  protected f nal Dec der dec der;

  publ c  ngesterVal d ssageF lter(Dec der dec der) {
    t .dec der = dec der;
  }

  /**
   * Evaluate a  ssage to see  f   matc s t  f lter or not.
   *
   * @param  ssage to evaluate
   * @return true  f t   ssage should be em ted.
   */
  publ c boolean accepts(Tw ter ssage  ssage) {
    return Tw ter ssageUt l.val dateTw ter ssage(
         ssage, getStr pEmoj sF elds(), acceptNullcast());
  }

  pr vate Set<Tw ter ssageUt l.F eld> getStr pEmoj sF elds() {
    Set<Tw ter ssageUt l.F eld> str pEmoj sF elds =
        EnumSet.noneOf(Tw ter ssageUt l.F eld.class);
    for (Tw ter ssageUt l.F eld f eld : Tw ter ssageUt l.F eld.values()) {
       f (Dec derUt l. sAva lableForRandomRec p ent(
          dec der,
          STR P_SUPPLEMENTARY_EMOJ S_DEC DER_KEY_PREF X + f eld.getNa ForStats())) {
        str pEmoj sF elds.add(f eld);
      }
    }
    return str pEmoj sF elds;
  }

  protected f nal boolean acceptNullcast() {
    return Dec derUt l. sAva lableForRandomRec p ent(dec der, KEEP_NULLCAST_DEC DER_KEY);
  }
}
