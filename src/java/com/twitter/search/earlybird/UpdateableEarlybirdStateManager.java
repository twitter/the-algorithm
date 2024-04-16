package com.tw ter.search.earlyb rd;

 mport java. o.F le;
 mport java. o. OExcept on;
 mport java.ut l.Random;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.concurrent.atom c.Atom cLong;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Charsets;

 mport org.apac .thr ft.TExcept on;
 mport org.apac .zookeeper.KeeperExcept on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.common.zookeeper.ZooKeeperCl ent;
 mport com.tw ter.search.common.aurora.AuroraSc dulerCl ent;
 mport com.tw ter.search.common.concurrent.Sc duledExecutorServ ceFactory;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.f le.LocalF le;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common.sc ma.AnalyzerFactory;
 mport com.tw ter.search.common.sc ma.Dynam cSc ma;
 mport com.tw ter.search.common.sc ma. mmutableSc ma;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftSc ma;
 mport com.tw ter.search.common.ut l.ml.tensorflow_eng ne.TensorflowModelsManager;
 mport com.tw ter.search.common.ut l.thr ft.Thr ftUt ls;
 mport com.tw ter.search.common.ut l.zookeeper.ZooKeeperProxy;
 mport com.tw ter.search.earlyb rd.common.NonPag ngAssert;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.ml.Scor ngModelsManager;
 mport com.tw ter.search.earlyb rd.part  on.Dynam cPart  onConf g;
 mport com.tw ter.search.earlyb rd.part  on.Part  onConf g;
 mport com.tw ter.search.earlyb rd.part  on.Part  onConf gLoader;
 mport com.tw ter.search.earlyb rd.part  on.Part  onConf gLoad ngExcept on;
 mport com.tw ter.search.earlyb rd.ut l.OneTaskSc duledExecutorManager;
 mport com.tw ter.search.earlyb rd.ut l.Per od cAct onParams;
 mport com.tw ter.search.earlyb rd.ut l.ShutdownWa T  Params;

/**
 * A class that keeps track of Earlyb rd state that may change wh le an Earlyb rd runs, and keeps
 * that state up to date. Currently keeps track of t  current Earlyb rd sc ma and part  on
 * conf gurat on, and per od cally updates t m from Zookeeper.   also reloads per od cally t 
 * scor ng models from HDFS.
 */
