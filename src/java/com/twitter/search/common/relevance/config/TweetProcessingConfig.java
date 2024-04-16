package com.tw ter.search.common.relevance.conf g;

 mport java. o. nputStream;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.conf g.Conf gF le;

/**
 * Conf g f le for relevance computat on.
 */
publ c f nal class T etProcess ngConf g {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(T etProcess ngConf g.class);
  pr vate stat c f nal Str ng SCORER_CONF G_D R = "common/relevance/conf g";
  publ c stat c f nal Str ng DEFAULT_CONF G_F LE = "relevance.yml";
  pr vate stat c Conf gF le relevanceConf g = null;

  pr vate T etProcess ngConf g() {
  }

  /**  n  al zes t   nstance from t  g ven conf g f le. */
  publ c stat c vo d  n (Str ng conf gF le) {
     f (relevanceConf g == null) {
      synchron zed (T etProcess ngConf g.class) {
         f (relevanceConf g == null) {
          Str ng f le = conf gF le == null ? DEFAULT_CONF G_F LE : conf gF le;
          relevanceConf g = new Conf gF le(SCORER_CONF G_D R, f le);
        }
      }
    }
  }

  /**  n  al zes t   nstance from t  g ven  nput stream. */
  publ c stat c vo d  n ( nputStream  nputStream, Str ng conf gType) {
     f (relevanceConf g == null) {
      synchron zed (T etProcess ngConf g.class) {
         f (relevanceConf g == null) {
          relevanceConf g = new Conf gF le( nputStream, conf gType);
        }
      }
    }
  }

  /**  n  al zes t   nstance. */
  publ c stat c vo d  n () {
     n (null);
  }

  /**
   * Returns t  value of t  g ven property as a double value.
   *
   * @param property T  property.
   * @param defaultValue T  default value to return  f t  property  s not present  n t  conf g.
   */
  publ c stat c double getDouble(Str ng property, double defaultValue) {
    return relevanceConf g.getDouble(property, defaultValue);
  }

  /**
   * Returns t  value of t  g ven property as a str ng value.
   *
   * @param property T  property.
   * @param defaultValue T  default value to return  f t  property  s not present  n t  conf g.
   */
  publ c stat c Str ng getStr ng(Str ng property, Str ng defaultValue) {
    return relevanceConf g.getStr ng(property, defaultValue);
  }

  /**
   * Returns t  value of t  g ven property as an  nteger value.
   *
   * @param property T  property.
   * @param defaultValue T  default value to return  f t  property  s not present  n t  conf g.
   */
  publ c stat c  nt get nt(Str ng property,  nt defaultValue) {
    return relevanceConf g.get nt(property, defaultValue);
  }

  /**
   * Returns t  value of t  g ven property as a long value.
   *
   * @param property T  property.
   * @param defaultValue T  default value to return  f t  property  s not present  n t  conf g.
   */
  publ c stat c long getLong(Str ng property, long defaultValue) {
    return relevanceConf g.getLong(property, defaultValue);
  }

  /**
   * Returns t  value of t  g ven property as a boolean value.
   *
   * @param property T  property.
   * @param defaultValue T  default value to return  f t  property  s not present  n t  conf g.
   */
  publ c stat c boolean getBool(Str ng property, boolean defaultValue) {
    return relevanceConf g.getBool(property, defaultValue);
  }

  /**
   * Returns t  value of t  g ven property as a str ng.
   *
   * @param property T  property.
   * @throws Conf gurat onExcept on  f t  g ven property  s not found  n t  conf g.
   */
  publ c stat c Str ng getStr ng(Str ng property) {
    try {
      return relevanceConf g.getStr ng(property);
    } catch (Conf gurat onExcept on e) {
      LOG.error("Fatal error: could not get conf g str ng " + property, e);
      throw new Runt  Except on(e);
    }
  }
}
