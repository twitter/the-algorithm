package com.tw ter.search.earlyb rd.part  on;

 mport java.ut l.Date;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.apac .commons.lang3.bu lder.ToStr ngBu lder;

 mport com.tw ter.search.common.conf g.Conf g;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.conf g.T erConf g;

publ c class Part  onConf g {
  // Wh ch sub-cluster t  host belongs to
  pr vate f nal Str ng t erNa ;

  // Wh ch cluster t  host belongs to
  pr vate f nal Str ng clusterNa ;

  publ c stat c f nal Str ng DEFAULT_T ER_NAME = "all";

  // t  date range of t  t  sl ces t  t er w ll load. T  start date  s  nclus ve, wh le
  // t  end date  s exclus ve.
  pr vate f nal Date t erStartDate;
  pr vate f nal Date t erEndDate;

  pr vate f nal  nt  ndex ngHashPart  on D; // Hash Part  on  D ass gned for t  EB
  pr vate f nal  nt maxEnabledLocalSeg nts; // Number of seg nts to keep
  // T  pos  on of t  host  n t  ordered l st of hosts serv ng t  hash part  on
  pr vate f nal  nt hostPos  onW h nHashPart  on;
  pr vate volat le  nt numRepl cas nHashPart  on;

  pr vate f nal  nt numPart  ons; // Total number of part  ons  n t  current cluster

  publ c Part  onConf g(
       nt  ndex ngHashPart  on D,
       nt maxEnabledLocalSeg nts,
       nt hostPos  onW h nHashPart  on,
       nt numRepl cas nHashPart  on,
       nt numPart  ons) {
    t (DEFAULT_T ER_NAME,
        T erConf g.DEFAULT_T ER_START_DATE,
        T erConf g.DEFAULT_T ER_END_DATE,
         ndex ngHashPart  on D,
        maxEnabledLocalSeg nts,
        hostPos  onW h nHashPart  on,
        numRepl cas nHashPart  on,
        numPart  ons);
  }

  publ c Part  onConf g(Str ng t erNa ,
                         Date t erStartDate,
                         Date t erEndDate,
                          nt  ndex ngHashPart  on D,
                          nt maxEnabledLocalSeg nts,
                          nt hostPos  onW h nHashPart  on,
                          nt numRepl cas nHashPart  on,
                          nt numPart  ons) {
    t (t erNa , t erStartDate, t erEndDate,  ndex ngHashPart  on D, maxEnabledLocalSeg nts,
        hostPos  onW h nHashPart  on, numRepl cas nHashPart  on, Conf g.getEnv ron nt(),
        numPart  ons);
  }

  publ c Part  onConf g(Str ng t erNa ,
                         Date t erStartDate,
                         Date t erEndDate,
                          nt  ndex ngHashPart  on D,
                          nt maxEnabledLocalSeg nts,
                          nt hostPos  onW h nHashPart  on,
                          nt numRepl cas nHashPart  on,
                         Str ng clusterNa ,
                          nt numPart  ons) {
    t .t erNa  = Precond  ons.c ckNotNull(t erNa );
    t .clusterNa  = Precond  ons.c ckNotNull(clusterNa );
    t .t erStartDate = Precond  ons.c ckNotNull(t erStartDate);
    t .t erEndDate = Precond  ons.c ckNotNull(t erEndDate);
    t . ndex ngHashPart  on D =  ndex ngHashPart  on D;
    t .maxEnabledLocalSeg nts = maxEnabledLocalSeg nts;
    t .hostPos  onW h nHashPart  on = hostPos  onW h nHashPart  on;
    t .numRepl cas nHashPart  on = numRepl cas nHashPart  on;
    t .numPart  ons = numPart  ons;
  }

  publ c Str ng getT erNa () {
    return t erNa ;
  }

  publ c Str ng getClusterNa () {
    return clusterNa ;
  }

  publ c Date getT erStartDate() {
    return t erStartDate;
  }

  publ c Date getT erEndDate() {
    return t erEndDate;
  }

  publ c  nt get ndex ngHashPart  on D() {
    return  ndex ngHashPart  on D;
  }

  publ c  nt getMaxEnabledLocalSeg nts() {
    return maxEnabledLocalSeg nts;
  }

  publ c  nt getHostPos  onW h nHashPart  on() {
    return hostPos  onW h nHashPart  on;
  }

  publ c  nt getNumRepl cas nHashPart  on() {
    return numRepl cas nHashPart  on;
  }

  /**
   * T  number of ways t  T et and/or user data  s part  oned (or sharded)  n t  Earlyb rd,  n
   * t  t er.
   */
  publ c  nt getNumPart  ons() {
    return numPart  ons;
  }

  publ c Str ng getPart  onConf gDescr pt on() {
    return ToStr ngBu lder.reflect onToStr ng(t );
  }

  publ c vo d setNumRepl cas nHashPart  on( nt numRepl cas) {
    numRepl cas nHashPart  on = numRepl cas;
  }

  publ c stat c f nal  nt DEFAULT_NUM_SERV NG_T MESL CES_FOR_TEST = 18;
  publ c stat c Part  onConf g getPart  onConf gForTests() {
    return getPart  onConf gForTests(
        T erConf g.DEFAULT_T ER_START_DATE,
        T erConf g.DEFAULT_T ER_END_DATE);
  }

  publ c stat c Part  onConf g getPart  onConf gForTests(Date t erStartDate, Date t erEndDate) {
    return getPart  onConf gForTests(
        DEFAULT_NUM_SERV NG_T MESL CES_FOR_TEST, t erStartDate, t erEndDate, 1);
  }

  /**
   * Returns a Part  onConf g  nstance conf gured for tests.
   *
   * @param numServ ngT  sl ces T  number of t  sl ces that should be served.
   * @param t erStartDate T  t er's start date. Used only  n t  full arch ve earlyb rds.
   * @param t erEndDate T  t er's end date. Used only by  n t  full arch ve earlyb rds.
   * @param numRepl cas nHashPart  on T  number of repl cas for each part  on.
   * @return A Part  onConf g  nstance conf gured for tests.
   */
  @V s bleForTest ng
  publ c stat c Part  onConf g getPart  onConf gForTests(
       nt numServ ngT  sl ces,
      Date t erStartDate,
      Date t erEndDate,
       nt numRepl cas nHashPart  on) {
    return new Part  onConf g(
        Earlyb rdConf g.getStr ng("sub_t ers_for_tests", "test"),
        t erStartDate,
        t erEndDate,
        Earlyb rdConf g.get nt("hash_part  on_for_tests", -1),
        numServ ngT  sl ces,
        0, // hostPos  onW h nHashPart  on
        numRepl cas nHashPart  on,
        Earlyb rdConf g.get nt("num_part  ons_for_tests", -1)
    );
  }
}