publ c class UpdateableEarlyb rdStateManager extends OneTaskSc duledExecutorManager {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(UpdateableEarlyb rdStateManager.class);
  publ c stat c f nal Str ng SCHEMA_SUFF X = ".sc ma.v";

  pr vate stat c f nal Str ng THREAD_NAME_PATTERN = "state_update-%d";
  pr vate stat c f nal boolean THREAD_ S_DAEMON = true;
  pr vate stat c f nal long EXECUTOR_SHUTDOWN_WA T_SEC = 5;

  pr vate stat c f nal Str ng DEFAULT_ZK_SCHEMA_LOCAT ON =
      "/tw ter/search/product on/earlyb rd/sc ma";
  pr vate stat c f nal Str ng DEFAULT_LOCAL_SCHEMA_LOCAT ON =
      "/ho /search/earlyb rd_sc ma_canary";
  pr vate stat c f nal long DEFAULT_UPDATE_PER OD_M LL S =
      T  Un .M NUTES.toM ll s(30);

  pr vate stat c f nal Str ng SCHEMA_MAJOR_VERS ON_NAME =
      "sc ma_major_vers on";
  pr vate stat c f nal Str ng SCHEMA_M NOR_VERS ON_NAME =
      "sc ma_m nor_vers on";
  pr vate stat c f nal Str ng LAST_SUCCESSFUL_SCHEMA_RELOAD_T ME_M LL S_NAME =
      "last_successful_sc ma_reload_t  stamp_m ll s";
  @V s bleForTest ng
  stat c f nal Str ng FA L_TO_LOAD_SCHEMA_COUNT_NAME =
      "fa l_to_load_sc ma_count";
  @V s bleForTest ng
  stat c f nal Str ng HOST_ S_CANARY_SCHEME = "host_ s_canary_sc ma";
  @V s bleForTest ng
  stat c f nal Str ng D D_NOT_F ND_SCHEMA_COUNT_NAME =
      "d d_not_f nd_sc ma_count";
  pr vate stat c f nal Str ng LAST_SUCCESSFUL_PART T ON_CONF G_RELOAD_T ME_M LL S_NAME =
      "last_successful_part  on_conf g_reload_t  stamp_m ll s";
  @V s bleForTest ng
  stat c f nal Str ng FA L_TO_LOAD_PART T ON_CONF G_COUNT_NAME =
      "fa l_to_load_part  on_conf g_count";
  @V s bleForTest ng
  stat c f nal Str ng HOST_ S_ N_LAYOUT_STAT_NAME = "host_ s_ n_la t";
  pr vate stat c f nal Str ng NOT_ N_LAYOUT_SHUT_DOWN_ATTEMPTED_NAME =
      "not_ n_la t_shut_down_attempted";

  pr vate stat c f nal Str ng SHUT_DOWN_EARLYB RD_WHEN_NOT_ N_LAYOUT_DEC DER_KEY =
      "shut_down_earlyb rd_w n_not_ n_la t";

  pr vate stat c f nal Str ng NO_SHUTDOWN_WHEN_NOT_ N_LAYOUT_NAME =
      "no_shutdown_w n_not_ n_la t";

  pr vate f nal SearchLongGauge sc maMajorVers on;
  pr vate f nal SearchLongGauge sc maM norVers on;
  pr vate f nal SearchLongGauge lastSuccessfulSc maReloadT  M ll s;
  pr vate f nal SearchCounter fa lToLoadSc maCount;
  pr vate f nal SearchLongGauge host sCanarySc ma;
  pr vate f nal SearchCounter d dNotF ndSc maCount;
  pr vate f nal SearchLongGauge lastSuccessfulPart  onConf gReloadT  M ll s;
  pr vate f nal SearchCounter fa lToLoadPart  onConf gCount;
  pr vate f nal SearchLongGauge host s nLa t;
  pr vate f nal SearchCounter not nLa tShutDownAttemptedCount;
  pr vate f nal SearchLongGauge noShutdownW nNot nLa tGauge;

  pr vate f nal Earlyb rd ndexConf g  ndexConf g;
  pr vate f nal Dynam cPart  onConf g part  onConf g;
  pr vate f nal Str ng sc maLocat onOnLocal;
  pr vate f nal Str ng sc maLocat onOnZK;
  pr vate f nal ZooKeeperProxy zkCl ent;
  pr vate f nal AuroraSc dulerCl ent sc dulerCl ent;
  pr vate f nal Scor ngModelsManager scor ngModelsManager;
  pr vate f nal TensorflowModelsManager tensorflowModelsManager;
  pr vate f nal SearchDec der searchDec der;
  pr vate f nal Atom cLong noShutdownW nNot nLa t;
  pr vate Earlyb rdServer earlyb rdServer;
  pr vate Clock clock;

  publ c UpdateableEarlyb rdStateManager(
      Earlyb rd ndexConf g  ndexConf g,
      Dynam cPart  onConf g part  onConf g,
      ZooKeeperProxy zooKeeperCl ent,
      @Nullable  AuroraSc dulerCl ent sc dulerCl ent,
      Sc duledExecutorServ ceFactory executorServ ceFactory,
      Scor ngModelsManager scor ngModelsManager,
      TensorflowModelsManager tensorflowModelsManager,
      SearchStatsRece ver searchStatsRece ver,
      SearchDec der searchDec der,
      Cr  calExcept onHandler cr  calExcept onHandler,
      Clock clock) {
    t (
         ndexConf g,
        part  onConf g,
        DEFAULT_LOCAL_SCHEMA_LOCAT ON,
        DEFAULT_ZK_SCHEMA_LOCAT ON,
        DEFAULT_UPDATE_PER OD_M LL S,
        zooKeeperCl ent,
        sc dulerCl ent,
        executorServ ceFactory,
        scor ngModelsManager,
        tensorflowModelsManager,
        searchStatsRece ver,
        searchDec der,
        cr  calExcept onHandler,
        clock);
  }

  protected UpdateableEarlyb rdStateManager(
      Earlyb rd ndexConf g  ndexConf g,
      Dynam cPart  onConf g part  onConf g,
      Str ng sc maLocat onOnLocal,
      Str ng sc maLocat onOnZK,
      long updatePer odM ll s,
      ZooKeeperProxy zkCl ent,
      @Nullable  AuroraSc dulerCl ent sc dulerCl ent,
      Sc duledExecutorServ ceFactory executorServ ceFactory,
      Scor ngModelsManager scor ngModelsManager,
      TensorflowModelsManager tensorflowModelsManager,
      SearchStatsRece ver searchStatsRece ver,
      SearchDec der searchDec der,
      Cr  calExcept onHandler cr  calExcept onHandler,
      Clock clock) {
    super(
        executorServ ceFactory,
        THREAD_NAME_PATTERN,
        THREAD_ S_DAEMON,
        Per od cAct onParams.w hF xedDelay(
          updatePer odM ll s,
          T  Un .M LL SECONDS
        ),
        new ShutdownWa T  Params(
          EXECUTOR_SHUTDOWN_WA T_SEC,
          T  Un .SECONDS
        ),
        searchStatsRece ver,
        cr  calExcept onHandler);
    t . ndexConf g =  ndexConf g;
    t .part  onConf g = part  onConf g;
    t .sc maLocat onOnLocal = sc maLocat onOnLocal;
    t .sc maLocat onOnZK = sc maLocat onOnZK;
    t .zkCl ent = zkCl ent;
    t .sc dulerCl ent = sc dulerCl ent;
    t .scor ngModelsManager = scor ngModelsManager;
    t .searchDec der = searchDec der;
    t .noShutdownW nNot nLa t = new Atom cLong(0);
    t .tensorflowModelsManager = tensorflowModelsManager;
    t .clock = clock;
    t .sc maMajorVers on = getSearchStatsRece ver().getLongGauge(
        SCHEMA_MAJOR_VERS ON_NAME);
    t .sc maM norVers on = getSearchStatsRece ver().getLongGauge(
        SCHEMA_M NOR_VERS ON_NAME);
    t .lastSuccessfulSc maReloadT  M ll s = getSearchStatsRece ver().getLongGauge(
        LAST_SUCCESSFUL_SCHEMA_RELOAD_T ME_M LL S_NAME);
    t .fa lToLoadSc maCount = getSearchStatsRece ver().getCounter(
        FA L_TO_LOAD_SCHEMA_COUNT_NAME);
    t .host sCanarySc ma = getSearchStatsRece ver().getLongGauge(HOST_ S_CANARY_SCHEME);
    t .d dNotF ndSc maCount = getSearchStatsRece ver().getCounter(
        D D_NOT_F ND_SCHEMA_COUNT_NAME);
    t .lastSuccessfulPart  onConf gReloadT  M ll s = getSearchStatsRece ver().getLongGauge(
        LAST_SUCCESSFUL_PART T ON_CONF G_RELOAD_T ME_M LL S_NAME);
    t .fa lToLoadPart  onConf gCount = getSearchStatsRece ver().getCounter(
        FA L_TO_LOAD_PART T ON_CONF G_COUNT_NAME);
    t .host s nLa t = getSearchStatsRece ver().getLongGauge(
        HOST_ S_ N_LAYOUT_STAT_NAME);
    t .not nLa tShutDownAttemptedCount = getSearchStatsRece ver().getCounter(
        NOT_ N_LAYOUT_SHUT_DOWN_ATTEMPTED_NAME);
    t .noShutdownW nNot nLa tGauge = getSearchStatsRece ver().getLongGauge(
        NO_SHUTDOWN_WHEN_NOT_ N_LAYOUT_NAME, noShutdownW nNot nLa t);

    updateSc maVers onStats( ndexConf g.getSc ma());
  }

  pr vate vo d updateSc maVers onStats(Sc ma sc ma) {
    sc maMajorVers on.set(sc ma.getMajorVers onNumber());
    sc maM norVers on.set(sc ma.getM norVers onNumber());
    lastSuccessfulSc maReloadT  M ll s.set(System.currentT  M ll s());
    lastSuccessfulPart  onConf gReloadT  M ll s.set(System.currentT  M ll s());
    host s nLa t.set(1);
  }

  pr vate vo d updateSc maVers onW hThr ftSc ma(Thr ftSc ma thr ftSc ma)
      throws Sc ma.Sc maVal dat onExcept on, Dynam cSc ma.Sc maUpdateExcept on {

       mmutableSc ma newSc ma = new  mmutableSc ma(
          thr ftSc ma, new AnalyzerFactory(),  ndexConf g.getCluster().getNa ForStats());
       ndexConf g.getSc ma().updateSc ma(newSc ma);
      tensorflowModelsManager.updateFeatureSc ma dToMl dMap(newSc ma.getSearchFeatureSc ma());
      updateSc maVers onStats( ndexConf g.getSc ma());
      LOG. nfo("Sc ma updated. New Sc ma  s: \n" + Thr ftUt ls.toTextFormatSafe(thr ftSc ma));
  }

  protected vo d updateSc ma(ZooKeeperProxy zkCl entToUse) {
    // T re are 3 cases:
    // 1. Try to locate local sc ma f le to canary,   m ght fa l e  r because f le not ex st or
    //  nel g ble vers ons.
    // 2. Canary local sc ma fa led, lookup sc ma f le from zookeeper.
    // 3. Both local and zookeeper updates fa led,   do not update sc ma. E  r sc ma not ex sts
    //  n zookeeper, or t  would happened after canary sc ma:   updated current sc ma but d d
    // not rollback after f n s d.
     f (updateSc maFromLocal()) {
      LOG. nfo("Host  s used for sc ma canary");
      host sCanarySc ma.set(1);
    } else  f (updateSc maFromZooKeeper(zkCl entToUse)) {
      // Host  s us ng sc ma f le from zookeeper
      host sCanarySc ma.set(0);
    } else {
      // Sc ma update fa led. Please c ck sc ma f le ex sts on zookeeper and make sure
      // rollback after canary. Current vers on: {}.{}
      return;
    }
  }

  pr vate boolean updateSc maFromLocal() {
    Thr ftSc ma thr ftSc ma =
        loadCanaryThr ftSc maFromLocal(getCanarySc maF leOnLocal());
     f (thr ftSc ma == null) {
      //    s expected to not f nd a local sc ma f le. T  sc ma f le only ex sts w n t  host
      //  s used as canary for sc ma updates
      return false;
    }
    return updateSc maFromThr ftSc ma(thr ftSc ma);
  }

  pr vate boolean updateSc maFromZooKeeper(ZooKeeperProxy zkCl entToUse) {
    Thr ftSc ma thr ftSc ma = loadThr ftSc maFromZooKeeper(zkCl entToUse);
     f (thr ftSc ma == null) {
      //    s expected to usually not f nd a sc ma f le on ZooKeeper; one  s only uploaded  f t 
      // sc ma changes after t  package has been comp led. All t  relevant error handl ng and
      // logg ng  s expected to be handled by loadThr ftSc maFromZooKeeper().
      fa lToLoadSc maCount. ncre nt();
      return false;
    }
    return updateSc maFromThr ftSc ma(thr ftSc ma);
  }

  pr vate boolean updateSc maFromThr ftSc ma(Thr ftSc ma thr ftSc ma) {
    Sc ma currentSc ma =  ndexConf g.getSc ma();
     f (thr ftSc ma.getMajorVers onNumber() != currentSc ma.getMajorVers onNumber()) {
      LOG.warn(
          "Major vers on updates are not allo d. Current major vers on {}, try to update to {}",
          currentSc ma.getMajorVers onNumber(), thr ftSc ma.getMajorVers onNumber());
      return false;
    }
     f (thr ftSc ma.getM norVers onNumber() > currentSc ma.getM norVers onNumber()) {
      try {
        updateSc maVers onW hThr ftSc ma(thr ftSc ma);
      } catch (Sc ma.Sc maVal dat onExcept on | Dynam cSc ma.Sc maUpdateExcept on e) {
        LOG.warn("Except on wh le updat ng sc ma: ", e);
        return false;
      }
      return true;
    } else  f (thr ftSc ma.getM norVers onNumber() == currentSc ma.getM norVers onNumber()) {
      LOG. nfo("Sc ma vers on to update  s sa  as current one: {}.{}",
          currentSc ma.getMajorVers onNumber(), currentSc ma.getM norVers onNumber());
      return true;
    } else {
      LOG. nfo("Found sc ma to update, but not el g ble for dynam c update. "
              + "Current Vers on: {}.{};  Sc ma Vers on for updates: {}.{}",
          currentSc ma.getMajorVers onNumber(),
          currentSc ma.getM norVers onNumber(),
          thr ftSc ma.getMajorVers onNumber(),
          thr ftSc ma.getM norVers onNumber());
      return false;
    }
  }

  vo d updatePart  onConf g(@Nullable AuroraSc dulerCl ent sc dulerCl entToUse) {
    try {
       f (sc dulerCl entToUse == null) {
        NonPag ngAssert.assertFa led("aurora_sc duler_cl ent_ s_null");
        throw new Part  onConf gLoad ngExcept on("AuroraSc dulerCl ent can not be null.");
      }

      Part  onConf g newPart  onConf g =
          Part  onConf gLoader.getPart  on nfoFor sosConf g(sc dulerCl entToUse);
      part  onConf g.setCurrentPart  onConf g(newPart  onConf g);
      lastSuccessfulPart  onConf gReloadT  M ll s.set(System.currentT  M ll s());
      host s nLa t.set(1);
    } catch (Part  onConf gLoad ngExcept on e) {
      // Do not change host s nLa t's value  f   could not load t  la t.
      LOG.warn("Fa led to load part  on conf g from ZooKeeper.", e);
      fa lToLoadPart  onConf gCount. ncre nt();
    }
  }

  @Nullable
  pr vate Thr ftSc ma loadCanaryThr ftSc maFromLocal(LocalF le sc maF le) {
    Str ng sc maStr ng;
     f (!sc maF le.getF le().ex sts()) {
      return null;
    }
    try {
      sc maStr ng = sc maF le.getCharS ce().read();
    } catch ( OExcept on e) {
      LOG.warn("Fa l to read from local sc ma f le.");
      return null;
    }
    Thr ftSc ma thr ftSc ma = new Thr ftSc ma();
    try {
      Thr ftUt ls.fromTextFormat(sc maStr ng, thr ftSc ma);
      return thr ftSc ma;
    } catch (TExcept on e) {
      LOG.warn("Unable to deser al ze Thr ftSc ma loaded locally from {}.\n{}",
          sc maF le.getNa (), e);
      return null;
    }
  }

  @Nullable
  pr vate Thr ftSc ma loadThr ftSc maFromZooKeeper(ZooKeeperProxy zkCl entToUse) {
    Str ng sc maPathOnZk = getFullSc maPathOnZK();
    byte[] rawBytes;
    try {
      rawBytes = zkCl entToUse.getData(sc maPathOnZk, false, null);
    } catch (KeeperExcept on.NoNodeExcept on e) {
      d dNotF ndSc maCount. ncre nt();
      return null;
    } catch (KeeperExcept on e) {
      LOG.warn("Except on wh le load ng sc ma from ZK at {}.\n{}", sc maPathOnZk, e);
      return null;
    } catch ( nterruptedExcept on e) {
      Thread.currentThread(). nterrupt();
      LOG.warn(" nterrupted wh le load ng sc ma from ZK at {}.\n{}", sc maPathOnZk, e);
      return null;
    } catch (ZooKeeperCl ent.ZooKeeperConnect onExcept on e) {
      LOG.warn("Except on wh le load ng sc ma from ZK at {}.\n{}", sc maPathOnZk, e);
      return null;
    }
     f (rawBytes == null) {
      LOG.warn("Got null sc ma from ZooKeeper at {}.", sc maPathOnZk);
      return null;
    }
    Str ng sc maStr ng = new Str ng(rawBytes, Charsets.UTF_8);
    Thr ftSc ma thr ftSc ma = new Thr ftSc ma();
    try {
      Thr ftUt ls.fromTextFormat(sc maStr ng, thr ftSc ma);
      return thr ftSc ma;
    } catch (TExcept on e) {
      LOG.warn("Unable to deser al ze Thr ftSc ma loaded from ZK at {}.\n{}", sc maPathOnZk, e);
      return null;
    }
  }

  @V s bleForTest ng
  protected Str ng getSc maF leNa () {
    return  ndexConf g.getCluster().na ().toLo rCase()
        + UpdateableEarlyb rdStateManager.SCHEMA_SUFF X
        +  ndexConf g.getSc ma().getMajorVers onNumber();
  }

  @V s bleForTest ng
  protected Str ng getFullSc maPathOnZK() {
    return Str ng.format("%s/%s", sc maLocat onOnZK, getSc maF leNa ());
  }

  LocalF le getCanarySc maF leOnLocal() {
    Str ng canarySc maF lePath =
        Str ng.format("%s/%s", sc maLocat onOnLocal, getSc maF leNa ());
    return new LocalF le(new F le(canarySc maF lePath));
  }

  vo d setNoShutdownW nNot nLa t(boolean noShutdown) {
    noShutdownW nNot nLa t.set(noShutdown ? 1 : 0);
  }

  @Overr de
  protected vo d runOne erat on() {
    updateSc ma(zkCl ent);
    updatePart  onConf g(sc dulerCl ent);

    LOG. nfo("Reload ng models.");
    scor ngModelsManager.reload();
    tensorflowModelsManager.run();

    Random random = new Random();

    try {
      //   had an  ssue w re HDFS operat ons  re block ng, so reload ng t se models
      // was f n sh ng at t  sa  t   on each  nstance and after that every t   an  nstance
      // was reload ng models,   was happen ng at t  sa  t  . T  caused  ssues w h HDFS
      // load.   now place a "guard" wa  ng t   after each reload so that t  execut on t  
      // on every  nstance  s d fferent and t se calls can't eas ly sync to t  sa  po nt  n t  .
       nt sleepSeconds = random.next nt(30 * 60);
      LOG. nfo("Sleep ng for {} seconds", sleepSeconds);
      clock.wa For(sleepSeconds * 1000);
    } catch ( nterruptedExcept on ex) {
      LOG. nfo(" nterrupted wh le sleep ng");
    }
  }

  publ c vo d setEarlyb rdServer(Earlyb rdServer earlyb rdServer) {
    t .earlyb rdServer = earlyb rdServer;
  }
}
