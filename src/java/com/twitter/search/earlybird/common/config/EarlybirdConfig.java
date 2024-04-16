package com.tw ter.search.earlyb rd.common.conf g;

 mport java.ut l.Date;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport javax.annotat on.Nullable;

 mport com.google.common.collect. mmutableMap;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common.aurora.Aurora nstanceKey;
 mport com.tw ter.search.common.conf g.Conf g;
 mport com.tw ter.search.common.conf g.Conf gF le;
 mport com.tw ter.search.common.conf g.Conf gurat onExcept on;
 mport com.tw ter.search.common.conf g.SearchPengu nVers onsConf g;

publ c f nal class Earlyb rdConf g {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdConf g.class);

  pr vate stat c f nal Str ng DEFAULT_CONF G_F LE = "earlyb rd-search.yml";
  pr vate stat c f nal Str ng LATE_TWEET_BUFFER_KEY = "late_t et_buffer";

  publ c stat c f nal Str ng EARLYB RD_ZK_CONF G_D R = "/tw ter/search/product on/earlyb rd/";
  publ c stat c f nal Str ng EARLYB RD_CONF G_D R = "earlyb rd/conf g";

  publ c stat c f nal Str ng USER_SNAPSHOT_BASE_D R = "user_snapshot_base_d r";

  pr vate stat c volat le Conf gF le earlyb rdConf g = null;
  pr vate stat c volat le Map<Str ng, Object> overr deValueMap =  mmutableMap.of();

  pr vate stat c Str ng logD rOverr de = null;
  pr vate stat c Aurora nstanceKey aurora nstanceKey = null;

  pr vate stat c  nt adm nPort;

  pr vate Earlyb rdConf g() { }

  pr vate stat c f nal class Pengu nVers onHolder {
    pr vate stat c f nal Pengu nVers on PENGU N_VERS ON_S NGLETON =
        SearchPengu nVers onsConf g.getS ngleSupportedVers on(
            Earlyb rdProperty.PENGU N_VERS ON.get());
    pr vate stat c f nal byte PENGU N_VERS ON_BYTE_VALUE =
        PENGU N_VERS ON_S NGLETON.getByteValue();
  }

  publ c stat c byte getPengu nVers onByte() {
    return Pengu nVers onHolder.PENGU N_VERS ON_BYTE_VALUE;
  }

  publ c stat c Pengu nVers on getPengu nVers on() {
    return Pengu nVers onHolder.PENGU N_VERS ON_S NGLETON;
  }

  /**
   * Reads t  earlyb rd conf gurat on from t  g ven f le.
   */
  publ c stat c synchron zed vo d  n (@Nullable Str ng conf gF le) {
     f (earlyb rdConf g == null) {
      Str ng f le = conf gF le == null ? DEFAULT_CONF G_F LE : conf gF le;
      earlyb rdConf g = new Conf gF le(EARLYB RD_CONF G_D R, f le);
    }
  }

  publ c stat c synchron zed vo d setOverr deValues(Map<Str ng, Object> overr deValues) {
    overr deValueMap =  mmutableMap.copyOf(overr deValues);
  }

  /**
   * Pack all values  n a str ng that can be pr nted for  nformat onal purposes.
   * @return t  str ng.
   */
  publ c stat c Str ng allValuesAsStr ng() {
    Map<Str ng, Str ng> str ngMap = earlyb rdConf g.getStr ngMap();

    Str ngBu lder str ngBu lder = new Str ngBu lder();

    str ngBu lder.append("Conf g env ron nt: " + Conf g.getEnv ron nt() + "\n\n");
    str ngBu lder.append(
        Str ng.format("Values from earlyb rd-search.yml (total %d):\n", str ngMap.s ze()));

    str ngMap.forEach((key, value) -> {
      str ngBu lder.append(Str ng.format("  %s: %s\n", key, value.toStr ng()));
       f (overr deValueMap.conta nsKey(key)) {
        str ngBu lder.append(Str ng.format(
          "    overr de value: %s\n", overr deValueMap.get(key).toStr ng()));
      }
    });

    str ngBu lder.append(Str ng.format(
        "\n\nAll command-l ne overr des (total: %d):\n", overr deValueMap.s ze()));
    overr deValueMap.forEach((key, value) -> {
      str ngBu lder.append(Str ng.format("  %s: %s\n", key, value.toStr ng()));
    });

    return str ngBu lder.toStr ng();
  }

  /**
   * Returns t  value of t  g ven property as a str ng.  f t  property  s not set, a runt  
   * except on  s thrown.
   */
  publ c stat c Str ng getStr ng(Str ng property) {
    Object overr deValue = overr deValueMap.get(property);
     f (overr deValue != null) {
      return (Str ng) overr deValue;
    }

    try {
      return earlyb rdConf g.getStr ng(property);
    } catch (Conf gurat onExcept on e) {
      LOG.error("Fatal error: could not get conf g str ng " + property, e);
      throw new Runt  Except on(e);
    }
  }

  /**
   * Returns t  value of t  g ven property as a str ng.
   */
  publ c stat c Str ng getStr ng(Str ng property, Str ng defaultValue) {
    Object overr deValue = overr deValueMap.get(property);
     f (overr deValue != null) {
      return (Str ng) overr deValue;
    }

    return earlyb rdConf g.getStr ng(property, defaultValue);
  }

  /**
   * Returns t  value of t  g ven property as an  nteger.  f t  property  s not set, a runt  
   * except on  s thrown.
   */
  publ c stat c  nt get nt(Str ng property) {
    Object overr deValue = overr deValueMap.get(property);
     f (overr deValue != null) {
      return ( nt) overr deValue;
    }

    try {
      return earlyb rdConf g.get nt(property);
    } catch (Conf gurat onExcept on e) {
      LOG.error("Fatal error: could not get conf g  nt " + property, e);
      throw new Runt  Except on(e);
    }
  }

  /**
   * Returns t  value of t  g ven property as an  nteger.
   */
  publ c stat c  nt get nt(Str ng property,  nt defaultValue) {
    Object overr deValue = overr deValueMap.get(property);
     f (overr deValue != null) {
      return ( nt) overr deValue;
    }

    return earlyb rdConf g.get nt(property, defaultValue);
  }

  /**
   * Returns t  value of t  g ven property as a double.
   */
  publ c stat c double getDouble(Str ng property, double defaultValue) {
    Object overr deValue = overr deValueMap.get(property);
     f (overr deValue != null) {
      return (double) overr deValue;
    }

    return earlyb rdConf g.getDouble(property, defaultValue);
  }

  /**
   * Returns t  value of t  g ven property as a long.  f t  property  s not set, a runt  
   * except on  s thrown.
   */
  publ c stat c long getLong(Str ng property) {
    Object overr deValue = overr deValueMap.get(property);
     f (overr deValue != null) {
      return (long) overr deValue;
    }

    try {
      return earlyb rdConf g.getLong(property);
    } catch (Conf gurat onExcept on e) {
      LOG.error("Fatal error: could not get conf g long " + property, e);
      throw new Runt  Except on(e);
    }
  }

  /**
   * Returns t  value of t  g ven property as a long.
   */
  publ c stat c long getLong(Str ng property, long defaultValue) {
    Object overr deValue = overr deValueMap.get(property);
     f (overr deValue != null) {
      return (long) overr deValue;
    }

    return earlyb rdConf g.getLong(property, defaultValue);
  }

  /**
   * Returns t  value of t  g ven property as a boolean.  f t  property  s not set, a runt  
   * except on  s thrown.
   */
  publ c stat c boolean getBool(Str ng property) {
    Object overr deValue = overr deValueMap.get(property);
     f (overr deValue != null) {
      return (boolean) overr deValue;
    }

    try {
      return earlyb rdConf g.getBool(property);
    } catch (Conf gurat onExcept on e) {
      LOG.error("Fatal error: could not get conf g boolean " + property, e);
      throw new Runt  Except on(e);
    }
  }

  /**
   * Returns t  value of t  g ven property as a boolean.
   */
  publ c stat c boolean getBool(Str ng property, boolean defaultValue) {
    Object overr deValue = overr deValueMap.get(property);
     f (overr deValue != null) {
      return (boolean) overr deValue;
    }

    return earlyb rdConf g.getBool(property, defaultValue);
  }

  /**
   * Returns t  value of t  g ven property as a date.
   */
  publ c stat c Date getDate(Str ng property) {
    Object overr deValue = overr deValueMap.get(property);
     f (overr deValue != null) {
      return (Date) overr deValue;
    }

    Date date = (Date) earlyb rdConf g.getObject(property, null);
     f (date == null) {
      throw new Runt  Except on("Could not get conf g date: " + property);
    }
    return date;
  }

  /**
   * Returns t  value of t  g ven property as a l st of str ngs.
   */
  publ c stat c L st<Str ng> getL stOfStr ngs(Str ng property) {
    Object overr deValue = overr deValueMap.get(property);
     f (overr deValue != null) {
      return (L st<Str ng>) overr deValue;
    }

    L st<Str ng> l st = (L st<Str ng>) earlyb rdConf g.getObject(property, null);
     f (l st == null) {
      throw new Runt  Except on("Could not get l st of str ngs: " + property);
    }
    return l st;
  }

  /**
   * Returns t  value of t  g ven property as a map.
   */
  @SuppressWarn ngs("unc cked")
  publ c stat c Map<Str ng, Object> getMap(Str ng property) {
    Map<Str ng, Object> map = (Map<Str ng, Object>) earlyb rdConf g.getObject(property, null);
     f (map == null) {
      throw new Runt  Except on("Could not f nd conf g property: " + property);
    }
    return map;
  }

  publ c stat c  nt getMaxSeg ntS ze() {
    return Earlyb rdConf g.get nt("max_seg nt_s ze", 1 << 16);
  }

  /**
   * Returns t  log propert es f le.
   */
  publ c stat c Str ng getLogPropert esF le() {
    try {
      Str ng f lena  = earlyb rdConf g.getStr ng("log_propert es_f lena ");
      return earlyb rdConf g.getConf gF lePath(f lena );
    } catch (Conf gurat onExcept on e) {
      // Pr nt  re rat r than use LOG - log was probably not  n  al zed yet.
      LOG.error("Fatal error: could not get log propert es f le", e);
      throw new Runt  Except on(e);
    }
  }

  /**
   * Returns t  log d rectory.
   */
  publ c stat c Str ng getLogD r() {
     f (logD rOverr de != null) {
      return logD rOverr de;
    } else {
      return Earlyb rdConf g.getStr ng("log_d r");
    }
  }

  publ c stat c vo d overr deLogD r(Str ng logD r) {
    Earlyb rdConf g.logD rOverr de = logD r;
  }

  publ c stat c  nt getThr ftPort() {
    return Earlyb rdProperty.THR FT_PORT.get();
  }

  publ c stat c  nt getWarmUpThr ftPort() {
    return Earlyb rdProperty.WARMUP_THR FT_PORT.get();
  }

  publ c stat c  nt getSearc rThreads() {
    return Earlyb rdProperty.SEARCHER_THREADS.get();
  }

  publ c stat c  nt getLateT etBuffer() {
    return get nt(LATE_TWEET_BUFFER_KEY);
  }

  publ c stat c  nt getAdm nPort() {
    return adm nPort;
  }

  publ c stat c vo d setAdm nPort( nt adm nPort) {
    Earlyb rdConf g.adm nPort = adm nPort;
  }

  publ c stat c boolean  sRealt  OrProtected() {
    Str ng earlyb rdNa  = Earlyb rdProperty.EARLYB RD_NAME.get();
    return earlyb rdNa .conta ns("realt  ") || earlyb rdNa .conta ns("protected");
  }

  publ c stat c boolean consu UserScrubGeoEvents() {
    return Earlyb rdProperty.CONSUME_GEO_SCRUB_EVENTS.get();
  }

  @Nullable
  publ c stat c Aurora nstanceKey getAurora nstanceKey() {
    return aurora nstanceKey;
  }

  publ c stat c vo d setAurora nstanceKey(Aurora nstanceKey aurora nstanceKey) {
    Earlyb rdConf g.aurora nstanceKey = aurora nstanceKey;
  }

  publ c stat c boolean  sAurora() {
    return aurora nstanceKey != null;
  }

  publ c stat c vo d setForTests(Str ng property, Object value) {
    earlyb rdConf g.setForTests(DEFAULT_CONF G_F LE, property, value);
  }

  publ c stat c synchron zed vo d clearForTests() {
    earlyb rdConf g = new Conf gF le(EARLYB RD_CONF G_D R, DEFAULT_CONF G_F LE);
  }
}
