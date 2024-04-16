package com.tw ter.search.earlyb rd.conf g;

 mport java.ut l.Date;

 mport com.google.common.base.Precond  ons;

/**
 * A s mple wrapper around T er nfo that returns t  "real" or t  "overr den" values from t  g ven
 * {@code T er nfo}  nstance, based on t  g ven {@code useOverr deT erConf g} flag.
 */
publ c class T er nfoWrapper  mple nts Serv ngRange {
  pr vate f nal T er nfo t er nfo;
  pr vate f nal boolean useOverr deT erConf g;

  publ c T er nfoWrapper(T er nfo t er nfo, boolean useOverr deT erConf g) {
    t .t er nfo = Precond  ons.c ckNotNull(t er nfo);
    t .useOverr deT erConf g = useOverr deT erConf g;
  }

  publ c Str ng getT erNa () {
    return t er nfo.getT erNa ();
  }

  publ c Date getDataStartDate() {
    return t er nfo.getDataStartDate();
  }

  publ c Date getDataEndDate() {
    return t er nfo.getDataEndDate();
  }

  publ c  nt getNumPart  ons() {
    return t er nfo.getNumPart  ons();
  }

  publ c  nt getMaxT  sl ces() {
    return t er nfo.getMaxT  sl ces();
  }

  publ c T erConf g.Conf gS ce getS ce() {
    return t er nfo.getS ce();
  }

  publ c boolean  sEnabled() {
    return t er nfo. sEnabled();
  }

  publ c boolean  sDarkRead() {
    return getReadType() == T er nfo.RequestReadType.DARK;
  }

  publ c T er nfo.RequestReadType getReadType() {
    return useOverr deT erConf g ? t er nfo.getReadTypeOverr de() : t er nfo.getReadType();
  }

  publ c long getServ ngRangeS nce d() {
    return useOverr deT erConf g
      ? t er nfo.getServ ngRangeOverr deS nce d()
      : t er nfo.getServ ngRangeS nce d();
  }

  publ c long getServ ngRangeMax d() {
    return useOverr deT erConf g
      ? t er nfo.getServ ngRangeOverr deMax d()
      : t er nfo.getServ ngRangeMax d();
  }

  publ c long getServ ngRangeS nceT  SecondsFromEpoch() {
    return useOverr deT erConf g
      ? t er nfo.getServ ngRangeOverr deS nceT  SecondsFromEpoch()
      : t er nfo.getServ ngRangeS nceT  SecondsFromEpoch();
  }

  publ c long getServ ngRangeUnt lT  SecondsFromEpoch() {
    return useOverr deT erConf g
      ? t er nfo.getServ ngRangeOverr deUnt lT  SecondsFromEpoch()
      : t er nfo.getServ ngRangeUnt lT  SecondsFromEpoch();
  }

  publ c stat c boolean serv ngRangesOverlap(T er nfoWrapper t er1, T er nfoWrapper t er2) {
    return (t er1.getServ ngRangeMax d() > t er2.getServ ngRangeS nce d())
      && (t er2.getServ ngRangeMax d() > t er1.getServ ngRangeS nce d());
  }

  publ c stat c boolean serv ngRangesHaveGap(T er nfoWrapper t er1, T er nfoWrapper t er2) {
    return (t er1.getServ ngRangeMax d() < t er2.getServ ngRangeS nce d())
      || (t er2.getServ ngRangeMax d() < t er1.getServ ngRangeS nce d());
  }
}
