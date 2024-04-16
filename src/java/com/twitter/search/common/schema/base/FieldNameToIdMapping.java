package com.tw ter.search.common.sc ma.base;

 mport java.ut l.Map;

 mport com.google.common.collect. mmutableMap;

/**
 * Maps from f eldNa  to f eld Ds.
 */
publ c abstract class F eldNa To dMapp ng {
  /**
   * Returns f eld  D for t  g ven f eldNa .
   * Can throw unc cked except ons  s t  f eldNa   s not known to Earlyb rd.
   */
  publ c abstract  nt getF eld D(Str ng f eldNa );

  /**
   * Wrap t  g ven map  nto a f eldNa To dMapp ng  nstance.
   */
  publ c stat c F eldNa To dMapp ng newF eldNa To dMapp ng(Map<Str ng,  nteger> map) {
    f nal  mmutableMap<Str ng,  nteger>  mmutableMap =  mmutableMap.copyOf(map);
    return new F eldNa To dMapp ng() {
      @Overr de publ c  nt getF eld D(Str ng f eldNa ) {
        return  mmutableMap.get(f eldNa );
      }
    };
  }
}
