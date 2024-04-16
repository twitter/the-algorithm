package com.tw ter.search.earlyb rd.conf g;

 mport java.ut l.Date;
 mport java.ut l.Map;
 mport java.ut l.Set;

 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.conf g.Conf g;
 mport com.tw ter.search.common.conf g.Conf gF le;
 mport com.tw ter.search.common.conf g.Conf gurat onExcept on;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common.ut l.date.DateUt l;

/**
 * T  class prov des AP s to access t  t er conf gurat ons for a cluster.
 * Each t er has t er na , number of part  ons, t er start t   and end t  .
 */
publ c f nal class T erConf g {
  pr vate stat c f nal org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(T erConf g.class);

  pr vate stat c f nal Str ng DEFAULT_CONF G_D R = "common/conf g";
  publ c stat c f nal Str ng DEFAULT_T ER_F LE = "earlyb rd-t ers.yml";

  publ c stat c f nal Date DEFAULT_T ER_START_DATE = DateUt l.toDate(2006, 3, 21);
  //  's conven ent for DEFAULT_T ER_END_DATE to be before ~2100, because t n t  output of
  // F eldTermCounter.getH Value(DEFAULT_T ER_END_END_DATE) can st ll f   nto an  nteger.
  publ c stat c f nal Date DEFAULT_T ER_END_DATE = DateUt l.toDate(2099, 1, 1);

  publ c stat c f nal Str ng DEFAULT_T ER_NAME = "all";
  publ c stat c f nal boolean DEFAULT_ENABLED = true;
  publ c stat c f nal T er nfo.RequestReadType DEFAULT_READ_TYPE = T er nfo.RequestReadType.L GHT;

  pr vate stat c Conf gF le t erConf gF le = null;
  pr vate stat c Conf gS ce t erConf gS ce = null;

  publ c enum Conf gS ce {
    LOCAL,
    ZOOKEEPER
  }

  pr vate T erConf g() { }

  pr vate stat c synchron zed vo d  n () {
     f (t erConf gF le == null) {
      t erConf gF le = new Conf gF le(DEFAULT_CONF G_D R, DEFAULT_T ER_F LE);
      t erConf gS ce = Conf gS ce.LOCAL;
      SearchLongGauge.export("t er_conf g_s ce_" + t erConf gS ce.na ()).set(1);
      LOG. nfo("T er conf g f le " + DEFAULT_T ER_F LE + "  s successfully loaded from bundle.");
    }
  }

  publ c stat c Conf gF le getConf gF le() {
     n ();
    return t erConf gF le;
  }

  publ c stat c Str ng getConf gF leNa () {
    return getConf gF le().getConf gF leNa ();
  }

  /**
   * Return all t  t er na s spec f ed  n t  conf g f le.
   */
  publ c stat c Set<Str ng> getT erNa s() {
    return Conf g.getConf g().getMapCopy(getConf gF leNa ()).keySet();
  }

  /**
   * Sets t  value of t  g ven t er conf g property to t  g ven value.
   */
  publ c stat c vo d setForTests(Str ng property, Object value) {
    Conf g.getConf g().setForTests(DEFAULT_T ER_F LE, property, value);
  }

  /**
   * Returns t  conf g  nfo for t  spec f ed t er.
   */
  publ c stat c T er nfo getT er nfo(Str ng t erNa ) {
    return getT er nfo(t erNa , null /* use current env ron nt */);
  }

  /**
   * Returns t  conf g  nfo for t  spec f ed t er and env ron nt.
   */
  publ c stat c T er nfo getT er nfo(Str ng t erNa , @Nullable Str ng env ron nt) {
    Str ng t erConf gF leType = getConf gF leNa ();
    Map<Str ng, Object> t er nfo;
    try {
      t er nfo = (Map<Str ng, Object>) Conf g.getConf g()
          .getFromEnv ron nt(env ron nt, t erConf gF leType, t erNa );
    } catch (Conf gurat onExcept on e) {
      throw new Runt  Except on(e);
    }
     f (t er nfo == null) {
      LOG.error("Cannot f nd t er conf g for "
          + t erNa  + " n conf g f le: " + t erConf gF leType);
      throw new Runt  Except on("Conf gurat on error: " + t erConf gF leType);
    }

    Long part  ons = (Long) t er nfo.get("number_of_part  ons");
     f (part  ons == null) {
      LOG.error("No number of part  on  s spec f ed for t er "
          + t erNa  + "  n t er conf g f le " + t erConf gF leType);
      throw new Runt  Except on("Conf gurat on error: " + t erConf gF leType);
    }

    Long numT  sl ces = (Long) t er nfo.get("serv ng_t  sl ces");
     f (numT  sl ces == null) {
      LOG. nfo("No max t  sl ces  s spec f ed for t er "
          + t erNa  + "  n t er conf g f le " + t erConf gF leType
          + ", not sett ng a cap on number of serv ng t  sl ces");
      // NOTE:   use max  nt32  re because   w ll ult mately be cast to an  nt, but t  conf g
      // map expects Longs for all  ntegral types.  Us ng Long.MAX_VALUE leads to max serv ng
      // t  sl ces be ng set to -1 w n    s truncated to an  nt.
      numT  sl ces = (long)  nteger.MAX_VALUE;
    }

    Date t erStartDate = (Date) t er nfo.get("data_range_start_date_ nclus ve");
     f (t erStartDate == null) {
      t erStartDate = DEFAULT_T ER_START_DATE;
    }
    Date t erEndDate = (Date) t er nfo.get("data_range_end_date_exclus ve");
     f (t erEndDate == null) {
      t erEndDate = DEFAULT_T ER_END_DATE;
    }

    Boolean t erEnabled = (Boolean) t er nfo.get("t er_enabled");
     f (t erEnabled == null) {
      t erEnabled = DEFAULT_ENABLED;
    }

    T er nfo.RequestReadType readType =
      getRequestReadType((Str ng) t er nfo.get("t er_read_type"), DEFAULT_READ_TYPE);
    T er nfo.RequestReadType readTypeOverr de =
      getRequestReadType((Str ng) t er nfo.get("t er_read_type_overr de"), readType);

    return new T er nfo(
        t erNa ,
        t erStartDate,
        t erEndDate,
        part  ons. ntValue(),
        numT  sl ces. ntValue(),
        t erEnabled,
        (Str ng) t er nfo.get("serv ng_range_s nce_ d_exclus ve"),
        (Str ng) t er nfo.get("serv ng_range_max_ d_ nclus ve"),
        (Date) t er nfo.get("serv ng_range_start_date_ nclus ve_overr de"),
        (Date) t er nfo.get("serv ng_range_end_date_exclus ve_overr de"),
        readType,
        readTypeOverr de,
        Clock.SYSTEM_CLOCK);
  }

  publ c stat c synchron zed vo d clear() {
    t erConf gF le = null;
    t erConf gS ce = null;
  }

  protected stat c synchron zed Conf gS ce getT erConf gS ce() {
    return t erConf gS ce;
  }

  pr vate stat c T er nfo.RequestReadType getRequestReadType(
      Str ng readTypeEnumNa , T er nfo.RequestReadType defaultReadType) {
    T er nfo.RequestReadType readType = defaultReadType;
     f (readTypeEnumNa  != null) {
      readType = T er nfo.RequestReadType.valueOf(readTypeEnumNa .tr m().toUpperCase());
      Precond  ons.c ckState(readType != null);
    }
    return readType;
  }
}
