package com.tw ter.search.common.query;

 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.Maps;

/**
 * T   nd ces may map t  f elds declared  re to f elds  nternally w hout expos ng t  r sc mas
 * to ot r serv ces. T  can be used, for example, to set boosts for URL-l ke f elds  n Earlyb rd
 * w hout d rect knowledge of t   nternal Earlyb rd f eld na 
 */
publ c enum MappableF eld {
  REFERRAL,
  URL;

  stat c {
     mmutableMap.Bu lder<MappableF eld, Str ng> bu lder =  mmutableMap.bu lder();
    for (MappableF eld mappableF eld : MappableF eld.values()) {
      bu lder.put(mappableF eld, mappableF eld.toStr ng().toLo rCase());
    }
    MAPPABLE_F ELD_TO_NAME_MAP = Maps. mmutableEnumMap(bu lder.bu ld());
  }

  pr vate stat c f nal  mmutableMap<MappableF eld, Str ng> MAPPABLE_F ELD_TO_NAME_MAP;

  /** Returns t  na  of t  g ven MappableF eld. */
  publ c stat c Str ng mappableF eldNa (MappableF eld mappableF eld) {
    return MAPPABLE_F ELD_TO_NAME_MAP.get(mappableF eld);
  }

  /** Returns t  na  of t  MappableF eld. */
  publ c Str ng getNa () {
    return MAPPABLE_F ELD_TO_NAME_MAP.get(t );
  }
}
