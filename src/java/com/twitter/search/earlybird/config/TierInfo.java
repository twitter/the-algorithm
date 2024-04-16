package com.tw ter.search.earlyb rd.conf g;

 mport java.ut l.Date;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.ut l.Clock;

/**
 * Propert es of a s ngle t er.
 */
publ c class T er nfo  mple nts Serv ngRange {
  // What  'm see ng  tor cally  s that t  has been used w n add ng a new t er. F rst  
  // add   and send dark traff c to  , t n poss bly grey and t n   launch   by turn ng on
  // l ght traff c.
  publ c stat c enum RequestReadType {
    // L ght read: send request, wa  for results, and results are returned
    L GHT,
    // Dark read: send request, do not wa  for results, and results are d scarded
    DARK,
    // Grey read: send request, wa  for results, but d scard after results co  back.
    // Sa  results as dark read; s m lar latency as l ght read.
    GREY,
  }

  pr vate f nal Str ng t erNa ;
  pr vate f nal Date dataStartDate;
  pr vate f nal Date dataEndDate;
  pr vate f nal  nt numPart  ons;
  pr vate f nal  nt maxT  sl ces;
  pr vate f nal T erServ ngBoundaryEndPo nt serv ngRangeS nce;
  pr vate f nal T erServ ngBoundaryEndPo nt serv ngRangeMax;
  pr vate f nal T erServ ngBoundaryEndPo nt serv ngRangeS nceOverr de;
  pr vate f nal T erServ ngBoundaryEndPo nt serv ngRangeMaxOverr de;

  // T se two propert es are only used by cl ents of Earlyb rd (E.g. roots),
  // but not by Earlyb rds.
  pr vate f nal boolean enabled;
  pr vate f nal RequestReadType readType;
  pr vate f nal RequestReadType readTypeOverr de;

  publ c T er nfo(Str ng t erNa ,
                  Date dataStartDate,
                  Date dataEndDate,
                   nt numPart  ons,
                   nt maxT  sl ces,
                  boolean enabled,
                  Str ng s nce dStr ng,
                  Str ng max dStr ng,
                  Date serv ngStartDateOverr de,
                  Date serv ngEndDateOverr de,
                  RequestReadType readType,
                  RequestReadType readTypeOverr de,
                  Clock clock) {
    Precond  ons.c ckArgu nt(numPart  ons > 0);
    Precond  ons.c ckArgu nt(maxT  sl ces > 0);
    t .t erNa  = t erNa ;
    t .dataStartDate = dataStartDate;
    t .dataEndDate = dataEndDate;
    t .numPart  ons = numPart  ons;
    t .maxT  sl ces = maxT  sl ces;
    t .enabled = enabled;
    t .readType = readType;
    t .readTypeOverr de = readTypeOverr de;
    t .serv ngRangeS nce = T erServ ngBoundaryEndPo nt
        .newT erServ ngBoundaryEndPo nt(s nce dStr ng, dataStartDate, clock);
    t .serv ngRangeMax = T erServ ngBoundaryEndPo nt
        .newT erServ ngBoundaryEndPo nt(max dStr ng, dataEndDate, clock);
     f (serv ngStartDateOverr de != null) {
      t .serv ngRangeS nceOverr de = T erServ ngBoundaryEndPo nt.newT erServ ngBoundaryEndPo nt(
          T erServ ngBoundaryEndPo nt. NFERRED_FROM_DATA_RANGE, serv ngStartDateOverr de, clock);
    } else {
      t .serv ngRangeS nceOverr de = serv ngRangeS nce;
    }

     f (serv ngEndDateOverr de != null) {
      t .serv ngRangeMaxOverr de = T erServ ngBoundaryEndPo nt.newT erServ ngBoundaryEndPo nt(
          T erServ ngBoundaryEndPo nt. NFERRED_FROM_DATA_RANGE, serv ngEndDateOverr de, clock);
    } else {
      t .serv ngRangeMaxOverr de = serv ngRangeMax;
    }
  }

  @V s bleForTest ng
  publ c T er nfo(Str ng t erNa ,
                  Date dataStartDate,
                  Date dataEndDate,
                   nt numPart  ons,
                   nt maxT  sl ces,
                  boolean enabled,
                  Str ng s nce dStr ng,
                  Str ng max dStr ng,
                  RequestReadType readType,
                  Clock clock) {
    // No overr des:
    //   serv ngRangeS nceOverr de == serv ngRangeS nce
    //   serv ngRangeMaxOverr de == serv ngRangeMax
    //   readTypeOverr de == readType
    t (t erNa , dataStartDate, dataEndDate, numPart  ons, maxT  sl ces, enabled, s nce dStr ng,
         max dStr ng, null, null, readType, readType, clock);
  }

  @Overr de
  publ c Str ng toStr ng() {
    return t erNa ;
  }

  publ c Str ng getT erNa () {
    return t erNa ;
  }

  publ c Date getDataStartDate() {
    return dataStartDate;
  }

  publ c Date getDataEndDate() {
    return dataEndDate;
  }

  publ c  nt getNumPart  ons() {
    return numPart  ons;
  }

  publ c  nt getMaxT  sl ces() {
    return maxT  sl ces;
  }

  publ c T erConf g.Conf gS ce getS ce() {
    return T erConf g.getT erConf gS ce();
  }

  publ c boolean  sEnabled() {
    return enabled;
  }

  publ c boolean  sDarkRead() {
    return readType == RequestReadType.DARK;
  }

  publ c RequestReadType getReadType() {
    return readType;
  }

  publ c RequestReadType getReadTypeOverr de() {
    return readTypeOverr de;
  }

  publ c long getServ ngRangeS nce d() {
    return serv ngRangeS nce.getBoundaryT et d();
  }

  publ c long getServ ngRangeMax d() {
    return serv ngRangeMax.getBoundaryT et d();
  }

  long getServ ngRangeOverr deS nce d() {
    return serv ngRangeS nceOverr de.getBoundaryT et d();
  }

  long getServ ngRangeOverr deMax d() {
    return serv ngRangeMaxOverr de.getBoundaryT et d();
  }

  publ c long getServ ngRangeS nceT  SecondsFromEpoch() {
    return serv ngRangeS nce.getBoundaryT  SecondsFromEpoch();
  }

  publ c long getServ ngRangeUnt lT  SecondsFromEpoch() {
    return serv ngRangeMax.getBoundaryT  SecondsFromEpoch();
  }

  long getServ ngRangeOverr deS nceT  SecondsFromEpoch() {
    return serv ngRangeS nceOverr de.getBoundaryT  SecondsFromEpoch();
  }

  long getServ ngRangeOverr deUnt lT  SecondsFromEpoch() {
    return serv ngRangeMaxOverr de.getBoundaryT  SecondsFromEpoch();
  }
}
