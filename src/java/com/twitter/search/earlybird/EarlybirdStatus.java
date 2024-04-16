package com.tw ter.search.earlyb rd;

 mport java.text.S mpleDateFormat;
 mport java.ut l.Date;
 mport java.ut l.L st;
 mport java.ut l.Opt onal;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.concurrent.atom c.Atom cBoolean;

 mport com.google.common.collect.L sts;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Bu ld nfo;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdStatusCode;
 mport com.tw ter.ut l.Durat on;

/**
 * H gh level status of an Earlyb rd server. SEARCH-28016
 */
publ c f nal class Earlyb rdStatus {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdStatus.class);

  pr vate stat c f nal Str ng BU LD_SHA = getBu ldShaFromVars();

  protected stat c long startT  ;
  protected stat c Earlyb rdStatusCode statusCode;
  protected stat c Str ng status ssage;
  protected stat c f nal Atom cBoolean THR FT_PORT_OPEN = new Atom cBoolean(false);
  protected stat c f nal Atom cBoolean WARMUP_THR FT_PORT_OPEN = new Atom cBoolean(false);
  protected stat c f nal Atom cBoolean THR FT_SERV CE_STARTED = new Atom cBoolean(false);

  pr vate stat c f nal L st<Earlyb rdEvent> EARLYB RD_SERVER_EVENTS = L sts.newArrayL st();
  pr vate stat c class Earlyb rdEvent {
    pr vate f nal Str ng eventNa ;
    pr vate f nal long t  stampM ll s;
    pr vate f nal long t  S nceServerStartM ll s;
    pr vate f nal long durat onM ll s;

    publ c Earlyb rdEvent(Str ng eventNa , long t  stampM ll s) {
      t (eventNa , t  stampM ll s, -1);
    }

    publ c Earlyb rdEvent(
        Str ng eventNa ,
        long t  stampM ll s,
        long eventDurat onM ll s) {
      t .eventNa  = eventNa ;
      t .t  stampM ll s = t  stampM ll s;
      t .t  S nceServerStartM ll s = t  stampM ll s - startT  ;
      t .durat onM ll s = eventDurat onM ll s;
    }

    publ c Str ng getEventLogStr ng() {
      Str ng result = Str ng.format(
          "%s %s",
          new S mpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(t  stampM ll s)),
          eventNa );

       f (durat onM ll s > 0) {
        result += Str ng.format(
            ", took: %s", Durat on.apply(durat onM ll s, T  Un .M LL SECONDS).toStr ng());
      }

      result += Str ng.format(
          ", t   s nce server start: %s",
          Durat on.apply(t  S nceServerStartM ll s, T  Un .M LL SECONDS).toStr ng()
      );

      return result;
    }
  }

  pr vate Earlyb rdStatus() {
  }

  publ c stat c synchron zed vo d setStartT  (long t  ) {
    startT   = t  ;
    LOG. nfo("startT   set to " + t  );
  }

  publ c stat c synchron zed vo d setStatus(Earlyb rdStatusCode code) {
    setStatus(code, null);
  }

  publ c stat c synchron zed vo d setStatus(Earlyb rdStatusCode code, Str ng  ssage) {
    statusCode = code;
    status ssage =  ssage;
    LOG. nfo("status set to " + code + ( ssage != null ? " w h  ssage " +  ssage : ""));
  }

  publ c stat c synchron zed long getStartT  () {
    return startT  ;
  }

  publ c stat c synchron zed boolean  sStart ng() {
    return statusCode == Earlyb rdStatusCode.START NG;
  }

  publ c stat c synchron zed boolean hasStarted() {
    return statusCode == Earlyb rdStatusCode.CURRENT;
  }

  publ c stat c boolean  sThr ftServ ceStarted() {
    return THR FT_SERV CE_STARTED.get();
  }

  publ c stat c synchron zed Earlyb rdStatusCode getStatusCode() {
    return statusCode;
  }

  publ c stat c synchron zed Str ng getStatus ssage() {
    return (status ssage == null ? "" : status ssage + ", ")
        + "warmup thr ft port  s " + (WARMUP_THR FT_PORT_OPEN.get() ? "OPEN" : "CLOSED")
        + ", product on thr ft port  s " + (THR FT_PORT_OPEN.get() ? "OPEN" : "CLOSED");
  }

  publ c stat c synchron zed vo d recordEarlyb rdEvent(Str ng eventNa ) {
    long t  M ll s = System.currentT  M ll s();
    EARLYB RD_SERVER_EVENTS.add(new Earlyb rdEvent(eventNa , t  M ll s));
  }

  pr vate stat c Str ng getBeg nEvent ssage(Str ng eventNa ) {
    return "[Beg n Event] " + eventNa ;
  }

  pr vate stat c Str ng getEndEvent ssage(Str ng eventNa ) {
    return "[ End Event ] " + eventNa ;
  }

  /**
   * Records t  beg nn ng of t  g ven event.
   *
   * @param eventNa  T  event na .
   * @param startup tr c T   tr c that w ll be used to keep track of t  t   for t  event.
   */
  publ c stat c synchron zed vo d beg nEvent(Str ng eventNa ,
                                             Search ndex ng tr cSet.Startup tr c startup tr c) {
    long t  M ll s = System.currentT  M ll s();
    Str ng event ssage = getBeg nEvent ssage(eventNa );
    LOG. nfo(event ssage);
    EARLYB RD_SERVER_EVENTS.add(new Earlyb rdEvent(event ssage, t  M ll s));

    startup tr c.beg n();
  }

  /**
   * Records t  end of t  g ven event.
   *
   * @param eventNa  T  event na .
   * @param startup tr c T   tr c used to keep track of t  t   for t  event.
   */
  publ c stat c synchron zed vo d endEvent(Str ng eventNa ,
                                           Search ndex ng tr cSet.Startup tr c startup tr c) {
    long t  M ll s = System.currentT  M ll s();

    Str ng beg nEvent ssage = getBeg nEvent ssage(eventNa );
    Opt onal<Earlyb rdEvent> beg nEventOpt = EARLYB RD_SERVER_EVENTS.stream()
        .f lter(event -> event.eventNa .equals(beg nEvent ssage))
        .f ndF rst();

    Str ng event ssage = getEndEvent ssage(eventNa );
    LOG. nfo(event ssage);
    Earlyb rdEvent endEvent = new Earlyb rdEvent(
        event ssage,
        t  M ll s,
        beg nEventOpt.map(e -> t  M ll s - e.t  stampM ll s).orElse(-1L));

    EARLYB RD_SERVER_EVENTS.add(endEvent);

    startup tr c.end(endEvent.durat onM ll s);
  }

  publ c stat c synchron zed vo d clearAllEvents() {
    EARLYB RD_SERVER_EVENTS.clear();
  }

  publ c stat c Str ng getBu ldSha() {
    return BU LD_SHA;
  }

  /**
   * Returns t  l st of all earlyb rd events that happened s nce t  server started.
   */
  publ c stat c synchron zed  erable<Str ng> getEarlyb rdEvents() {
    L st<Str ng> eventLog = L sts.newArrayL stW hCapac y(EARLYB RD_SERVER_EVENTS.s ze());
    for (Earlyb rdEvent event : EARLYB RD_SERVER_EVENTS) {
      eventLog.add(event.getEventLogStr ng());
    }
    return eventLog;
  }

  pr vate stat c Str ng getBu ldShaFromVars() {
    Bu ld nfo bu ld nfo = new Bu ld nfo();
    Str ng bu ldSha = bu ld nfo.getPropert es().getProperty(Bu ld nfo.Key.G T_REV S ON.value);
     f (bu ldSha != null) {
      return bu ldSha;
    } else {
      return "UNKNOWN";
    }
  }
}
