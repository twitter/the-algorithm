package com.tw ter.search.earlyb rd.factory;

 mport java. o. OExcept on;
 mport java.lang.manage nt.Manage ntFactory;
 mport java.ut l.Opt onal;
 mport java.ut l.concurrent.Sc duledThreadPoolExecutor;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;
 mport com.sun.manage nt.Operat ngSystemMXBean;

 mport org.apac .d rectory.ap .ut l.Str ngs;
 mport org.apac .hadoop.fs.F leSystem;
 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;
 mport org.apac .kafka.common.Top cPart  on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.f nagle.stats. tr csStatsRece ver;
 mport com.tw ter.f nagle.stats.StatsRece ver;
 mport com.tw ter.search.common.aurora.AuroraSc dulerCl ent;
 mport com.tw ter.search.common.concurrent.Sc duledExecutorServ ceFactory;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.f le.F leUt ls;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver mpl;
 mport com.tw ter.search.common.part  on ng.zookeeper.SearchZkCl ent;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.search.term nat on.QueryT  outFactory;
 mport com.tw ter.search.common.ut l. o.kafka.F nagleKafkaCl entUt ls;
 mport com.tw ter.search.common.ut l. o.kafka.Thr ftDeser al zer;
 mport com.tw ter.search.common.ut l.ml.tensorflow_eng ne.TensorflowModelsManager;
 mport com.tw ter.search.common.ut l.zktrylock.ZooKeeperTryLockFactory;
 mport com.tw ter.search.common.ut l.zookeeper.ZooKeeperProxy;
 mport com.tw ter.search.earlyb rd.Earlyb rdCPUQual yFactor;
 mport com.tw ter.search.earlyb rd.Earlyb rdDarkProxy;
 mport com.tw ter.search.earlyb rd.Earlyb rdF nagleServerManager;
 mport com.tw ter.search.earlyb rd.Earlyb rdFuturePoolManager;
 mport com.tw ter.search.earlyb rd.Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.Earlyb rdProduct onF nagleServerManager;
 mport com.tw ter.search.earlyb rd.Earlyb rdServerSetManager;
 mport com.tw ter.search.earlyb rd.Earlyb rdWarmUpManager;
 mport com.tw ter.search.earlyb rd.Qual yFactor;
 mport com.tw ter.search.earlyb rd.ServerSet mber;
 mport com.tw ter.search.earlyb rd.UpdateableEarlyb rdStateManager;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veEarlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veSearchPart  onManager;
 mport com.tw ter.search.earlyb rd.common.CaughtUpMon or;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserScrubGeoMap;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserUpdatesC cker;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.except on.M ss ngKafkaTop cExcept on;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdSeg ntFactory;
 mport com.tw ter.search.earlyb rd.ml.Scor ngModelsManager;
 mport com.tw ter.search.earlyb rd.part  on.Aud oSpaceEventsStream ndexer;
 mport com.tw ter.search.earlyb rd.part  on.Aud oSpaceTable;
 mport com.tw ter.search.earlyb rd.part  on.Dynam cPart  onConf g;
 mport com.tw ter.search.earlyb rd.part  on.Earlyb rd ndexFlus r;
 mport com.tw ter.search.earlyb rd.part  on.Earlyb rd ndexLoader;
 mport com.tw ter.search.earlyb rd.part  on.Earlyb rdKafkaConsu r;
 mport com.tw ter.search.earlyb rd.part  on.Earlyb rdStartup;
 mport com.tw ter.search.earlyb rd.part  on.Opt m zat onAndFlush ngCoord nat onLock;
 mport com.tw ter.search.earlyb rd.part  on.T  L m edHadoopEx stsCall;
 mport com.tw ter.search.earlyb rd.part  on.UserScrubGeoEventStream ndexer;
 mport com.tw ter.search.earlyb rd.part  on.freshstartup.FreshStartupHandler;
 mport com.tw ter.search.earlyb rd.part  on.HdfsUt l;
 mport com.tw ter.search.earlyb rd.part  on.KafkaStartup;
 mport com.tw ter.search.earlyb rd.part  on.Mult Seg ntTermD ct onaryManager;
 mport com.tw ter.search.earlyb rd.part  on.Part  onManager;
 mport com.tw ter.search.earlyb rd.part  on.Part  onManagerStartup;
 mport com.tw ter.search.earlyb rd.part  on.Part  onWr er;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSyncConf g;
 mport com.tw ter.search.earlyb rd.part  on.StartupUserEvent ndexer;
 mport com.tw ter.search.earlyb rd.part  on.T etCreateHandler;
 mport com.tw ter.search.earlyb rd.part  on.T etUpdateHandler;
 mport com.tw ter.search.earlyb rd.part  on.UserUpdatesStream ndexer;
 mport com.tw ter.search.earlyb rd.querycac .QueryCac Conf g;
 mport com.tw ter.search.earlyb rd.querycac .QueryCac Manager;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.ut l.Coord natedEarlyb rdAct on;
 mport com.tw ter.search.earlyb rd.ut l.Earlyb rdDec der;
 mport com.tw ter.search.earlyb rd.ut l.TermCountMon or;
 mport com.tw ter.search.earlyb rd.ut l.T etCountMon or;
 mport com.tw ter.ubs.thr ftjava.Aud oSpaceBaseEvent;

