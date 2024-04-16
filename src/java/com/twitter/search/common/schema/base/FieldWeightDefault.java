package com.tw ter.search.common.sc ma.base;

 mport java.ut l.L nkedHashMap;
 mport java.ut l.Map;

 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.Maps;

 mport stat c com.google.common.base.Precond  ons.c ckNotNull;

/**
 * Records w t r a f eld's enabled for search by default and  s default   ght. Note that t se
 * two are decoupled -- a f eld can have a default   ght but not enabled for search by default.
 *  n a query   can be enabled by an annotat on that does not spec fy a   ght (e.g., ":f:foo"),
 * wh ch would t n use t  default   ght.
 *
 *  nstances are mutable.
 */
publ c class F eld  ghtDefault {
  pr vate f nal boolean enabled;
  pr vate f nal float   ght;

  publ c F eld  ghtDefault(boolean enabled, float   ght) {
    t .enabled = enabled;
    t .  ght =   ght;
  }

  publ c stat c F eld  ghtDefault fromS gned  ght(float s gnedValue) {
    return new F eld  ghtDefault(s gnedValue >= 0, Math.abs(s gnedValue));
  }

  /**
   * Returns an  mmutable map from f eld na  to default f eld   ghts for only enabled f elds.
   * F elds that are not enabled for search by default w ll not be  ncluded.
   */
  publ c stat c <T>  mmutableMap<T, Float> getOnlyEnabled(
      Map<T, F eld  ghtDefault> map) {

     mmutableMap.Bu lder<T, Float> bu lder =  mmutableMap.bu lder();
    for (Map.Entry<T, F eld  ghtDefault> entry : map.entrySet()) {
       f (entry.getValue(). sEnabled()) {
        bu lder.put(entry.getKey(), entry.getValue().get  ght());
      }
    }
    return bu lder.bu ld();
  }

  publ c boolean  sEnabled() {
    return enabled;
  }

  publ c float get  ght() {
    return   ght;
  }

  /**
   * Overlays t  base f eld-  ght map w h t  g ven one. S nce    s an overlay, a
   * f eld that does not ex st  n t  base map w ll never be added. Also, negat ve value  ans
   * t  f eld  s not enabled for search by default, but  f    s, t  absolute value would serve as
   * t  default.
   */
  publ c stat c  mmutableMap<Str ng, F eld  ghtDefault> overr deF eld  ghtMap(
      Map<Str ng, F eld  ghtDefault> base,
      Map<Str ng, Double> f eld  ghtMapOverr de) {

    c ckNotNull(base);
     f (f eld  ghtMapOverr de == null) {
      return  mmutableMap.copyOf(base);
    }

    L nkedHashMap<Str ng, F eld  ghtDefault> map = Maps.newL nkedHashMap(base);
    for (Map.Entry<Str ng, Double> entry : f eld  ghtMapOverr de.entrySet()) {
       f (base.conta nsKey(entry.getKey())
          && entry.getValue() >= -Float.MAX_VALUE
          && entry.getValue() <= Float.MAX_VALUE) {

        map.put(
            entry.getKey(),
            F eld  ghtDefault.fromS gned  ght(entry.getValue().floatValue()));
      }
    }

    return  mmutableMap.copyOf(map);
  }

  /**
   * Creates a f eld-to-F eld  ghtDefault map from t  g ven f eld-to-  ght map, w re negat ve
   *   ght  ans t  t  f eld  s not enabled for search by default, but  f    s (e.g.,
   * by annotat on), t  absolute value of t    ght shall be used.
   */
  publ c stat c <T>  mmutableMap<T, F eld  ghtDefault> fromS gned  ghtMap(
      Map<T, ? extends Number> s gned  ghtMap) {

     mmutableMap.Bu lder<T, F eld  ghtDefault> bu lder =  mmutableMap.bu lder();
    for (Map.Entry<T, ? extends Number> entry : s gned  ghtMap.entrySet()) {
      //  f double to float convers on fa led,   w ll get a float  nf n y.
      // See http://stackoverflow.com/a/10075093/716468
      float floatValue = entry.getValue().floatValue();
       f (floatValue != Float.NEGAT VE_ NF N TY
          && floatValue != Float.POS T VE_ NF N TY) {

        bu lder.put(
            entry.getKey(),
            F eld  ghtDefault.fromS gned  ght(floatValue));
      }
    }

    return bu lder.bu ld();
  }
}
