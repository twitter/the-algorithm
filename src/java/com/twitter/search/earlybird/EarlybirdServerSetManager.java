package com.tw ter.search.earlyb rd;

 mport java.net. netAddress;
 mport java.net. netSocketAddress;
 mport java.ut l.concurrent.atom c.Atom cLong;

 mport javax.annotat on.concurrent.GuardedBy;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.Maps;

 mport org.apac .zookeeper.KeeperExcept on;
 mport org.apac .zookeeper.Watc r;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.zookeeper.ServerSet;
 mport com.tw ter.common.zookeeper.ZooKeeperCl ent;
 mport com.tw ter.common_ nternal.zookeeper.Tw terServerSet;
 mport com.tw ter.search.common.conf g.Conf g;
 mport com.tw ter.search.common.database.DatabaseConf g;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common.ut l.zookeeper.ZooKeeperProxy;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;
 mport com.tw ter.search.earlyb rd.conf g.T erConf g;
 mport com.tw ter.search.earlyb rd.except on.Already nServerSetUpdateExcept on;
 mport com.tw ter.search.earlyb rd.except on.Not nServerSetUpdateExcept on;
 mport com.tw ter.search.earlyb rd.part  on.Part  onConf g;

publ c class Earlyb rdServerSetManager  mple nts ServerSet mber {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdServerSetManager.class);

  // How many t  s t  earlyb rd jo ned/left  s part  on's server set
  @V s bleForTest ng
  protected f nal SearchCounter leaveServerSetCounter;
  @V s bleForTest ng
  protected f nal SearchCounter jo nServerSetCounter;
  pr vate f nal ZooKeeperProxy d scoveryZKCl ent;
  pr vate f nal SearchLongGauge  nServerSetGauge;
  pr vate f nal Part  onConf g part  onConf g;
  pr vate f nal  nt port;
  pr vate f nal Str ng serverSetNa Pref x;

  @V s bleForTest ng
  protected f nal SearchLongGauge connectedToZooKeeper;

  pr vate f nal Object endpo ntStatusLock = new Object();
  @GuardedBy("endpo ntStatusLock")
  pr vate ServerSet.Endpo ntStatus endpo ntStatus = null;

  pr vate boolean  nServerSetForServ ceProxy = false;

  publ c Earlyb rdServerSetManager(
      SearchStatsRece ver searchStatsRece ver,
      ZooKeeperProxy d scoveryZKCl ent,
      f nal Part  onConf g part  onConf g,
       nt port,
      Str ng serverSetNa Pref x) {
    t .d scoveryZKCl ent = d scoveryZKCl ent;
    t .part  onConf g = part  onConf g;
    t .port = port;
    t .serverSetNa Pref x = serverSetNa Pref x;

    // Export serverset related stats
    Precond  ons.c ckNotNull(searchStatsRece ver);
    t .jo nServerSetCounter = searchStatsRece ver.getCounter(
        serverSetNa Pref x + "jo n_server_set_count");
    t .leaveServerSetCounter = searchStatsRece ver.getCounter(
        serverSetNa Pref x + "leave_server_set_count");

    // Create a new stat based on t  part  on number for hosts- n-part  on aggregat on.
    // T  value of t  stat  s dependent on w t r t  server  s  n t  serverset so that t 
    // aggregate stat reflects t  number serv ng traff c  nstead of t  l ve process count.
    Atom cLong shared nServerSetStatus = new Atom cLong();
    t . nServerSetGauge = searchStatsRece ver.getLongGauge(
        serverSetNa Pref x + " s_ n_server_set", shared nServerSetStatus);
    t .connectedToZooKeeper = searchStatsRece ver.getLongGauge(
        serverSetNa Pref x + "connected_to_zookeeper");

    searchStatsRece ver.getLongGauge(
        serverSetNa Pref x + " mber_of_part  on_" + part  onConf g.get ndex ngHashPart  on D(),
        shared nServerSetStatus);

    t .d scoveryZKCl ent.reg sterExp rat onHandler(() -> connectedToZooKeeper.set(0));

    t .d scoveryZKCl ent.reg ster(event -> {
       f (event.getType() == Watc r.Event.EventType.None
          && event.getState() == Watc r.Event.KeeperState.SyncConnected) {
        connectedToZooKeeper.set(1);
      }
    });
  }

  /**
   * Jo n ServerSet and update endpo ntStatus.
   * T  w ll allow Earlyb rd consu rs, e.g. Blender, to detect w n an
   * Earlyb rd goes onl ne and offl ne.
   * @param userna 
   */
  @Overr de
  publ c vo d jo nServerSet(Str ng userna ) throws ServerSet.UpdateExcept on {
    jo nServerSetCounter. ncre nt();

    synchron zed (endpo ntStatusLock) {
      LOG. nfo("Jo n ng {} ServerSet ( nstructed by: {}) ...", serverSetNa Pref x, userna );
       f (endpo ntStatus != null) {
        LOG.warn("Already  n ServerSet. Noth ng done.");
        throw new Already nServerSetUpdateExcept on("Already  n ServerSet. Noth ng done.");
      }

      try {
        Tw terServerSet.Serv ce serv ce = getServerSetServ ce();

        ServerSet serverSet = d scoveryZKCl ent.createServerSet(serv ce);
        endpo ntStatus = serverSet.jo n(
            new  netSocketAddress( netAddress.getLocalHost().getHostNa (), port),
            Maps.newHashMap(),
            part  onConf g.getHostPos  onW h nHashPart  on());

         nServerSetGauge.set(1);

        Str ng path = serv ce.getPath();
        Earlyb rdStatus.recordEarlyb rdEvent("Jo ned " + serverSetNa Pref x + " ServerSet " + path
                                             + " ( nstructed by: " + userna  + ")");
        LOG. nfo("Successfully jo ned {} ServerSet {} ( nstructed by: {})",
                 serverSetNa Pref x, path, userna );
      } catch (Except on e) {
        endpo ntStatus = null;
        Str ng  ssage = "Fa led to jo n " + serverSetNa Pref x + " ServerSet of part  on "
            + part  onConf g.get ndex ngHashPart  on D();
        LOG.error( ssage, e);
        throw new ServerSet.UpdateExcept on( ssage, e);
      }
    }
  }

  /**
   * Takes t  Earlyb rd out of  s reg stered ServerSet.
   *
   * @throws ServerSet.UpdateExcept on  f t re was a problem leav ng t  ServerSet,
   * or  f t  Earlyb rd  s already not  n a ServerSet.
   * @param userna 
   */
  @Overr de
  publ c vo d leaveServerSet(Str ng userna ) throws ServerSet.UpdateExcept on {
    leaveServerSetCounter. ncre nt();
    synchron zed (endpo ntStatusLock) {
      LOG. nfo("Leav ng {} ServerSet ( nstructed by: {}) ...", serverSetNa Pref x, userna );
       f (endpo ntStatus == null) {
        Str ng  ssage = "Not  n a ServerSet. Noth ng done.";
        LOG.warn( ssage);
        throw new Not nServerSetUpdateExcept on( ssage);
      }

      endpo ntStatus.leave();
      endpo ntStatus = null;
       nServerSetGauge.set(0);
      Earlyb rdStatus.recordEarlyb rdEvent("Left " + serverSetNa Pref x
                                           + " ServerSet ( nstructed by: " + userna  + ")");
      LOG. nfo("Successfully left {} ServerSet. ( nstructed by: {})",
               serverSetNa Pref x, userna );
    }
  }

  @Overr de
  publ c  nt getNumberOfServerSet mbers()
      throws  nterruptedExcept on, ZooKeeperCl ent.ZooKeeperConnect onExcept on, KeeperExcept on {
    Str ng path = getServerSetServ ce().getPath();
    return d scoveryZKCl ent.getNumberOfServerSet mbers(path);
  }

  /**
   * Determ nes  f t  earlyb rd  s  n t  server set.
   */
  @Overr de
  publ c boolean  s nServerSet() {
    synchron zed (endpo ntStatusLock) {
      return endpo ntStatus != null;
    }
  }

  /**
   * Returns t  server set that t  earlyb rd should jo n.
   */
  publ c Str ng getServerSet dent f er() {
    Tw terServerSet.Serv ce serv ce = getServerSetServ ce();
    return Str ng.format("/cluster/local/%s/%s/%s",
                         serv ce.getRole(),
                         serv ce.getEnv(),
                         serv ce.getNa ());
  }

  pr vate Tw terServerSet.Serv ce getServerSetServ ce() {
    //  f t  t er na   s 'all' t n   treat   as an unt ered EB cluster
    // and do not add t  t er component  nto t  ZK path   reg sters under.
    Str ng t erZKPathComponent = "";
     f (!T erConf g.DEFAULT_T ER_NAME.equals gnoreCase(part  onConf g.getT erNa ())) {
      t erZKPathComponent = "/" + part  onConf g.getT erNa ();
    }
     f (Earlyb rdConf g. sAurora()) {
      // ROLE, EARYLB RD_NAME, and ENV propert es are requ red on Aurora, thus w ll be set  re
      return new Tw terServerSet.Serv ce(
          Earlyb rdProperty.ROLE.get(),
          Earlyb rdProperty.ENV.get(),
          getServerSetPath(Earlyb rdProperty.EARLYB RD_NAME.get() + t erZKPathComponent));
    } else {
      return new Tw terServerSet.Serv ce(
          DatabaseConf g.getZooKeeperRole(),
          Conf g.getEnv ron nt(),
          getServerSetPath("earlyb rd" + t erZKPathComponent));
    }
  }

  pr vate Str ng getServerSetPath(Str ng earlyb rdNa ) {
    return Str ng.format("%s%s/hash_part  on_%d", serverSetNa Pref x, earlyb rdNa ,
        part  onConf g.get ndex ngHashPart  on D());
  }

  /**
   * Jo n ServerSet for Serv ceProxy w h a na d adm n port and w h a zookeeper path that Serv ce
   * Proxy can translate to a doma n na  label that  s less than 64 characters (due to t  s ze
   * l m  for doma n na  labels descr bed  re: https://tools. etf.org/html/rfc1035)
   * T  w ll allow us to access Earlyb rds that are not on  sos v a Serv ceProxy.
   */
  @Overr de
  publ c vo d jo nServerSetForServ ceProxy() {
    // T  add  onal Zookeeper server set  s only necessary for Arch ve Earlyb rds wh ch are
    // runn ng on bare  tal hardware, so ensure that t   thod  s never called for serv ces
    // on Aurora.
    Precond  ons.c ckArgu nt(!Earlyb rdConf g. sAurora(),
        "Attempt ng to jo n server set for Serv ceProxy on Earlyb rd runn ng on Aurora");

    LOG. nfo("Attempt ng to jo n ServerSet for Serv ceProxy");
    try {
      Tw terServerSet.Serv ce serv ce = getServerSetForServ ceProxyOnArch ve();

      ServerSet serverSet = d scoveryZKCl ent.createServerSet(serv ce);
      Str ng hostNa  =  netAddress.getLocalHost().getHostNa ();
       nt adm nPort = Earlyb rdConf g.getAdm nPort();
      serverSet.jo n(
          new  netSocketAddress(hostNa , port),
           mmutableMap.of("adm n", new  netSocketAddress(hostNa , adm nPort)),
          part  onConf g.getHostPos  onW h nHashPart  on());

      Str ng path = serv ce.getPath();
      LOG. nfo("Successfully jo ned ServerSet for Serv ceProxy {}", path);
       nServerSetForServ ceProxy = true;
    } catch (Except on e) {
      Str ng  ssage = "Fa led to jo n ServerSet for Serv ceProxy of part  on "
          + part  onConf g.get ndex ngHashPart  on D();
      LOG.warn( ssage, e);
    }
  }

  @V s bleForTest ng
  protected Tw terServerSet.Serv ce getServerSetForServ ceProxyOnArch ve() {
    Str ng serverSetPath = Str ng.format("proxy/%s/p_%d",
        part  onConf g.getT erNa (),
        part  onConf g.get ndex ngHashPart  on D());
    return new Tw terServerSet.Serv ce(
        DatabaseConf g.getZooKeeperRole(),
        Conf g.getEnv ron nt(),
        serverSetPath);
  }

  @V s bleForTest ng
  protected boolean  s nServerSetForServ ceProxy() {
    return  nServerSetForServ ceProxy;
  }
}