/**
 * Product on module that prov des Earlyb rd components.
 */
publ c class Earlyb rdW reModule {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdW reModule.class);
  pr vate stat c f nal  nt MAX_POLL_RECORDS = 1000;

  /**
   * How many threads   w ll use for bu ld ng up t  query cac  dur ng startup.
   * T  number of threads w ll be set to 1 after t  earlyb rd  s current.
   */
  pr vate stat c f nal  nt QUERY_CACHE_NUM_WORKER_THREADS_AT_STARTUP =
      Earlyb rdConf g.get nt("query_cac _updater_startup_threads", 1);

  /**
   * Sc duled executor serv ce factory can be re-used  n product on.
   * All t  managers can share t  sa  executor serv ce factory.
   */
  pr vate f nal Sc duledExecutorServ ceFactory sharedExecutorServ ceFactory =
      new Sc duledExecutorServ ceFactory();

  pr vate f nal SearchStatsRece ver sharedSearchStatsRece ver = new SearchStatsRece ver mpl();
  pr vate f nal StatsRece ver sharedF nagleStatsRece ver = new  tr csStatsRece ver();

  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet =
      new Search ndex ng tr cSet(sharedSearchStatsRece ver);

  pr vate f nal Earlyb rdSearc rStats t etsSearc rStats =
      new Earlyb rdSearc rStats(sharedSearchStatsRece ver);

  pr vate f nal CaughtUpMon or  ndexCaughtUpMon or = new CaughtUpMon or("dl_ ndex");

  publ c CaughtUpMon or prov de ndexCaughtUpMon or() {
    return  ndexCaughtUpMon or;
  }

  pr vate f nal CaughtUpMon or kafka ndexCaughtUpMon or = new CaughtUpMon or("kafka_ ndex");

  publ c CaughtUpMon or prov deKafka ndexCaughtUpMon or() {
    return kafka ndexCaughtUpMon or;
  }

  pr vate f nal Opt m zat onAndFlush ngCoord nat onLock opt m zat onAndFlush ngCoord nat onLock =
      new Opt m zat onAndFlush ngCoord nat onLock();

  publ c Opt m zat onAndFlush ngCoord nat onLock prov deOpt m zat onAndFlush ngCoord nat onLock() {
    return opt m zat onAndFlush ngCoord nat onLock;
  }

  publ c QueryT  outFactory prov deQueryT  outFactory() {
    return new QueryT  outFactory();
  }

  publ c stat c class ZooKeeperCl ents {
    publ c ZooKeeperProxy d scoveryCl ent;
    publ c ZooKeeperProxy stateCl ent;

    publ c ZooKeeperCl ents() {
      t (
          SearchZkCl ent.getServ ceD scoveryZooKeeperCl ent(),
          SearchZkCl ent.getSZooKeeperCl ent());
    }

    publ c ZooKeeperCl ents(ZooKeeperProxy d scoveryCl ent, ZooKeeperProxy stateCl ent) {
      t .d scoveryCl ent = d scoveryCl ent;
      t .stateCl ent = stateCl ent;
    }
  }

  /**
   * Prov des t  earlyb rd dec der.
   */
  publ c Dec der prov deDec der() {
    return Earlyb rdDec der. n  al ze();
  }

  /**
   * Prov des t  set of ZooKeeper cl ents to be used by earlyb rd.
   */
  publ c ZooKeeperCl ents prov deZooKeeperCl ents() {
    return new ZooKeeperCl ents();
  }

  /**
   * Prov des t  query cac  conf g.
   */
  publ c QueryCac Conf g prov deQueryCac Conf g(SearchStatsRece ver searchStatsRece ver) {
    return new QueryCac Conf g(searchStatsRece ver);
  }

  /**
   * Prov des t  earlyb rd  ndex conf g.
   */
  publ c Earlyb rd ndexConf g prov deEarlyb rd ndexConf g(
      Dec der dec der, Search ndex ng tr cSet  ndex ng tr cSet,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    return Earlyb rd ndexConf gUt l.createEarlyb rd ndexConf g(dec der,  ndex ng tr cSet,
        cr  calExcept onHandler);
  }

  publ c Dynam cPart  onConf g prov deDynam cPart  onConf g() {
    return new Dynam cPart  onConf g(Part  onConf gUt l. n Part  onConf g());
  }

  /**
   * Prov des t  seg nt manager to be used by t  earlyb rd.
   */
  publ c Seg ntManager prov deSeg ntManager(
      Dynam cPart  onConf g dynam cPart  onConf g,
      Earlyb rd ndexConf g earlyb rd ndexConf g,
      Search ndex ng tr cSet part  on ndex ng tr cSet,
      Earlyb rdSearc rStats searc rStats,
      SearchStatsRece ver earlyb rdServerStats,
      UserUpdatesC cker userUpdatesC cker,
      Seg ntSyncConf g seg ntSyncConf g,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      Clock clock,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    return new Seg ntManager(
        dynam cPart  onConf g,
        earlyb rd ndexConf g,
        part  on ndex ng tr cSet,
        searc rStats,
        earlyb rdServerStats,
        userUpdatesC cker,
        seg ntSyncConf g,
        userTable,
        userScrubGeoMap,
        clock,
        Earlyb rdConf g.getMaxSeg ntS ze(),
        cr  calExcept onHandler,
        prov deKafka ndexCaughtUpMon or());
  }

  publ c QueryCac Manager prov deQueryCac Manager(
      QueryCac Conf g conf g,
      Earlyb rd ndexConf g  ndexConf g,
       nt maxEnabledSeg nts,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      Sc duledExecutorServ ceFactory queryCac UpdaterSc duledExecutorFactory,
      SearchStatsRece ver searchStatsRece ver,
      Earlyb rdSearc rStats searc rStats,
      Dec der dec der,
      Cr  calExcept onHandler cr  calExcept onHandler,
      Clock clock) {
    return new QueryCac Manager(conf g,  ndexConf g, maxEnabledSeg nts, userTable,
        userScrubGeoMap, queryCac UpdaterSc duledExecutorFactory, searchStatsRece ver,
        searc rStats, dec der, cr  calExcept onHandler, clock);
  }

  publ c TermCountMon or prov deTermCountMon or(
      Seg ntManager seg ntManager, Sc duledExecutorServ ceFactory executorServ ceFactory,
      SearchStatsRece ver searchStatsRece ver,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    return new TermCountMon or(seg ntManager, executorServ ceFactory, 500, T  Un .M LL SECONDS,
        searchStatsRece ver, cr  calExcept onHandler);
  }

  publ c T etCountMon or prov deT etCountMon or(
      Seg ntManager seg ntManager,
      Sc duledExecutorServ ceFactory executorServ ceFactory,
      SearchStatsRece ver searchStatsRece ver,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    return new T etCountMon or(seg ntManager, executorServ ceFactory, 500,
        T  Un .M LL SECONDS, searchStatsRece ver, cr  calExcept onHandler);
  }

  /**
   * Returns a manager that keeps track of earlyb rd's global state wh le   runs.
   */
  publ c UpdateableEarlyb rdStateManager prov deUpdateableEarlyb rdStateManager(
      Earlyb rd ndexConf g earlyb rd ndexConf g,
      Dynam cPart  onConf g dynam cPart  onConf g,
      ZooKeeperProxy zooKeeperCl ent,
      AuroraSc dulerCl ent sc dulerCl ent,
      Sc duledExecutorServ ceFactory executorServ ceFactory,
      Scor ngModelsManager scor ngModelsManager,
      TensorflowModelsManager tensorflowModelsManager,
      SearchStatsRece ver searchStatsRece ver,
      SearchDec der searchDec der,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    Clock clock = prov deClockForStateManager();

    return new UpdateableEarlyb rdStateManager(
        earlyb rd ndexConf g, dynam cPart  onConf g, zooKeeperCl ent, sc dulerCl ent,
        executorServ ceFactory, scor ngModelsManager, tensorflowModelsManager, searchStatsRece ver,
        searchDec der, cr  calExcept onHandler,
        clock);
  }

  publ c Clock prov deClockForStateManager() {
    return t .prov deClock();
  }

  publ c Sc duledExecutorServ ceFactory prov dePart  onManagerExecutorFactory() {
    return sharedExecutorServ ceFactory;
  }

  publ c Sc duledExecutorServ ceFactory prov deStateUpdateManagerExecutorFactory() {
    return sharedExecutorServ ceFactory;
  }

  publ c Sc duledExecutorServ ceFactory prov deTermCountMon orSc duledExecutorFactory() {
    return sharedExecutorServ ceFactory;
  }

  publ c Sc duledExecutorServ ceFactory prov deT etCountMon orSc duledExecutorFactory() {
    return sharedExecutorServ ceFactory;
  }

  /**
   * Prov des t  Sc duledExecutorServ ceFactory that w ll be used to sc dule all query cac 
   * update tasks.
   */
  publ c Sc duledExecutorServ ceFactory prov deQueryCac UpdateTaskSc duledExecutorFactory() {
    return new Sc duledExecutorServ ceFactory() {
      @Overr de
      publ c QueryCac UpdaterSc duledExecutorServ ce<Sc duledThreadPoolExecutor> bu ld(
          Str ng threadNa Format, boolean  sDaemon) {
        Sc duledThreadPoolExecutor threadpoolExecutor =
            new Sc duledThreadPoolExecutor(QUERY_CACHE_NUM_WORKER_THREADS_AT_STARTUP,
                bu ldThreadFactory(threadNa Format,  sDaemon));
        threadpoolExecutor.setMax mumPoolS ze(QUERY_CACHE_NUM_WORKER_THREADS_AT_STARTUP);
        threadpoolExecutor.setCorePoolS ze(QUERY_CACHE_NUM_WORKER_THREADS_AT_STARTUP);
        threadpoolExecutor.setExecuteEx st ngDelayedTasksAfterShutdownPol cy(false);
        threadpoolExecutor.setCont nueEx st ngPer od cTasksAfterShutdownPol cy(false);
        threadpoolExecutor.setRemoveOnCancelPol cy(true);
        LOG. nfo("Start ng query cac  executor w h {} thread.",
            QUERY_CACHE_NUM_WORKER_THREADS_AT_STARTUP);

        return new QueryCac UpdaterSc duledExecutorServ ce<Sc duledThreadPoolExecutor>(
            threadpoolExecutor) {
          @Overr de publ c vo d setWorkerPoolS zeAfterStartup() {
            delegate.setCorePoolS ze(1);
            delegate.setMax mumPoolS ze(1);
            LOG. nfo("Reset query cac  executor to be s ngle threaded.");
          }
        };
      }
    };
  }

  publ c Sc duledExecutorServ ceFactory prov deS mpleUserUpdate ndexerSc duledExecutorFactory() {
    return sharedExecutorServ ceFactory;
  }

  /**
   * Returns t  manager that manages t  pool of searc r threads.
   */
  publ c Earlyb rdFuturePoolManager prov deFuturePoolManager() {
    return new Earlyb rdFuturePoolManager("Searc rWorker");
  }

  /**
   * Returns t  manager that manages all earlyb rd f nagle servers (warm up and product on).
   */
  publ c Earlyb rdF nagleServerManager prov deF nagleServerManager(
      Cr  calExcept onHandler cr  calExcept onHandler) {
    return new Earlyb rdProduct onF nagleServerManager(cr  calExcept onHandler);
  }

  /**
   * Creates t  product on serverset manager.
   */
  publ c Earlyb rdServerSetManager prov deServerSetManager(
      ZooKeeperProxy d scoveryCl ent,
      Dynam cPart  onConf g dynam cPart  onConf g,
      SearchStatsRece ver searchStatsRece ver,
       nt port,
      Str ng serverSetNa Pref x) {
    return new Earlyb rdServerSetManager(
        searchStatsRece ver,
        d scoveryCl ent,
        dynam cPart  onConf g.getCurrentPart  onConf g(),
        port,
        serverSetNa Pref x);
  }

  /**
   * Creates t  warm up serverset manager.
   */
  publ c Earlyb rdWarmUpManager prov deWarmUpManager(
      ZooKeeperProxy d scoveryCl ent,
      Dynam cPart  onConf g dynam cPart  onConf g,
      SearchStatsRece ver searchStatsRece ver,
      Dec der dec der,
      Clock clock,
       nt port,
      Str ng serverSetNa Pref x) {
    return new Earlyb rdWarmUpManager(
        new Earlyb rdServerSetManager(
            searchStatsRece ver,
            d scoveryCl ent,
            dynam cPart  onConf g.getCurrentPart  onConf g(),
            port,
            serverSetNa Pref x),
        dynam cPart  onConf g.getCurrentPart  onConf g(),
        search ndex ng tr cSet,
        dec der,
        clock);
  }

  /**
   * Returns a dark proxy that knows how to send dark traff c to t  warm up earlyb rd serverset.
   */
  publ c Earlyb rdDarkProxy prov deEarlyb rdDarkProxy(
      SearchDec der searchDec der,
      StatsRece ver f nagleStatsRece ver,
      Earlyb rdServerSetManager earlyb rdServerSetManager,
      Earlyb rdWarmUpManager earlyb rdWarmUpManager,
      Str ng clusterNa ) {
    return new Earlyb rdDarkProxy(searchDec der,
                                  f nagleStatsRece ver.scope("dark_proxy"),
                                  earlyb rdServerSetManager,
                                  earlyb rdWarmUpManager,
                                  clusterNa );
  }


  /**
   * Returns t  manager for all (non-Tensorflow) scor ng models.
   */
  publ c Scor ngModelsManager prov deScor ngModelsManager(
      SearchStatsRece ver serverStats,
      Earlyb rd ndexConf g earlyb rd ndexConf g) {
    boolean modelsEnabled = Earlyb rdConf g.getBool("scor ng_models_enabled", false);
     f (!modelsEnabled) {
      LOG. nfo("Scor ng Models - D sabled  n t  conf g. Not load ng any models.");
      serverStats.getCounter("scor ng_models_d sabled_ n_conf g"). ncre nt();
      return Scor ngModelsManager.NO_OP_MANAGER;
    }

    Str ng hdfsNa Node = Earlyb rdConf g.getStr ng("scor ng_models_na node");
    Str ng hdfsModelsPath = Earlyb rdConf g.getStr ng("scor ng_models_based r");
    try {
      return Scor ngModelsManager.create(
          serverStats, hdfsNa Node, hdfsModelsPath, earlyb rd ndexConf g.getSc ma());
    } catch ( OExcept on e) {
      LOG.error("Scor ng Models - Error creat ng Scor ngModelsManager", e);
      serverStats.getCounter("scor ng_models_ n  al zat on_errors"). ncre nt();
      return Scor ngModelsManager.NO_OP_MANAGER;
    }
  }

  /**
   * Prov des t  manager for all Tensorflow models.
   */
  publ c TensorflowModelsManager prov deTensorflowModelsManager(
      SearchStatsRece ver serverStats,
      Str ng statsPref x,
      Dec der dec der,
      Earlyb rd ndexConf g earlyb rd ndexConf g) {

    boolean modelsEnabled = Earlyb rdProperty.TF_MODELS_ENABLED.get(false);

     f (!modelsEnabled) {
      LOG. nfo("Tensorflow Models - D sabled  n t  conf g. Not load ng any models.");
      serverStats.getCounter("tf_models_d sabled_ n_conf g"). ncre nt();
      return TensorflowModelsManager.createNoOp(statsPref x);
    }

    Str ng modelsConf gPath =
        Precond  ons.c ckNotNull(Earlyb rdProperty.TF_MODELS_CONF G_PATH.get());


     nt  ntraOpThreads = Precond  ons.c ckNotNull(Earlyb rdProperty.TF_ NTRA_OP_THREADS.get(0));
     nt  nterOpThreads = Precond  ons.c ckNotNull(Earlyb rdProperty.TF_ NTER_OP_THREADS.get(0));

    TensorflowModelsManager. n TensorflowThreadPools( ntraOpThreads,  nterOpThreads);

    return TensorflowModelsManager.createUs ngConf gF le(
        F leUt ls.getF leHandle(modelsConf gPath),
        true,
        statsPref x,
        () -> Dec derUt l. sAva lableForRandomRec p ent(
          dec der, "enable_tf_serve_models"),
        () -> dec der. sAva lable("enable_tf_load_models"),
        earlyb rd ndexConf g.getSc ma());
  }

  publ c SearchStatsRece ver prov deEarlyb rdServerStatsRece ver() {
    return sharedSearchStatsRece ver;
  }

  publ c StatsRece ver prov deF nagleStatsRece ver() {
    return sharedF nagleStatsRece ver;
  }

  publ c Search ndex ng tr cSet prov deSearch ndex ng tr cSet() {
    return search ndex ng tr cSet;
  }

  publ c Earlyb rdSearc rStats prov deT etsSearc rStats() {
    return t etsSearc rStats;
  }

  /**
   * Prov des t  clock to be used by t  earlyb rd.
   */
  publ c Clock prov deClock() {
    return Clock.SYSTEM_CLOCK;
  }

  /**
   * Prov des t  conf g for t  mult -seg nt term d ct onary manager.
   */
  publ c Mult Seg ntTermD ct onaryManager.Conf g prov deMult Seg ntTermD ct onaryManagerConf g() {
    return new Mult Seg ntTermD ct onaryManager.Conf g(
        L sts.newArrayL st(
            Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa ()));
  }

  /**
   * Prov des t  manager for t  term d ct onary that spans all seg nts.
   */
  publ c Mult Seg ntTermD ct onaryManager prov deMult Seg ntTermD ct onaryManager(
      Mult Seg ntTermD ct onaryManager.Conf g termD ct onaryConf g,
      Seg ntManager seg ntManager,
      SearchStatsRece ver statsRece ver,
      Dec der dec der,
      Earlyb rdCluster earlyb rdCluster) {
    return new Mult Seg ntTermD ct onaryManager(
        termD ct onaryConf g, seg ntManager, statsRece ver, dec der, earlyb rdCluster);
  }

  /**
   * Returns t  part  on manager to be used by t  arch ve earlyb rds.
   */
  publ c Part  onManager prov deFullArch vePart  onManager(
      ZooKeeperTryLockFactory zooKeeperTryLockFactory,
      QueryCac Manager queryCac Manager,
      Seg ntManager seg ntManager,
      Dynam cPart  onConf g dynam cPart  onConf g,
      UserUpdatesStream ndexer userUpdatesStream ndexer,
      UserScrubGeoEventStream ndexer userScrubGeoEventStream ndexer,
      SearchStatsRece ver searchStatsRece ver,
      Arch veEarlyb rd ndexConf g earlyb rd ndexConf g,
      ServerSet mber serverSet mber,
      Sc duledExecutorServ ceFactory executorServ ceFactory,
      Sc duledExecutorServ ceFactory userUpdate ndexerExecutorFactory,
      Search ndex ng tr cSet earlyb rdSearch ndex ng tr cSet,
      Clock clock,
      Seg ntSyncConf g seg ntSyncConf g,
      Cr  calExcept onHandler cr  calExcept onHandler) throws  OExcept on {

    return new Arch veSearchPart  onManager(
        zooKeeperTryLockFactory,
        queryCac Manager,
        seg ntManager,
        dynam cPart  onConf g,
        userUpdatesStream ndexer,
        userScrubGeoEventStream ndexer,
        searchStatsRece ver,
        earlyb rd ndexConf g,
        serverSet mber,
        executorServ ceFactory,
        userUpdate ndexerExecutorFactory,
        earlyb rdSearch ndex ng tr cSet,
        seg ntSyncConf g,
        clock,
        cr  calExcept onHandler);
  }

  /**
   * Prov des t  Seg ntSyncConf g  nstance to be used by earlyb rd.
   */
  publ c Seg ntSyncConf g prov deSeg ntSyncConf g(Earlyb rdCluster cluster) {
    Str ng scrubGen = null;
     f (cluster == Earlyb rdCluster.FULL_ARCH VE) {
      scrubGen = Earlyb rdProperty.EARLYB RD_SCRUB_GEN.get();
      LOG. nfo("T  scrubGen prov ded from Aurora  s: {}", scrubGen);
      Precond  ons.c ckState(Str ngs. sNotEmpty(scrubGen));
    }
    return new Seg ntSyncConf g(Opt onal.ofNullable(scrubGen));
  }

  protected vo d storeEarlyb rdStartupProducts(
      T etCreateHandler t etCreateHandler,
      Part  onWr er part  onWr er,
      Earlyb rd ndexFlus r earlyb rd ndexFlus r
  ) {
    // TestW reModule wants to store t se for furt r use.
  }

  /**
   * What d rectory are   go ng to load seg nts from on startup.
   *
   * W n   runn ng loadtests or stag ngN  nstances and t y don't have a recent  ndex
   * flus d,   can take h s to generate a new  ndex w h a fresh startup. T  slows
   * down develop nt.  f t  read_ ndex_from_prod_locat on flag  s set to true,   w ll read
   * t   ndex from t  locat on w re prod  nstances are flush ng t  r  ndex to.
   * Unset    f   want to generate y  own  ndex.
   *
   * @return a str ng w h t  d rectory.
   */
  publ c Str ng get ndexLoad ngD rectory() {
    boolean read ndexFromProdLocat on = Earlyb rdProperty.READ_ NDEX_FROM_PROD_LOCAT ON.get(false);
    Str ng env ron nt = Earlyb rdProperty.ENV.get("no_env_spec f ed"); // default value for tests.
    Str ng read ndexD r = Earlyb rdProperty.HDFS_ NDEX_SYNC_D R.get();

     f (read ndexFromProdLocat on) {
      LOG. nfo("W ll attempt to read  ndex from prod locat ons");
      LOG. nfo(" ndex d rectory prov ded: {}", read ndexD r);
      // Replac ng t  path  s a b  hacky, but   works ok.
      read ndexD r = read ndexD r.replace("/" + env ron nt + "/", "/prod/");
      LOG. nfo("W ll  nstead use  ndex d rectory: {}", read ndexD r);
    }

    return read ndexD r;
  }

  /**
   *  ndexer for aud o space events.
   */
  publ c Aud oSpaceEventsStream ndexer prov deAud oSpaceEventsStream ndexer(
      Aud oSpaceTable aud oSpaceTable,
      Clock clock) {
    try {
      return new Aud oSpaceEventsStream ndexer(
          F nagleKafkaCl entUt ls.newKafkaConsu rForAss gn ng(
              "",
              new Thr ftDeser al zer<>(Aud oSpaceBaseEvent.class),
              "",
              20
          ), aud oSpaceTable, clock);
    } catch (M ss ngKafkaTop cExcept on ex) {
      LOG.error("M ss ng kafka stream", ex);
      return null;
    }
  }

  /**
   * Returns a class to start t  Earlyb rd. See {@l nk Earlyb rdStartup}.
   */
  publ c Earlyb rdStartup prov deEarlyb rdStartup(
      Part  onManager part  onManager,
      UserUpdatesStream ndexer userUpdatesStream ndexer,
      UserScrubGeoEventStream ndexer userScrubGeoEventStream ndexer,
      Aud oSpaceEventsStream ndexer aud oSpaceEventsStream ndexer,
      Dynam cPart  onConf g dynam cPart  onConf g,
      Cr  calExcept onHandler cr  calExcept onHandler,
      Seg ntManager seg ntManager,
      Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager,
      QueryCac Manager queryCac Manager,
      ZooKeeperTryLockFactory zooKeeperTryLockFactory,
      ServerSet mber serverSet mber,
      Clock clock,
      Seg ntSyncConf g seg ntSyncConf g,
      Earlyb rdSeg ntFactory earlyb rdSeg ntFactory,
      Earlyb rdCluster cluster,
      SearchDec der dec der) throws  OExcept on {
     f (cluster == Earlyb rdCluster.FULL_ARCH VE) {
      return new Part  onManagerStartup(clock, part  onManager);
    }

    // C ck that t  earlyb rd na   s what  're expect ng so   can bu ld t  kafka top cs.
    Str ng earlyb rdNa  = Earlyb rdProperty.EARLYB RD_NAME.get();
    Precond  ons.c ckArgu nt("earlyb rd-realt  ".equals(earlyb rdNa )
        || "earlyb rd-protected".equals(earlyb rdNa )
        || "earlyb rd-realt  -exp0".equals(earlyb rdNa )
        || "earlyb rd-realt  _cg".equals(earlyb rdNa ));

    StartupUserEvent ndexer startupUserEvent ndexer = new StartupUserEvent ndexer(
        prov deSearch ndex ng tr cSet(),
        userUpdatesStream ndexer,
        userScrubGeoEventStream ndexer,
        seg ntManager,
        clock);

    // Coord nate leav ng t  serverset to flush seg nts to HDFS.
    Coord natedEarlyb rdAct on act onCoord nator = new Coord natedEarlyb rdAct on(
        zooKeeperTryLockFactory,
        "seg nt_flus r",
        dynam cPart  onConf g,
        serverSet mber,
        cr  calExcept onHandler,
        seg ntSyncConf g);
    act onCoord nator.setShouldSynchron ze(true);

    F leSystem hdfsF leSystem = HdfsUt l.getHdfsF leSystem();
    Earlyb rd ndexFlus r earlyb rd ndexFlus r = new Earlyb rd ndexFlus r(
        act onCoord nator,
        hdfsF leSystem,
        Earlyb rdProperty.HDFS_ NDEX_SYNC_D R.get(),
        seg ntManager,
        dynam cPart  onConf g.getCurrentPart  onConf g(),
        clock,
        new T  L m edHadoopEx stsCall(hdfsF leSystem),
        prov deOpt m zat onAndFlush ngCoord nat onLock());

    Str ng baseTop cNa  = "search_ ngester_%s_events_%s_%s";

    Str ng earlyb rdType;

     f ("earlyb rd-protected".equals(earlyb rdNa )) {
      earlyb rdType = "protected";
    } else  f ("earlyb rd-realt  _cg".equals(earlyb rdNa )) {
      earlyb rdType = "realt  _cg";
    } else {
      earlyb rdType = "realt  ";
    }

    Str ng t etTop cNa  = Str ng.format(
        baseTop cNa ,
        " ndex ng",
        earlyb rdType,
        Earlyb rdProperty.KAFKA_ENV.get());

    Str ng updateTop cNa  = Str ng.format(
        baseTop cNa ,
        "update",
        earlyb rdType,
        Earlyb rdProperty.KAFKA_ENV.get());

    LOG. nfo("T et top c: {}", t etTop cNa );
    LOG. nfo("Update top c: {}", updateTop cNa );

    Top cPart  on t etTop c = new Top cPart  on(
        t etTop cNa ,
        dynam cPart  onConf g.getCurrentPart  onConf g().get ndex ngHashPart  on D());
    Top cPart  on updateTop c = new Top cPart  on(
        updateTop cNa ,
        dynam cPart  onConf g.getCurrentPart  onConf g().get ndex ngHashPart  on D());

    Earlyb rdKafkaConsu rsFactory earlyb rdKafkaConsu rsFactory =
        prov deEarlyb rdKafkaConsu rsFactory();
    FreshStartupHandler freshStartupHandler = new FreshStartupHandler(
        clock,
        earlyb rdKafkaConsu rsFactory,
        t etTop c,
        updateTop c,
        seg ntManager,
        Earlyb rdConf g.getMaxSeg ntS ze(),
        Earlyb rdConf g.getLateT etBuffer(),
        cr  calExcept onHandler
    );

    T etUpdateHandler updateHandler = new T etUpdateHandler(seg ntManager);

    Coord natedEarlyb rdAct on postOpt m zat onRebu lds = new Coord natedEarlyb rdAct on(
            zooKeeperTryLockFactory,
            "post_opt m zat on_rebu lds",
            dynam cPart  onConf g,
            serverSet mber,
            cr  calExcept onHandler,
            seg ntSyncConf g
    );
    postOpt m zat onRebu lds.setShouldSynchron ze(true);
    Coord natedEarlyb rdAct on gcAct on = new Coord natedEarlyb rdAct on(
            zooKeeperTryLockFactory,
            "gc_before_opt m zat on",
            dynam cPart  onConf g,
            serverSet mber,
            cr  calExcept onHandler,
            seg ntSyncConf g
    );
    gcAct on.setShouldSynchron ze(true);

    T etCreateHandler createHandler = new T etCreateHandler(
        seg ntManager,
        prov deSearch ndex ng tr cSet(),
        cr  calExcept onHandler,
        mult Seg ntTermD ct onaryManager,
        queryCac Manager,
        postOpt m zat onRebu lds,
        gcAct on,
        Earlyb rdConf g.getLateT etBuffer(),
        Earlyb rdConf g.getMaxSeg ntS ze(),
        prov deKafka ndexCaughtUpMon or(),
        prov deOpt m zat onAndFlush ngCoord nat onLock());

    Part  onWr er part  onWr er = new Part  onWr er(
        createHandler,
        updateHandler,
        cr  calExcept onHandler,
        Pengu nVers on.vers onFromByteValue(Earlyb rdConf g.getPengu nVers onByte()),
        clock);

    KafkaConsu r<Long, Thr ftVers onedEvents> rawKafkaConsu r =
        earlyb rdKafkaConsu rsFactory.createKafkaConsu r(
            "earlyb rd_t et_kafka_consu r");

    Earlyb rdKafkaConsu r earlyb rdKafkaConsu r = prov deKafkaConsu r(
        cr  calExcept onHandler,
        rawKafkaConsu r,
        t etTop c,
        updateTop c,
        part  onWr er,
        earlyb rd ndexFlus r);

    Earlyb rd ndexLoader earlyb rd ndexLoader = new Earlyb rd ndexLoader(
        hdfsF leSystem,
        get ndexLoad ngD rectory(), // See SEARCH-32839
        Earlyb rdProperty.ENV.get("default_env_value"),
        dynam cPart  onConf g.getCurrentPart  onConf g(),
        earlyb rdSeg ntFactory,
        seg ntSyncConf g,
        clock);

    t .storeEarlyb rdStartupProducts(
        createHandler,
        part  onWr er,
        earlyb rd ndexFlus r
    );

    return new KafkaStartup(
        seg ntManager,
        earlyb rdKafkaConsu r,
        startupUserEvent ndexer,
        userUpdatesStream ndexer,
        userScrubGeoEventStream ndexer,
        aud oSpaceEventsStream ndexer,
        queryCac Manager,
        earlyb rd ndexLoader,
        freshStartupHandler,
        prov deSearch ndex ng tr cSet(),
        mult Seg ntTermD ct onaryManager,
        cr  calExcept onHandler,
        dec der
    );
  }

  publ c Qual yFactor prov deQual yFactor(
      Dec der dec der,
      SearchStatsRece ver searchStatsRece ver
  ) {
    return new Earlyb rdCPUQual yFactor(dec der,
        Manage ntFactory.getPlatformMXBean(Operat ngSystemMXBean.class),
        searchStatsRece ver);
  }

  /**
   * Returns a new UserUpdatesKafkaConsu r to read user updates.
   */
  publ c UserUpdatesStream ndexer prov deUserUpdatesKafkaConsu r(
      Seg ntManager seg ntManager) {
    try {
      return new UserUpdatesStream ndexer(
          UserUpdatesStream ndexer.prov deKafkaConsu r(),
          Earlyb rdProperty.USER_UPDATES_KAFKA_TOP C.get(),
          prov deSearch ndex ng tr cSet(),
          seg ntManager);
    } catch (M ss ngKafkaTop cExcept on ex) {
      // Yes,   w ll crash t  server.  've never seen t  top c m ss ng, but
      //  've seen so  ot rs, so   had to bu ld t  funct onal y  n t 
      // constructor.  f one day t  one goes m ss ng,  'll have to f gure out
      // how to handle  . For now,   crash.
      throw new Runt  Except on(ex);
    }
  }

  /**
   * Returns a new UserScrubGeosKafkaConsu r to read geo scrubb ng events.
   */
  publ c UserScrubGeoEventStream ndexer prov deUserScrubGeoEventKafkaConsu r(
      Seg ntManager seg ntManager) {
    try {
      return new UserScrubGeoEventStream ndexer(
          UserScrubGeoEventStream ndexer.prov deKafkaConsu r(),
          Earlyb rdProperty.USER_SCRUB_GEO_KAFKA_TOP C.get(),
          prov deSearch ndex ng tr cSet(),
          seg ntManager);
    } catch (M ss ngKafkaTop cExcept on ex) {
      /**
       * See {@l nk #prov deUserUpdatesKafkaConsu r}
       */
      throw new Runt  Except on(ex);
    }
  }

  /**
   * Returns a new Product onEarlyb rdKafkaConsu r to read Thr ftVers onedEvents.
   */
  publ c Earlyb rdKafkaConsu rsFactory prov deEarlyb rdKafkaConsu rsFactory() {
    return new Product onEarlyb rdKafkaConsu rsFactory(
        Earlyb rdProperty.KAFKA_PATH.get(),
        MAX_POLL_RECORDS
    );
  }

  /**
   * Returns a class to read T ets  n t  Earlyb rd. See {@l nk Earlyb rdKafkaConsu r}.
   */
  publ c Earlyb rdKafkaConsu r prov deKafkaConsu r(
      Cr  calExcept onHandler cr  calExcept onHandler,
      KafkaConsu r<Long, Thr ftVers onedEvents> rawKafkaConsu r,
      Top cPart  on t etTop c,
      Top cPart  on updateTop c,
      Part  onWr er part  onWr er,
      Earlyb rd ndexFlus r earlyb rd ndexFlus r
  ) {
    return new Earlyb rdKafkaConsu r(
        rawKafkaConsu r,
        prov deSearch ndex ng tr cSet(),
        cr  calExcept onHandler,
        part  onWr er,
        t etTop c,
        updateTop c,
        earlyb rd ndexFlus r,
        prov deKafka ndexCaughtUpMon or());
  }
}
